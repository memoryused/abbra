package com.sit.app.web.master.product.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.master.product.domain.Item;
import com.sit.app.core.master.product.domain.ItemModel;
import com.sit.app.core.master.product.domain.ItemSearch;
import com.sit.app.core.master.product.domain.ItemSearchCriteria;
import com.sit.app.core.master.product.service.ItemManager;
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

public class ProductAction extends CommonAction implements InterfaceAction, ModelDriven<ItemModel>{

	private static final long serialVersionUID = -9045677964496488749L;

	private ItemModel model = new ItemModel();
	
	public ProductAction() {
		try {
			getAuthorization(PFCode.PRODUCT);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}
	
	@Override
	public String init() throws AuthorizationException{
		loggerInititial().info("INIT");
		
		String result = null;

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// ตรวจสอบสิทธิ์การใช้งาน และกำหนดค่าเริ่มต้นให้กับหน้าค้นหาของระบบ
			result = manageInitial(conn, model, new ItemSearchCriteria(), PF_CODE.getSearchFunction(), PageType.SEARCH);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, model);

		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public ItemModel getModel() {
		return model;
	}

	@Override
	public void getComboForSearch(CCTConnection conn) {
		SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
		
		try {
			model.setListProduct(manager.searchProductSelectItem());
			model.setListStatus(manager.searchStatusSelectItem());
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
		
		try {
			model.setListStatus(manager.searchStatusSelectItem());
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

			ItemManager manager = new ItemManager(conn, getUser(), loggerInititial());
			List<ItemSearch> lstResult = manager.search(model.getCriteria());

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
		loggerInititial().info("gotoAdd");
		
		String result = null;
		CCTConnection conn = null;

		try {
			// 1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// 2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			result = manageGotoAdd(conn, model);

		} catch (Exception e) {
			// 3.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getAddFunction(), this, e, model);
		} finally {
			// 4.Load combo ทั้งหมดที่ใช้ในหน้าเพิ่ม
			getComboForAddEdit(conn);
			// 5.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}
		return result;
	}

	@Override
	public String add() throws AuthorizationException {
		loggerInititial().info("add");
		
		String result = null;
		CCTConnection conn = null;

		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			result = manageAdd(conn, model);

			//3.บันทึกเพิ่มข้อมูล
			ItemManager manager = new ItemManager(conn, getUser(), loggerInititial());
			manager.add(model.getItem());

			//4.เคลียร์ค่าหน้าเพิ่มทั้งหมด
			model.setItem(new Item());		

		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getAddFunction(), this, e, model);
			result = ReturnType.ADD_EDIT.getResult();
		} finally {
			//6.Load combo ทั้งหมดที่ใช้ในหน้าเพิ่ม
			getComboForAddEdit(conn);
			//7.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public String edit() throws AuthorizationException {
		loggerInititial().info("edit");
		
		String result = null;
		CCTConnection conn = null;

		try {
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getItem().getItemId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			
			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			result = manageEdit(conn, model, ResultType.BASIC);

			//3.บันทึกเพิ่มข้อมูล
			ItemManager manager = new ItemManager(conn, getUser(), loggerInititial());
			model.getItem().setItemId(refId);
			manager.edit(model.getItem());

			//4.เคลียร์ค่าหน้าเพิ่มทั้งหมด
			model.setItem(new Item());		

			//retrieve Criteria temp
			SearchCriteria criteria = retrieveCriteria(model.getCriteriaKeyTemp());
			model.setCriteria(criteria);

			result = ReturnType.SEARCH.getResult();
		} catch (Exception e) {
			getComboForAddEdit(conn);
			
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getEditFunction(), this, e, model);
			result = ReturnType.ADD_EDIT.getResult();
		} finally {
			//6.Load combo 
			getComboForSearch(conn);
			
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
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getItem().getItemId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าแก้ไข
			result = manageGotoEdit(conn, model);
			
			//3.ค้นหาข้อมูลตาม id ที่เลือกมาจากหน้าจอ
			ItemManager manager = new ItemManager(conn, getUser(), loggerInititial());
			model.setItem(manager.searchById(refId));
			
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

			//2.ตรวจสอบสิทธิ์ 
			manageUpdateActive(conn, model, ResultType.BASIC);
			
			ItemManager manager = new ItemManager(conn, getUser(), loggerInititial());
			
			//3.update สภานะ
			if(model.getCriteria().getSelectedIds()!=null && !model.getCriteria().getSelectedIds().equals("")){
				try{
					manager.setActiveStatus(model.getCriteria().getSelectedIds(), model.getCriteria().getStatusForUpdate());
				} catch(Exception e) {
					manageException(conn, PF_CODE.getChangeFunction(), this, e, model);
				}
			}

			result = manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			List<ItemSearch> lstResult = manager.search(model.getCriteria());
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
	
	public String changeDelete() throws AuthorizationException {
		loggerInititial().info("changeDelete");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			manageDelete(conn, model, ResultType.BASIC);
			
			ItemManager manager = new ItemManager(conn, getUser(), loggerInititial());
			
			//3.update Deleted flag
			if(model.getCriteria().getSelectedIds()!=null && !model.getCriteria().getSelectedIds().equals("")){
				try{
					manager.delete(model.getCriteria().getSelectedIds());
				} catch(Exception e) {
					manageException(conn, PF_CODE.getDeleteFunction(), this, e, model);
				}
			}

			result = manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			List<ItemSearch> lstResult = manager.search(model.getCriteria());
			manageSearchResult(model, lstResult);

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
	protected Logger loggerInititial() {
		return LogUtil.PRODUCT;
	}
}
