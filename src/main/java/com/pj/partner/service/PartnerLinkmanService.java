package com.pj.partner.service;

import com.pj.conf.base.BaseService;
import com.pj.partner.pojo.PartnerLinkman;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface PartnerLinkmanService extends BaseService<PartnerLinkman,Integer> {

    void deletePartnerLinkmanByDetailsId(Integer detailsId, String email);

    List<PartnerLinkman> selectPartnerLinkmansByDetailsId(Integer id);


    void insertList(List<PartnerLinkman> linkmans, String email);

    void updateByPrimaryKey(PartnerLinkman partnerLinkman, String email);

    void delete(PartnerLinkman pl, String email);

    /**
     * 校验联系人手机号是否重复
     * @param partnerLinkman
     * @return
     */
    List<PartnerLinkman> selectListByPhone(PartnerLinkman partnerLinkman);


}
