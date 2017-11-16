package com.pj.auth.service.impl;


import com.pj.auth.mapper.AuthPostMenuMapper;
import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
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

    @Override
    public BaseMapper<AuthPostMenu> getMapper() {
        return authPostMenuMapper;
    }

    @Override
    public List<AuthPostMenuVo> findMenuByPostId(Integer postId) {
        return authPostMenuMapper.findMenuByPostId(postId,null);
    }
    @Override
    public List<AuthPostMenuVo> findButtonByPostIdAndMenuIds(Integer postId, Integer[] menuIds) {
        return authPostMenuMapper.findMenuByPostId(postId,menuIds);
    }

    public List<AuthPostMenuVo> findMenuOrButtonByPostId(Integer postId, Integer menuId, boolean isMenu){
        return authPostMenuMapper.findMenuOrButtonByPostId(postId,menuId,isMenu);
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
}
