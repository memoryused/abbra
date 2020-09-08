package com.sit.app.core.dialog.master.product.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.dialog.master.product.domain.ProductDialog;
import com.sit.app.core.dialog.master.product.domain.ProductDialogSearchCriteria;
import com.sit.common.CommonDomain;
import com.sit.common.CommonUser;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class ProductDialogDAO extends AbstractDAO<ProductDialogSearchCriteria, CommonDomain, ProductDialog, CommonUser>{

	public ProductDialogDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, ProductDialogSearchCriteria criteria, CommonUser user) throws Exception {
		long total = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProduct().getItemName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProduct().getItemCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProduct().getActive().getCode(), conn.getDbType(), ResultType.NULL);
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
	protected List<CommonDomain> search(CCTConnection conn, ProductDialogSearchCriteria criteria, CommonUser user) throws Exception {
		List<CommonDomain> listCommon = new ArrayList<CommonDomain>();

		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProduct().getItemName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProduct().getItemCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProduct().getActive().getCode(), conn.getDbType(), ResultType.NULL);
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
				ProductDialog detail = new ProductDialog();
				detail.setRownum(String.valueOf(rowIndex++));
				detail.setIdPopup(rst.getString("item_id"));
				detail.setId(rst.getString("item_id"));
				detail.setItemCode(StringUtil.nullToString(rst.getString("item_code")));
				detail.setItemName(StringUtil.nullToString(rst.getString("item_short_name")));
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
				ProductDialog detail = new ProductDialog();
				detail.setRownum(String.valueOf(rowIndex++));
				detail.setIdPopup(rst.getString("item_id"));
				detail.setId(rst.getString("item_id"));
				detail.setItemCode(StringUtil.nullToString(rst.getString("item_code")));
				detail.setItemName(StringUtil.nullToString(rst.getString("item_short_name")));
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
	protected ProductDialog searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int add(CCTConnection conn, ProductDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int edit(CCTConnection conn, ProductDialog obj, CommonUser user) throws Exception {
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
	protected boolean checkDup(CCTConnection conn, ProductDialog obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
