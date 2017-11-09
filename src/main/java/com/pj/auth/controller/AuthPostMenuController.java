package com.pj.auth.controller;

import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.service.AuthPostMenuService;
import com.pj.conf.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/11/9.
 */
@Controller
@Api(value = "权限")
@RequestMapping(value = "/auth/menu")
public class AuthPostMenuController extends BaseController{

    @Autowired
    private AuthPostMenuService authPostMenuService;

    @ApiOperation(value = "根据岗位id查询菜单" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findMenuByPostId")
    public Object findMenuByPostId(@ApiParam(name = "postid")Integer postid){
//        AuthPostMenu authPostMenu = authPostMenuService.findMenuByPostIduByPostId(postid);
    return null;
    }
}
