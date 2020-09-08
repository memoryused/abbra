package com.sit.app.web.dialog.master.vendor.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.app.core.dialog.master.vendor.domain.VendorDialogModel;
import com.sit.app.core.dialog.master.vendor.domain.VendorDialogSearchCriteria;
import com.sit.app.core.dialog.master.vendor.service.VendorDialogManager;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonDialogDatatableAction;
import com.sit.common.CommonDomain;
import com.sit.domain.SearchCriteria;

import util.database.CCTConnection;
import util.log.LogUtil;

public class VendorDialogAction extends CommonDialogDatatableAction{

	private static final long serialVersionUID = -8259029865370575304L;

	private VendorDialogModel model = new VendorDialogModel();
	
	public VendorDialogAction() {
		try {
			getAuthorization(PFCode.VENDOR);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}
	
	@Override
	public List<CommonDomain> search(CCTConnection conn) throws Exception {
		return new VendorDialogManager(conn, getUser(), loggerInititial()).search((VendorDialogSearchCriteria) getModel().getCriteria());
	}
	
	@Override
	public List<CommonDomain> searchByIds(CCTConnection conn) throws Exception {
		return new VendorDialogManager(conn, getUser(), loggerInititial()).searchListById(getModel().getCriteria().getSelectedIds());
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
	public VendorDialogModel getModel() {
		return model;
	}
	
	@Override
	protected Logger loggerInititial() {
		return LogUtil.DIALOG;
	}
	
	@Override
	public SearchCriteria initSearchCriteria() {
		return new VendorDialogSearchCriteria();
	}

}
