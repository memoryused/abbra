package com.sit.app.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.log.LogUtil;
import util.string.StringUtil;

public class URLFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = -146731137907391317L;

	public void init(FilterConfig config) throws ServletException {
		LogUtil.INTERCEPTOR.debug("URLFilter: init");

	}

	@Override
	public void destroy() {
		LogUtil.INTERCEPTOR.debug("URLFilter: destroy");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		servletResponse.setContentType("text/html;charset=UTF-8");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		try {
			String flag = null;
			if (request.getSession(false) != null) {
				flag = (String) request.getSession().getAttribute("flag");
			}
			
			if (request.getServletPath() != null) {
				// LogUtil.FILTER.debug("serrvlet path > " +request.getServletPath() + ":: flag > " +flag);
				if (request.getServletPath().endsWith("html")) {
					if (StringUtil.nullToString(flag).equals("Y")) {
						// pass the request along the filter chain
						filterChain.doFilter(servletRequest, servletResponse);
					} else {
						// RequestDispatcher rd = servletRequest.getServletContext().getRequestDispatcher("/jsp/security/initLogin.action");
						// rd.forward(servletRequest, servletResponse);
						response.sendRedirect("jsp/security/initLogin.action");
					}
				} else {
					// pass the request along the filter chain
					filterChain.doFilter(servletRequest, servletResponse);
				}
			} else {
				response.sendRedirect("jsp/security/initLogin.action");
			}
		} catch (Exception e) {
			LogUtil.INTERCEPTOR.error(e);
		}
		
	}

}
