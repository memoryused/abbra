package com.sit.app.core.master.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.master.product.domain.Item;
import com.sit.app.core.master.product.domain.ItemSearch;
import com.sit.app.core.master.product.domain.ItemSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.exception.MaxExceedException;

import util.database.CCTConnection;

public class ItemManager extends AbstractManager<ItemSearchCriteria, ItemSearch, Item, CommonUser>{

	private ItemService service;
	
	public ItemManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		service = new ItemService(conn, user, log);
	}

	@Override
	public List<ItemSearch> search(ItemSearchCriteria criteria) throws Exception {
		List<ItemSearch> listResult = new ArrayList<>();
		
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
	public Item searchById(String id) throws Exception {
		return service.searchById(id);
	}

	@Override
	public int add(Item obj) throws Exception {
		service.checkDup(obj);
		
		try {
			conn.setAutoCommit(false);
			
			service.add(obj);
			
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
	public int edit(Item obj) throws Exception {
		service.checkDup(obj);
		
		try {
			conn.setAutoCommit(false);
			
			service.edit(obj);
			
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
	public int delete(String ids) throws Exception {
		return service.delete(ids);
	}

	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		return 0;
	}

	public int setActiveStatus(String ids, String activeFlag) throws Exception{
		return service.setActiveStatus(ids, activeFlag);
	}
}
