<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sit.app.core.config.parameter.domain.ParameterConfig"%>
<%@ page import="com.sit.common.CommonAction"%>

<script type="text/javascript" src="<s:url value='/js/jquery/jquery-1.10.2.min.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/jquery/jquery-ui-1.11.4.custom.min.js' />"></script>

<script type="text/javascript" src="<s:url value='/js/ui/pagination.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/ui/headersorts.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/ui/inputmethod.js' />"></script>

<script type="text/javascript" src="<s:url value='/js/validate/inputvalidate.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/control/control.js' />"></script>

<script type="text/javascript" src="<s:url value='/js/jquery.loadJSON.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/ajax-loader.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/dialog/dialog.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/table/table.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/table/tableForce.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/table/apps.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/submit/submit.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/jquery/jquery-button.js' />"></script>

<script type="text/javascript" src="<s:url value='/js/charactor/checkCharactor.js' />"></script>

<script type="text/javascript" src="<s:url value='/js/datatable/jquery.dataTables.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/datatable/datatables.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/datatable/dataTables.fixedColumns.js' />"></script>
<%--override-display.js วางชั่วคราว เดี๋ยวเจี๊ยบต้องปรับแก้  ให้ใช้ กับ Popup --%>
<script type="text/javascript" src="<s:url value='/jsapps/display1600/override-display.js' />"></script>

<script type="text/javascript">
	var passenger_type = "<s:property value='@com.cct.domain.GlobalVariable@PASSENGER_TYPE' />"; //ค่า type ของ passenger
	var crew_type = "<s:property value='@com.cct.domain.GlobalVariable@CREW_TYPE' />"; //ค่า type ของ crew
	var expected_type = "<s:property value='@com.cct.domain.GlobalVariable@EXPECTED_TYPE' />"; //ค่า type ของ  broding status
	var cancelled_type = "<s:property value='@com.cct.domain.GlobalVariable@CANCELLED_TYPE' />"; //ค่า type ของ broding status
	var notoktoboard_type = "<s:property value='@com.cct.domain.GlobalVariable@NOTOKTOBOARD_TYPE' />"; //ค่า type ของ broding status
	var valueHit = "<s:property value='@com.cct.domain.GlobalVariable@CHKHIT' />"; //สำหรับ compare ค่าที่ติด blacklist stoplist
	var watchlist_type = "<s:property value='@com.cct.domain.GlobalVariable@WATCHLIST' />"; //สำหรับ compare ค่าที่ติด watchlist

	//SCHEDULE TYPE
	var schedule_type = "<s:property value='@com.cct.domain.GlobalVariable@SCHEDULED_TYPE' />"; //สำหรับ compare ค่าที่ติด watchlist

	/*overrideStatus*/
	var new_status = "<s:property value='@com.cct.domain.GlobalVariable@OVERRIDE_STATUS_NEW' />"; //สำหรับ OVERRIDE_STATUS_NEW
	var pending_status = "<s:property value='@com.cct.domain.GlobalVariable@OVERRIDE_STATUS_PENDING' />"; //สำหรับ OVERRIDE_STATUS_PENDING
	var inprogress_status = "<s:property value='@com.cct.domain.GlobalVariable@OVERRIDE_STATUS_INPROGRESS' />"; //สำหรับOVERRIDE_STATUS_INPROGRESS
	var cannot_status = "<s:property value='@com.cct.domain.GlobalVariable@OVERRIDE_STATUS_CANNOT' />"; //สำหรับ OVERRIDE_STATUS_CANNOT
	var overridden_status = "<s:property value='@com.cct.domain.GlobalVariable@OVERRIDE_STATUS_OVERRIDDEN' />"; //สำหรับ  OVERRIDE_STATUS_OVERRIDDEN
	var declined_status = "<s:property value='@com.cct.domain.GlobalVariable@OVERRIDE_STATUS_DECLINED' />"; //สำหรับ  OVERRIDE_STATUS_DECLINED
	
	var risk_stoplist = "<s:property value='@com.cct.domain.GlobalVariable@RISK_STOPLIST' />"; //สำหรับ compare ค่าที่ติด watchlist
	var risk_blacklist = "<s:property value='@com.cct.domain.GlobalVariable@RISK_BLACKLIST' />"; //สำหรับ compare ค่าที่ติด watchlist
	var risk_watchlist = "<s:property value='@com.cct.domain.GlobalVariable@RISK_WATCHLIST' />"; //สำหรับ compare ค่าที่ติด watchlist
	
	
	//Check length Date calendar for spinner
	var maxDay  =  "<s:property value='@com.cct.app.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getMaxDay()'/>";
	var minDay  =  "<s:property value='@com.cct.app.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getMinDay()'/>";
	var maxDayCustom  =  "366";
	
	/* กำหนดค่าการกรอกข้อมูลอย่างน้อย ก่อนค้นหาของ autocomplete*/
	var fillAtLeast  =  "<s:property value='@com.cct.app.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getFillAtLeast()'/>";
	
	/* กำหนดจำนวนของตัวอักษร ที่ใช้ตัดตัวอักษรถ้าหากมีความยาวเกินจำนวนที่กำหนด ใช้ในระบบ monitor profiling */
	var txtLength = "<s:property value='@com.cct.domain.GlobalVariable@SUB_MESSAGE_LENGTH' />";
	
	// APPS2015-1076 สำหรับใช้ในการตรวจสอบ SITE
	var context = "<s:property value='@com.cct.app.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getContext()'/>";


	//สำหรับ config show updateVersion
	var showUpdateV = "<s:property value='@com.cct.app.core.config.parameter.domain.ParameterConfig@getParameter().getShowUpdateversion().getShow()'/>";

	//จำนวนวัน สำหรับตรวจสอบการออกรายงานของ FlightSearch By Direction
	var dayRangeForExport = "<s:property value='@com.cct.domain.GlobalVariable@DAY_RANGE_FOR_EXPORT' />";


	
	

	var surename = '<s:text name="cus.surname" />';
	var name = '<s:text name="cus.name" />';
	var docNo = '<s:text name="cus.docno" />';
	
	var useGlobalTab = true;	
	var useGlobalFocus = true;
	var urlContext = "<s:url value='/images/icon/'/>"; //สำหรับไฟล์ js ดึงไปใช้
	var validateMessage = {
		CODE_10002: '<s:text name="10002"/>',
		CODE_60004: '<s:text name="60004"/>',
		CODE_60007: '<s:text name="60007"/>',
		CODE_60006: '<s:text name="60006"/>',
		CODE_60005: '<s:text name="60005"/>',
		CODE_10033: '<s:text name="10033"/>',
		CODE_10001: '<s:text name="10001"/>',
		CODE_10008: '<s:text name="10008"/>',
		CODE_10034: '<s:text name="10034"/>',
		CODE_50027: '<s:text name="50027"/>',
		CODE_10040: '<s:text name="10040"/>',
		CODE_10039: '<s:text name="10039"/>',
		CODE_30011: '<s:text name="30011"/>',	
		CODE_numberOfSearch: '<s:text name="numberOfSearch"/>',
		CODE_order: '<s:text name="order"/>',
		CODE_10036: '<s:text name="10036"/>',
		CODE_30074: '<s:text name="30074"/>',
		CODE_EDIT: '<s:text name="edit"/>',
		CODE_VIEW: '<s:text name="view"/>',
		CODE_ACTIVE: '<s:text name="active"/>',
		CODE_INACTIVE: '<s:text name="inactive"/>',
		CODE_50041: '<s:text name="50041"/>',
		CODE_50005: '<s:text name="50005"/>',
		CODE_10066: '<s:text name="10066"/>',
		CODE_10068: '<s:text name="10068"/>',
		CODE_10079: '<s:text name="10079"/>',
		CODE_50026: '<s:text name="50026"/>'
	};

	$( document ).ready(function() {
		
		// console.log( "ready!" );
		if (typeof(document.getElementsByName("urlEdit")[0]) == "undefined"){
			$("#reload").hide();
		}
		
		// disable drag and drop on webpage: nanthaporn.p 20150508
		window.addEventListener("dragover",function(e){
		  e = e || event;
		  e.preventDefault();
		},false);
		
		window.addEventListener("drop",function(e){
		  e = e || event;
		  e.preventDefault();
		},false);
		// end disable drag and drop on webpage: nanthaporn.p 20150508
	});

	
	document.onkeydown = function(e) { 
		e = e || window.event;
		var keyCode = e.keyCode || e.which;
		if(keyCode == 112) {
			if(e.ctrlKey) {
				if (typeof F1 == 'function') { 
					F1(); 
				}
			}
		}		
		if(keyCode == 113) {
			if(e.ctrlKey) {
				if (typeof F2 == 'function') { 
					F2(); 
				}
			}
		}
		if(keyCode == 114) {
			if(e.ctrlKey) {
				if (typeof F3 == 'function') { 
					F3(); 
				}
			}
		}
		if(keyCode == 115) {
			if(e.ctrlKey) {
				if (typeof F4 == 'function') { 
					F4(); 
				}
			}
		}
		if(keyCode == 117) {
			if(e.ctrlKey) {
				if (typeof F6 == 'function') { 
					F6(); 
				}
			}
		}
		if(keyCode == 118) {
			if(e.ctrlKey) {
				if (typeof F7 == 'function') { 
					F7(); 
				}
			}
		}
		if(keyCode == 119) {
			if(e.ctrlKey) {
				if (typeof F8 == 'function') { 
					F8(); 
				}
			}
		}
		if(keyCode == 120) {
			if(e.ctrlKey) {
				if (typeof F9 == 'function') { 
					F9(); 
				}
			}
		}
		if(keyCode == 121) {
			if(e.ctrlKey) {
				if (typeof F10 == 'function') { 
					F10(); 
				}
			}
		}
		if(keyCode == 122) {
			if(e.ctrlKey) {
				if (typeof F11 == 'function') { 
					F11(); 
				}
			}
		}
		if(keyCode == 123) {
			if(e.ctrlKey) {
				if (typeof F12 == 'function') { 
					F12(); 
				}
			}
		}
		if(keyCode == 27) {
			if(e.ctrlKey) {
				if (typeof ESC == 'function') { 
					ESC(); 
				}
			}
		}
	}
	
	function profile() {
		submitPage("<s:url value='/jsp/security/initProfileLogin.action' />");
	}
	
	function changeLanguage(language) {
		var inputParameterLanguage = "<s:property value='@com.cct.domain.GlobalVariable@DEFAULT_PARAMETER_LANGUAGE' />";
		if (document.getElementsByName(inputParameterLanguage).length == 0) {
			var textbox = document.createElement('input');
			textbox.type = 'hidden';
			textbox.name = inputParameterLanguage;
			textbox.id = inputParameterLanguage;
			document.forms[0].appendChild(textbox);
		}
		document.getElementsByName(inputParameterLanguage)[0].value = language;
		submitPage("<s:url value='/jsp/security/initLogin.action'/>");
	}

	//*** ตรวจสอบรหัสผ่าน
	function validatePasswordFormat(elementIds, msg) {

		var elArray = elementIds.split(',');
		for (var index in elementIds.split(',')) {
			var el = document.getElementById(trim(elArray[index]));
			if (validatePasswordFormatFormIndex(el, msg) == false) {
				return false;
			}
		}
		return true;
	}

	function validatePasswordFormatFormIndex(el, msg) {
		
		if (msg == undefined) {
			msg = validateMessage.CODE_30074;
		}
		
		var elHtml = el;
		if ((elHtml.disabled) || (trim(elHtml.value) == '')) {
			return true;
		}

		elHtml.className = elHtml.className.replace("requirePasswordFormatSelect", "input_password_format");
		var formatLength = "<s:property value='getPasswordLenght()' />"
		if (trim(elHtml.value).length < parseInt(formatLength)) {
			alert(getMessageResponse(msg));
			elHtml.focus();
			elHtml.className = elHtml.className.replace("input_password_format", "requirePasswordFormatSelect");
			return false;
		}

		var flagActive = "<s:property value='@com.cct.domain.GlobalVariable@FLAG_ACTIVE' />";
		var formatNummeric = "<s:property value='getPasswordFormatNummeric()' />";
		if (formatNummeric == flagActive) {
			var regularExpression = new RegExp("<s:property value='@com.cct.app.core.security.config.domain.ConfigSystem@MAP_OF_PASSWORD_FORMAT_JS[@com.cct.app.core.security.config.domain.ConfigSystem@PASSWORD_FORMAT_NUMMERIC_KEY]' />");
			if (checkPasswordFormat(elHtml, msg, regularExpression) == false) {
				return false;
			}
		}


		var formatAlphabeticLower = "<s:property value='getPasswordFormatAlphabeticLower()' />";
		if (formatAlphabeticLower == flagActive) {
			var regularExpression = new RegExp("<s:property value='@com.cct.app.core.security.config.domain.ConfigSystem@MAP_OF_PASSWORD_FORMAT_JS[@com.cct.app.core.security.config.domain.ConfigSystem@PASSWORD_FORMAT_ALPHABETIC_LOWER_KEY]' />");
			if (checkPasswordFormat(elHtml, msg, regularExpression) == false) {
				return false;
			}
		}


		var formatAlphabeticUpper = "<s:property value='getPasswordFormatAlphabeticUpper()' />";
		if (formatAlphabeticUpper == flagActive) {
			var regularExpression = new RegExp("<s:property value='@com.cct.app.core.security.config.domain.ConfigSystem@MAP_OF_PASSWORD_FORMAT_JS[@com.cct.app.core.security.config.domain.ConfigSystem@PASSWORD_FORMAT_ALPHABETIC_UPPER_KEY]' />");
			if (checkPasswordFormat(elHtml, msg, regularExpression) == false) {
				return false;
			}
		}


		var formatSpecial = "<s:property value='getPasswordFormatSpecial()' />";
		if (formatSpecial == flagActive) {
			var invalid = true;
			var specialString = "<s:property value='@com.cct.app.core.security.config.domain.ConfigSystem@MAP_OF_PASSWORD_FORMAT_JS[@com.cct.app.core.security.config.domain.ConfigSystem@PASSWORD_FORMAT_SPECIAL_KEY]' escapeHtml='false' />";
			for(var i = 0; i < specialString.length; i++){
				if(elHtml.value.indexOf(specialString.substring(i, i + 1)) > -1){
					invalid = false;
				}
			}

			if (invalid) {
				alert(getMessageResponse(msg));
				elHtml.focus();
				elHtml.className = elHtml.className.replace("input_password_format", "requirePasswordFormatSelect");
				return false;
			}
		}

		return true;
	}

	/** ตรวจสอบ รูปแบบรหัสผ่าน **/
	function checkPasswordFormat(elHtml, msg, regularExpression){
		elHtml.className = elHtml.className.replace("requirePasswordFormatSelect", "input_password_format");
		var invalid = regularExpression.test(trim(elHtml.value));
		if (invalid == false) {
			alert(getMessageResponse(msg));
			elHtml.focus();
			elHtml.className = elHtml.className.replace("input_password_format", "requirePasswordFormatSelect");
			return false;
		}
		return invalid;
	}

	/** กำหนดขนาดของ LoaderStatus 
		- ความยาว/ความกว้าง
		- ตำแหน่งของรูปภาพ ที่ต้องอยู่ตรงกลางของหน้าจอ  
	**/
	function showLoaderStatus(){

		// Create the overlay
		var overlay = jQuery('<div></div>').css({
				'background-color': '#fff',
				'opacity':0.7,
				'width':document.body.scrollWidth,
				'height':document.body.scrollHeight,
				'position':'absolute',
				'top':'0px',
				'left':'0px',
				'z-index':99999
		}).addClass('ajax_overlay');

		jQuery("body").append(overlay.append(
				jQuery('<div></div>').addClass('ajax_loader')
			).fadeIn(200)
		);
	}
	
	function hideLoaderStatus(){
		jQuery("div.ajax_overlay").remove();
    }

    function reloadEditPage(){
		if (typeof(document.getElementsByName("urlEdit")[0]) != "undefined"){
			var url =document.getElementsByName("urlEdit")[0].value ;
			submitPage(url);
		}

	}  

    /**
    * initialWidget
    */
	function initialWidget(){
		// สร้าง selectmenu 
		jQuery("select:visible").not(".ui-datepicker-month").not(".ui-datepicker-year").selectmenu();

		jQuery('span.ui-selectmenu-button').each(function(){
			if(jQuery(this).prev().hasClass("requireInput")){
				jQuery(this).addClass('requireInput');
			}
		});
		
		// Clear all accordion state except current page if "Search" or "Print" page
		var page = jQuery(document).find("input[name $= 'page']").val();
		if (page == "SEARCH" || page == "search" || page == "PRINT" || page == "print" || page == undefined) {
			initAccordionState();
		}
	}
</script>
<style>
	/* กำหนดความสูงให้ selectmenu */
	.ui-selectmenu-menu .ui-menu {max-height: 150px;}
</style>