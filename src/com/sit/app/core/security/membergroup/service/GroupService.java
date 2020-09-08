package com.sit.app.core.security.membergroup.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.security.member.domain.MemberSearch;
import com.sit.app.core.security.membergroup.domain.Group;
import com.sit.app.core.security.membergroup.domain.GroupData;
import com.sit.app.core.security.membergroup.domain.GroupSearchCriteria;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Operator;
import com.sit.exception.DuplicateException;

import util.APPSUtil;
import util.database.CCTConnection;

public class GroupService extends AbstractService {

	private GroupDAO dao;
	
	public GroupService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new GroupDAO(log);
		this.dao.setSqlPath(SQLPath.MEMBER_GROUP);
	}

	/**
	 * นับจำนวนข้อมูลที่ค้นพบทั้งหมด
	 * 
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	protected long countData(GroupSearchCriteria criteria) throws Exception {
		return dao.countData(conn, criteria, user);
	}

	/**
	 * ค้นหาข้อมูล
	 * 
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	protected List<Group> search(GroupSearchCriteria criteria) throws Exception {
		return dao.search(conn, criteria, user);
	}

	/**
	 * บันทึกการเปลื่ยนสถานะ
	 * 
	 * @param ids
	 * @param activeFlag
	 * @throws Exception
	 */
	protected void setActiveStatus(String ids, String activeFlag) throws Exception {
		dao.setActiveStatus(conn, ids, activeFlag, user);
	}

	/**
	 * ตรวจสอบข้อมูลซ่ำ ถ้าเป็นการแก้ไขให้ส่งตรวจสอบปกติ, ถ้าเป็นเพิ่มให้ส่ง id = null
	 * 
	 * @param group
	 * @throws Exception
	 */
	protected void checkDup(Group group) throws Exception {
		try {
			boolean isDup = dao.checkDup(conn, group, user);
			log.debug("isDup: " + isDup);
			if (isDup) {
				throw new DuplicateException(); // Throw DuplicateException ถ้าพบว่าข้อมูลซ้ำ
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Add Group<br>
	 * 1. บันทึกเพิ่มข้อมูลกลุ่มผุ้ใช้<br>
	 * 2. บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของกลุ่มผู้ใช้<br>
	 * @param obj
	 * @param newPassword
	 * @return
	 * @throws Exception
	 */
	protected long add(GroupData obj) throws Exception {

		// หา pk
		long groupId = dao.getGroupSEQ(conn);

		// บันทึกข้อมูลผู้ใช้
		dao.add(conn, groupId, obj.getGroup(), user);

		// วน Loop บันทึกเพิ่มรายการสิทธิโปรแกรม
		Object[] operators = APPSUtil.getListOperatorIdFromListOperator(obj.getListProgram());
		insertGroupOperator(groupId, operators);

		// วน Loop บันทึกเพิ่มรายการผู้ใช้
		insertGroupUser(groupId, obj.getListUser());

		return groupId;
	}

	/**
	 * บันทึกเพิ่มรายการสิทธิ์โปรแกรมหรือรายงานของกลุ่มผู้ใช้
	 * 
	 * @param groupId
	 * @param listOperator
	 * @throws Exception
	 */
	protected void insertGroupOperator(long groupId, Object[] listOperator) throws Exception {

		for (Object operator : listOperator) {
			dao.insertGroupOperator(conn, groupId, operator.toString(), user);
		}
	}

	/**
	 * บันทึกเพิ่มรายการผู้ใช้ที่อ้างอิงกับกลุ่มผู้ใช้
	 * 
	 * @param groupId
	 * @param listUser
	 * @throws Exception
	 */
	protected void insertGroupUser(long groupId, List<MemberSearch> listUser) throws Exception {

		for (MemberSearch userInGroup : listUser) {
			if (userInGroup.getDeleteFlag().equals(GlobalVariable.FLAG_DELETED)) {
				continue;
			}
			dao.insertGroupUser(conn, groupId, userInGroup, user);
		}
	}
	
	/**
	 * Edit Group<br>
	 * 1. บันทึกแก้ไขข้อมูลกลุ่มผุ้ใช้<br>
	 * 2. บันทึกแก้ไขรายการสิทธิ์โปรแกรมหรือรายงานของกลุ่มผู้ใช้<br>
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected void edit(GroupData obj) throws Exception {

		// บันทึกแก้กลุ่มผู้ใช้
		dao.edit(conn, obj.getGroup(), user);

		// ลบสิทธิ์โปรแกรมและรายงาน
		dao.delOperator(conn, obj.getGroup().getId(), user);
		
		// วน Loop บันทึกเพิ่มรายการสิทธิโปรแกรม
		Object[] operators = APPSUtil.getListOperatorIdFromListOperator(obj.getListProgram());
		insertGroupOperator(APPSUtil.convertLongValue(obj.getGroup().getId()), operators);

		// ลบกลุ่มผุ้ใช้
		dao.delUser(conn, obj.getGroup().getId(), user);
		// วน Loop บันทึกเพิ่มกลุ่มผู้ใช้
		insertGroupUser(APPSUtil.convertLongValue(obj.getGroup().getId()), obj.getListUser());
	}

	/**
	 * แสดงข้อมูลกลุ่มผู้ใช้<br>
	 * แสดงรายการข้อมูลสิทธิ์โปรแกรมหรือรายงานอ้างอิง<br>
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	protected GroupData searchById(String id) throws Exception {

		GroupData groupData = new GroupData();

		// แสดงข้อมูลกลุ่มผู้ใช้
		Group groupInfo = dao.searchById(conn, id, user);
		
		Operator operatorLevel = dao.searchProgramLevelByGroupId(conn, id,user);
		Map<String, Operator> mapOperator = dao.searchProgramByGroupId(conn, id, user, operatorLevel);
		List<Operator> listProgram = APPSUtil.generateOperatorResult(mapOperator);
		
		// แสดงรายการข้อมูลผุ้ใช้อ้างอิง
		List<MemberSearch> listUser = dao.searchUserByGroupId(conn, id, user);

		groupData.setGroup(groupInfo);
		groupData.setListProgram(listProgram);
		groupData.setListUser(listUser);

		return groupData;
	}
}
