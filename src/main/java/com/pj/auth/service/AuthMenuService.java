package com.pj.auth.service;

import com.pj.auth.pojo.AuthMenu;
import com.pj.conf.base.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface AuthMenuService extends BaseService<AuthMenu,Integer> {

    List<AuthMenu> findAuthMenuListBypostId(int postId);   // 支持  aop切面获取 权限信息 2017年11月17日10:08:27
}
