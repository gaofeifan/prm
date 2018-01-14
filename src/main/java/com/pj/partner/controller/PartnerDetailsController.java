package com.pj.partner.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pj.auth.service.AuthUserService;
import com.pj.cache.PartnerDetailsCache;
import com.pj.conf.base.BaseController;
import com.pj.conf.utils.ExcelUtils;
import com.pj.conf.utils.ThreadEmail;
import com.pj.conf.utils.TypeConversionUtils;
import com.pj.partner.pojo.PartnerAddress;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import com.pj.user.Utils.ObjectTrim;
import com.pj.user.mapper.HierarchyMapper;
import com.pj.user.pojo.Hierarchy;
import com.pj.user.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;

@Controller
@Api(value = "合作伙伴")
@RequestMapping(value = "/partner/details")
public class PartnerDetailsController extends BaseController {

    @Autowired
    private PartnerDetailsService partnerDetailsService;
    @Autowired(required = false)
    private HierarchyMapper hierarchyMapper;
    @Autowired
    private PartnerLinkmanService partnerLinkmanService;
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private EmailService emailService;


    public static final String [] fields = {"chineseName","chineseAbbreviation","englishName","englishAbbreviation","headingCode","code"};
    public static final String [] fieldName = {"中文名称","中文简称","英文名称","英文简称","纳税人识别码","代码"};
    public static final String [] fieldIndex = {"1","2","3","4","5","6"};

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
    	
	  for (int i = 0 ; i < fields.length ; i++) {
          boolean b = this.partnerDetailsService.verifyValueRepeat(partnerDetails.getId(), fields[i], TypeConversionUtils.selectFieldValueByName(partnerDetails, fields[i]),partnerDetails.getPId());
          if (!b) {
              return this.error(fieldIndex[i]);
          }
      }
        if(linkmans != null){
            JSONArray array = JSONArray.fromString(linkmans);
            List<PartnerLinkman> list = JSONArray.toList(array, PartnerLinkman.class);
            for (PartnerLinkman partnerLinkman : list) {
            	partnerLinkman.setDetailsId(partnerDetails.getId());
			}
            partnerDetails.setLinkmansList(list);
        }

        if(address != null){
            JSONArray array = JSONArray.fromString(address);
            List<PartnerAddress> list = JSONArray.toList(array, PartnerAddress.class);
            for (PartnerAddress partnerAddress : list) {
            	partnerAddress.setDetailsId(partnerDetails.getId());
			}
            
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
        List<PartnerLinkman> linkmanlist = new ArrayList<PartnerLinkman>();
        if(linkmans != null){
            JSONArray array = JSONArray.fromString(linkmans);
            linkmanlist = JSONArray.toList(array, PartnerLinkman.class);
            partnerDetails.setLinkmansList(linkmanlist);

        }
        if(address != null){
            JSONArray array = JSONArray.fromString(address);
            List<PartnerAddress> list = JSONArray.toList(array, PartnerAddress.class);
            partnerDetails.setAddressList(list);
        }
        for (int i = 0 ; i < fields.length ; i++) {
            boolean b = this.partnerDetailsService.verifyValueRepeat(partnerDetails.getId(), fields[i], TypeConversionUtils.selectFieldValueByName(partnerDetails, fields[i]),partnerDetails.getPId());
            if (!b) {
                return this.error(fieldIndex[i]);
            }
        }
        partnerDetails = (PartnerDetails) ObjectTrim.beanAttributeValueTrim(partnerDetails);
        try {
            this.partnerDetailsService.insertSelective(partnerDetails,getRequest(),email);
            // 校验手机号 发送邮件
            ThreadEmail thread =  new  ThreadEmail( partnerDetailsService ,authUserService, emailService,partnerLinkmanService);
            thread.checkPhoneSendEmail(request,linkmanlist, partnerDetails);
            return this.success();
        }catch (Exception e){
            return this.error();
        }


    }

    /**
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
                                     @ApiParam("pId") @RequestParam(name = "pId") Integer pId ,
                                     @ApiParam("id" ) @RequestParam(name = "id" , required = false) Integer id
    ){
        boolean flag = this.partnerDetailsService.verifyValueRepeat(id,fieldName,fieldValue,pId);
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
        boolean flag = this.partnerDetailsService.shiftPartnerDetailsFileByIds(id,email);
        if(!flag){
            return this.error("合作伙伴代码重复，请返回修改");
        }
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
        // 如果没有修改手机号则删除此条信息
        if(null!=partnerLinkman && partnerLinkmen.size()!=0){
            for(PartnerLinkman parlik:partnerLinkmen){
                if(parlik.getId().equals(partnerLinkman.getId())  ){
                    partnerLinkmen.remove(partnerLinkman);
                }
            }
            if(partnerLinkmen.remove(partnerLinkman)){

            }
        }
        if(null!=partnerLinkmen && partnerLinkmen.size()!=0 ){
            return this.success(true);
        }
        return this.success(false);
    }


    /**
     * @Description:
     * @author SevenBoy

     * @return java.lang.Object   
     * @throws
     * @Date 2018/1/3
     */
    @ApiOperation(value = "页面导出excel" ,httpMethod = "GET", response = Object.class)
    @RequestMapping(value = "/excel")
    public void parTnerDetailExcel(@ApiParam("name") @RequestParam(name = "name",required = false) String name ,
                                   @ApiParam("查看停用Partner") @RequestParam(name = "offPartner",required = false) Integer offPartner ,
                                   @ApiParam("分类") @RequestParam(name = "partnerCategory",required = false) String partnerCategory ,
                                   @ApiParam("查看黑名单Partner") @RequestParam(name = "blacklistPartner",required = false) Integer blacklistPartner
            , HttpServletRequest request , HttpServletResponse response) throws ParseException {
        List<PartnerDetails> list = this.partnerDetailsService.selectListByQuery(name,offPartner,blacklistPartner,partnerCategory);
        // 导出excel
        ExcelUtils excel = new ExcelUtils();
        excel.customerOutExcel(request,response,list ,partnerDetailsService);
    }




}
