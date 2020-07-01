package com.sit.app.core.product.home.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.product.home.domain.ProductHomeSearch;
import com.sit.app.core.product.home.domain.ProductHomeSearchCriteria;
import com.sit.common.CommonUser;

import util.database.CCTConnection;

public class ProductHomeService extends AbstractService {

	private ProductHomeDAO dao = null;
	
	public ProductHomeService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new ProductHomeDAO(log);
		this.dao.setSqlPath(SQLPath.PRODUCT_HOME);
	}

	public Map<String, Object> getFile(String refId) throws Exception {
		return dao.getFile(conn, refId);
	}
	
	protected long countData(ProductHomeSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<ProductHomeSearch> search(ProductHomeSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria, user);
	}
}
