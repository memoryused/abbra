package com.sit.app.core.dialog.security.group.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

@XmlRootElement
public class GroupUserDialogModel extends CommonModel{
	
	private static final long serialVersionUID = 2507607484165143369L;
	
	private GroupUserDialogSearchCriteria criteria = new GroupUserDialogSearchCriteria();
	
	//combo active
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();

	@Override
	public GroupUserDialogSearchCriteria getCriteria() {
		return criteria;
	}
	
	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (GroupUserDialogSearchCriteria) criteria;
	}

	public List<CommonSelectItem> getListActiveStatus() {
		return listActiveStatus;
	}

	public void setListActiveStatus(List<CommonSelectItem> listActiveStatus) {
		this.listActiveStatus = listActiveStatus;
	}


}
