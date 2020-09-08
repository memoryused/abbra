package com.sit.app.core.master.vendor.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class VendorSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = 1L;

	public static final int TABLE_HEADER_SORTS_TYPE_1 = 1;
	
	private String vendorId;
	private String vendorCode;
	private String vendorName;
	private String vendorShortName;
	private String status;
	
	public VendorSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = {
				new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("vh.vendor_code", HeaderSorts.ASC, "0")
				, new HeaderSorts("vh.vendor_name", HeaderSorts.ASC, "0")
				, new HeaderSorts("vh.active", HeaderSorts.ASC, "0")
		};
		
		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}
	
	private static final HeaderSorts[] HEADER_SORTS_1 = { 
			new HeaderSorts("", HeaderSorts.ASC),
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC)
	};
	
	public void setDefaultHeaderSorts() {
		switch (getCriteriaType()) {
			case TABLE_HEADER_SORTS_TYPE_1:
				setHeaderSortsSelect("");
				break;
			default:{
				for (int i = 0; i < getHeaderSortsSize(); i++) {
					getHeaderSorts()[i].setOrder(HeaderSorts.ASC);
					getHeaderSorts()[i].setSeq("");
				}
				getHeaderSorts()[0].setSeq("0");
				setHeaderSortsSelect("3");
			}
		}
	}

	public void initHeaderSorts() {
		HeaderSorts[] arrayHeaderSorts = null;

		switch (getCriteriaType()) {
			case TABLE_HEADER_SORTS_TYPE_1:	{
				arrayHeaderSorts = HEADER_SORTS_1;
				setHeaderSorts(arrayHeaderSorts);
				setHeaderSortsSize(arrayHeaderSorts.length);
				
				break;
			}
			default:
				break;
		}
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorShortName() {
		return vendorShortName;
	}

	public void setVendorShortName(String vendorShortName) {
		this.vendorShortName = vendorShortName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static int getTableHeaderSortsType1() {
		return TABLE_HEADER_SORTS_TYPE_1;
	}
}
