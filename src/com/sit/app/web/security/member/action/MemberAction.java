package com.sit.app.web.security.member.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.security.authorization.domain.PFCode;
import com.sit.app.core.security.member.domain.Member;
import com.sit.app.core.security.member.domain.MemberModel;
import com.sit.app.core.security.member.domain.MemberSearch;
import com.sit.app.core.security.member.domain.MemberSearchCriteria;
import com.sit.app.core.security.member.service.MemberManager;
import com.sit.app.core.selectitem.service.SelectItemManager;
import com.sit.common.CommonAction;
import com.sit.common.CommonModel.PageType;
import com.sit.domain.GlobalVariable;
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

public class MemberAction extends CommonAction implements InterfaceAction, ModelDriven<MemberModel>{

	private static final long serialVersionUID = 1903244133364223129L;

	private MemberModel model = new MemberModel();
	
	public MemberAction() {
		try {
			getAuthorization(PFCode.MEMBER);
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}

	@Override
	public MemberModel getModel() {
		return model;
	}

	@Override
	public String init() throws AuthorizationException {
		loggerInititial().info("INIT");
		
		String result = null;

		CCTConnection conn = null;
		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// ตรวจสอบสิทธิ์การใช้งาน และกำหนดค่าเริ่มต้นให้กับหน้าค้นหาของระบบ
			result = manageInitial(conn, model, new MemberSearchCriteria(), PF_CODE.getSearchFunction(), PageType.SEARCH);

		} catch (Exception e) {
			manageException(conn, PF_CODE.getSearchFunction(), this, e, model);

		} finally {
			getComboForSearch(conn);
			CCTConnectionUtil.close(conn);
		}

		return result;
	}
	
	@Override
	public void getComboForSearch(CCTConnection conn) {
		SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
		
		try {
			model.setListOrganization(manager.searchOrganizationSelectItem());
			model.setListLockStatus(manager.searchLockStatusSelectItem());
			model.setListStatus(manager.searchStatusSelectItem());
		} catch (Exception e) {
			loggerInititial().error("", e);
		}
	}

	@Override
	public void getComboForAddEdit(CCTConnection conn) {
		SelectItemManager manager = new SelectItemManager(conn, getUser(), loggerInititial());
		
		try {
			model.setListOrganization(manager.searchOrganizationSelectItem());
			model.setListPrefix(manager.searchPrefixSelectItem());
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

			MemberManager manager = new MemberManager(conn, getUser(), loggerInititial());
			List<MemberSearch> lstResult = manager.search(model.getCriteria());

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

			//default checkbox value
			model.getMember().getMemberData().getActive().setCode("true");
			model.getMember().getMemberData().setLockStatus("true");
			
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

			MemberManager manager = new MemberManager(conn, getUser(), loggerInititial());

			//3.บันทึกเพิ่มข้อมูล
			manager.add(model.getMember());

			//4.เคลียร์ค่าหน้าเพิ่มทั้งหมด
			model.setMember(new Member());

			//default checkbox
			model.getMember().getMemberData().getActive().setCode("true");
			model.getMember().getMemberData().setLockStatus("true");

		} catch (Exception e) {
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getAddFunction(), this, e, model);

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
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getMember().getMemberData().getUserId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าเพิ่ม
			result = manageEdit(conn, model, ResultType.BASIC);

			MemberManager manager = new MemberManager(conn, getUser(), loggerInititial());
			model.getMember().getMemberData().setUserId(refId);
			
			//3.บันทึกเพิ่มข้อมูล
			manager.edit(model.getMember(), model.getResetPassword());

			//4.เคลียร์ค่าหน้าเพิ่มทั้งหมด
			model.setMember(new Member());

			manageSearchAjax(conn, model, model.getCriteria(), PF_CODE.getSearchFunction());

			result = ReturnType.SEARCH.getResult();

		} catch (Exception e) {

			getComboForAddEdit(conn);
			//5.จัดการ exception กรณีที่มี exception เกิดขึ้นในระบบ
			manageException(conn, PF_CODE.getEditFunction(), this, e, model);

			result = ReturnType.ADD_EDIT.getResult();

		} finally {
			//6.Close connection หลังเลิกใช้งาน
			getComboForSearch(conn);
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
			String refId = ReferrerUtil.convertReferrerToId(getUser().getUserName(), SessionUtil.getId(), model.getMember().getMemberData().getUserId());
			if (StringUtil.stringToNull(refId) == null) {
				throw new AuthorizationException();
			}
			
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ หน้าแก้ไข
			result = manageGotoEdit(conn, model);

			//3.ค้นหาข้อมูลผู้ใช้ ตาม id ที่เลือกมาจากหน้าจอ
			MemberManager manager = new MemberManager(conn, getUser(), loggerInititial());
			model.getMember().getMemberData().setUserId(refId);
			model.setMember(manager.searchById(model.getMember().getMemberData().getUserId()));
			model.setResetPassword(GlobalVariable.FLAG_INACTIVE);
			
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

	public String changeActive() throws AuthorizationException {
		loggerInititial().info("changeActive");
		
		String result = null;
		CCTConnection conn = null;
		
		try {
			//1.สร้าง connection โดยจะต้องระบุ lookup ที่ใช้ด้วย
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			//2.ตรวจสอบสิทธิ์ 
			manageUpdateActive(conn, model, ResultType.BASIC);
			
			MemberManager manager = new MemberManager(conn, getUser(), loggerInititial());
			
			//3.update สภานะ
			if(model.getCriteria().getSelectedIds()!=null && !model.getCriteria().getSelectedIds().equals("")){
				try{
					manager.setActiveStatus(model.getCriteria().getSelectedIds(), model.getCriteria().getStatusForUpdate());
				} catch(Exception e) {
					manageException(conn, PF_CODE.getChangeFunction(), this, e, model);
				}
			}

			result = manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			List<MemberSearch> lstResult = manager.search(model.getCriteria());
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
			
			MemberManager manager = new MemberManager(conn, getUser(), loggerInititial());
			
			//3.update Deleted flag
			if(model.getCriteria().getSelectedIds()!=null && !model.getCriteria().getSelectedIds().equals("")){
				try{
					manager.delete(model.getCriteria().getSelectedIds());
				} catch(Exception e) {
					manageException(conn, PF_CODE.getDeleteFunction(), this, e, model);
				}
			}

			result = manageSearchAjax(conn, model, model.getCriteria(), getPF_CODE().getSearchFunction());
			List<MemberSearch> lstResult = manager.search(model.getCriteria());
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
		return LogUtil.MEMBER;
	}

}
