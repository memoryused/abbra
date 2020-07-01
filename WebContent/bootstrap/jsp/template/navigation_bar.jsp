<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sit.common.CommonAction"%>
<%@ page import="com.sit.common.CommonUser"%>
<%@ page import="com.sit.domain.GlobalVariable"%>
<%@ page import="com.sit.domain.Operator"%>
<%@ page import="util.web.SessionUtil"%>
<%@ page import="util.web.MenuUtil"%>
<%@ page import="com.opensymphony.xwork2.ActionContext "%>

<%
	String htmlHeader = "";
	boolean haveUser = false;
	String haveMenu = "true";
	//String F_CODE = request.getParameter("F_CODE");
	String F_CODE = (String) ActionContext.getContext().getValueStack().findValue("F_CODE");
	try {
		if (SessionUtil.getAttribute("useMenu") != null) {
			haveMenu = (String) SessionUtil.getAttribute("useMenu");
		}
		
		CommonUser user = new CommonAction(true).getUser();
		if (user != null) {
			haveUser = true;
			htmlHeader = MenuUtil.searchLabel(user.getMapOperator(), F_CODE);
		}
	} catch (Exception ex) {
		//ex.printStackTrace();
	}
%>
<style type="text/css">
.disSize{
	z-index: 7777;
	position: absolute;  
	color: red; 
	font-size: 10px; 
	margin-top: 40px; 
	color: white;
	margin-left: 50px;
}

@media (max-width: 768px) {
	.disSize{
		margin-top: 30px; 
	}
}



/***************** HAMBURGER 1, 2 & 3 STYLES  ********************/
.menuTrigger {
    display: inline-block;
    width: 25px;
    height: 17px;
    position: relative;
    cursor: pointer;
    margin: 0 3%;
    vertical-align: middle;
}

.mainLine {
    position: absolute;
    left: 0;
    top: 50%;
    width: 100%;
    height: 3px;
    margin-top: -5px;
    background: #fff;
    transition: all linear 0.3s;
    -webkit-transition: all linear 0.3s;
    -moz-transition: all linear 0.3s;
    -ms-transition: all linear 0.3s;
}

.mainLine:after, .mainLine:before {
    content: "";
    position: absolute;
    left: 0;
    display: block;
    width: 100%;
    height: 3px;
    background: #fff;
    -webkit-transform: rotate(0);
    -moz-transform: rotate(0);
    -ms-transform: rotate(0);
    transform: rotate(0);
    transition: all linear 0.3s;
    -webkit-transition: all linear 0.3s;
    -moz-transition: all linear 0.3s;
    -ms-transition: all linear 0.3s;
}

.mainLine:before {
    top: -7px;
}

.mainLine:after {
    top: 7px;
}

/****** Hover Effects ******/
#hamburger-one:hover .mainLine:before {
    top: -8px;
}

#hamburger-one:hover .mainLine:after {
    top: 8px;
}

/******  Click Effects ******/
/* Hamburger One */
#hamburger-one.menuToggle .mainLine {
    width: 25%;
	height: 5px;
	top: 50;
    -webkit-transition: all 0.3s linear ; 
    -moz-transition: all 0.3s linear ; 
    -ms-transition: all 0.3s linear ; 
    transition: all 0.3s linear ; 
}

#hamburger-one.menuToggle .mainLine:before {
    visibility: visible;
	height: 5px;
}

#hamburger-one.menuToggle .mainLine:after {
    visibility: visible;
    top: 100 !important;
	height: 5px;
}


@media only screen and (max-width: 480px) {
  .menuTrigger {margin:0 1%;}
}
</style>

<script>
    $(document).ready(function(){           
    
    // Navicon Menu Toggle Class    
    $('.menuTrigger').on("click",function(){    
        $(this).toggleClass('menuToggle')
    });

});
</script>
<nav id="navbarTop" class="navbar navbar-collapse navbar-toggleable-sm navbar-inverse fixed-top bg-tutorial">

	<!-- MENU SIDEBAR -->
	<button class="navbar-toggler navbar-toggler-right cursor-pointer" type="button" data-toggle="collapse" data-target="#navbarTogglerHome" aria-controls="navbarTogglerHome" aria-expanded="false" aria-label="Toggle navigation">
		<i class="fa fa-cog fa-fw" aria-hidden="true"></i>
	</button>


	<!-- ICON MENU LEFT -->
<%-- 	<a class="navbar-brand hidden-md-up" id="menu-toggle" href="javascript:void(0);"><i id="hamburgerIcon" class="fa fa-bars fa-lg"></i><s:text name="applicationName" /></a>
	<span class="navbar-brand hidden-sm-down"><s:text name="applicationName" /></span> --%>
	
	<span class="navbar-brand" id="menu-toggle" style="margin-left: 40px; font-weight: bold;">
		<%=htmlHeader %>
	</span>
	<span class="navbar-brand menuTrigger" id="hamburger-one" style="margin-top: 5px; margin-left: 0px; position: absolute;">
	    <span class="mainLine"></span>
	</span>
	
	<!-- ICON MENU RIGHT -->
	<div id="navbarTogglerHome">
		<ul class="navbar-nav mr-auto mt-2 mt-md-0">
		</ul>
		<ul class="navbar-nav my-2 my-lg-0">
			<s:include value="/jsp/template/profile_menu.jsp"></s:include>
			<li class="nav-item"><a class="nav-link" href="javascript:void(0);"><i class="fa fa-question-circle-o fa-fw" aria-hidden="true"></i>&nbsp;Help</a></li>
			<li class="nav-item"><a class="nav-link" href='<s:url value="/jsp/security/logoutLogin.action" />'><i class="fa fa-sign-out fa-fw" aria-hidden="true"></i>&nbsp;Logout</a></li>
			
		</ul>
	</div>
    
    
    <!-- DISPLAY WIDTH WINDOW & COLUMN TEST -->
    <div id="disSize" class="disSize"></div>
    
	
</nav>

