package com.sit.app.core.dialog.master.product.domain;

import com.sit.domain.HeaderSorts;
import com.sit.domain.SearchCriteria;

public class ProductDialogSearchCriteria extends SearchCriteria{

	private static final long serialVersionUID = -215455101915389943L;

	private ProductDialog product = new ProductDialog();
	
	public ProductDialogSearchCriteria() {
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

	public ProductDialog getProduct() {
		return product;
	}

	public void setProduct(ProductDialog product) {
		this.product = product;
	}
}
