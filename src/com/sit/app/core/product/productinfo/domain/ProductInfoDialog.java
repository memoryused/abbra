package com.sit.app.core.product.productinfo.domain;

import java.io.Serializable;

import com.sit.domain.FileMeta;

public class ProductInfoDialog implements Serializable{

	private static final long serialVersionUID = -2969665402123629580L;

	//Add, Edit
	private String docTransId; 
	private String certificateId; 
	private String docExpireDate; 
	private String contractEmail; 
	private FileMeta fileMetaPdf = new FileMeta();
	private FileMeta fileMetaTxt = new FileMeta();
	
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public String getDocExpireDate() {
		return docExpireDate;
	}
	public void setDocExpireDate(String docExpireDate) {
		this.docExpireDate = docExpireDate;
	}
	public String getContractEmail() {
		return contractEmail;
	}
	public void setContractEmail(String contractEmail) {
		this.contractEmail = contractEmail;
	}
	public FileMeta getFileMetaPdf() {
		return fileMetaPdf;
	}
	public void setFileMetaPdf(FileMeta fileMetaPdf) {
		this.fileMetaPdf = fileMetaPdf;
	}
	public FileMeta getFileMetaTxt() {
		return fileMetaTxt;
	}
	public void setFileMetaTxt(FileMeta fileMetaTxt) {
		this.fileMetaTxt = fileMetaTxt;
	}
	public String getDocTransId() {
		return docTransId;
	}
	public void setDocTransId(String docTransId) {
		this.docTransId = docTransId;
	}
	
}
