package com.sit.app.core.dialog.security.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

@XmlRootElement
public class UserDialogModel extends CommonModel{

	private static final long serialVersionUID = -4107670848372512915L;
	
	private UserDialogSearchCriteria criteria = new UserDialogSearchCriteria();
	
	//combo Password Status
	private List<CommonSelectItem> listPwdStatus = new ArrayList<CommonSelectItem>();
	
	//combo Organization
	private List<CommonSelectItem> listOrganization = new ArrayList<CommonSelectItem>();
	
	
	@Override
	public UserDialogSearchCriteria getCriteria() {
		return criteria;
	}
	
	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (UserDialogSearchCriteria) criteria;
	}

	public List<CommonSelectItem> getListPwdStatus() {
		return listPwdStatus;
	}

	public void setListPwdStatus(List<CommonSelectItem> listPwdStatus) {
		this.listPwdStatus = listPwdStatus;
	}

	public List<CommonSelectItem> getListOrganization() {
		return listOrganization;
	}

	public void setListOrganization(List<CommonSelectItem> listOrganization) {
		this.listOrganization = listOrganization;
	}
}
