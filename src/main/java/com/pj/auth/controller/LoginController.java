package com.pj.auth.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Api(value = "登录")
public class LoginController {

    @RequestMapping("/index")
    public String index(){
        return "/login";
    }
}
