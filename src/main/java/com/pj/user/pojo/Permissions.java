package com.pj.user.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Data
@Table(name = "log_permissions")
public class Permissions  implements Serializable{


    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "序列号id", required = false)
    private  Integer  id;


    @Column(name = "type")
    @ApiModelProperty(value = "操作    ", required = false)
    private  String  type;



    @Column(name = "create_date")
    @ApiModelProperty(value = "创建时间    ", required = false)
    private Date createDate;


    @Column(name = "involves_user")
    @ApiModelProperty(value = "涉及用户    ", required = false)
    private  String   involvesUser;

    @Column(name = "involves_permissions")
    @ApiModelProperty(value = "涉及权限    ", required = false)
    private  String   involvesPermissions;




}
