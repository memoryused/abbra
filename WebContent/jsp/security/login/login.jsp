<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title><s:text name="applicationName" /></title>
<script type="text/javascript">

	jQuery(document).on("keydown",function(event){
		var keyCode = event.which || event.keyCode;
		
		if (keyCode == 13) {
			login();
		}
	});

	function sf() {	

	}

	function login() {
		if (!validate()) {
			return false;
		}
		submitPage("<s:url value='/jsp/security/checkLogin.action' />");
	}
	
	function forgot(){
		submitPage("<s:url value='/jsp/security/initForgotPassword.action' />");
	}
	
	function validate(){
		return true;
	}
	
</script>
<style>
body {
	background-image: url('${pageContext.request.contextPath}/images/bg/bg_login_<%= (int) (Math.random() * 3) %>.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	margin: 0;
	padding: 0;
	font-family: 'Kanit', sans-serif;
}

.md-form label{
	margin-left: 10px;
	color: rgba(255, 255, 255, 0.7) !important;
}

.md-form label.active{
	color: white !important;
}

.md-form input,
div#footer{
	color: white !important;
}

.card-header{
	color: white !important;
	background-color: lightslategrey;
}
</style>
</head>
<body onload="sf();">

	<!-- Form -->
	<s:form id="loginForm" name="loginForm" action="checkLogin" namespace="/jsp/security" method="POST" cssClass="" >
		<!-- Material form login -->
		<div class="col-md-4" style="min-height: 70vh">
			<div class="border rounded">
				<h5 class="card-header">
					<strong>Sign in</strong>
				</h5>
				
				<s:include value="%{#application.CONTEXT_JS_CSS}/jsp/template/message.jsp"/>	
				
				<!--Card content-->
				<div class="card-body mx-3 mt-0">
					
					<div class="align-items-center justify-content-center">
						<!-- Username -->
						<div class="form-row justify-content-center">
							<div class="col-md-12">
								<div class="md-form">
									<label for="username"><s:text name="sec.login_username" /></label>
									
									<s:textfield id="username" cssClass="form-control requireInput"
										cssStyle="" name="username" autocomplete="off" maxlength="50" />
									<div class="invalid-feedback">
										<s:text name="10002" />
									</div>
								</div>
							</div>
						</div>

						<!-- Password -->
						<div class="form-row justify-content-center">
							<div class="col-md-12">
								<div class="md-form">
									<label for="password"><s:text name="sec.login_password" /></label>
									
									<s:password cssClass="form-control requireInput" cssStyle=""
										name="password" autocomplete="off" maxlength="100"/>
									<div class="invalid-feedback">
										<s:text name="10002" />
									</div>
								</div>
							</div>
						</div>

						<div>&nbsp;</div>
						<!-- Sign in button -->
						<div class="form-row justify-content-center mt-1 mb-1">
							<button id="btnLoginx"
								class="btn-lg btn-default btn-rounded btn-block " type="button"
								onclick="login();">
								<s:text name="sec.sign_in" />
							</button>
						</div>

						<div class="form-row mt-3 justify-content-between">
							<div class="">
								<!-- Forgot password -->
								<a href="javascript:void(0);" onclick="forgot();"
									class="text-primary"><strong><s:text name="sec.forgot_pwd" /></strong></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<!-- Material form login -->
	<p class="h1 mb-4"></p>
		
	</s:form>
	<!-- Form -->
</body>
</html>