package com.sit.app.core.master.vendor.domain;

import com.sit.common.CommonDomain;

public class VendorSearch extends CommonDomain{

	private static final long serialVersionUID = -6551853942686874004L;

	private String vendorId;
	private String vendorCode;
	private String vendorName;
	private String vendorShortName;
	private String status;
	
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
}
