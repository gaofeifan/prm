package com.pj.auth.service;

import com.pj.auth.pojo.AuthMenu;
import com.pj.conf.base.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface AuthMenuService extends BaseService<AuthMenu,Integer> {

    List<AuthMenu> findAuthMenuListBypostId(String postId);   // 支持  aop切面获取 权限信息 2017年11月17日10:08:27

    /**
     *  查询默认权限
     * @return
     */
    public List<AuthMenu> selectDefaultMenu();

    /**
     *  查询menuid
     * @return
     */
    Integer[] selectMenuIds(String postId);
}
