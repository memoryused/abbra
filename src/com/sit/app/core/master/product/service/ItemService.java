package com.sit.app.core.master.product.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.master.product.domain.Item;
import com.sit.app.core.master.product.domain.ItemSearch;
import com.sit.app.core.master.product.domain.ItemSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.exception.DuplicateException;

import util.database.CCTConnection;

public class ItemService extends AbstractService{

	private ItemDAO dao;
	
	public ItemService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		dao = new ItemDAO(log);
		dao.setSqlPath(SQLPath.ITEM);
	}

	protected long countData(ItemSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<ItemSearch> search(ItemSearchCriteria criteria) throws Exception{
		return dao.search(conn, criteria, user);
	}
	
	protected Item searchById(String id) throws Exception{
		return dao.searchById(conn, id, user);
	}
	
	protected void checkDup(Item item) throws Exception {
		try {
			boolean isDup = dao.checkDup(conn, item, user);
			log.debug("isDup: " + isDup);
			if (isDup) {
				throw new DuplicateException(); // Throw DuplicateException ถ้าพบว่าข้อมูลซ้ำ
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	protected int add(Item item) throws Exception {
		return dao.add(conn, item, user);
	}
	
	protected int edit(Item item) throws Exception {
		return dao.edit(conn, item, user);
	}
	
	protected int setActiveStatus(String ids, String activeFlag) throws Exception {
		return dao.setActiveStatus(conn, ids, activeFlag, user);
	}
	
	protected int delete(String ids) throws Exception {
		return dao.delete(conn, ids, user);
	}
}
