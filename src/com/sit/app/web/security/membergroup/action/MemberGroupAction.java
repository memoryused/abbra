package com.sit.app.web.security.membergroup.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.security.membergroup.domain.Group;
import com.sit.app.core.security.membergroup.domain.GroupData;
import com.sit.app.core.security.membergroup.domain.GroupModel;
import com.sit.app.core.security.membergroup.domain.GroupSearchCriteria;
import com.sit.app.core.security.membergroup.service.GroupManager;
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
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.web.SessionUtil;

public class MemberGroupAction extends CommonAction implements InterfaceAction, ModelDriven<GroupModel>{

	private static final long serialVersionUID = 1064176716674094033L;

	private GroupModel model = new GroupModel();
	
	public MemberGroupAction() {
		try {
			getAuthorization(PFCode.MEMBER_GROUP);
		} catch (Exception e) {
			loggerInititial().error("",e);
		}
	}
	
	@Override
	public String init() throws AuthorizationException{
		String result = null;

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// ตรวจสอบสิทธิ์การใช้งาน และกำหนดค่าเริ่มต้นให้กับหน้าค้นหาของระบบ
			result = manageInitial(conn, model, new GroupSearchCriteria(), getPF_CODE().getSearchFunction(), PageType.SEARCH);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, model);
		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public GroupModel getModel() {
		return model;
	}

	@Override
	public void getComboForSearch(CCTConnection conn) {
		try {
			//โหลด combo active
			SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
			model.setListActiveStatus(manager.searchStatusSelectItem());
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
		
	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String search() throws AuthorizationException {
		loggerInititial().info("search");
		
		String result= null;
		CCTConnection conn =null;

		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			result = manageSearchAjax(conn, model, model.getCriteria(), PF_CODE.getSearchFunction());

			GroupManager manager = new GroupManager(conn, getUser(), loggerInititial());
			List<Group> lstResult = manager.search(model.getCriteria());

			manageSearchResult(model, lstResult);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, model);
		}finally{
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
			//เปิด connection
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//เช็คสิทธิ์เพิ่ม
			result = manageGotoAdd(conn, model);

			model.setData(new GroupData());

		} catch (Exception e) {
			manageException(conn, PF_CODE.getAddFunction(), this, e, model);
		}finally{
			getComboForAddEdit(conn);
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

			GroupManager manager = new GroupManager(conn, getUser(), loggerInititial());

			//3.บันทึกเพิ่มข้อมูล
			manager.add(model.getData());

			//4.เคลียร์ค่าหน้าเพิ่มทั้งหมด
			model.setData(new GroupData());

		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, getPF_CODE().getAddFunction(), this, e, model);
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
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getData().getGroup().getId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์แก้ไข
			result = manageEdit(conn, model,ResultType.BASIC);

			GroupManager manager = new GroupManager(conn, getUser(), loggerInititial());
			model.getData().getGroup().setId(refId);
			manager.edit(model.getData());

			//เคลียร์หน้า
			model.setData(new GroupData());
			manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			result = ReturnType.SEARCH.getResult();

		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getEditFunction(), this, e, model);
			result = ReturnType.ADD_EDIT.getResult();
		}finally{
			//6.Load combo ทั้งหมด
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
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getData().getGroup().getId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าแก้ไข
			result = manageGotoEdit(conn, model);

			//3.ค้นหาข้อมูลผู้ใช้ ตาม id ที่เลือกมาจากหน้าจอ
			GroupManager manager = new GroupManager(conn, getUser(), loggerInititial());
			model.getData().getGroup().setId(refId);
			model.setData(manager.searchById(model.getData().getGroup().getId()));

			//4.กำหนดให้แสดง user transaction
			showTransaction(model.getData().getGroup().getTransaction());

		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, getPF_CODE().getEditFunction(), this, e, model);
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

	/**
	 * เปลี่ยนสถานะ Active & Inactive
	 * @return
	 * @throws AuthorizationException
	 */
	public String changeActive() throws AuthorizationException {
		loggerInititial().info("changeActive");
		
		String result = null;
		CCTConnection conn = null;
		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			manageUpdateActive(conn, model, ResultType.BASIC);

			GroupManager manager = new GroupManager(conn, getUser(), loggerInititial());

			//3.update สภานะ
			if(model.getCriteria().getSelectedIds()!=null && !model.getCriteria().getSelectedIds().equals("")){
				try{
					manager.setActiveStatus(model.getCriteria().getSelectedIds(), model.getCriteria().getStatusForUpdate());
				} catch(Exception e) {
					manageException(conn, PF_CODE.getChangeFunction(), this, e, model);
				}
			}

			result = manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			List<Group> lstResult = manager.search(model.getCriteria());
			manageSearchResult(model, lstResult);
		} catch (Exception e) {
			//4.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getChangeFunction(), this, e, model);
		} finally {
			getComboForSearch(conn);
			//5.Close connection หลังเลิกใช้งาน
			CCTConnectionUtil.close(conn);
		}
		return result;
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
		model.setTransaction(transaction);
	}

	/**
	 * สำหรับยกเลิก
	 * @return
	 * @throws AuthorizationException
	 */
	public String cancel() throws AuthorizationException {
		CCTConnection conn = null;
		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
			// ตรวจสอบสิทธิ์การใช้งาน และจัดการเงือนไขการค้นหา
		    manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());

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
		return LogUtil.MEMBER_GROUP;
	}
	
	
}
