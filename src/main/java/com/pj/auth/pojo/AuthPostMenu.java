package com.pj.auth.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/11/8.
 */
public
@Data
@Table(name = "auth_post_menu")
class AuthPostMenu {
    public AuthPostMenu() { }

    public AuthPostMenu(String postId) {
        this.postId = postId;
    }

    public AuthPostMenu(Integer menuId, String postId) {
        this.menuId = menuId;
        this.postId = postId;
    }

    @Column()
    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

    @Column()
    @ApiModelProperty(value = "岗位id")
    private String postId;
}
