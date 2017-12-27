package com.pj.auth.mapper;

import java.util.List;

import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.pojo.UserMenu;
import com.pj.conf.base.BaseMapper;

/**  
* @desc:   
* @author: x.gao  
* @createTime: 2017年12月27日 上午11:07:27  
* @history:  
* @version: v1.0    
*/

public interface UserMenuMapper  extends BaseMapper<UserMenu>{

  //通过用户id查询菜单
  List<AuthMenu> selectByUserId(String userId);
}

