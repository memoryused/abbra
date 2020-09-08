package com.sit.app.web.dialog.security.user.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.app.core.dialog.security.user.domain.UserDialogModel;
import com.sit.app.core.dialog.security.user.domain.UserDialogSearchCriteria;
import com.sit.app.core.dialog.security.user.service.UserDialogManager;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonDialogDatatableAction;
import com.sit.common.CommonDomain;
import com.sit.domain.SearchCriteria;

import util.database.CCTConnection;
import util.log.LogUtil;

public class UserDialogAction extends CommonDialogDatatableAction{

	private static final long serialVersionUID = 8451519183796162744L;
	
	private UserDialogModel model = new UserDialogModel();
	
	public UserDialogAction() {
		try {
			getAuthorization(PFCode.MEMBER);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}
	
	@Override
	public List<CommonDomain> search(CCTConnection conn) throws Exception {
		return new UserDialogManager(conn, getUser(), loggerInititial()).search((UserDialogSearchCriteria) getModel().getCriteria());
	}
	
	@Override
	public List<CommonDomain> searchByIds(CCTConnection conn) throws Exception {
		return new UserDialogManager(conn, getUser(), loggerInititial()).searchListById(getModel().getCriteria().getSelectedIds());
	}
	
	@Override
	public void getComboForSearch(CCTConnection conn) {
		try {
			SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
			
			//load combo Password Status
			model.setListOrganization(manager.searchOrganizationSelectItem());
			
			//load combo Organization
			//model.setListPwdStatus(manager.searchPasswordStatusSelectItem());
			
		} catch (Exception e) {
			LogUtil.DIALOG.error("",e);
		}
	}
	
	@Override
	public UserDialogModel getModel() {
		return model;
	}
	
	@Override
	protected Logger loggerInititial() {
		return LogUtil.DIALOG;
	}
	
	@Override
	public SearchCriteria initSearchCriteria() {
		return new UserDialogSearchCriteria();
	}
}
