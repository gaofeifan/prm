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
    public AuthPostMenuVo findMenuByPostId(Integer postId) {
        return authPostMenuMapper.findMenuByPostId(postId);
    }
}
