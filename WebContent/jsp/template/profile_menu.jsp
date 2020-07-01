<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sit.app.core.config.parameter.domain.ParameterConfig"%>
<%@ page import="com.sit.common.CommonAction"%>
<%@ page import="com.sit.common.CommonUser"%>
<%@ page import="util.bundle.BundleUtil"%>
<%@ page import="util.web.SessionUtil"%>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="java.util.Locale"%>
<script type="text/javascript" src="<s:url value='/js/jquery/jquery-timer.js' />"></script>
<%
boolean haveProfile = false;
String context = request.getContextPath();
Locale locale = new CommonAction(true).getLocale();
String email = "";
String username = "";
String fullname = "";
String orgy = "";
boolean useExitMenu = false;
int timeoutSession = 0;
String changLoguser = "";

try {
	CommonUser user = new CommonAction(true).getUser();
	if (user != null) {
		email = user.getEmail() == null ? "" : user.getEmail();
		username = user.getUserName();
		fullname = user.getFullName();
		locale = user.getLocale();
		haveProfile = true;
	}
} catch(Exception e) {
	//e.printStackTrace();
}

timeoutSession = SessionUtil.getTimeout();

%>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
   <script type="text/javascript">  
	var timerConfig =  <%=timeoutSession%>;
	
    jQuery( document ).ready(function() { 
    	
	    if(jQuery("input[name='page']").val() == "ADD" || jQuery("input[name='page']").val() == "EDIT"){
	    	startTimer();
	    }
    });
    
    function startTimer(){
	    //data-minutes-left(แบบหน่วยนาที) && data-minutes-left(แบบหน่วยนาที)
	    //data-seconds-left(แบบหน่วยวินาที) && data-seconds-left(แบบหน่วยวินาที)
    	jQuery('.timerConfigS').attr('data-seconds-left', timerConfig);
	    	
    	jQuery('.timerConfigS').startTimer({
   			onComplete: function(element){
   				//เมื่อหมดเวลาจะดีดออกไปหน้า login
   				cheksession();
			  }
    	});
    	
    }
    
    //ไหหน้า login
    function logout(){
    	window.location.href = "<s:url value='/jsp/security/logoutLogin.action' />";
    }
    
    function cheksession(){
    	jQuery.ajax({
				
	        type: "POST",
	        url : "<s:url value='/processCheckSession.action' />"+"?functioncode=000",
	        dataType : 'json',
	        async: false,
	        global: false,
	        success: function(data){
	        	
	        	if(data <= 0){
	        		//เมื่อหมดเวลาจะดีดออกไปหน้า login
	        		logout();
	        	}else{
	        		//เวลา session ที่เหลือ
	        		timerConfig = data;
		        	jQuery('.timerConfigS').remove();
		        	jQuery("#timerDiv").after("<div  class='timerConfigS' ></div>");
		        	
		        	//Start Timer ใหม่
		        	startTimer();
	        	}
	        } 
	    });
    }
	
    </script>
    
    <style>
	  .jst-hours , .jst-minutes , .jst-seconds{
	    display: inline;
	    line-height: 20px;
	  }
	</style>
	
</head>
<%if (haveProfile) { %>
<s:if test="page.getPage() == 'add' || page.getPage() == 'edit'">
	<div style="color: rgba(255, 255, 255, 0.75); padding-left: 5px; padding-right: 5px; text-align: center; width: 85px; float: right; border-right: 1px solid silver; text-align: center; height:100%;">
		<div id="timerDiv"><s:text name='timeOut'/></div>
		<div class="timerConfigS" ></div>
	</div>
</s:if>
<s:if test="useProfileMenu">
	<li class="nav-item"><a class="nav-link" href="javascript:void(0);" data-toggle="modal" data-target="#fullHeightModalRight"><i class="fa fa-address-card-o fa-fw" aria-hidden="true"></i>&nbsp;<%=username%></a>
		
		<div class="modal right" id="fullHeightModalRight" data-modal-header='Profile'>
			<div class="modal-dialog modal-side modal-top-right">
				<div class="modal-body">
				
					<div id="divProfile">
						<div class="row">
							<div class="col-std-1" style="text-align: right;">name :</div>	
							<div class="col-std" style="color: blue;"><%=fullname %></div>
						</div>
						<div class="row">
							<div class="col-std-1" style="text-align: right;">e-mail :</div>	
							<div class="col-std" style="color: blue;"><%=email %></div>
						</div>
					</div>
					
				</div>
				<div class="modal-footer">
				
				</div>
			</div>
		</div>
	</li>
</s:if>
<s:if test="page.getPage() == 'search'">
<%-- 	<li class="nav-item"><a class="nav-link" href="javascript:void(0);" onclick="addPage();"><i class="fa fa-plus fa-fw" aria-hidden="true"></i>&nbsp;<s:text name="add" /></a></li> --%>
</s:if>
<s:if test="page.getPage() == 'edit'">
<%-- 	<li class="nav-item"><a class="nav-link" href="javascript:void(0);" onclick="reloadEditPage();"><i class="fa fa-refresh fa-fw" aria-hidden="true"></i>&nbsp;<s:text name="reload" /></a></li> --%>
</s:if>
<% }%>
 
