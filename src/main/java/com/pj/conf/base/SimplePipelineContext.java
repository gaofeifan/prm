package com.pj.conf.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *	@author		GFF
 *	@date		2017年6月6日上午11:53:29
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
public class SimplePipelineContext extends PipelineContext {

	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	

}
