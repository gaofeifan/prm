package com.pj.conf.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author GFF
 * @date 2017年6月6日上午11:24:17
 * @version 1.0.0
 * @parameter
 * @since 1.8
 */
public abstract class PipelineContext {

/*	public abstract Object getContextObject(String paramString);

	public abstract String getContextString(String paramString);

	public abstract String getContextString(String paramString1, String paramString2);

	public abstract int getContextInt(String paramString);

	public abstract int getContextInt(String paramString, int paramInt);

	public abstract boolean getContextBoolean(String paramString);

	public abstract boolean getContextBoolean(String paramString, boolean paramBoolean);

	public abstract void setContextObject(String paramString, Object paramObject);
*/
	public abstract HttpServletRequest getRequest();

	public abstract HttpServletResponse getResponse();

	/*public abstract String getCurrentNode();

	public abstract void setCurrentNode(String paramString);

	public abstract void remove(String paramString);*/
}
