package com.pj.Aspect;
/**
 * Created by SenevBoy on 2017/11/10.
 */
public class BasicProperties {

    /**
     *  修改修改的字段
     */
    public static final String [] PartnerDetailsstatus_name = new String[]{"isBlacklist","isDisable","disableRemark" };
    public static final String [] PartnerDetailsstatus_paramVal  = new String[]{"是否黑名单","是否停用","停用备注" };
    // 用户信用等级
            public static  final  String[]  Basic_UserLevel_paramName ={"id",   "level",    "protocolType","effectiveness","mark","defaultQuota","defaultTtime"};
            public static  final  String[]  Basic_UserLevel_paramVal  ={"序列号","信用等级","协议类型",    "是否有效","备注","默认额度","默认期限"};
    // 层级位数
            public static  final  String[]  Basic_Operation_paramName ={"layerOne",   "layerTwo",    "layerThree","layerFour" };
            public static  final  String[]  Basic_Operation_paramVal  ={"第一层级",     "第二层级",       "第三层级",    "第四层级"};
    //联系地址

            public static final String Basic_address_paramName[] = {"id" ,"addressType","abbreviation","address","zipCode"};
            public static final String Basic_address_paramVal [] = {"序列号","地址类型","简称","地址","邮编"};
    // 联系人
            public static final String Basic_linkmanCN_paramName [] = {"id" ,"name","obligation","duty","demp","fixPhone","phone","email" ,"address"};
            public static final String Basic_linkmanCN_paramVal  [] = {"序列号","名称","职责","职务","部门","固话","电话","邮箱","地址"};

    //合作伙伴
            public static final String Basic_PartnerDeta_paramName [] = {"dirName" ,"id","code","mnemonicCode",
                    "chineseName","chineseAbbreviation","englishName","englishAbbreviation","financingCode",
                    "headingCode","receiverName","scopeBusiness","partnerCategory","isBlacklist","isDisable","disableRemark",
               /*外部客户*/
                     "wbkhCustomerClass","wbkhCreditRating","wbkhTypeCreditPeriod",
                     "wbkhInvoiceType","wbkhIsPayForAnother","wbkhPaymentTerm","wbkhPaidAmount","wbkhCreditPeriod","wbkhLineCredit","wbkhDepositBank","wbkhBankAccount","wbkhCompanyAddress","wbkhCompanyTel",
              /*互为代理*/
                     "hwdlTaxReceipt","hwdlTaxRate",  "hwdlOutputRate",/*"hwdlCreditRating","hwdlInvoiceType","hwdlTypeCreditPeriod","hwdlIsPayForAnother","hwdlPaymentTerm","hwdlPaidAmount","hwdlCreditPeriod","hwdlLineCredit","hwdlDepositBank","hwdlBankAccount","hwdlCompanyAddress","hwdlCompanyTel",*/
           /*海外代理*/
                   /*  "hwIsPayForAnother","hwPaymentTerm","hwPaidAmount","hwCreditRating","hwTypeCreditPeriod","hwCreditPeriod","hwLineCredit",*/
             /*干线运城*/
                     "gxcyrClassOfService",/*"gxcyrTaxReceipt","gxcyrTaxRate",
            *//*不可控供应商*//*
                      "bkkgysClassOfService","bkkgysTaxReceipt","bkkgysTaxRate",
            *//*延伸服务供应商*//*
                      "ysfwgysClassOfService","ysfwgysTaxReceipt","ysfwgysTaxRate",*/
            /*发货人*/
                      "sfhrIsConsignee","sfhrIsShipper","sfhrIsConsigneesAddress","sfhrConsigneeNation","sfhrConsigneeAddress","sfhrConsigneeContinent","sfhrConsigneeCity","sfhrConsigneePhone","sfhrShipperZipCode",
                        "sfhrShipperNation","sfhrShipperAddress","sfhrShipperContinent","sfhrShipperCity","sfhrShipperZipCode","sfhrShipperPhone"/*,*/
             /*结算对象*/
                       /* "jsdxDepositBank", "jsdxBankAccount", "jsdxCompan", "jsdxCompanyTel"*/};
            public static final String Basic_PartnerDeta_paramVal  [] = {"名称",    "ID","代码",  "助记码",
                     "中文全称","中文简称","英文全称","英文简称","财务代码",
                    "纳税人识别码","提醒接受者","业务范畴","合作伙伴分类","黑名单","停用","停用备注",
                    /*外部客户*/
                    "客户分类","信用等级","信用期限类型",
                    "开票类型","代垫","代垫期限(天)","代垫额度(万元)","信用期限(天)","信用额度(万元)","开户银行","银行账号","公司地址","公司电话",
                    /*互为代理*/
                    "进项税票","进项税率%","销项税率",
               /*海外代理*//*
                    "代垫","代垫期限(天)","代垫额度(万元)","信用等级*","信用期限类型*","信用期限(天)","信用额度(万元)",*/
                    /*干线运城*/
                    "服务类别",/*"进项税票","进项税率%",
                    "服务类别","进项税票","进项税率%",
                    "服务类别","进项税票","进项税率%",*/
                    /*收发货人*/
                    "收货人","发货人","与收货人地址相同","国家","地址","所属州","城市","邮编","电话",
                    "国家","地址","所属州","城市","邮编","电话"/*,
                    *//*结算对象*//*
                    "开户银行","银行账号","公司地址","公司电话"*/
            };




     //  手机号校验邮箱发送total信息 error信息
     //重复联系人
  public static final  String  total_repetition_telephone = " 以下提醒重复联系人（Owner）无效，请注意跟进。";
  public static final   String  error_repetition_telephone = "---重复联系人（Owner）无效发送给管理者的异常信息统计邮件发送失败  ： ";

  // 新增Partner
    public static final  String  total_The_new_Partner = "月 新增Partner如下，如有问题，请联系相应Owner.";
    public static final   String  error_The_new_Partner = "月新增Partner清单 邮件信息获取异常请检查 exception   :";

    //  签约在途告警邮件
    public static final  String  total_signing_In_Transit =  " 以下客户信用等级为签约在途，保存时间已经超过15/30个自然日，请注意跟进。";
    public static final   String  error_signing_In_Transit = "-- 发送给提醒接受者 的邮件发送失败 已存储并已转发通知管理员--";

    // 信用等级
    public static final  String  total_A_credit_rating =  " 以下客户信用等级为签约在途，保存时间已经超过15/30个自然日，请注意跟进。";
    public static final   String  error_A_credit_rating = "-- 发送给提醒接受者 的邮件发送失败 已存储并已转发通知管理员--";

    // total

    public static final  String  total_basic_data =   " 以下提醒接收者（Owner）无效，请注意跟进。";



    public static void main(String[] args) {
        System.out.println(Basic_PartnerDeta_paramName.length);
        System.out.println(Basic_PartnerDeta_paramVal.length);
    }
}
