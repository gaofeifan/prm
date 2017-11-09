package com.pj.auth.controller;

import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.auth.service.AuthPostMenuService;
import com.pj.conf.base.BaseController;
import com.sun.prism.paint.Stop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @ResponseBody
    public Object findMenuByPostId(@ApiParam(name = "岗位id") @RequestParam("postId") Integer postId){
        AuthPostMenuVo menuVo = authPostMenuService.findMenuByPostId(postId);
        return this.success(menuVo);
    }

    @ApiOperation(value = "根据父菜单查询子按钮" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findButtonByMenuIds")
    @ResponseBody
    public Object findButtonByMenuIds(@ApiParam(name = "菜单ids") @RequestParam("menuIds") String menuIds){
        List<AuthPostMenu> authPostMenus = this.authPostMenuService.selectAll();


        return this.success(authPostMenus);
    }
}
