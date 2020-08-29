package com.sit.app.core.master.product.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class ItemSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = 3131001927246417936L;

	public static final int TABLE_HEADER_SORTS_TYPE_1 = 1;
	
	private String itemId;
	private String itemCode;
	private String itemShortName;
	private String status;
	
	public ItemSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = {
				new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("ih.item_code", HeaderSorts.ASC, "0")
				, new HeaderSorts("ih.item_short_name", HeaderSorts.ASC, "0")
				, new HeaderSorts("ih.active", HeaderSorts.ASC, "0")
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
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemShortName() {
		return itemShortName;
	}

	public void setItemShortName(String itemShortName) {
		this.itemShortName = itemShortName;
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
