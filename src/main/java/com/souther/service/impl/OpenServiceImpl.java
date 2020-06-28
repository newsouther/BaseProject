package com.souther.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.souther.common.constant.RedisKeyEnum;
import com.souther.common.constant.ResultTips;
import com.souther.common.constant.TbConfigEnum;
import com.souther.entity.TbUser;
import com.souther.service.CommonService;
import com.souther.service.OpenService;
import com.souther.service.TbUserService;
import com.souther.service.WxLoginService;
import com.souther.utils.JwtUtil;
import com.souther.utils.RedisUtil;
import com.souther.vo.bo.CommonResult;
import com.souther.vo.bo.LoginUserInfoBO;
import com.souther.vo.bo.WxLoginAppletsBO;
import com.souther.vo.db.WxSmallDB;
import com.souther.vo.po.AccountLoginPO;
import com.souther.vo.po.WxLoginAppletsPO;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 16:23
 * @Version 1.0
 **/
@Service
public class OpenServiceImpl implements OpenService {

  @Autowired
  private CommonService commonService;

  @Autowired
  private WxLoginService wxLoginService;

  @Autowired
  private TbUserService tbUserService;

  @Autowired
  private RedisUtil redisUtil;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public CommonResult<LoginUserInfoBO> login(AccountLoginPO accountLoginPO) {

    // 获取公众号登配置信息
    WxSmallDB dbWxSmallConfig = JSONObject
        .parseObject(commonService.getConfigDB(TbConfigEnum.WX_PUBLIC_LOGIN),
            WxSmallDB.class);

    WxLoginAppletsBO wxLoginAppletsBO = wxLoginService
        .appletsLogin(new WxLoginAppletsPO(dbWxSmallConfig.getAppId(), dbWxSmallConfig.getSecret(),
            accountLoginPO.getWxSmallCode(), accountLoginPO.getEncryptedData(),
            accountLoginPO.getIv()));
    if (wxLoginAppletsBO == null) {
      return new CommonResult<>(500, ResultTips.Account.AUTHORITY_FAILURE);
    }

    //查询用户是否存在
    QueryWrapper<TbUser> queryWrapper = new QueryWrapper<TbUser>();
    queryWrapper.eq("openid", wxLoginAppletsBO.getOpenid());
    TbUser dbUser = tbUserService.getOne(queryWrapper);
    Date now = new Date();
    //新增
    if (null == dbUser) {
      TbUser newBean = new TbUser();
      newBean.setName(wxLoginAppletsBO.getNickName());
      newBean.setHeadImg(wxLoginAppletsBO.getAvatarUrl());
      newBean.setOpenid(wxLoginAppletsBO.getOpenid());
      newBean.setUnionid(wxLoginAppletsBO.getUnionid());

      newBean.setCreateTime(now);
      newBean.setUpdateTime(now);
      tbUserService.save(newBean);
      dbUser = newBean;
    } else {
      //更新
      dbUser.setName(wxLoginAppletsBO.getNickName());
      dbUser.setHeadImg(wxLoginAppletsBO.getAvatarUrl());
      // 保存小程序openid
      if (StringUtils.isBlank(dbUser.getOpenid())) {
        dbUser.setOpenid(wxLoginAppletsBO.getOpenid());
      }
      // 保存unionid
      if (StringUtils.isBlank(dbUser.getUnionid()) && StringUtils
          .isNoneBlank(wxLoginAppletsBO.getUnionid())) {
        dbUser.setUnionid(wxLoginAppletsBO.getUnionid());
      }
      // 删除redis缓存
      redisUtil.delete(String.format(RedisKeyEnum.JWT_SESSION.getKey(), dbUser.getId()));
      dbUser.setUpdateTime(now);
      tbUserService.updateById(dbUser);
    }
    String tokenByWxAccount = jwtUtil.createTokenByWxAccount(dbUser);
    LoginUserInfoBO result = new LoginUserInfoBO();
    BeanUtils.copyProperties(dbUser, result);
    result.setToken(tokenByWxAccount);
    return new CommonResult<>(result);
  }

}
