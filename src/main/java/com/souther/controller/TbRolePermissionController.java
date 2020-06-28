/**
 * @filename:TbRolePermissionController 2020年6月28日
 * @project baseproject  V1.0
 * Copyright(c) 2020 souther Co. Ltd. 
 * All right reserved. 
 */
package com.souther.controller;

import com.souther.common.aid.AbstractController;
import com.souther.entity.TbRolePermission;
import com.souther.service.TbRolePermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 角色权限API接口层</P>
 * @version: V1.0
 * @author: souther
 * @time    2020年6月28日
 *
 */
@Api(description = "角色权限",value="角色权限" )
@RestController
@RequestMapping("/tbRolePermission")
public class TbRolePermissionController extends
		AbstractController<TbRolePermissionService,TbRolePermission> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}