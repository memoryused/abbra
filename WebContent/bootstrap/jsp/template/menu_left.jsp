<%@page import="util.web.SessionUtil"%>
<%@page import="util.web.MenuLeftTutorialUtil"%>
<%@page import="com.sit.common.CommonUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//boolean haveUser = false;
String context = request.getContextPath();
String MENU_CODE = request.getParameter("MENU_CODE");
String menuBootstrapUI 		= "";
try {
	if (SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE) != null) {
		CommonUser user 	= (CommonUser)SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE);
		menuBootstrapUI 	= new MenuLeftTutorialUtil(context, user.getMapOperatorBootstrap(), user).genarateMenuBar();
	}
} catch(Exception e) {
	e.printStackTrace();
}
%>
<style type="text/css">

.icon-arrow-down{
	color : #bdbdbd; 
	margin-top: 5px; 
	padding-right: 5px;
}

.menu-left-box-fix{
	height: 100%;
	background-color: #F5F5F5;
	position: fixed;
}

.menu-left-box-shadow{
	box-shadow: 0 3px 5px 2px rgba(0, 0, 0, 0.2);
}
	
</style>
<script type="text/javascript">
var FLAG_TOGGLE_MENU_TOP = false;
jQuery(document).ready(function(){

	//————————————————————————————————————————————————————————————————————————
	//	DRAW MENU
	//————————————————————————————————————————————————————————————————————————
	var MENU_TYPE = jQuery("#MENU_TYPE");

	if (MENU_TYPE.length > 0) {
		if(MENU_TYPE.val() == 1){
			//jQuery("#menu-left").removeClass("invisible");
			jQuery("#menu-left").html(jQuery("#DRAW_MENU_BOOTSTRAP").text());
		}
	}
	//————————————————————————————————————————————————————————————————————————
	//	SELECT MENU CURRENT
	//————————————————————————————————————————————————————————————————————————
	var MENU_CODE = jQuery("input[id='MENU_CODE']");
	if (MENU_CODE.length > 0) {
		jQuery("li[id*='"+ MENU_CODE.val() +"']").addClass("active");
		jQuery("li[id*='"+ MENU_CODE.val() +"']").parent("[class*='menu-level']").collapse('show');
	}
	
	
	
	//————————————————————————————————————————————————————————————————————————
	//	CLICK MENU
	//————————————————————————————————————————————————————————————————————————
	jQuery("#hamburger-one").click(function(e) {		
		if(jQuery("#menu-left").is(".invisible")){
			//กดเปิดเมนู
			FLAG_TOGGLE_MENU_TOP = true;
			
			jQuery(".div-container-menu-left").addClass("menu-left-box-fix menu-left-box-shadow");
			jQuery(".div-container-menu-left").css("z-index", 1020);
			jQuery(".margin-container-left").html('<i class="fa fa-certificate fa-lg orange-text"></i>&nbsp;&nbsp;<s:text name="applicationName"/>');
			jQuery("#menu-left").removeClass("invisible");
			jQuery("#menu-left").show();
		}else{
			//กดปิดเมนู
			FLAG_TOGGLE_MENU_TOP = false;
			jQuery(".div-container-menu-left").removeClass("menu-left-box-fix menu-left-box-shadow");
			jQuery(".div-container-menu-left").css("z-index", 0);
			jQuery(".margin-container-left").html("");
			jQuery("#menu-left").addClass("invisible");
			jQuery("#menu-left").hide();
		}
		
	});
	
	
	
	//————————————————————————————————————————————————————————————————————————
	//	RESIZE WINDOWS
	//————————————————————————————————————————————————————————————————————————
	$(window).resize(function(){
		var colWidth = jQuery("#container-right .col-content").css("width");
		if(typeof colWidth == "undefined"){
			colWidth = 0;
		}
		
		var colWidthw = $( window ).width();
			//jQuery("#disSize").html("w: "+colWidth + " | ww: "+colWidthw);
			
		var colLWidth = jQuery("#container-left").css("width");
		jQuery(".div-container-menu-left").css("z-index", "0");
		if(colWidthw >= 770){
			//jQuery("#menu-left").removeClass("invisible");
			//jQuery(".div-container-menu-left").removeClass("menu-left-box-shadow");
			//jQuery(".div-container-menu-left").addClass("menu-left-box-fix");
			jQuery(".div-container-menu-left").css("width" , colLWidth);
		}else{
			if(FLAG_TOGGLE_MENU_TOP){
				//กดเปิดเมนู
				jQuery("#menu-left").removeClass("invisible");
				jQuery(".div-container-menu-left").addClass("menu-left-box-fix menu-left-box-shadow");
			}else{
				//กดปิดเมนู
				jQuery(".div-container-menu-left").removeClass("menu-left-box-fix menu-left-box-shadow");
				jQuery("#menu-left").addClass("invisible");
			}
			
			jQuery(".div-container-menu-left").css("width" , "250px");
		}
		
	});
	
	
	
	//————————————————————————————————————————————————————————————————————————
	//	INIT MENU LEFT
	//————————————————————————————————————————————————————————————————————————
	var colWidthw = $( window ).width();
	var colLLWidth = jQuery("#container-left").css("width");
	
	var colWidthd = jQuery("#container-right .col-content").css("width");
	if(typeof colWidthd == "undefined"){
		colWidthd = 0;
	}
	//jQuery("#disSize").html("w: "+colWidthd + " | ww: "+colWidthw);
	jQuery(".div-container-menu-left").css("z-index", "0");
	if(colWidthw >= 770){
		//jQuery(".div-container-menu-left").addClass("menu-left-box-fix");
		//jQuery("#menu-left").removeClass("invisible");
		jQuery(".div-container-menu-left").css("width" , colLLWidth);
	}else{
		jQuery("#menu-left").addClass("invisible");
		jQuery(".div-container-menu-left").css("width" , "250px");
	}
	
	
		
});
</script>



<!-- <div class="div-container-menu-left"> -->
<!--———————————————————————————————————————————————————————————————————————
	HIDDEN
———————————————————————————————————————————————————————————————————————-->
	<input type="hidden" id="MENU_TYPE" value="1" />
	<input type="hidden" id="MENU_CODE" name="MENU_CODE" value="<%= MENU_CODE %>"/>
	<textarea rows="" cols="" class="d-none" id="DRAW_MENU_BOOTSTRAP">
		<%= menuBootstrapUI %>
		
	</textarea>

<!--———————————————————————————————————————————————————————————————————————
	DRAW MENU
———————————————————————————————————————————————————————————————————————-->
<!-- 	<div id="menu-left" class="menu-left invisible"> -->
<!-- 	</div> -->
<!-- </div> -->




<div class="div-container-menu-left ">
	<div class="margin-container-left"></div>
	<div id="menu-left" class="menu-left invisible">
			
	</div>
</div>