package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/***
 * @ClassName: BasicData
 * @Description: 新需求新增字段 2017年12月26日10:32:52 创建人 刘哲
 * @author SenevBoy
 * @date 2017/12/25 18:04
 **/
@Data
public class BasicData {
    //  -----开始-- createBY SevenboyLiu 2017年12月25日18:01:01 -新增字段 销项税率 ， 已用额度 ， 信用期限类型和信用期限：每个业务范畴对应一组，勾选业务范畴后启用
    /**
     * createBy SevenBoyLiu 2017年12月25日17:44:16
     * 销项税率
     */
    @Column
    @ApiModelProperty(value = " 销项税率" ,required = false)
    private BigDecimal hwdlOutputRate;

    /**
     * createBy SevenBoyLiu 2017年12月25日17:44:16
     * 销项税率
     */
    @Column
    @ApiModelProperty(value = " 已用额度" ,required = false)
    private BigDecimal useQuota;




      // 外部客户 信用期限类型


    @Column
    @ApiModelProperty(value = " 外部客户 信用期限类型" ,required = false)
    private String wbkhTypeCreditPeriod;


 // 外部客户 信用期限


    @Column
    @ApiModelProperty(value = "外部客户 信用期限" ,required = false)
    private BigDecimal wbkhCreditPeriod;


    /**
     * AI 客户 信用期限类型
     */
    @Column
    @ApiModelProperty(value = " AI 信用期限类型" ,required = false)
    private String aiTypeCreditPeriod;  //wbkhTypeCreditPeriod

    /**
     * AI 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "AI 信用期限" ,required = false)
    private BigDecimal aiCreditPeriod; //wbkhCreditPeriod

    /**
     * AE 客户 信用期限类型
     */
    @Column
    @ApiModelProperty(value = " AE 信用期限类型" ,required = false)
    private String aeTypeCreditPeriod;  //wbkhTypeCreditPeriod

    /**
     * AE 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "AE 信用期限" ,required = false)
    private BigDecimal aeCreditPeriod; //wbkhCreditPeriod


    /**
     * SI 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "SI 信用期限类型" ,required = false)
    private String siTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * SI 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "SI 信用期限" ,required = false)
    private BigDecimal siCreditPeriod; //wbkhCreditPeriod



    /**
     * SE 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "SE 信用期限类型" ,required = false)
    private String seTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * SE 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "SE 信用期限" ,required = false)
    private BigDecimal seCreditPeriod; //wbkhCreditPeriod


    /**
     * TI 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "TI 信用期限类型" ,required = false)
    private String tiTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * TI 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "TI 信用期限" ,required = false)
    private BigDecimal tiCreditPeriod; //wbkhCreditPeriod


    /**
     * TE 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "TE 信用期限类型" ,required = false)
    private String teTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * TE 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "TE 信用期限" ,required = false)
    private BigDecimal teCreditPeriod; //wbkhCreditPeriod


    /**
     * OI 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "OI 信用期限类型" ,required = false)
    private String oiTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * OI 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "OI 信用期限" ,required = false)
    private BigDecimal oiCreditPeriod; //wbkhCreditPeriod


    /**
     * OE 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "OE 信用期限类型" ,required = false)
    private String oeTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * OE 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "OE 信用期限" ,required = false)
    private BigDecimal oeCreditPeriod; //wbkhCreditPeriod


    /**
     * IT 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "IT 信用期限类型" ,required = false)
    private String itTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * IT 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "IT 信用期限" ,required = false)
    private BigDecimal itCreditPeriod; //wbkhCreditPeriod


    /**
     * DDN 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "DDN 信用期限类型" ,required = false)
    private String ddnTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * DDN 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "DDN 信用期限" ,required = false)
    private BigDecimal ddnCreditPeriod; //wbkhCreditPeriod


    /**
     * YYOX 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "YYOX 信用期限类型" ,required = false)
    private String yyoxTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * YYOX 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "YYOX 信用期限" ,required = false)
    private BigDecimal yyoxCreditPeriod; //wbkhCreditPeriod


    /**
     * Industrial 客户  信用期限类型
     */
    @Column
    @ApiModelProperty(value = "Industrial 信用期限类型" ,required = false)
    private String IndustrialTypeCreditPeriod; //wbkhCreditPeriod


    /**
     * Industrial 客户 信用期限
     */
    @Column
    @ApiModelProperty(value = "Industrial 信用期限" ,required = false)
    private BigDecimal IndustrialCreditPeriod; //wbkhCreditPeriod

// --------------------结束-------------------------------------------------
}
