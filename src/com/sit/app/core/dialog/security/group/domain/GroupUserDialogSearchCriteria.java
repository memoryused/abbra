package com.sit.app.core.dialog.security.group.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class GroupUserDialogSearchCriteria extends SearchCriteria {
	
	private static final long serialVersionUID = -1633522652749086603L;
	
	private  GroupUserDialog group = new GroupUserDialog();
	
	public GroupUserDialogSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
				new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0") 
				, new HeaderSorts("", HeaderSorts.ASC, "0") 
		};

		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	// กำหนด default header sorts ให้สำหรับการกด search ครั้งแรก
	public void setDefaultHeaderSorts() {
		for (int i = 0; i < getHeaderSortsSize(); i++) {
			getHeaderSorts()[i].setOrder(HeaderSorts.ASC);
			getHeaderSorts()[i].setSeq("0");
		}
		getHeaderSorts()[0].setSeq("0");
//		setHeaderSortsSelect("1");
	}
	

	public GroupUserDialog getGroup() {
		return group;
	}

	public void setGroup(GroupUserDialog group) {
		this.group = group;
	}

}
