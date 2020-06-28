/**
 * @filename:TbRolePermission 2020年6月28日
 * @project baseproject  V1.0
 * Copyright(c) 2020 souther Co. Ltd. 
 * All right reserved. 
 */
package com.souther.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 角色权限实体类</P>
 * @version: V1.0
 * @author: souther
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbRolePermission extends Model<TbRolePermission> {

	private static final long serialVersionUID = 1593308081602L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "ID")
	private Integer id;
    
	@ApiModelProperty(name = "roleId" , value = "角色id")
	private Integer roleId;
    
	@ApiModelProperty(name = "permissionId" , value = "权限id")
	private Integer permissionId;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间(公共字段)")
	private Date createTime;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "更新时间(公共字段)")
	private Date updateTime;
    
	@ApiModelProperty(name = "dataDeleted" , value = "数据删除(公共字段)")
	private Integer dataDeleted;
    

	@Override
    protected Serializable pkVal() {
        return this.id;
    }
}
