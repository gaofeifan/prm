package com.pj.auth.service;

import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.conf.base.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface AuthPostMenuService extends BaseService<AuthPostMenu,Integer> {

    /**
     * 查询菜单或按钮通过岗位id
     * @user  GFF
     * @param postId
     * @return
     */
    List<AuthPostMenuVo> findMenuByPostId(Integer postId);

    List<AuthPostMenuVo> findMenuOrButtonByPostId(Integer postId, Integer menuId, boolean isMenu);

    List<AuthPostMenuVo> findButtonByPostIdAndMenuIds(Integer postId, Integer[] menuIds);

    void editPostAuthority(Integer postId, Integer[] menuIds);

    boolean findOperatingAuthorizationByPostIdAndByButton(Integer postId , Integer button);
}
