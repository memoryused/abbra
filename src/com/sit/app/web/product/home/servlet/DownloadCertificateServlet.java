package com.sit.app.web.product.home.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.product.home.service.ProductHomeManager;
import com.sit.common.CommonUser;

import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;
import util.referrer.ReferrerUtil;
import util.string.StringUtil;
import util.web.SessionUtil;

@WebServlet("/DownloadCertificateServlet")
public class DownloadCertificateServlet extends HttpServlet {

	private static final long serialVersionUID = 4447678041469885516L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		prepare(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		prepare(req, resp);
	}

	private void prepare(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		LogUtil.GET_IMAGE.info("getFile..." + SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE));

		CommonUser sessionUser = (CommonUser) SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE);
		if (sessionUser != null) {
			ServletOutputStream output = null;
			FileInputStream in = null;
			boolean isFindImage = false;
			
			resp.setContentType("application/octet-stream");
			
			try {

				String reqRefId = req.getParameter("refId");
				String refId = ReferrerUtil.convertReferrerToId(sessionUser.getUserName(), SessionUtil.getId(), reqRefId);

				output = resp.getOutputStream();

				if (StringUtil.stringToNull(refId) != null) {
					Map<String, Object> dataMap = null;

					CCTConnection conn = null;
					try {
						conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());
						
						dataMap = new ProductHomeManager(conn, sessionUser, LogUtil.GET_IMAGE).getFile(refId);
						
						if (dataMap != null && ((byte[])dataMap.get("FILE")).length != 0) {
							LogUtil.GET_IMAGE.debug(dataMap.get("FILENAME"));
							
							resp.setCharacterEncoding("UTF-8");
							resp.setHeader("Content-Disposition", "inline;filename="+ URLEncoder.encode((String)dataMap.get("FILENAME"), "UTF-8"));
							isFindImage = true;
							output.write((byte[])dataMap.get("FILE"));
						}
					} catch (Exception e) {
						LogUtil.GET_IMAGE.error("",e);
					} finally {
						CCTConnectionUtil.close(conn);
					}
				}

				if (!isFindImage) {
					File file = new File(ParameterConfig.getParameter().getAttachFile().getImageDefault() + "no_image_resize.jpg");
					in = new FileInputStream(file);
					byte[] data = new byte[(int) file.length()];
					in.read(data);
					output.write(data);
				}
			} catch (Exception e) {
				LogUtil.GET_IMAGE.error("",e);
			} finally {
				try {
					if (in != null){
						in.close();
					}
					
					if (output != null){
						output.close();
					}
				} catch (Exception e) {
					LogUtil.GET_IMAGE.error("",e);
				}
			}
		} else {
			// กรณีไม่มี user login ให้ redirect ไปยังหน้า login
			try {
				resp.sendRedirect(req.getServletContext().getContextPath() + "/jsp/security/initLogin.action");
			} catch (IOException e) {
				LogUtil.GET_IMAGE.error("",e);
			}
		}
	}
}
