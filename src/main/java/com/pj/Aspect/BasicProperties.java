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
//            public static  final  String[]  Basic_Operation_paramName ={"layerOne",   "layerTwo",    "layerThree","layerFour" };
//            public static  final  String[]  Basic_Operation_paramVal  ={"第一层级",     "第二层级",       "第三层级",    "第四层级"};
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
                       /* "jsdxDepositBank", "jsdxBankAccount", "jsdxCompan", "jsdxCompanyTel"*/
              /*新增 字段 2017-12-26 11:42:36 */
            ,"useQuota","aiTypeCreditPeriod","aiCreditPeriod","aeTypeCreditPeriod","aeCreditPeriod","siTypeCreditPeriod","siCreditPeriod"
            ,"seTypeCreditPeriod","seCreditPeriod","tiTypeCreditPeriod","tiCreditPeriod","teTypeCreditPeriod","teCreditPeriod"
            ,"oiTypeCreditPeriod","oiCreditPeriod","oeTypeCreditPeriod","oeCreditPeriod","itTypeCreditPeriod","itCreditPeriod"
            ,"ddnTypeCreditPeriod","ddnCreditPeriod","yyoxTypeCreditPeriod","yyoxCreditPeriod","IndustrialTypeCreditPeriod","IndustrialCreditPeriod",
            "profitsCenterName","shortName","profitsCenterId"
            };
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
            /*新增 字段 2017-12-26 11:42:36 */
                    ,"已用额度","AI 信用期限类型","AI 信用期限"," AE 信用期限类型"," AE 信用期限","SI 信用期限类型","SI 信用期限"
                    ,"SE 信用期限类型","SE 信用期限","TI 信用期限类型","TI 信用期限","TE 信用期限类型","TE 信用期限"
                    ,"OI 信用期限类型","OI 信用期限","OE 信用期限类型","OE 信用期限","IT 信用期限类型","IT 信用期限"
                    ,"DDN 信用期限类型","DDN 信用期限","YYOX 信用期限类型","YYOX 信用期限","Industrial 信用期限类型","Industrial 信用期限",
                    "利润中心名称","利润中心简称","利润中心ID"
            };



    // 导出excel 数组列
    public static final String[] excelData = { "ID","合作伙伴中文名","停用","黑名单","外部客户","互为代理","海外代理","干线承运人","不可控供应商","延伸服务供应商","结算对象"};

}