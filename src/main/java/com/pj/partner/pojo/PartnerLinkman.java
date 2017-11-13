package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

public @Data @Table(name="partner_linkman")
class PartnerLinkman implements Serializable {
    @ApiModelProperty(value = "联系人 id")
    @GeneratedValue(generator = "JDBC")
    @Id
    private Integer id;

    @Column
    @ApiModelProperty(value = "联系人  名称 ")
    private String name;

    /**
     * 职责
     */
    @Column
    @ApiModelProperty(value = "联系人   职责")
    private String obligation;

    /**
     * 职务
     */
    @Column
    @ApiModelProperty(value = "联系人   职务")
    private String duty;

    /**
     * 部门
     */
    @Column
    @ApiModelProperty(value = "联系人   部门")
    private String demp;

    /**
     * 固话
     */
    @Column
    @ApiModelProperty(value = "联系人   固话")
    private String fixPhone;

    /**
     * 电话
     */
    @Column
    @ApiModelProperty(value = "联系人   电话")
    private String phone;

    /**
     * 邮箱
     */
    @Column
    @ApiModelProperty(value = "联系人   邮箱")
    private String email;

    /**
     * 地址
     */
    @Column
    @ApiModelProperty(value = "联系人   地址")
    private String address;

    /**
     * 详情管理id
     */
    @Column
    @ApiModelProperty(value = "联系人   详情管理id")
    private Integer detailsId;

    private static final long serialVersionUID = 1L;





}