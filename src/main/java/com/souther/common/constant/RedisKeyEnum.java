package com.souther.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum RedisKeyEnum {

  /**
   * 数据库常量,p1-类型
   **/
  DB_CONFIG("db_config_%s"),

  /** JWT-SESSION,p1-用户id **/
  JWT_SESSION("JWT_SESSION_%s")
  ;
  private String key;

  public String getKey() {
    return "mall:" + key;
  }
}
