package com.pj.conf.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 	正则工具类
 *	@author		GFF
 *	@date		2017年9月15日下午2:59:27
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
public class RegExpUtils {

	public static boolean verifyEmail(String email){
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}
	
	public static boolean verify(String str){
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
