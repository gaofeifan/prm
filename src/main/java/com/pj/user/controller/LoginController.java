package com.pj.user.controller;

import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthUserService;
import com.pj.conf.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by SenevBoy on 2017/11/13.
 */
@RestController
@RequestMapping("/login")
@Api(value ="首页 -- "  )
public class LoginController  extends BaseController {


    @Autowired
    AuthUserService authUserServic;
    //  用 hu信用等级
    @RequestMapping("/toLogin")
    @ResponseBody
    @ApiOperation(value = " 用户登录完成后调用接口 保存用户登录信息" , httpMethod = "GET" , response = Object.class)
    private Map<String, Object> LoginController(@ApiParam("email") String email, HttpServletRequest request){
        try {
                User user = authUserServic.selectUserByEmail(email);
                if(user!=null){
                    request.getSession().setAttribute("user_object",user);
                    return  this.success();
                }
            }catch (Exception e){
                System.out.println(e);
                return  this.error();
            }
        return success();
    }
}