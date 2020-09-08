package com.sit.app.core.security.member.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.security.member.domain.Member;
import com.sit.app.core.security.member.domain.MemberSearch;
import com.sit.app.core.security.member.domain.MemberSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.exception.MaxExceedException;

import util.database.CCTConnection;

public class MemberManager extends AbstractManager<MemberSearchCriteria, MemberSearch, Member, CommonUser>{

	private MemberService service;
	
	public MemberManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.service = new MemberService(conn, user, log);
	}

	@Override
	public List<MemberSearch> search(MemberSearchCriteria criteria) throws Exception {
		List<MemberSearch> listResult = new ArrayList<>();
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
	public Member searchById(String id) throws Exception {
		return service.searchById(id);
	}

	@Override
	public int add(Member obj) throws Exception {
		service.checkDup(obj);

		try {
			conn.setAutoCommit(false);

			// บันทึกเพิ่มผู้ใช้
			String userId = service.add(obj);
			
			//service.afterEditUser(userId, null);

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
	public int edit(Member obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Process3: Edit User<br>
	 * แก้ไขข้อมูล พร้อมตรวจสอบข้อมูลซ้ำ
	 */
	public int edit(Member obj, String flagResetPassword) throws Exception {
		
		service.checkDup(obj);

		try {
			conn.setAutoCommit(false);
			
			service.edit(obj, flagResetPassword);
			
			//service.afterEditUser(obj.getUser().getId(), null);
					
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
		}
		
		return 0;
	}
	
	public int setActiveStatus(String ids, String activeFlag) throws Exception{
		return service.setActiveStatus(ids, activeFlag);
	}
	
	@Override
	public int delete(String ids) throws Exception {
		return service.delete(ids);
	}

	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
