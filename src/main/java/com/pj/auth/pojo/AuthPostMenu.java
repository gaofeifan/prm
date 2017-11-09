package com.pj.auth.pojo;

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

    @Column()
    private Integer menuId;

    @Column()
    private Integer postId;
}
