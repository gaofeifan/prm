package com.pj.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.auth.mapper.AuthMenuMapper;
import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.service.UserMenuService;

/**  
        * @desc: prm  
        * @author: ykxue  
        * @createTime: 2017年12月27日 上午11:02:36  
        * @history:  
        * @version: v1.0    
        */

@Service(value="userMenuService")
public class UserMenuServiceImpl implements UserMenuService {
  
  @Autowired
  private AuthMenuMapper authMenuMapper;


  @Override
  public List<AuthMenu> selectByUserId(String userId) {
    List<AuthMenu> selectByUserId = authMenuMapper.selectByUserId(userId);
    return selectByUserId;  
        
  }

}

