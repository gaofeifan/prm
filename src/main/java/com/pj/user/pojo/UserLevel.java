package com.pj.user.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.apache.xpath.operations.String;

import javax.persistence.*;

/**
 * Created by SenevBoy on 2017/11/8.
 * 用户信用等级表
 */
@Table(name  ="user_level")
@Data
public class UserLevel {

    @Id //@id注意选择这个javax.persistence
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "序列号id", required = false)
    private  Integer  id;

    @Column(name = "protocol_type")
    @ApiModelProperty(value = "协议类型 1-协议 2-付款买单 3-签约在途 ", required = false)
    private  Integer   protocolType; // 协议类型

    @Column(name = "effectiveness")
    @ApiModelProperty(value = "是否有效  默认 0 -  无效 ，  1 - 有效  ", required = false)
    private  Integer   effectiveness;  // 是否有效


}
