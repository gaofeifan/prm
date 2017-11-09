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
public
@Data
class AuthPostMenuVo {

    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

    @ApiModelProperty(value = "岗位id")
    private Integer postId;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "是否是菜单")
    private Integer isMenu;

    @ApiModelProperty(value = "超链接")
    private String href;

    @ApiModelProperty(value = "父id")
    private Integer pId;

    @ApiModelProperty(value = "选中 1 0 未选中")
    private Integer checks;
}
