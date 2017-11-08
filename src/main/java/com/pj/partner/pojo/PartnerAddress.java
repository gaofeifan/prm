package com.pj.partner.pojo;

import lombok.Data;

import java.io.Serializable;

public @Data
class PartnerAddress implements Serializable {
    private Integer id;

    /**
     * 地址类型
     */
    private Integer addressType;

    /**
     * 简称
     */
    private String abbreviation;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zipCode;

    private Integer detailsId;

    private static final long serialVersionUID = 1L;


}