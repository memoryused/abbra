package com.sit.app.core.dialog.master.product.domain;

import com.sit.common.CommonDomain;

public class ProductDialog extends CommonDomain {

	private static final long serialVersionUID = -6225095066876433349L;
	
	private String ids;
	private String itemCode;
	private String itemName;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
