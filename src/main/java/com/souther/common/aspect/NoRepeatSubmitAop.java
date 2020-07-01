package com.souther.common.aspect;

import com.alibaba.fastjson.JSON;
import com.souther.common.exception.MyException;
import com.souther.utils.Md5Util;
import com.souther.utils.RedisUtil;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Auther: souther
 * @Date: 2020/6/30 15:05
 * @Description:
 */
@Aspect
@Slf4j
public class NoRepeatSubmitAop {

  private static final String JWT_TOKEN_KEY = "jwt-token";

  @Pointcut("@annotation(NoRepeatSubmit)")
  public void serviceNoRepeat() {
  }

  @Around("serviceNoRepeat()")
  public Object around(ProceedingJoinPoint pjp) throws Throwable {

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes()).getRequest();
    String jwtToken = request.getHeader(JWT_TOKEN_KEY);
    String key = Md5Util.md5(jwtToken + "-" + request.getRequestURL().toString() + "-" + JSON
        .toJSONString(request.getParameterMap()));
    if (RedisUtil.get(key) == null) {
      Object o = pjp.proceed();
      MethodSignature signature = (MethodSignature) pjp.getSignature();
      NoRepeatSubmit noRepeatSubmit = signature.getMethod().getAnnotation(NoRepeatSubmit.class);
      // 默认1秒内统一用户同一个地址同一个参数，视为重复提交
      RedisUtil.setExpire(key, "0", noRepeatSubmit.time(), TimeUnit.SECONDS);
      return o;
    } else {
      throw new MyException(600, "重复提交");
    }
  }

}
