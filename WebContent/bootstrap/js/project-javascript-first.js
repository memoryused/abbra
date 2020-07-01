/**
# ---------------------------------------------------------------------------------
# 							PROJECT-JAVASCRIPT-FIRST.JS
# ---------------------------------------------------------------------------------
# javascript function ไว้สำหรับกำหนดค่าเริ่มต้นของโครงการ
#
# 
# ---------------------------------------------------------------------------------
#	UPDATE
# ---------------------------------------------------------------------------------
# Update Date	: 03/08/2017 (Saranyu.s)
#
#
#
*/


jQuery.fn.size = function() {
	return this.length;
};



jQuery( document ).ready(function() {
	//console.info("[ PRO-JAVASCRIPT-FIRST : document ready ]");

	initLayout();
	initComponent();
	
});








function initLayout(){
	//
	//INIT LAYOUT
	//
	manageColCriteriaAdd();
	manageColCriteria();
	manageColVerify();
	// manageLabel();// ไม่ใช้เนื่องจากไม่มี column ที่เป็น label แล้ว
	
}


function initComponent(){
	//
	//INIT COMPONENT
	//
	
	//ACCORDION
	manageAccordion(); //gen ก่อนค่อย gen componnent อื่นด้านใน
	
	//MODAL
	manageModal();
	
	//DATETIMEPICKER
	var configLocale 		 = 'th'; 		/* ParameterConfig applicationLocaleString*/ 	//FIXME
	var configDatetimeLocale = 'th';		/* ParameterConfig datetimeLocaleString*/		//FIXME
	createDateTimePicker(configLocale, configDatetimeLocale);
	manageDateTimepicker();
	
	//CHECKBOX & RADIO
	manageCheckbox();
	manageRadio();
	
	//UNIT
	manageUnit();
	
	//BUTTON
	manageButton();
	
	//Dropdownlist
	manageDropdownlist();
	
	//Autocomplete
	manageAutocomplete();
	
	//AutocompleteAjax
	manageAutocompleteAjax();
	
	//FileUpload
	manageFileUpload();
	
	//SPINNER
	manageSpinner(0, 365, 3); //(min, max, maxlength)
	
	//INIT MENU RIGHT
	jQuery("body").scrollspy({ target: '#menu-right' , offset: 50})
}






