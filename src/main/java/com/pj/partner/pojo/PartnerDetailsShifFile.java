package com.pj.partner.pojo;

import lombok.Data;

import javax.persistence.Table;

public  @Data
@Table(name="partner_details")  class PartnerDetailsShifFile {

    private Integer id;

    private String dirName;

    private Integer pId;

    private Integer isDelete;

    private String chineseAbbreviation;
}
