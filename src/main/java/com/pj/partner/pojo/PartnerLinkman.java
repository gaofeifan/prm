package com.pj.partner.pojo;

import lombok.Data;

import java.io.Serializable;

public @Data
class PartnerLinkman implements Serializable {
    private Integer id;

    private String name;

    /**
     * 职责
     */
    private String obligation;

    /**
     * 职务
     */
    private String duty;

    /**
     * 部门
     */
    private String demp;

    /**
     * 固话
     */
    private String fixPhone;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 详情管理id
     */
    private Integer detailsId;

    private static final long serialVersionUID = 1L;



}