package com.sit.app.core.dialog.security.group.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.dialog.security.group.domain.GroupUserDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;

import util.database.CCTConnection;

public class GroupUserDialogService extends AbstractService {
	
	private GroupUserDialogDAO dao;

	public GroupUserDialogService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		dao = new GroupUserDialogDAO(log);
		dao.setSqlPath(SQLPath.DIALOG_GROUP_SQL);
	}

	protected long countData(GroupUserDialogSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<CommonDomain> search(GroupUserDialogSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria, user);
	}

	protected List<CommonDomain> searchListById(String ids) throws Exception {
		return dao.searchListById(conn, ids, user);
	}


}
