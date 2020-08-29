package com.sit.app.core.master.vendor.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.master.vendor.domain.Vendor;
import com.sit.app.core.master.vendor.domain.VendorSearch;
import com.sit.app.core.master.vendor.domain.VendorSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.exception.MaxExceedException;

import util.database.CCTConnection;

public class VendorManager extends AbstractManager<VendorSearchCriteria, VendorSearch, Vendor, CommonUser>{

	private VendorService service;
	
	public VendorManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.service = new VendorService(conn, user, log);
	}

	@Override
	public List<VendorSearch> search(VendorSearchCriteria criteria) throws Exception {
		List<VendorSearch> listResult = new ArrayList<>();
		try {
			//COUNT SEARCH
			criteria.setTotalResult(service.countData(criteria));
			
			if (criteria.getTotalResult() == 0) {

			} else if ((criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceed())) {
				throw new MaxExceedException();
			} else {
				listResult = service.search(criteria);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return listResult;
	}

	@Override
	public Vendor searchById(String id) throws Exception {
		return service.searchById(id);
	}

	@Override
	public int add(Vendor obj) throws Exception {
		service.checkDup(obj);
		
		try {
			conn.setAutoCommit(false);
			
			service.add(obj);
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		return 0;
	}

	@Override
	public int edit(Vendor obj) throws Exception {
		service.checkDup(obj);
		
		try {
			conn.setAutoCommit(false);
			
			service.edit(obj);
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		return 0;
	}

	@Override
	public int delete(String ids) throws Exception {
		return service.delete(ids);
	}

	public int setActiveStatus(String ids, String activeFlag) throws Exception{
		return service.setActiveStatus(ids, activeFlag);
	}
	
	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
