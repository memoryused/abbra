package com.sit.app.core.product.productinfo.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class ProductInfoSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = 9094124996987183362L;

	public static final int TABLE_HEADER_SORTS_TYPE_1 = 1;
	
	private String productId; 
	private String productName; 
	private String venderId; 
	private String venderName; 
	private String status;
	
	public ProductInfoSearchCriteria() {
		HeaderSorts[] arrayHeaderSorts = {
				new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("", HeaderSorts.ASC, "0")
				, new HeaderSorts("item_short_name", HeaderSorts.ASC, "0")
				, new HeaderSorts("vendor_short_name", HeaderSorts.ASC, "0")
				, new HeaderSorts("active", HeaderSorts.ASC, "0")
		};
		
		setHeaderSorts(arrayHeaderSorts);
		setHeaderSortsSize(arrayHeaderSorts.length);
	}
	
	private static final HeaderSorts[] HEADER_SORTS_1 = { 
			new HeaderSorts("", HeaderSorts.ASC),
			new HeaderSorts("", HeaderSorts.ASC), 
			new HeaderSorts("", HeaderSorts.DESC),
			new HeaderSorts("", HeaderSorts.DESC), 
			new HeaderSorts("", HeaderSorts.DESC),
			new HeaderSorts("", HeaderSorts.DESC),
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
				setHeaderSortsSelect("2");
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
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
}
