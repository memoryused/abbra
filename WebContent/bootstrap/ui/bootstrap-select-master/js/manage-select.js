/**
# ---------------------------------------------------------------------------------
# 							MANAGE-SELECT.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
	//Dropdownlist
//	manageDropdownlist();
	
});




function manageDropdownlist(){
	jQuery("select:not([id*='autocomplete']):not(.select-modal)").each(function(index){
		createDropdownlist(jQuery(this));
	});
}



function manageDropdownlistModal(){
	jQuery("select.select-modal:not([id*='autocomplete'])").each(function(index){
		createDropdownlist(jQuery(this));
	});
}



function createDropdownlist($this){
	if($this.is("[data-model-id]")){
		var modelId = $this.attr("data-model-id");
		var firstElement = jQuery("#" + modelId);
		
		var optionsArray = [];
		
		var opt = new Object();
		opt.cssClass = jQuery("#" + modelId).attr("class");
		opt.style = "btn-default";
		
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
		
		if(jQuery("#" + modelId).is("[data-change-function]")){
			opt.onChangeFunction = jQuery("#" + modelId).attr("data-change-function");
		}
		if(jQuery("#" + modelId).is("[disabled]")){
			opt.disabled = jQuery("#" + modelId).attr("disabled");
		}
		
		optionsArray.push(opt);
		/* วนลูปเพื่อสร้าง			 
		[{
			style: "btn-default"
			, cssClass: "form-control"
		},{
			url: "<s:url value='/islandSelectItemServlet'/>"
			, postParamsId: {atollId: "criteria_atollId"}
			, inputModelId: 'criteria_islandId'
			, defaultValue: "All"
			, cssClass: "form-control"
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
			opt.inputModelId = jQuery($ele).attr("id");
			opt.cssClass = jQuery($ele).attr("class");
			
			
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

			if(jQuery($ele).is("[data-change-function]")){
				opt.onChangeFunction = jQuery($ele).attr("data-change-function");
			}
			
			if(jQuery($ele).is("[disabled]")){
				opt.disabled = jQuery($ele).attr("disabled");
			}
			
			optionsArray.push(opt);
			modelId = jQuery($ele).attr("id");
		}
		jQuery(firstElement).sitSelectpickerFilter(optionsArray);
	}else{
		
		var opt = new Object();
		opt.style = "btn-default";
		
		if($this.is("[data-default-value]")){
			opt.defaultValue = $this.attr("data-default-value");
		}else{
			opt.defaultValue = "";
		}
		if($this.is("[data-default-key]")){
			opt.defaultKey = $this.attr("data-default-key");
		}else{
			opt.defaultKey = "";
		}
		
		// Callback function
		if($this.is("[data-change-function]")){
			opt.onChangeFunction = $this.attr("data-change-function");
		}
		
		if($this.is("[disabled]")){
			opt.disabled = $this.attr("disabled");
		}
		
		if($this.hasClass('select-table')){
			opt.container = jQuery('body');
		}
		$this.sitSelectpicker(opt);
	}
}



function generatePostParameters(triggerChangePostParamsId) {

	var paramsObject = new Object();
	if (triggerChangePostParamsId == null) {
		return paramsObject;
	}

	for (var key in triggerChangePostParamsId) {
		var postParam = jQuery("#" + triggerChangePostParamsId[key]);
		if (postParam == undefined) {
			paramsObject[key] = "";
		} else if (postParam.val() == undefined) {
			paramsObject[key] = "";
		} else {
			paramsObject[key] = postParam.val();
		}
	}
	return paramsObject;
}