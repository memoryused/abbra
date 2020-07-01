package com.sit.app.web.product.home.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.product.home.domain.ProductHomeModel;
import com.sit.app.core.product.home.domain.ProductHomeSearch;
import com.sit.app.core.product.home.domain.ProductHomeSearchCriteria;
import com.sit.app.core.product.home.service.ProductHomeManager;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonAction;
import com.sit.common.CommonModel.PageType;
import com.sit.domain.Transaction;
import com.sit.exception.AuthorizationException;
import com.sit.interfaces.InterfaceAction;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;

public class HomeAction extends CommonAction implements InterfaceAction, ModelDriven<ProductHomeModel>{

	private static final long serialVersionUID = -8282020968376725217L;

	private ProductHomeModel model = new ProductHomeModel();
	
	public HomeAction() {
		try {
			getAuthorization(PFCode.PRODUCTHOME);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}
	
	@Override
	public String init() throws AuthorizationException {
		loggerInititial().info("INIT");
		
		String result = null;

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// ตรวจสอบสิทธิ์การใช้งาน และกำหนดค่าเริ่มต้นให้กับหน้าค้นหาของระบบ
			result = manageInitial(conn, model, new ProductHomeSearchCriteria(), PF_CODE.getSearchFunction(), PageType.SEARCH);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, model);

		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public ProductHomeModel getModel() {
		return model;
	}

	@Override
	public void getComboForSearch(CCTConnection conn) {
		SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
		
		try {
			model.setListProduct(manager.searchProductSelectItem());
			model.setListVender(manager.searchVenderSelectItem());
			model.setListDocumentType(manager.searchDocumentTypeSelectItem());
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		
	}

	@Override
	public String search() throws AuthorizationException {
		loggerInititial().info("search");
		
		String result = null;

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			result = manageSearchAjax(conn, model, model.getCriteria(), PF_CODE.getSearchFunction());

			ProductHomeManager manager = new ProductHomeManager(conn, getUser(), loggerInititial());

			List<ProductHomeSearch> lstResult = manager.search(model.getCriteria());

			// จัดการผลลัพธ์และข้อความถ้าไม่พบข้อมูล
			manageSearchResult(model, lstResult);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, model);

		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public String gotoAdd() throws AuthorizationException {
		return null;
	}

	@Override
	public String add() throws AuthorizationException {
		return null;
	}

	@Override
	public String edit() throws AuthorizationException {
		return null;
	}

	@Override
	public String gotoEdit() throws AuthorizationException {
		return null;
	}

	@Override
	public String gotoView() throws AuthorizationException {
		return null;
	}

	@Override
	public String updateActive() throws AuthorizationException {
		return null;
	}

	@Override
	public String delete() throws AuthorizationException {
		return null;
	}

	@Override
	public String export() throws AuthorizationException {
		return null;
	}

	@Override
	public void showTransaction(Transaction transaction) {
		
	}

	@Override
	protected Logger loggerInititial() {
		return LogUtil.PRODUCT_HOME;
	}
	
}
