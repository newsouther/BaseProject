package com.souther.utils;

/**
 * @Auther: souther
 * @Date: 2020/7/2 15:50
 * @Description:
 */
public class CommonUtils {

  /** 随机数字字串获取 **/
  public static String getRandomNumCode(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append((int) (Math.random() * 10));
    }
    return sb.toString();
  }

}
