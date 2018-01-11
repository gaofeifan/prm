
package com.pj.auth.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**  
* @desc: prm用户与菜单关系表，用于权限细化到人的功能 
* @author: x.gao  
* @createTime: 2017年12月27日 上午10:50:51  
* @history:  
* @version: v1.0    
*/
@Data
@Table(name = "user_menu")
public class UserMenu {

  public UserMenu() {};

  @ApiModelProperty(value = "id")
  @GeneratedValue(generator = "JDBC")
  @Id
  private Integer id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "auth_id")
  private Integer authId;

  @Column(name = "post_id")
  private String postId;

}

