/**
# ---------------------------------------------------------------------------------
# 							button-sit-0.0.1.js
# ---------------------------------------------------------------------------------
#
# Update Date	: 31/07/2017
#
# ---------------------------------------------------------------------------------
*/

+function($) {
	'use button';
	
	var pageSearch = "search";
	var pageAdd = "add";
	var pageEdit = "edit";
	var pageView = "view";
	var pageReport = "report";
	var pagePrint = "print";
	var pageSearchDialog = "search_dialog";
	var pageChooseDialog = "choose_dialog";
	
	
	var SitButton = function(element, options) {

		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id');
		this.auth = options.auth;
		this.funcSearch = options.funcSearch;
		this.funcClearFormCallBack = options.funcClearFormCallBack;
		this.buttonType = options.buttonType;
		this.funcValidSearch = options.funcValidSearch;
		this.divCriteriaId = options.divCriteriaId;
		this.divTableResultId = options.divTableResultId;
		
		
		this.messageConfirmAdd = options.messageConfirmAdd;
		this.funcSaveAdd = options.funcSaveAdd;
		this.funcValidAdd = options.funcValidAdd;
		this.messageCancelAdd = options.messageCancelAdd;
		this.funcCancelAdd = options.funcCancelAdd;
		
		this.messageConfirmEdit = options.messageConfirmEdit;
		this.funcSaveEdit = options.funcSaveEdit;
		this.funcValidEdit = options.funcValidEdit;
		this.messageCancelEdit = options.messageCancelEdit;
		this.funcCancelEdit = options.funcCancelEdit;
		
		this.messageCancelView = options.messageCancelView;
		this.funcCancelView = options.funcCancelView;
		
		this.funcPrint = options.funcPrint;
		this.funcCancelPrint = options.funcCancelPrint;
		this.funcValidPrint = options.funcValidPrint;
		
		this.funcSearchDialog = options.funcSearchDialog;
		this.funcValidSearchDialog = options.funcValidSearchDialog;
		this.funcClearDialog = options.funcClearDialog;
		this.funcCloseDialog = options.funcCloseDialog;
		this.divCriteriaIdDialog = options.divCriteriaIdDialog;
		this.funcClearFormCallBackDialog = options.funcClearFormCallBackDialog;
		this.divTableResultIdDialog = options.divTableResultIdDialog;
		
		this.funcChooseDialog = options.funcChooseDialog;
	}
	
	
	SitButton.VERSION = '0.0.1'
	SitButton.DEFAULTS = {
		funcSearch : "searchPage" ,
		funcClearFormCallBack : "clearFormCallBack",
		funcValidSearch : "checkValidSearch",
		funcSaveAdd : "saveAdd",
		funcValidAdd : "checkValidAdd",
		funcCancelAdd : "cancelAdd",
		funcSaveEdit : "saveEdit",  
		funcCancelEdit : "cancelEdit",
		funcValidEdit : "checkValidEdit",
		funcCancelView : "cancelView",
		funcPrint : "print",
		funcCancelPrint : "cancelPrint",
		funcValidPrint : "checkValidPrint",
		funcSearchDialog : "searchDialog",
		funcValidSearchDialog : "checkValidSearchDialog",
		funcClearDialog : "clearDialog",
		funcCloseDialog : "closeDialog",
		funcChooseDialog : "chooseDialog",
		funcClearFormCallBackDialog : "clearFormCallBackDialog"
	}
	
	SitButton.prototype.create = function() {
		this.createSitButtonBox();
	}
	
	SitButton.prototype.createSitButtonBox = function() {	
		var messageButton = {
			msgConfirmSaveAdd : validateMessage.CODE_50003,
			msgConfirmSaveEdit : validateMessage.CODE_50004,
			msgCancelAdd : validateMessage.CODE_50006,
			msgCancelEdit : validateMessage.CODE_50007,
			msgCancelView : validateMessage.CODE_50010,
			msgCancelDialog : validateMessage.CODE_50012,
				
			msgSearch : validateMessage.CODE_SEARCH,
			msgClear : validateMessage.CODE_CLEAR,
			msgSave : validateMessage.CODE_SAVE,
			msgCancel : validateMessage.CODE_CANCEL,
			msgEdit : validateMessage.CODE_EDIT,
			msgClose : validateMessage.CODE_CLOSE,
			msgPrint : validateMessage.CODE_PRINT,
			msgOk : validateMessage.CODE_OK	
		}		
		
		
		if (typeof(this.messageConfirmAdd) == "undefined" || !this.messageConfirmAdd){
			this.messageConfirmAdd = messageButton.msgConfirmSaveAdd;
		}
		if (typeof(this.messageCancelAdd) == "undefined" || !this.messageCancelAdd){
			this.messageCancelAdd = messageButton.msgCancelAdd;
		}
		if (typeof(this.messageConfirmEdit) == "undefined" || !this.messageConfirmEdit){
			this.messageConfirmEdit = messageButton.msgConfirmSaveEdit;
		}
		if (typeof(this.messageCancelEdit) == "undefined" || !this.messageCancelEdit){
			this.messageCancelEdit = messageButton.msgCancelEdit;
		}
		if (typeof(this.messageCancelView) == "undefined" || !this.messageCancelView){
			this.messageCancelView = messageButton.msgCancelView;
		}
		
		if (typeof(this.divCriteriaId) == "undefined" || !this.divCriteriaId){
			this.divCriteriaId = "";
		}
		
		if (typeof(this.divCriteriaIdDialog) == "undefined" || !this.divCriteriaIdDialog){
			this.divCriteriaIdDialog = "";
		}
		
		if (typeof(this.objClearId) == "undefined" || !this.objClearId){
			this.objClearId = "";
		}
		
		var tableHead = "<div class='row'><div class='div-btn col-12 col-xl-11 text-right'>";
		var tableFooter = "</div></div>";

		
		
		
		if(this.buttonType == pageSearch){
			if(this.auth=="true"){
				$(this.$element).html(
					tableHead +
					"<button id=\"btnSearch\" class=\"btn btn-fixsize  btn-default wave-effect font-weight-bold\" onclick=\"if(!onSearch('"+this.divCriteriaId+"','"+this.funcValidSearch+"')){return false;}; return "+this.funcSearch+"();\"> " +
					"<i class='fa fa-search' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgSearch +
					"</button> "+
					"<button id=\"btnRefresh\" class=\"btn btn-fixsize btn-outline-secondary wave-effect font-weight-bold\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"','"+this.divCriteriaId+"','"+this.divTableResultId+"' );\"> "+
					"<i class='fa fa-eraser' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button> " +				
					tableFooter
				);
			}else{
				$(this.$element).html(
					tableHead +
					"<button id=\"btnSearch\"  class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> "+
					"<i class='fa fa-search' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgSearch+
					"</button> "+
					"<button id=\"btnRefresh\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"','"+this.divCriteriaId+"');\"> "+
					"<i class='fa fa-eraser' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button> " +				
					tableFooter
				);
			}
		}
		if(this.buttonType == pageAdd){
			if(this.auth=="true"){
				$(this.$element).html(
					tableHead +
					"<button id=\"btnAdd\" class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"if(!onAdd('"+this.messageConfirmAdd+"','"+this.funcValidAdd+"')){return false;}; return "+this.funcSaveAdd+"();\"> " +
					"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgSave +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"if(!onCancelAdd('"+this.messageCancelAdd+"')){return false;}; return "+this.funcCancelAdd+"();\" > " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel +
					"</button> " +	
					tableFooter
				);
			}else{
				$(this.$element).html(
					tableHead +		
					"<button id=\"btnAdd\" class=\"btn btn-fixsize btn-default font-weight-bold\"onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgSave +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"if(!onCancelAdd('"+this.messageCancelAdd+"')){return false;}; return "+this.funcCancelAdd+"();\" > " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel +
					"</button> " +
					tableFooter
				);
			}			
		}
		if(this.buttonType == pageEdit){
			if(this.auth=="true"){
				$(this.$element).html(
					tableHead +				
					"<button id=\"btnEdit\" class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"if(!onEdit('"+this.messageConfirmEdit+"','"+this.funcValidEdit+"')){return false;}; return "+this.funcSaveEdit+"();\"> " +
					"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgEdit + 
					"</button> " +
					"<button id=\"btnCancel\"  class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\"  onclick=\"if(!onCancelEdit('"+this.messageCancelEdit+"')){return false;}; return "+this.funcCancelEdit+"();\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel + 
					"</button> " +
					tableFooter
				);
			}else{
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnEdit\"  class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgEdit + 
					"</button> " +
					"<button id=\"btnCancel\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\"  onclick=\"return cancelEdit('"+this.messageCancelEdit+"');\" > " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel + 
					"</button> " +				
					tableFooter
				);
			}			
		}
		if(this.buttonType == pageView){
			$(this.$element).html(
				tableHead +
				"<button id=\"btnCancel\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"if(!onCancelView('"+this.messageCancelView+"')){return false;}; return "+this.funcCancelView+"();\" > " +
				"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
				messageButton.msgClose +
				"</button> " +
				tableFooter
			);			
		}	
		
		if(this.buttonType == pageReport){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +					
					"<button id=\"btnPrint\" class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"if(!onReport()){return false;}; return "+this.funcPrint+"();\"> " +
					"<i class='fa fa-print' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnClear\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"');\"> " +
					"<i class='fa fa-refresh' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button> " +
					tableFooter
				);				
			}else{				
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnPrint\"  class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-print' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnClear\"  class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return clearForm('"+this.funcClearFormCallBack+"');\" > " +
					"<i class='fa fa-refresh' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button> " +	
					tableFooter
				);
			}				
		}
		
		if(this.buttonType == pagePrint){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +
					"<button id=\"btnPrint\" class=\"btn btn-fixsize btn-default font-weight-bold\"  onclick=\"if(!onPrint()){return false;}; return "+this.funcPrint+"();\"> " +
					"<i class='fa fa-print' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return "+this.funcCancelPrint+"();\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel +
					"</button> " +				
					tableFooter
				);
			}else{				
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnPrint\" class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-print' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgPrint +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return "+this.funcCancelPrint+"();\" > " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel +
					"</button> " +
					tableFooter
				);	
			}				
		}
		
		if(this.buttonType == pageSearchDialog){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +					
					"<button id=\"btnSearch\" class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"if(!onSearchDialog('"+this.divCriteriaIdDialog+"','"+this.funcValidSearchDialog+"')){return false;}; return "+this.funcSearchDialog+"();\"> " +
					"<i class='fa fa-search' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgSearch +
					"</button> " +
					"<button id=\"btnRefresh\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return clearForm('"+this.funcClearFormCallBackDialog+"','"+this.divCriteriaIdDialog+"','"+this.divTableResultIdDialog+"');\"> " +
					"<i class='fa fa-refresh' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button> " +
					"<button id=\"btnCancel\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return "+this.funcCloseDialog+"();\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClose +
					"</button> " +
					tableFooter
				);
				
			}else{					
				$(this.$element).html(
					tableHead +	
					"<button id=\"btnSearch\"  class=\"btn btn-fixsize btn-default font-weight-bold\" disabled=\"disabled\" > " +
					"<i class='fa fa-search' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgSearch +
					"</button> " +
					"<button id=\"btnRefresh\" class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\" onclick=\"return clearForm('"+this.funcClearFormCallBackDialog+"','"+this.divCriteriaIdDialog+"','"+this.divTableResultIdDialog+"');\"> " +
					"<i class='fa fa-refresh' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button> " +
					"<button id=\"btnCancel\"  class=\"btn btn-fixsize btn-outline-secondary font-weight-bold\"  onclick=\"return "+this.funcCancelDialog+"();\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClose + 
					"</button> " +
					tableFooter
				);
			}				
		}
		if(this.buttonType == pageChooseDialog){
			if(this.auth=="true"){	
				$(this.$element).html(
					tableHead +					
					"<button id=\"btnOk\"  class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"return "+this.funcChooseDialog+"();\"> " +
					"<i class='fa fa-check' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgOk +
					"</button> " +
					tableFooter
				);
			}else{					
				$(this.$element).html(
					tableHead +
					"<button id=\"btnOk\"  class=\"btn btn-fixsize btn-default font-weight-bold\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-check' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgOk +
					"</button> " +
					tableFooter
				);
			}				
		}		
	}
	
	
	
	
	
	// PLUGIN DEFINITION
	// =======================
	

	function Plugin(option, _relatedTarget) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data('sitButton')
			var options = $.extend({}, SitButton.DEFAULTS, $this.data(),
					typeof option == 'object' && option)
					
			if (!data) {
				$this.data('sitButton', (data = new SitButton(this, options)));
				data.create(_relatedTarget);
			}
			
			
			if (typeof option == 'string') {
				data[option](_relatedTarget);
			}
		})
	}
	

	
	var old = $.fn.sitButton

	$.fn.sitButton = Plugin
	$.fn.sitButton.Constructor = SitButton

}(jQuery);


/**
* สำหรับ print หน้าจอในรูปแบบ browser
*/
function printBrowser() {
	/*
	if (typeof document.getElementsByName("printId")[0] != "undefined") {
		var operationId = document.getElementsByName("printId")[0].value;
	    $.ajax({
	        type: 'post',
	        url: contextPath +'/jsp/log/printLog.action',
	        data: {
	          'operationId': operationId,
	        },
	        success: function (response) {

	        },
	        error: function () {

	        }
	    });
	}
	*/
	window.print();
    return false;
}


/**
* สำหรับ clear ค่าของหน้าจอ [ค้นหา, เพิ่ม, ลงทะเบียน]
*/
function clearForm(clearFormCallBack, divCriteria, divTableResult) {
	
	clearMessage();
	
	var objClear;
	if (typeof(divCriteria) == "undefined" || !divCriteria){
		objClear = jQuery(".clearform");
	}else{
		objClear = jQuery("#"+divCriteria).find(".clearform");
	}
	
	jQuery("#"+divTableResult).hide();
	
	var objId = "";
	jQuery(objClear).each(function(index) {
		objId = jQuery(this).attr("id");
		jQuery(this).val("");
		
		//CLEAR SELECT BOOTSTRAP
		if(jQuery(this).is("select:visible")){
			jQuery("#"+objId).sitSelectpicker("dropdownlistValue", "");
		}
		if(jQuery(this).hasClass("lpp-style")){
			jQuery("#"+objId).sitSelectpicker("dropdownlistValue", jQuery("#"+objId+" option:first").val());
		}
		
		//CLEAR BOOTSTRAP RADIO CHECKBOX & UNCHECK
		if(jQuery(this).attr("type") == "checkbox" || jQuery(this).attr("type") == "radio"){
			jQuery(this).prop("checked", false);
		}
		
	});

	// กรณีที่มีการกำหนดค่า default หรือ กำหนดค่าอื่นๆในแต่ละหน้าจอเอง
	// จะทำการตรวจสอบว่าแต่ละหน้าจอมี function clearFormCallBack()?
	// กรณีมี function การทำงานจะทำการเรียกใช้งาน function clearFormCallBack() ของหน้าจอนั้นๆ
	//if(typeof "'+clearFormCallBack+'" == 'function'){
		//clearFormCallBack();
	//}
	
	var fn = window[clearFormCallBack]; 
	if(typeof fn === 'function') {
	    fn();
	}
}


function onSearch(divid,checkValidSearch) {
	if(validateFormInputAll()){
		var criteriaKey;
		if (typeof(divid) == "undefined" || !divid){
			criteriaKey = $("input[name='criteria.criteriaKey']");
		}else{
			criteriaKey = $("#"+divid+" input[name='criteria.criteriaKey']")
		}		
		var fn = window[checkValidSearch]; 
		if(typeof fn === 'function') {
			if(fn()){
				criteriaKey.val("");
				return true;
			}else{
				return false;
			}
		}
		criteriaKey.val("");
		return true;
	}else{
		return false;
	}
}



//msgConfirm : ข้อความที่แสดงสำหรับยืนยันการ add
//checkValidAdd : function สำหรับการตรวจสอบ validate Add
function onAdd(msgConfirm,checkValidAdd){
	if(validateFormInputAll()){
		var fn = window[checkValidAdd]; 
		if(typeof fn == 'function'){
			if(fn() == false){
				return false;
			}
		}
		if (confirm(msgConfirm) == false) {
			return false;
		} else{
			return true;
		}
	}
}

function onEdit(msgConfirm,checkValid) {
	if(validateFormInputAll()){
		var fn = window[checkValid];
		if(typeof fn == 'function'){
			if(fn() == false){
				return false;
			}
		}
		if (confirm(msgConfirm) == false){;
			return false;
		} else{
			return true;
		}
	}

}

function onSearchDialog(divid,chkValidSearchDialog) {
	if(validateFormInputAll()){
		var fn = window[chkValidSearchDialog]; 
		if(typeof fn === 'function') {
			if(fn()){
				$("#"+divid+" input[name='criteria.criteriaKey']").val("");
				return true;
			}else{
				return false;
			}
		}
		$("#"+divid+" input[name='criteria.criteriaKey']").val("");
		return true;
	}else{
		return false;
	}	
	
	
}

function onValidSearch(){
	return false;
}


function onCancelView(msgCancelView) {
	if(confirm(msgCancelView) == false) {	
		return false;
	}else{
		return true;
	}
}

function onCancelAdd(msgCancelAdd){
	if(confirm(msgCancelAdd) == false) {	
		return false;
	}else{
		return true;
	}
}


function onCancelEdit(msgCancelEdit){
	if(confirm(msgCancelEdit) == false) {	
		return false;
	}else{
		return true;
	}
}

function onCancelDialog(){
	if(confirm(messageButton.msgCancelDialog) == false) {	
		return false;
	}else{
		return true;
	}	
}

function onReport() {
	if(validateFormInputAll()){
		return true;
	}else{
		return false;
	}
}

function onPrint() {
	if(validateFormInputAll()){
		return true;
	}else{
		return false;
	}
}
