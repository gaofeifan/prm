package com.pj.conf.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 字符串类型转换器
 * 
 * @author GFF
 * @date 2017年9月13日下午3:14:49
 * @version 1.0.0
 * @parameter
 * @since 1.8
 */
@Configuration
public class StringConverter implements Converter<String, Object> {
	public String convert(String source) {
		if (source != null) {
			source = source.trim(); // 去除空格，进行判断
			if (!"".equals(source)) {
				return source;
			}
		}
		return null;
	}

}
