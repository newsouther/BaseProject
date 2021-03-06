/**
 * @filename:UserController 2020年5月19日
 * @project generalproject  V1.0 Copyright(c) 2020 souther Co. Ltd. All right reserved.
 */
package com.souther.conf;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * Swagger核心配置文件 ========================
 *
 * @author souther
 * @Date 2020年5月19日 ========================
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

	@Value("${server.port}")
	private static String port;

	public static String CONTROLLER_URL = "com.souther.controller";    //Swagger扫描的接口路径
	public static String SWAGGER_TITLE = "API文档-souther";          //Swagger接口文档标题
	public static String SWAGGER_DESCRIPTION = "API文档";          //Swagger接口文档描述
	public static String SWAGGER_VERSION = "1.0";                         //Swagger接口文档版本
	public final static String SWAGGER_URL = "http://127.0.0.1:" + port;    //Swagger项目服务的URL

	//验证的页面http://127.0.0.1:8080/swagger-ui.html
	@Bean
	public Docket createRestApi() {
		//添加全局响应状态码
		List<ResponseMessage> responseMessageList = new ArrayList<>();
//    Arrays.stream(ErrorEnums.values()).forEach(errorEnums -> {
//
//    });
		responseMessageList.add(
				new ResponseMessageBuilder().code(200).message("请求成功")
						.responseModel(
								new ModelRef("请求成功")).build()
		);
		responseMessageList.add(
				new ResponseMessageBuilder().code(400).message("前端的问题")
						.responseModel(
								new ModelRef("前端的问题")).build()
		);
		responseMessageList.add(
				new ResponseMessageBuilder().code(500).message("后端的问题")
						.responseModel(
								new ModelRef("后端的问题")).build()
		);
		responseMessageList.add(
				new ResponseMessageBuilder().code(600).message("用户的问题")
						.responseModel(
								new ModelRef("用户的问题")).build()
		);
		return new Docket(DocumentationType.SWAGGER_2)
				.globalResponseMessage(RequestMethod.POST, responseMessageList)
				.globalResponseMessage(RequestMethod.GET, responseMessageList)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(CONTROLLER_URL))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(SWAGGER_TITLE)
				.description(SWAGGER_DESCRIPTION)
				.termsOfServiceUrl(SWAGGER_URL)
				.version(SWAGGER_VERSION)
				.build();
	}
}
