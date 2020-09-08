package com.sit.app.core.security.membergroup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.sit.common.CommonModel;
import com.sit.common.CommonSelectItem;
import com.sit.domain.SearchCriteria;

public class GroupModel extends CommonModel{

	private static final long serialVersionUID = 2645572898449666157L;

	private String criteriaKeyTemp;

	// สำหรับเก็บเงื่อนไขการค้นหา
	private GroupSearchCriteria criteria = new GroupSearchCriteria();

	// สำหรับหน้าเพิ่ม, หน้าแก้ไข และแสดง
	private GroupData data = new GroupData();

	// combo สถานะใช้งาน
	private List<CommonSelectItem> listActiveStatus = new ArrayList<CommonSelectItem>();
		
	@XmlElement
	@Override
	public GroupSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = (GroupSearchCriteria) criteria;
	}

	public List<CommonSelectItem> getListActiveStatus() {
		return listActiveStatus;
	}

	public void setListActiveStatus(List<CommonSelectItem> listActiveStatus) {
		this.listActiveStatus = listActiveStatus;
	}

	public void setCriteria(GroupSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public GroupData getData() {
		return data;
	}

	public void setData(GroupData data) {
		this.data = data;
	}
	
	public String getCriteriaKeyTemp() {
		return criteriaKeyTemp;
	}

	public void setCriteriaKeyTemp(String criteriaKeyTemp) {
		this.criteriaKeyTemp = criteriaKeyTemp;
	}
}
