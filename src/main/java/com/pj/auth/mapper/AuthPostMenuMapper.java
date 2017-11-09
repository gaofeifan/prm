package com.pj.auth.mapper;

import com.pj.auth.pojo.AuthPostMenu;
import com.pj.auth.pojo.AuthPostMenuVo;
import com.pj.conf.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
public interface AuthPostMenuMapper extends BaseMapper<AuthPostMenu> {
   AuthPostMenuVo findMenuByPostId(@Param("postId") Integer postId);
}
