package com.pj.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pj.auth.pojo.AuthMenu;
import com.pj.conf.base.BaseMapper;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
public interface AuthMenuMapper extends BaseMapper<AuthMenu> {

    List<AuthMenu> findAuthMenuListBypostId(@Param("postId") int postId); // 支持  aop切面获取 权限信息 2017年11月17日10:08:27
    
    //通过userMenu的userid查询此用户的菜单权限
    List<AuthMenu> selectByUserId(@Param("userId") String userId);


    /**
     * 根据岗位查询
     * @param postId
     * @return
     */
    Integer[] selectMenuIds(Integer postId);
}
