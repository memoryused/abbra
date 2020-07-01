package com.sit.app.core.security.login.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.abstracts.AbstractManager;
import com.sit.app.core.security.login.domain.LoginModel;
import com.sit.app.core.security.login.domain.User;
import com.sit.common.CommonUser;

import util.database.CCTConnection;

public class LoginManager extends AbstractManager<Object, Object, Object, CommonUser> {

	private LoginService service = null;

	public LoginManager(CCTConnection conn, CommonUser user, Logger log) {
		super(conn, user, log);
		this.service = new LoginService(conn, user, log);
	}

	/**
	 * Process login
	 * @param ip
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public CommonUser login(String ip, LoginModel login) throws Exception {
		log.info("Process login");
		
		CommonUser commonUser = null;
		try {
			//TODO PDP-1 Authentication
			User tempUser = service.authentication(ip, login.getUsername(), login.getPassword());
			
			// Clear สถานะ login ผิด
			service.updateLoginWrong(tempUser.getId());
						
			commonUser = service.copyUser(tempUser, ip);
			
			//หาข้อมูลสิทธิ์
			service.searchMenuAndFunction(commonUser);
			
		} catch (Exception e) {
			throw e;
		}

		return commonUser;
	}

	
	/**
	 * @deprecated ไม่ได้ใช้
	 */
	@Override
	public List<Object> search(Object criteria) throws Exception {
		return null;
	}

	/**
	 * @deprecated ไม่ได้ใช้
	 */
	@Override
	public Object searchById(String id) throws Exception {
		return null;
	}

	/**
	 * @deprecated ไม่ได้ใช้
	 */
	@Override
	public int add(Object obj) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้
	 */
	@Override
	public int edit(Object obj) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้
	 */
	@Override
	public int delete(String ids) throws Exception {
		return 0;
	}

	/**
	 * @deprecated ไม่ได้ใช้
	 */
	@Override
	public int updateActive(String ids, String activeFlag) throws Exception {
		return 0;
	}
}
