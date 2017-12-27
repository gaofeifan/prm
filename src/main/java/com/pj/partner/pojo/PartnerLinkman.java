package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

public @Data @Table(name="partner_linkman")
class PartnerLinkman implements Serializable  {
    @ApiModelProperty(value = "联系人 id" ,required = false)
    @GeneratedValue(generator = "JDBC")
    @Id
    private Integer id;

    @Column
    @ApiModelProperty(value = "联系人  名称 " ,required = false)
    private String name;

    /**
     * 职责
     */
    @Column
    @ApiModelProperty(value = "联系人   职责" ,required = false)
    private String obligation;

    /**
     * 职务
     */
    @Column
    @ApiModelProperty(value = "联系人   职务" ,required = false)
    private String duty;

    /**
     * 部门
     */
    @Column
    @ApiModelProperty(value = "联系人   部门" ,required = false)
    private String demp;

    /**
     * 固话
     */
    @Column
    @ApiModelProperty(value = "联系人   固话" ,required = false)
    private String fixPhone;

    /**
     * 电话
     */
    @Column
    @ApiModelProperty(value = "联系人   电话" ,required = false)
    private String phone;

    /**
     * 邮箱
     */
    @Column
    @ApiModelProperty(value = "联系人   邮箱" ,required = false)
    private String email;

    /**
     * 地址
     */
    @Column
    @ApiModelProperty(value = "联系人   地址" ,required = false)
    private String address;

    /**
     * 详情管理id
     */
    @Column
    @ApiModelProperty(value = "联系人   详情管理id" ,required = false)
    private Integer detailsId;

    @Column
    @ApiModelProperty(value = "qq" ,required = false)
    private String qq;

    @Column
    @ApiModelProperty(value = "微信" ,required = false)
    private String weChat;

    private static final long serialVersionUID = 1L;

    @Transient
    @ApiModelProperty(value = "联系人  合作伙伴中文全称-新维护重复名称" ,required = false)
    private String newchineseName;


    @Transient
    @ApiModelProperty(value = "联系人  合作伙伴中文全称-元数据被重复名称 " ,required = false)
    private String oldchineseName;

    @Transient
    @ApiModelProperty(value = "联系人  合作伙伴中文全称-接受者ID " ,required = false)
    private Integer receiverId;

}