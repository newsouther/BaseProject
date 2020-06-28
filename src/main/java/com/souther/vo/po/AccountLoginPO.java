package com.souther.vo.po;

import javax.validation.constraints.NotBlank;

public class AccountLoginPO {

  /**
   * 微信小程序code
   */
  @NotBlank(message = "code不能为空")
  private String wxSmallCode;

  /**
   * 微信小程序--初始变量iv
   */
  @NotBlank(message = "初始变量iv不能为空")
  private String iv;

  /**
   * 微信小程序--目标密文encryptedData
   */
  @NotBlank(message = "目标密文不能为空")
  private String encryptedData;

  public String getWxSmallCode() {
    return wxSmallCode;
  }

  public void setWxSmallCode(String wxSmallCode) {
    this.wxSmallCode = wxSmallCode;
  }

  public String getIv() {
    return iv;
  }

  public void setIv(String iv) {
    this.iv = iv;
  }

  public String getEncryptedData() {
    return encryptedData;
  }

  public void setEncryptedData(String encryptedData) {
    this.encryptedData = encryptedData;
  }

}
