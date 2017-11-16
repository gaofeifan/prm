package com.pj.auth.controller;

import com.pj.auth.service.AuthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping()
@Api(value = "登录")
public class LoginController {

  @Autowired
  private AuthUserService authUserService;
    @ApiOperation(value = "跳转login" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/index")
    @ResponseBody
    public String index( ){

        return this.authUserService.getEmailsByPostId("11114");
    }
}
