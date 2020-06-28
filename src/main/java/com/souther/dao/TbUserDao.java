/**
 * @filename:TbUserDao 2020年6月22日
 * @project baseproject  V1.0
 * Copyright(c) 2020 souther Co. Ltd. 
 * All right reserved. 
 */
package com.souther.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.souther.entity.TbUser;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户数据访问层</p>
 * @version: V1.0
 * @author: souther
 * 
 */
@Mapper
public interface TbUserDao extends BaseMapper<TbUser> {
	
}
