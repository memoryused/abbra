package com.sit.app.core.product.home.domain;

import com.sit.common.CommonDomain;

public class ProductHomeSearch extends CommonDomain {

	private static final long serialVersionUID = 4289092026191537868L;

	private String docTranId;
	private String productName;
	private String venderName;
	private String documentType;
	private String standard;
	private String expireDate;
	private String pdfPath;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getVenderName() {
		return venderName;
	}
	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public String getDocTranId() {
		return docTranId;
	}
	public void setDocTranId(String docTranId) {
		this.docTranId = docTranId;
	}
	
}
