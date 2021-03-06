package com.sit.abstracts;

import java.util.List;

import org.apache.log4j.Logger;

import com.sit.common.CommonUser;

import util.database.CCTConnection;


/**
 * @param <C> Criteria
 * @param <R> Search Result
 * @param <T> Main domain
 * @param <U> Common User
 * @param <L> Logger
 */
public abstract class AbstractManager<C, R, T, U> {

	protected CCTConnection conn = null;
	protected CommonUser user = null;
	protected Logger log = null;

	/**
	 * @param conn
	 */
	public AbstractManager(CCTConnection conn, CommonUser user, Logger log) {
		this.conn = conn;
		this.user = user;
		this.log = log;
	}

	/**
	 * @Desc For Search Button
	 * @param conn
	 * @param criteria
	 * @param confirm
	 *            (confirm status for user confirm to show result over max
	 *            limit)
	 * @return
	 * @throws Exception
	 */
	public abstract List<R> search(C criteria) throws Exception;

	/**
	 * @Desc For Load Edit or View Button
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract T searchById(String id) throws Exception;

	/**
	 * @Desc For Add Button
	 * @param conn
	 * @param obj
	 * @param userId
	 * @throws Exception
	 */
	public abstract int add(T obj) throws Exception;

	/**
	 * @Desc For Edit Button
	 * @param conn
	 * @param obj
	 * @throws Exception
	 */
	public abstract int edit(T obj) throws Exception;

	/**
	 * @Desc For Delete Button
	 * @param conn
	 * @param ids
	 *            = 1,2,3,...,N กรณีต้องการลบหลายรายการ ,ids = 1 กรณีต้องการลบ 1
	 *            รายการ
	 * @param userId
	 *            for updateUser field
	 * @throws Exception
	 */
	public abstract int delete(String ids) throws Exception;

	/**
	 * @Desc For Active and Inactive Button
	 * @param conn
	 * @param ids
	 *            = 1,2,3,...,N กรณีต้องการ update หลายรายการ ,ids = 1 รายการ
	 * @param activeFlag
	 *            Y=active,N=inactive
	 * @param userId
	 *            for updateUser field
	 * @throws Exception
	 */
	public abstract int updateActive(String ids, String activeFlag) throws Exception;

}
