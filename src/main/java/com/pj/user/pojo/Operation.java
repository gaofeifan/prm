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
@Data
@Table(name  = "log_operation")
public class Operation  implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "序列号id", required = false)
    private  Integer  id;


    @Column(name = "user_id")
    @ApiModelProperty(value = "登录ID    ", required = false)
    private  String   userId;

    @Column(name = "user_name")
    @ApiModelProperty(value = "姓名    ", required = false)
    private  String   userName;

    @Column(name = "company")
    @ApiModelProperty(value = "公司    ", required = false)
    private  String   company;

    @Column(name = "department")
    @ApiModelProperty(value = "部门    ", required = false)
    private  String   department;



    @Column(name = "jobs")
    @ApiModelProperty(value = "岗位    ", required = false)
    private  String   jobs;

    @Column(name = "create_date")
    @ApiModelProperty(value = "操作时间    ", required = false)
    private  String   createDate;

    @Column(name = "action")
    @ApiModelProperty(value = "操作日志    ", required = false)
    private  String   action;


}
