package com.pj.partner.service;

import com.pj.conf.base.BaseService;
import com.pj.partner.pojo.PartnerAddress;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface PartnerAddressService extends BaseService<PartnerAddress,Integer> {

    void deletePartnerAddressByDetails(Integer detailsId);
}
