package com.pj.partner.service.impl;

import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.pojo.PartnerAddress;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerAddressService;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
@Transactional
public class PartnerDetailsServiceImpl extends AbstractBaseServiceImpl<PartnerDetails,Integer> implements PartnerDetailsService {
    @Autowired
    private PartnerDetailsMapper partnerDetailsMapper;
    @Autowired
    private PartnerAddressService partnerAddressService;
    @Autowired
    private PartnerLinkmanService partnerLinkmanService;
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

    @Override
    public PartnerDetails selectByPrimaryKey(Integer key) {
        PartnerDetails pd = super.selectByPrimaryKey(key);
        PartnerAddress address = new PartnerAddress();
        address.setDetailsId(key);
        List<PartnerAddress> addresss = this.partnerAddressService.select(address);
        pd.setAddress(addresss);
        PartnerLinkman linkman = new PartnerLinkman();
        linkman.setDetailsId(key);
        List<PartnerLinkman> linkmens = this.partnerLinkmanService.select(linkman);
        pd.setLinkmans(linkmens);
        return pd;
    }


    @Override
    public void updateByPrimaryKey(PartnerDetails record, HttpServletRequest request){
        List<PartnerAddress> address = record.getAddress();
        List<PartnerLinkman> linkmans = record.getLinkmans();
        request.getSession().setAttribute("old_partnerLinkman",this.partnerLinkmanService.selectPartnerLinkmansByDetailsId(record.getId()));
        request.getSession().setAttribute("new_partnerLinkman",linkmans);
        request.getSession().setAttribute("old_partnerAddress",this.partnerAddressService.selectPartnerAddressesByDetailsId(record.getId()));
        request.getSession().setAttribute("new_partnerAddress",address);
        this.partnerLinkmanService.deletePartnerLinkmanByDetailsId(record.getId());
        this.partnerAddressService.deletePartnerAddressByDetails(record.getId());
        this.partnerAddressService.insertList(address);
        this.partnerLinkmanService.insertList(linkmans);
       super.updateByPrimaryKey(record);
    }

    @Override
    public void insertSelective(PartnerDetails partnerDetails, HttpServletRequest request) {
        super.insertSelective(partnerDetails);
        List<PartnerLinkman> linkmans = partnerDetails.getLinkmans();
        List<PartnerAddress> address = partnerDetails.getAddress();
        request.getSession().setAttribute("new_partnerAddress",address);
        request.getSession().setAttribute("new_partnerLinkman",linkmans);
        for(PartnerLinkman pl : linkmans){
            pl.setDetailsId(partnerDetails.getId());
            this.partnerLinkmanService.insert(pl);
        }
        for (PartnerAddress pa: address ) {
            pa.setDetailsId(partnerDetails.getId());
            this.partnerAddressService.insert(pa);
        }
    }
}
