package com.sit.app.core.security.login.domain;

import javax.xml.bind.annotation.XmlTransient;

import com.sit.common.CommonDomain;

public class User extends CommonDomain {

	private static final long serialVersionUID = -4874560239918392328L;

	private String prefixId;
	private String surname;
	private String forname;
	private String fullName;
	private String organizationId;
	private String organizationName;
	private String positionName;
	private String email;
	
	private String startDate;
	private String endDate;
	
	private String username;
	private String password;
	private String passwordDate;
	private String passwordExp;
	
	private String lockStatus;
	private String lockStatusDesc;
	private String resetPasswordStatus;
	 
	private String employeeCode;
	private String cellPhone1;
	private String cellPhone2;
	private String cellPhone3;
	
	private String remark;
	
	private String question;
	private String answer;
	 
	private String doctypeRefUser;
	private String doctypeRefUserName;
	private String doctypeRefUserNo;

	private String prefixName;

	private String fullNameTh;
	private String fullNameEn;
	private String lockStatusName;
	
	private String fullNameEng;
	private String surnameEng;
	private String fornameEng;
	private String ready;
	
	private String changeLogs;
	
	private String groupUserId; 	
	private String groupUserName; 	
	private String groupUserNameHint; 
	
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getPrefixId() {
		return prefixId;
	}

	public void setPrefixId(String prefixId) {
		this.prefixId = prefixId;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getForname() {
		return forname;
	}

	public void setForname(String forname) {
		this.forname = forname;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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

	public String getCellPhone1() {
		return cellPhone1;
	}

	public void setCellPhone1(String cellPhone1) {
		this.cellPhone1 = cellPhone1;
	}

	public String getCellPhone2() {
		return cellPhone2;
	}

	public void setCellPhone2(String cellPhone2) {
		this.cellPhone2 = cellPhone2;
	}

	public String getCellPhone3() {
		return cellPhone3;
	}

	public void setCellPhone3(String cellPhone3) {
		this.cellPhone3 = cellPhone3;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordDate() {
		return passwordDate;
	}

	public void setPasswordDate(String passwordDate) {
		this.passwordDate = passwordDate;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getResetPasswordStatus() {
		return resetPasswordStatus;
	}

	public void setResetPasswordStatus(String resetPasswordStatus) {
		this.resetPasswordStatus = resetPasswordStatus;
	}

	public String getDoctypeRefUser() {
		return doctypeRefUser;
	}

	public void setDoctypeRefUser(String doctypeRefUser) {
		this.doctypeRefUser = doctypeRefUser;
	}

	public String getDoctypeRefUserNo() {
		return doctypeRefUserNo;
	}

	public void setDoctypeRefUserNo(String doctypeRefUserNo) {
		this.doctypeRefUserNo = doctypeRefUserNo;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPasswordExp() {
		return passwordExp;
	}

	public void setPasswordExp(String passwordExp) {
		this.passwordExp = passwordExp;
	}

	public String getFullNameTh() {
		return fullNameTh;
	}

	public void setFullNameTh(String fullNameTh) {
		this.fullNameTh = fullNameTh;
	}

	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getLockStatusName() {
		return lockStatusName;
	}

	public void setLockStatusName(String lockStatusName) {
		this.lockStatusName = lockStatusName;
	}

	public String getFullNameEng() {
		return fullNameEng;
	}

	public void setFullNameEng(String fullNameEng) {
		this.fullNameEng = fullNameEng;
	}

	public String getLockStatusDesc() {
		return lockStatusDesc;
	}

	public void setLockStatusDesc(String lockStatusDesc) {
		this.lockStatusDesc = lockStatusDesc;
	}

	public String getSurnameEng() {
		return surnameEng;
	}

	public void setSurnameEng(String surnameEng) {
		this.surnameEng = surnameEng;
	}

	public String getFornameEng() {
		return fornameEng;
	}

	public void setFornameEng(String fornameEng) {
		this.fornameEng = fornameEng;
	}

	public String getReady() {
		return ready;
	}

	public void setReady(String ready) {
		this.ready = ready;
	}

	public String getDoctypeRefUserName() {
		return doctypeRefUserName;
	}

	public void setDoctypeRefUserName(String doctypeRefUserName) {
		this.doctypeRefUserName = doctypeRefUserName;
	}

	public String getChangeLogs() {
		return changeLogs;
	}

	public void setChangeLogs(String changeLogs) {
		this.changeLogs = changeLogs;
	}

	public String getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(String groupUserId) {
		this.groupUserId = groupUserId;
	}

	public String getGroupUserName() {
		return groupUserName;
	}

	public void setGroupUserName(String groupUserName) {
		this.groupUserName = groupUserName;
	}

	public String getGroupUserNameHint() {
		return groupUserNameHint;
	}

	public void setGroupUserNameHint(String groupUserNameHint) {
		this.groupUserNameHint = groupUserNameHint;
	}

}
