package com.sit.app.core.security.member.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class MemberSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = -3354485517366230338L;

	public static final int TABLE_HEADER_SORTS_TYPE_1 = 1;
	
	private String userCode;
	private String username;
	private String forename;
	private String surname;
	private String organizationId;
	private String positionName;
	private String lockStatus;
	private String status;
	
	public MemberSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = {
				new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("su.USER_CODE", HeaderSorts.ASC, "0")
				, new HeaderSorts("su.USERNAME", HeaderSorts.ASC, "0")
				, new HeaderSorts("fullname", HeaderSorts.ASC, "0")
				, new HeaderSorts("mo.organization_name", HeaderSorts.ASC, "0")
				, new HeaderSorts("su.POSITION_NAME", HeaderSorts.ASC, "0")
				, new HeaderSorts("su.LOCK_STATUS", HeaderSorts.ASC, "0")
				, new HeaderSorts("su.ACTIVE", HeaderSorts.ASC, "0")
		};
		
		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	private static final HeaderSorts[] HEADER_SORTS_1 = { 
			new HeaderSorts("", HeaderSorts.ASC),
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC)
	};
	
	public void setDefaultHeaderSorts() {
		switch (getCriteriaType()) {
			case TABLE_HEADER_SORTS_TYPE_1:
				setHeaderSortsSelect("");
				break;
			default:{
				for (int i = 0; i < getHeaderSortsSize(); i++) {
					getHeaderSorts()[i].setOrder(HeaderSorts.ASC);
					getHeaderSorts()[i].setSeq("");
				}
				getHeaderSorts()[0].setSeq("0");
				setHeaderSortsSelect("3");
			}
		}
	}

	public void initHeaderSorts() {
		HeaderSorts[] arrayHeaderSorts = null;

		switch (getCriteriaType()) {
			case TABLE_HEADER_SORTS_TYPE_1:	{
				arrayHeaderSorts = HEADER_SORTS_1;
				setHeaderSorts(arrayHeaderSorts);
				setHeaderSortsSize(arrayHeaderSorts.length);
				
				break;
			}
			default:
				break;
		}
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static int getTableHeaderSortsType1() {
		return TABLE_HEADER_SORTS_TYPE_1;
	}
}
