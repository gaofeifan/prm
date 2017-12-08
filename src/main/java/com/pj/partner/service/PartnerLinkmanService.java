package com.pj.partner.service;

import com.pj.conf.base.BaseService;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerLinkman;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface PartnerLinkmanService extends BaseService<PartnerLinkman,Integer> {

    void deletePartnerLinkmanByDetailsId(Integer detailsId);

    List<PartnerLinkman> selectPartnerLinkmansByDetailsId(Integer id);


    void insertList(List<PartnerLinkman> linkmans, String email);
}
