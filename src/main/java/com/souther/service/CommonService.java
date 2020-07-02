package com.souther.service;

import com.souther.common.constant.SmsTypeEnum;
import com.souther.common.constant.TbConfigEnum;
import com.souther.vo.bo.CommonResult;
import com.souther.vo.po.SendCodePO;

public interface CommonService {

  String getConfigDB(TbConfigEnum mdbConfigEnum);

  /**
   * @Description: 发送短信
   * @Author: souther
   * @Date: 2020/7/2 15:46
   * @param sendCodePO:
   * @return: com.souther.vo.bo.CommonResult<java.lang.String>
   **/
  CommonResult<String> sendSMS(SendCodePO sendCodePO);

  /**
   * @Description: 校验验证码
   * @Author: souther
   * @Date: 2020/7/2 16:17
   * @param smsTypeEnum:
   * @param phone:
   * @param code:
   * @return: com.souther.vo.bo.CommonResult<java.lang.String>
   **/
  CommonResult<String> checkSmsCode(SmsTypeEnum smsTypeEnum, String phone, String code);

}
