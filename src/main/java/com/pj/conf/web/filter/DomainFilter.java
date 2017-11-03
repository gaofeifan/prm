package com.pj.conf.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.support.http.WebStatFilter;

@WebFilter(filterName="domainFilter",urlPatterns="/*")
public class DomainFilter  extends WebStatFilter{

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest rep = (HttpServletRequest) request;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		res.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
		
//		res.setContentType("textml;charset=UTF-8");
//		res.setHeader("Access-Control-Allow-Origin", res.getHeader("Origin"));
//		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//		res.setHeader("Access-Control-Max-Age", "0");
//		res.setHeader("Access-Control-Allow-Headers","Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
//		res.setHeader("XDomainRequestAllowed", "1");
	    chain.doFilter(rep, res);
	    
	    
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
