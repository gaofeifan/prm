package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

public @Data @Table(name="partner_address" )
class PartnerAddress implements Serializable {

    @GeneratedValue(generator = "JDBC")
    @Id
    @ApiModelProperty(value = "id" ,required = false)
    private Integer id;

    /**
     * 地址类型
     */
    @Column
    @ApiModelProperty(value = "联系地址  地址类型" ,required = false)
    private Integer addressType;

    /**
     * 简称
     */
    @Column
    @ApiModelProperty(value = "联系地址 简称" ,required = false)
    private String abbreviation;

    /**
     * 地址
     */
    @Column
    @ApiModelProperty(value = "联系地址 地址" ,required = false)
    private String address;

    /**
     * 邮编
     */
    @Column
    @ApiModelProperty(value = "联系地址 邮编" ,required = false)
    private String zipCode;

    @Column
    @ApiModelProperty(value = " detailsId" ,required = false)
    private Integer detailsId;

    private static final long serialVersionUID = 1L;


}