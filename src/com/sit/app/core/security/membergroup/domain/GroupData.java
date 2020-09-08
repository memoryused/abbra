package com.sit.app.core.security.membergroup.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.app.core.security.member.domain.MemberSearch;
import com.sit.domain.Operator;

public class GroupData {
	// tab รายละเอียดข้อมูล
	private Group group = new Group();

	// tab สิทธิ์ ส่วนของโปรแกรม
	private List<Operator> listProgram = new ArrayList<Operator>();

	// tab ผู้ใช้
	private List<MemberSearch> listUser = new ArrayList<MemberSearch>();

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Operator> getListProgram() {
		return listProgram;
	}

	public void setListProgram(List<Operator> listProgram) {
		this.listProgram = listProgram;
	}

	public List<MemberSearch> getListUser() {
		return listUser;
	}

	public void setListUser(List<MemberSearch> listUser) {
		this.listUser = listUser;
	}

}
