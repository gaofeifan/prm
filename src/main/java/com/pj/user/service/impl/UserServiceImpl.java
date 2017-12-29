package com.pj.user.service.impl;

import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.mapper.PartnerDetailsShifFileMapper;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;
import com.pj.user.mapper.HierarchyMapper;
import com.pj.user.mapper.UsaerLevelMapper;
import com.pj.user.pojo.Hierarchy;
import com.pj.user.pojo.UserLevel;
import com.pj.user.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;


import java.util.ArrayList;
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
    @Autowired
    private PartnerDetailsShifFileMapper partnerDetailsShifFileMapper;
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
//        Integer length = this.partnerDetailsMapper.selectDetailsMaxCode();
        Example example = new Example(PartnerDetails.class);
        example.createCriteria().andCondition("is_delete = 0").andIsNotNull("pId");
        List<PartnerDetails> partnerDetails = this.partnerDetailsMapper.selectByExample(example);
        List<List<PartnerDetails>> lists = new ArrayList<>();

        for (PartnerDetails pd : partnerDetails){
            List<PartnerDetails> parentList = this.partnerDetailsMapper.getParentList(pd.getId());
            lists.add(parentList);
        }
        int number = 0;
        List<PartnerDetails> partnerDetailsShifFiles = new ArrayList<>();

        for ( int i = 0;i <  lists.size();i++){
            if(number<lists.get(i).size()){
                number =   lists.get(i).size();
               partnerDetailsShifFiles = lists.get(i);
            }
        }
        Object[] objects = partnerDetailsShifFiles.stream().map(pd -> pd.getCode()).toArray();
        StringBuilder sb = new StringBuilder();
        for (Object obj: objects) {
                sb.append(obj.toString());
        }
       int length = sb.toString().length();
        List<PartnerDetails> partnerPIdIsNull = null;
        if(partnerDetails.size() == 0){
            example.clear();
            example.createCriteria().andCondition("is_delete = 0").andIsNull("pId");
            partnerPIdIsNull = this.partnerDetailsMapper.selectByExample(example);
        }

        int num = 0;
        List<Hierarchy> list = this.hierarchyMapper.selectAll();
        boolean [] flag = new boolean[list.size()];
        int i = 0;
        for ( ;i<list.size();i++) {
            num += list.get(i).getLayerNumber();
            if( i ==0){
                if((partnerPIdIsNull == null || partnerPIdIsNull.size() ==0) && partnerDetails.size() == 0){
                    flag[i] = true;
                }
                continue;
            }
            if (length < num) {
                flag[i] = true;
            }
        }
            return flag;
    }

    @Override
    public UserLevel selectLevelByName(String name) {
        String[] strings = name.split("-");
        UserLevel record = new UserLevel();
        record.setLevel(strings[0]);
        record.setProtocolType(strings[1]);
        List<UserLevel> levels = this.userLevelMapper.select(record);
        return levels.size() != 0 ? levels.get(0) : null;
    }
}
