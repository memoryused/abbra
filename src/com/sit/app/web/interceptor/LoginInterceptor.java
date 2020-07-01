package com.sit.app.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sit.common.CommonUser;

import util.log.LogUtil;
import util.web.SessionUtil;

public class LoginInterceptor implements Interceptor {

	private static final long serialVersionUID = -1921944000166163942L;

	@Override
	public void destroy() {
		LogUtil.INTERCEPTOR.debug("");
	}

	@Override
	public void init() {
		LogUtil.INTERCEPTOR.debug("");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			
			HttpServletRequest request = ServletActionContext.getRequest();
			LogUtil.INTERCEPTOR.debug(request.getRemoteUser());
			
			if (SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE) != null) {
				
			}else if (request.getRemoteUser() != null) {
				CommonUser commonUser = new CommonUser();
				commonUser.setUserName(request.getRemoteUser());
				commonUser.setEmail(request.getRemoteUser() + "@mail.com");
				ServletActionContext.getRequest().getSession().setAttribute(CommonUser.DEFAULT_SESSION_ATTRIBUTE, commonUser);
			}
		} catch (Exception e) {
			LogUtil.INTERCEPTOR.error("",e);
		}
		return invocation.invoke();
	}

}
