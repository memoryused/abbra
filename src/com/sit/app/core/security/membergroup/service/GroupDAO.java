package com.sit.app.core.security.membergroup.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.security.member.domain.MemberSearch;
import com.sit.app.core.security.membergroup.domain.Group;
import com.sit.app.core.security.membergroup.domain.GroupSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Operator;

import util.APPSUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;
import util.web.SessionUtil;

public class GroupDAO extends AbstractDAO<GroupSearchCriteria, Group, Group, CommonUser> {

	public GroupDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, GroupSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("countData");
		
		long total = 0;
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getActive().getCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupName(), conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCountGroup"
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
	protected List<Group> search(CCTConnection conn, GroupSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("search");
		
		List<Group> listResult = new ArrayList<>();
		
		int paramIndex = 0;
		Object[] params = new Object[6];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getActive().getCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getGroup().getGroupName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = criteria.getOrderList();
		params[paramIndex++] = criteria.getStart();
		params[paramIndex] = criteria.getLinePerPage();
		
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
			
			while (rst.next()) {
				Group result = new Group();
				result.setId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("GROUP_ID"))));
				result.setGroupCode(StringUtil.nullToString(rst.getString("GROUP_CODE")));
				result.setGroupName(StringUtil.nullToString(rst.getString("GROUP_NAME")));
				result.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
				result.getActive().setDesc(StringUtil.nullToString(rst.getString("ACTIVE").equals(GlobalVariable.FLAG_ACTIVE)? "ACTIVE":"INACTIVE"));
				listResult.add(result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return listResult;
	}

	@Override
	protected Group searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchById");
		
		Group result = null;
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex] = APPSUtil.convertLongValue(id);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchByIdGroup"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				result = new Group();
				result.setId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("GROUP_ID"))));
				result.setGroupCode(StringUtil.stringToNull(rst.getString("GROUP_CODE")));
				result.setGroupName(StringUtil.stringToNull(rst.getString("GROUP_NAME")));
				result.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")).equals(GlobalVariable.FLAG_ACTIVE)? "true":"false");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}

	/**
	 * ค้นหา level ของ Program
	 * @param conn
	 * @param id
	 * @param user 
	 * @return
	 * @throws Exception
	 */
	protected Operator searchProgramLevelByGroupId(CCTConnection conn, String id, CommonUser user) throws Exception{
		log.info("searchProgramLevelByGroupId");
		
		Operator result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[2];

		params[paramIndex++] = StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = null;
		
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProgramLevel"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				result = new Operator();
				result.setMaxLevel(rst.getInt("MAX_OPERATOR_LEVEL"));
				result.setMinLevel(rst.getInt("MIN_OPERATOR_LEVEL"));
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return result;
	}
	
	/**
	 * ค้นหาสิทธิ์การใช้งานของโปรแกรม
	 * @param conn
	 * @param id
	 * @param user
	 * @param locale
	 * @param operatorLevel 
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchProgramByGroupId(CCTConnection conn, String id, CommonUser user, Operator operatorLevel) throws Exception {
		log.info("searchProgramByGroupId");
		
		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.NULL);
		params[paramIndex] = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProgramByGroupId"
				, params);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				Operator operator = new Operator();
				operator.setMinLevel(operatorLevel.getMinLevel());
				operator.setMaxLevel(operatorLevel.getMaxLevel());
				
				operator.setOperatorId(StringUtil.nullToString(rst.getString("OPERATOR_ID")));
				//เงื่อนไขกรณีที่ มี operator เป็น 0 จะไดัไม่ error 
				if (operator.getOperatorId().equals("0")) {
					operator.setOperatorId(null);
					continue;
				}
				
				operator.setOperatorType(rst.getString("OPERATOR_TYPE"));
				operator.setVisible(GlobalVariable.FLAG_ACTIVE);
				
				operator.setParentId(StringUtil.nullToString(rst.getString("PARENT_ID")));
				//เงื่อนไขกรณีที่ มีParentId เป็น 0 จะไดัไม่ error 
				if (operator.getParentId().equals("0")) {
					operator.setParentId(null);
				}
				
				operator.setCurrentId(operator.getOperatorId());
				
				operator.setLabel(StringUtil.nullToString(rst.getString("LABEL")));
				
				//เงื่อนไขกรณีที่ มี CurrentLevel น้อยกว่า 0  จะไดัไม่ error 
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
	
	protected List<MemberSearch> searchUserByGroupId(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchUserByGroupId");
		
		List<MemberSearch> listResult = new ArrayList<MemberSearch>();

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(id);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "searchUserByGroupId", params);

		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				MemberSearch result = new MemberSearch();
				result.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				result.setLockStatus(StringUtil.nullToString(rst.getString("LOCK_STATUS")));
				result.setLockStatusName(StringUtil.nullToString(rst.getString("LOCK_STATUS").equals("1") ? "Ready":"Locked"));
				result.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
				result.getActive().setDesc(StringUtil.nullToString(rst.getString("ACTIVE").equals(GlobalVariable.FLAG_ACTIVE)? "ACTIVE":"INACTIVE"));
				result.setUserCode(StringUtil.nullToString(rst.getString("USER_CODE")));
				result.setUsername(StringUtil.nullToString(rst.getString("USERNAME")));
				result.setFullname(StringUtil.nullToString(rst.getString("fullname")));
				result.setOrganizationId(StringUtil.nullToString(rst.getString("ORGANIZATION_ID")));
				result.setOrganizationName(StringUtil.nullToString(rst.getString("ORGANIZATION_NAME")));
				result.setPositionName(StringUtil.nullToString(rst.getString("POSITION_NAME")));
				
				listResult.add(result);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return listResult;
	}
	
	@Override
	protected int add(CCTConnection conn, Group obj, CommonUser user) throws Exception {
		return 0;
	}
		
	protected int add(CCTConnection conn, long groupId, Group obj, CommonUser user) throws Exception {
		log.info("add");
		
		int paramIndex = 0;
		
		Object[] params = new Object[5];
		params[paramIndex++] = groupId;
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getGroupCode() , conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getGroupName() , conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(Boolean.parseBoolean(obj.getActive().getCode())? GlobalVariable.FLAG_ACTIVE:GlobalVariable.FLAG_INACTIVE, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertGroup"
				, params);
		
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
	
	/**
	 * บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของกลุ่มผู้ใช้_SQL
	 * @param conn
	 * @param groupId
	 * @param obj
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected void insertGroupOperator(CCTConnection conn, long groupId, String operatorId, CommonUser user) throws Exception {
		log.info("insertGroupOperator");
		
		int paramIndex = 0;
		
		Object[] params = new Object[3];
		params[paramIndex++] = APPSUtil.convertLongValue(operatorId);
		params[paramIndex++] = groupId;
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertGroupOperator"
				, params);
		
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
	}
	
	/**
	 * บันทึกเพิ่มรายการผู้ใช้ที่อ้างอิงกับกลุ่มผู้ใช้_SQL
	 * 
	 * @param conn
	 * @param groupId
	 * @param obj
	 * @param user
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	protected void insertGroupUser(CCTConnection conn, long groupId, MemberSearch obj, CommonUser user) throws Exception {
		log.info("insertGroupUser");
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = groupId;
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getId());
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertGroupUser"
				, params);
		
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
	}
	
	@Override
	protected int edit(CCTConnection conn, Group obj, CommonUser user) throws Exception {
		log.info("edit");
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getGroupCode() , conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getGroupName() , conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(Boolean.parseBoolean(obj.getActive().getCode())? GlobalVariable.FLAG_ACTIVE:GlobalVariable.FLAG_INACTIVE, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());
		params[paramIndex] = APPSUtil.convertLongValue(obj.getId());
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "updateGroupUser"
				, params);
		
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
	protected int delete(CCTConnection conn, String ids, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int updateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * บันทึกการเปลื่ยนสถานะ_SQL<br>
	 * activeFlag = Y (ใช้งาน)<br>
	 * activeFlag = N (ยกเลิก)<br>
	 */
	protected void setActiveStatus(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		log.info("setActiveStatus");
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(activeFlag, conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());
		params[paramIndex] = ids;

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "setActiveStatus"
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

	@Override
	protected boolean checkDup(CCTConnection conn, Group obj, CommonUser user) throws Exception {
		log.info("checkDup");
		
		boolean isDuplicate = false;
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getGroupCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getGroupName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getId(), conn.getDbType(), ResultType.NULL);

		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "checkDup"
				, params);
		
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				if (rst.getInt("TOT") > 0) {
					isDuplicate = true;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isDuplicate;
	}

	/**
	 * Select Group SEQ_SQL
	 * 
	 * @return
	 * @throws Exception
	 */
	protected long getGroupSEQ(CCTConnection conn) throws Exception {
		log.info("getGroupSEQ");
		
		long result = 0;

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "getUserSEQ");
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				result = rst.getLong("GROUP_ID_SEQ");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return result;
	}
	
	/**
	 * บันทึกลบรายการสิทธิ์โปรแกรมหรือรายงานของกลุ่มผู้ใช้_SQL
	 * @param conn
	 * @param groupId
	 * @param user
	 * @throws Exception
	 */
	protected void delOperator(CCTConnection conn, String groupId, CommonUser user) throws Exception {
		log.info("delOperator");
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(groupId);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteOperator"
				, params);
		
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
	}
	
	/**
	 * บันทึกลบรายการผู้ใช้อ้างอิงกับกลุ่มผู้ใช้_SQL
	 * @param conn
	 * @param groupId
	 * @param user
	 * @throws Exception
	 */
	protected void delUser(CCTConnection conn, String groupId, CommonUser user) throws Exception {
		log.info("delUser");
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(groupId);
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteUser"
				, params);
		
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
	}
}
