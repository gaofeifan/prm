package com.pj.auth.mapper;

import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.conf.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
public interface AuthPostMenuMapper extends BaseMapper<AuthPostMenu> {

   List<AuthPostMenuVo> findMenuByPostId(@Param("postId") Integer postId,@Param("menuIds") Integer[] menuIds);

   List<AuthPostMenuVo> findMenuOrButtonByPostId(@Param("postId")Integer postId, @Param("menuId") Integer menuId, @Param("isMenu") boolean isMenu);

}
