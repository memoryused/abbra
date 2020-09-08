package com.sit.app.core.dialog.security.operator.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.common.CommonUser;
import com.sit.domain.Operator;
import com.sit.domain.Tree;

import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.string.StringUtil;

public class OperatorDialogDAO extends AbstractDAO<Object, Object, Object, CommonUser> {

	public OperatorDialogDAO(Logger log) {
		super(log);
	}

	protected Tree searchLevelProgram(CCTConnection conn, CommonUser user) throws Exception{
		
		Tree tree = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLevelProgram"
				);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				tree = new Tree();
				tree.setMaxLevel(rst.getInt("MAX_OPERATOR_LEVEL"));
				tree.setMinLevel(rst.getInt("MIN_OPERATOR_LEVEL"));
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return tree;
	}

	protected Map<String, Tree> searchMenuProgramTree(CCTConnection conn, CommonUser user, Tree tree) throws Exception{
		Map<String, Tree> mapTree = new LinkedHashMap<String, Tree>();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchOperatorProgram"
				);
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			String level = "0";
			while (rst.next()) {
				level = StringUtil.nullToString(rst.getString("OPERATOR_LEVEL"));
				if (level.equals("0")) {
					continue;
				}

				Operator operator = new Operator();
				operator.setOperatorType(StringUtil.nullToString(rst.getString("OPERATOR_TYPE")));
				operator.setUrl(StringUtil.nullToString(rst.getString("URL")));
				
				operator.setCurrentId(StringUtil.nullToString(rst.getString("OPERATOR_ID")));
				operator.setParentId(StringUtil.nullToString(rst.getString("PARENT_ID")));
				
				operator.setLabel(StringUtil.nullToString(rst.getString("LABEL")));				
				operator.setCurrentLevel(Integer.valueOf(level));
				operator.setMinLevel(tree.getMinLevel());
				operator.setMaxLevel(tree.getMaxLevel());
				operator.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));

				mapTree.put(operator.getCurrentId(), operator);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return mapTree;
	}
	
	protected Tree searchLevelReport(CCTConnection conn, CommonUser user) throws Exception{
		Tree tree = null;
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchLevelReport"
				);
		
		log.debug(sql);
		
		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				tree = new Tree();
				tree.setMaxLevel(rst.getInt("MAX_OPERATOR_LEVEL"));
				tree.setMinLevel(rst.getInt("MIN_OPERATOR_LEVEL"));
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		
		return tree;
	}
	
	protected Map<String, Tree> searchMenuReportTree(CCTConnection conn, CommonUser user, Tree tree) throws Exception{
		
		Map<String, Tree> mapTree = new LinkedHashMap<String, Tree>();
		
		String sql = SQLUtil.getSQLString(conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchOperatorReport"
				);
		
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			String level = "0";
			while (rst.next()) {
				level = StringUtil.nullToString(rst.getString("OPERATOR_LEVEL"));
				if (level.equals("0")) {
					continue;
				}

				Operator operator = new Operator();
				operator.setOperatorType(StringUtil.nullToString(rst.getString("OPERATOR_TYPE")));
				operator.setUrl(StringUtil.nullToString(rst.getString("URL")));
				
				operator.setCurrentId(StringUtil.nullToString(rst.getString("OPERATOR_ID")));
				operator.setParentId(StringUtil.nullToString(rst.getString("PARENT_ID")));
				operator.setLabel(StringUtil.nullToString(rst.getString("LABEL")));
				operator.setCurrentLevel(Integer.valueOf(level));
				operator.setMinLevel(tree.getMinLevel());
				operator.setMaxLevel(tree.getMaxLevel());
				
				operator.getActive().setCode(StringUtil.nullToString(rst.getString("ACTIVE")));

				mapTree.put(operator.getCurrentId(), operator);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
				
		return mapTree;
	}

	@Override
	protected long countData(CCTConnection conn, Object criteria,
			CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<Object> search(CCTConnection conn, Object criteria,
			CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int add(CCTConnection conn, Object obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int edit(CCTConnection conn, Object obj, CommonUser user) throws Exception {
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
	protected boolean checkDup(CCTConnection conn, Object obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
