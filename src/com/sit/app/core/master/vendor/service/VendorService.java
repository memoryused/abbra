package com.sit.app.core.master.vendor.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.master.vendor.domain.Vendor;
import com.sit.app.core.master.vendor.domain.VendorSearch;
import com.sit.app.core.master.vendor.domain.VendorSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.exception.DuplicateException;

import util.database.CCTConnection;

public class VendorService extends AbstractService{

	private VendorDAO dao;
	
	public VendorService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new VendorDAO(log);
		this.dao.setSqlPath(SQLPath.VENDOR);
	}
	
	protected long countData(VendorSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<VendorSearch> search(VendorSearchCriteria criteria) throws Exception{
		return dao.search(conn, criteria, user);
	}
	
	protected Vendor searchById(String id) throws Exception{
		return dao.searchById(conn, id, user);
	}
	
	protected void checkDup(Vendor obj) throws Exception {
		try {
			boolean isDup = dao.checkDup(conn, obj, user);
			log.debug("isDup: " + isDup);
			if (isDup) {
				throw new DuplicateException(); // Throw DuplicateException ถ้าพบว่าข้อมูลซ้ำ
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	protected int add(Vendor obj) throws Exception {
		return dao.add(conn, obj, user);
	}
	
	protected int edit(Vendor obj) throws Exception {
		return dao.edit(conn, obj, user);
	}
	
	protected int setActiveStatus(String ids, String activeFlag) throws Exception {
		return dao.setActiveStatus(conn, ids, activeFlag, user);
	}
	
	protected int delete(String ids) throws Exception {
		return dao.delete(conn, ids, user);
	}
}
