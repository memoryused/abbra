package com.sit.app.core.dialog.master.product.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class ProductDialogModel extends CommonModel{

	private static final long serialVersionUID = 7527642216474054136L;

	private ProductDialogSearchCriteria criteria = new ProductDialogSearchCriteria();
	
	//combo active
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();

	public List<CommonSelectItem> getListActiveStatus() {
		return listActiveStatus;
	}

	public void setListActiveStatus(List<CommonSelectItem> listActiveStatus) {
		this.listActiveStatus = listActiveStatus;
	}

	@Override
	public ProductDialogSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ProductDialogSearchCriteria) criteria;
	}
}
