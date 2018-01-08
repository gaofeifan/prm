package com.pj.partner.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public @Data @Table(name="partner_details") class PartnerDetails extends BasicData implements Serializable {
    @GeneratedValue(generator = "JDBC")
    @Id
    @ApiModelProperty(value = "id" ,required = false)
    private Integer id;

    /**
     * 目录或文件名称
     */
    @Column
    @ApiModelProperty(value = "目录或文件名称" ,required = false)
    private String dirName;

    /**
     * 代码
     */
    @Column
    @ApiModelProperty(value = "代码" ,required = false)
    private String code;


    /**
     * 助记码
     */
    @Column
    @ApiModelProperty(value = "助记码" ,required = false)
    private String mnemonicCode;

    /**
     * 中文名称
     */
    @Column
    @ApiModelProperty(value = "中文名称" ,required = false)
    private String chineseName;

    /**
     * 中文简称
     */
    @Column
    @ApiModelProperty(value = "中文简称" ,required = false)
    private String chineseAbbreviation;

    /**
     * 识别码
     */
    @Column
    @ApiModelProperty(value = "识别码" ,required = false)
    private String headingCode;

    /**
     * 英文名称
     */
    @Column
    @ApiModelProperty(value = "英文名称" ,required = false)
    private String englishName;

    /**
     * 英文简称
     */
    @Column
    @ApiModelProperty(value = "英文简称" ,required = false)
    private String englishAbbreviation;

    /**
     *
     * 财务代码
     *
     */
    @Column
    @ApiModelProperty(value = "财务代码" ,required = false)
    private String financingCode;

    /**
     * 接受者id
     */
    @Column
    @ApiModelProperty(value = "接受者id" ,required = false)
    private Integer receiverId;

    /**
     * 接受者名称
     */
    @Column
    @ApiModelProperty(value = "接受者名称" ,required = false)
    private String receiverName;

    /**
     * 业务范畴
     */
    @Column
    @ApiModelProperty(value = "业务范畴" ,required = false)
    private String scopeBusiness;

    /**
     * 合作伙伴f分类
     */
    @Column
    @ApiModelProperty(value = "合作伙伴分类" ,required = false)
    private String partnerCategory;

    /**
     * 是否是黑名单  0否 1 是
     */
    @Column
    @ApiModelProperty(value = "是否是黑名单  0否 1 是" ,required = false)
    private Integer isBlacklist;

    /**
     * 是否停用 0否 1是
     */
    @Column
    @ApiModelProperty(value = "是否停用 0否 1是" ,required = false)
    private Integer isDisable;

    /**
     * 停用备注
     */
    @Column
    @ApiModelProperty(value = "停用备注" ,required = false)
    private String disableRemark;

    /**
     * 外部客户 客户分类
     */
    @Column
    @ApiModelProperty(value = "外部客户 客户分类" ,required = false)
    private String wbkhCustomerClass;


    /**
     * 外部客户 信用等级
     */
    @Column
    @ApiModelProperty(value = "外部客户 信用等级" ,required = false)
    private String wbkhCreditRating;

    /**
     * 外部客户 开票类型
     */
    @Column
    @ApiModelProperty(value = "外部客户 开票类型" ,required = false)
    private String wbkhInvoiceType;


    /**
     * 外部客户 信用额度
     */
    @Column
    @ApiModelProperty(value = "外部客户 信用额度" ,required = false)
    private BigDecimal wbkhLineCredit;

    /**
     * 外部客户 开户银行
     */
    @Column
    @ApiModelProperty(value = "外部客户 开户银行" ,required = false)
    private String wbkhDepositBank;

    /**
     * 外部客户 银行账号
     */
    @Column
    @ApiModelProperty(value = "外部客户 银行账号" ,required = false)
    private String wbkhBankAccount;

    /**
     * 外部客户 公司电话
     */
    @Column
    @ApiModelProperty(value = "外部客户 公司电话" ,required = false)
        private String wbkhCompanyTel;

    /**
     * 外部客户 公司地址
     */
    @Column
    @ApiModelProperty(value = "外部客户 公司地址" ,required = false)
    private String wbkhCompanyAddress;

    /**
     * 外部客户 是否代垫 0 否 1是
     */
    @Column
    @ApiModelProperty(value = "外部客户 是否代垫 0 否 1是" ,required = false)
    private Integer wbkhIsPayForAnother;

    /**
     * 外部客户 代付期限
     */
    @Column
    @ApiModelProperty(value = "外部客户 代付期限" ,required = false)
    private BigDecimal wbkhPaymentTerm;

    /**
     * 外部客户 代垫额度
     */
    @Column
    @ApiModelProperty(value = "外部客户 代垫额度" ,required = false)
    private BigDecimal wbkhPaidAmount;

    /**
     * 互为代理 进项税票
     */
    @Column
    @ApiModelProperty(value = "互为代理 进项税票" ,required = false)
    private String hwdlTaxReceipt;

    /**
     * 互为代理  进项税率
     */
    @Column
    @ApiModelProperty(value = "互为代理  进项税率" ,required = false)
    private BigDecimal hwdlTaxRate;


    /**
     * 干线承运人 服务类别
     */
    @Column
    @ApiModelProperty(value = "干线承运人 服务类别" ,required = false)
    private String gxcyrClassOfService;

    /**
     * 收发货人 是否是收货人 0 否 1 是
     */
    @Column
    @ApiModelProperty(value = "收发货人 是否是收货人 0 否 1 是" ,required = false)
    private Integer sfhrIsConsignee;

    /**
     * 收发货人 是否是发货人 0否 1 是
     */
    @Column
    @ApiModelProperty(value = "收发货人 是否是发货人 0否 1 是" ,required = false)
    private Integer sfhrIsShipper;

    /**
     * 与收货人地址相同
     */
    @Column
    @ApiModelProperty(value = "与收货人地址相同" ,required = false)
    private Integer sfhrIsConsigneesAddress;

    /**
     * 收货人国家
     */
    @Column
    @ApiModelProperty(value = "收货人国家" ,required = false)
    private String sfhrConsigneeNation;

    /**
     * 收发货人 收货人 所属洲
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人 所属洲" ,required = false)
    private String sfhrConsigneeContinent;

    /**
     * 收发货人 收货人 市
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人 市" ,required = false)
    private String sfhrConsigneeCity;

    /**
     * 收发货人 收货人电话
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人电话" ,required = false)
    private String sfhrConsigneePhone;

    /**
     * 收发货人 收货人地址
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人地址" ,required = false)
    private String sfhrConsigneeAddress;

    /**
     * 收发货人 发货人国家
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人国家" ,required = false)
    private String sfhrShipperNation;

    /**
     * 收发货人 发货人洲
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人洲" ,required = false)
    private String sfhrShipperContinent;

    /**
     * 收发货人 发货人市
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人市" ,required = false)
    private String sfhrShipperCity;

    /**
     * 收发货人 发货人电话
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人电话" ,required = false)
    private String sfhrShipperPhone;

    /**
     * 收发货人 发货人邮编
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人邮编" ,required = false)
    private String sfhrShipperZipCode;

    /**
     * 收发货人 发货人地址
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人地址" ,required = false)
    private String sfhrShipperAddress;

    /**
     * 收发货人 收货人邮编
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人邮编" ,required = false)
    private String sfhrConsigneeZipCode;

    /**
     *  是否是目录
     */
    @Column
    @ApiModelProperty(value = "是否是目录" ,required = false)
    private Integer isDir;

    /**
     *创建时间
     */
    @Column
    @ApiModelProperty(value = "创建时间" ,required = false)
    private Date createDate;


    /**
     * 是否删除  0否 1 是
     */
    @Column
    @ApiModelProperty(value = "是否删除  0否 1 是" ,required = false)
    private Integer isDelete;

    /**
     * 父级id
     */
    @Column
    @ApiModelProperty(value = "父id" ,required = false)
    private Integer pId;

    /**
     * 公司id
     */
    @Column
    @ApiModelProperty(value = "公司id" ,required = false)
    private String companyId;

    /**
     * 公司名称
     */
    @Column
    @ApiModelProperty(value = "公司名称" ,required = false)
    private String  companyName;

    @Transient
    @ApiModelProperty(value = "联系地址 格式  [{'id':'1','addressType':'地址类型','abbreviation':'简称','address':'地址','zipCode':'邮编'},{'id':'2','addressType':'地址类型','abbreviation':'简称','address':'地址','zipCode':'邮编'}]" ,required = false)
    private List<PartnerAddress> addressList;

    @Transient
    @ApiModelProperty(value = "联系人 格式[{'id':'0','name':'名称','obligation':'职责','duty':'职务','demp':'部门','fixPhone':'固话','phone':'电话','email':'邮箱','address':'地址'},{'id':'0','name':'名称','obligation':'职责','duty':'职务','demp':'部门','fixPhone':'固话','phone':'电话','email':'邮箱','address':'地址'}]" ,required = false)
    private List<PartnerLinkman> linkmansList;

    private static final long serialVersionUID = 1L;

    @Transient
    @ApiModelProperty(value = "合作伙伴分类" ,required = false)
    private String[]  partnerCategorys;
    @Transient
    @ApiModelProperty(value = "业务范畴" ,required = false)
    private String[] scopeBusinesss;
    @Transient
    @ApiModelProperty(value = "服务类别" ,required = false)


    private String[] gxcyrClassOfServices;
    @Transient
    @ApiModelProperty(value = "客户分类" ,required = false)
    private String[] wbkhCustomerClasss;
    @Transient
    @ApiModelProperty(value = "总长度code" ,required = false)
    private String codes;

    /**
     * 本信息第几层级
     */
    @Transient
    @ApiModelProperty(value = "仅参与excel导出" ,required = false)
    private Integer hierarchy;
    public String[] getScopeBusinesss() {
        if(null!=scopeBusiness){
            return scopeBusiness.split(",");
        }else{
            return null;
        }
    }

     public String[] getPartnerCategorys(){
         if(null!=partnerCategory){
             return partnerCategory.split(",");
         }else{
             return null;
         }
    }

    public String[] getGxcyrClassOfServices() {
        if(null!=gxcyrClassOfService){
            return gxcyrClassOfService.split("," );
        }else{
            return null;
        }
    }

    public String[] getWbkhCustomerClasss() {
        if(null!=wbkhCustomerClass){
            return wbkhCustomerClass.split(",");
        }else{
            return null;
        }
    }
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8" )
    public Date getCreateDate() {
        return createDate;
    }


}