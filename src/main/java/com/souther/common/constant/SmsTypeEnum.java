package com.souther.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Auther: souther
 * @Date: 2020/7/2 15:35
 * @Description: 短信类型
 */
@Getter
@AllArgsConstructor
public enum SmsTypeEnum {

  /**
   * 重置密码
   **/
  RET_PWD("retPwd"),
  ;

  private String type;
}
