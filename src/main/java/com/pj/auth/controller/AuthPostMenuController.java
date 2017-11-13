package com.pj.auth.controller;

import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthPostMenuService;
import com.pj.auth.service.UserService;
import com.pj.conf.base.BaseController;
import com.sun.prism.paint.Stop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */
@Controller
@Api(value = "权限")
@RequestMapping(value = "/auth/menu")
public class AuthPostMenuController extends BaseController{

    public static final String TAG = "user";
    @Autowired
    private AuthPostMenuService authPostMenuService;
    @Autowired
    private UserService userService;
    @ApiOperation(value = "根据岗位id查询菜单" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findMenuByPostId")
    @ResponseBody
    public Object findMenuByPostId(@ApiParam("岗位id") @RequestParam(name="postId") Integer postId){
        List<AuthPostMenuVo> menuVo = authPostMenuService.findMenuByPostId(postId);
        return this.success(menuVo);
    }

    @ApiOperation(value = "根据父菜单查询子按钮" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findButtonByMenuIds")
    @ResponseBody
    public Object findButtonByMenuIds(
            @ApiParam("岗位id") @RequestParam(name="postId") Integer postId,
            @ApiParam("菜单ids") @RequestParam(name="menuIds") Integer[] menuIds){
        List<AuthPostMenuVo> authPostMenus = this.authPostMenuService.findButtonByPostIdAndMenuIds(postId,menuIds);
        return this.success(authPostMenus);
    }
    @ApiOperation(value = "根据岗位获取菜单或按钮" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findMenuOrButtonByPostId")
    @ResponseBody
    public Object findMenuOrButtonByPostId(@ApiParam("岗位id") @RequestParam(name="postId") Integer postId,
                                           @ApiParam(value = "菜单id",required = false) @RequestParam(name="menuId",required = false) Integer menuId,
                                           @ApiParam(value = "是否是菜单 1是（默认） 0 否",required = false) @RequestParam(name="isMenu",required = false) boolean isMenu
                                           ){
        List<AuthPostMenuVo> authPostMenus = this.authPostMenuService.findMenuOrButtonByPostId(postId,menuId,isMenu);
        return this.success(authPostMenus);
    }
    @ApiOperation(value = "设置岗位权限" ,httpMethod = "POST", response = Object.class)
    @RequestMapping(value = "/editPostAuthority")
    @ResponseBody
    public Object editPostAuthority(@ApiParam("岗位id") @RequestParam(name="postId") Integer postId,
                                           @ApiParam(value = "菜单ids") @RequestParam(name="menuIds") Integer[] menuIds){
        authPostMenuService.editPostAuthority(postId,menuIds);
        return this.success(null);
    }

    @ApiOperation(value = "登录成功后调用接口" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/getLoginUserDetails")
    @ResponseBody
    public void getLoginUserDetails( @ApiParam(value = "登录人邮箱") @RequestParam(name="email") String email){
        User user = userService.selectUserByEmail(email);
        HttpSession session = getRequest().getSession();
        Object obj = session.getAttribute(email);
        if(obj == null){
            session.setAttribute(TAG,user);
            session.setMaxInactiveInterval(60*60*24);
        }
    }
}
