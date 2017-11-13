package com.pj.partner.service.impl;

import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
@Transactional
public class PartnerDetailsServiceImpl extends AbstractBaseServiceImpl<PartnerDetails,Integer> implements PartnerDetailsService {
    @Autowired
    private PartnerDetailsMapper partnerDetailsMapper;

    @Override
    public BaseMapper<PartnerDetails> getMapper() {
        return partnerDetailsMapper;
    }

    @Override
    public List<PartnerDetails> selectPartnerDetailsList() {
        return null;
    }
}
