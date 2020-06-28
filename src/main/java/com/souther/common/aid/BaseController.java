package com.souther.common.aid;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.souther.common.constant.Constant;
import com.souther.common.constant.RedisKeyEnum;
import com.souther.common.exception.MyException;
import com.souther.entity.TbUser;
import com.souther.service.TbUserService;
import com.souther.utils.JwtUtil;
import com.souther.utils.RedisUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 15:24
 * @Version 1.0
 **/
public class BaseController {

  @Autowired
  protected HttpServletRequest request;

  @Autowired
  private TbUserService tbUserService;

  @Autowired
  private JwtUtil jwtUtil;

  protected TbUser getLoginConsumer() {
    String token = (String) SecurityUtils.getSubject().getPrincipal();
    String userId = jwtUtil.getUserIdByToken(token);
    TbUser dbUser = tbUserService.getOne(new QueryWrapper<TbUser>().eq("id", userId));
    if (dbUser == null) {
      throw new MyException(400, "当前用户未登录");
    }
    return dbUser;
  }

}
