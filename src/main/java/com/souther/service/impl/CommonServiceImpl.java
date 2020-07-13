package com.souther.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.souther.common.constant.RedisKeyEnum;
import com.souther.common.constant.SmsTypeEnum;
import com.souther.common.constant.TbConfigEnum;
import com.souther.entity.TbConfig;
import com.souther.service.CommonService;
import com.souther.service.QiNiuService;
import com.souther.service.TbConfigService;
import com.souther.utils.CommonUtils;
import com.souther.utils.RedisUtil;
import com.souther.vo.bo.CommonResult;
import com.souther.vo.po.SendCodePO;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 16:29
 * @Version 1.0
 **/
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

  @Resource
  private TbConfigService tbConfigService;

  @Resource
  private QiNiuService qiNiuService;

  @Override
  public String getConfigDB(TbConfigEnum dbConfigEnum) {

    String cacheKey = String.format(RedisKeyEnum.DB_CONFIG.getKey(), dbConfigEnum.getType());
    String cacheData = RedisUtil.get(cacheKey);
    if (StringUtils.isNoneBlank(cacheData)) {
      TbConfig tbConfig = JSONObject.parseObject(cacheData, TbConfig.class);
      return tbConfig.getRecord();
    }
    QueryWrapper<TbConfig> queryWrapper = new QueryWrapper<TbConfig>();
    queryWrapper.eq("type", dbConfigEnum.getType());
    TbConfig dbConfig = tbConfigService.getOne(queryWrapper);
    Assert.notNull(dbConfig, "【DB配置】数据库缺少类型为" + dbConfigEnum.getType() + "的配置信息");
    RedisUtil.set(cacheKey, JSONObject.toJSONString(dbConfig));
    return dbConfig.getRecord();
  }

  @Override
  public CommonResult<String> sendSMS(SendCodePO sendCodePO) {
    // 判断验证码类型是否合法
    Boolean flag = false;
    for (SmsTypeEnum type : SmsTypeEnum.values()) {
      // 如果匹配到验证码类型，直接跳出循环
      if (type.getType().equals(sendCodePO.getType())) {
        flag = true;
        break;
      }
    }
    if (!flag) {
      return new CommonResult<>(400, "验证码类型无效");
    }

    // 验证码
    String code = CommonUtils.getRandomNumCode(6);
    //统一发送入口
    Map<String, String> param = new HashMap<String, String>(1);
    param.put(code, code);
    qiNiuService.sendMessags(new String[]{""}, "1278598098423136256", param);
    // 缓存验证码
    String cacheKey = String
        .format(RedisKeyEnum.SMS_CODE.getKey(), sendCodePO.getType(), sendCodePO.getPhone());
    RedisUtil.setEx(cacheKey, code, 5, TimeUnit.MINUTES);
    return new CommonResult<>();
  }

  @Override
  public CommonResult<String> checkSmsCode(SmsTypeEnum smsTypeEnum, String phone, String code) {
    // 获取超级验证码
    String superCode = getConfigDB(TbConfigEnum.SUPER_CODE);
    // 如果是超级验证码，直接通过
    if (superCode.equals(code)) {
      // 校验成功
      return new CommonResult<>();
    }

    // 如果不是超级验证码
    String cacheKey = String.format(RedisKeyEnum.SMS_CODE.getKey(), smsTypeEnum.getType(), phone);
    String cacheCode = RedisUtil.get(cacheKey);
    if (StringUtils.isBlank(cacheCode)) {
      return new CommonResult<>(600, "验证码已过期");
    }
    if (!cacheCode.equals(code)) {
      return new CommonResult<>(600, "验证码错误");
    }
    RedisUtil.delete(cacheKey);
    // 校验成功
    return new CommonResult<>();
  }
}
