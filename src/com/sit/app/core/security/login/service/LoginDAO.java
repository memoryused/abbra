package com.sit.app.core.security.login.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.security.login.domain.User;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Operator;

import util.APPSUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;

public class LoginDAO extends AbstractDAO<Object, Object, Object, CommonUser> {

	public LoginDAO(Logger log) {
		super(log);
	}

	/**
	 * ตรวจสอบผู้ใช้งาน
	 *
	 * @param conn
	 * @param username
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected User searchUser(CCTConnection conn, String username) throws Exception {

		User result = null;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.replaceSpecialString(username, conn.getDbType(), ResultType.EMPTY);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "searchUserLogin", params);
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				 result = new User();
				 result.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				 result.setPrefixId(StringUtil.nullToString(rst.getString("PREFIX_ID")));
				 result.setSurname(StringUtil.nullToString(rst.getString("SURNAME")));
				 result.setForname(StringUtil.nullToString(rst.getString("FORENAME")));
				 result.setFullName(StringUtil.nullToString(rst.getString("FULLNAME")));
				 result.setOrganizationId(StringUtil.nullToString(rst.getString("ORGANIZATION_ID")));
				 result.setPositionName(StringUtil.nullToString(rst.getString("POSITION_NAME")));
				 result.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				 result.setStartDate(StringUtil.nullToString(rst.getString("START_DATE")));
				 result.setEndDate(StringUtil.nullToString(rst.getString("END_DATE")));
				 result.setUsername(StringUtil.nullToString(rst.getString("USERNAME")));
				 result.setPassword(StringUtil.nullToString(rst.getString("PASSWORD")));
				 result.setPasswordDate(StringUtil.nullToString(rst.getString("PASSWORD_DATE")));
				 result.setPasswordExp(StringUtil.nullToString(rst.getString("PASSWORD_EXP")));
				 result.setLockStatus(StringUtil.nullToString(rst.getString("LOCK_STATUS")));
				 result.setResetPasswordStatus(StringUtil.nullToString(rst.getString("RESET_PASSWORD_STATUS")));
				 result.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
			 }

		} catch (Exception e) {
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}
	
	/**
	 * บันทึกการ Login ผิด_SQL
	 *
	 * @param conn
	 * @param username
	 * @param password
	 * @param userId
	 * @throws Exception
	 */
	protected void addLoginWrong(CCTConnection conn, String username, String password, String createUserId) throws Exception {
		int paramIndex = 0;

		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(username, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(password, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = APPSUtil.convertLongValue(createUserId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "insertLoginWrong", params);
		log.debug("SQL: " + sql);

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
	
	/**
	 * หาจำนวนครั้งที่ Login ผิด_SQL
	 * @param conn
	 * @param userId
	 * @param loginWrongIn
	 * @param stationInfo
	 * @return
	 * @throws Exception
	 */
	protected int searchCountLoginWrong(CCTConnection conn, String userId, Integer loginWrongIn) throws Exception {

		int countLoginWrong = 0;

		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = APPSUtil.convertLongValue(userId);
		params[paramIndex++] = loginWrongIn;

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "searchCountLoginWrong", params);
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {
			  stmt = conn.createStatement();
			  rst = stmt.executeQuery(sql);

			  if (rst.next()) {
				  countLoginWrong = rst.getInt("WRONG_NUM");
			  }

		} catch (Exception e) {
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return countLoginWrong;
	}

	/**
	 * ระงับผู้ใช้งาน_SQL
	 * @param conn
	 * @param userId
	 * @throws Exception
	 */
	protected void updatelockUser(CCTConnection conn, String userId, String lockStatus) throws Exception {

		int paramIndex = 0;

		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(lockStatus, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = APPSUtil.convertLongValue(userId);
		params[paramIndex++] = APPSUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updatelockUser"
				, params);
		log.debug("SQL: " + sql);

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
	
	protected int calculateLockMinutes(CCTConnection conn, String userId) throws Exception {

		int lockedStatus = 0;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "calculateLockedMinutes", params);
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {
			  stmt = conn.createStatement();
			  rst = stmt.executeQuery(sql);

			  if (rst.next()) {
				  lockedStatus = rst.getInt("LOCKED_STATUS");
			  }

		} catch (Exception e) {
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return lockedStatus;
	}
	
	/**
	 * UPDATE สถานะใน SEC_LOGIN_WRONG_SQL
	 * @param conn
	 * @param userId
	 * @throws Exception
	 */
	protected void updateLoginWrong(CCTConnection conn, String userId) throws Exception {

		int paramIndex = 0;

		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateLoginWrong"
				, params);
		log.debug("SQL: " + sql);

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
	
	/**
	 * หาข้อมูลสิทธิ์_SQL
	 * @param conn
	 * @param userId
	 * @param conditionId
	 * @return
	 * @throws Exception
	 */
	protected String searchOperatorId(CCTConnection conn, String userId) throws Exception {

		String operatorIds = "";
		List<String> listOperatoreId = new ArrayList<String>();

		int paramIndex = 0;

		//menu condition = บุคคล และ กลุ่ม
		Object[] params = new Object[5];
		
		params[paramIndex++] = APPSUtil.convertLongValue(userId);
		params[paramIndex++] = APPSUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchOperatorId"
				, params);
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while(rst.next()) {
				listOperatoreId.add(StringUtil.nullToString(rst.getString("OPERATOR_ID")));
			}

			if (listOperatoreId.size() > 0) {
				operatorIds = String.join(",", listOperatoreId);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return operatorIds;
	}
	
	/**
	 * หาข้อมูลสิทธิ์_SQL (SEARCH MIN, MAX MENU LEVEL)
	 * 
	 * @param conn
	 * @param userId
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Operator searchOperatorLevel(CCTConnection conn, String operatorIds) throws Exception {

		Operator operatorLevel = null;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.replaceSpecialString(operatorIds, conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
			, getSqlPath().getClassName()
			, getSqlPath().getPath()
			, "searchOperatorLevel"
			, params);
		log.debug("SQL: " + sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				operatorLevel = new Operator();
				operatorLevel.setMinLevel(rst.getInt("MIN_LEVEL"));
				operatorLevel.setMaxLevel(rst.getInt("MAX_LEVEL"));
			}
		} catch (Exception e) {
			throw e;

		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return operatorLevel;
	}

	/**
	 * หาข้อมูลสิทธิ์_SQL (SEARCH MENU)
	 * 
	 * @param conn
	 * @param userId
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchOperator(CCTConnection conn, String operatorIds, Locale locale) throws Exception {

		Map<String, Operator> mapOperator = new LinkedHashMap<>();

		int paramIndex = 0;
		
		Object[] params = new Object[1];
		params[paramIndex++] = StringUtil.replaceSpecialString(operatorIds, conn.getDbType(), ResultType.NULL);

		 String sql = SQLUtil.getSQLString(conn.getSchemas()
			 , getSqlPath().getClassName()
			 , getSqlPath().getPath()
			 , "searchOperator"
			 , params);
		 log.debug("SQL: " + sql);

		Statement stmt = null;
		ResultSet rst = null;

		try {
			 stmt = conn.createStatement();
			 rst = stmt.executeQuery(sql);
			 
			 while (rst.next()) {
				 Operator operator = new Operator();
				 operator.setOperatorId(StringUtil.nullToString(rst.getString("OPERATOR_ID")));
				 if (operator.getOperatorId().equals("0")) {
					 operator.setOperatorId(null);
					 continue;
				 }

				 operator.setOperatorType(rst.getString("OPERATOR_TYPE"));
				 operator.setUrl(rst.getString("URL"));
				 operator.getActive().setCode(rst.getString("ACTIVE"));
				 operator.setVisible(GlobalVariable.FLAG_ACTIVE);
				 operator.setParentId(StringUtil.nullToString(rst.getString("PARENT_ID")));
				 
				 if (operator.getParentId().equals("0")) {
					 operator.setParentId(null);
				 }
				 operator.setCurrentId(operator.getOperatorId());
				 operator.setLabel(rst.getString("LABEL"));
				 operator.setCurrentLevel(rst.getInt("OPERATOR_LEVEL"));
				 
				 if (operator.getCurrentLevel() <= 0) {
					 continue;
				 }
				 
				 mapOperator.put(operator.getOperatorId(), operator);
			 }
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return mapOperator;
	}

	private Map<String, Operator> searchMenu(String operatorIds, Locale locale) throws Exception {
		Map<String, Operator> mapOperator = new LinkedHashMap<>();

		// ---------------------------------------------------------------------------------
		// MENU HOME
		Operator operator = new Operator();
		operator.setOperatorId("10100000");
		operator.setOperatorType("M");
		operator.setUrl("jsp/product/initProductHome.action");
		operator.setVisible("Y");
		operator.setParentId("");
		operator.setCurrentId("10100000");
		operator.setLabel("Home");
		operator.setSystemName("Home");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);

		operator = new Operator();
		operator.setOperatorId("10100101");
		operator.setOperatorType("F");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("10100000");
		operator.setCurrentId("10100101");
		operator.setLabel("Search");
		operator.setSystemName("Search");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		// ---------------------------------------------------------------------------------
		// MENU PRODUCT INFO
		operator = new Operator();
		operator.setOperatorId("10200000");
		operator.setOperatorType("M");
		operator.setUrl("jsp/product/initProductInfo.action");
		operator.setVisible("Y");
		operator.setParentId("");
		operator.setCurrentId("10200000");
		operator.setLabel("Product Information");
		operator.setSystemName("Product Information");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		operator = new Operator();
		operator.setOperatorId("10200101");
		operator.setOperatorType("F");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("10200000");
		operator.setCurrentId("10200101");
		operator.setLabel("Search");
		operator.setSystemName("Search");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		operator = new Operator();
		operator.setOperatorId("10200102");
		operator.setOperatorType("F");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("10200000");
		operator.setCurrentId("10200102");
		operator.setLabel("Add");
		operator.setSystemName("Add");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		operator = new Operator();
		operator.setOperatorId("10200103");
		operator.setOperatorType("F");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("10200000");
		operator.setCurrentId("10200103");
		operator.setLabel("Edit");
		operator.setSystemName("Edit");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		operator = new Operator();
		operator.setOperatorId("10200104");
		operator.setOperatorType("F");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("10200000");
		operator.setCurrentId("10200104");
		operator.setLabel("View");
		operator.setSystemName("View");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		operator = new Operator();
		operator.setOperatorId("10200105");
		operator.setOperatorType("F");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("10200000");
		operator.setCurrentId("10200105");
		operator.setLabel("Delete");
		operator.setSystemName("Delete");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		operator = new Operator();
		operator.setOperatorId("10200106");
		operator.setOperatorType("F");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("10200000");
		operator.setCurrentId("10200106");
		operator.setLabel("Change");
		operator.setSystemName("Change");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);
		
		// ---------------------------------------------------------------------------------
		// MENU MEMBER
		operator = new Operator();
		operator.setOperatorId("10300000");
		operator.setOperatorType("M");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("");
		operator.setCurrentId("10300000");
		operator.setLabel("Member");
		operator.setSystemName("Member");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);

		// MENU : LV_2
		operator = new Operator();
		operator.setOperatorId("10300100");
		operator.setOperatorType("M");
		operator.setUrl("jsp/security/initMember.action");
		operator.setVisible("Y");
		operator.setParentId("10300000");
		operator.setCurrentId("10300100");
		operator.setLabel("Member");
		operator.setSystemName("Member");
		operator.setCurrentLevel(2);
		mapOperator.put(operator.getOperatorId(), operator);

		// MENU : LV_2
		operator = new Operator();
		operator.setOperatorId("10300200");
		operator.setOperatorType("M");
		operator.setUrl("jsp/security/initMemberGroup.action");
		operator.setVisible("Y");
		operator.setParentId("10300000");
		operator.setCurrentId("10300200");
		operator.setLabel("Member Group");
		operator.setSystemName("Member Group");
		operator.setCurrentLevel(2);
		mapOperator.put(operator.getOperatorId(), operator);

		// ---------------------------------------------------------------------------------
		// MENU MASTER DATA
		operator = new Operator();
		operator.setOperatorId("10400000");
		operator.setOperatorType("M");
		operator.setUrl("");
		operator.setVisible("Y");
		operator.setParentId("");
		operator.setCurrentId("10400000");
		operator.setLabel("Master Data");
		operator.setSystemName("Master Data");
		operator.setCurrentLevel(1);
		mapOperator.put(operator.getOperatorId(), operator);

		// MENU : LV_2
		operator = new Operator();
		operator.setOperatorId("10400100");
		operator.setOperatorType("M");
		operator.setUrl("jsp/master/initProduct.action");
		operator.setVisible("Y");
		operator.setParentId("10400000");
		operator.setCurrentId("10400100");
		operator.setLabel("Product");
		operator.setSystemName("Product");
		operator.setCurrentLevel(2);
		mapOperator.put(operator.getOperatorId(), operator);

		// MENU : LV_2
		operator = new Operator();
		operator.setOperatorId("10400200");
		operator.setOperatorType("M");
		operator.setUrl("jsp/master/initVender.action");
		operator.setVisible("Y");
		operator.setParentId("10400000");
		operator.setCurrentId("10400200");
		operator.setLabel("Vender");
		operator.setSystemName("Vender");
		operator.setCurrentLevel(2);
		mapOperator.put(operator.getOperatorId(), operator);

		// MENU : LV_2
		operator = new Operator();
		operator.setOperatorId("10400300");
		operator.setOperatorType("M");
		operator.setUrl("jsp/master/initProductMapping.action");
		operator.setVisible("Y");
		operator.setParentId("10400000");
		operator.setCurrentId("10400300");
		operator.setLabel("Product Mapping");
		operator.setSystemName("Product Mapping");
		operator.setCurrentLevel(2);
		mapOperator.put(operator.getOperatorId(), operator);
				
		return mapOperator;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected long countData(CCTConnection conn, Object criteria, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected List<Object> search(CCTConnection conn, Object criteria, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected Object searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected int add(CCTConnection conn, Object obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected int edit(CCTConnection conn, Object obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected int delete(CCTConnection conn, String ids, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected int updateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้งาน
	 */
	@Override
	protected boolean checkDup(CCTConnection conn, Object obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
