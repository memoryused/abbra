package com.sit.app.core.product.home.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.product.home.domain.ProductHomeSearch;
import com.sit.app.core.product.home.domain.ProductHomeSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.exception.MaxExceedException;

import util.database.CCTConnection;

public class ProductHomeManager extends AbstractManager<ProductHomeSearchCriteria, ProductHomeSearch, Object, CommonUser>{

	private ProductHomeService service;
	
	public ProductHomeManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.service = new ProductHomeService(conn, user, log);
	}

	public Map<String, Object> getFile(String refId) throws Exception {
		return service.getFile(refId);
	}
	
	@Override
	public List<ProductHomeSearch> search(ProductHomeSearchCriteria criteria) throws Exception {
		List<ProductHomeSearch> listResult = new ArrayList<>();
		
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
	public Object searchById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String ids) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
