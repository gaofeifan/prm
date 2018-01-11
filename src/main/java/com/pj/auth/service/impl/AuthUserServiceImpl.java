package com.pj.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthUserService;
import com.pj.conf.properties.EamsProperties;
import com.pj.conf.utils.HttpClienUtils;
import com.pj.conf.utils.TypeConversionUtils;
import com.pj.conf.utils.VerifyUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AuthUserServiceImpl implements AuthUserService {

	@Autowired
	private EamsProperties easProperties;

	Map<String, Object> map = null;

	@Override
	public User selectPersonByEmail(String email) {
		map = new HashMap<>();
		map.put("email", email);
		List<User> list = this.selectPersonByQuery(map);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User selectPersonById(String id) {
		map = new HashMap<>();
		map.put("id", id);
		List<User> list = this.selectPersonByQuery(map);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/*
	 * public String selectEmailsByPostId(String postId) { List<User> users =
	 * this.selectUserByPostId(postId, false); StringBuilder sb = new
	 * StringBuilder(); for (User user : users) { if
	 * (StringUtils.isNoneBlank(user.getEmail())) { sb.append(user.getUsername()
	 * + "(" + user.getEmail() + "),"); } } if
	 * (StringUtils.isNoneBlank(sb.toString())) { String string =
	 * sb.substring(0, sb.length() - 1); return string; } return ""; }
	 */

	public List<User> selectPersonByQuery(Map<String, Object> map) {
		JSONObject jsonObject = HttpClienUtils.doGet(easProperties.getSelectUserByUsername(), map);
		Object object = TypeConversionUtils.jsonToString(jsonObject, null);
		JSONArray array = JSONArray.fromObject(object);
		List<User> users = new ArrayList<>();
		User user = null;
		for (int i = 0; i < array.length(); i++) {
			map = JSON.parseObject(array.get(i).toString());
			user = new User();
			Object id = map.get("id");
			if (id != null) {
				user.setId(id.toString());
			}
			String positionId = VerifyUtils.objectToString(map.get("positionId"));
			String email = VerifyUtils.objectToString(map.get("email"));
			String name = VerifyUtils.objectToString(map.get("name"));
			String positionName = VerifyUtils.objectToString(map.get("positionName"));
			String deptName = VerifyUtils.objectToString(map.get("deptName"));
			String companyName = VerifyUtils.objectToString(map.get("companyName"));
			String deptId = VerifyUtils.objectToString(map.get("deptId"));
			String companyId = VerifyUtils.objectToString(map.get("companyId"));
			String phone = VerifyUtils.objectToString(map.get("phone"));
			user.setCompanyId(companyId);
			user.setCompanyName(companyName);
			user.setDeptId(deptId);
			user.setDeptName(deptName);
			user.setPositionId(positionId);
			user.setPositionName(positionName);
			user.setPhone(phone);
			user.setEmail(email);
			user.setName(name);
			users.add(user);
		}
		return users;
	}

	@Override
	public User selectAdminUserById() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", 8732);
		JSONObject object = HttpClienUtils.doGet(easProperties.getSsoUrl_selectAdmin(), map);
		map = JSON.parseObject(object.toString());
		String username = VerifyUtils.objectToString(map.get("username"));
		String email = VerifyUtils.objectToString(map.get("email"));
		User user = new User();
		user.setName(username);
		user.setEmail(email);
		return user;
	}


}
