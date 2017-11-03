package com.pj.conf.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 *	@author		GFF
 *	@date		2017年2月24日下午5:25:06
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
public class HttpClienUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClienUtils.class);
	public static JSONObject doPost(String url,Object obj){
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	    HttpPost post = new HttpPost(url);
	    JSONObject response = null;
	    CloseableHttpResponse res = null;
	    try {
	    	// 构建请求配置信息
	        @SuppressWarnings("deprecation")
			RequestConfig config = RequestConfig.custom().setConnectTimeout(10000) // 创建连接的最长时间
	                .setConnectionRequestTimeout(10000) // 从连接池中获取到连接的最长时间
	                .setSocketTimeout(10 * 10000) // 数据传输的最长时间
	                .setStaleConnectionCheckEnabled(true) // 提交请求前测试连接是否可用
	                .build();
	        // 设置请求配置信息
	        post.setConfig(config);
	        // 模拟浏览器访问，加入浏览器的信息到header
	        post.setHeader(
	                "User-Agent",
	                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
	        Map<String, Object> map = TypeConversionUtils.objectToMap(obj);
	        List<NameValuePair> params = HttpMthodsUtils.getUrlParams(map);
	        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
	        post.setEntity(formEntity);
	        res = httpclient.execute(post);
	    	if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	            String result = EntityUtils.toString(res.getEntity());// 返回json格式：
	            response = JSONObject.fromObject(result);
	        }
	    } catch (Exception e) {
	    	logger.error(e.getMessage());
	    } finally {
			// 关闭连接 ,释放资源
			try {
				if(httpclient != null){
					httpclient.close();
				}
				if(res != null){
					res.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return response;
	}

	public static JSONObject doGet(String url, Map<String, Object> map) {
		List<NameValuePair> params = HttpMthodsUtils.getUrlParams(map);
		CloseableHttpResponse res = null;
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url + (params != null ? "?" + URLEncodedUtils.format(params, "UTF-8"): ""));
		try {
			res = httpclient.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(res.getEntity());
				return JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);

		} finally {
			// 关闭连接 ,释放资源
			try {
				if(httpclient != null){
					httpclient.close();
				}
				if(res != null){
					res.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
