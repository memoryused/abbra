<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="Cache-control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0" />		
		
		<title><s:text name="applicationName"/></title>
		<s:set var="CONTEXT_JS_CSS" value="%{'/bootstrap'}" scope="application"/>
		<s:include value="/bootstrap/jsp/template/javascript.jsp"/>
		<s:include value="/bootstrap/jsp/template/css.jsp"/>
		
<%-- 		<link href="<s:url value='/css/default-not-ie.css' />" rel="stylesheet" type="text/css"/> --%>
		<decorator:head/>
		
	</head>
	<body onload="sf();" class="margin-zero" style="overflow: hidden;">
		<div id="banner" class="font-kanit">
			<s:include value="/jsp/template/banner.jsp"/>
		</div>
		<div id="content" class="font-kanit">
			<decorator:body/>
		</div>
			
		<div id="footer" class="font-kanit d-flex align-items-end" style="height:15vh;">
			<s:include value="/jsp/template/footer.jsp"/>
		</div>
	</body>
</html>