<%@page import="com.sit.domain.GlobalVariable"%>
<%@page import="com.sit.app.core.config.parameter.domain.ParameterConfig"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<% 
	response.sendRedirect("jsp/security/initLogin.action");
%>
</body>
</html>