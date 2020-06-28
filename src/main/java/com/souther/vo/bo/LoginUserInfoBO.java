package com.souther.vo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description
 * @Author souther
 * @Date 2020/6/2 16:19
 * @Version 1.0
 **/
@Setter
@Getter
public class LoginUserInfoBO implements Serializable {

  private static final long serialVersionUID = -7399577783792057608L;
  @ApiModelProperty(name = "id" , value = "用户表")
  private Integer id;

  @ApiModelProperty(name = "name" , value = "用户名")
  private String name;

  @ApiModelProperty(name = "headImg" , value = "头像")
  private String headImg;

  @ApiModelProperty(name = "token" , value = "令牌")
  private String token;

  @ApiModelProperty(name = "openid" , value = "用户唯一标识")
  private String openid;

  @ApiModelProperty(name = "unionid" , value = "开放平台的唯一标识符")
  private String unionid;

  @ApiModelProperty(name = "record" , value = "拓展")
  private String record;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  @ApiModelProperty(name = "createTime" , value = "创建时间(公共字段)")
  private Date createTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  @ApiModelProperty(name = "updateTime" , value = "更新时间(公共字段)")
  private Date updateTime;

  @ApiModelProperty(name = "dataDeleted" , value = "数据删除(公共字段)")
  private Boolean dataDeleted;

}
