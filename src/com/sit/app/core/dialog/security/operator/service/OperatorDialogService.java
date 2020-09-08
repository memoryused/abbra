package com.sit.app.core.dialog.security.operator.service;

import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.common.CommonUser;
import com.sit.domain.Tree;

import util.database.CCTConnection;

public class OperatorDialogService extends AbstractService {
	
	private OperatorDialogDAO dao = null;
	
	public OperatorDialogService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		dao = new OperatorDialogDAO(log);
		dao.setSqlPath(SQLPath.DIALOG_OPERATOR_SQL);
	}
	
	protected Map<String, Tree> searchMenuProgramTree() throws Exception {
		Tree tree = dao.searchLevelProgram(conn, user);
		return dao.searchMenuProgramTree(conn, user, tree);
	}

	protected Map<String, Tree> searchMenuReportTree() throws Exception {
		Tree tree = dao.searchLevelReport(conn, user);
		return dao.searchMenuReportTree(conn, user, tree);
	}


}
