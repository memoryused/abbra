package com.sit.app.core.dialog.master.vendor.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class VendorDialogSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = -4585736490920403825L;

	private VendorDialog vendor = new VendorDialog();
	
	public VendorDialogSearchCriteria() {
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

	public VendorDialog getVendor() {
		return vendor;
	}

	public void setVendor(VendorDialog vendor) {
		this.vendor = vendor;
	}
}
