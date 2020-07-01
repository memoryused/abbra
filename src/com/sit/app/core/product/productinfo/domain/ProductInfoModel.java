package com.sit.app.core.product.productinfo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.sit.app.core.product.domain.MailControl;
import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class ProductInfoModel extends CommonModel{

	private static final long serialVersionUID = 6616399530977408336L;

	// สำหรับหน้าเพิ่ม, หน้าแก้ไข และแสดง
	private ProductInfo productInfo = new ProductInfo();
	
	private MailControl mailControl = new MailControl();
	
	private ProductInfoSearchCriteria criteria = new ProductInfoSearchCriteria();

	private String criteriaKeyTemp; 
	
	//Combo box
	private List<CommonSelectItem> listProduct = new ArrayList<>();
	private List<CommonSelectItem> listVender = new ArrayList<>();
	private List<CommonSelectItem> listStatus = new ArrayList<>();
	private List<CommonSelectItem> listStandard = new ArrayList<>();
		
	
	@XmlElement
	@Override
	public ProductInfoSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ProductInfoSearchCriteria) criteria;
	}

	public String getCriteriaKeyTemp() {
		return criteriaKeyTemp;
	}

	public void setCriteriaKeyTemp(String criteriaKeyTemp) {
		this.criteriaKeyTemp = criteriaKeyTemp;
	}

	public List<CommonSelectItem> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<CommonSelectItem> listProduct) {
		this.listProduct = listProduct;
	}

	public List<CommonSelectItem> getListVender() {
		return listVender;
	}

	public void setListVender(List<CommonSelectItem> listVender) {
		this.listVender = listVender;
	}

	public List<CommonSelectItem> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<CommonSelectItem> listStatus) {
		this.listStatus = listStatus;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public List<CommonSelectItem> getListStandard() {
		return listStandard;
	}

	public void setListStandard(List<CommonSelectItem> listStandard) {
		this.listStandard = listStandard;
	}

	public MailControl getMailControl() {
		return mailControl;
	}

	public void setMailControl(MailControl mailControl) {
		this.mailControl = mailControl;
	}
	
}
