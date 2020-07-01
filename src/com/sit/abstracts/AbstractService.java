package com.sit.abstracts;

import org.apache.log4j.Logger;

import com.sit.common.CommonUser;

import util.database.CCTConnection;

public abstract class AbstractService {
	protected CCTConnection conn = null;
	protected CommonUser user = null;
	protected Logger log = null;

	/**
	 * @param conn
	 */
	public AbstractService(CCTConnection conn, CommonUser user, Logger log) {
		this.conn = conn;
		this.user = user;
		this.log = log;
	}
}
