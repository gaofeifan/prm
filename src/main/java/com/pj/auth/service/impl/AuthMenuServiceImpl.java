package com.pj.auth.service.impl;

import com.pj.auth.mapper.AuthMenuMapper;
import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.service.AuthMenuService;
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
public class AuthMenuServiceImpl  extends AbstractBaseServiceImpl<AuthMenu,Integer> implements AuthMenuService{

    @Autowired
   private AuthMenuMapper authMenuMapper;

    @Override
    public BaseMapper<AuthMenu> getMapper() {
        return authMenuMapper;
    }
}
