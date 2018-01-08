package com.pj.auth.service;

import java.util.List;

import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.pojo.AuthPostMenuVo;

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
  
  
  
  /**
   * 通过用用户id查询,用于前台展示相比较于selectByUserId，这个方法会返回auth_menu表的全部，还会反回一个checks（是否选中）
          * @author: x.gao  
          * @createTime: 2017年12月27日 上午11:00:33  
          * @history:  
          * @param userId
          * @return AuthMenu
   */
  List<AuthPostMenuVo> selectVOByUserId(String userId);
}

