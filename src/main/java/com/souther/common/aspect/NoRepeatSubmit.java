package com.souther.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

/**
 * @Auther: souther
 * @Date: 2020/6/30 15:14
 * @Description: 防止重复提交标记注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {
  /**
   * 重复统计时长 unit：s
   * @return
   */
  int time() default 1;

  @AliasFor("time")
  int value() default 1;
}
