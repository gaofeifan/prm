package com.pj;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author GFF
 * @date 2017年2月20日上午11:28:00
 * @version 1.0.0
 * @parameter
 * @since 1.8
 */
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class Application
{
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("1024MB"); // KB,MB     
		// 设置总上传数据总大小
		factory.setMaxRequestSize("10240MB");
		return factory.createMultipartConfig();
	}
}
