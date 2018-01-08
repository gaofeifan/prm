package com.pj.conf.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringUtils {
	
	/**
	 * 	设置不同浏览器请求时 中文乱码
	 *	@author 	GFF
	 *	@date		2017年2月9日下午1:59:17	
	 * 	@param request	
	 * 	@param fileName		文件名称
	 * 	@param response
	 * 	@return
	 * 	@throws UnsupportedEncodingException
	 */
	public static String toUtf8String(HttpServletRequest request, String fileName, HttpServletResponse response)
			throws UnsupportedEncodingException {
		  if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {  
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器  
	        }else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {  
	            fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器  
	        }else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {  
	            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");// 谷歌  
	        }
		return fileName;  
	}
	
	/**
	 * 	设置不同浏览器下载时的编码格式
	 *	@author 	GFF
	 *	@date		2017年2月9日下午1:51:45	
	 * 	@param 		request		
	 * 	@param 		fileName   文件名称
	 * 	@return		fileName   修改编码后的文件名称
	 * 	@throws UnsupportedEncodingException
	 */
	public static String downloadEncoding(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		String agent = request.getHeader("USER-AGENT"); 
		if (agent != null && -1 != agent.indexOf("MSIE")) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// Firefox
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		} else {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}
		return fileName;  
	}
}
