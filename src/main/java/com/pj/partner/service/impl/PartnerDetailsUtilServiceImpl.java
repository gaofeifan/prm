package com.pj.partner.service.impl;

import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.pojo.PartnerDetails;

import com.pj.partner.service.PartnerDetailsUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * @ClassName:
 * @Description: (这里用一句话描述这个类的作用)
 * @author
 * @date 2017/12/27 15:18   
 **/
@Service
@Transactional
public class PartnerDetailsUtilServiceImpl implements PartnerDetailsUtilService {

    @Autowired
    private PartnerDetailsMapper partnerDetailsMapper;

    @Override
    public void updateByPrimaryKey(PartnerDetails pd, String email) {
        this.partnerDetailsMapper.updateByPrimaryKey(pd);
    }
}
