package com.pj.partner.controller;

import com.pj.cache.PartnerDetailsCache;
import com.pj.conf.base.BaseController;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;
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

import java.util.ArrayList;
import java.util.Arrays;
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
                                     @ApiParam("partnerCategory") @RequestParam(name = "partnerCategory",required = false) String partnerCategory ,
                                     @ApiParam("blacklistPartner") @RequestParam(name = "blacklistPartner",required = false) Integer blacklistPartner){
        List<PartnerDetails> list = this.partnerDetailsService.selectListByQuery(name,offPartner,blacklistPartner,partnerCategory);
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
        List<Hierarchy> list = this.hierarchyMapper.selectAll();
        Object[] array = list.stream().map(hi -> hi.getLayerNumber()).toArray();
      /*  int num = 0;
        for (Hierarchy h: list) {
            num += h.getLayerNumber();
        }*/
        return this.success(array);
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

    /**
     * 根据主键删除合作伙伴
     * @param id
     * @return
     */
    @ApiOperation(value = "根据主键删除合作伙伴" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/deletePartnerDetailsById")
    @ResponseBody
    public Object deletePartnerDetailsById(@ApiParam("id") @RequestParam(name = "id") Integer id){
        this.partnerDetailsService.deletePartnerDetailsById(id);
        return this.success();
    }

    /**
     * 根据主键删除合作伙伴
     * @param id
     * @return
     */
    @ApiOperation(value = "判断是否可以删除合作伙伴" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/isDeletePartnerDetails")
    @ResponseBody
    public Object isDeletePartnerDetails(@ApiParam("id") @RequestParam(name = "id") Integer id){
        boolean b = this.partnerDetailsService.isDeletePartnerDetails(id);
        return this.success(b);
    }

    /**
     * @param ids     查询转移文件
     * @return
     */
    @ApiOperation(value = "查询转移文件" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/selectShiftFile")
    @ResponseBody
    public Object selectShiftFile(@ApiParam("ids") @RequestParam(name = "ids") Integer[] ids){
        List<PartnerDetailsShifFile> pdsf = this.partnerDetailsService.selectShiftFile(ids);
        PartnerDetailsCache.put("details",pdsf);
        return this.success(pdsf);
    }

   @ApiOperation(value = "删除转移文件" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/deleteShiftFile")
    @ResponseBody
    public Object deleteShiftFile(@ApiParam("ids") @RequestParam(name = "ids") Integer[] ids){
       List<PartnerDetailsShifFile> deletePd = new ArrayList<>();
       Object o = PartnerDetailsCache.getValueByKey("details");
       List<PartnerDetailsShifFile> pdsf = (List<PartnerDetailsShifFile>) o;
       for (PartnerDetailsShifFile pd:pdsf) {
           List<Integer> list = Arrays.asList(ids);
           if(list.contains(pd.getId())){
               deletePd.add(pd);
           }
       }
       pdsf.removeAll(deletePd);
       PartnerDetailsCache.put("details",pdsf);
       return this.success(pdsf);
    }

    /**
     * 修改转移目录
     * @param ids
     * @return
     */
    @ApiOperation(value = "修改转移目录" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/shiftPartnerDetailsFileByIds")
    @ResponseBody
    public Object shiftPartnerDetailsFileByIds(
                                                @ApiParam("id") @RequestParam(name = "id") Integer id){
        this.partnerDetailsService.shiftPartnerDetailsFileByIds(id);
        return this.success();
    }

    @ApiOperation(value = "获取父集代码集合" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/getParentCodeList")
    @ResponseBody
    public Object getParentCodeList(@ApiParam("id") @RequestParam(name = "id") Integer id){
        Object[] codes = this.partnerDetailsService.getParentCodeList(id);
        return this.success(codes);
    }

}
