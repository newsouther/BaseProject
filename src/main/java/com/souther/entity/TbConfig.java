/**
 * @filename:TbConfig 2020年6月22日
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
 * <p>说明： 公共配置实体类</P>
 * @version: V1.0
 * @author: souther
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbConfig extends Model<TbConfig> {

	private static final long serialVersionUID = 1592811905317L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "常量配置")
	private Integer id;
    
	@ApiModelProperty(name = "type" , value = "类型")
	private Integer type;
    
	@ApiModelProperty(name = "remark" , value = "备注")
	private String remark;
    
	@ApiModelProperty(name = "record" , value = "记录值")
	private String record;
    
	@ApiModelProperty(name = "richText" , value = "富文本Id")
	private Integer richText;
    
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
