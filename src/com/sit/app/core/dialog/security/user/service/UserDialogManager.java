package com.sit.app.core.dialog.security.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.dialog.security.user.domain.UserDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;
import com.sit.exception.MaxExceedAlertException;

import util.database.CCTConnection;
import util.log.LogUtil;

public class UserDialogManager extends AbstractManager<UserDialogSearchCriteria, CommonDomain, CommonDomain, CommonUser> {
	
	private UserDialogService service = null;

	public UserDialogManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		service = new UserDialogService(conn, user, log);
	}

	public List<CommonDomain> search(UserDialogSearchCriteria criteriaPopup) throws Exception {
	List<CommonDomain> listResult = new ArrayList<CommonDomain>();
		
		try {
			criteriaPopup.setTotalResult(service.countData(criteriaPopup));
			LogUtil.DIALOG.debug("Count data [" + criteriaPopup.getTotalResult() + "] record.");

			if (criteriaPopup.getTotalResult() == 0) {

			} else if ((criteriaPopup.isCheckMaxExceed()) && (criteriaPopup.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceedPopup())) {
				throw new MaxExceedAlertException();
			} else {
				// ค้นหาข้อมูล
				listResult = service.search(criteriaPopup);
			}
			
			
		} catch (Exception e) {
			throw e;
		}
		
		return listResult;
	}

	public List<CommonDomain> searchListById(String ids) throws Exception {
		List<CommonDomain> listResult = null;
		try {
			listResult = service.searchListById(ids);
		} catch (Exception e) {
			throw e;
		}

		return listResult;
	}

	@Override
	public CommonDomain searchById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(CommonDomain obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(CommonDomain obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
