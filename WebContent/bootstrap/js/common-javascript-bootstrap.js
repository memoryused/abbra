/**
# ---------------------------------------------------------------------------------
# 							COMMON-JAVASCRIPT-BOOTSTRAP.JS
# ---------------------------------------------------------------------------------
# javascript function ตัวกลางที่สามาถย้ายไปโครงการอื่นได้ โดยไม่ยึดติดกับโครงการใดโครงการหนึ่ง
#
#
# ---------------------------------------------------------------------------------
#
#
#
*/


function readonlyAll(){
	//INPUT TEXT , TEXTAREA , RADIO , CHECKBOX
	jQuery("input:visible").prop("readonly", true);
	jQuery("textarea").prop("readonly", true);
	
	jQuery("input:checkbox,input:radio").prop("disabled", true);
		
	//READONLY COMBOBOX
	jQuery(".bootstrap-select").each(function(){
		var select = jQuery(this).find("select");
		var txt = jQuery("#"+select.attr("id")+" option:selected").text();
		var inputHTML = "<input type='text' id='"+ select.attr("id") +"' value='"+ txt +"' class='form-control' readonly='true'>";
		jQuery(this).replaceWith(inputHTML);
	});
	
	//READONLY AUTOCOMPLETE
	jQuery(".ui-autocomplete-input").each(function(){
		var inputHTML = "<input type='text' id='"+ jQuery(this).attr("id") +"' value='"+ jQuery(this).val() +"' class='form-control' readonly='true'>";
		if(jQuery(this).parent(".input-group").length > 0){
			jQuery(this).parent(".input-group").replaceWith(inputHTML);
		}
	});
	
	//READONLY DATETIMEPICKER
	jQuery(".datetimepicker").each(function(){
		jQuery(this).attr("placeholder" , "");
		if(jQuery(this).next(".input-group-addon").length > 0){
			jQuery(this).next(".input-group-addon").remove();
		}
	});
		
	/**
	 * แนวคิด
	 * disabled ปุ่มทั้งหมดที่ไม่ใช่ตัวกลาง gen มาให้
	 * disabled ปุ่มของ predefind ด้วย
	*/
	jQuery(".btn:button:not(.div-btn :button)").each(function(){
		jQuery(this).prop("disabled", true);
	});
}



function tolggleDdisabledAllDiv(divId, flag){
	console.log("tolggleDdisabledAllDiv("+divId+","+ flag +")");
	//INPUT TEXT , TEXTAREA , RADIO , CHECKBOX
	jQuery("#"+ divId + " input").prop("disabled", flag);
	jQuery("#"+ divId + " textarea").prop("disabled", flag);
	
	jQuery("#"+ divId + " input:checkbox,input:radio").prop("disabled", flag);
		
	
	//READONLY DATETIMEPICKER
	jQuery("#"+ divId + " .datetimepicker").each(function(){
		jQuery(this).attr("placeholder" , "");
	});
		
	jQuery("#"+ divId + " .bootstrap-select select").prop("disabled", flag);
	
	jQuery("#"+ divId + " .btn:button:not(.div-btn :button)").each(function(){
		jQuery(this).prop("disabled", flag);
	});
}
