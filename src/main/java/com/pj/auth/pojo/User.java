package com.pj.auth.pojo;

import lombok.Data;

/**
 * @author GFF
 * @date 2017年9月12日下午2:35:37
 * @version 1.0.0
 * @parameter
 * @since 1.8
 */
public @Data class User {

	private String id;

	private String name;

	private String email;

	private String positionId;
	
	private String positionName;
	
	private String deptName;
	
	private String companyName;
	
	private String deptId;
	
	private String companyId;

	private String phone; // 手机号 支持 邮件发送 2017年11月16日11:21:20

}
