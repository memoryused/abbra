package com.sit.app.web.dialog.master.product.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.app.core.dialog.master.product.domain.ProductDialogModel;
import com.sit.app.core.dialog.master.product.domain.ProductDialogSearchCriteria;
import com.sit.app.core.dialog.master.product.service.ProductDialogManager;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonDialogDatatableAction;
import com.sit.common.CommonDomain;
import com.sit.domain.SearchCriteria;

import util.database.CCTConnection;
import util.log.LogUtil;

public class ProductDialogAction extends CommonDialogDatatableAction{

	private static final long serialVersionUID = -8259029865370575304L;

	private ProductDialogModel model = new ProductDialogModel();
	
	public ProductDialogAction() {
		try {
			getAuthorization(PFCode.PRODUCT);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}
	
	@Override
	public List<CommonDomain> search(CCTConnection conn) throws Exception {
		return new ProductDialogManager(conn, getUser(), loggerInititial()).search((ProductDialogSearchCriteria) getModel().getCriteria());
	}
	
	@Override
	public List<CommonDomain> searchByIds(CCTConnection conn) throws Exception {
		return new ProductDialogManager(conn, getUser(), loggerInititial()).searchListById(getModel().getCriteria().getSelectedIds());
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
	public ProductDialogModel getModel() {
		return model;
	}
	
	@Override
	protected Logger loggerInititial() {
		return LogUtil.DIALOG;
	}
	
	@Override
	public SearchCriteria initSearchCriteria() {
		return new ProductDialogSearchCriteria();
	}

}
