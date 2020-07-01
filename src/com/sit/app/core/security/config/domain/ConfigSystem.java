package com.sit.app.core.security.config.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import util.bundle.BundleUtil;
import util.log.LogUtil;

import com.sit.common.CommonDomain;
import com.sit.domain.GlobalVariable;

public class ConfigSystem extends CommonDomain {

	private static final long serialVersionUID = -954371012483883366L;
	
	private Integer pwLength;

	/*รูปแบบรหัสผ่าน*/
	private String pwFormatNum;
	private String pwFormatBigChar;
	private String pwFormatLitChar;
	private String pwFormatSpecial;
	
	/*อายุรหัสผ่าน*/
	private Integer pwExpRenew;
	private String pwExp;
	
	/*รูปแบบการแสดง capcha*/
	private String loginCapcha;
	private Integer loginWrongCaptcha;
	
	/*ระงับ User Action*/
	private String loginWrong;
	private Integer loginWrongTime;
	private Integer loginWrongIn;
	
	/*วีธียกเลิกระงับ*/
	private String methodUnlock;
	private Integer methodUnlockAuto;

	/*time out*/
	private Integer connectionTimeOut;
	
	/*เงื่อนไขในการให้สิทธิ์*/
	private String conditionRight;
	
	/*Email*/
	private String email;
	private String password;
	private String checkPassword;
	private String mailServer;
	private String mailProtocal;
	private String mailPort;
	
	/*IP Address*/
	private List<ConfigIP> listIp = new ArrayList<ConfigIP>();
	private String accessIp;

	private String configSystemId;			// เพิ่มตาม SQL
	
	
	public static final String PASSWORD_FORMAT_LENGTH_KEY = "0";
	public static final String PASSWORD_FORMAT_NUMMERIC_KEY = "1";
	public static final String PASSWORD_FORMAT_ALPHABETIC_LOWER_KEY = "2";
	public static final String PASSWORD_FORMAT_ALPHABETIC_UPPER_KEY = "3";
	public static final String PASSWORD_FORMAT_SPECIAL_KEY = "4";

	public static final String PASSWORD_FORMAT_LENGTH_BUNDLE = "sec.passwordFormat" + PASSWORD_FORMAT_LENGTH_KEY;
	public static final String PASSWORD_FORMAT_NUMMERIC_BUNDLE = "sec.passwordFormat" + PASSWORD_FORMAT_NUMMERIC_KEY;
	public static final String PASSWORD_FORMAT_ALPHABETIC_LOWER_BUNDLE = "sec.passwordFormat" + PASSWORD_FORMAT_ALPHABETIC_LOWER_KEY;
	public static final String PASSWORD_FORMAT_ALPHABETIC_UPPER_BUNDLE = "sec.passwordFormat" + PASSWORD_FORMAT_ALPHABETIC_UPPER_KEY;
	public static final String PASSWORD_FORMAT_SPECIAL_BUNDLE = "sec.passwordFormat" + PASSWORD_FORMAT_SPECIAL_KEY;

	public static final Map<String, String> MAP_OF_PASSWORD_FORMAT = new HashMap<String, String>();
	static {
		MAP_OF_PASSWORD_FORMAT.put(PASSWORD_FORMAT_NUMMERIC_KEY, "0123456789");
		MAP_OF_PASSWORD_FORMAT.put(PASSWORD_FORMAT_SPECIAL_KEY, "!#$%^&*?+_()[]|");
		MAP_OF_PASSWORD_FORMAT.put(PASSWORD_FORMAT_ALPHABETIC_UPPER_KEY, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		MAP_OF_PASSWORD_FORMAT.put(PASSWORD_FORMAT_ALPHABETIC_LOWER_KEY, "abcdefghijklmnopqrstuvwxyz");
	}

	public static final Map<String, String> MAP_OF_PASSWORD_FORMAT_JS = new HashMap<String, String>();
	static {
		MAP_OF_PASSWORD_FORMAT_JS.put(PASSWORD_FORMAT_NUMMERIC_KEY, "[0-9]");
		MAP_OF_PASSWORD_FORMAT_JS.put(PASSWORD_FORMAT_SPECIAL_KEY, "!#$%^&*?+_()[]|");
		MAP_OF_PASSWORD_FORMAT_JS.put(PASSWORD_FORMAT_ALPHABETIC_UPPER_KEY, "[A-Z]");
		MAP_OF_PASSWORD_FORMAT_JS.put(PASSWORD_FORMAT_ALPHABETIC_LOWER_KEY, "[a-z]");
	}

	public static final Map<String, String> MAP_OF_PASSWORD_FORMAT_DESCRIPTION = new HashMap<String, String>();
	static {
		MAP_OF_PASSWORD_FORMAT_DESCRIPTION.put(PASSWORD_FORMAT_NUMMERIC_KEY, "0-9");
		MAP_OF_PASSWORD_FORMAT_DESCRIPTION.put(PASSWORD_FORMAT_ALPHABETIC_LOWER_KEY, "a-z");
		MAP_OF_PASSWORD_FORMAT_DESCRIPTION.put(PASSWORD_FORMAT_ALPHABETIC_UPPER_KEY, "A-Z");
		MAP_OF_PASSWORD_FORMAT_DESCRIPTION.put(PASSWORD_FORMAT_SPECIAL_KEY, "!#$%^&*?+_()[]|");
	}

	public String getPasswordFormatDescriptionHTML(String localeString) {
		LogUtil.COMMON.debug("localeString: " + localeString);
		String lineBreak = "<br>";
		Locale locale = new Locale(localeString.toLowerCase(), localeString.toUpperCase());
		return getPasswordFormatDescription(locale, lineBreak);
	}

	public String getPasswordFormatDescriptionText(String localeString) {
		LogUtil.COMMON.debug("localeString: " + localeString);
		String lineBreak = "<br>";
		Locale locale = new Locale(localeString.toLowerCase(), localeString.toUpperCase());
		return getPasswordFormatDescription(locale, lineBreak);
	}

	public String getPasswordFormatDescription(Locale locale, String lineBreak) {

		StringBuilder message = new StringBuilder();
		try {
			ResourceBundle bundle = BundleUtil.getBundle("resources.bundle.security.MessageSecurity", locale);

			if (getPwLength() > 0) {
				if (message.length() > 0) {
					message.append("," + lineBreak);
				}
				message.append(String.format(bundle.getString(PASSWORD_FORMAT_LENGTH_BUNDLE), getPwLength()));
			}

			if (getPwFormatNum().equals(GlobalVariable.FLAG_ACTIVE)) {
				if (message.length() > 0) {
					message.append("," + lineBreak);
				}
				message.append(String.format(bundle.getString(PASSWORD_FORMAT_NUMMERIC_BUNDLE), MAP_OF_PASSWORD_FORMAT_DESCRIPTION.get(PASSWORD_FORMAT_NUMMERIC_KEY)));
			}

			if (getPwFormatLitChar().equals(GlobalVariable.FLAG_ACTIVE)) {
				if (message.length() > 0) {
					message.append("," + lineBreak);
				}
				message.append(String.format(bundle.getString(PASSWORD_FORMAT_ALPHABETIC_LOWER_BUNDLE), MAP_OF_PASSWORD_FORMAT_DESCRIPTION.get(PASSWORD_FORMAT_ALPHABETIC_LOWER_KEY)));
			}

			if (getPwFormatBigChar().equals(GlobalVariable.FLAG_ACTIVE)) {
				if (message.length() > 0) {
					message.append("," + lineBreak);
				}
				message.append(String.format(bundle.getString(PASSWORD_FORMAT_ALPHABETIC_UPPER_BUNDLE), MAP_OF_PASSWORD_FORMAT_DESCRIPTION.get(PASSWORD_FORMAT_ALPHABETIC_UPPER_KEY)));
			}

			if (getPwFormatSpecial().equals(GlobalVariable.FLAG_ACTIVE)) {
				if (message.length() > 0) {
					message.append("," + lineBreak);
				}
				message.append(String.format(bundle.getString(PASSWORD_FORMAT_SPECIAL_BUNDLE), MAP_OF_PASSWORD_FORMAT_DESCRIPTION.get(PASSWORD_FORMAT_SPECIAL_KEY)));
			}
		} catch (Exception e) {
			LogUtil.COMMON.error("", e);
		}

		return message.toString();
	}
	

	public String getPwFormatNum() {
		return pwFormatNum;
	}

	public void setPwFormatNum(String pwFormatNum) {
		this.pwFormatNum = pwFormatNum;
	}

	public String getPwFormatBigChar() {
		return pwFormatBigChar;
	}

	public void setPwFormatBigChar(String pwFormatBigChar) {
		this.pwFormatBigChar = pwFormatBigChar;
	}

	public String getPwFormatLitChar() {
		return pwFormatLitChar;
	}

	public void setPwFormatLitChar(String pwFormatLitChar) {
		this.pwFormatLitChar = pwFormatLitChar;
	}

	public String getPwFormatSpecial() {
		return pwFormatSpecial;
	}

	public void setPwFormatSpecial(String pwFormatSpecial) {
		this.pwFormatSpecial = pwFormatSpecial;
	}

	public String getLoginCapcha() {
		return loginCapcha;
	}

	public void setLoginCapcha(String loginCapcha) {
		this.loginCapcha = loginCapcha;
	}

	public Integer getLoginWrongCaptcha() {
		return loginWrongCaptcha;
	}

	public void setLoginWrongCaptcha(Integer loginWrongCaptcha) {
		this.loginWrongCaptcha = loginWrongCaptcha;
	}

	public String getLoginWrong() {
		return loginWrong;
	}

	public void setLoginWrong(String loginWrong) {
		this.loginWrong = loginWrong;
	}

	public Integer getLoginWrongTime() {
		return loginWrongTime;
	}

	public void setLoginWrongTime(Integer loginWrongTime) {
		this.loginWrongTime = loginWrongTime;
	}

	public String getMethodUnlock() {
		return methodUnlock;
	}

	public void setMethodUnlock(String methodUnlock) {
		this.methodUnlock = methodUnlock;
	}

	public Integer getMethodUnlockAuto() {
		return methodUnlockAuto;
	}

	public void setMethodUnlockAuto(Integer methodUnlockAuto) {
		this.methodUnlockAuto = methodUnlockAuto;
	}

	public Integer getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(Integer connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public String getConditionRight() {
		return conditionRight;
	}

	public void setConditionRight(String conditionRight) {
		this.conditionRight = conditionRight;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getMailProtocal() {
		return mailProtocal;
	}

	public void setMailProtocal(String mailProtocal) {
		this.mailProtocal = mailProtocal;
	}

	public String getMailPort() {
		return mailPort;
	}

	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}

	public List<ConfigIP> getListIp() {
		return listIp;
	}

	public void setListIp(List<ConfigIP> listIp) {
		this.listIp = listIp;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public String getConfigSystemId() {
		return configSystemId;
	}

	public void setConfigSystemId(String configSystemId) {
		this.configSystemId = configSystemId;
	}

	public Integer getLoginWrongIn() {
		return loginWrongIn;
	}

	public void setLoginWrongIn(Integer loginWrongIn) {
		this.loginWrongIn = loginWrongIn;
	}


	public String getCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(String checkPassword) {
		this.checkPassword = checkPassword;
	}

	public String getPwExp() {
		return pwExp;
	}

	public void setPwExp(String pwExp) {
		this.pwExp = pwExp;
	}

	public Integer getPwLength() {
		return pwLength;
	}

	public void setPwLength(Integer pwLength) {
		this.pwLength = pwLength;
	}

	public Integer getPwExpRenew() {
		return pwExpRenew;
	}

	public void setPwExpRenew(Integer pwExpRenew) {
		this.pwExpRenew = pwExpRenew;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
