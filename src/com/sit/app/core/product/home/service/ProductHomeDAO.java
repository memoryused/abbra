package com.sit.app.core.product.home.service;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.product.home.domain.ProductHomeSearch;
import com.sit.app.core.product.home.domain.ProductHomeSearchCriteria;
import com.sit.common.CommonUser;

import util.APPSUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;
import util.web.SessionUtil;

public class ProductHomeDAO extends AbstractDAO<ProductHomeSearchCriteria, ProductHomeSearch, Object, CommonUser>{

	public ProductHomeDAO(Logger log) {
		super(log);
	}

	public Map<String, Object> getFile(CCTConnection conn, String refId) throws Exception {
		Map<String, Object> map = null;
		
		int paramIndex = 0;
		Object[] params = new Object[1];
		params[paramIndex++] = APPSUtil.convertLongValue(refId);

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchFile"
				, params);
		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		
		File file = null;
		FileInputStream in = null;
		
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			if (rst.next()) {
				map = new LinkedHashMap<>();
				String filePath = StringUtil.stringToNull(rst.getString("FILE_PATH"));
				
				if (filePath != null) {
					String[] filenameArr = filePath.split("/");
					map.put("FILENAME", filenameArr[filenameArr.length-1]);
					log.debug("Found !!!");
					file = new File(filePath);
				} else {
					log.debug("Not found file on server");
					filePath = ParameterConfig.getParameter().getAttachFile().getImageDefault() + "no_image_resize.jpg";
					map.put("FILENAME", "no_image_resize.jpg");
					file = new File(filePath);
				}
				
				try {
					in = new FileInputStream(file);
					byte[] data = new byte[(int) file.length()];
					in.read(data);
					map.put("FILE", data);
				} finally {
					if (in != null)
						in.close();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}

		return map;
	}
	
	@Override
	protected long countData(CCTConnection conn, ProductHomeSearchCriteria criteria, CommonUser user) throws Exception {
		long total = 0;
		int paramIndex = 0;
		
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProductId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVenderId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDocumentTypeId(), conn.getDbType(), ResultType.NULL);
		
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
	protected List<ProductHomeSearch> search(CCTConnection conn, ProductHomeSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("search");
		
		List<ProductHomeSearch> listResult = new ArrayList<ProductHomeSearch>();
		
		int paramIndex = 0;
		
		Object[] params = new Object[6];
		
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProductId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVenderId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getDocumentTypeId(), conn.getDbType(), ResultType.NULL);
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
				ProductHomeSearch obj = new ProductHomeSearch();
				
				obj.setDocTranId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("Doc_TranD_ID"))));
				obj.setProductName(StringUtil.nullToString(rst.getString("item_short_name")));
				obj.setVenderName(StringUtil.nullToString(rst.getString("vendor_name")));
				obj.setDocumentType(StringUtil.nullToString(rst.getString("DOCUMENT_NAME")));
				obj.setStandard(StringUtil.nullToString(rst.getString("CERTIFICATE_NAME")));
				obj.setExpireDate(StringUtil.nullToString(rst.getString("Doc_expire_date")));
				obj.setPdfPath(StringUtil.nullToString(rst.getString("PDF_Path")));
				
				listResult.add(obj);
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listResult;
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
	protected int updateActive(CCTConnection conn, String ids, String activeFlag, CommonUser user)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean checkDup(CCTConnection conn, Object obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
