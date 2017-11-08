package com.pj.user.service.impl;

import com.pj.user.mapper.UsaerMapper;
import com.pj.user.pojo.UserLevel;
import com.pj.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 */
public class UserServiceImpl implements UserService{

    @Autowired
    private UsaerMapper usaermapper;

    @Override
    public List<UserLevel> findUserLevelList() {
        return null;
    }
}
