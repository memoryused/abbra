package com.sit.app.core.dialog.security.group.domain;

import com.sit.common.CommonDomain;

public class GroupUserDialog extends CommonDomain{

	private static final long serialVersionUID = 7139152053108029712L;
	
	private String groupCode;
	private String groupName;
	private String ids;
	
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
