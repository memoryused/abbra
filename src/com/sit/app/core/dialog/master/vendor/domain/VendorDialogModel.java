package com.sit.app.core.dialog.master.vendor.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class VendorDialogModel extends CommonModel{

	private static final long serialVersionUID = 9115060093850729311L;

	private VendorDialogSearchCriteria criteria = new VendorDialogSearchCriteria();
	
	//combo active
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();

	public List<CommonSelectItem> getListActiveStatus() {
		return listActiveStatus;
	}

	public void setListActiveStatus(List<CommonSelectItem> listActiveStatus) {
		this.listActiveStatus = listActiveStatus;
	}

	@Override
	public VendorDialogSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (VendorDialogSearchCriteria) criteria;
	}
	
	
}
