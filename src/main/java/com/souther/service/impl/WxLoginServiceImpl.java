package com.souther.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.souther.common.exception.MyException;
import com.souther.service.WxLoginService;
import com.souther.vo.bo.WxLoginAppletsBO;
import com.souther.vo.po.WxLoginAppletsPO;
import java.security.AlgorithmParameters;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.Jscode2sessionResult;

/**
 * @Auther: souther
 * @Date: 2020/6/22 18:22
 * @Description:
 */
@Slf4j
@Service
public class WxLoginServiceImpl implements WxLoginService {

  // 小程序登录
  private static final String URL_APPLET = "https://api.weixin.qq.com/sns/jscode2session";

  @Override
  public WxLoginAppletsBO appletsLogin(WxLoginAppletsPO loginAppletsPO) {
    Jscode2sessionResult jscode2sessionResult = SnsAPI
        .jscode2session(loginAppletsPO.getAppId(), loginAppletsPO.getSecret(),
            loginAppletsPO.getJsCode());
    try {
      if (jscode2sessionResult.isSuccess()) {
        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(loginAppletsPO.getEncryptedData());
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(jscode2sessionResult.getSession_key());
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(loginAppletsPO.getIv());

        Security.addProvider(new BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
          WxLoginAppletsBO loginResult = JSONObject.parseObject(new String(resultByte, "UTF-8"),
              WxLoginAppletsBO.class);
          loginResult.setSession_key(jscode2sessionResult.getSession_key());
          log.info("【小程序登录成功】结果：" + JSONObject.toJSONString(jscode2sessionResult));
          return loginResult;
        }
      }
    } catch (Exception e) {
      log.error("【WxLoginServiceImpl-71】小程序登录异常", e);
      throw new MyException(500, "小程序登录异常");
    }
    //这个就不要放在里面啦，不然会双重报错
    log.error("【WxLoginServiceImpl-68】小程序登录失败:" + JSONObject.toJSONString(jscode2sessionResult));
    throw new MyException(400,
        "小程序登录失败,errorCode:" + jscode2sessionResult.getErrcode() + "   errorMsg:"
            + jscode2sessionResult
            .getErrmsg());
  }
}
