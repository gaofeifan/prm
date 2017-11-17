package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public @Data @Table(name="partner_details") class PartnerDetails implements Serializable {
    @GeneratedValue(generator = "JDBC")
    @Id
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 目录或文件名称
     */
    @Column
    @ApiModelProperty(value = "目录或文件名称")
    private String dirName;

    /**
     * 代码
     */
    @Column
    @ApiModelProperty(value = "代码")
    private String code;

    /**
     * 助记码
     */
    @Column
    @ApiModelProperty(value = "助记码")
    private String mnemonicCode;

    /**
     * 中文名称
     */
    @Column
    @ApiModelProperty(value = "中文名称")
    private String chineseName;

    /**
     * 中文简称
     */
    @Column
    @ApiModelProperty(value = "中文简称")
    private String chineseAbbreviation;

    /**
     * 识别码
     */
    @Column
    @ApiModelProperty(value = "识别码")
    private String headingCode;

    /**
     * 英文名称
     */
    @Column
    @ApiModelProperty(value = "英文名称")
    private String englishName;

    /**
     * 英文简称
     */
    @Column
    @ApiModelProperty(value = "英文简称")
    private String englishAbbreviation;

    /**
     * 财务代码
     */
    @Column
    @ApiModelProperty(value = "财务代码")
    private String financingCode;

    /**
     * 接受者id
     */
    @Column
    @ApiModelProperty(value = "接受者id")
    private Integer receiverId;

    /**
     * 接受者名称
     */
    @Column
    @ApiModelProperty(value = "接受者名称")
    private String receiverName;

    /**
     * 业务范畴
     */
    @Column
    @ApiModelProperty(value = "业务范畴")
    private String scopeBusiness;

    /**
     * 合作伙伴f分类
     */
    @Column
    @ApiModelProperty(value = "合作伙伴分类")
    private String partnerCategory;

    /**
     * 是否是黑名单  0否 1 是
     */
    @Column
    @ApiModelProperty(value = "是否是黑名单  0否 1 是")
    private Integer isBlacklist;

    /**
     * 是否停用 0否 1是
     */
    @Column
    @ApiModelProperty(value = "是否停用 0否 1是")
    private Integer isDisable;

    /**
     * 停用备注
     */
    @Column
    @ApiModelProperty(value = "停用备注")
    private String disableRemark;

    /**
     * 外部客户 客户分类
     */
    @Column
    @ApiModelProperty(value = "外部客户 客户分类")
    private String wbkhCustomerClass;

    /**
     * 外部客户 信用等级
     */
    @Column
    @ApiModelProperty(value = "外部客户 信用等级")
    private String wbkhCreditRating;

    /**
     * 外部客户 开票类型
     */
    @Column
    @ApiModelProperty(value = "外部客户 开票类型")
    private Integer wbkhInvoiceType;

    /**
     * 外部客户 信用期限类型
     */
    @Column
    @ApiModelProperty(value = " 外部客户 信用期限类型")
    private Integer wbkhTypeCreditPeriod;

    /**
     * 外部客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "外部客户 信用期限")
    private BigDecimal wbkhCreditPeriod;

    /**
     * 外部客户 信用额度
     */
    @Column
    @ApiModelProperty(value = "外部客户 信用额度")
    private BigDecimal wbkhLineCredit;

    /**
     * 外部客户 开户银行
     */
    @Column
    @ApiModelProperty(value = "外部客户 开户银行")
    private String wbkhDepositBank;

    /**
     * 外部客户 银行账号
     */
    @Column
    @ApiModelProperty(value = "外部客户 银行账号")
    private String wbkhBankAccount;

    /**
     * 外部客户 公司电话
     */
    @Column
    @ApiModelProperty(value = "外部客户 公司电话")
    private String wbkhCompanyTel;

    /**
     * 外部客户 公司地址
     */
    @Column
    @ApiModelProperty(value = "外部客户 公司地址")
    private String wbkhCompanyAddress;

    /**
     * 外部客户 是否代垫 0 否 1是
     */
    @Column
    @ApiModelProperty(value = "外部客户 是否代垫 0 否 1是")
    private Integer wbkhIsPayForAnother;

    /**
     * 外部客户 代付期限
     */
    @Column
    @ApiModelProperty(value = "外部客户 代付期限")
    private BigDecimal wbkhPaymentTerm;

    /**
     * 外部客户 代垫额度
     */
    @Column
    @ApiModelProperty(value = "外部客户 代垫额度")
    private BigDecimal wbkhPaidAmount;

    /**
     * 互为代理 进项税票
     */
    @Column
    @ApiModelProperty(value = "互为代理 进项税票")
    private Integer hwdlTaxReceipt;

    /**
     * 互为代理  进项税率
     */
    @Column
    @ApiModelProperty(value = "互为代理  进项税率")
    private BigDecimal hwdlTaxRate;


    /**
     * 干线承运人 服务类别
     */
    @Column
    @ApiModelProperty(value = "干线承运人 服务类别")
    private String gxcyrClassOfService;

    /**
     * 收发货人 是否是收货人 0 否 1 是
     */
    @Column
    @ApiModelProperty(value = "收发货人 是否是收货人 0 否 1 是")
    private Integer sfhrIsConsignee;

    /**
     * 收发货人 是否是发货人 0否 1 是
     */
    @Column
    @ApiModelProperty(value = "收发货人 是否是发货人 0否 1 是")
    private Integer sfhrIsShipper;

    /**
     * 与收货人地址相同
     */
    @Column
    @ApiModelProperty(value = "与收货人地址相同")
    private Integer sfhrIsConsigneesAddress;

    /**
     * 收货人国家
     */
    @Column
    @ApiModelProperty(value = "收货人国家")
    private String sfhrConsigneeNation;

    /**
     * 收发货人 收货人 所属洲
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人 所属洲")
    private String sfhrConsigneeContinent;

    /**
     * 收发货人 收货人 市
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人 市")
    private String sfhrConsigneeCity;

    /**
     * 收发货人 收货人电话
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人电话")
    private String sfhrConsigneePhone;

    /**
     * 收发货人 收货人地址
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人地址")
    private String sfhrConsigneeAddress;

    /**
     * 收发货人 发货人国家
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人国家")
    private String sfhrShipperNation;

    /**
     * 收发货人 发货人洲
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人洲")
    private String sfhrShipperContinent;

    /**
     * 收发货人 发货人市
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人市")
    private String sfhrShipperCity;

    /**
     * 收发货人 发货人电话
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人电话")
    private String sfhrShipperPhone;

    /**
     * 收发货人 发货人邮编
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人邮编")
    private String sfhrShipperZipCode;

    /**
     * 收发货人 发货人地址
     */
    @Column
    @ApiModelProperty(value = "收发货人 发货人地址")
    private String sfhrShipperAddress;

    /**
     * 收发货人 收货人邮编
     */
    @Column
    @ApiModelProperty(value = "收发货人 收货人邮编")
    private String sfhrConsigneeZipCode;

    /**
     *  是否是目录
     */
    @Column
    @ApiModelProperty(value = "是否是目录")
    private Integer isDir;

    /**
     *创建时间
     */
    @Column
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 是否删除  0否 1 是
     */
    @Column
    @ApiModelProperty(value = "是否删除  0否 1 是")
    private Integer isDelete;

    /**
     * 是否删除  0否 1 是
     */
    @Column
    @ApiModelProperty(value = "父id")
    private Integer pId;

    @Transient
    private List<PartnerAddress> address;

    @Transient
    private List<PartnerLinkman> linkmans;

    private static final long serialVersionUID = 1L;

/*    @Transient
    @ApiModelProperty(value = "合作伙伴分类")
    private String[]  partnerCategorys;*/
    @Transient
    @ApiModelProperty(value = "业务范畴")
    private String[] scopeBusinesss;
    @Transient
    @ApiModelProperty(value = "服务类别")
    private String[] gxcyrClassOfServices;
    @Transient
    @ApiModelProperty(value = "客户分类")
    private String[] wbkhCustomerClasss;

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
            return gxcyrClassOfService.split(",");
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
}