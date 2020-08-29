package com.sit.app.core.master.vendor.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class VendorModel extends CommonModel{

	private static final long serialVersionUID = 659787092077451464L;

	private String criteriaKeyTemp;
	
	private VendorSearchCriteria criteria = new VendorSearchCriteria();
	
	// สำหรับหน้าเพิ่ม, หน้าแก้ไข และแสดง
	private Vendor vendor = new Vendor();
	
	//Combo box
	private List<CommonSelectItem> listVendor = new ArrayList<>();
	private List<CommonSelectItem> listStatus = new ArrayList<>();
	
	@XmlElement
	@Override
	public VendorSearchCriteria getCriteria() {
		return criteria;
	}
	
	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (VendorSearchCriteria) criteria;
	}

	public String getCriteriaKeyTemp() {
		return criteriaKeyTemp;
	}

	public void setCriteriaKeyTemp(String criteriaKeyTemp) {
		this.criteriaKeyTemp = criteriaKeyTemp;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<CommonSelectItem> getListVendor() {
		return listVendor;
	}

	public void setListVendor(List<CommonSelectItem> listVendor) {
		this.listVendor = listVendor;
	}

	public List<CommonSelectItem> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<CommonSelectItem> listStatus) {
		this.listStatus = listStatus;
	}
}
