package com.pj.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Api(value = "登录")
public class LoginController {

    @RequestMapping("/index")
    @ApiOperation(value = "登录跳转" ,httpMethod = "GET", response = Object.class)
    public String index(){
        return "/login";
    }
}
