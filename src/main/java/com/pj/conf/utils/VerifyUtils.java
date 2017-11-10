package com.pj.conf.utils;

/**
 * 	校验工具类
 *	@author		GFF
 *	@date		2017年9月13日下午2:22:44
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
public class VerifyUtils {

	/**
	 * 	判断邮箱是否未null （不为空转换为string）
	 *	@author 	GFF
	 *	@date		2017年9月15日下午3:01:31	
	 * 	@param obj
	 * 	@return
	 */
	public static String objectToString(Object obj){
		if(obj != null){
			return obj.toString();
		}
		return "";
	}
	
	/**
	 * 	判断是否为文件类型 
	 *	@author 	GFF
	 *	@date		2017年9月15日下午3:03:03	
	 * 	@return
	 */
	public static int isFileType(Integer liuchengFileStatus){
		if(liuchengFileStatus == null){
			return 0;
		}
		if(liuchengFileStatus == 2){
			return 1;
		}
		return 0;
	}
}
