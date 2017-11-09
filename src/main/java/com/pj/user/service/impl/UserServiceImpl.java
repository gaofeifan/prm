package com.pj.user.service.impl;

import com.pj.user.mapper.HierarchyMapper;
import com.pj.user.mapper.UsaerLevelMapper;
import com.pj.user.pojo.Hierarchy;
import com.pj.user.pojo.UserLevel;
import com.pj.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UsaerLevelMapper userLevelMapper;

    @Autowired
    private HierarchyMapper hierarchyMapper;

    @Override
    public List<UserLevel> findUserLevelList() {
        return userLevelMapper.selectAll();
    }

    @Override
    public List<Hierarchy> findHierarchyList() {
        return hierarchyMapper.selectAll();
    }
}
