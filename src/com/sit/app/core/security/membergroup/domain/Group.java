package com.sit.app.core.security.membergroup.domain;

import com.sit.common.CommonDomain;

public class Group extends CommonDomain{

	private static final long serialVersionUID = -8594053639433233684L;

	private String groupCode;
	private String groupName;
	private String remark;
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
