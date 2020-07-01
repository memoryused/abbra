package com.sit.app.core.security.login.service;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractService;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.config.parameter.domain.SQLPath;
import com.sit.app.core.security.login.domain.User;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;
import com.sit.domain.Operator;
import com.sit.exception.AuthenticateException;

import util.APPSUtil;
import util.calendar.CalendarUtil;
import util.database.CCTConnection;

public class LoginService extends AbstractService {

	private LoginDAO dao = null;

	public LoginService(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.dao = new LoginDAO(log);
		this.dao.setSqlPath(SQLPath.LOGIN_SQL);
	}

	protected User authentication(String ip, String username, String password) throws Exception {
		// 1. ตรวจสอบผู้ใช้งาน
		User tempUser = dao.searchUser(conn, username);
		String tempPassword = APPSUtil.passwordEncryptOneWay(password);
				
		// 2. ถ้ารหัสผ่านไม่ถูกต้อง
		if (tempUser == null) {
			throw new AuthenticateException(GlobalVariable.MessageCode.INVALID_USERNAME.getValue());

		} else if (!tempUser.getPassword().equals(tempPassword)) {

			// 2.1. บันทึกการ LogIn ผิด
			dao.addLoginWrong(conn, username, tempPassword, tempUser.getId());

			// 2.2. ตรวจสอบการระงับใช้
			checkLock(tempUser.getId());
		}
		
		// 3. ตรวจสถานะต่างๆ ตามเงื่อนไข
		String format = ParameterConfig.getParameter().getDateFormat().getForDatabaseSelectHHMMSS();
		Locale locale = ParameterConfig.getParameter().getApplication().getDatabaseLocale();
		Calendar currentDate = CalendarUtil.getCalendarFromDateString(APPSUtil.getCurrentDateTimeDB(conn.getConn()), format, locale);

		// 3.1. วันที่เริ่มใช้งาน
		if(tempUser.getStartDate().isEmpty() == false) {

			Calendar startDate = CalendarUtil.getCalendarFromDateString(tempUser.getStartDate(), format, locale);

			// start_date <= current_date
			if(currentDate.before(startDate)) {
				throw new AuthenticateException(GlobalVariable.MessageCode.USER_NOT_AVAILABLE.getValue());
			}

		}

		// 3.2. วันหมดอายุผู้ใช้งาน
		if(tempUser.getEndDate().isEmpty() == false) {
			Calendar endDate = CalendarUtil.getCalendarFromDateString(tempUser.getEndDate(), format, locale);
			int intCurrentDate = Integer.parseInt(CalendarUtil.getDateStringFromCalendar(currentDate, "YYYYMMDD"));
			int intEndDate = Integer.parseInt(CalendarUtil.getDateStringFromCalendar(endDate, "YYYYMMDD"));
			
			// เช็คเงื่อนไขว่าวันที่สิ้นสุดน้อยกว่าหรือเท่ากับวันที่ปัจจุบันจะสามารถล๊อคอินได้ ถ้าไม่จะแจ้งว่า password หมดอายุ
			if(intEndDate < intCurrentDate) {
				throw new AuthenticateException(GlobalVariable.MessageCode.PASSWORD_EXPIRED.getValue());
			}

		}

		// 3.3. สถานะใช้งาน
		if(tempUser.getActive().getCode().equals(GlobalVariable.FLAG_INACTIVE)) {
			throw new AuthenticateException(GlobalVariable.MessageCode.FOUND_PROBLEM.getValue());
		}


		// 4. ตรวจสอบสถานะการล็อคผู้ใช้งาน
		if (tempUser.getLockStatus().equals(GlobalVariable.LockStatus.LOCKED.getValue())) {
			// ปลดล็อค
			checkUnlock(tempUser.getId());
		}

		// 5. ตรวจสอบวันหมดอายุรหัสผ่าน
		
		return tempUser;
	}
	
	/**
	 * ตรวจสอบการระงับใช้งาน
	 * @param userId
	 * @throws Exception
	 */
	protected void checkLock(String userId) throws Exception {
		Integer loginWrongTime = 3;	//FIXME 3 Time login wrong
		
		// 1. หาจำนวนครั้งที่ Login ผิด
		int loginWrong = dao.searchCountLoginWrong(conn, userId, loginWrongTime);	

		//ถ้า login ผิดเกิน จะระงับการใช้งาน
		if(loginWrong >= loginWrongTime) {
			dao.updatelockUser(conn, userId, GlobalVariable.LockStatus.LOCKED.getValue());
			throw new AuthenticateException(GlobalVariable.MessageCode.USER_IS_LOCKED.getValue());
		} else {
			throw new AuthenticateException(GlobalVariable.MessageCode.INVALID_USERNAME.getValue());
		}
	}
	
	/**
	 * ปลดล็อค
	 * @param userId
	 * @throws Exception
	 */
	protected void checkUnlock(String userId) throws Exception {

		// หาสถานะที่เกินระยะเวลาที่ต้องระงับแล้วหรือยัง
		int lockedStatus = dao.calculateLockMinutes(conn, userId);

		//ถึงเวลาปลดล็อค
		if(lockedStatus == 1) {
			conn.setAutoCommit(false);
			try {

				// ถ้าเลยเวลาแล้วให้ปลดล็อด
				dao.updatelockUser(conn, userId, GlobalVariable.LockStatus.UNLOCKED.getValue());
				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}

		//ไม่ถึงเวลาปลดล็อค
		} else {
			throw new AuthenticateException(GlobalVariable.MessageCode.USER_IS_LOCKED.getValue());
		}

	}

	protected void updateLoginWrong(String userId) throws Exception {
		dao.updateLoginWrong(conn, userId);
	}
	
	protected CommonUser copyUser(User tempUser, String ip) throws Exception {

		CommonUser commonUser = new CommonUser();
		commonUser.setUserId(tempUser.getId());
		commonUser.setUserName(tempUser.getUsername());
		commonUser.setFirstName(tempUser.getForname());
		commonUser.setLastName(tempUser.getSurname());
		commonUser.setPrefixName(tempUser.getPrefixName());
		commonUser.setStartDate(tempUser.getStartDate());
		commonUser.setEndDate(tempUser.getEndDate());
		commonUser.setActive(tempUser.getActive().getCode());
		commonUser.setLockStatus(tempUser.getLockStatus());
		commonUser.setPasswordDate(tempUser.getPasswordDate());
		commonUser.setLocale(Locale.ENGLISH);
		commonUser.setEmail(tempUser.getEmail());
		commonUser.setFullName(tempUser.getFullName());
		commonUser.setOrganizationId(tempUser.getOrganizationId());

		return commonUser;
	}
	
	protected CommonUser tempUser() throws Exception {

		CommonUser commonUser = new CommonUser();
		commonUser.setUserId("99");
		commonUser.setUserName("admin");
		commonUser.setFirstName("Arman");
		commonUser.setLastName("Rajash");
		commonUser.setPrefixName("Mr.");
		commonUser.setStartDate("12/06/2020");
		commonUser.setEndDate("12/06/2021");
		commonUser.setActive("Y");
		commonUser.setLockStatus("N");
		commonUser.setPasswordDate("12/06/2020");
		commonUser.setLocale(Locale.ENGLISH);
		commonUser.setEmail("arman.r@somapait.com");
		commonUser.setFullName("Arman Deva Rajash");
		commonUser.setOrganizationId("1");

		return commonUser;
	}
	
	/**
	 * ค้นหา operator
	 * @param commonUser
	 * @param config
	 * @param siteId
	 * @throws Exception
	 */
	protected void searchMenuAndFunction(CommonUser commonUser) throws Exception {
		String operatorIds = dao.searchOperatorId(conn, commonUser.getUserId());
		
		commonUser.setMapOperator(searchDetailOperatorByOperatorId(operatorIds, Locale.ENGLISH));
		commonUser.setMapOperatorBootstrap(commonUser.getMapOperator());
	}
	
	/**
	 * ค้นหารายละเอียดเมนูและสิทธิ์
	 * @param operatorIds
	 * @param locale
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Operator> searchDetailOperatorByOperatorId(String operatorIds, Locale locale) throws Exception {


		Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();

		try {
			Operator operatorLevel = dao.searchOperatorLevel(conn, operatorIds);
			mapOperator = dao.searchOperator(conn, operatorIds, locale);
			for (String key : mapOperator.keySet()) {
				mapOperator.get(key).setMinLevel(operatorLevel.getMinLevel());
				mapOperator.get(key).setMaxLevel(operatorLevel.getMaxLevel());
				break;
			}
		} catch (Exception e) {
			throw e;
		}

		return mapOperator;
	}
	
}