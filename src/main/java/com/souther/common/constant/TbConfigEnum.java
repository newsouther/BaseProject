package com.souther.common.constant;

import com.souther.vo.db.WxSmallDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 14:49
 * @Version 1.0
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TbConfigEnum {

  /*** ==================== 配置相关【基础项目专区，11xx】 ========================= **/

  /** 微信公众号登陆配置,{@link WxSmallDB} **/
  WX_PUBLIC_LOGIN(1101),

  /** 是否开发版本,1为开发版本 **/
  DEVELOPMENT(1102),

  /** shiro cache time,unit:s **/
  SHIRO_CACHE_TIME(1103),

  /**
   * 七牛配置,关联：QiNiuConfigDB
   */
  QI_NIU_CONFIG(1004),
  ;

  private Integer type;

}
