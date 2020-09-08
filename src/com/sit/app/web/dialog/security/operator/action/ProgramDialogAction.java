package com.sit.app.web.dialog.security.operator.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.dialog.security.operator.domain.ProgramDialogModel;
import com.sit.app.core.dialog.security.operator.service.OperatorDialogManager;
import com.sit.common.CommonDialogAction;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;
import util.web.TreeUtil;

public class ProgramDialogAction extends CommonDialogAction {

	private static final long serialVersionUID = -6954524755932806616L;

	private ProgramDialogModel model = new ProgramDialogModel();

	public String search() {
		loggerInititial().debug("searchTree");

		String treeId = "";
		if (ServletActionContext.getRequest().getParameter("treeId") != null) {
			treeId = ServletActionContext.getRequest().getParameter("treeId").trim();
		}

		String treeType = "";
		if (ServletActionContext.getRequest().getParameter("treeType") != null) {
			treeType = ServletActionContext.getRequest().getParameter("treeType").trim();
		}

		String event = "";
		if (ServletActionContext.getRequest().getParameter("event") != null) {
			event = ServletActionContext.getRequest().getParameter("event").trim();
		}

		Map<String, com.sit.domain.Tree> mapTree = new LinkedHashMap<String, com.sit.domain.Tree>();
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			mapTree = new OperatorDialogManager(conn, getUser()).searchMenuTree();
		} catch (Exception e) {
			setMessagePopup(getModel(), null, e.toString());
			loggerInititial().error("", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}

		if (mapTree.size() == 0) {
			getModel().getTreeData().setMapTree(null);
			getModel().getTreeData().setHtmlTree(null);
		} else {
			getModel().getTreeData().setMapTree(mapTree);
			getModel().getTreeData().setHtmlTree(new TreeUtil(treeId, treeType, mapTree, event).genarateTree());
		}

		return "searchListTree";
	}

	public ProgramDialogModel getModel() {
		return model;
	}

	@Override
	protected Logger loggerInititial() {
		return LogUtil.DIALOG;
	}


}
