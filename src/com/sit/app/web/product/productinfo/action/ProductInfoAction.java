package com.sit.app.web.product.productinfo.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.product.productinfo.domain.ProductInfoDialog;
import com.sit.app.core.product.productinfo.domain.ProductInfoModel;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearch;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearchCriteria;
import com.sit.app.core.product.productinfo.domain.StandardInfo;
import com.sit.app.core.product.productinfo.service.ProductInfoManager;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonAction;
import com.sit.common.CommonModel.PageType;
import com.sit.domain.SearchCriteria;
import com.sit.domain.Transaction;
import com.sit.exception.AuthorizationException;
import com.sit.interfaces.InterfaceAction;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.web.SessionUtil;

public class ProductInfoAction extends CommonAction implements InterfaceAction, ModelDriven<ProductInfoModel>{

	private static final long serialVersionUID = 6134200086854847766L;

	private ProductInfoModel model = new ProductInfoModel();
	
	public ProductInfoAction() {
		try {
			getAuthorization(PFCode.PRODUCTINFO);
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
			result = manageInitial(conn, model, new ProductInfoSearchCriteria(), PF_CODE.getSearchFunction(), PageType.SEARCH);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, model);

		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public ProductInfoModel getModel() {
		return model;
	}

	@Override
	public void getComboForSearch(CCTConnection conn) {
		SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
		
		try {
			model.setListProduct(manager.searchProductSelectItem());
			model.setListVender(manager.searchVenderSelectItem());
			model.setListStatus(manager.searchStatusSelectItem());
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
		
	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
		
		try {
			model.setListStandard(manager.searchStandardSelectItem(model.getProductInfo().getDocumentId()));
			model.setMailControl(manager.searchMailControl());
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
		
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

			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			List<ProductInfoSearch> lstResult = manager.search(model.getCriteria());

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
		loggerInititial().info("gotoAddEdit");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			// 1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// 2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			result = manageGotoAdd(conn, model);
			result = ReturnType.SEARCH_AJAX.getResult();
			
			String certId = model.getProductInfo().getCertId();
			String docTransId = model.getProductInfo().getDocTransId();
			loggerInititial().debug(certId + ", " + docTransId);
			
			if(!docTransId.isEmpty()) {
				docTransId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), docTransId);
				if (StringUtil.stringToNull(docTransId) == null) {
					throw new AuthorizationException();
				}
			}
			
			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			model.getProductInfo().setDialog(manager.searchTransDByCertId(certId, docTransId));
			
		} catch (Exception e) {
			// 3.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getAddFunction(), this, e, model);
		} finally {
			getComboForAddEdit(conn);
			CCTConnectionUtil.close(conn);
		}
		return result;
	}

	@Override
	public String add() throws AuthorizationException {
		return null;
	}

	public String save() throws AuthorizationException {
		loggerInititial().info("save");
		
		String result = null;
		CCTConnection conn = null;

		try {
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getProductInfo().getVenderItemId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			result = manageAdd(conn, model);
			result = ReturnType.SEARCH_AJAX.getResult();

			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			model.getProductInfo().setVenderItemId(refId);
			
			//3.บันทึกเพิ่มข้อมูล
			manager.add(model.getProductInfo());

			//4.เคลียร์ค่าหน้าเพิ่มทั้งหมด
			model.getProductInfo().setDialog(new ProductInfoDialog());
			
		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getAddFunction(), this, e, model);

		} finally {
			//7.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}

		return result;
	}
	
	@Override
	public String edit() throws AuthorizationException {
		return null;
	}
	
	public String modalEdit() throws AuthorizationException {
		loggerInititial().info("modalEdit");
		
		String result = null;
		CCTConnection conn = null;

		try {
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getProductInfo().getVenderItemId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			result = manageEdit(conn, model);
			result = ReturnType.SEARCH_AJAX.getResult();

			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			model.getProductInfo().setVenderItemId(refId);
			
			//3.บันทึกเพิ่มข้อมูล
			manager.edit(model.getProductInfo());

			//4.เคลียร์ค่าหน้าเพิ่มทั้งหมด
			model.getProductInfo().setDialog(new ProductInfoDialog());
			
		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getEditFunction(), this, e, model);

		} finally {
			//7.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public String gotoEdit() throws AuthorizationException {
		loggerInititial().info("gotoEdit");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getProductInfo().getVenderItemId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าแก้ไข
			result = manageGotoEdit(conn, model);
			
			//3.ค้นหาข้อมูลตาม id ที่เลือกมาจากหน้าจอ
			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), LogUtil.PRODUCT_INFO);
			model.setProductInfo(manager.searchById(refId));
			
		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getEditFunction(), this, e, model);

		} finally {
			//6.Load combo ทั้งหมดที่ใช้ในหน้าแก้ไข
			getComboForAddEdit(conn);

			//7.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);

		}
		
		return result;
	}
	
	public String searchByStandard() throws AuthorizationException {
		loggerInititial().info("searchByStandard");
		
		String result = null;

		CCTConnection conn = null;
		try {
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getProductInfo().getVenderItemId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
			//result = manageSearchAjax(conn, model, model.getCriteria(), PF_CODE.getEditFunction());
			result = ReturnType.SEARCH_AJAX.getResult();

			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			List<StandardInfo> lstResult = manager.searchByStandard(refId, model.getProductInfo().getDocumentId());
			model.getCriteria().setTotalResult(lstResult.size());
			
			// จัดการผลลัพธ์และข้อความถ้าไม่พบข้อมูล
			manageSearchResult(model, lstResult);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getEditFunction(), this, e, model);

		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	/**
	 * สำหรับปิดหน้าจอ และยกเลิก
	 */
	public String cancel() throws AuthorizationException {
		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			SearchCriteria criteria = retrieveCriteria(model.getCriteriaKeyTemp());
			
			model.setCriteria(criteria);

		} catch (Exception e) {
			manageException(conn, getPF_CODE().getSearchFunction(), this, e, model);

		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}
		return ReturnType.SEARCH.getResult();
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
	
	public String changeActive() throws AuthorizationException {
		loggerInititial().info("changeActive");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			manageUpdateActive(conn, model, ResultType.BASIC);
			
			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			
			//3.update สภานะ
			if(model.getCriteria().getSelectedIds()!=null && !model.getCriteria().getSelectedIds().equals("")){
				try{
					manager.setActiveStatus(model.getCriteria().getSelectedIds(), model.getCriteria().getStatusForUpdate());
				} catch(Exception e) {
					manageException(conn, PF_CODE.getChangeFunction(), this, e, model);
				}
			}

			result = manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			List<ProductInfoSearch> lstResult = manager.search(model.getCriteria());
			manageSearchResult(model, lstResult);

		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getChangeFunction(), this, e, model);

		} finally {
			//7.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}

		return result;
	} 
	
	public String changeDelete() throws AuthorizationException {
		loggerInititial().info("changeDelete");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			manageDelete(conn, model, ResultType.BASIC);
			
			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			
			//3.update Deleted flag
			if(model.getCriteria().getSelectedIds()!=null && !model.getCriteria().getSelectedIds().equals("")){
				try{
					manager.deleteDocVenderItemMap(model.getCriteria().getSelectedIds());
				} catch(Exception e) {
					manageException(conn, PF_CODE.getDeleteFunction(), this, e, model);
				}
			}

			result = manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			List<ProductInfoSearch> lstResult = manager.search(model.getCriteria());
			manageSearchResult(model, lstResult);

		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getChangeFunction(), this, e, model);

		} finally {
			//7.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}

		return result;
	} 

	@Override
	public String delete() throws AuthorizationException {
		return null;
	}
	
	public String remove() throws AuthorizationException {
		loggerInititial().info("remove");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			manageDelete(conn, model, ResultType.BASIC);
			result = ReturnType.SEARCH_AJAX.getResult();
			
			String[] docTransArrTemp = model.getCriteria().getSelectedIds().split(",", -1);
			List<String> selectedIds = new ArrayList<String>();
			
			for (String dTemp : docTransArrTemp) {
				String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), dTemp.trim());
				if (StringUtil.stringToNull(refId) == null) {
					throw new AuthorizationException();
				} else {
					selectedIds.add(refId);
				}
			}
			
			//3.Delete
			ProductInfoManager manager = new ProductInfoManager(conn, getUser(), loggerInititial());
			manager.delete(String.join(",", selectedIds));

		} catch (Exception e) {

			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getDeleteFunction(), this, e, model);

		} finally {

			//7.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}

		return result;
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

	@Override
	protected Logger loggerInititial() {
		return LogUtil.PRODUCT_INFO;
	}
	
}
