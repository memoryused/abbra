package com.sit.app.core.master.vendor.domain;

import java.util.ArrayList;
import java.util.List;

import com.sit.app.core.master.product.domain.Item;
import com.sit.common.CommonDomain;

public class Vendor extends CommonDomain{

	private static final long serialVersionUID = 294444732951794846L;

	private String vendorId;
	private String vendorCode;
	private String vendorName;
	private String vendorShortName;
	private String status;
	
	private List<Item> listProduct = new ArrayList<>();
	
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorShortName() {
		return vendorShortName;
	}
	public void setVendorShortName(String vendorShortName) {
		this.vendorShortName = vendorShortName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Item> getListProduct() {
		return listProduct;
	}
	public void setListProduct(List<Item> listProduct) {
		this.listProduct = listProduct;
	}
}
