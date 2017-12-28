package com.pj.partner.service.impl;

import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.partner.mapper.PartnerLinkmanMapper;
import com.pj.partner.pojo.PartnerLinkman;
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
public class PartnerLinkmanServiceImpl extends AbstractBaseServiceImpl<PartnerLinkman,Integer> implements PartnerLinkmanService {
    @Autowired
    private PartnerLinkmanMapper partnerLinkmanMapper;

    @Override
    public BaseMapper<PartnerLinkman> getMapper() {
        return partnerLinkmanMapper;
    }

    @Override
    public void deletePartnerLinkmanByDetailsId(Integer detailsId, String email) {
        PartnerLinkman partnerLinkman = new PartnerLinkman();
        partnerLinkman.setDetailsId(detailsId);
        this.partnerLinkmanMapper.delete(partnerLinkman);
    }

    @Override
    public List<PartnerLinkman> selectPartnerLinkmansByDetailsId(Integer detailsId) {
        PartnerLinkman record = new PartnerLinkman();
        record.setDetailsId(detailsId);
        return this.partnerLinkmanMapper.select(record);
    }

    @Override
    public void insertList(List<PartnerLinkman> linkmans, String email) {
        this.partnerLinkmanMapper.insertList(linkmans);
    }

    @Override
    public void updateByPrimaryKey(PartnerLinkman partnerLinkman, String email) {
        this.partnerLinkmanMapper.updateByPrimaryKey(partnerLinkman);
    }

    @Override
    public void delete(PartnerLinkman pl, String email) {
        this.partnerLinkmanMapper.delete(pl);
    }

    /**
     * 校验联系人手机号是否重复
     * @param partnerLinkman
     * @return
     */
    @Override
    public List<PartnerLinkman> selectListByPhone(PartnerLinkman partnerLinkman) {
        PartnerLinkman partnerLinkman1 = new PartnerLinkman();
        partnerLinkman1.setPhone(partnerLinkman.getPhone());
        return partnerLinkmanMapper.select(partnerLinkman1);
    }
}
