package com.pj.auth.pojo;

import lombok.Data;

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

}
