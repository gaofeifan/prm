package com.pj.user.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by SenevBoy on 2017/11/8.
 * 用户信用等级表
 */
@Entity
@Data
public class UserLevel {

    @Id //@id注意选择这个javax.persistence
    @GeneratedValue
    @ApiModelProperty(value = "序列号id", required = false)
    private  Integer  id;

    @ApiModelProperty(value = "用户信用等级", required = false)
    private  String   level;    // 用户信用等级

    @ApiModelProperty(value = "协议类型 1-协议 2-付款买单 3-签约在途 ", required = false)
    private  Integer   Protocol_type; // 协议类型

    @ApiModelProperty(value = "是否有效  默认 0 -  无效 ，  1 - 有效  ", required = false)
    private  Integer   effectiveness;  // 是否有效


}
