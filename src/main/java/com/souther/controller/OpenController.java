package com.souther.controller;


import com.souther.common.aid.BaseController;
import com.souther.service.OpenService;
import com.souther.vo.bo.CommonResult;
import com.souther.vo.bo.LoginUserInfoBO;
import com.souther.vo.po.AccountLoginPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 15:17
 * @Version 1.0
 **/
@RestController
@RequestMapping("api/open")
public class OpenController extends BaseController {

  @Autowired
  private OpenService openService;

  /**
   * @Description: 授权
   * @Author: souther
   * @Date: 2020/6/2 18:21
   * @param accountLoginPO
   * @return: com.souther.vo.bo.CommonResult<com.souther.vo.bo.LoginUserInfoBO>
   **/
  @PostMapping("login")
  public CommonResult<LoginUserInfoBO> login(@Validated @RequestBody AccountLoginPO accountLoginPO) {
    return openService.login(accountLoginPO);
  }

}
