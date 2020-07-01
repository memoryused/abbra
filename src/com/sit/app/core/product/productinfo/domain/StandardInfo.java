package com.sit.app.core.product.productinfo.domain;

import java.io.Serializable;

public class StandardInfo implements Serializable{

	private static final long serialVersionUID = 2367836655737621652L;

	private String docTranId;
	private String documentId;
	private String certificateId;
	private String certificateName;
	private String docExpireDate;
	private String contractEmail;
	private String pdfPath;
	private String txtPath;
	
	public String getDocTranId() {
		return docTranId;
	}
	public void setDocTranId(String docTranId) {
		this.docTranId = docTranId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
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
	public String getPdfPath() {
		return pdfPath;
	}
	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	public String getTxtPath() {
		return txtPath;
	}
	public void setTxtPath(String txtPath) {
		this.txtPath = txtPath;
	}
	
}
