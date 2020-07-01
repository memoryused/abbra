package com.sit.app.core.product.productinfo.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractDAO;
import com.sit.app.core.product.productinfo.domain.ProductInfo;
import com.sit.app.core.product.productinfo.domain.ProductInfoDialog;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearch;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearchCriteria;
import com.sit.app.core.product.productinfo.domain.StandardInfo;
import com.sit.common.CommonUser;

import util.APPSUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionUtil;
import util.database.SQLUtil;
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.type.StringType.ResultType;
import util.web.SessionUtil;

public class ProductInfoDAO extends AbstractDAO<ProductInfoSearchCriteria, ProductInfoSearch, ProductInfo, CommonUser>{

	public ProductInfoDAO(Logger log) {
		super(log);
	}

	@Override
	protected long countData(CCTConnection conn, ProductInfoSearchCriteria criteria, CommonUser user) throws Exception {
		long total = 0;
		int paramIndex = 0;
		
		Object[] params = new Object[3];
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProductId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVenderId(), conn.getDbType(), ResultType.NULL);
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
	protected List<ProductInfoSearch> search(CCTConnection conn, ProductInfoSearchCriteria criteria, CommonUser user) throws Exception {
		log.info("search");
		
		List<ProductInfoSearch> listResult = new ArrayList<ProductInfoSearch>();
		
		int paramIndex = 0;
		
		Object[] params = new Object[6];
		
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getProductId(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(criteria.getVenderId(), conn.getDbType(), ResultType.NULL);
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
				ProductInfoSearch obj = new ProductInfoSearch();
				obj.setId(StringUtil.nullToString(rst.getString("Vendor_item_id")));
				obj.setVenderItemId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("Vendor_item_id"))));
				obj.setProductName(StringUtil.nullToString(rst.getString("item_short_name")));
				obj.setVenderShortName(StringUtil.nullToString(rst.getString("vendor_short_name")));
				obj.setStatus(StringUtil.nullToString(rst.getString("active")));
				
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
	protected ProductInfo searchById(CCTConnection conn, String id, CommonUser user) throws Exception {
		log.info("searchById");
		
		ProductInfo productInfo = null;
		
		int paramIndex = 0;
		
		Object[] params = new Object[1];
		
		params[paramIndex++] = StringUtil.replaceSpecialString(id, conn.getDbType(), ResultType.EMPTY);
		
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
				productInfo = new ProductInfo();
				
				productInfo.setVenderItemId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("Vendor_item_id"))));
				productInfo.setVenderCode(StringUtil.nullToString(rst.getString("vendor_code")));
				productInfo.setVenderShortName(StringUtil.nullToString(rst.getString("vendor_short_name")));
				productInfo.setVenderName(StringUtil.nullToString(rst.getString("vendor_name")));
				productInfo.setProductCode(StringUtil.nullToString(rst.getString("Item_code")));
				productInfo.setProductName(StringUtil.nullToString(rst.getString("item_short_name")));
				
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return productInfo;
	}
	
	protected List<StandardInfo> searchByStandard(CCTConnection conn, String venderItemId, String documentId, CommonUser user) throws Exception {
		log.info("searchByStandard");
		
		List<StandardInfo> listResult = new ArrayList<StandardInfo>();
		
		int paramIndex = 0;
		
		Object[] params = new Object[2];
		
		params[paramIndex++] = StringUtil.replaceSpecialString(venderItemId, conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(documentId, conn.getDbType(), ResultType.EMPTY);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchByStandard"
				, params);

		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				StandardInfo standardInfo = new StandardInfo();
				
				standardInfo.setDocTranId(ReferrerUtil.convertIdToReferrer(user.getUserName(), SessionUtil.getId(), StringUtil.nullToString(rst.getString("Doc_TranD_ID"))));
				standardInfo.setDocumentId(StringUtil.nullToString(rst.getString("DOCUMENT_ID")));
				standardInfo.setCertificateId(StringUtil.nullToString(rst.getString("CERTIFICATE_ID")));
				standardInfo.setCertificateName(StringUtil.nullToString(rst.getString("CERTIFICATE_NAME")));
				standardInfo.setDocExpireDate(StringUtil.nullToString(rst.getString("Doc_expire_date")));
				standardInfo.setContractEmail(StringUtil.nullToString(rst.getString("contract_email")));
				standardInfo.setPdfPath(StringUtil.nullToString(rst.getString("PDF_Path")));
				standardInfo.setTxtPath(StringUtil.nullToString(rst.getString("Text_Path")));
				
				listResult.add(standardInfo);
				
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return listResult;
	}

	@Override
	protected int add(CCTConnection conn, ProductInfo obj, CommonUser user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected long getSeqDocTransactionD(CCTConnection conn) throws Exception {
		log.info("getSeqDocTransactionD");
		
		long pk = 0;
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "getSeqDocTransactionD");

		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				pk = rst.getLong("docTranPK");
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return pk;
	}
	
	protected void insertDocTransactionMap(CCTConnection conn, String vendorItemId, long docTransId, CommonUser user) throws Exception {
		log.info("insertDocTransactionMap");
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = APPSUtil.convertLongValue(vendorItemId);
		params[paramIndex++] = docTransId;

		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertDocTransactionMap"
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
	
	protected void insertDocTransaction(CCTConnection conn, long docTransId, ProductInfo productInfo, CommonUser user) throws Exception {
		log.info("insertDocTransaction");
		
		int paramIndex = 0;
		Object[] params = new Object[7];
		params[paramIndex++] = docTransId;
		params[paramIndex++] = APPSUtil.convertLongValue(productInfo.getDocumentId());
		params[paramIndex++] = APPSUtil.convertLongValue(productInfo.getDialog().getCertificateId());
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getDocExpireDate(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getContractEmail(), conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getFileMetaPdf().getFileUploadFileName()[0], conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getFileMetaTxt().getFileUploadFileName()[0], conn.getDbType(), ResultType.NULL);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "insertDocTransaction"
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
	protected int edit(CCTConnection conn, ProductInfo productInfo, CommonUser user) throws Exception {
		log.info("setDocTransaction");
		
		int paramIndex = 0;
		Object[] params = new Object[5];
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getDocExpireDate(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getContractEmail(), conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getFileMetaPdf().getFileUploadFileName()[0], conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getFileMetaTxt().getFileUploadFileName()[0], conn.getDbType(), ResultType.NULL);
		params[paramIndex++] = StringUtil.replaceSpecialString(productInfo.getDialog().getDocTransId(), conn.getDbType(), ResultType.EMPTY);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "setDocTransaction"
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

	protected ProductInfoDialog searchTransDByCertId(CCTConnection conn, String certId, String docTransId) throws Exception {
		log.info("searchTransDByCertId");
		
		ProductInfoDialog dialog = new ProductInfoDialog();
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = StringUtil.replaceSpecialString(certId, conn.getDbType(), ResultType.EMPTY);
		params[paramIndex++] = StringUtil.replaceSpecialString(docTransId, conn.getDbType(), ResultType.EMPTY);
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchTransDByCertId"
				, params
				);

		log.debug(sql);

		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			if (rst.next()) {
				//dialog = new ProductInfoDialog();
				dialog.setDocTransId(StringUtil.nullToString(rst.getString("Doc_TranD_ID")));
				dialog.setCertificateId(StringUtil.nullToString(rst.getString("CERTIFICATE_ID")));
				dialog.setDocExpireDate(StringUtil.nullToString(rst.getString("Doc_expire_date")));
				dialog.setContractEmail(StringUtil.nullToString(rst.getString("contract_email")));
				dialog.getFileMetaPdf().setFileUploadFileName(new String[]{StringUtil.nullToString(rst.getString("PDF_Path"))});
				dialog.getFileMetaTxt().setFileUploadFileName(new String[]{StringUtil.nullToString(rst.getString("Text_Path"))});
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return dialog;
	}
	
	@Override
	protected int delete(CCTConnection conn, String ids, CommonUser user) throws Exception {
		log.info("deleteDocTransaction");
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteDocTransaction"
				, StringUtil.replaceSpecialString(ids, conn.getDbType(), ResultType.EMPTY)
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

	protected int deleteDocVenderItemMap(CCTConnection conn, String ids, CommonUser user) throws Exception {
		log.info("deleteDocVenderItemMap");
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "deleteDocVenderItemMap"
				, StringUtil.replaceSpecialString(ids, conn.getDbType(), ResultType.EMPTY)
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

	/**
	 * บันทึกการเปลื่ยนสถานะ_SQL<br>
	 * activeFlag = Y (ใช้งาน)<br>
	 * activeFlag = N (ยกเลิก)<br>
	 */
	protected int setActiveStatus(CCTConnection conn, String ids, String activeFlag, CommonUser user) throws Exception {
		
		int paramIndex = 0;
		Object[] params = new Object[2];
		params[paramIndex++] = activeFlag;
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
	protected boolean checkDup(CCTConnection conn, ProductInfo obj, CommonUser user) throws Exception {
		log.info("searchCheckDup");
		
		boolean isDuplicate = true;
		
		int paramIndex = 0;
		Object[] params = new Object[4];
		
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getVenderItemId());
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getDocumentId());
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getDialog().getCertificateId());
		params[paramIndex++] = APPSUtil.convertLongValue(obj.getDialog().getDocTransId());
		
		String sql = SQLUtil.getSQLString(
				conn.getSchemas()
				, getSqlPath().getClassName()
				, getSqlPath().getPath()
				, "searchCheckDup"
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
				} else {
					isDuplicate = false;
				}
			}
		} catch (Exception e) {
			throw e;
			
		} finally {
			CCTConnectionUtil.closeAll(rst, stmt);
		}
		return isDuplicate;
	}

}
