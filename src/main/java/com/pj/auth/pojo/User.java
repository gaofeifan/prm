package com.pj.auth.pojo;

import lombok.Data;

/**
 *	@author		GFF
 *	@date		2017年9月12日下午2:35:37
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
public @Data class User {

	private Integer id;
	
	private String username;
	
	private String postname;
	
	private Integer postid;
	
	private String dempname;
	
	private String companyname;
	
	private String email;
	
	
}
