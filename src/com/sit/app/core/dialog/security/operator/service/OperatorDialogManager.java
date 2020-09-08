package com.sit.app.core.dialog.security.operator.service;

import java.util.Map;

import com.sit.common.CommonUser;
import com.sit.domain.Tree;

import util.database.CCTConnection;
import util.log.LogUtil;

public class OperatorDialogManager {
	
	private OperatorDialogService service = null;
	
	public OperatorDialogManager(CCTConnection conn, CommonUser user) {
		service = new OperatorDialogService(conn, user, LogUtil.DIALOG);
	}

	public Map<String, Tree> searchMenuTree() throws Exception {
		return service.searchMenuProgramTree();
	}

	public Map<String, Tree> searchReportTree() throws Exception {
		return service.searchMenuReportTree();
	}

}
