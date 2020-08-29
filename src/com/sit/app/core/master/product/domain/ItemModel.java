package com.sit.app.core.master.product.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class ItemModel extends CommonModel{

	private static final long serialVersionUID = -6396198398925141452L;

	private String criteriaKeyTemp;

	private ItemSearchCriteria criteria = new ItemSearchCriteria();
	
	// สำหรับหน้าเพิ่ม, หน้าแก้ไข และแสดง
	private Item item = new Item();
	
	//Combo box
	private List<CommonSelectItem> listProduct = new ArrayList<>();
	private List<CommonSelectItem> listStatus = new ArrayList<>();
		
	public String getCriteriaKeyTemp() {
		return criteriaKeyTemp;
	}

	public void setCriteriaKeyTemp(String criteriaKeyTemp) {
		this.criteriaKeyTemp = criteriaKeyTemp;
	}

	@XmlElement
	@Override
	public ItemSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ItemSearchCriteria) criteria;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<CommonSelectItem> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<CommonSelectItem> listProduct) {
		this.listProduct = listProduct;
	}

	public List<CommonSelectItem> getListStatus() {
		return listStatus;
	}

	public void setListStatus(List<CommonSelectItem> listStatus) {
		this.listStatus = listStatus;
	} 
}
