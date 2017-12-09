package com.pj.conf.web.filter;

import com.alibaba.druid.support.http.WebStatFilter;
import com.pj.conf.base.ParameterRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName="commonFilter",urlPatterns="/*")
public class CommonFilter extends WebStatFilter {

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getServletPath().trim();
        if (url.equals("xxx")) {//不需要过滤的url，这里可以使用一个配置文件配置这些url，项目启动时读入内存一个map中，然后在这里进行判断
            //我定义的是urlFilterMap，然后在这里urlFilterMap.containsValue(url)进行判断
            chain.doFilter(req, response);
        } else {
            ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest) request);
            chain.doFilter(req, res);


        }

    }

}
