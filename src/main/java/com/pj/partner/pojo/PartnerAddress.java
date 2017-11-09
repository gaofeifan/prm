package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

public @Data @Table(name="partner_address")
class PartnerAddress implements Serializable {

    @GeneratedValue(generator = "JDBC")
    @Id
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 地址类型
     */
    @Column
    @ApiModelProperty(value = "联系地址  地址类型")
    private Integer addressType;

    /**
     * 简称
     */
    @Column
    @ApiModelProperty(value = "联系地址 简称")
    private String abbreviation;

    /**
     * 地址
     */
    @Column
    @ApiModelProperty(value = "联系地址 地址")
    private String address;

    /**
     * 邮编
     */
    @Column
    @ApiModelProperty(value = "联系地址 邮编")
    private String zipCode;

    @Column
    @ApiModelProperty(value = "联系地址 detailsId")
    private Integer detailsId;

    private static final long serialVersionUID = 1L;


}