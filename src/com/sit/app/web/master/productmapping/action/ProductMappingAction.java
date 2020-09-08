package com.sit.app.web.master.productmapping.action;

import com.opensymphony.xwork2.ModelDriven;
import com.sit.app.core.master.productmapping.domain.ProductMappingModel;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.common.CommonAction;
import com.sit.domain.Transaction;
import com.sit.exception.AuthorizationException;
import com.sit.interfaces.InterfaceAction;

import util.database.CCTConnection;

public class ProductMappingAction extends CommonAction implements InterfaceAction, ModelDriven<ProductMappingModel>{

	private static final long serialVersionUID = -5983069085362072580L;

	private ProductMappingModel model = new ProductMappingModel();
	
	public ProductMappingAction() {
		try {
			getAuthorization(PFCode.PRODUCTMAPPING);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}
	
	@Override
	public String init() throws AuthorizationException{
		return ReturnType.INIT.getResult();
	}

	@Override
	public ProductMappingModel getModel() {
		return model;
	}

	@Override
	public void getComboForSearch(CCTConnection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String search() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gotoAdd() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gotoEdit() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String gotoView() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateActive() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String export() throws AuthorizationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		
	}
}
