package com.pj.auth.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/11/8.
 */
public @Data @Table(name = "auth_menu")
class AuthMenu {

    @ApiModelProperty(value = "id")
    @GeneratedValue(generator = "JDBC")
    @Id
    private Integer id;

    @ApiModelProperty(value = "名称")
    @Column
    private String name;

    @ApiModelProperty(value = "是否是菜单")
    @Column
    private Integer isMenu;

    @ApiModelProperty(value = "超链接")
    @Column
    private String href;

    @ApiModelProperty(value = "父id")
    @Column
    private Integer pId;

    @ApiModelProperty(value = "是否是默认权限")
    @Column
    private Integer isDefault;

}
