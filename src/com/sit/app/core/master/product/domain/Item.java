package com.sit.app.core.master.product.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.app.core.master.vendor.domain.Vendor;
import com.sit.common.CommonDomain;

public class Item extends CommonDomain{

	private static final long serialVersionUID = 1L;

	private String itemId;
	private String itemCode;
	private String itemShortName;
	private String status;
	
	// tab vendor
	private List<Vendor> listVendor = new ArrayList<Vendor>();
		
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
	public List<Vendor> getListVendor() {
		return listVendor;
	}
	public void setListVendor(List<Vendor> listVendor) {
		this.listVendor = listVendor;
	}
	
}
