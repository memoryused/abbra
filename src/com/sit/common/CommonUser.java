package com.sit.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.sit.domain.Operator;

public class CommonUser implements Serializable {

	private static final long serialVersionUID = 8392198685797505086L;

	public static final String DEFAULT_SESSION_ATTRIBUTE = "user";

	private String userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String prefixName;

	private String startDate;
	private String endDate;
	private String active;
	private String lockStatus;
	private String passwordDate;
	private String fullName;
	private String organizationId;

	private Locale locale;

	private String email;

	private Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();
	private Map<String, Operator> mapOperatorBootstrap = new LinkedHashMap<String, Operator>();
	private Map<String, Operator> mapOperatorBootstrapLayout = new LinkedHashMap<String, Operator>();
	private Map<String, Operator> mapOperatorServerSide = new LinkedHashMap<String, Operator>();
	private Map<String, Operator> mapOperatorJQueryUI = new LinkedHashMap<String, Operator>();
	private Map<String, Operator> mapOperatorSitUtil = new LinkedHashMap<String, Operator>();
	private Map<String, Operator> mapOperatorCssClass = new LinkedHashMap<String, Operator>();


	public Map<String, Operator> getMapOperatorCssClass() {
		return mapOperatorCssClass;
	}

	public void setMapOperatorCssClass(Map<String, Operator> mapOperatorCssClass) {
		this.mapOperatorCssClass = mapOperatorCssClass;
	}

	public Map<String, Operator> getMapOperatorSitUtil() {
		return mapOperatorSitUtil;
	}

	public void setMapOperatorSitUtil(Map<String, Operator> mapOperatorSitUtil) {
		this.mapOperatorSitUtil = mapOperatorSitUtil;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Map<String, Operator> getMapOperatorBootstrap() {
		return mapOperatorBootstrap;
	}

	public void setMapOperatorBootstrap(Map<String, Operator> mapOperatorBootstrap) {
		this.mapOperatorBootstrap = mapOperatorBootstrap;
	}

	public Map<String, Operator> getMapOperatorJQueryUI() {
		return mapOperatorJQueryUI;
	}

	public void setMapOperatorJQueryUI(Map<String, Operator> mapOperatorJQueryUI) {
		this.mapOperatorJQueryUI = mapOperatorJQueryUI;
	}

	public String getPasswordDate() {
		return passwordDate;
	}

	public void setPasswordDate(String passwordDate) {
		this.passwordDate = passwordDate;
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

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Map<String, Operator> getMapOperator() {
		return mapOperator;
	}

	public void setMapOperator(Map<String, Operator> mapOperator) {
		this.mapOperator = mapOperator;
	}

	public Map<String, Operator> getMapOperatorServerSide() {
		return mapOperatorServerSide;
	}

	public void setMapOperatorServerSide(Map<String, Operator> mapOperatorServerSide) {
		this.mapOperatorServerSide = mapOperatorServerSide;
	}

	public Map<String, Operator> getMapOperatorBootstrapLayout() {
		return mapOperatorBootstrapLayout;
	}

	public void setMapOperatorBootstrapLayout(
			Map<String, Operator> mapOperatorBootstrapLayout) {
		this.mapOperatorBootstrapLayout = mapOperatorBootstrapLayout;
	}

}