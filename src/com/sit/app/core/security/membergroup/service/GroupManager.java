package com.sit.app.core.security.membergroup.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.security.membergroup.domain.Group;
import com.sit.app.core.security.membergroup.domain.GroupData;
import com.sit.app.core.security.membergroup.domain.GroupSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.exception.MaxExceedException;

import util.database.CCTConnection;

public class GroupManager extends AbstractManager<GroupSearchCriteria, Group, GroupData, CommonUser>{

	private GroupService service;
	
	public GroupManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.service = new GroupService(conn, user, log);
	}

	@Override
	public List<Group> search(GroupSearchCriteria criteria) throws Exception {
		List<Group> listResult = new ArrayList<Group>();
		try {
			criteria.setTotalResult(service.countData(criteria));
			log.debug("Count data [" + criteria.getTotalResult() + "] record.");

			if (criteria.getTotalResult() == 0) {
				// Nothing
			} else if ((criteria.isCheckMaxExceed()) && (criteria.getTotalResult() > ParameterConfig.getParameter().getApplication().getMaxExceed())) {
				// เกินจำนวนที่กำหนด
				throw new MaxExceedException();
			} else {
				// ค้นหาข้อมูล
				listResult = service.search(criteria);
			}
		} catch (Exception e) {
			throw e;
		}

		return listResult;
	}

	@Override
	public GroupData searchById(String id) throws Exception {
		return service.searchById(id);
	}

	@Override
	public int add(GroupData obj) throws Exception {
		service.checkDup(obj.getGroup());

		try {
			conn.setAutoCommit(false);

			long groupId = service.add(obj);
			
			//service.afterEditGroup(Long.toString(groupId));
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
	public int edit(GroupData obj) throws Exception {
		service.checkDup(obj.getGroup());

		try {
			conn.setAutoCommit(false);
			
			service.edit(obj);

			//service.afterEditGroup(obj.getGroup().getId());
					
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
	 * Active or Inactive
	 * @param ids
	 * @param activeFlag
	 * @return
	 * @throws Exception
	 */
	public int setActiveStatus(String ids, String activeFlag) throws Exception {
		
		try {
			conn.setAutoCommit(false);
			
			service.setActiveStatus(ids, activeFlag);
					
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
