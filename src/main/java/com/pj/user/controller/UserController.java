package com.pj.user.controller;

import com.google.gson.JsonObject;
import com.pj.Aspect.AspectServer;
import com.pj.conf.base.BaseController;
import com.pj.user.pojo.Hierarchy;
import com.pj.user.pojo.RequestParam;
import com.pj.user.pojo.UserLevel;
import com.pj.user.service.EmailService;
import com.pj.user.service.LogService;
import com.pj.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by SenevBoy on 2017/11/8.
 * 普通用户 业务操作
 */
@RestController
@RequestMapping("/user")
@Api(value ="普通用户模块"  )
public class UserController extends BaseController{

    @Autowired
private  UserService userservice;

    @Autowired
    private EmailService emailService;


    //  用 hu信用等级
    @RequestMapping("/level")
    @ResponseBody
    @ApiOperation(value = "用户信用权限--列表查询接口" , httpMethod = "GET" , response = Object.class)
    public List<UserLevel> findUserLevelList(){
        return userservice.findUserLevelList();
    }

    //  用 hu信用等级 修改
    @RequestMapping("/levelUpdate")
    @ResponseBody
    @ApiOperation(value = "用户信用权限--修改接口" , httpMethod = "POST" , response = Object.class)
    public Map<String, Object> updateLevel(@RequestBody RequestParam requestParam, HttpServletRequest request ){
        try {
            for (UserLevel usel : requestParam.getUserLevelList()){
                userservice.updateLevelById(usel,request);
            }
            return  this.success();
        } catch (Exception e) {
            return  this.error();
        }
    }

    @RequestMapping("/levelUpdateBack")
    @ResponseBody
    @ApiOperation(value = "用户信用权限--修改接口-参数回显" , httpMethod = "POST" , response = Object.class)
    public void levelUpdateBack(@ModelAttribute("userlevelList")UserLevel userlevelList ){

    }


    //  层级位数管理
    @RequestMapping("/hierarchy")
    @ResponseBody
    @ApiOperation(value = "层级位数管理  --查询" , httpMethod = "GET" , response = Object.class)
    public  Map<String, Object> findHierarchyList(){
        try {
            userservice.findHierarchyList();
            return this.success();
        }catch (Exception e){
            System.out.println(e);
            return this.error();
        }
    }
    //  层级位数管理
    @RequestMapping("/hierarchyUpdate")
    @ResponseBody
    @ApiOperation(value = "层级位数管理  --修改" , httpMethod = "POST" , response = Object.class)
    public  Map<String, Object> updateHierarchyList(@RequestBody RequestParam requestParam, HttpServletRequest request){
        try {
            userservice.updateHierarchyList(requestParam.getHierarchyList(),request);
            return this.success();
        }catch (Exception e){
            System.out.println(e);
            return this.error();
        }
    }
}
