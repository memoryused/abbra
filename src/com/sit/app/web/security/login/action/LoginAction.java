package com.sit.app.web.security.login.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.sit.app.core.config.parameter.domain.DBLookup;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.app.core.security.login.domain.LoginModel;
import com.sit.app.core.security.login.service.LoginManager;
import com.sit.common.CommonAction;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;
import com.sit.exception.AuthenticateException;

import util.APPSUtil;
import util.database.CCTConnection;
import util.database.CCTConnectionProvider;
import util.database.CCTConnectionUtil;
import util.log.LogUtil;
import util.web.SessionUtil;

public class LoginAction extends CommonAction implements ModelDriven<LoginModel> {

	private static final long serialVersionUID = 4557027877706302838L;

	private LoginModel model = new LoginModel();

	public LoginAction() {
		loggerInititial().info("userId: " + getUserIdFromSession() + ", sessionId: "+ SessionUtil.getId());
		
		// set style ให้ button
		SessionUtil.setAttribute("themeActive", SessionUtil.getContextName() + "/css/theme/" + ParameterConfig.getParameter().getApplication().getTheme() + "/jquery-ui-1.10.4.custom.css");
	}

	public String init() {
		return ReturnType.INIT.getResult();
	}

	/**
	 * สำหรับการเปลี่ยนภาษาหน้าเลือก site
	 * @return
	 */
	public String initLanguage() {
		return ReturnType.INIT.getResult();
	}

	public String check() {
		loggerInititial().info("userId: " + getUserIdFromSession() + ", sessionId: "+ SessionUtil.getId());

		String result = ReturnType.INIT.getResult();
		CCTConnection conn = null;
		try {
			//กรณีที่เข้า url ตรง โดยตรวจสอบว่า หาก username หรือ password เป็นค่าว่าง หรือ null ให้ทำการแจ้ง msg ข้อมูลไม่เพียงพอ พร้อมกลับไปที่ init login
			if(model.getUsername() == null || model.getPassword() == null
				|| model.getPassword().isEmpty() || model.getUsername().isEmpty()) {
					throw new AuthenticateException(GlobalVariable.MessageCode.REQUIRED_DATA.getValue());
			}

			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			LoginManager manager = new LoginManager(conn, null, loggerInititial());
			CommonUser commonUser = manager.login(APPSUtil.getIPAddress(), getModel());
			
			//ลบ object ทุกอย่างออกจาก session ยกเว้น language
			SessionUtil.removesIgnore(GlobalVariable.DEFAULT_PARAMETER_LANGUAGE);

			int timeOut = 30;	//FIXME
			SessionUtil.setTimeout(timeOut * 60);
			
			SessionUtil.put(CommonUser.DEFAULT_SESSION_ATTRIBUTE, commonUser);
			SessionUtil.setAttribute(GlobalVariable.MENU_TYPE, 1);
			
//			LogManager logManager = new LogManager(conn, loggerInititial());
//			logManager.addEvent(conn, "10100690", getUserIdFromSession(), model ,getUser().getStationInfo().getSiteId());

			result = ReturnType.MAINPAGE.getResult();

		} catch (AuthenticateException ae) {
			loggerInititial().error("", ae);
			addActionMessage(getText(ae.getMessage()));
		} catch (Exception e) {
			loggerInititial().error("", e);
			setMessage(CommonAction.MessageType.ERROR, GlobalVariable.Message.SERVER_ERROR.getValue(), getErrorMessage(e), ResultType.BASIC);

		} finally {
			loggerInititial().info("userId: " + getUserIdFromSession() + ", sessionId: "+ SessionUtil.getId());
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	public void getComboForAddEdit(CCTConnection conn) {

	}

	public String logout() {
		loggerInititial().info("userId: " + getUserIdFromSession() + ", sessionId: "+ SessionUtil.getId());
		String result = ReturnType.INIT.getResult();
		CCTConnection conn = null;

		try {
			conn = new CCTConnectionProvider().getConnection(conn, DBLookup.ORA_OC.getLookup());

			// บันทึก LOG_EVENT ก่อน Logout
//			LogManager logManager = new LogManager(conn, loggerInititial());
//			logManager.addEvent(conn, "10101190", getUserIdFromSession(), model, siteId);
			
//			LoginManager manager = new LoginManager(conn, getUser(), getLocale());
//			manager.logoutLog(SessionUtil.getId(), getUser().getUserName(), siteId, getUser().getStationInfo().getIpAddress());

			SessionUtil.removesIgnore(GlobalVariable.DEFAULT_PARAMETER_LANGUAGE);

		} catch (Exception e) {
			loggerInititial().error("", e);

		} finally {
			CCTConnectionUtil.close(conn);
		}

		return result;
	}

	@Override
	public LoginModel getModel() {
		return model;
	}

	@Override
	protected Logger loggerInititial() {
		return LogUtil.LOGIN;
	}

}
