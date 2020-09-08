package com.sit.app.core.security.member.domain;

import com.sit.common.CommonDomain;

public class MemberSearch extends CommonDomain{

	private static final long serialVersionUID = 8724497542646247176L;

	private String userId;
	private String userCode;
	private String username;
	private String fullname;
	private String organizationId;
	private String organizationName;
	private String positionName;
	private String email;
	private String lockStatus;
	private String lockStatusName;
	private String status;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLockStatusName() {
		return lockStatusName;
	}
	public void setLockStatusName(String lockStatusName) {
		this.lockStatusName = lockStatusName;
	}
}
