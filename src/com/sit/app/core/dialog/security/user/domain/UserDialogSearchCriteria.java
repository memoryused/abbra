package com.sit.app.core.dialog.security.user.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class UserDialogSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = -7196894830575464194L;
	
	private UserDialog user = new UserDialog();
	
	public UserDialogSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = { 
				new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0") 
			};
		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}

	public void setDefaultHeaderSorts() {
		for (int i = 0; i < getHeaderSortsSize(); i++) {
			getHeaderSorts()[i].setOrder(HeaderSorts.ASC);
			getHeaderSorts()[i].setSeq("");
		}
		getHeaderSorts()[2].setSeq("0");
		setHeaderSortsSelect("2");
	}

	public UserDialog getUser() {
		return user;
	}

	public void setUser(UserDialog user) {
		this.user = user;
	}
	
} 
