package com.souther.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum RedisKeyEnum {

  /*** ==================== DB相关========================= **/

  /**
   * 数据库常量,p1-类型
   **/
  DB_CONFIG("db:config_%s"),

  /*** ==================== shiro相关========================= **/

  /**
   * JWT-SESSION,p1-用户id
   **/
  JWT_SESSION("shiro:JWT_SESSION_%s"),

  /**
   * shiro cache,p1-用户id
   **/
  SHIRO_CACHE("shiro:CACHE_%s"),

  ;

  private String key;

  public String getKey() {
    return Constant.REDIS_PREFIX + key;
  }
}
