/**
# ---------------------------------------------------------------------------------
# 							MANAGE-UNIT.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
//	manageUnit();
	
});



function manageUnit(){
	var htmlUnit = "";
	var txt 	= "";
	var input 	= "";
	jQuery("input[data-unit]").each(function(index){
		txt = jQuery(this).attr("data-unit");
		txt = txt == "" ? "-" : txt;
		input = jQuery(this).prop('outerHTML');
		
		htmlUnit = "";
		htmlUnit += '<div class="input-group">';
		htmlUnit += input;
		htmlUnit += '<span class="input-group-detail">';
		htmlUnit += txt;
		htmlUnit += '</span>';
		htmlUnit += '</div>';
		
		jQuery(this).replaceWith(htmlUnit);
		
	});
	
}



