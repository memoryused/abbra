package com.sit.app.core.product.productinfo.domain;

import com.sit.common.CommonDomain;

public class ProductInfoSearch extends CommonDomain{

	private static final long serialVersionUID = 7968623449411954870L;

	private String venderItemId;
	private String productName; 
	private String venderShortName; 
	private String status;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVenderItemId() {
		return venderItemId;
	}
	public void setVenderItemId(String venderItemId) {
		this.venderItemId = venderItemId;
	}
	public String getVenderShortName() {
		return venderShortName;
	}
	public void setVenderShortName(String venderShortName) {
		this.venderShortName = venderShortName;
	}
	
}
