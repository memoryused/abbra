package com.sit.app.core.security.member.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.security.member.domain.Member;
import com.sit.app.core.security.member.domain.MemberData;
import com.sit.app.core.security.member.domain.MemberSearch;
import com.sit.app.core.security.member.domain.MemberSearchCriteria;
import com.sit.app.core.security.membergroup.domain.Group;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Operator;
import com.sit.exception.DuplicateException;

import util.APPSUtil;
import util.database.CCTConnection;

public class MemberService extends AbstractService{
	
	private MemberDAO dao;
	
	public MemberService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new MemberDAO(log);
		this.dao.setSqlPath(SQLPath.MEMBER);
	}

	protected long countData(MemberSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}
	
	protected List<MemberSearch> search(MemberSearchCriteria criteria) throws Exception{
		return dao.search(conn, criteria, user);
	}
	
	protected int setActiveStatus(String ids, String activeFlag) throws Exception {
		return dao.setActiveStatus(conn, ids, activeFlag, user);
	}
	
	protected int delete(String ids) throws Exception {
		return dao.delete(conn, ids, user);
	}
	
	/**
	 * ตรวจสอบข้อมูลซ่ำ ถ้าเป็นการแก้ไขให้ส่งตรวจสอบปกติ, ถ้าเป็นเพิ่มให้ส่ง id = null
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	protected void checkDup(Member member) throws Exception {
		try {
			boolean isDup = dao.checkDup(conn, member, user);
			log.debug("isDup: " + isDup);
			if (isDup) {
				throw new DuplicateException(); // Throw DuplicateException ถ้าพบว่าข้อมูลซ้ำ
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Add User<br>
	 * 1. บันทึกข้อมูลผุ้ใช้<br>
	 * 2. บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของผู้ใช้<br>
	 * 3. บันทึกเพิ่มรายการกลุ่มผู้ใช้ที่อ้างอิงกับผู้ใช้<br>
	 * 
	 * @param obj
	 * @param newPassword
	 * @return
	 * @throws Exception
	 */
	protected String add(Member obj) throws Exception {

		// หา pk
		Long userId = dao.getUserSEQ(conn);

		// บันทึกข้อมูลผู้ใช้
		dao.add(conn, userId, obj.getMemberData(), user);

		// วน Loop บันทึกเพิ่มรายการสิทธิโปรแกรม
		Object[] operators = APPSUtil.getListOperatorIdFromListOperator(obj.getListProgram());
		insertUserOperator(userId, operators);

		// วน Loop บันทึกเพิ่มรายการกลุ่ม
		insertUserGroup(userId, obj.getListGroup());
		
		return userId.toString();

	}
	
	/**
	 * บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของผู้ใช้
	 * 
	 * @param userId
	 * @param listOperator
	 * @throws Exception
	 */
	protected void insertUserOperator(long userId, Object[] listOperator) throws Exception {

		for (Object operator : listOperator) {
			dao.insertUserOperator(conn, userId, operator.toString(), user);
		}
	}
	
	/**
	 * บันทึกเพิ่มรายการกลุ่มผู้ใช้ที่อ้างอิงกับผู้ใช้
	 * 
	 * @param userId
	 * @param listUser
	 * @throws Exception
	 */
	protected void insertUserGroup(long userId, List<Group> listGroup) throws Exception {

		for (Group groupInUser : listGroup) {
			if (groupInUser.getDeleteFlag().equals(GlobalVariable.FLAG_DELETED)) {
				continue;
			}
			dao.insertUserGroup(conn, userId, groupInUser, user);
		}
	}
	
	/**
	 * แสดงข้อมูลผู้ใช้<br>
	 * แสดงรายการข้อมูลสิทธิ์โปรแกรมหรือรายงานอ้างอิง<br>
	 * แสดงรายการข้อมูลกลุ่มผู้ใช้อ้างอิง<br>
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected Member searchById(String id) throws Exception {

		String programKey = "P";
		Member member = new Member();

		// แสดงข้อมูลผู้ใช้
		MemberData memberData = dao.searchByIds(conn, id, user);
		
		//PROGRAM
		Operator operatorLevel = dao.searchLevel(conn, programKey, id, user);
		Map<String, Operator> mapOperator = dao.searchProgramByUserId(conn, id, programKey, user, operatorLevel);
		List<Operator> listProgram = APPSUtil.generateOperatorResult(mapOperator);
		
		// แสดงรายการข้อมูลกลุ่มผุ้ใช้อ้างอิง
		List<Group> listGroup = dao.searchGroupByUserId(conn, id, user);

		member.setMemberData(memberData);
		member.setListProgram(listProgram);
		member.setListGroup(listGroup);

		return member;
	}
	
	/**
	 * Edit User<br>
	 * 1. บันทึกแก้ไขข้อมูลผุ้ใช้<br>
	 * 2. บันทึกแก้ไขรายการสิทธิ์โปรแกรมหรือรายงานของผู้ใช้<br>
	 * 3. บันทึกแก้ไขรายการกลุ่มผู้ใช้ที่อ้างอิงกับผู้ใช้<br>
	 * 
	 * @param obj
	 * @param newPassword
	 * @return
	 * @throws Exception
	 */
	protected void edit(Member obj, String flagResetPassword) throws Exception {
		
		String password = flagResetPassword.equals(GlobalVariable.FLAG_ACTIVE) ? APPSUtil.passwordEncryptOneWay(obj.getMemberData().getPassword()):null;
		
		// บันทึกแก้ผู้ใช้
		dao.edit(conn, obj.getMemberData(), password, user);

		// ลบสิทธิ์โปรแกรมและรายงาน
		dao.delOperator(conn, obj.getMemberData().getUserId(), user);
		
		// วน Loop บันทึกเพิ่มรายการสิทธิโปรแกรม
		Object[] operators = APPSUtil.getListOperatorIdFromListOperator(obj.getListProgram());
		insertUserOperator(APPSUtil.convertLongValue(obj.getMemberData().getUserId()), operators);

		// ลบกลุ่มผุ้ใช้
		dao.delGroup(conn, obj.getMemberData().getUserId(), user);

		// วน Loop บันทึกเพิ่มกลุ่มผู้ใช้
		insertUserGroup(APPSUtil.convertLongValue(obj.getMemberData().getUserId()), obj.getListGroup());
	}
}
