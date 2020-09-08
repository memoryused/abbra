package com.sit.app.core.selectitem.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.product.domain.MailControl;
import com.sit.common.CommonSelectItem;
import com.sit.domain.GlobalVariable;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class SelectItemDAO extends AbstractDAO<Object, Object, Object, Object> {

	public SelectItemDAO(Logger log) {
		super(log);
	}

	protected Map<String, List<CommonSelectItem>> searchGlobalDataSelectItem(CCTConnection conn, Locale locale) throws Exception {
		
		Map<String, List<CommonSelectItem>> mapGlobalData = new HashMap<String, List<CommonSelectItem>>();
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = locale.getLanguage();

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchGlobalDataSelectItem"
				, params);
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				String globalType = StringUtil.nullToString(rst.getString("GLOBAL_TYPE_CODE"));
				if (mapGlobalData.get(globalType) == null) {
					mapGlobalData.put(globalType, new ArrayList<CommonSelectItem>());
				}

				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("GLOBAL_DATA_CODE")));
				selectItem.setValue(StringUtil.nullToString(rst.getString(SQLUtil.getColumnByLocale("GLOBAL_DATA_VALUE", locale))));
				mapGlobalData.get(globalType).add(selectItem);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return mapGlobalData;
	}
	
	/**
	 * Product [COMBOBOX]
	 * @param conn
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchProductSelectItem(CCTConnection conn) throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		String sql = SQLUtil.getSQLString(conn.getSchemas()
											, getSqlPath().getClassName()
											, getSqlPath().getPath()
											, "searchProductSelectItem"
											);
		//log.debug("[ SQL PRODUCT SELECT_ITEM ] : \n\n"+sql+"\n\n");

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("Item_id")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("item_short_name")));
				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * Vender [COMBOBOX]
	 * @param conn
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchVenderSelectItem(CCTConnection conn) throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		String sql = SQLUtil.getSQLString(conn.getSchemas()
											, getSqlPath().getClassName()
											, getSqlPath().getPath()
											, "searchVenderSelectItem"
											);
		//log.debug("[ SQL VENDER SELECT_ITEM ] : \n\n"+sql+"\n\n");

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("vendor_id")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("vendor_name")));
				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * Document Type [COMBOBOX]
	 * @param conn
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchDocumentTypeSelectItem(CCTConnection conn) throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		String sql = SQLUtil.getSQLString(conn.getSchemas()
											, getSqlPath().getClassName()
											, getSqlPath().getPath()
											, "searchDocumentTypeSelectItem"
											);
		//log.debug("[ SQL DOCUMENT TYPE SELECT_ITEM ] : \n\n"+sql+"\n\n");

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("DOCUMENT_ID")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("DOCUMENT_NAME")));
				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * Status [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchStatusSelectItem() throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();
		
		try {
			CommonSelectItem selectItem = new CommonSelectItem();
			selectItem.setKey(GlobalVariable.FLAG_ACTIVE);
			selectItem.setValue("Active");
			listSelectItem.add(selectItem);
			
			selectItem = new CommonSelectItem();
			selectItem.setKey(GlobalVariable.FLAG_INACTIVE);
			selectItem.setValue("Inactive");
			listSelectItem.add(selectItem);
			
		} catch (Exception e) {
			throw e;
		} finally {
		}
		return listSelectItem;
	}
	
	/**
	 * Standerd [COMBOBOX]
	 * @param conn
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchStandardSelectItem(CCTConnection conn, String documentId) throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		int paramIndex = 0;
		
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.replaceSpecialString(documentId, conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
											, getSqlPath().getClassName()
											, getSqlPath().getPath()
											, "searchStandardSelectItem"
											, params
											);
		//log.debug("[ SQL STANDARD SELECT_ITEM ] : \n\n"+sql+"\n\n");

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("CERTIFICATE_ID")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("CERTIFICATE_NAME")));
				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	protected MailControl searchMailControl(CCTConnection conn) throws Exception {
		MailControl mail = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
											, getSqlPath().getClassName()
											, getSqlPath().getPath()
											, "searchMailControl"
											);
		//log.debug("[ SQL MAIL CONTROL ] : \n\n"+sql+"\n\n");

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				mail = new MailControl();
				mail.setEmailId(StringUtil.nullToString(rst.getString("Email_id")));
				mail.setSender(StringUtil.nullToString(rst.getString("Sender_1")));
				mail.setCc1(StringUtil.nullToString(rst.getString("CC_1")));
				mail.setCc2(StringUtil.nullToString(rst.getString("CC_2")));
				mail.setSendNotify(StringUtil.nullToString(rst.getString("Send_notif")));
				mail.setmEmailControlCol(StringUtil.nullToString(rst.getString("M_Email_Controlcol")));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return mail;
	}
	
	/**
	 * Lock Status [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchLockStatusSelectItem() throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();
		
		try {
			CommonSelectItem selectItem = new CommonSelectItem();
			selectItem.setKey("1");
			selectItem.setValue("Ready");
			listSelectItem.add(selectItem);
			
			selectItem = new CommonSelectItem();
			selectItem.setKey("2");
			selectItem.setValue("Locked");
			listSelectItem.add(selectItem);
			
		} catch (Exception e) {
			throw e;
		} finally {
		}
		return listSelectItem;
	}
	
	/**
	 * Organization [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchOrganizationSelectItem(CCTConnection conn) throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		String sql = SQLUtil.getSQLString(conn.getSchemas()
											, getSqlPath().getClassName()
											, getSqlPath().getPath()
											, "searchOrganizationSelectItem"
											);
		//log.debug("[ SQL STANDARD SELECT_ITEM ] : \n\n"+sql+"\n\n");

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("organization_id")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("organization_name")));
				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * Prefix [COMBOBOX]
	 * @return
	 * @throws Exception
	 */
	protected List<CommonSelectItem> searchPrefixSelectItem(CCTConnection conn) throws Exception {
		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		String sql = SQLUtil.getSQLString(conn.getSchemas()
											, getSqlPath().getClassName()
											, getSqlPath().getPath()
											, "searchPrefixSelectItem"
											);
		//log.debug("[ SQL STANDARD SELECT_ITEM ] : \n\n"+sql+"\n\n");

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				CommonSelectItem selectItem = new CommonSelectItem();
				selectItem.setKey(StringUtil.nullToString(rst.getString("prefix_id")));
				selectItem.setValue(StringUtil.nullToString(rst.getString("prefix_name")));
				listSelectItem.add(selectItem);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listSelectItem;
	}
	
	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected long countData(CCTConnection conn, Object criteria, Object user) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected List<Object> search(CCTConnection conn, Object criteria, Object user) throws Exception {
		return null;
	}

	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected Object searchById(CCTConnection conn, String id, Object user) throws Exception {
		return null;
	}

	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected int add(CCTConnection conn, Object obj, Object user) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected int edit(CCTConnection conn, Object obj, Object user) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected int delete(CCTConnection conn, String ids, Object user) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected int updateActive(CCTConnection conn, String ids, String activeFlag, Object user) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ใช้งาน
	 * */
	@Override
	protected boolean checkDup(CCTConnection conn, Object obj, Object user) throws Exception {
		return false;
	}

}