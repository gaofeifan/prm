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

   //根据userId查询 x.gao
   List<AuthPostMenuVo> findMenuOrButtonByUserId(@Param("userId")String userId, @Param("menuId") Integer menuId, @Param("isMenu") boolean isMenu);
   
   //通过userMenu的userid查询此用户的菜单权限，这个用于前台展示，多一个是否选中，同时会反回auth_menu表的全部x.gao 2018-1-2
   List<AuthPostMenuVo> selectVOByUserId(@Param("userId") String userId);

   /**
    * 查询
    * @param postId
    * @return
    */
    List<AuthPostMenuVo> selectMenuVos(@Param("postId")Integer postId);
}
