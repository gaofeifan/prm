package com.pj.auth.service;

import java.util.List;

import com.pj.auth.pojo.AuthMenu;

/**  
* @desc: prm  通过用户查询菜单
* @author: x.gao  
* @createTime: 2017年12月27日 上午10:57:16  
* @history:  
* @version: v1.0    
*/

public interface UserMenuService{

  /**
   * 通过用用户id查询
          * @author: x.gao  
          * @createTime: 2017年12月27日 上午11:00:33  
          * @history:  
          * @param userId
          * @return AuthMenu
   */
  List<AuthMenu> selectByUserId(String userId);
}

