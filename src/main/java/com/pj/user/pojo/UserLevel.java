package com.pj.user.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by SenevBoy on 2017/11/8.
 * 用户信用等级表
 */
@Table(name  ="user_level")
@Data
public class UserLevel implements Serializable{

    @Id //@id注意选择这个javax.persistence
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "序列号id", required = false)
    private  Integer  id;

    @Column(name = "protocol_type")
    @ApiModelProperty(value = "协议类型 ", required = false)
    private  String   protocolType; // 协议类型  1-协议/保函 2-付款买单 3-签约在途

    @Column(name = "effectiveness")
    @ApiModelProperty(value = "是否有效  默认 0 -  无效 ，  1 - 有效  ", required = false)
    private  Integer   effectiveness;  // 是否有效


    @Column(name = "level")
    @ApiModelProperty(value = " 信用等级    ", required = false)
    private String level;


    @Column(name = "mark")
    @ApiModelProperty(value = " 备注  ", required = false)
    private String mark;


    @Column(name = "default_quota")
    @ApiModelProperty(value = " 默认额度  ", required = false)
    private String defaultQuota;


    @Column(name = "default_time")
    @ApiModelProperty(value = " 默认期限  ", required = false)
    private String defaultTtime;

}
