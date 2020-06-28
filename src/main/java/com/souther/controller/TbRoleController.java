/**
 * @filename:TbRoleController 2020年6月24日
 * @project baseproject  V1.0
 * Copyright(c) 2020 souther Co. Ltd. 
 * All right reserved. 
 */
package com.souther.controller;

import com.souther.common.aid.AbstractController;
import com.souther.entity.TbRole;
import com.souther.service.TbRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 角色API接口层</P>
 * @version: V1.0
 * @author: souther
 * @time    2020年6月24日
 *
 */
@Api(description = "角色",value="角色" )
@RestController
@RequestMapping("/tbRole")
public class TbRoleController extends AbstractController<TbRoleService,TbRole> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}