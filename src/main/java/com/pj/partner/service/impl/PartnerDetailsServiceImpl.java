package com.pj.partner.service.impl;

import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
        PartnerDetails pd = new PartnerDetails();
        pd.setIsDelete(0);
        return this.partnerDetailsMapper.select(pd);
    }

    @Override
    public List<PartnerDetails> selectListByQuery(String name, Integer offPartner, Integer blacklistPartner) {
        Example example = new Example(PartnerDetails.class);
        Example.Criteria criteria = example.createCriteria();
        if(offPartner != null){
            criteria.andCondition("is_disable" , offPartner);
        }
        if(blacklistPartner != null){
            criteria.andCondition("is_blacklist" , blacklistPartner);
        }
        if(StringUtils.isNotBlank(name)){
            criteria.andLike("name",name);
        }
        criteria.andCondition("is_delete",0);
        criteria.andCondition("is_dir",0);
        List<PartnerDetails> pds = this.partnerDetailsMapper.selectByExample(example);
        return pds;
    }
}
