package com.sit.app.core.dialog.security.user.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.dialog.security.user.domain.UserDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;

import util.database.CCTConnection;

public class UserDialogService extends AbstractService {
	
	private UserDialogDAO dao;

	public UserDialogService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new UserDialogDAO(log);
		dao.setSqlPath(SQLPath.DIALOG_USER_SQL);
	}

	protected long countData(UserDialogSearchCriteria criteriaPopup) throws Exception {
		return dao.countData(conn, criteriaPopup, user);
	}

	protected List<CommonDomain> search(UserDialogSearchCriteria criteriaPopup) throws Exception {
		return dao.search(conn, criteriaPopup, user);
	}

	protected List<CommonDomain> searchListById(String ids) throws Exception {
		return dao.searchListById(conn, ids, user);
	}
                                                                                                                                                                                                                                                                                                                                                                                
}
