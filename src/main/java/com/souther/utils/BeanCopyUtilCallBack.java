package com.souther.utils;

/**
 * @Auther: souther
 * @Date: 2020/7/4 18:14
 * @Description:
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack <S, T> {

  /**
   * 定义默认回调方法
   * @param t
   * @param s
   */
  void callBack(S t, T s);
}