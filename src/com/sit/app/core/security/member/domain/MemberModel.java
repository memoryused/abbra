package com.sit.app.core.security.member.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class MemberModel extends CommonModel{

	private static final long serialVersionUID = -6885917667959060837L;

	private String criteriaKeyTemp;
	private String resetPassword;
	
	// สำหรับเก็บเงื่อนไขการค้นหา
	private MemberSearchCriteria criteria = new MemberSearchCriteria();
	
	// สำหรับหน้าเพิ่ม, หน้าแก้ไข และแสดง
	private Member member = new Member();
	
	//Combo
	private List<CommonSelectItem> listOrganization = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listLockStatus = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listStatus = new ArrayList<CommonSelectItem>();
	private List<CommonSelectItem> listPrefix = new ArrayList<CommonSelectItem>();
	
	@XmlElement 
	@Override
	public MemberSearchCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (MemberSearchCriteria) criteria;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public List<CommonSelectItem> getListOrganization() {
		return listOrganization;
	}
	public void setListOrganization(List<CommonSelectItem> listOrganization) {
		this.listOrganization = listOrganization;
	}
	public List<CommonSelectItem> getListLockStatus() {
		return listLockStatus;
	}
	public void setListLockStatus(List<CommonSelectItem> listLockStatus) {
		this.listLockStatus = listLockStatus;
	}
	public List<CommonSelectItem> getListStatus() {
		return listStatus;
	}
	public void setListStatus(List<CommonSelectItem> listStatus) {
		this.listStatus = listStatus;
	}
	public String getCriteriaKeyTemp() {
		return criteriaKeyTemp;
	}
	public void setCriteriaKeyTemp(String criteriaKeyTemp) {
		this.criteriaKeyTemp = criteriaKeyTemp;
	}
	public List<CommonSelectItem> getListPrefix() {
		return listPrefix;
	}
	public void setListPrefix(List<CommonSelectItem> listPrefix) {
		this.listPrefix = listPrefix;
	}
	public String getResetPassword() {
		return resetPassword;
	}
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}
	
}
