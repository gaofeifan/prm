package com.pj.partner.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public  @Data
@Table(name="partner_details")  class PartnerDetailsShifFile  extends  BasicData{
    @GeneratedValue(generator = "JDBC")
    @Id
    private Integer id;

    @Column
    private String dirName;

    @Column
    private Integer pId;

    @Column
    private Integer isDelete;

    @Column
    private String code;

    @Column
    private String chineseAbbreviation;
}
