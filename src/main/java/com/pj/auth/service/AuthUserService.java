package com.pj.auth.service;

import com.pj.auth.pojo.User;

public interface AuthUserService {

	/**
	 * 	根据邮箱查询
	 *	@author 	GFF
	 *	@date		2018年1月10日下午6:03:36	
	 * 	@param email
	 * 	@return
	 */
	User selectPersonByEmail(String email);

	/**
	 * 	根据id查询
	 *	@author 	GFF
	 *	@date		2018年1月10日下午6:04:01	
	 * 	@param id
	 * 	@return
	 */
	User selectPersonById(String id);

	
	/*  public User selectUserByEmail(String email);

    public User selectUserByEmail(Integer id);

    public String getEmailsByPostId(String postId);

    public User selectAdminUserById();
*/
	public User selectAdminUserById();
}
