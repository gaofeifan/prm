package com.pj.conf.base;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pj.auth.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;

import static javax.swing.text.html.CSS.getAttribute;

/**
 * 
 * 项目名称：documentation   
 * 类名称：BaseController   
 * 类描述：   
 * 创建人：GFF   
 * 创建时间：2017年9月5日 上午10:17:01   
 * 修改人：limr   
 * 修改时间：2017年9月5日 上午10:17:01 
 * 修改备注：   
 * @version    
 */
public class BaseController extends AdvanceControllerSupport{

	protected Logger logger = LoggerFactory.getLogger(BaseController.class); 
	public Map<String,Object> success(Object data){
		Map<String,Object> datas=new HashMap<String, Object>();
		datas.put("status",true);
		datas.put("msg","操作成功！");
		datas.put("code","200");
		datas.put("data",data);
		return datas;
	}
	public Map<String,Object> success(){
		Map<String,Object> datas=new HashMap<String, Object>();
		datas.put("status",true);
		datas.put("msg","操作成功！");
		datas.put("code","200");
		return datas;
	}

	public MappingJacksonValue successJsonp(Object obj){
		Map<String,Object> datas=new HashMap<String, Object>();
		datas.put("status",true);
		datas.put("msg","操作成功！");
		datas.put("code","200");
		datas.put("data",obj);
		MappingJacksonValue mjv = new MappingJacksonValue(datas);
		mjv.setJsonpFunction(getCallBack());
		return mjv;
	}
	
	public Map<String,Object> error(){
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("status",false);
		data.put("msg","操作失败！");
		data.put("code","400");
		return data;
	}
	
	public MappingJacksonValue errorToJsonp(){
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("status",false);
		data.put("msg","操作失败！");
		data.put("code","400");
		MappingJacksonValue mjv = new MappingJacksonValue(data);
		mjv.setJsonpFunction(getCallBack());
		return mjv;
	}

	public MappingJacksonValue errorToJsonp(Object data){
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("status",false);
		map.put("msg",data);
		map.put("code","400");
		MappingJacksonValue mjv = new MappingJacksonValue(map);
		mjv.setJsonpFunction(getCallBack());
		return mjv;
	}
	
	public Map<String,Object> error(Object data){
		Map<String,Object> datas=new HashMap<String, Object>();
		datas.put("status",false);
		datas.put("msg",data);
		datas.put("code","400");
		datas.put("data",data);
		return datas;
	}
	
	/**
	 * 	获取当前callback
	 *	@author 	GFF
	 *	@date		2017年6月6日下午1:46:11	
	 * 	@return
	 */
	public String getCallBack(){
		return buildPipelineContent().getRequest().getParameter("callback");
	}
	
	
	/**
	 * 	获取访问ip
	 *	@author 	GFF
	 *	@date		2017年9月13日下午2:41:58	
	 * 	@param request
	 * 	@return
	 */
	protected String getAddrIp() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("X-Real-IP");  
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            return ip;  
        }  
        ip = request.getHeader("X-Forwarded-For");  
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个IP值，第一个为真实IP。  
            int index = ip.indexOf(',');  
            if (index != -1) {  
                return ip.substring(0, index);  
            } else {  
                return ip;  
            }  
        } else {  
            return request.getRemoteAddr();  
        } 
	}

	protected  User getSessionUser(){
		HttpSession session = getRequest().getSession();
		Object o = session.getAttribute("user");
		if(o != null){
			return (User) o;
		}
		throw  new RuntimeException("用户未登录");

	}
	
	
}
