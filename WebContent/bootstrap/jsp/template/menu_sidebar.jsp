<%@page import="util.web.SessionUtil"%>
<%@page import="util.web.MenuSideBarUtil"%>
<%@page import="com.sit.common.CommonUser"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
boolean haveUser = false;
String context = request.getContextPath();
String P_CODE = request.getParameter("P_CODE");
String menu = "";
try {
	if (SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE) != null) {
		CommonUser user = (CommonUser)SessionUtil.get(CommonUser.DEFAULT_SESSION_ATTRIBUTE);
		menu = new MenuSideBarUtil(context, user.getMapOperator(), user).genarateMenuBar();
		haveUser = true;
	}
} catch(Exception e) {
	e.printStackTrace();
}
%>
<head>
<style type="text/css">
	.wrapper {
		padding-left: 0;
		-webkit-transition: all 0.5s ease;
		-moz-transition: all 0.5s ease;
		-o-transition: all 0.5s ease;
		transition: all 0.5s ease;
	}
	
	.wrapper.toggled {
		padding-left: 280px;
	}
	
	.sidebar-wrapper {
 	    z-index: 1000; 
	    position: fixed;
	    left: 280px;
	    width: 0;
	    height: 95%;
	    margin-left: -280px;
	    overflow-y: auto;
	    background: #FCFCFA;
	    -webkit-transition: all 0.5s ease;
	    -moz-transition: all 0.5s ease;
	    -o-transition: all 0.5s ease;
	    transition: all 0.5s ease;
	}
	
	.wrapper.toggled .sidebar-wrapper {
	    width: 280px;
	}
	
	.page-content-wrapper {
	    width: 95%;
	    position: absolute;
	    padding: 15px;
	}
	
	.wrapper.toggled .page-content-wrapper {
	    position: absolute;
	    margin-right: -280px;
	}
	
	/* Sidebar Styles */
	
	.sidebar-nav {
	    position: absolute;
	    top: 50px;
	    width: 280px;
	    margin: 0;
	    padding: 0;
	    list-style: none;
	}
	
	.sidebar-nav li {
	    text-indent: 20px; 
	    line-height: 30px;
	}
	
	.sidebar-nav li a {
	    display: block;
	    text-decoration: none;
	}
	
	.sidebar-nav li a:hover {
	    text-decoration: none;
	}
	
	.sidebar-nav li a:active,
	.sidebar-nav li a:focus {
	    text-decoration: none;
	}
	
	.sidebar-nav > .sidebar-brand {
	    height: 65px;
/* 	    font-size: 18px; */
	    line-height: 60px;
	}
	
	.sidebar-nav > .sidebar-brand a {
	    color: #999999;
	}
	
	.sidebar-nav > .sidebar-brand a:hover {
	    color: #fff;
	    background: none;
	}
	
	/* Menu Parent*/
	.sidebar-wrapper a{
		color: #69A9B3 !important;
	}
	.sidebar-wrapper a:hover{
		color: #5FBAC7 !important;
	}
	
	/* Menu Child*/
	.sidebar-wrapper a.dropdown-toggle{
		font-weight: bold;
		color: #33575C !important;
	}
	
	.sidebar-wrapper a.dropdown-toggle:hover{
		color: #5FBAC7 !important;
	}
	
	.menu-level2 li a{
		text-indent: 50px;
	}
	
	.menu-level3 > li > a{
		text-indent: 70px;
	}

	.current_menu > a{
		font-weight: bold;
		color: #1585C2 !important;
		background: #DDE7ED;
	}		
</style>
<script type="text/javascript">

	jQuery(document).ready(function() {
		// กดปุ่มเมนูด้านบน
		jQuery("#menu-toggle").click(function(e) {
			
			jQuery(".sidebar-wrapper").css('top', jQuery(".container").height() + "px");
// 			jQuery(".sidebar-nav").css('top', (jQuery(".userProfile").parent().height() + 10) + "px");
			jQuery(".sidebar-nav").css('top', (jQuery(".userProfile").height() + 44) + "px");
			
	    	e.preventDefault();
	    	
	    	//เปลี่ยนเมนูด้านข้าง จาก Hide -> Show หรือ Show -> Hide 
	    	jQuery(".wrapper").toggleClass("toggled");
	    	
	    	// Hilight Current Menu
	    	var pCode = jQuery("input[name='P_CODE']").length;
	    	if (pCode > 0) {
	    	    
	    		//ใส่สีให้เมนูปัจจุบันที่เปิดอยู่
	    		jQuery("li[id*='" + jQuery("input[name='P_CODE']").val()+ "']").addClass("current_menu");
	    		
	    		//สั่ง expand level แม่
	    		jQuery("li[id*='" + jQuery("input[name='P_CODE']").val()+ "']").parent("[class*='menu-level']").collapse('show');
	    		
	    		//สั่งเปิดทุกๆ Level ที่อยู่ก่อนหน้า
	    		openParentLevel(jQuery("li[id*='" + jQuery("input[name='P_CODE']").val()+ "']").parent("[class*='menu-level']"));
	    		
	    	}
	    	
		});
		
		/*
		jQuery(".sidebar-wrapper").swipeleft(function() {
			jQuery(".wrapper").toggleClass("toggled");
	    });
		*/
		
		//ให้เปิดได้แค่ครั้งละ 1 เมนู
		jQuery("li.menu-level1").click(function(e) {
	    	jQuery("li.menu-level1").not(this).each(function(){
	    		jQuery(jQuery(this).attr("data-target")).collapse('hide');
	    	});
		});
		
	});

	/**
	 * เวลากดส่วนที่ไม่ใช่เมนู ให้เมนูซ่อนไป
	 */
	jQuery(document).mouseup(function (e){
		var container = jQuery(".sidebar-wrapper");
		var btnMenu = jQuery("#menu-toggle");
		var hamburgerIcon = jQuery("#hamburgerIcon");
		if (!btnMenu.is(e.target) && !hamburgerIcon.is(e.target)){
			if (!container.is(e.target) // if the target of the click isn't the container...
					&& container.has(e.target).length === 0){ // ... nor a descendant of the container
				jQuery(".wrapper").removeClass("toggled");
			}
		}else{
		}
	});
	
	/**
	 * Open Menu เพื่อ Hilight Current Menu
	 */
	function openParentLevel(ele){
		if(jQuery(ele).attr("class").indexOf("menu-level1") != -1){
			//ถ้าเป็น Level1 จะสั่ง collapse ที่ <li>
			jQuery(jQuery(ele).attr("data-target")).addClass("show");
			
			//Hide เมนูอื่นที่เคยเปิดทิ้งไว้
			jQuery("li.menu-level1").not(ele).each(function(){
	    		jQuery(jQuery(this).attr("data-target")).removeClass('show');
	    	});
			
		}else{
			//ถ้าเป็น Level อื่น จะสั่ง collapse ที่ <ul>
			jQuery(ele).addClass("show");
		}
		
		//ตรวจสอบว่ามี Level ก่อนหน้าหรือเปล่า ถ้ามีต้องสั่งเปิดให้ครบ
		if(jQuery("[data-target='#" + jQuery(ele).attr("id")).length > 0){
			if(jQuery(ele).attr("class").indexOf("menu-level2") == -1){
				openParentLevel(jQuery("[data-target='#" + jQuery(ele).attr("id")).closest("ul"));
			}else{
				openParentLevel(jQuery("[data-target='#" + jQuery(ele).attr("id")));
			}
		}else{
			
		}
	}

</script>

</head>
<!-- Sidebar -->
<div class='sidebar-wrapper'>
	<%if (haveUser) {
		System.out.println("##################################################################");
	System.out.println(menu);
	%>
		<%=menu%>	
	<%} %>
</div>