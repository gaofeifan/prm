package com.pj.auth.service;

import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseService;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface AuthPostMenuService extends BaseService<AuthPostMenu,Integer> {

    public AuthPostMenuVo findMenuByPostId(Integer postId);
}
