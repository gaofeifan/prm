package com.pj;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 *	@author		GFF
 *	@date		2017年9月23日上午10:55:27
 *	@version	1.0.0
 *	@parameter		@Override
 *  @since		1.8
 */
public class ServletInitializer    extends SpringBootServletInitializer
{
 	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
} 
}

