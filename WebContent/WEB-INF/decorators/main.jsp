<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<!-- Global site tag (gtag.js) - Google Analytics -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=UA-110962165-1"></script>
		<script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());
		
		  gtag('config', 'UA-110962165-1');
		</script>
		
		<title><s:text name="applicationName"/></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="Cache-control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0" />
		
		<!-- Required meta tags -->
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    
		<s:set var="CONTEXT_JS_CSS" value="%{'/bootstrap'}" scope="application"/>
		<link rel="stylesheet" type="text/css" href="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/prism/okaidia/css/prism.css'/>">
		<s:include value="%{#application.CONTEXT_JS_CSS}/jsp/template/css.jsp"/>
		<s:include value="%{#application.CONTEXT_JS_CSS}/jsp/template/javascript.jsp"/>
		
		<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/project-javascript-first.js'/> "></script>
		
		<decorator:head/>
	</head>
	<body onload="sf();" class="margin-zero">
		<s:include value="%{#application.CONTEXT_JS_CSS}/jsp/template/navigation_bar.jsp"/>	
		<div id="themeColor" class="bg-tutorial invisible"></div>
			
			
		<div class="wrapper">
			<!-- Page Content -->
			<div class="page-content">
				<div class="container-fluid">
					<div class="row">
						<div id="container-left" class="col-md-3 pt-2 font-kanit pl-0">
							<s:include value="%{#application.CONTEXT_JS_CSS}/jsp/template/menu_left.jsp"/>
						</div>
						<div id="container-right" class="col-12 col-md-9 col-lg-12 pt-2 font-kanit" >
							<div class="margin-container-right"></div>
							<decorator:body/>
							<s:include value="%{#application.CONTEXT_JS_CSS}/jsp/template/message.jsp"/>	
							<br><br><br><br><br><br><br><br><br><br>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
		<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/javascript-lasted.js'/> "></script>
		<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/prism/okaidia/js/prism.js'/>"></script>
	</body>
</html>