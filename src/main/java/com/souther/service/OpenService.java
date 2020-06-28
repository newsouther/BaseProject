package com.souther.service;

import com.souther.vo.bo.CommonResult;
import com.souther.vo.bo.LoginUserInfoBO;
import com.souther.vo.po.AccountLoginPO;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 16:21
 * @Version 1.0
 **/
public interface OpenService {

  CommonResult<LoginUserInfoBO> login(AccountLoginPO accountLoginPO);
}
