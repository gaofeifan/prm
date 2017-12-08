package com.pj.user.service;

import com.pj.user.pojo.Hierarchy;
import com.pj.user.pojo.UserLevel;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.util.List;

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
}
