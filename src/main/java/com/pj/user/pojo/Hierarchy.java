package com.pj.user.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Table(name  ="hierarchy")
@Data
public class Hierarchy {

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "序列号id", required = false)
    private  Integer  id;



    @Column(name = "layer")
    @ApiModelProperty(value = "层级    ", required = false)
    private  String   layer;

    @Column(name = "num")
    @ApiModelProperty(value = "位数    ", required = false)
    private  Integer   num;


}
