/**
 * @filename:TbUserController 2020年6月22日
 * @project baseproject  V1.0
 * Copyright(c) 2020 souther Co. Ltd. 
 * All right reserved. 
 */
package com.souther.controller;

import com.souther.common.aid.AbstractController;
import com.souther.entity.TbUser;
import com.souther.service.TbUserService;
import com.souther.vo.bo.CommonResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户API接口层</P>
 * @version: V1.0
 * @author: souther
 * @time    2020年6月22日
 *
 */
@Api(description = "用户",value="用户" )
@RestController
@RequestMapping("/tbUser")
public class TbUserController extends AbstractController<TbUserService,TbUser> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	@GetMapping("/getById/{id}")
	@ApiOperation(value = "获取对象", notes = "作者：souther")
	@ApiImplicitParam(paramType = "path", name = "id", value = "对象id", required = true, dataType = "Long")
	@RequiresRoles("test")
	@RequiresPermissions("user:edit")
	public CommonResult<TbUser> getById(@PathVariable("id") Long id) {
		return super.getById(id);
	}
}