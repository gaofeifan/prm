package com.pj.user.controller;

import com.pj.user.pojo.UserLevel;
import com.pj.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 * 普通用户 业务操作
 */
@RestController
@RequestMapping("/user")
@Api(value ="普通用户模块"  )
public class UserController {

    @Autowired
   private  UserService userservice;


    //  用 hu信用等级
    @RequestMapping("/level")
    @ResponseBody
    @ApiOperation(value = "用户信用权限列表查询接口" , httpMethod = "GET" , response = Object.class)
    public List<UserLevel> findUserLevelList(){
     return userservice.findUserLevelList();
    }

}
