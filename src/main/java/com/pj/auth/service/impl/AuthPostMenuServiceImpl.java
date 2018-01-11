package com.pj.auth.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pj.auth.mapper.AuthPostMenuMapper;
import com.pj.auth.mapper.UserMenuMapper;
import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.auth.pojo.UserMenu;
import com.pj.auth.service.AuthMenuService;
import com.pj.auth.service.AuthPostMenuService;
import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
@Transactional
public class AuthPostMenuServiceImpl extends AbstractBaseServiceImpl<AuthPostMenu,Integer> implements AuthPostMenuService {
    @Autowired
    private AuthPostMenuMapper authPostMenuMapper;
    @Autowired
    private AuthMenuService authMenuService;
    @Autowired
    private UserMenuMapper userMenuMapper;
    @Override
    public BaseMapper<AuthPostMenu> getMapper() {
        return authPostMenuMapper;
    }

    @Override
    public List<AuthPostMenuVo> findMenuByPostId(String postId) {
        AuthPostMenu record = new AuthPostMenu();
        record.setPostId(postId);
        List<AuthPostMenu> menus = this.authPostMenuMapper.select(record);
        List<AuthPostMenuVo> menuVos = this.authPostMenuMapper.selectMenuVos(postId);
        Integer [] menuIds = this.authMenuService.selectMenuIds(postId);
        List<AuthPostMenuVo> menu = authPostMenuMapper.findMenuByPostId(postId, menuIds);
        if(menus.size() != 0){
            menuVos.addAll(menu);
            return menuVos;
        }
        editDefaultAuth(postId);
        menu.clear();
        menu = authPostMenuMapper.findMenuByPostId(postId, menuIds);
        menuVos.addAll(menu);
        return menuVos;

    }
    @Override
    public List<AuthPostMenuVo> findButtonByPostIdAndMenuIds(String postId, Integer[] menuIds) {
        if(menuIds == null || menuIds.length == 0){
            return null;
        }
        return authPostMenuMapper.findMenuByPostId(postId,menuIds);
    }
    @Override

    public List<AuthPostMenuVo> findMenuOrButtonByPostId(String postId, Integer menuId, boolean isMenu){
        AuthPostMenu am = new AuthPostMenu();
        am.setPostId(postId);
        List<AuthPostMenu> list = authPostMenuMapper.select(am);
        if(list.size() == 0){
            editDefaultAuth(postId);
        }
        return authPostMenuMapper.findMenuOrButtonByPostId(postId, menuId, isMenu);
    }

    @Override
    public void editPostAuthority(String postId, Integer[] menuIds) {

        this.authPostMenuMapper.delete(new AuthPostMenu(postId));
        //加一个新的逻辑，当用户没有勾选到人的时候，按部门修改时要把之前按人修改的记录给删除也就是user_menu表中跟此postId相关的数删掉.x.gao 20171229
        UserMenu um = new UserMenu();
        um.setPostId(postId);
        userMenuMapper.delete(um);
        for(Integer id : menuIds){
            this.authPostMenuMapper.insert(new AuthPostMenu(id,postId));
        }

    }
    @Override
    public boolean findOperatingAuthorizationByPostIdAndByButton(String postId , Integer button) {
        List<AuthPostMenu> list = this.authPostMenuMapper.select(new AuthPostMenu(button, postId));
        if(list.size() > 0){
            return true;
        }
        return false;
    }
    @Override
    public void editDefaultAuth(String postId){
        List<AuthMenu> menus = this.authMenuService.selectDefaultMenu();
        AuthPostMenu apm = null;
        for (AuthMenu am:menus) {
            apm = new AuthPostMenu();
            apm.setMenuId(am.getId());
            apm.setPostId(postId);
            this.authPostMenuMapper.insert(apm);
        }
    }

    @Override
    public void editPostAuthorityByuserId(String userId, Integer[] menuIds,String postId) {
        UserMenu um = new UserMenu();
        um.setUserId(userId);
        userMenuMapper.delete(um);
       // userMenuMapper.delete(new UserMenu(userId));
        for(Integer menuid : menuIds){
          UserMenu userMenu = new UserMenu();
          userMenu.setUserId(userId);
          userMenu.setAuthId(menuid);
          userMenu.setPostId(postId);
          userMenuMapper.insert(userMenu);
        }
    }
      
    @Override
    public List<AuthPostMenuVo> findMenuOrButtonByUserId(String userId, Integer menuId, boolean isMenu,String postId) {
      List<AuthPostMenuVo> findMenuOrButtonByUserId = null;
      //1先去authPostMenu表中查询是不是有默认过个人权限，如果有就直接反回
      findMenuOrButtonByUserId  =  authPostMenuMapper.findMenuOrButtonByUserId(userId, menuId, isMenu);
      //2.如果表中没有数据通过postid查询默认权限。
      if(findMenuOrButtonByUserId.size()==0){
        findMenuOrButtonByUserId= authPostMenuMapper.findMenuOrButtonByPostId(postId, menuId, isMenu);
      }
      return findMenuOrButtonByUserId;

    }

}
