package com.sit.app.core.product.productinfo.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.product.productinfo.domain.ProductInfo;
import com.sit.app.core.product.productinfo.domain.ProductInfoDialog;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearch;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearchCriteria;
import com.sit.app.core.product.productinfo.domain.StandardInfo;
import com.sit.common.CommonUser;
import com.sit.exception.DuplicateException;

import util.calendar.CalendarUtil;
import util.database.CCTConnection;
import util.file.FileManagerUtil;

public class ProductInfoService extends AbstractService{

	private ProductInfoDAO dao;
	
	public ProductInfoService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		dao = new ProductInfoDAO(log);
		dao.setSqlPath(SQLPath.PRODUCT_INFO);
	}
	
	protected long countData(ProductInfoSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<ProductInfoSearch> search(ProductInfoSearchCriteria criteria) throws Exception{
		return dao.search(conn, criteria, user);
	}
	
	protected ProductInfo searchById(String id) throws Exception{
		return dao.searchById(conn, id, user);
	}

	protected List<StandardInfo> searchByStandard(String venderItemId, String documentId) throws Exception {
		return dao.searchByStandard(conn, venderItemId, documentId, user);
	}
	
	protected void checkDup(ProductInfo productInfo) throws Exception {
		try {
			boolean isDup = dao.checkDup(conn, productInfo, user);
			log.debug("isDup: " + isDup);
			if (isDup) {
				throw new DuplicateException(); // Throw DuplicateException ถ้าพบว่าข้อมูลซ้ำ
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	protected int add(ProductInfo productInfo) throws Exception {
		try {
			//get sequence
			long docTransId = dao.getSeqDocTransactionD(conn);
			
			//insert transaction d
			dao.insertDocTransaction(conn, docTransId, productInfo, user);
			
			//insert transaction map
			dao.insertDocTransactionMap(conn, productInfo.getVenderItemId(), docTransId, user);
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		return 0;
	}
	
	protected void manageFile(ProductInfo productInfo) throws Exception {
		log.info("manage file");
		
		try {
			//PDF
			if(productInfo.getDialog().getFileMetaPdf().getFileUploadFileNameTmp() != null 
					&& !productInfo.getDialog().getFileMetaPdf().getFileUploadFileNameTmp()[0].isEmpty()) {
				
				String srcPdfPath = ParameterConfig.getParameter().getAttachFile().getTmpPath() 
						+ productInfo.getDialog().getFileMetaPdf().getFileUploadFileNameTmp()[0];
				
				FileManagerUtil.moveFile(srcPdfPath, productInfo.getDialog().getFileMetaPdf().getFileUploadFileName()[0]);
			}
			
			if(productInfo.getDialog().getFileMetaTxt().getFileUploadFileNameTmp() != null 
					&& !productInfo.getDialog().getFileMetaTxt().getFileUploadFileNameTmp()[0].isEmpty()) {
				
				String srcTxtPath = ParameterConfig.getParameter().getAttachFile().getTmpPath() 
						+ productInfo.getDialog().getFileMetaTxt().getFileUploadFileNameTmp()[0];
				
				FileManagerUtil.moveFile(srcTxtPath, productInfo.getDialog().getFileMetaTxt().getFileUploadFileName()[0]);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	protected void concatPathFile(ProductInfo productInfo) throws Exception {
		log.info("concat path file");
		
		try {
			StringBuilder descPdfPath = new StringBuilder();
			if(productInfo.getDialog().getFileMetaPdf().getFileUploadFileNameTmp() != null 
					&& !productInfo.getDialog().getFileMetaPdf().getFileUploadFileNameTmp()[0].isEmpty()) {
				
				descPdfPath.append(ParameterConfig.getParameter().getAttachFile().getServerPath());
				descPdfPath.append(productInfo.getProductName()).append(CalendarUtil.DELIMITER_SLASH);
				descPdfPath.append(productInfo.getVenderShortName()).append(CalendarUtil.DELIMITER_SLASH);
				descPdfPath.append(productInfo.getDialog().getCertificateId()).append(CalendarUtil.DELIMITER_SLASH);
				descPdfPath.append(productInfo.getDialog().getFileMetaPdf().getFileUploadFileName()[0]);
				
				productInfo.getDialog().getFileMetaPdf().setFileUploadFileName(new String[]{descPdfPath.toString()});
			} else {
				productInfo.getDialog().getFileMetaPdf().setFileUploadFileName(new String[1]);
			}
			
			//TXT
			StringBuilder descTxtPath = new StringBuilder();
			if(productInfo.getDialog().getFileMetaTxt().getFileUploadFileNameTmp() != null 
					&& !productInfo.getDialog().getFileMetaTxt().getFileUploadFileNameTmp()[0].isEmpty()) {
				
				descTxtPath.append(ParameterConfig.getParameter().getAttachFile().getServerPath());
				descTxtPath.append(productInfo.getProductName()).append(CalendarUtil.DELIMITER_SLASH);
				descTxtPath.append(productInfo.getVenderShortName()).append(CalendarUtil.DELIMITER_SLASH);
				descTxtPath.append(productInfo.getDialog().getCertificateId()).append(CalendarUtil.DELIMITER_SLASH);
				descTxtPath.append(productInfo.getDialog().getFileMetaTxt().getFileUploadFileName()[0]);
				
				productInfo.getDialog().getFileMetaTxt().setFileUploadFileName(new String[]{descTxtPath.toString()});
			} else {
				productInfo.getDialog().getFileMetaTxt().setFileUploadFileName(new String[1]);
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
	}
	
	/**
	 * ดึงข้อมูลไปแสดงบน dialog เพื่อแก้ไข
	 * @param certId
	 * @return
	 * @throws Exception
	 */
	protected ProductInfoDialog searchTransDByCertId(String certId, String docTransId) throws Exception {
		return dao.searchTransDByCertId(conn, certId, docTransId);
	}
	
	protected int edit(ProductInfo productInfo) throws Exception {
		return dao.edit(conn, productInfo, user);
	}
	
	protected int deleteDocVenderItemMap(String ids, CommonUser user) throws Exception {
		return dao.deleteDocVenderItemMap(conn, ids, user);
	}
	
	protected int delete(String ids, CommonUser user) throws Exception {
		return dao.delete(conn, ids, user);
	}

	protected int setActiveStatus(String ids, String activeFlag) throws Exception {
		return dao.setActiveStatus(conn, ids, activeFlag, user);
	}

}
