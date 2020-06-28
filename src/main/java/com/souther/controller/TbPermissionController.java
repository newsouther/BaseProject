/**
 * @filename:TbPermissionController 2020年6月24日
 * @project baseproject  V1.0 Copyright(c) 2020 souther Co. Ltd. All right reserved.
 */
package com.souther.controller;

import com.souther.common.aid.AbstractController;
import com.souther.entity.TbPermission;
import com.souther.service.TbPermissionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 *
 * <p>说明： 权限API接口层</P>
 * @version: V1.0
 * @author: souther
 * @time 2020年6月24日
 *
 */
@Api(description = "权限", value = "权限")
@RestController
@RequestMapping("/tbPermission")
public class TbPermissionController extends AbstractController<TbPermissionService, TbPermission> {

  Logger logger = LoggerFactory.getLogger(this.getClass());

}