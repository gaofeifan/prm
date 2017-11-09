package com.pj.user.service;

import com.pj.user.pojo.UserLevel;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 */
public interface UserService {

    // 用户信用等级列表获取
    List<UserLevel> findUserLevelList();
}
