package com.souther.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import com.souther.common.constant.TbConfigEnum;
import com.souther.service.CommonService;
import com.souther.service.QiNiuService;
import com.souther.vo.bo.CommonResult;
import com.souther.vo.db.QiNiuConfigDB;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Auther: souther
 * @Date: 2020/7/2 14:28
 * @Description:
 */
@Slf4j
@Service
public class QiNiuServiceImpl implements QiNiuService {

  @Resource
  private CommonService commonService;

  @Override
  public QiNiuConfigDB getConfig() {
    String configDB = commonService.getConfigDB(TbConfigEnum.QI_NIU_CONFIG);
    return JSONObject.parseObject(configDB, QiNiuConfigDB.class);
  }

  @Override
  public CommonResult<String> getUploadToken() {
    QiNiuConfigDB qiNiuConfig = getConfig();
    Auth auth = Auth.create(qiNiuConfig.getAk(), qiNiuConfig.getSk());
    return new CommonResult<>(auth.uploadToken(qiNiuConfig.getBucket()));
  }

  @Override
  public CommonResult<String> sendMessags(String[] phone, String templateId,
      Map<String, String> param) {
    QiNiuConfigDB qiNiuConfig = getConfig();
    Auth auth = Auth.create(qiNiuConfig.getAk(), qiNiuConfig.getSk());
    SmsManager smsManager = new SmsManager(auth);
    try {
      Response response = smsManager.sendMessage(templateId, phone, param);
    } catch (QiniuException e) {
      log.error("【QiNiuServiceImpl-sendMessags】短信验证码发送异常", e);
    }

    return new CommonResult<>();
  }
}
