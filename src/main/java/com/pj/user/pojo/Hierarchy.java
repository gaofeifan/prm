package com.pj.user.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Table(name  ="hierarchy")
@Data
public class Hierarchy implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "序列号id", required = false)
    private  Integer  id;



    @Column(name = "layer_name")
    @ApiModelProperty(value = "等級名称    ", required = false)
    private  String   layerName;

    @Column(name = "layer_number")
    @ApiModelProperty(value = "层级数量    ", required = false)
    private  Integer   layerNumber;

}
