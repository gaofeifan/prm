package com.pj.auth.service.impl;


import com.pj.auth.mapper.AuthPostMenuMapper;
import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.auth.service.AuthMenuService;
import com.pj.auth.service.AuthPostMenuService;
import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Override
    public BaseMapper<AuthPostMenu> getMapper() {
        return authPostMenuMapper;
    }

    @Override
    public List<AuthPostMenuVo> findMenuByPostId(Integer postId) {
        AuthPostMenu record = new AuthPostMenu();
        record.setPostId(postId);
        List<AuthPostMenu> menus = this.authPostMenuMapper.select(record);
        List<AuthPostMenuVo> menu = authPostMenuMapper.findMenuByPostId(postId, null);
        if(menus.size() != 0){
            return menu;
        }
        editDefaultAuth(postId);
        return authPostMenuMapper.findMenuByPostId(postId, null);
    }
    @Override
    public List<AuthPostMenuVo> findButtonByPostIdAndMenuIds(Integer postId, Integer[] menuIds) {
        if(menuIds == null || menuIds.length == 0){
            return null;
        }
        return authPostMenuMapper.findMenuByPostId(postId,menuIds);
    }

    public List<AuthPostMenuVo> findMenuOrButtonByPostId(Integer postId, Integer menuId, boolean isMenu){
        AuthPostMenu am = new AuthPostMenu();
        am.setPostId(postId);
        List<AuthPostMenu> list = authPostMenuMapper.select(am);
        if(list.size() == 0){
            editDefaultAuth(postId);
        }
        return authPostMenuMapper.findMenuOrButtonByPostId(postId, menuId, isMenu);
    }

    @Override
    public void editPostAuthority(Integer postId, Integer[] menuIds) {

        this.authPostMenuMapper.delete(new AuthPostMenu(postId));
        for(Integer id : menuIds){
            this.authPostMenuMapper.insert(new AuthPostMenu(id,postId));
        }

    }
    @Override
    public boolean findOperatingAuthorizationByPostIdAndByButton(Integer postId , Integer button) {
        List<AuthPostMenu> list = this.authPostMenuMapper.select(new AuthPostMenu(button, postId));
        if(list.size() > 0){
            return true;
        }
        return false;
    }

    public void editDefaultAuth(Integer postId){
        List<AuthMenu> menus = this.authMenuService.selectDefaultMenu();
        AuthPostMenu apm = null;
        for (AuthMenu am:menus) {
            apm = new AuthPostMenu();
            apm.setMenuId(am.getId());
            apm.setPostId(postId);
            this.authPostMenuMapper.insert(apm);
        }
    }

}
