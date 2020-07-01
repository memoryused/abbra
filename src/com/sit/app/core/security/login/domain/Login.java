package com.sit.app.core.security.login.domain;

import com.sit.common.CommonDomain;

public class Login extends CommonDomain {

	private static final long serialVersionUID = -4196930762894785496L;

	private String secLoginID;
	private String username;
	private String password;

	public String getSecLoginID() {
		return secLoginID;
	}

	public void setSecLoginID(String secLoginID) {
		this.secLoginID = secLoginID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
