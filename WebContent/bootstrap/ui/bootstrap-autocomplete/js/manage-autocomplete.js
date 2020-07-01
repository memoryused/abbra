/**
# ---------------------------------------------------------------------------------
# 							MANAGE-AUTOCOMPLETE.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
	//Autocomplete
//	manageAutocomplete();
	
	//AutocompleteAjax
//	manageAutocompleteAjax();
	
});


function manageAutocompleteAjax(){
	jQuery(".autocomplete-ajax").each(function(index){
		createAutocompleteAjax(jQuery(this));
	});
}

function manageAutocomplete(){
	jQuery(".autocomplete").each(function(index){
		createAutocomplete(jQuery(this));
	});
}


function manageAutocompleteModal(){
	jQuery(".autocomplete-modal").each(function(index){
		createAutocomplete(jQuery(this));
	});
}

function createAutocomplete($this){
	
	var modelId = jQuery($this).attr("id");
	if(jQuery("[data-filter-from='" + modelId + "']").length > 0){
		
		var firstElement = jQuery("#" + modelId);
		
		var optionsArray = [];
		
		var opt = new Object();
		opt.cssClass = jQuery("#" + modelId).attr("class");
		var opt = new Object();
		
		if(jQuery("#" + modelId).is("[data-default-value]")){
			opt.defaultValue = jQuery("#" + modelId).attr("data-default-value");
		}else{
			opt.defaultValue = "";
		}
		
		if(jQuery("#" + modelId).is("[data-default-key]")){
			opt.defaultKey = jQuery("#" + modelId).attr("data-default-key");
		}else{
			opt.defaultKey = "";
		}
		
		if(jQuery("#" + modelId).is("[data-limit]")){
			opt.limit = jQuery("#" + modelId).attr("data-limit");
		}
		
		// TODO: เพื่อวาด label กับแสดงค่าเมื่อติด validate
		if(jQuery("#" + modelId).is("[data-invalid-feedBack]")){
			opt.invalidFeedbackName = jQuery("#" + modelId).attr("data-invalid-feedBack");
		}
		
		if(jQuery("#" + modelId).is("[data-label-text]")){
			opt.label = jQuery("#" + modelId).attr("data-label-text");
		}
		
		optionsArray.push(opt);
		/* วนลูปเพื่อสร้าง			 
		 [{      
             defaultKey: "",     // กำหนดค่า key ตัวแรกของ Autocomplete
             defaultValue: ""    // กำหนดค่า value ตัวแรกของ Autocomplete
         },{
             inputModelId: 'criteria_islandId_code',   // id ของ input อำเภอ
             url: "<s:url value='/islandSelectItemServlet'/>",        // กำหนดให้โหลดอำเภอผ่าน servlet
             postParamsId: {atollId: "criteria_atollId_code"},
             defaultKey: "",     // กำหนดค่า key ตัวแรกของ Autocomplete
             defaultValue: ""    // กำหนดค่า value ตัวแรกของ Autocomplete
         }]
		 */
		var postParamId = {};
		while(jQuery("[data-filter-from='" + modelId + "']").length > 0){
			var $ele = jQuery("[data-filter-from='" + modelId + "']");
			var opt = new Object();
			postParamId[jQuery("#" + modelId).attr("data-filter-key")] = modelId;
			
			opt.postParamsId = postParamId;
			
			postParamId = jQuery.extend({}, postParamId);
			opt.url = jQuery($ele).attr("data-url");
			
			if(jQuery($ele).is("[data-default-value]")){
				opt.defaultValue = jQuery($ele).attr("data-default-value");
			}else{
				opt.defaultValue = "";
			}
			if(jQuery($ele).is("[data-default-key]")){
				opt.defaultKey = jQuery($ele).attr("data-default-key");
			}else{
				opt.defaultKey = "";
			}
			
			if(jQuery($ele).is("[data-limit]")){
				opt.limit = jQuery($ele).attr("data-limit");
			}
			
			opt.inputModelId = jQuery($ele).attr("id");
			opt.cssClass = jQuery($ele).attr("class");
			optionsArray.push(opt);
			modelId = jQuery($ele).attr("id");
		}
		jQuery(firstElement).sitAutocomplete(optionsArray);
	}else{
		var optionsArray = [];
		var opt = new Object();
		opt.cssClass = jQuery("#" + modelId).attr("class");
		
		if(jQuery($this).is("[data-default-value]")){
			opt.defaultValue = jQuery($this).attr("data-default-value");
		}else{
			opt.defaultValue = "";
		}
		
		if(jQuery($this).is("[data-default-key]")){
			opt.defaultKey = jQuery($this).attr("data-default-key");
		}else{
			opt.defaultKey = "";
		}
		
		if(jQuery($this).is("[data-limit]")){
			opt.limit = jQuery($this).attr("data-limit");
		}
		
		// TODO: เพื่อวาด label กับแสดงค่าเมื่อติด validate
		if(jQuery($ele).is("[data-invalid-feedBack]")){
			opt.invalidFeedbackName = jQuery("#" + modelId).attr("data-invalid-feedBack");
		}
		
		if(jQuery($ele).is("[data-label-text]")){
			opt.label = jQuery("#" + modelId).attr("data-label-text");
		}
		
		optionsArray.push(opt);
		jQuery($this).sitAutocomplete(optionsArray);
	}
}

function manageAutocompleteAjaxModal(){
	jQuery(".autocomplete-ajax-modal").each(function(index){
		createAutocompleteAjax(jQuery(this));
	});
}

function createAutocompleteAjax($this){
	var modelId = jQuery($this).attr("id");
	if(jQuery("[data-filter-from='" + modelId + "']").length > 0){
		
		var firstElement = jQuery("#" + modelId);
		
		var optionsArray = [];
		
		var opt = new Object();
		opt.cssClass = jQuery("#" + modelId).attr("class");
		var opt = new Object();
		opt.url = jQuery($this).attr("data-url");
		
		if(jQuery("#" + modelId).is("[data-default-value]")){
			opt.defaultValue = jQuery("#" + modelId).attr("data-default-value");
		}else{
			opt.defaultValue = "";
		}
		
		if(jQuery("#" + modelId).is("[data-default-key]")){
			opt.defaultKey = jQuery("#" + modelId).attr("data-default-key");
		}else{
			opt.defaultKey = "";
		}
		
		if(jQuery("#" + modelId).is("[data-limit]")){
			opt.limit = jQuery("#" + modelId).attr("data-limit");
		}
		
		if(jQuery("#" + modelId).is("[data-after-change-func]")){
			opt.limit = jQuery("#" + modelId).attr("data-after-change-func");
		}
		
		// TODO: เพื่อวาด label กับแสดงค่าเมื่อติด validate
		if(jQuery("#" + modelId).is("[data-invalid-feedBack]")){
			opt.invalidFeedbackName = jQuery("#" + modelId).attr("data-invalid-feedBack");
		}
		
		if(jQuery("#" + modelId).is("[data-label-text]")){
			opt.label = jQuery("#" + modelId).attr("data-label-text");
		}
		
		optionsArray.push(opt);
		/* วนลูปเพื่อสร้าง			 
		 [{      
        	 url: "<s:url value='/atollSelectItemServlet'/>", 
             defaultKey: "",     // กำหนดค่า key ตัวแรกของ Autocomplete
             defaultValue: ""    // กำหนดค่า value ตัวแรกของ Autocomplete
         },{
             inputModelId: 'criteria_islandId_code',   // id ของ input อำเภอ
             url: "<s:url value='/islandSelectItemServlet'/>",        // กำหนดให้โหลดอำเภอผ่าน servlet
             postParamsId: {atollId: "criteria_atollId_code"},
             defaultKey: "",     // กำหนดค่า key ตัวแรกของ Autocomplete
             defaultValue: ""    // กำหนดค่า value ตัวแรกของ Autocomplete
         }]
		 */
		var postParamId = {};
		while(jQuery("[data-filter-from='" + modelId + "']").length > 0){
			var $ele = jQuery("[data-filter-from='" + modelId + "']");
			var opt = new Object();
			postParamId[jQuery("#" + modelId).attr("data-filter-key")] = modelId;
			
			opt.postParamsId = postParamId;
			
			postParamId = jQuery.extend({}, postParamId);
			opt.url = jQuery($ele).attr("data-url");

			if(jQuery($ele).is("[data-default-value]")){
				opt.defaultValue = jQuery($ele).attr("data-default-value");
			}else{
				opt.defaultValue = "";
			}
			if(jQuery($ele).is("[data-default-key]")){
				opt.defaultKey = jQuery($ele).attr("data-default-key");
			}else{
				opt.defaultKey = "";
			}
			
			if(jQuery($ele).is("[data-limit]")){
				opt.limit = jQuery($ele).attr("data-limit");
			}
			
			if(jQuery($ele).is("[data-after-change-func]")){
				opt.afterChangeFunction = jQuery($ele).attr("data-after-change-func");
			}
			
			opt.inputModelId = jQuery($ele).attr("id");
			opt.cssClass = jQuery($ele).attr("class");
			optionsArray.push(opt);
			modelId = jQuery($ele).attr("id");
		}
		jQuery(firstElement).sitAutocompleteAjax(optionsArray);
	}else{
		var optionsArray = [];
		var opt = new Object();
		opt.url = jQuery($this).attr("data-url");
		opt.cssClass = jQuery("#" + modelId).attr("class");
		
		if(jQuery($this).is("[data-default-value]")){
			opt.defaultValue = jQuery($this).attr("data-default-value");
		}else{
			opt.defaultValue = "";
		}
		
		if(jQuery($this).is("[data-default-key]")){
			opt.defaultKey = jQuery($this).attr("data-default-key");
		}else{
			opt.defaultKey = "";
		}
		
		if(jQuery($this).is("[data-limit]")){
			opt.limit = jQuery($this).attr("data-limit");
		}
		
		if(jQuery($this).is("[data-after-change-func]")){
			opt.afterChangeFunction = jQuery($this).attr("data-after-change-func");
		}
		
		// TODO: เพื่อวาด label กับแสดงค่าเมื่อติด validate
		if(jQuery("#" + modelId).is("[data-invalid-feedBack]")){
			opt.invalidFeedbackName = jQuery("#" + modelId).attr("data-invalid-feedBack");
		}
		
		if(jQuery("#" + modelId).is("[data-label-text]")){
			opt.label = jQuery("#" + modelId).attr("data-label-text");
		}
		
		optionsArray.push(opt);
		jQuery($this).sitAutocompleteAjax(optionsArray);
	}
}
