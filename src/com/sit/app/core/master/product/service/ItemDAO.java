package com.sit.app.core.master.product.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.master.product.domain.Item;
import com.sit.app.core.master.product.domain.ItemSearch;
import com.sit.app.core.master.product.domain.ItemSearchCriteria;
import com.sit.app.core.master.vendor.domain.Vendor;
import com.sit.common.CommonUser;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;
import util.web.SessionUtil;

public class ItemDAO extends AbstractDAO<ItemSearchCriteria, ItemSearch, Item, CommonUser>{

	public ItemDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, ItemSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("searchCount");
		
		long total = 0;
		int paramIndex = 0;
		
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getItemId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getItemCode(), conn.getDbType(), ResultType.NULL);
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
	protected List<ItemSearch> search(CCTConnection conn, ItemSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("search");
		
		List<ItemSearch> listResult = new ArrayList<ItemSearch>();
		
		int paramIndex = 0;
		
		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getItemId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getItemCode(), conn.getDbType(), ResultType.NULL);
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
				ItemSearch item = new ItemSearch();
				item.setId(StringUtil.nullToString(rst.getString("item_id")));
				item.setItemId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("item_id"))));
				item.setItemCode(StringUtil.nullToString(rst.getString("item_code")));
				item.setItemShortName(StringUtil.nullToString(rst.getString("item_short_name")));
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
	protected Item searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchById");
		
		Item item = null;
		
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
				item = new Item();
				item.setItemId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("item_id"))));
				item.setItemCode(StringUtil.nullToString(rst.getString("item_code")));
				item.setItemShortName(StringUtil.nullToString(rst.getString("item_short_name")));
				item.setStatus(StringUtil.nullToString(rst.getString("active")));
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return item;
	}

	protected List<Vendor> searchVenderByItemId(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchVenderByItemId");
		
		List<Vendor> listVendor = new ArrayList<Vendor>();
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchVenderByItemId"
				, StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.EMPTY)
				);

		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				Vendor result = new Vendor();
				result.setId(rst.getString("vendor_id"));
				result.setVendorCode(StringUtil.nullToString(rst.getString("vendor_code")));
				result.setVendorName(StringUtil.nullToString(rst.getString("vendor_name")));
				result.getActive().setDesc(StringUtil.nullToString(rst.getString("Active").equals("Y")? "ACTIVE" : "INACTIVE"));
				result.getActive().setCode(StringUtil.nullToString(rst.getString("Active")));
				listVendor.add(result);
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listVendor;
	}
	
	@Override
	protected int add(CCTConnection conn, Item obj, CommonUser user) throws Exception {
		return 0;
	}
	
	protected int add(CCTConnection conn, Long itemId, Item obj, CommonUser user) throws Exception {
		log.info("add");
		
		int paramIndex = 0;

		Object[] params = new Object[5];
		params[paramIndex++] = itemId;
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getUserId(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertItem"
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
	protected int edit(CCTConnection conn, Item obj, CommonUser user) throws Exception {
		log.info("edit");
		
		int paramIndex = 0;

		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getStatus(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getUserId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemId(), conn.getDbType(), ResultType.EMPTY);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateItem"
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
				, "deleteItem"
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

	@Override
	protected int updateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected int setActiveStatus(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		log.info("setActiveStatus");
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = activeFlag;
		params[paramIndex++] = StringUtil.replaceSpecialString(user.getUserId(), conn.getDbType(), ResultType.NULL);
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
	protected boolean checkDup(CCTConnection conn, Item obj, CommonUser user) throws Exception {
		log.info("checkDup");
		
		boolean checkDup = false;
		
		int count = 0;
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemShortName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getItemId(), conn.getDbType(), ResultType.NULL);
		
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
	protected long getItemSEQ(CCTConnection conn) throws Exception {
		
		long result = 0;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "getItemSEQ"
				);
		
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				result = rst.getLong("Item_ID_SEQ");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return result;
	}
	
	public void insertVendorItemMap(CCTConnection conn, long vendorId, long itemId, CommonUser user) throws Exception {
		log.info("insertVendorItemMap");
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = vendorId;
		params[paramIndex++] = itemId;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertVendorItemMap"
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
	}
	
	public boolean checkDupVendorItemMap(CCTConnection conn, long venderId, long itemId) throws Exception {
		
		boolean checkDup = false;
		long result = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = venderId;
		params[paramIndex++] = itemId;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "checkDupVendorItemMap"
				, params
				);
		
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				result = rst.getLong("CNT");
			}

			if(result != 0){
				checkDup = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return checkDup;
	}
	
	public void deleteVendorItemMapById(CCTConnection conn, long venderId, long itemId, CommonUser user) throws Exception {
		log.info("deleteVendorItemMapById");
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = venderId;
		params[paramIndex++] = itemId;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteVendorItemMapById"
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
	}
}
