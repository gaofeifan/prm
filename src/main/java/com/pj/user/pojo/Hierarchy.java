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



    @Column(name = "layer_one")
    @ApiModelProperty(value = "第一层级    ", required = false)
    private  Integer   layerOne;

    @Column(name = "layer_two")
    @ApiModelProperty(value = "第二层级    ", required = false)
    private  Integer layerTwo  ;

    @Column(name = "layer_three")
    @ApiModelProperty(value = "第三层级    ", required = false)
    private  Integer  layerThree ;

    @Column(name = "layer_four")
    @ApiModelProperty(value = "第四层级     ", required = false)
    private  Integer   layerFour;



}
