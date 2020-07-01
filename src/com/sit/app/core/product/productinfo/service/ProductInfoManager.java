package com.sit.app.core.product.productinfo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.product.productinfo.domain.ProductInfo;
import com.sit.app.core.product.productinfo.domain.ProductInfoDialog;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearch;
import com.sit.app.core.product.productinfo.domain.ProductInfoSearchCriteria;
import com.sit.app.core.product.productinfo.domain.StandardInfo;
import com.sit.common.CommonUser;
import com.sit.exception.MaxExceedException;

import util.database.CCTConnection;

public class ProductInfoManager extends AbstractManager<ProductInfoSearchCriteria, ProductInfoSearch, ProductInfo, CommonUser>{

	private ProductInfoService service;
	
	public ProductInfoManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		service = new ProductInfoService(conn, user, log);
	}

	@Override
	public List<ProductInfoSearch> search(ProductInfoSearchCriteria criteria) throws Exception {
		List<ProductInfoSearch> listResult = new ArrayList<>();
		
		try {
			//COUNT SEARCH
			criteria.setTotalResult(service.countData(criteria));
			
			if (criteria.getTotalResult() == 0) {

			} else if ((criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceed())) {
				throw new MaxExceedException();
			} else {
				listResult = service.search(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return listResult;
	}

	@Override
	public ProductInfo searchById(String id) throws Exception {
		return service.searchById(id);
	}

	public List<StandardInfo> searchByStandard(String venderItemId, String documentId) throws Exception {
		return service.searchByStandard(venderItemId, documentId);
	}
	
	@Override
	public int add(ProductInfo productInfo) throws Exception {
		service.checkDup(productInfo);
		
		try {
			conn.setAutoCommit(false);
			
			//set path file
			service.concatPathFile(productInfo);
			
			//insert 
			service.add(productInfo);
			
			//manage file
			service.manageFile(productInfo);
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		return 0;
	}

	@Override
	public int edit(ProductInfo productInfo) throws Exception {
		service.checkDup(productInfo);
		
		try {
			conn.setAutoCommit(false);
			
			//set path file
			service.concatPathFile(productInfo);
			
			//insert 
			service.edit(productInfo);
			
			//manage file
			service.manageFile(productInfo);
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		return 0;
	}

	/**
	 * ดึงข้อมูลไปแสดงบน dialog เพื่อแก้ไข
	 * @param certId
	 * @return
	 * @throws Exception
	 */
	public ProductInfoDialog searchTransDByCertId(String certId, String docTransId) throws Exception {
		return service.searchTransDByCertId(certId, docTransId);
	}
	
	public int deleteDocVenderItemMap(String ids) throws Exception {
		return service.deleteDocVenderItemMap(ids, user);
	}
	
	@Override
	public int delete(String ids) throws Exception {
		return service.delete(ids, user);
	}

	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int setActiveStatus(String ids, String activeFlag) throws Exception{
		return service.setActiveStatus(ids, activeFlag);
	}

}
