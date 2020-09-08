package com.sit.app.core.security.member.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.security.member.domain.Member;
import com.sit.app.core.security.member.domain.MemberData;
import com.sit.app.core.security.member.domain.MemberSearch;
import com.sit.app.core.security.member.domain.MemberSearchCriteria;
import com.sit.app.core.security.membergroup.domain.Group;
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

public class MemberDAO extends AbstractDAO<MemberSearchCriteria, MemberSearch, Member, CommonUser>{

	public MemberDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, MemberSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("searchCount");
		
		long total = 0;
		int paramIndex = 0;
		
		Object[] params = new Object[8];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getUserCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getUsername(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getForename(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getSurname(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getOrganizationId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getPositionName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getLockStatus(), conn.getDbType(), ResultType.NULL);
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
	protected List<MemberSearch> search(CCTConnection conn, MemberSearchCriteria criteria, CommonUser user)
			throws Exception {
		log.info("search");
		
		List<MemberSearch> listResult = new ArrayList<MemberSearch>();
		
		int paramIndex = 0;
		
		Object[] params = new Object[11];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getUserCode(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getUsername(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getForename(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getSurname(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getOrganizationId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getPositionName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getLockStatus(), conn.getDbType(), ResultType.NULL);
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
				MemberSearch member = new MemberSearch();
				member.setId(StringUtil.nullToString(rst.getString("USER_ID")));
				member.setUserId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("USER_ID"))));
				member.setUserCode(StringUtil.nullToString(rst.getString("USER_CODE")));
				member.setUsername(StringUtil.nullToString(rst.getString("USERNAME")));
				member.setFullname(StringUtil.nullToString(rst.getString("fullname")));
				member.setOrganizationId(StringUtil.nullToString(rst.getString("organization_id")));
				member.setOrganizationName(StringUtil.nullToString(rst.getString("organization_name")));
				member.setPositionName(StringUtil.nullToString(rst.getString("POSITION_NAME")));
				member.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				member.setLockStatus(StringUtil.nullToString(rst.getString("LOCK_STATUS")));
				member.setStatus(StringUtil.nullToString(rst.getString("ACTIVE")));
				
				listResult.add(member);
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listResult;
	}

	@Override
	protected Member searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected MemberData searchByIds(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchById");
		
		MemberData result = null;

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(id);

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchById"
				, params);
		
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				result = new MemberData();
				result.setId(rst.getString("USER_ID"));
				result.setUserId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("USER_ID"))));
				result.setUserCode(StringUtil.nullToString(rst.getString("USER_CODE")));
				result.setPrefixId(StringUtil.nullToString(rst.getString("PREFIX_ID")));
				result.setPrefixName(StringUtil.nullToString(rst.getString("prefix_name")));
				result.setForename(StringUtil.nullToString(rst.getString("FORENAME")));
				result.setSurname(StringUtil.nullToString(rst.getString("SURNAME")));
				result.setOrganizationId(StringUtil.nullToString(rst.getString("ORGANIZATION_ID")));
				result.setOrganizationName(StringUtil.nullToString(rst.getString("organization_name")));
				result.setPositionName(StringUtil.nullToString(rst.getString("POSITION_NAME")));
				result.setEmail(StringUtil.nullToString(rst.getString("EMAIL")));
				result.setPhone(StringUtil.nullToString(rst.getString("CELL_PHONE")));
				result.setStartDate(StringUtil.nullToString(rst.getString("START_DATE")));
				result.setEndDate(StringUtil.nullToString(rst.getString("END_DATE")));
				result.setUsername(StringUtil.nullToString(rst.getString("USERNAME")));
				result.setPassword(StringUtil.nullToString(rst.getString("PASSWORD")));
				result.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")).equals(GlobalVariable.FLAG_ACTIVE) ? "true":"false");
				result.setLockStatus(StringUtil.nullToString(rst.getString("LOCK_STATUS")).equals("1") ? "true":"false");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return result;
	}

	/**
	 * หาค่า Min-Max OPERATOR_LEVEL 
	 */
	protected Operator searchLevel(CCTConnection conn, String type, String id, CommonUser user) throws Exception {
		log.info("searchLevel");
		
		Operator result = null;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		
		params[paramIndex++] = type;
		params[paramIndex++] = id;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLevel"
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
	 * แสดงรายการข้อมูลสิทธิ์โปรแกรมหรือรายงานอ้างอิง_SQL
	 * @param conn
	 * @param id
	 * @param type
	 * @param user
	 * @param operatorLevel
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchProgramByUserId(CCTConnection conn, String id, String type, CommonUser user, Operator operatorLevel) throws Exception {
		log.info("searchProgramByUserId");
		
		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		
		params[paramIndex++] = type;
		params[paramIndex++] = id;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchProgramByUserId"
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
	
	/**
	 * แสดงรายการข้อมูลกลุ่มผู้ใช้อ้างอิง_SQL
	 * @param conn
	 * @param id
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected List<Group> searchGroupByUserId(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchGroupByUserId");
		
		List<Group> listResult = new ArrayList<Group>();

		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(id);

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchGroupByUserId"
				, params);

		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				Group result = new Group();
				result.setId(rst.getString("GROUP_ID"));
				result.setGroupCode(StringUtil.nullToString(rst.getString("GROUP_CODE")));
				result.setGroupName(StringUtil.nullToString(rst.getString("GROUP_NAME")));
				result.getActive().setDesc(StringUtil.nullToString(rst.getString("ACTIVE").equals("Y")? "ACTIVE" : "INACTIVE"));
				result.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));
				
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
	protected int add(CCTConnection conn, Member obj, CommonUser user) throws Exception {
		return 0;
	}
	
	protected int add(CCTConnection conn, Long userId, MemberData obj, CommonUser user) throws Exception {
		log.info("add");
		
		int paramIndex = 0;
		Object[] params = new Object[16];
		params[paramIndex++] = userId;
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getUserCode(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getPrefixId());
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getSurname(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getForename(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getOrganizationId());
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getPositionName(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getEmail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getPhone(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getStartDate(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getEndDate(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getUsername(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = APPSUtil.passwordEncryptOneWay(obj.getPassword());
		params[paramIndex++] = Boolean.parseBoolean(obj.getLockStatus()) ? "1":"2";
		params[paramIndex++] = Boolean.parseBoolean(obj.getActive().getCode()) ? GlobalVariable.FLAG_ACTIVE : GlobalVariable.FLAG_INACTIVE;
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertMember"
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
	 * บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของผู้ใช้_SQL
	 * 
	 * @param conn
	 * @param userId
	 * @param operatorId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected void insertUserOperator(CCTConnection conn, long userId, String operatorId, CommonUser user) throws Exception {
		log.info("insertUserOperator");
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = userId;
		params[paramIndex++] = APPSUtil.convertLongValue(operatorId);
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertUserOperator"
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
	
	/**
	 * บันทึกเพิ่มรายการกลุ่มผู้ใช้ที่อ้างอิงกับผู้ใช้_SQL
	 * 
	 * @param conn
	 * @param userId
	 * @param obj
	 * @param user
	 * @return
	 * @throws Exception
	 */
	protected void insertUserGroup(CCTConnection conn, long userId, Group obj, CommonUser user) throws Exception {
		log.info("insertUserGroup");
		
		int paramIndex = 0;
		Object[] params = new Object[3];
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getId());
		params[paramIndex++] = userId;
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertUserGroup"
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
	protected int edit(CCTConnection conn, Member obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	protected int edit(CCTConnection conn, MemberData obj, String password, CommonUser user) throws Exception {
		log.info("edit");
		
		int paramIndex = 0;
		Object[] params = new Object[16];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getUserCode(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getPrefixId());
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getSurname(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getForename(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getOrganizationId());
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getPositionName(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getEmail(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getPhone(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getStartDate(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getEndDate(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getUsername(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = password;
		params[paramIndex++] = Boolean.parseBoolean(obj.getActive().getCode()) ? GlobalVariable.FLAG_ACTIVE:GlobalVariable.FLAG_INACTIVE;
		params[paramIndex++] = Boolean.parseBoolean(obj.getLockStatus()) ? "1":"2";
		params[paramIndex++] = APPSUtil.convertLongValue(user.getUserId());
		params[paramIndex] = obj.getUserId();

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "setMember", params);
		
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

	/**
	 * บันทึกลบรายการสิทธิ์โปรแกรมหรือรายงานของผู้ใช้_SQL
	 * 
	 * @param conn
	 * @param userId
	 * @param user
	 * @param locale
	 * @throws Exception
	 */
	protected void delOperator(CCTConnection conn, String userId, CommonUser user) throws Exception {
		log.info("delOperator");
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "deleteOperator", params);

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
	 * บันทึกลบรายการกลุ่มผู้ใช้อ้างอิงกับผู้ใช้_SQL
	 * 
	 * @param conn
	 * @param userId
	 * @param user
	 * @param locale
	 * @throws Exception
	 */
	protected void delGroup(CCTConnection conn, String userId, CommonUser user) throws Exception {
		log.info("delGroup");
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(userId);

		String sql = SQLUtil.getSQLString(conn.getSchemas(), getSqlPath().getClassName(), getSqlPath().getPath(), "deleteGroup", params);

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
				, "deleteMember"
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
	protected boolean checkDup(CCTConnection conn, Member obj, CommonUser user) throws Exception {
		log.info("checkDup");
		
		boolean isDuplicate = false;
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(obj.getMemberData().getUsername(), conn.getDbType(), ResultType.NULL);
		params[paramIndex] = APPSUtil.convertLongValue(obj.getMemberData().getUserId());

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
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
	 * Select User SEQ
	 * 
	 * @return
	 * @throws Exception
	 */
	protected long getUserSEQ(CCTConnection conn) throws Exception {
		
		long result = 0;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "getUserSEQ"
				);
		
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				result = rst.getLong("USER_ID_SEQ");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return result;
	}
	
}
