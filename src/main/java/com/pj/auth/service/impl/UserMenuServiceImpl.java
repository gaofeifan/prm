package com.pj.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.auth.mapper.AuthMenuMapper;
import com.pj.auth.mapper.AuthPostMenuMapper;
import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
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
  @Autowired
  private AuthPostMenuMapper authPostMenuMapper;

  @Override
  public List<AuthMenu> selectByUserId(String userId) {
    List<AuthMenu> selectByUserId = authMenuMapper.selectByUserId(userId);
    return selectByUserId;  
        
  }

      
  @Override
  public List<AuthPostMenuVo> selectVOByUserId(String userId,Integer postId) {
    List<AuthPostMenuVo> apmv=null;
    List<AuthPostMenuVo> selectByUserId = authPostMenuMapper.selectByUserId(userId);
    if(selectByUserId.size()==0){
      apmv = authPostMenuMapper.findMenuByPostId(postId, null);
    }else{
      apmv = authPostMenuMapper.selectVOByUserId(userId);
    }
    return apmv;
        
  }

}

