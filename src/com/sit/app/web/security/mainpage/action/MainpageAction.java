package com.sit.app.web.security.mainpage.action;

import com.sit.app.core.config.parameter.domain.ContextConfig;
import com.sit.app.core.config.parameter.domain.ParameterConfig;
import com.sit.common.CommonAction;
import com.sit.common.CommonUser;
import com.sit.domain.GlobalVariable;

import util.log.LogUtil;
import util.web.SessionUtil;

public class MainpageAction extends CommonAction {

	private static final long serialVersionUID = -7180942456024412378L;

	public MainpageAction() {

	}

	public String init() {

		String result = ReturnType.INIT.getResult();
		try {
			
			String[] ignore = { CommonUser.DEFAULT_SESSION_ATTRIBUTE, GlobalVariable.DEFAULT_PARAMETER_LANGUAGE };
			SessionUtil.removesIgnore(ignore);
			SessionUtil.setAttribute(GlobalVariable.THEME_ACTIVE_BOOTSTRAP, 
					SessionUtil.getContextName() + "/bootstrap/css/theme/" + ParameterConfig.getParameter().getApplication().getThemeBootstrap()
					+ "/bootstrap-theme.css");
			
			SessionUtil.setAttribute(GlobalVariable.THEME_ACTIVE, 
					SessionUtil.getContextName() + "/jquery-ui/css/theme/" + ParameterConfig.getParameter().getApplication().getTheme()
					+ "/jquery-ui.css");

		} catch (Exception e) {
			LogUtil.LOGIN.error("", e);
		} finally {

		}

		return result;
	}


	public ContextConfig getContextConfig(){
		return ParameterConfig.getParameter().getContextConfig();
	}
}
