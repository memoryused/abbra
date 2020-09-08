package com.sit.app.core.dialog.master.vendor.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.dialog.master.vendor.domain.VendorDialog;
import com.sit.app.core.dialog.master.vendor.domain.VendorDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class VendorDialogDAO extends AbstractDAO<VendorDialogSearchCriteria, CommonDomain, VendorDialog, CommonUser> {

	public VendorDialogDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, VendorDialogSearchCriteria criteria, CommonUser user) throws Exception {
		long total = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendor().getVendorName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendor().getVendorCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendor().getActive().getCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getSelectedIds(), conn.getDbType(), ResultType.NULL);
		
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCount"
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
	protected List<CommonDomain> search(CCTConnection conn, VendorDialogSearchCriteria criteria, CommonUser user) throws Exception {
		List<CommonDomain> listCommon = new ArrayList<CommonDomain>();

		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendor().getVendorName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendor().getVendorCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendor().getActive().getCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getSelectedIds(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "search"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			int rowIndex = 1;
			while (rst.next()) {
				VendorDialog detail = new VendorDialog();
				detail.setRownum(String.valueOf(rowIndex++));
				detail.setIdPopup(rst.getString("vendor_id"));
				detail.setId(rst.getString("vendor_id"));
				detail.setVendorCode(StringUtil.nullToString(rst.getString("vendor_code")));
				detail.setVendorName(StringUtil.nullToString(rst.getString("vendor_name")));
				detail.getActive().setCode(StringUtil.nullToString(rst.getString("active")));
				detail.getActive().setDesc(StringUtil.nullToString(rst.getString("active")).equals("Y") ? "Active" : "Inactive");
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
				, "search"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			int rowIndex = 1;
			while (rst.next()) {
				VendorDialog detail = new VendorDialog();
				detail.setRownum(String.valueOf(rowIndex++));
				detail.setIdPopup(rst.getString("vendor_id"));
				detail.setId(rst.getString("vendor_id"));
				detail.setVendorCode(StringUtil.nullToString(rst.getString("vendor_code")));
				detail.setVendorName(StringUtil.nullToString(rst.getString("vendor_name")));
				detail.getActive().setCode(StringUtil.nullToString(rst.getString("active")));
				detail.getActive().setDesc(StringUtil.nullToString(rst.getString("active")).equals("Y") ? "ACTIVE" : "INACTIVE");
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
	protected VendorDialog searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int add(CCTConnection conn, VendorDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int edit(CCTConnection conn, VendorDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int delete(CCTConnection conn, String ids, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int updateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean checkDup(CCTConnection conn, VendorDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
