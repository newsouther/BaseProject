/**
 * @filename:TbConfigController 2020年6月22日
 * @project baseproject  V1.0
 * Copyright(c) 2020 souther Co. Ltd. 
 * All right reserved. 
 */
package com.souther.controller;

import com.souther.common.aid.AbstractController;
import com.souther.entity.TbConfig;
import com.souther.service.TbConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 公共配置API接口层</P>
 * @version: V1.0
 * @author: souther
 * @time    2020年6月22日
 *
 */
@Api(description = "公共配置",value="公共配置" )
@RestController
@RequestMapping("api/tbConfig")
public class TbConfigController extends AbstractController<TbConfigService,TbConfig> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}