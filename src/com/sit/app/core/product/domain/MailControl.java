package com.sit.app.core.product.domain;

import java.io.Serializable;

public class MailControl implements Serializable{

	private static final long serialVersionUID = -7192280795590707964L;
	
	private String emailId; 
	private String sender; 
	private String cc1; 
	private String cc2; 
	private String sendNotify; 
	private String mEmailControlCol;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getCc1() {
		return cc1;
	}
	public void setCc1(String cc1) {
		this.cc1 = cc1;
	}
	public String getCc2() {
		return cc2;
	}
	public void setCc2(String cc2) {
		this.cc2 = cc2;
	}
	public String getSendNotify() {
		return sendNotify;
	}
	public void setSendNotify(String sendNotify) {
		this.sendNotify = sendNotify;
	}
	public String getmEmailControlCol() {
		return mEmailControlCol;
	}
	public void setmEmailControlCol(String mEmailControlCol) {
		this.mEmailControlCol = mEmailControlCol;
	} 
}
