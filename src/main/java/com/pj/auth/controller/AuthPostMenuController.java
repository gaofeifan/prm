package com.pj.auth.controller;

import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthPostMenuService;
import com.pj.auth.service.AuthUserService;
import com.pj.auth.service.UserMenuService;
import com.pj.conf.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    private AuthUserService userService;
    @Autowired
    private UserMenuService userMenuService;

    @ApiOperation(value = "根据岗位id查询菜单" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findMenuByPostId")
    @ResponseBody
    public Object findMenuByPostId(@ApiParam("岗位id") @RequestParam(name="postId") String postId){
        List<AuthPostMenuVo> menuVo = authPostMenuService.findMenuByPostId(postId);
        return this.success(menuVo);
    }

    @ApiOperation(value = "根据父菜单查询子按钮" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findButtonByMenuIds")
    @ResponseBody
    public Object findButtonByMenuIds(
            @ApiParam("岗位id") @RequestParam(name="postId") String postId,
            @ApiParam("菜单ids") @RequestParam(name="menuIds") Integer[] menuIds){
        List<AuthPostMenuVo> authPostMenus = this.authPostMenuService.findButtonByPostIdAndMenuIds(postId,menuIds);
        return this.success(authPostMenus);
    }
    @ApiOperation(value = "根据岗位获取菜单或按钮" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/findMenuOrButtonByPostId")
    @ResponseBody
    public Object findMenuOrButtonByPostId(
                                           @ApiParam(value = "email",required = true) @RequestParam(name="email",required = true) String email,
                                           @ApiParam(value = "菜单id",required = false) @RequestParam(name="menuId",required = false) Integer menuId,
                                           @ApiParam(value = "是否是菜单 1是（默认） 0 否",required = false) @RequestParam(name="isMenu",required = false) boolean isMenu
                                           ){
//        User user = this.getSessionUser();
        User user = this.userService.selectPersonByEmail(email);
        //新加一个逻辑，通过userId查询中间表user_menu来判断这个用户是不是有自定义菜单权限，如果有，就读取自定义权限，没有的话还走原来的Post权限; x.gao 20171227
        List<AuthMenu> selectByUserId = userMenuService.selectByUserId(user.getId().toString());
        List<AuthPostMenuVo> authPostMenus = null;
        //size==0还是原来的
        if(selectByUserId.size()==0){
          authPostMenus = this.authPostMenuService.findMenuOrButtonByPostId(user.getPositionId(),menuId,isMenu);
        }else{
          authPostMenus =  authPostMenuService.findMenuOrButtonByUserId(user.getId().toString(), menuId, isMenu,user.getPositionId());
        }
        return this.successJsonp(authPostMenus);
    }
    @ApiOperation(value = "设置岗位权限" ,httpMethod = "POST", response = Object.class)
    @RequestMapping(value = "/editPostAuthority")
    @ResponseBody
    public Object editPostAuthority(@ApiParam("岗位id") @RequestParam(name="postId") String postId,
                                           @ApiParam(value = "菜单ids") @RequestParam(name="menuIds") Integer[] menuIds,
                                           @ApiParam(value="用户id")@RequestParam(name="userId",required=false) String userId){
        //增加一个通过用户id更新的方法，当不传userId的时候还是按以前的来，传了userId要按userId更新此用户的菜单权限x.gao 20171227
        if(StringUtils.isBlank(userId)){
          authPostMenuService.editPostAuthority(postId,menuIds);
        }else{
          authPostMenuService.editPostAuthorityByuserId(userId, menuIds,postId);
        }
        return this.success(null);
    }

    @ApiOperation(value = "登录成功后调用接口" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/getLoginUserDetails")
    @ResponseBody
    public Object getLoginUserDetails(@ApiParam(value = "登录人邮箱") @RequestParam(name="email") String email, HttpServletRequest request){
        System.out.println(request.getSession().getId().toString());
        User user = userService.selectPersonByEmail(email);
        Object obj = getRequest().getSession().getAttribute(TAG);
        if(obj == null){
            getRequest().setAttribute(TAG,user);
        }
        return this.successJsonp("成功");

    }
    /**
     * 
            * @author: x.gao  
            * @createTime: 2017年12月27日 上午10:15:22  
            * @history:  
            * @param userId 用户id
            * @return Object
     */
  @ApiOperation(value = "根据用户查询菜单",httpMethod = "GET", response = Object.class)
  @RequestMapping(value = "/findMenuByUserId")
  @ResponseBody
  public Object findMenuByUserId(@ApiParam(value = "用户id") @RequestParam(name = "userId") String userId,
      @ApiParam("岗位id") @RequestParam(name = "postId",required=false) String postId) {
    List<AuthPostMenuVo> selectVOByUserId = userMenuService.selectVOByUserId(userId,postId);
    //List<AuthMenu> selectByUserId = userMenuService.selectByUserId(userId);
    return this.successJsonp(selectVOByUserId);
  }
    
}
//3FA618F018CA6F61C23D96E995456640
//4E7CEC5C457C19C881C298C49C7239BF
//923CFB6C4006DAC01CBBD2D38A7504C6
//269C9A4921C8FB86003A750BA876B3FF