package com.sit.app.core.security.login.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sit.app.core.security.config.domain.ConfigSystem;
import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;

@XmlRootElement
public class LoginModel extends CommonModel {

	private static final long serialVersionUID = -2657506932060023400L;

	private String username;
	private String password;
	private String captcha;
	private boolean showCaptcha;
	private String remarkPasswordFormat;
	private String newPassword;
	private String confirmNewPassword;
	private String answer;
	private String questionId;
	private boolean firstTimeLogin;
	private boolean changePassword;
	private boolean changeQA;

	private Integer siteIndex;
	private String siteCode;

	private boolean confirmOverride;
	private String passwordOverride;
	
	private ConfigSystem configSystem;
	
	//combo คำถาม สำหรับหน้า Profile
	private List<CommonSelectItem> listQuestion = new ArrayList<CommonSelectItem>();

	public boolean isChangePassword() {
		return changePassword;
	}

	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}

	public boolean isChangeQA() {
		return changeQA;
	}

	public void setChangeQA(boolean changeQA) {
		this.changeQA = changeQA;
	}

	public List<CommonSelectItem> getListQuestion() {
		return listQuestion;
	}

	public void setListQuestion(List<CommonSelectItem> listQuestion) {
		this.listQuestion = listQuestion;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isFirstTimeLogin() {
		return firstTimeLogin;
	}

	public void setFirstTimeLogin(boolean firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isShowCaptcha() {
		return showCaptcha;
	}

	public void setShowCaptcha(boolean showCaptcha) {
		this.showCaptcha = showCaptcha;
	}

	public String getRemarkPasswordFormat() {
		return remarkPasswordFormat;
	}

	public void setRemarkPasswordFormat(String remarkPasswordFormat) {
		this.remarkPasswordFormat = remarkPasswordFormat;
	}

	public boolean isConfirmOverride() {
		return confirmOverride;
	}

	public void setConfirmOverride(boolean confirmOverride) {
		this.confirmOverride = confirmOverride;
	}
	
	@XmlTransient
	public String getPasswordOverride() {
		return passwordOverride;
	}

	public void setPasswordOverride(String passwordOverride) {
		this.passwordOverride = passwordOverride;
	}

	public Integer getSiteIndex() {
		return siteIndex;
	}

	public void setSiteIndex(Integer siteIndex) {
		this.siteIndex = siteIndex;
	}

	public ConfigSystem getConfigSystem() {
		return configSystem;
	}

	public void setConfigSystem(ConfigSystem configSystem) {
		this.configSystem = configSystem;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

}
