package com.sit.app.core.product.home.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class ProductHomeModel extends CommonModel{

	private static final long serialVersionUID = 6949135957102367663L;

	private ProductHomeSearchCriteria criteria = new ProductHomeSearchCriteria();

	private String criteriaKeyTemp; 
	
	//Combo box
	private List<CommonSelectItem> listProduct = new ArrayList<>();
	private List<CommonSelectItem> listVender = new ArrayList<>();
	private List<CommonSelectItem> listDocumentType = new ArrayList<>();
	
	@XmlElement 
	@Override
	public ProductHomeSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (ProductHomeSearchCriteria) criteria;
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

	public List<CommonSelectItem> getListDocumentType() {
		return listDocumentType;
	}

	public void setListDocumentType(List<CommonSelectItem> listDocumentType) {
		this.listDocumentType = listDocumentType;
	}

	public String getCriteriaKeyTemp() {
		return criteriaKeyTemp;
	}

	public void setCriteriaKeyTemp(String criteriaKeyTemp) {
		this.criteriaKeyTemp = criteriaKeyTemp;
	}
}
