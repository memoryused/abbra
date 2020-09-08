package com.sit.app.web.dialog.security.group.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.app.core.dialog.security.group.domain.GroupUserDialogModel;
import com.sit.app.core.dialog.security.group.domain.GroupUserDialogSearchCriteria;
import com.sit.app.core.dialog.security.group.service.GroupUserDialogManager;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonDialogDatatableAction;
import com.sit.common.CommonDomain;
import com.sit.domain.SearchCriteria;

import util.database.CCTConnection;
import util.log.LogUtil;

public class GroupUserDialogAction extends CommonDialogDatatableAction{

	private static final long serialVersionUID = -4735186884979719137L;
	
	private GroupUserDialogModel model = new GroupUserDialogModel();
	
	public GroupUserDialogAction() {
		try {
			getAuthorization(PFCode.MEMBER_GROUP);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}
	
	@Override
	public List<CommonDomain> search(CCTConnection conn) throws Exception {
		return new GroupUserDialogManager(conn, getUser(), loggerInititial()).search((GroupUserDialogSearchCriteria) getModel().getCriteria());
	}
	
	@Override
	public List<CommonDomain> searchByIds(CCTConnection conn) throws Exception {
		return new GroupUserDialogManager(conn, getUser(), loggerInititial()).searchListById(getModel().getCriteria().getSelectedIds());
	}
	
	@Override
	public void getComboForSearch(CCTConnection conn) {
		try {
			SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
			//load combo Active
			model.setListActiveStatus(manager.searchStatusSelectItem());
			
		} catch (Exception e) {
			loggerInititial().error("",e);
		}
	}
	
	@Override
	public GroupUserDialogModel getModel() {
		return model;
	}
	
	@Override
	protected Logger loggerInititial() {
		return LogUtil.DIALOG;
	}
	
	@Override
	public SearchCriteria initSearchCriteria() {
		return new GroupUserDialogSearchCriteria();
	}

}
