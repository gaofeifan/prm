package com.pj.partner.service.impl;

import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.partner.mapper.PartnerAddressMapper;
import com.pj.partner.pojo.PartnerAddress;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerAddressService;
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
public class PartnerAddressServiceImpl extends AbstractBaseServiceImpl<PartnerAddress,Integer> implements PartnerAddressService {

    @Autowired
    private PartnerAddressMapper partnerAddressMapper;

    @Override
    public BaseMapper<PartnerAddress> getMapper() {
        return partnerAddressMapper;
    }

    @Override
    public void deletePartnerAddressByDetails(Integer detailsId, String email) {
        PartnerAddress record = new PartnerAddress();
        record.setDetailsId(detailsId);
        this.partnerAddressMapper.delete(record);
    }

    @Override
    public List<PartnerAddress> selectPartnerAddressesByDetailsId(Integer detailsId) {
        PartnerAddress record = new PartnerAddress();
        record.setDetailsId(detailsId);
        return this.partnerAddressMapper.select(record);
    }

    @Override
    public void insertList(List<PartnerAddress> address, String email) {
        this.insertList(address);
    }
}
