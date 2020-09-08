package com.sit.app.core.master.vendor.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.master.product.domain.Item;
import com.sit.app.core.master.vendor.domain.Vendor;
import com.sit.app.core.master.vendor.domain.VendorSearch;
import com.sit.app.core.master.vendor.domain.VendorSearchCriteria;
import com.sit.common.CommonUser;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;
import util.web.SessionUtil;

public class VendorDAO extends AbstractDAO<VendorSearchCriteria, VendorSearch, Vendor, CommonUser>{

	public VendorDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, VendorSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("searchCount");
		
		long total = 0;
		int paramIndex = 0;
		
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendorId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendorCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendorShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getStatus(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
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
			
			if(rst.next()) {
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
	protected List<VendorSearch> search(CCTConnection conn, VendorSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("search");
		
		List<VendorSearch> listResult = new ArrayList<VendorSearch>();
		
		int paramIndex = 0;
		
		Object[] params = new Object[7];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendorId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendorCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVendorShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = criteria.getOrderList();
		params[paramIndex++] = criteria.getStart();
		params[paramIndex] = criteria.getLinePerPage();
		
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

			while (rst.next()) {
				VendorSearch item = new VendorSearch();
				item.setId(StringUtil.nullToString(rst.getString("vendor_id")));
				item.setVendorId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("vendor_id"))));
				item.setVendorCode(StringUtil.nullToString(rst.getString("vendor_code")));
				item.setVendorName(StringUtil.nullToString(rst.getString("vendor_name")));
				item.setVendorShortName(StringUtil.nullToString(rst.getString("vendor_short_name")));
				item.setStatus(StringUtil.nullToString(rst.getString("active")));
				
				listResult.add(item);
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listResult;
	}

	@Override
	protected Vendor searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchById");
		
		Vendor vendor = null;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchById"
				, StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.EMPTY)
				);

		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				vendor = new Vendor();
				vendor.setVendorId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("vendor_id"))));
				vendor.setVendorCode(StringUtil.nullToString(rst.getString("vendor_code")));
				vendor.setVendorName(StringUtil.nullToString(rst.getString("vendor_name")));
				vendor.setVendorShortName(StringUtil.nullToString(rst.getString("vendor_short_name")));
				vendor.setStatus(StringUtil.nullToString(rst.getString("active")));
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return vendor;
	}
	
	protected List<Item> searchProductByVendorId(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchProductByVendorId");
		
		List<Item> listProduct = new ArrayList<Item>();
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProductByVendorId"
				, StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.EMPTY)
				);

		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				Item result = new Item();
				result.setId(rst.getString("Item_id"));
				result.setItemCode(StringUtil.nullToString(rst.getString("Item_code")));
				result.setItemShortName(StringUtil.nullToString(rst.getString("item_short_name")));
				result.getActive().setDesc(StringUtil.nullToString(rst.getString("Active").equals("Y")? "ACTIVE" : "INACTIVE"));
				result.getActive().setCode(StringUtil.nullToString(rst.getString("Active")));
				listProduct.add(result);
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listProduct;
	}

	@Override
	protected int add(CCTConnection conn, Vendor obj, CommonUser user) throws Exception {
		return 0;
	}
		
	protected int add(CCTConnection conn, long vendorId, Vendor obj, CommonUser user) throws Exception {
		log.info("add");
		
		int paramIndex = 0;

		Object[] params = new Object[6];
		params[paramIndex++] = vendorId;
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getUserId(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertVendor"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		return 0;
	}

	@Override
	protected int edit(CCTConnection conn, Vendor obj, CommonUser user) throws Exception {
		log.info("edit");
		
		int paramIndex = 0;

		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getUserId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorId(), conn.getDbType(), ResultType.EMPTY);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateVendor"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		return 0;
	}

	@Override
	protected int delete(CCTConnection conn, String ids, CommonUser user) throws Exception {
		log.info("delete");
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getUserId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(ids, conn.getDbType(), ResultType.EMPTY);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteVendor"
				, params
				);
		
		log.debug(sql);
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql.toString());
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		return 0;
	}

	protected int setActiveStatus(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		log.info("setActiveStatus");
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = activeFlag;
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getUserId(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(ids, conn.getDbType(), ResultType.EMPTY);

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "setActiveStatus"
				, params
				);
		
		log.debug(sql);
		
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeStatement(stmt);
		}
		return 0;
	}
	
	@Override
	protected int updateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean checkDup(CCTConnection conn, Vendor obj, CommonUser user) throws Exception {
		log.info("checkDup");
		
		boolean checkDup = false;
		
		int count = 0;
		int paramIndex = 0;
		Object[] params = new Object[4];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getVendorId(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "checkDup"
				, params
				);

		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				count = rst.getInt("cnt");
			}
			if(count != 0){
				checkDup = true;
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return checkDup;
	}
	
	/**
	 * Select Item SEQ
	 * 
	 * @return
	 * @throws Exception
	 */
	protected long getVendorSEQ(CCTConnection conn) throws Exception {
		
		long result = 0;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "getVendorSEQ"
				);
		
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				result = rst.getLong("VENDOR_ID_SEQ");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return result;
	}

}
