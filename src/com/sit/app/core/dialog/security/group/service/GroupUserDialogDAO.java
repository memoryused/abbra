package com.sit.app.core.dialog.security.group.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.dialog.security.group.domain.GroupUserDialog;
import com.sit.app.core.dialog.security.group.domain.GroupUserDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class GroupUserDialogDAO  extends AbstractDAO<GroupUserDialogSearchCriteria, CommonDomain, GroupUserDialog, CommonUser> {
	
	public GroupUserDialogDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, GroupUserDialogSearchCriteria criteria, CommonUser user ) throws Exception {
		long total = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getActive().getCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getSelectedIds(), conn.getDbType(), ResultType.NULL);
		
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "countGroup"
				, params);
		
		log.debug(sql);
		
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
	
	@Override
	protected List<CommonDomain> search(CCTConnection conn,GroupUserDialogSearchCriteria criteria,CommonUser user) throws Exception {
		List<CommonDomain> listCommon = new ArrayList<CommonDomain>();

		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getActive().getCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getSelectedIds(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchGroup"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			int rowIndex = 1;
			while (rst.next()) {
				GroupUserDialog detail = new GroupUserDialog();
				detail.setRownum(String.valueOf(rowIndex++));
				detail.setIdPopup(rst.getString("GROUP_ID"));
				detail.setId(rst.getString("GROUP_ID"));
				detail.setGroupCode(StringUtil.nullToString(rst.getString("GROUP_CODE")));
				detail.setGroupName(StringUtil.nullToString(rst.getString("GROUP_NAME")));
				detail.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
				detail.getActive().setDesc(StringUtil.nullToString(rst.getString("ACTIVE")).equals("Y") ? "Active" : "Inactive");
				listCommon.add(detail);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listCommon;
	}

	protected List<CommonDomain> searchListById(CCTConnection conn, String ids,CommonUser user) throws Exception {
		
		List<CommonDomain> listCommon = new ArrayList<CommonDomain>();
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString("", conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString("", conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString("", conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString("", conn.getDbType(), ResultType.NULL);
		params[paramIndex] = ids;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchGroup"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			int rowIndex = 1;
			while (rst.next()) {
				GroupUserDialog detail = new GroupUserDialog();
				detail.setRownum(String.valueOf(rowIndex++));
				detail.setIdPopup(rst.getString("GROUP_ID"));
				detail.setId(rst.getString("GROUP_ID"));
				detail.setGroupCode(StringUtil.nullToString(rst.getString("GROUP_CODE")));
				detail.setGroupName(StringUtil.nullToString(rst.getString("GROUP_NAME")));
				detail.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
				detail.getActive().setDesc(StringUtil.nullToString(rst.getString("ACTIVE")).equals("Y") ? "Active" : "Inactive");
				listCommon.add(detail);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listCommon;
	}

	@Override
	protected GroupUserDialog searchById(CCTConnection conn, String id,
			CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int add(CCTConnection conn, GroupUserDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int edit(CCTConnection conn, GroupUserDialog obj,
			CommonUser user) throws Exception {
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
	protected boolean checkDup(CCTConnection conn, GroupUserDialog obj,
			CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
