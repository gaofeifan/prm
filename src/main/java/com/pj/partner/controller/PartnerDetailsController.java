package com.pj.partner.controller;

import com.pj.conf.base.BaseController;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.user.mapper.HierarchyMapper;
import com.pj.user.pojo.Hierarchy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Controller
@Api(value = "合作伙伴")
@RequestMapping(value = "/partner/details")
public class PartnerDetailsController extends BaseController {

    @Autowired
    private PartnerDetailsService partnerDetailsService;
    @Autowired
    private HierarchyMapper hierarchyMapper;

    /**
     *  查询树桩数据
     * @user  GFF
     * @param name
     * @param offPartner
     * @param blacklistPartner
     * @return
     */
    @ApiOperation(value = "查询树桩数据" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/selectListByQuery")
    @ResponseBody
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
    @ApiOperation(value = "查询合作伙伴管理集合" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/selectPartnerDetailsList")
    @ResponseBody
    public Object selectPartnerDetailsList(){
        List<PartnerDetails> list = this.partnerDetailsService.selectPartnerDetailsList();
        return this.success(list);
    }

    /**
     *  根据id查询合作伙伴详情
     *  @User  GFF
     * @return
     */
    @ApiOperation(value = "根据id查询合作伙伴详情" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/selectPartnerDetailsById")
    @ResponseBody
    public Object selectPartnerDetailsById(@ApiParam("id") @RequestParam(name = "id") Integer id ){
        PartnerDetails pd = this.partnerDetailsService.selectByPrimaryKey(id);
        return this.success(pd);
    }



    /**
     * 更新合作伙伴详情
     * @User  GFF
     * @param partnerDetails
     * @return
     */
    @ApiOperation(value = "更新合作伙伴详情" ,httpMethod = "POST", response = Object.class)
    @RequestMapping(value = "/updatePartnerDetailsById")
    @ResponseBody
    public Object updatePartnerDetailsById(@ModelAttribute("partnerDetails") PartnerDetails partnerDetails){
        this.partnerDetailsService.updateByPrimaryKey(partnerDetails , getRequest());
        return this.success();
    }

    /**
     * 新增合作伙伴详情
     * @User  GFF
     * @param partnerDetails
     * @return
     */
    @ApiOperation(value = "新增合作伙伴详情" ,httpMethod = "POST", response = Object.class)
    @RequestMapping(value = "/insertPartnerDetails")
    @ResponseBody
    public Object insertPartnerDetails(@ModelAttribute("partnerDetails") PartnerDetails partnerDetails){
        this.partnerDetailsService.insertSelective(partnerDetails,getRequest());
        return this.success();
    }

    /**
     * 获取代码长度
     * @User  GFF
     * @return
     */
    @ApiOperation(value = "获取代码长度" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/getCodeLength")
    @ResponseBody
    public Object getCodeLength(){
        Hierarchy hierarchy = this.hierarchyMapper.selectAll().get(0);
        int codeLength = hierarchy.getLayerFour() + hierarchy.getLayerOne() + hierarchy.getLayerTwo() + hierarchy.getLayerThree();
        return this.success(codeLength);
    }

    /**
     *  校验字段是否重复
     * @param fieldName
     * @param fieldValue
     * @return
     */
    @ApiOperation(value = "校验字段是否重复" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/verifyValueRepeat")
    @ResponseBody
    public Object verifyValueRepeat(@ApiParam("字段名称") @RequestParam(name = "fieldName") String fieldName ,
                                     @ApiParam("字段值") @RequestParam(name = "fieldValue") String fieldValue ){
        boolean flag = this.partnerDetailsService.verifyValueRepeat(fieldName,fieldValue);
        return this.success(flag);
    }



}
