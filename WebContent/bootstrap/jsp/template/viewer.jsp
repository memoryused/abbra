<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/jquery/jquery-3.2.1.js'/>"></script>
<script type="text/javascript">
	jQuery(document).ready(function(){
		jQuery("#srcFile").val(window.opener.jQuery("#fileNameTmpForViewer").val());
		jQuery("#fileName").val(window.opener.jQuery("#fileNameForViewer").val());
		
		document.forms[0].action = '<s:url value="/DownloadStreamServlet"/>';
		document.forms[0].submit();
	});
</script>
</head>

<body >
<form method="post">
	<input type="hidden" id="srcFile" name="srcFile"/>
	<input type="hidden" id="fileName" name="fileName"/>
</form>
</body>
</html>