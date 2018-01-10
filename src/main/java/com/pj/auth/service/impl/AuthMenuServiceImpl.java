package com.pj.auth.service.impl;

import com.pj.auth.mapper.AuthMenuMapper;
import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.service.AuthMenuService;
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
public class AuthMenuServiceImpl  extends AbstractBaseServiceImpl<AuthMenu,Integer> implements AuthMenuService{

    @Autowired
   private AuthMenuMapper authMenuMapper;

    @Override
    public BaseMapper<AuthMenu> getMapper() {
        return authMenuMapper;
    }

    // 支持  aop切面获取 权限信息 2017年11月17日10:08:27
    @Override
    public List<AuthMenu> findAuthMenuListBypostId(String postId) {
        return authMenuMapper.findAuthMenuListBypostId(postId);
    }

    public List<AuthMenu> selectDefaultMenu(){
        AuthMenu record = new AuthMenu();
        record.setIsDefault(1);
        List<AuthMenu> list = this.authMenuMapper.select(record);
        return list;

    }

    @Override
    public Integer[] selectMenuIds(String postId) {
        return this.authMenuMapper.selectMenuIds(postId);
    }
}
