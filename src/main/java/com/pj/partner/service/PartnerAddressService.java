package com.pj.partner.service;

import com.pj.conf.base.BaseService;
import com.pj.partner.pojo.PartnerAddress;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface PartnerAddressService extends BaseService<PartnerAddress,Integer> {

    void deletePartnerAddressByDetails(Integer detailsId, String email);

    List<PartnerAddress> selectPartnerAddressesByDetailsId(Integer detailsId);

    void insertList(List<PartnerAddress> address, String email);
}
