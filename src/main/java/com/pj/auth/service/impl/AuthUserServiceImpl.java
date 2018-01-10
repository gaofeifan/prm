package com.pj.auth.service.impl;

import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthUserService;
import com.pj.conf.properties.OaProperties;
import com.pj.conf.utils.HttpClienUtils;
import com.pj.conf.utils.TypeConversionUtils;
import com.pj.conf.utils.VerifyUtils;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private OaProperties oaProperties;
    @Override
    public User selectUserByEmail(String email) {
        User user = new User();
        if (StringUtils.isBlank(email)) {
            throw new RuntimeException("邮箱不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        JSONObject object = HttpClienUtils.doGet(oaProperties.getFindUserByemail(), map);
        Object obj = TypeConversionUtils.jsonToString(object, null);
        map = JSON.parseObject(obj.toString());
        String postname = VerifyUtils.objectToString(map.get("postname"));
        String companyname = VerifyUtils.objectToString(map.get("companyname"));
        String dempname = VerifyUtils.objectToString(map.get("dempname"));
        String username = VerifyUtils.objectToString(map.get("username"));
        String postId = VerifyUtils.objectToString(map.get("postid"));
        String id = VerifyUtils.objectToString(map.get("id"));
        if (StringUtils.isNoneBlank(postId)) {
            Integer postid = Integer.decode(VerifyUtils.objectToString(map.get("postid")));
            user.setPostid(postid);
        }
        user.setCompanyname(companyname);
        user.setDempname(dempname);
        user.setPostname(postname);
        user.setUsername(username);
        user.setEmail(email);
        user.setId(id);
        return user;
    }

    @Override
    public User selectUserByEmail(Integer id) {
        User user = new User();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        JSONObject object = HttpClienUtils.doGet(oaProperties.getFindUserById(), map);
        Object obj = TypeConversionUtils.jsonToString(object, null);
        map = JSON.parseObject(obj.toString());
        String postname = VerifyUtils.objectToString(map.get("postname"));
        String companyname = VerifyUtils.objectToString(map.get("companyname"));
        String companyEmail = VerifyUtils.objectToString(map.get("companyEmail"));
        String dempname = VerifyUtils.objectToString(map.get("dempname"));
        String username = VerifyUtils.objectToString(map.get("username"));
        String postId = VerifyUtils.objectToString(map.get("postid"));
        String phone = VerifyUtils.objectToString(map.get("phone"));
        if (StringUtils.isNoneBlank(postId)) {
            Integer postid = Integer.decode(VerifyUtils.objectToString(map.get("postid")));
            user.setPostid(postid);
        }
        user.setCompanyname(companyname);
        user.setDempname(dempname);
        user.setPostname(postname);
        user.setUsername(username);
        user.setEmail(companyEmail);
        user.setPhone(phone);
        return user;
    }

    public String getEmailsByPostId(String postId){
        List<User> users = this.selectUserByPostId(postId, false);
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            if (StringUtils.isNoneBlank(user.getEmail())) {
                sb.append(user.getUsername()+"("+user.getEmail() + "),");
            }
        }
        if (StringUtils.isNoneBlank(sb.toString())) {
            String string = sb.substring(0, sb.length() - 1);
            return string;
        }
        return "";
    }

    public List<User> selectUserByPostId(String postIds, boolean isDefault) {
        if (!isDefault && StringUtils.isBlank(postIds)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("postIds", postIds);
        JSONObject jsonObject = HttpClienUtils.doGet(oaProperties.getSelectUserByPostId(), map);
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
            Object postid = map.get("postid");
            if (postid != null) {
                user.setPostid(Integer.decode(postid.toString()));
            }
            if (map.get("companyEmail") != null) {
                user.setEmail(map.get("companyEmail").toString());
            }
            if (map.get("username") != null) {
                user.setUsername(map.get("username").toString());
            }
            if (map.get("postname") != null) {
                user.setPostname(map.get("postname").toString());
            }
            users.add(user);
        }
        return users;
    }

    @Override
    public User selectAdminUserById() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 8732);
        JSONObject object = HttpClienUtils.doGet(oaProperties.getSsoUrl_selectAdmin(), map);
        map = JSON.parseObject(object.toString());
        String username = VerifyUtils.objectToString(map.get("username"));
        String email = VerifyUtils.objectToString(map.get("email"));
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }


}
