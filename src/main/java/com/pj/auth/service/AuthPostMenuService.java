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

    /**
     *  设置默认权限
     * @param postId
     */
    public void editDefaultAuth(Integer postId);
    
    //x.gao 通过用户更新 20171227
    void editPostAuthorityByuserId(String userId, Integer[] menuIds);
    //x.gao 20171227
    List<AuthPostMenuVo> findMenuOrButtonByUserId(String userId, Integer menuId, boolean isMenu);

}
