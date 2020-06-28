package com.souther.vo.po;

/**
 * @Auther: souther
 * @Date: 2020/6/22 18:21
 * @Description:
 */
public class WxLoginAppletsPO {

  private String appId;
  private String secret;
  private String jsCode;
  private String encryptedData;
  private String iv;

  private String sessionKey;

  /**
   * 小程序登陆
   *
   * @param appId
   * @param secret
   * @param jsCode
   * @param encryptedData
   * @param iv
   */
  public WxLoginAppletsPO(String appId, String secret, String jsCode, String encryptedData,
      String iv) {
    super();
    this.appId = appId;
    this.secret = secret;
    this.jsCode = jsCode;
    this.encryptedData = encryptedData;
    this.iv = iv;
  }

  /**
   * 获取手机号码
   *
   * @param encryptedData
   * @param iv
   * @param sessionKey
   */
  public WxLoginAppletsPO(String encryptedData, String iv, String sessionKey) {
    super();
    this.encryptedData = encryptedData;
    this.iv = iv;
    this.sessionKey = sessionKey;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  public WxLoginAppletsPO() {
    super();
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getJsCode() {
    return jsCode;
  }

  public void setJsCode(String jsCode) {
    this.jsCode = jsCode;
  }

  public String getEncryptedData() {
    return encryptedData;
  }

  public void setEncryptedData(String encryptedData) {
    this.encryptedData = encryptedData;
  }

  public String getIv() {
    return iv;
  }

  public void setIv(String iv) {
    this.iv = iv;
  }
}
