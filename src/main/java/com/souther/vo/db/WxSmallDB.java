package com.souther.vo.db;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 14:52
 * @Version 1.0
 **/
@Setter
@Getter
public class WxSmallDB implements Serializable {

  private static final long serialVersionUID = -514258620030144903L;

  /**
   * 微信app Id
   */
  private String appId;

  /**
   * 密钥
   */
  private String secret;

}
