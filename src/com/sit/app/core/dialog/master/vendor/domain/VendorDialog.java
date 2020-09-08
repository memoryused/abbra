package com.sit.app.core.dialog.master.vendor.domain;

import com.sit.common.CommonDomain;

public class VendorDialog extends CommonDomain{

	private static final long serialVersionUID = 8179758303169183436L;

	private String ids;
	private String vendorCode;
	private String vendorName;
	private String vendorShortName;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
	
}
