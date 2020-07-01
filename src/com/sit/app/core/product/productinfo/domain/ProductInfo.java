package com.sit.app.core.product.productinfo.domain;

import java.io.Serializable;

public class ProductInfo implements Serializable{

	private static final long serialVersionUID = -5460468631882262501L;

	private String venderItemId;
	private String venderCode;
	private String venderShortName;
	private String venderName;
	private String productCode;
	private String productName;
	
	private String documentId;	//click tab
	
	private String certId;	//id for edit
	private String docTransId;	//id for edit

	private ProductInfoDialog dialog = new ProductInfoDialog();
	
	public String getVenderItemId() {
		return venderItemId;
	}

	public void setVenderItemId(String venderItemId) {
		this.venderItemId = venderItemId;
	}

	public String getVenderCode() {
		return venderCode;
	}

	public void setVenderCode(String venderCode) {
		this.venderCode = venderCode;
	}

	public String getVenderShortName() {
		return venderShortName;
	}

	public void setVenderShortName(String venderShortName) {
		this.venderShortName = venderShortName;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public ProductInfoDialog getDialog() {
		return dialog;
	}

	public void setDialog(ProductInfoDialog dialog) {
		this.dialog = dialog;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getDocTransId() {
		return docTransId;
	}

	public void setDocTransId(String docTransId) {
		this.docTransId = docTransId;
	}
}
