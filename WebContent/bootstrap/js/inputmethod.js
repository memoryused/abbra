
// onclick="checkboxToggle('criteria.selectedIds', this.checked);
function checkboxToggle(elName, checked){
	//	Arunya.k
	//	เพื่อรองรับกรณีส่งมาเป็น element
	if (typeof elName === 'object') {
		var el = elName;
	}else{
		var el = document.getElementsByName(elName);
	}
	
	
	var j = el.length - 1;
	for (var i = 0; i < el.length; i++) {
		if (i > j) {
			break;
		}

		if (el[i].className  == 'flatStyle') {
			el[i].className  == '';
		}
		
		if (el[j].className  == 'flatStyle') {
			el[j].className  == '';
		}
		
		if(!el[i].disabled){
			el[i].checked = checked;
		}
		
		if(!el[j].disabled){
			el[j].checked = checked;
		}

		if (el[i].className  == 'flatStyle') {
			el[i].className  == styleCheckbox;
		}
		if (el[j].className  == 'flatStyle') {
			el[j].className  == styleCheckbox;
		}
		
		j--;
	}
}

//function checkboxToggleDataTable(elName, elSelectAll){
//	// console.info(jQuery(elSelectAll));
//	// console.info(jQuery(elSelectAll).parent().parent().parent().parent().parent().parent().parent());
//	var tmpSelectAll = undefined;
//	for (var i = 1; i <= 8; i++) {
//		if (tmpSelectAll == undefined) {
//			tmpSelectAll = jQuery(elSelectAll);
//		} else {
//			tmpSelectAll = jQuery(tmpSelectAll).parent();
//			if (tmpSelectAll == undefined) {
//				break;
//			}
//		}
//	}
//	
//	// console.info(jQuery(tmpSelectAll));
//	if (tmpSelectAll == undefined) {
//		checkboxToggle(elName, elSelectAll.checked);
//	} else if (jQuery(tmpSelectAll).hasClass("DTFC_LeftWrapper")) {
//		jQuery(tmpSelectAll).children(".DTFC_LeftBodyWrapper").children(".DTFC_LeftBodyLiner").children(".DTFC_Cloned").children("tbody").children("tr").each(function() {
//			// console.info(jQuery(this).children("td").children("[name='" + elName + "']"));
//			var checkboxSelector = jQuery(this).children("td").children("[name='" + elName + "']");
//			if (jQuery(checkboxSelector).attr('disabled') == undefined) {
//				document.getElementById(jQuery(checkboxSelector).attr("id")).checked = elSelectAll.checked; 
//			}
//		});
//	} else {
//		checkboxToggle(elName, elSelectAll.checked);
//	}
//}

/**
 * ใช้ submit ไปยัง action Report ที่ต้องการ โดย default ที่ form 0
 * @param action คือ acion url
 */
//function submitPageReport(action) {
//	submitPageReportFromIndex(0, action);
//}

/**
 * ใช้ submit ไปยัง action ที่ต้องการ โดยสามารถระบุ form ที่ต้องการได้
 * @param formIndex คือ index ของ form ที่ต้องการ submit
 * @param action คือ acion url
 * ตัด showLoaderStatus ออก  Report ไม่ return หน้า
 */
//function submitPageReportFromIndex(formIndex, action) {
//	document.forms[formIndex].action = action;
//	document.forms[formIndex].submit();
//	
//}


/**
 * ใช้ submit ไปยัง action ที่ต้องการ โดย default ที่ form 0
 * @param action คือ acion url
 */
//function submitPage(action) {
//	submitPageFromIndex(0, action);
//}

/**
 * ใช้ submit ไปยัง action ที่ต้องการ โดยสามารถระบุ form ที่ต้องการได้
 * @param formIndex คือ index ของ form ที่ต้องการ submit
 * @param action คือ acion url
 */
//function submitPageFromIndex(formIndex, action) {
//	document.forms[formIndex].action = action;
//	//แสดง Ajax load status เพื่อทำการ lock หน้าจอ
//	showLoaderStatus();
//	document.forms[formIndex].submit();
//}

/**
 * สำหรับแปลงชื่อ html tag ให้เป็น id โดยเปลื่ยนจากจุดเป็นขีดล่างแทน เช่น province.id >> province_id
 * ถ้าไม่มีจุดจะได้เป็นชื่อเดิม เช่น provinceId >> provinceId
 * @param input
 * @returns {String}
 */
//function convertNameToIdWithDelimiterDot(input) {
//	var outputTemp = "";
//	var arrayTemp = input.split(".");
//	if (arrayTemp.length == 0) {
//		outputTemp = input;
//	} else {
//		for (var index = 0; index < arrayTemp.length; index++) {
//			outputTemp += arrayTemp[index] + "_";
//		}
//		if (outputTemp.length > 0) {
//			outputTemp = outputTemp.substring(0, outputTemp.length - 1);
//		}
//	}
//	return outputTemp;
//}

/**
 * ใช้ disable ข้อมูลพร้อมใส่ css และ clear value
 * @param elId คือ id ของ element ทุกตัวที่ต้องการ disable (id1, id2, .. , idn)
 */
function disableInput(elId){
	var elArray = elId.split(',');
	for (var index in elId.split(',')) {
		disableOneInput(trim(elArray[index]));
	}
}

/**
 * ใช้ disable ข้อมูลพร้อมใส่ css และ clear value
 * @param elId คือ id ของ element ที่ต้องการ disable
 */
function disableOneInput(elId){
	clearRequireSelect(elId);
//	jQuery("#" + elId).prop('disabled',true).addClass('readonly').val("");
	jQuery("#" + elId).prop('disabled',true).val("");
}

/**
 * ใช้ enable ข้อมูลพร้อมใส่ css และ clear value
 * @param elId คือ id ของ element ทุกตัวที่ต้องการ enable (id1, id2, .. , idn)
 */
function enableInput(elId){
	var elArray = elId.split(',');
	for (var index = 0; index < elArray.length; index++) {
		enableOneInput(trim(elArray[index]));
		if (index == 0) {
			jQuery("#" + trim(elArray[index])).focus();
		}
	}
}

/**
 * ใช้ enable ข้อมูลพร้อมใส่ css และ clear value
 * @param elId คือ id ของ element ที่ต้องการ enable
 */
function enableOneInput(elId){
	jQuery("#" + elId).prop('disabled',false).removeClass('readonly').val("");
}

//function clearAllRequireSelect(elId) {
//	var elArray = elId.split(',');
//	for (var index = 0; index < elArray.length; index++) {
//		clearRequireSelect(trim(elArray[index]));
//	}
//}

function clearRequireSelect(elId){
	document.getElementById(elId).className = document.getElementById(elId).className.replace("requireSelect", "requireInput");
	document.getElementById(elId).className = document.getElementById(elId).className.replace("requireEmailSelect", "input_email");
	document.getElementById(elId).className = document.getElementById(elId).className.replace("requireIDCardSelect", "input_idcard");
	document.getElementById(elId).className = document.getElementById(elId).className.replace("requirePasswordFormatSelect", "input_password_format");
}

//function setAllRequireSelect(elId) {
//	var elArray = elId.split(',');
//	for (var index = 0; index < elArray.length; index++) {
//		setRequireSelect(trim(elArray[index]));
//		if (index == 0) {
//			jQuery("#" + trim(elArray[index])).focus();
//		}
//	}
//}

//function setRequireSelect(elId){
//	document.getElementById(elId).className = document.getElementById(elId).className.replace("requireInput", "requireSelect");
//	document.getElementById(elId).className = document.getElementById(elId).className.replace("input_email", "requireEmailSelect");
//	document.getElementById(elId).className = document.getElementById(elId).className.replace("input_idcard", "requireIDCardSelect");
//	document.getElementById(elId).className = document.getElementById(elId).className.replace("input_password_format", "requirePasswordFormatSelect");
//}


function showMessageResponse(el) {
	var html = el.innerHTML;
	html = getMessageResponse(html);
	if (html.length > 0) {
		alert(html);
	}
}

function showErrorDetail(elName) {
	var html = document.getElementById(elName).innerHTML;
	html = getMessageResponse(html);
	if (html.length > 0) {
		alert(html);
	}
}

function getMessageResponse(text) {
	text = text.replace(/<br>|<BR>/g, '\n');
	text = text.replace(/^\s+|\s+$|\t|&nbsp;/g, '');
	return text;
}

/**
 * ทำการ focus ไปยัง elementId ที่ต้องการ
 */
//function doTabFocus(elementNextId, elementBackId, event, isNext){
//	var keynum = (event.which || event.keyCode);
//
//	if (isNext && !event.shiftKey && keynum == 9){ //tab
//		document.getElementById(elementNextId).focus();// for firefox 10 focus
//		//setTimeout('doument.getElementById("'+elementNextId+'").focus();',100);
//		return false;
//	}
//	else if(!isNext && (event.shiftKey && keynum==9)){ //shift + tab
//		document.getElementById(elementBackId).focus();// for firefox 10 focus
//		///setTimeout('document.getElementById("'+elementBackId+'").focus();',100);
//		return false;
//	}else if ((elementNextId.value == elementBackId.value ) && ((event.shiftKey && keynum==9) && isNext)){
//		document.getElementById(elementBackId).focus();
//		return false;
//	}
//	return true;
//}

/** Check browser IE,Firefox */
//function checkBrowser() {
//	var mybrowser = navigator.userAgent;
//	mybs = 'ไม่สามารถเช็ค browser ได้';
//	if (mybrowser.indexOf('MSIE') > 0) {
//		mybs = "IE";
//	}
//	if (mybrowser.indexOf('Firefox') > 0) {
//		mybs = "Firefox";
//	}
//	if (mybrowser.indexOf('Presto') > 0) {
//		mybs = "Opera";
//	}
//	if (mybrowser.indexOf('Chrome') > 0) {
//		mybs = "Chrome";
//	}
//	return mybs;
//}

//function getTextFromCombo(elId) {
//	return jQuery("#" + elId + " option:selected").text();
//}


//function haveEmptyPostParameters(paramsObject) {
//	var isEmpty = false;
//	for (var key in paramsObject) {
//		if (paramsObject[key] == "") {
//			isEmpty = true;
//			break;
//		}
//	}
//	return isEmpty;
//}


//function checkDataShowMessageError(data ,print){
//	console.log("[ checkDataShowMessageError ]");
//	if(data.messageAjax.messageType != null){
//		jQuery(".circleGraphic").css("display","none");
//		
//		if(data.messageAjax.messageType == "C"){
//			if(confirm(data.messageAjax.message)){
//				jQuery("[name='criteria.checkMaxExceed']").val(false);
//				jQuery("#flagprocess").val(true);
//				
//				setTimeout(function(){	
//					print();
//				},500);
//				
//			} 
//	
//		}else if(data.messageAjax.messageType == "W"){
//			var msg = '<td class="MESSAGE WARING" style="width: 40%; text-align: right;"><span class="ui-icon ui-icon-info" style="float: right; margin-right: .3em;"></td>';
//			msg += '<td class="MESSAGE WARING" style="width: 60%; text-align: left;">' +  data.messageAjax.message + '</td>';
//	
//			jQuery("#tblMessage").addClass('ui-state-highlight ui-corner-all');
//			jQuery('.messFed #tblMessage > tbody > tr').html(msg);
//			jQuery('.messFed').animate({opacity: 1} ,2500 ,"" ,function(){
//				jQuery(this).animate({opacity: 0} ,900 ,"",function(){
//					jQuery('.messFed #tblMessage > tbody > tr').html('<td class="MESSAGE">&nbsp;</td>');
//				});
//			});
//			
//		}else if(data.messageAjax.messageType == "A"){
//			alert(data.messageAjax.message);
//			
//		}else if(data.messageAjax.messageType == "S"){
//			var msg = '<td class="MESSAGE SUCCESS" style="width: 40%; text-align: right;"><span class="ui-icon ui-icon-circle-check" style="float: right; margin-right: .3em;"></td>';
//			msg += '<td class="MESSAGE SUCCESS" style="width: 60%; text-align: left;">' +  data.messageAjax.message + '</td>';
//
//			jQuery("#tblMessage").addClass('ui-state-highlight ui-corner-all');
//			jQuery('.messFed #tblMessage > tbody > tr').html(msg);
//			jQuery('.messFed').animate({opacity: 1} ,2500 ,"" ,function(){
//				jQuery(this).animate({opacity: 0} ,900 ,"",function(){
//					jQuery('.messFed #tblMessage > tbody > tr').html('<td class="MESSAGE">&nbsp;</td>');
//				});
//			});
//			
////		}else (data.messageAjax.messageType == "E"){
//		}else {
//			jQuery("#tblMessage").addClass('ui-state-error ui-corner-all');
//			jQuery('.messFed .MESSAGE').addClass("ERROR").html(
//					"<span class='ui-icon ui-icon-alert' style='margin-right: .6em;'></span>"
//					+ data.messageAjax.message
//					+ "&nbsp;&nbsp;<a class='LINK' href='javascript:void(0);' onclick='showErrorDetail(\"messageDetail\")'>detail</a>"
//					+ "<div id='messageDetail' style='display: none;'>"+data.messageAjax.messageDetail+"</div>"
//					);
//			
//		}
//	}
//}

//function checkDataShowMessageErrorThrown(errorThrown){
//	console.log("[ checkDataShowMessageErrorThrown ]");
//	jQuery(".circleGraphic").css("display","none");
//
//	jQuery("#tblMessage").addClass('ui-state-error ui-corner-all');
//	jQuery('.messFed .MESSAGE').addClass("ERROR").html(
//			"<span class='ui-icon ui-icon-alert' style='margin-right: .6em;'></span>"
//			+ errorThrown
//			+ "&nbsp;&nbsp;<a class='LINK' href='javascript:void(0);' onclick='showErrorDetail(\"messageDetail\")'>detail</a>"
//			+ "<div id='messageDetail' style='display: none;'>"+errorThrown+"</div>"
//			);
//	
//}

//function clearMessageError() {
//	
//	jQuery(".circleGraphic").css("display","none");
//
//	// console.log(jQuery("div.messFed table#tblMessage"));
//
//	jQuery("div.messFed table#tblMessage").removeClass("ui-state-error ui-corner-all").addClass("FORM");
//	jQuery("div.messFed table#tblMessage tbody tr td.ERROR").remove();
//
//	jQuery("div.messFed table#tblMessage tbody tr").html("<td class='MESSAGE'>&nbsp;</td>");
//}

function clearNotifyMessage() {
	// clear message เก่าออก
	jQuery("div.notify > a.notify-close-btn").each(function() {
		this.click();
		// jQuery("div.notify > a.notify-close-btn").click();
	});
}

function showNotifyMessageAlert(myMessage) {
	showNotifyMessage("alert", "Alert", myMessage);
}

function showNotifyMessageSuccess(myMessage) {
	showNotifyMessage("success", "Success", myMessage);
}

function showNotifyMessageWarning(myMessage) {
	showNotifyMessage("warning", "Warning", myMessage);
}

function showNotifyMessageInfo(myMessage) {
	showNotifyMessage("info", "Info", myMessage);
}

function showNotifyMessageError(myMessage, myError) {
	showNotifyMessage("error", "ERROR", myMessage, myError);
}

function showNotifyMessageValidate(myMessage) {
	showNotifyMessage("validate", "VALIDATE", myMessage);
}

function showNotifyMessage(myType, myTitle, myMessage, myError) {

	clearNotifyMessage();
	
	var myTitle = "";
	var myTemplate = '<div class="notify"><div class="notify-text"></div></div>';
	var mySize = "full";
	var myPosition = {
        x: "center",
        y: "top"
    }
	var myAutoHide = true;
	if (myType == 'error') {
		myTemplate =	'<div class="notify">';
		myTemplate +=		'<div class="notify-text">';
		myTemplate +=			'<h4 class="alert-icon"><span class="fa fa-times-circle" aria-hidden="true"></span></h4>&nbsp;&nbsp;&nbsp;';
		myTemplate +=				 myMessage + '&nbsp;&nbsp;';
		myTemplate +=			'<a class="LINK" href="javascript:void(0);" onclick=\'showErrorDetail("messageDetail")\'>&nbsp;detail&nbsp;</a>';
		myTemplate +=			'<div id="messageDetail" style="display: none;">';
		myTemplate +=				myError;
		myTemplate +=			'</div>';
		myTemplate +=	 	'</div>';
		myTemplate +=	'</div>';
		myMessage = "";
		myAutoHide = false;
		
	
	} else if (myType == 'validate') {
		myTitle = "Insufficient data"
		myTemplate =	'<div class="notify">';
		myTemplate +=		'<div class="notify-text" style="text-align: left; color: #cc0000; font-size: 18px; font-weight: normal;">';
		myTemplate += 			generateNotifyValidateMessage(myTitle, myMessage);
		myTemplate +=	 	'</div>';
		myTemplate +=	'</div>';
		myType = "alert";
		myMessage = "";
		myPosition = {
				x: "center",
		        y: "center"
		}
		mySize = "normal";
		myAutoHide = false;
	} else {
		myTemplate =	'<div class="notify">';
		myTemplate +=		'<div class="notify-text">';

		if(myType == 'success'){
			myTemplate +=	'<h4 class="alert-icon"><span class="fa fa-check-circle" aria-hidden="true"></span></h4>&nbsp;&nbsp;&nbsp;';
		}else if(myType == 'info'){
			myTemplate +=	'<h4 class="alert-icon"><i class="fa fa-info-circle" aria-hidden="true"></i></h4>&nbsp;&nbsp;&nbsp;';
		}else if(myType == 'warning'){
			myTemplate +=	'<h4 class="alert-icon"><i class="fa fa-exclamation-triangle" aria-hidden="true"></i></h4>&nbsp;&nbsp;&nbsp;';
		}
		
		myTemplate +=			 myMessage;
		myTemplate +=	 	'</div>';
		myTemplate +=	'</div>';
		myMessage = "";
	}
	
	notify({
        type: myType, //alert | success | error | warning | info
        // title: myTitle + '&nbsp;&rsaquo;&rsaquo;&nbsp;',
        message: myMessage,
        position: myPosition,
        //position: {
        //    x: "center", //right | left | center
        //    y: "top" //top | bottom | center
        //},
        size: mySize, //normal | full | small
        overlay: false, //true | false
        closeBtn: true, //true | false
        overflowHide: false, //true | false
        spacing: 0, //number px
        theme: "default", //default | dark-theme
        autoHide: myAutoHide, //true | false
        delay: 5000, //number ms
        onShow: null, //function
        onClick: null, //function
        onHide: null, //function
        template: myTemplate
    });
}


function generateNotifyValidateMessage(myTitle, myMessage) {
	var generateMessage = "<h4>" + myTitle + ":</h4>";
	generateMessage += "<ul>";
	var myMessageArray = myMessage.split("<br/>");
	// console.info(myMessageArray);
	for (var indexMsg = 0; indexMsg < myMessageArray.length - 1; indexMsg++) {
		// console.info(myMessageArray[indexMsg]);
		generateMessage += "<li>" + myMessageArray[indexMsg] + "</li>";
	}
	generateMessage += "</ul>";
	return generateMessage;
}


