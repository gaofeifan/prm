package com.pj.partner.service.impl;

import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.partner.mapper.PartnerLinkmanMapper;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerLinkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
@Transactional
public class PartnerLinkmanServiceImpl extends AbstractBaseServiceImpl<PartnerLinkman,Integer> implements PartnerLinkmanService {
    @Autowired
    private PartnerLinkmanMapper partnerLinkmanMapper;

    @Override
    public BaseMapper<PartnerLinkman> getMapper() {
        return partnerLinkmanMapper;
    }

    @Override
    public void deletePartnerLinkmanByDetailsId(Integer detailsId) {
        PartnerLinkman partnerLinkman = new PartnerLinkman();
        partnerLinkman.setDetailsId(detailsId);
        this.partnerLinkmanMapper.delete(partnerLinkman);
    }
}
