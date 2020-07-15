package com.souther.common.exception;

import com.souther.vo.bo.CommonResult;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Author souther
 * @Date 2020/5/14 14:05
 * @Version 1.0
 **/
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

  private static final String ARGUMENT_NOT_VALID = "参数[%s]校验失败：%s";

  // 字段校验
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResult<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    FieldError firstError = bindingResult.getFieldErrors().get(0);
    String info = String.format(ARGUMENT_NOT_VALID,
        firstError.getField(), firstError.getDefaultMessage());
    log.error(info);
    return CommonResult.error(info);
  }

  // 请求参数缺失
  @ExceptionHandler(BindException.class)
  public CommonResult<String> bindExceptionException(BindException e) {
    List<ObjectError> errors = e.getBindingResult().getAllErrors();
    ObjectError handleError = errors.get(0);
    String info = String.format(ARGUMENT_NOT_VALID,
        handleError.getCodes()[1], handleError.getDefaultMessage());
    log.error(info);
    return CommonResult.error(info);
  }

  // 请求参数缺失
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public CommonResult<String> missingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    String info = "缺少参数：" + e.getParameterName();
    log.error(info);
    return CommonResult.error(info);
  }

  // assert错误
  @ExceptionHandler(IllegalArgumentException.class)
  public CommonResult<String> illegalArgumentException(
      IllegalArgumentException e) {
    String info = "数据错误:" + e.getMessage();
    log.error(info);
    return CommonResult.error(info);
  }

  // 请求方式错误
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public CommonResult<String> httpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    String info = "请求方式错误:" + e.getMethod();
    log.error(info);
    return CommonResult.error(info);
  }

  @ExceptionHandler(ShiroException.class)
  public CommonResult<String> shiroException(ShiroException e) {
    String info = "权限异常:" + e.getMessage();
    log.info(info);
    return CommonResult.error(701,info);
  }

  @ExceptionHandler(IncorrectCredentialsException.class)
  public CommonResult<String> incorrectCredentialsException(IncorrectCredentialsException e) {
    String info = "不正确的凭证:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(ExpiredCredentialsException.class)
  public CommonResult<String> expiredCredentialsException(ExpiredCredentialsException e) {
    String info = "凭证过期:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(ConcurrentAccessException.class)
  public CommonResult<String> ConcurrentAccessException(ConcurrentAccessException e) {
    String info = "并发访问异常（多个用户同时登录时抛出）:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(UnknownAccountException.class)
  public CommonResult<String> UnknownAccountException(UnknownAccountException e) {
    String info = "未知的账号:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(ExcessiveAttemptsException.class)
  public CommonResult<String> ExcessiveAttemptsException(ExcessiveAttemptsException e) {
    String info = "认证次数超过限制:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(DisabledAccountException.class)
  public CommonResult<String> DisabledAccountException(DisabledAccountException e) {
    String info = "禁用的账号:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public CommonResult<String> UnauthorizedException(UnauthorizedException e) {
    String info = "请求的操作或对请求的资源的访问是不允许的:" + e.getMessage();
    log.info(info);
    return CommonResult.error(701,info);
  }

  @ExceptionHandler(UnauthenticatedException.class)
  public CommonResult<String> UnauthenticatedException(UnauthenticatedException e) {
    String info = "当尚未完成成功认证时，尝试执行授权操作时引发异常:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(LockedAccountException.class)
  public CommonResult<String> LockedAccountException(LockedAccountException e) {
    String info = "账号被锁定:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  @ExceptionHandler(UnsupportedTokenException.class)
  public CommonResult<String> UnsupportedTokenException(UnsupportedTokenException e) {
    String info = "使用了不支持的Token:" + e.getMessage();
    log.info(info);
    return CommonResult.error(700,info);
  }

  // 自定义异常
  @ExceptionHandler(MyException.class)
  public CommonResult<String> myException(
      MyException e) {
    String info = e.getMessage();
    log.error(info);
    return CommonResult.error(e.getCode(), info);
  }

  // 前端异常，不用记录
  @ExceptionHandler(FrontException.class)
  public CommonResult<String> frontException(
      FrontException e) {
    String info = e.getMessage();
    return CommonResult.error(e.getCode(), info);
  }

  // 默认异常处理
  @ExceptionHandler(Exception.class)
  public CommonResult<String> defaultException(Exception e) {
    e.printStackTrace();
    log.error("");
    log.error("*******************bugInfo begin*******************");
    log.error(e.toString());
    StackTraceElement[] stackTrace = e.getStackTrace();
    for (StackTraceElement item : stackTrace) {
      log.error(item.toString());
    }
    log.error("*******************bugInfo end*******************");
    return CommonResult.error(500, "系统正忙，请晚点再来。。。");
  }

}
