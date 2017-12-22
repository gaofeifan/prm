package com.pj.partner.pojo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;

/***
 * @ClassName: BasicEmail
 * @Description: (这里用一句话描述这个类的作用)
 * @author SenevBoy
 * @date 2017/12/22 17:40   
 **/
public class BasicEmail implements Serializable{
    @Column
    @ApiModelProperty(value = "联系人  名称-新维护 " ,required = false)
    private String newname;
}
