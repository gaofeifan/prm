package com.pj.auth.service.impl;

import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthUserService;
import com.pj.conf.properties.OaProperties;
import com.pj.conf.utils.HttpClienUtils;
import com.pj.conf.utils.TypeConversionUtils;
import com.pj.conf.utils.VerifyUtils;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        if (StringUtils.isNoneBlank(postId)) {
            Integer postid = Integer.decode(VerifyUtils.objectToString(map.get("postid")));
            user.setPostid(postid);
        }
        user.setCompanyname(companyname);
        user.setDempname(dempname);
        user.setPostname(postname);
        user.setUsername(username);
        user.setEmail(email);
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
}
