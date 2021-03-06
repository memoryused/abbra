package com.sit.app.core.master.product.domain;

import com.sit.common.CommonDomain;

public class ItemSearch extends CommonDomain{

	private static final long serialVersionUID = 2146242227926870914L;

	private String itemId;
	private String itemCode;
	private String itemShortName;
	private String status;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemShortName() {
		return itemShortName;
	}
	public void setItemShortName(String itemShortName) {
		this.itemShortName = itemShortName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
