package com.pj.partner.service;

import com.pj.conf.base.BaseService;
import com.pj.partner.pojo.PartnerDetails;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface PartnerDetailsService extends BaseService<PartnerDetails,Integer> {

    List<PartnerDetails> selectPartnerDetailsList();

    List<PartnerDetails> selectListByQuery(String name, Integer offPartner, Integer blacklistPartner);
}
