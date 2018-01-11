package com.pj.conf.web.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *	@author		GFF
 *	@date		2017年2月21日上午10:55:19
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
@Configuration
@EnableSwagger2
@Api(value = "swagger", description = "接口文档")
public class SwaggerConfig {
	  @Bean
	    public Docket createRestApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.pj"))
	                .paths(PathSelectors.any())
	                .build();
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("项目接口文档")
	                .contact("GFF")
	                .version("1.0")
	                .build();
	    }

}
