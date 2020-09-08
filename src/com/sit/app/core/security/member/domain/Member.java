package com.sit.app.core.security.member.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sit.app.core.security.membergroup.domain.Group;
import com.sit.domain.Operator;

public class Member implements Serializable{

	private static final long serialVersionUID = -2304794340266150910L;

	// tab รายละเอียดข้อมูล
	private MemberData memberData = new MemberData();

	// tab สิทธิ์ ส่วนของโปรแกรม
	private List<Operator> listProgram = new ArrayList<Operator>();
	
	// tab กลุ่มผู้ใช้
	private List<Group> listGroup = new ArrayList<Group>();

	public MemberData getMemberData() {
		return memberData;
	}

	public void setMemberData(MemberData memberData) {
		this.memberData = memberData;
	}

	public List<Group> getListGroup() {
		return listGroup;
	}

	public void setListGroup(List<Group> listGroup) {
		this.listGroup = listGroup;
	}

	public List<Operator> getListProgram() {
		return listProgram;
	}

	public void setListProgram(List<Operator> listProgram) {
		this.listProgram = listProgram;
	}
}
