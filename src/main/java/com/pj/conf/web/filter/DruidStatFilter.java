package com.pj.conf.web.filter;

import javax.servlet.annotation.WebFilter;

import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 *	@author		GFF
 *	@date		2017年2月21日上午10:24:34
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
initParams={
    @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
})
public class DruidStatFilter extends WebStatFilter {

}
