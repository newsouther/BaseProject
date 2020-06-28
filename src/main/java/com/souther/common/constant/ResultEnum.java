package com.souther.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultEnum {

  /**
   * 请求成功
   **/
  SUCCESS(200, "请求成功"),

  /**
   * TOKEN为空
   **/
  TOKEN_BLANK(400, Constant.USER_TOKEN + "不能为空"),
  ;

  private int code;
  private String msg;

}
