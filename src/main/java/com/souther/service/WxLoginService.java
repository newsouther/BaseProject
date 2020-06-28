package com.souther.service;

import com.souther.vo.bo.WxLoginAppletsBO;
import com.souther.vo.po.WxLoginAppletsPO;

/**
 * @Auther: souther
 * @Date: 2020/6/22 18:20
 * @Description:
 */
public interface WxLoginService {

  WxLoginAppletsBO appletsLogin(WxLoginAppletsPO loginAppletsPO);

}
