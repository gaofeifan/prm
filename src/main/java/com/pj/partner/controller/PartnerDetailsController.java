package com.pj.partner.controller;

import com.pj.conf.base.BaseController;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Api(value = "合作伙伴")
@RequestMapping(value = "/partner/details")
public class PartnerDetailsController extends BaseController {

    @Autowired
    private PartnerDetailsService partnerDetailsService;
    /**
     *  查询树桩数据
     * @user  GFF
     * @param name
     * @param offPartner
     * @param blacklistPartner
     * @return
     */
    public Object selectListByQuery(@ApiParam("name") @RequestParam(name = "name",required = false) String name ,
                                    @ApiParam("offPartner") @RequestParam(name = "offPartner",required = false) Integer offPartner ,
                                    @ApiParam("blacklistPartner") @RequestParam(name = "blacklistPartner",required = false) Integer blacklistPartner){
//        this.partnerDetailsService.selectListByQuery();

        return null;

    }

    public Object selectPartnerDetailsList(){
//        this.partnerDetailsService.selectListByQuery();
        List<PartnerDetails> list = this.partnerDetailsService.selectPartnerDetailsList();
        return null;

    }
}
