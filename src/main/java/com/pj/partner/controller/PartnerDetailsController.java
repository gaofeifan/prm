package com.pj.partner.controller;

import com.pj.conf.base.BaseController;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        List<PartnerDetails> list = this.partnerDetailsService.selectListByQuery(name,offPartner,blacklistPartner);
        return this.success(list);

    }

    /**
     *  查询合作伙伴管理集合
     * @return
     */
    public Object selectPartnerDetailsList(){
        List<PartnerDetails> list = this.partnerDetailsService.selectPartnerDetailsList();
        return this.success(list);
    }

    /**
     *  根据id查询合作伙伴详情
     *  @User  GFF
     * @return
     */
    public Object selectPartnerDetailsById(@ApiParam("id") @RequestParam(name = "id") Integer id ){
        PartnerDetails pd = this.partnerDetailsService.selectByPrimaryKey(id);
    }

    /**
     * 更新合作伙伴详情
     * @User  GFF
     * @param partnerDetails
     * @return
     */
    public Object updatePartnerDetailsById(@ModelAttribute("partnerDetails") PartnerDetails partnerDetails){
        this.partnerDetailsService.updateByPrimaryKey(partnerDetails);
        return this.success();
    }

    /**
     * 新增合作伙伴详情
     * @User  GFF
     * @param partnerDetails
     * @return
     */
    public Object updatePartnerDetails(@ModelAttribute("partnerDetails") PartnerDetails partnerDetails){
        this.partnerDetailsService.insertSelective(partnerDetails);
        return this.success();
    }

}
