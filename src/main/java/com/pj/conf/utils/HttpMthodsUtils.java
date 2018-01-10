package com.pj.conf.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *	@author		GFF
 *	@date		2017年2月28日下午3:40:30
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
public class HttpMthodsUtils {

	@SuppressWarnings("unused")
	private  List<NameValuePair> list;
	public static void saveApprove(){
		
	}

	public List<NameValuePair> getList() {
		return list =  new ArrayList<NameValuePair>();
	}

	
	public static List<NameValuePair> getUrlParams(Map<String, Object> map){
		Set<Entry<String,Object>> entrySet = map.entrySet();
		List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
		for (Entry<String, Object> entry : entrySet) {
			if(entry.getValue() != null){
				if(!(entry.getValue() instanceof Date)){
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
			}
		}
		return pairs;
	}

}
