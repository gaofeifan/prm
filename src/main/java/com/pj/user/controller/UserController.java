package com.pj.user.controller;

import com.pj.user.pojo.UserLevel;
import com.pj.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 * 普通用户 业务操作
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private  UserService userservice;


    //  用 hu信用等级
    @RequestMapping("/level")
    public List<UserLevel> findUserLevelList(){
     return userservice.findUserLevelList();
    }

}
