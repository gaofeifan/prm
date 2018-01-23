package com.pj.user.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pj.user.pojo.Hierarchy;
import com.pj.user.pojo.UserLevel;

/**
 * Created by SenevBoy on 2017/11/8.
 */
public interface UserService {

    // 用户信用等级列表获取
    List<UserLevel> findUserLevelList();

    // 获取普通用户模块层级列表
    List<Hierarchy> findHierarchyList();

    // 获取旧数据用以支持日志统计
    UserLevel findOneOldDataById(UserLevel usel);
    // 更新用户等级
    void updateLevelById(UserLevel usel, HttpServletRequest request, String email);

    // 更新层级位数
    void updateHierarchyList(List<Hierarchy> hierarchy, HttpServletRequest request, String email);

    /**
     *  校验是否可以修改
     */
    boolean[] checkIsEditHierarchy();

    /**
      * @Author:  GFF
      * @Date:  10:43 2017/12/29
      * @Param:  name
      * @Return:
      * @Description:  根据名称查询信用等级
      */
    UserLevel selectLevelByName(String name);
}
