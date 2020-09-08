package com.sit.app.core.dialog.security.user.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.dialog.security.user.domain.UserDialog;
import com.sit.app.core.dialog.security.user.domain.UserDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.log.LogUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class UserDialogDAO  extends AbstractDAO<UserDialogSearchCriteria, CommonDomain, UserDialog, CommonUser> {

	public UserDialogDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn,UserDialogSearchCriteria criteriaPopup, CommonUser user) throws Exception {
		long total = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getOfficeCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getUserName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getForeName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getSurName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getOrgId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getLockStatusId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getSelectedIds(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "countUser"
				, params);
		
		LogUtil.DIALOG.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				total = rst.getLong("TOT");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return total;
	}

	protected List<CommonDomain> search(CCTConnection conn,UserDialogSearchCriteria criteriaPopup, CommonUser user) throws Exception {
		List<CommonDomain> listCommon = new ArrayList<CommonDomain>();

		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getOfficeCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getUserName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getForeName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getSurName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getOrgId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getUser().getLockStatusId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteriaPopup.getSelectedIds(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUser"
				, params);
		
		LogUtil.DIALOG.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			int row = 1;
			while (rst.next()) {
				UserDialog detail = new UserDialog();
				detail.setRownum(String.valueOf(row));
				detail.setIdPopup(rst.getString("USER_ID"));
				detail.setId(rst.getString("USER_ID"));
				detail.setLockStatusId(StringUtil.nullToString(rst.getString("LOCK_STATUS")));
				detail.setLockStatusName(StringUtil.nullToString(rst.getString("LOCK_STATUS")).equals("1") ? "Ready" : "Locked");
				detail.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
				detail.getActive().setDesc(StringUtil.nullToString(rst.getString("ACTIVE")).equals(GlobalVariable.FLAG_ACTIVE) ? "Active" : "Inactive");
				detail.setOfficeCode(StringUtil.nullToString(rst.getString("USER_CODE")));
				detail.setUserName(StringUtil.nullToString(rst.getString("USERNAME")));
				detail.setFullName(StringUtil.nullToString(rst.getString("fullname")));
				detail.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				detail.setPosition(StringUtil.nullToString(rst.getString("POSITION_NAME")));
				detail.setOrgName(StringUtil.nullToString(rst.getString("ORGANIZATION_NAME")));
				
				listCommon.add(detail);
				row++;
			}
			
			
		} catch (Exception e) {
			throw e;
		}finally{
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listCommon;
	}

	protected List<CommonDomain> searchListById(CCTConnection conn, String ids,CommonUser user) throws Exception {
		List<CommonDomain> listCommon = new ArrayList<CommonDomain>();
		
		int paramIndex = 0;
		Object[] params = new Object[8];
		params[paramIndex++] = null;
		params[paramIndex++] = null;
		params[paramIndex++] = null;
		params[paramIndex++] = null;
		params[paramIndex++] = null;
		params[paramIndex++] = null;
		params[paramIndex++] = null;
		params[paramIndex++] = ids;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchUser"
				, params);
		
		LogUtil.DIALOG.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			int row = 1;
			while (rst.next()) {
				UserDialog detail = new UserDialog();
				detail.setRownum(String.valueOf(row));
				detail.setIdPopup(rst.getString("USER_ID"));
				detail.setId(rst.getString("USER_ID"));
				detail.setLockStatusId(StringUtil.nullToString(rst.getString("LOCK_STATUS")));
				detail.setLockStatusName(StringUtil.nullToString(rst.getString("LOCK_STATUS")).equals("1") ? "Ready" : "Locked");
				detail.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
				detail.getActive().setDesc(StringUtil.nullToString(rst.getString("ACTIVE")).equals(GlobalVariable.FLAG_ACTIVE) ? "Active" : "Inactive");
				detail.setOfficeCode(StringUtil.nullToString(rst.getString("USER_CODE")));
				detail.setUserName(StringUtil.nullToString(rst.getString("USERNAME")));
				detail.setFullName(StringUtil.nullToString(rst.getString("fullname")));
				detail.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				detail.setPosition(StringUtil.nullToString(rst.getString("POSITION_NAME")));
				detail.setOrgName(StringUtil.nullToString(rst.getString("ORGANIZATION_NAME")));
				
				listCommon.add(detail);
				row++;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listCommon;
	}

	@Override
	protected UserDialog searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int add(CCTConnection conn, UserDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int edit(CCTConnection conn, UserDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int delete(CCTConnection conn, String ids, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int updateActive(CCTConnection conn, String ids,
			String activeFlag, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean checkDup(CCTConnection conn, UserDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


}
