package com.sit.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONUtil;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;
import util.web.SessionUtil;

import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.config.parameter.domain.ParameterConfig;

public class CommonSelectItemServlet extends HttpServlet {

	private static final long serialVersionUID = -2018123156904737038L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initSelectItem(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initSelectItem(request, response);
	}

	/**
	 * init parameter ก่อน search
	 * 
	 * @param request
	 * @param response
	 */
	private void initSelectItem(HttpServletRequest request, HttpServletResponse response) {
		CCTConnection conn = null;
		String jsonString = "";
		try {

			Locale locale = null;
			CommonUser commonUser = getUser();
			if (commonUser == null) {
				locale = ParameterConfig.getParameter().getApplication().getApplicationLocale();
			} else {
				locale = commonUser.getLocale();
			}

			String term = "";
			if (SessionUtil.requestParameter("term") != null) {
				term = SessionUtil.requestParameter("term");
			}

			String limit = "";
			if (SessionUtil.requestParameter("limit") != null) {
				limit = SessionUtil.requestParameter("limit");
			}

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			List<CommonSelectItem> listSelectItem = searchSelectItem(conn, locale, commonUser, term, limit);
			if (listSelectItem.size() == 0) {
				listSelectItem = searchSelectItem(conn, ParameterConfig.getParameter().getApplication().getApplicationLocale(), commonUser, term, limit);
			}

			jsonString = JSONUtil.serialize(listSelectItem, null, null, false, true);
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out.print(jsonString);

		} catch (Exception e) {
			LogUtil.UTIL.error("", e);
		} finally {
			CCTConnectionUtil.close(conn);
		}
	}

	/**
	 *
	 * @param conn
	 * @param locale
	 * @param commonUser
	 * @param term
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<CommonSelectItem> searchSelectItem(CCTConnection conn, Locale locale, CommonUser commonUser, String term, String limit) throws Exception {

		List<CommonSelectItem> listSelectItem = new ArrayList<CommonSelectItem>();

		return listSelectItem;
	}
	
	/**
	 * ดึง user จาก session
	 *
	 * @return
	 */
	public CommonUser getUser() {
		CommonUser commonUser = null;
		try {
			Object common = SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE);
			if (common != null) {
				if (common instanceof String) {
					commonUser = new CommonUser();
					commonUser.setUserName(common.toString());
				} else if (common instanceof CommonUser) {
					commonUser = (CommonUser) common;
				}
			} 
		} catch (Exception e) {
			
		}
		return commonUser;
	}
}
