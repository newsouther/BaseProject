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

  /*** ==================== 配置相关========================= **/

  /** 微信公众号登陆配置,{@link WxSmallDB} **/
  WX_PUBLIC_LOGIN(1101),

  /** 是否开发版本,1为开发版本 **/
  DEVELOPMENT(1102),
  ;

  private Integer type;

}
