package com.pj.aeserviceapi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pj.aeserviceapi.pojo.ResponseData;
import com.pj.partner.service.PartnerDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/***
 * @ClassName: OutController
 * @Description: (项目对外接口)
 * @author SenevBoy
 * @date 2018/2/6 9:41   
 **/
@RestController("/ae")
@Api(value = "AE对外接口")
public class AeOutController {

    @Autowired
    private PartnerDetailsService partnerDetailsService;

    @RequestMapping(value = "/partnerdatils")
    @ResponseBody
    @ApiOperation(value = "AE对外接口 -- -" , httpMethod = "POST" , response = Object.class)
    public List<ResponseData> getPartnerDateilsList(@RequestBody ResponseData responseData) {
        List<ResponseData> partnerDetailsList = partnerDetailsService.aeAirlineFindPartnerDateilsList(responseData );
//        return JSONObject.fromObject(partnerDetailsList).toString();
        return partnerDetailsList;
    }


    /**
     * 客商
     * @param
     * @return
     */
    @RequestMapping(value = "/partner/partnerdatils")
    @ResponseBody
    @ApiOperation(value = "AE对外接口 --客商接口 -" , httpMethod = "POST" , response = Object.class)
    public List<ResponseData> getPartnerDatilsList(@RequestBody ResponseData responseData ) {
        List<ResponseData> partnerDetailsList = partnerDetailsService.aePartnerFindPartnerDateilsList( responseData);
//        return JSONObject.fromObject(partnerDetailsList).toString();
        return partnerDetailsList;
    }

}