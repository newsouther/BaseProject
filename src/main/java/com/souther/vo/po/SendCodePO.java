package com.souther.vo.po;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendCodePO {

	/**
	 * 手机号
	 */
	@Pattern(regexp = "^1\\d{10}$", message = "手机格式不正确")
	@NotNull(message = "手机号码不能为空")
	private String phone;

	/**
	 * 验证码类型：PSmsTypeEnum
	 */
	@NotNull(message = "类型不能为空")
	private String type;

}
