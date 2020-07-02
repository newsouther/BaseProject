package com.souther.service;

import com.souther.vo.bo.CommonResult;
import com.souther.vo.db.QiNiuConfigDB;
import java.util.Map;

/**
 * @Auther: souther
 * @Date: 2020/7/2 14:11
 * @Description: 七牛云相关服务
 */
public interface QiNiuService {

  /**
   * @Description: 获取七牛配置信息
   * @Author: souther
   * @Date: 2020/7/2 14:36
   * @return: com.souther.vo.db.QiNiuConfigDB
   **/
  QiNiuConfigDB getConfig();

  /**
   * @Description: 获取上传Token
   * @Author: souther
   * @Date: 2020/7/2 14:27
   * @return: com.souther.vo.bo.CommonResult<java.lang.String>
   **/
  CommonResult<String> getUploadToken();

  /**
   * @param phone:      手机号
   * @param templateId: 模板id
   * @param param:      模板参数
   * @Description: 发送单条或者群发短信
   * @Author: souther
   * @Date: 2020/7/2 14:25
   * @return: com.souther.vo.bo.CommonResult<java.lang.String>
   **/
  CommonResult<String> sendMessags(String[] phone, String templateId, Map<String, String> param);

}
