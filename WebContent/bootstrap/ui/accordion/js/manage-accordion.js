/**
# ---------------------------------------------------------------------------------
# 							MANAGE-ACCORDION.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
//	manageAccordion();
	
});



function manageAccordion(){
	jQuery("div.accordion").each(function(index){
//		console.log("[ manageAccordion ] "+index);
		
		if(jQuery(this).children("div").length == 2){
			var accrdion = jQuery(this);
			var divHead = jQuery(this).children("div:first");
			var divBody = jQuery(this).children("div:last");
			
//			console.log(divHead.attr("id"));
			
			//ADD ID
			if (typeof jQuery(this).attr("id") === "undefined") {
				jQuery(this).attr("id", "accordion_"+(index+1));
			}
			if (typeof divHead.attr("id") === "undefined") {
				divHead.attr("id", "accordionHeader_"+(index+1));
			}
			if (typeof divBody.attr("id") === "undefined") {
				divBody.attr("id", "accordionBody_"+(index+1));
			}
			
			divHead.addClass("card-header noselect").attr("data-toggle", "collapse").attr("data-parent", "").attr("data-target", "#collapse_"+(index+1));
			divBody.addClass("card-block");
//			console.log(divHead.prop("outerHTML"));
			
			var accoHtml = "";
			accoHtml += '<div id="'+ jQuery(this).attr("id") +'" class="accordion">';
			accoHtml += '<div id="card_'+ (index+1) +'" class="card">';
			accoHtml += divHead.prop("outerHTML");
			accoHtml += '<div id="collapse_'+ (index+1) +'" class="collapse">';
			accoHtml += divBody.prop("outerHTML");
			accoHtml += '</div>';
			accoHtml += '</div>';
			accoHtml += '</div>';
//			console.log(accoHtml);
			
			jQuery(this).replaceWith(accoHtml);
			
			//ADD ICON ARROW
			jQuery("#"+divHead.attr("id")).append('<i class="fa fa-chevron-right float-right acc-arrow" aria-hidden="true"></i>');
			
			var useHide = accrdion.attr("data-toggle-enable");
			
			if(typeof useHide !== "undefined"){
				if(useHide == "false"){
					tolggleDdisabledAllDiv(accrdion.attr("id"), true);
				}
			}
			
			jQuery("#card_"+ (index+1)).on('show.bs.collapse', function () {
				jQuery(this).find(".card-header").addClass("card-active");
				jQuery(this).find(".fa-chevron-right").removeClass("fa-chevron-right").addClass("fa-chevron-down");
				
				if(typeof useHide !== "undefined"){
					if(useHide == "false"){
						tolggleDdisabledAllDiv(accrdion.attr("id"), false);
					}
				}
				
				if(typeof accrdion.attr("data-event-show") !== "undefined"){
					callFunc(accrdion.attr("data-event-show"));
				}
				
			}).on('shown.bs.collapse', function () {
				if(typeof accrdion.attr("data-event-shown") !== "undefined"){
					callFunc(accrdion.attr("data-event-shown"));
				}
			}).on('hide.bs.collapse', function () {
				jQuery(this).find(".card-header").removeClass("card-active");
				
				if(typeof accrdion.attr("data-event-hide") !== "undefined"){
					callFunc(accrdion.attr("data-event-hide"));
				}
				
			}).on('hidden.bs.collapse', function () {
				jQuery(this).find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-right");
				if(typeof accrdion.attr("data-event-hidden") !== "undefined"){
					callFunc(accrdion.attr("data-event-hidden"));
				}
				
				if(typeof useHide !== "undefined"){
					if(useHide == "false"){
						tolggleDdisabledAllDiv(accrdion.attr("id"), true);
					}
				}
			});
		}
			
	});
	
}



function callFunc(func){
	try {
		var funhide = func;
		var fnstring = "";
		var fnparams = new Array();
		if(funhide.indexOf("(") > -1){
			// function name and parameters to pass
			fnname = funhide.substring(0, funhide.lastIndexOf("("));
			fnparams = funhide.substring(funhide.lastIndexOf("(")+1, funhide.lastIndexOf(")")).split(",");
		}else{
			fnname = funhide;
		}
		
		// find object
		var fn = window[fnname];

		// is object a function?
		if (typeof fn === "function"){
			if(fnparams != null && fnparams.length > 0){
				fn.apply(null, fnparams);
			}else{
				fn();
			}
		}
	
	}catch(err) {
	   console.log("ERROR : "+err);
	}
} 

