package com.souther.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.souther.common.constant.RedisKeyEnum;
import com.souther.common.constant.TbConfigEnum;
import com.souther.entity.TbConfig;
import com.souther.service.CommonService;
import com.souther.service.TbConfigService;
import com.souther.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private TbConfigService tbConfigService;

  @Override
  public String getConfigDB(TbConfigEnum dbConfigEnum) {

    String cacheKey = String.format(RedisKeyEnum.DB_CONFIG.getKey(), dbConfigEnum.getType());
    String cacheData = RedisUtil.getStr(cacheKey);
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
}
