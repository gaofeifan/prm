package com.pj.partner.controller;

import com.pj.cache.PartnerDetailsCache;
import com.pj.conf.base.BaseController;
import com.pj.conf.utils.ThreadEmail;
import com.pj.partner.pojo.PartnerAddress;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import com.pj.partner.service.impl.PartnerLinkmanServiceImpl;
import com.pj.user.Utils.ObjectTrim;
import com.pj.user.mapper.HierarchyMapper;
import com.pj.user.pojo.Hierarchy;
import com.pj.user.pojo.RequestParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
@Api(value = "合作伙伴")
@RequestMapping(value = "/partner/details")
public class PartnerDetailsController extends BaseController {

    @Autowired
    private PartnerDetailsService partnerDetailsService;
    @Autowired
    private HierarchyMapper hierarchyMapper;
  @Autowired
    private PartnerLinkmanService partnerLinkmanService;

    /**
     *  查询树桩数据
     * @user  GFF
     * @param name
     * \
     * @param offPartner
     * @param blacklistPartner
     * @return
     */
    @ApiOperation(value = "查询树桩数据" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/selectListByQuery")
    @ResponseBody
    public Object selectListByQuery(@ApiParam("name") @RequestParam(name = "name",required = false) String name ,
                                     @ApiParam("查看停用Partner") @RequestParam(name = "offPartner",required = false) Integer offPartner ,
                                    @ApiParam("分类") @RequestParam(name = "partnerCategory",required = false) String partnerCategory ,
                                    @ApiParam("查看黑名单Partner") @RequestParam(name = "blacklistPartner",required = false) Integer blacklistPartner
    ){
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
    public Object updatePartnerDetailsById(@ModelAttribute("partnerDetails") PartnerDetails partnerDetails,
                                           @ApiParam("联系方式") @RequestParam(name = "linkmans" ,required = false) String linkmans ,
                                           @ApiParam("联系地址") @RequestParam(name = "address" ,required = false) String address,
                                           @ApiParam("email") @RequestParam(name = "email" ,required = false) String email ,
                                           HttpServletRequest request
    ){
        if(linkmans != null){
            JSONArray array = JSONArray.fromString(linkmans);
            List<PartnerLinkman> list = JSONArray.toList(array, PartnerLinkman.class);
            partnerDetails.setLinkmansList(list);
            // 校验手机号 发送邮件
            checkPhoneSendEmail(request,list, partnerDetails.getChineseName());

        }

        if(address != null){
            JSONArray array = JSONArray.fromString(address);
            List<PartnerAddress> list = JSONArray.toList(array, PartnerAddress.class);
            partnerDetails.setAddressList(list);
        }
        partnerDetails = (PartnerDetails) ObjectTrim.beanAttributeValueTrim(partnerDetails);
        this.partnerDetailsService.updateByPrimaryKey(partnerDetails , getRequest(),email);
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
    public Object insertPartnerDetails(@ModelAttribute("partnerDetails") PartnerDetails partnerDetails,
                                       @ApiParam("联系方式") @RequestParam(name = "linkmans" ,required = false) String linkmans ,
                                       @ApiParam("联系地址") @RequestParam(name = "address" ,required = false) String address,
                                       @ApiParam("email") @RequestParam(name = "email" ,required = false) String email,
                                       HttpServletRequest request   ){

        if(linkmans != null){

            JSONArray array = JSONArray.fromString(linkmans);
            List<PartnerLinkman> list = JSONArray.toList(array, PartnerLinkman.class);
            partnerDetails.setLinkmansList(list);
            // 校验手机号 发送邮件
            checkPhoneSendEmail(request,list, partnerDetails.getChineseName());
        }
        if(address != null){
            JSONArray array = JSONArray.fromString(address);
            List<PartnerAddress> list = JSONArray.toList(array, PartnerAddress.class);
            partnerDetails.setAddressList(list);
        }
        partnerDetails = (PartnerDetails) ObjectTrim.beanAttributeValueTrim(partnerDetails);
        this.partnerDetailsService.insertSelective(partnerDetails,getRequest(),email);
        return this.success();
    }

    /**-
     *
     *
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
     * @return
     */
    @ApiOperation(value = "校验字段是否重复" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/verifyValueRepeat")
    @ResponseBody
    public Object verifyValueRepeat(@ApiParam("字段名称") @RequestParam(name = "fieldName") String fieldName ,
                                     @ApiParam("字段值") @RequestParam(name = "fieldValue") String fieldValue ,
                                     @ApiParam("id" ) @RequestParam(name = "id" , required = false) Integer id
    ){
        boolean flag = this.partnerDetailsService.verifyValueRepeat(id,fieldName,fieldValue);
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
    public Object deletePartnerDetailsById(
            @ApiParam("id") @RequestParam(name = "id") Integer id,

            @ApiParam("email") @RequestParam(name = "email") String email){
        this.partnerDetailsService.deletePartnerDetailsById(id,email);
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
     * @param id
     * @return
     */
    @ApiOperation(value = "修改转移目录" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/shiftPartnerDetailsFileByIds")

    @ResponseBody
    public Object shiftPartnerDetailsFileByIds(@ApiParam("id") @RequestParam(name = "id") Integer id,
    @ApiParam("email") @RequestParam(name = "email") String email){
        this.partnerDetailsService.shiftPartnerDetailsFileByIds(id,email);
        return this.success();
    }


    @ApiOperation(value = "获取父集代码集合" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/getParentCodeList")
    @ResponseBody
    public Object getParentCodeList(@ApiParam("id") @RequestParam(name = "id") Integer id){
        Object[] codes = this.partnerDetailsService.getParentCodeList(id);
        return this.success(codes);
    }

    @ApiOperation(value = "查询是否可以修改code" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/isEditCode")
    @ResponseBody
    public Object isEditCode(@ApiParam("id") @RequestParam(name = "id") Integer id){
        boolean flag = this.partnerDetailsService.isEditCode(id);
        return this.success(flag);
    }



    @ApiOperation(value = "查询是否有子集  返回值 true有(置灰)  false没有(必填)" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/selectIsChild")
    @ResponseBody
    public Object selectIsChild(@ApiParam("id") @RequestParam(name = "id") Integer id){
        boolean flag = this.partnerDetailsService.selectIsChild(id);
        return this.success(flag);
    }


    public Object checkPhone(){

        return null;
    }


    /**
     * @Description: 校验是否存在重复的手机号
     * @author SevenBoy
     * @param  partnerLinkman
     * @return java.lang.Object 
     * @Date 2017/12/22
     */
    @ApiOperation(value = "校验联系人电话是否重复  返回值 true重复  false不重复" ,httpMethod = "POST", response = Object.class)
    @RequestMapping(value = "/checkPhone")
    @ResponseBody
    public Object selectIsChild(@RequestBody PartnerLinkman partnerLinkman){
        List<PartnerLinkman> partnerLinkmen = this.partnerLinkmanService.selectListByPhone(partnerLinkman);
        if(null!=partnerLinkmen && partnerLinkmen.size()!=0 ){
            return this.success(true);
        }
        return this.success(false);
    }


    /**
     *  createBY   sevenBoyLiu
     *  验证 是否存在重复联系人 并发送邮件到提醒接受者
     */
    private void checkPhoneSendEmail(HttpServletRequest requseet,List<PartnerLinkman> partnerLinkmanList,String chinesName){
        List<PartnerLinkman> partnerLinkmenAl = new ArrayList<PartnerLinkman>();
        if(null!=partnerLinkmanList  && partnerLinkmanList.size()!=0){
            for (PartnerLinkman parList : partnerLinkmanList){        // 循环联系人检测 是否存在重复联系人电话
                List<PartnerLinkman> partnerLinkmen = this.partnerLinkmanService.selectListByPhone(parList);
                // 循环集合排除本身信息
                if(null!=parList.getId()){
                        for (PartnerLinkman par:partnerLinkmen){
                                if(par.getId().equals(parList.getId())){
                                    // 新旧合作伙伴中文名去全称 赋值
                                    par.setNewchineseName(chinesName);
                                    partnerLinkmen.remove(par);
                                }
                        }
                }
                partnerLinkmenAl.addAll(partnerLinkmen);  // 存储所有
            }
        }
            // 线程执行获取所有 提醒接受者信息 然后发送邮件
        requseet.getSession().setAttribute("partnerLinkmenAl",partnerLinkmenAl);
        ThreadEmail threadEmail = new ThreadEmail(requseet);
        threadEmail.start();
    }
}
