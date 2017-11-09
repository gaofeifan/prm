package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


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
    private Integer wbkhCreditRating;

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

    @Column
    @ApiModelProperty(value = "互为代理  客户分类")
    private String hwdlCustomerClass;

    /**
     * 互为代理 信用等级
     */
    @Column
    @ApiModelProperty(value = "互为代理 信用等级")
    private Integer hwdlCreditRating;

    /**
     * 互为代理  开票类型
     */
    @Column
    @ApiModelProperty(value = "互为代理  开票类型")
    private Integer hwdlInvoiceType;

    /**
     * 互为代理 信用期限类型
     */
    @Column
    @ApiModelProperty(value = "互为代理 信用期限类型")
    private Integer hwdlTypeCreditPeriod;

    /**
     * 互为代理  信用期限
     */
    @Column
    @ApiModelProperty(value = "互为代理  信用期限")
    private BigDecimal hwdlCreditPeriod;

    /**
     * 互为代理  信用额度
     */
    @Column
    @ApiModelProperty(value = "互为代理  信用额度")
    private BigDecimal hwdlLineCredit;

    /**
     * 互为代理 开户银行
     */
    @Column
    @ApiModelProperty(value = "互为代理 开户银行")
    private String hwdlDepositBank;

    /**
     * 互为代理 银行账号
     */
    @Column
    @ApiModelProperty(value = "互为代理 银行账号")
    private String hwdlBankAccount;

    /**
     * 互为代理  公司电话
     */
    @Column
    @ApiModelProperty(value = "互为代理  公司电话")
    private String hwdlCompanyTel;

    /**
     * 互为代理 公司地址
     */
    @Column
    @ApiModelProperty(value = "互为代理 公司地址")
    private String hwdlCompanyAddress;

    /**
     * 互为代理 是否代垫 0 否 1是
     */
    @Column
    @ApiModelProperty(value = "互为代理 是否代垫 0 否 1是")
    private Integer hwdlIsPayForAnother;

    /**
     * 互为代理 代付期限
     */
    @Column
    @ApiModelProperty(value = "互为代理 代付期限")
    private BigDecimal hwdlPaymentTerm;

    /**
     * 互为代理 代垫额度
     */
    @Column
    @ApiModelProperty(value = "互为代理 代垫额度")
    private BigDecimal hwdlPaidAmount;

    /**
     * 海外代理   是否代垫 0 否 1是
     */
    @Column
    @ApiModelProperty(value = "海外代理   是否代垫 0 否 1是")
    private Integer hwIsPayForAnother;

    /**
     * 海外代理  代付期限
     */
    @Column
    @ApiModelProperty(value = "海外代理  代付期限")
    private BigDecimal hwPaymentTerm;

    /**
     * 海外代理  代垫额度
     */
    @Column
    @ApiModelProperty(value = "海外代理  代垫额度")
    private BigDecimal hwPaidAmount;

    /**
     * 海外代理  信用等级
     */
    @Column
    @ApiModelProperty(value = "海外代理  信用等级")
    private Integer hwCreditRating;

    /**
     * 海外代理  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "海外代理  信用期限类型")
    private Integer hwTypeCreditPeriod;

    /**
     * 海外代理 信用期限
     */
    @Column
    @ApiModelProperty(value = "海外代理 信用期限")
    private BigDecimal hwCreditPeriod;

    /**
     * 海外代理   信用额度
     */
    @Column
    @ApiModelProperty(value = "海外代理   信用额度")
    private BigDecimal hwLineCredit;

    /**
     * 干线承运人 服务类别
     */
    @Column
    @ApiModelProperty(value = "干线承运人 服务类别")
    private String gxcyrClassOfService;

    /**
     * 干线承运人 进项税票
     */
    @Column
    @ApiModelProperty(value = "干线承运人 进项税票")
    private Integer gxcyrTaxReceipt;

    /**
     * 干线承运人 进项税率
     */
    @Column
    @ApiModelProperty(value = "干线承运人 进项税率")
    private BigDecimal gxcyrTaxRate;

    /**
     * 不可控供应商   服务类别
     */
    @Column
    @ApiModelProperty(value = "不可控供应商   服务类别")
    private String bkkgysClassOfService;

    /**
     * 不可控供应商  进项税票
     */
    @Column
    @ApiModelProperty(value = "不可控供应商  进项税票")
    private Integer bkkgysTaxReceipt;

    /**
     * 不可控供应商 进项税率
     */
    @Column
    @ApiModelProperty(value = "不可控供应商 进项税率")
    private BigDecimal bkkgysTaxRate;

    /**
     * 延伸服务供应商  服务类别
     */
    @Column
    @ApiModelProperty(value = "延伸服务供应商  服务类别")
    private String ysfwgysClassOfService;

    /**
     * 延伸服务供应商  进项税票
     */
    @Column
    @ApiModelProperty(value = " 延伸服务供应商  进项税票")
    private Integer ysfwgysTaxReceipt;

    /**
     * 延伸服务供应商  进项税率
     */
    @Column
    @ApiModelProperty(value = "延伸服务供应商  进项税率")
    private BigDecimal ysfwgysTaxRate;

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
     * 结算对象 开户银行
     */
    @Column
    @ApiModelProperty(value = "结算对象 开户银行")
    private String jsdxDepositBank;

    /**
     * 结算对象  银行账号
     */
    @Column
    @ApiModelProperty(value = "结算对象  银行账号")
    private String jsdxBankAccount;

    /**
     * 结算对象 公司电话
     */
    @Column
    @ApiModelProperty(value = " 结算对象 公司电话")
    private String jsdxCompanyTel;

    /**
     * 结算对象 公司地址
     */
    @Column
    @ApiModelProperty(value = "结算对象 公司地址")
    private String jsdxCompanyAddress;

    /**
     * 是否删除  0否 1 是
     */
    @Column
    @ApiModelProperty(value = "是否删除  0否 1 是")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;


}