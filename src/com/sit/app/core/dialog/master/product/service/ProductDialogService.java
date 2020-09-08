package com.sit.app.core.dialog.master.product.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.dialog.master.product.domain.ProductDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;

import util.database.CCTConnection;

public class ProductDialogService extends AbstractService{

	private ProductDialogDAO dao;
	
	public ProductDialogService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new ProductDialogDAO(log);
		dao.setSqlPath(SQLPath.DIALOG_ITEM_SQL);
	}

	protected long countData(ProductDialogSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<CommonDomain> search(ProductDialogSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria, user);
	}

	protected List<CommonDomain> searchListById(String ids) throws Exception {
		return dao.searchListById(conn, ids, user);
	}

}
