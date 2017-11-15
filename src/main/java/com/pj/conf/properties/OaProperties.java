package com.pj.conf.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author GFF
 * @date 2017年9月20日上午11:10:59
 * @version 1.0.0
 * @parameter
 * @since 1.8
 */
@Component
public class OaProperties {

	@Value("${oa.url}")
	@Getter
	private String url;

	@Value("${oa.adminId}")
	@Getter
	private Integer adminId;

	@Value("${oa.ssoUrl}")
	@Getter
	@Setter
	private String ssoUrl;

	private String ssoUrl_selectAdmin = "/sso/accountSet/selectAdmin";

	private String findUserByemail = "/oa/user/findUserByemail.do";

	private String findUserById = "/oa/user/find.do";

	private String selectUserByUsername = ""
			+ "/oa/user/selectUserByUsername.do";

	private String selectUserByPostId = "/oa/user/selectUserByPostId.do";

	private String selectPostList = "/oa/post/list.do";

	public String getFindUserByemail() {
		return url + findUserByemail;
	}

	public String getSelectUserByUsername() {
		return url + selectUserByUsername;
	}
	public String getFindUserById() {
		return url + findUserById;
	}

	public String getSelectUserByPostId() {
		return url + selectUserByPostId;
	}

	public String getSelectPostList() {
		return url + selectPostList;
	}
	
	public String getSsoUrl_selectAdmin() {
		return ssoUrl + ssoUrl_selectAdmin;
	}

}
