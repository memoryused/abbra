package com.sit.app.core.dialog.master.vendor.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.dialog.master.vendor.domain.VendorDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;

import util.database.CCTConnection;

public class VendorDialogService extends AbstractService{

	private VendorDialogDAO dao;
	
	public VendorDialogService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new VendorDialogDAO(log);
		dao.setSqlPath(SQLPath.DIALOG_VENDOR_SQL);
	}

	protected long countData(VendorDialogSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<CommonDomain> search(VendorDialogSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria, user);
	}

	protected List<CommonDomain> searchListById(String ids) throws Exception {
		return dao.searchListById(conn, ids, user);
	}
}
