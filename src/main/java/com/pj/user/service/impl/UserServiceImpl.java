package com.pj.user.service.impl;

import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.user.mapper.HierarchyMapper;
import com.pj.user.mapper.UsaerLevelMapper;
import com.pj.user.pojo.Hierarchy;
import com.pj.user.pojo.UserLevel;
import com.pj.user.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UsaerLevelMapper userLevelMapper;

    @Autowired
    private HierarchyMapper hierarchyMapper;
    @Autowired
    private PartnerDetailsMapper partnerDetailsMapper;

    @Override
    public List<UserLevel> findUserLevelList() {
        return userLevelMapper.selectAll();
    }

    @Override
    public List<Hierarchy> findHierarchyList() {
        return hierarchyMapper.selectAll();
    }


    @Override
    public UserLevel findOneOldDataById(UserLevel usel) {
        return userLevelMapper.selectByPrimaryKey(usel.getId());
    }

    @Override
    public void updateLevelById(UserLevel usel, HttpServletRequest request, String email) {

       request.getSession().setAttribute("oldUserLevelData",userLevelMapper.selectByPrimaryKey(usel.getId()));
        userLevelMapper.updateByPrimaryKey(usel);
        request.getSession().setAttribute("newUserLevelData", userLevelMapper.selectByPrimaryKey(usel.getId()));

    }

    @Override
    public void updateHierarchyList(List<Hierarchy> hierarchy, HttpServletRequest request, String email) {
        request.getSession().setAttribute("oldHierarchyData",hierarchyMapper.selectAll());
        if(null!=hierarchy){
            for (Hierarchy hi : hierarchy){
                hierarchyMapper.updateByPrimaryKey(hi);
            }
        }
        request.getSession().setAttribute("newHierarchyData", hierarchyMapper.selectAll());
    }

    @Override
    public boolean[] checkIsEditHierarchy() {
        Integer length = this.partnerDetailsMapper.selectDetailsMaxCode();
        int num = 0;
        List<Hierarchy> list = this.hierarchyMapper.selectAll();
        boolean [] flag = new boolean[list.size()];
        int i = 0;
        for ( ;i<list.size();i++) {

            num += list.get(i).getLayerNumber();
            if( i ==0){
                continue;
            }
            if (length < num) {
                flag[i] = true;
            }
        }
            return flag;
    }
}
