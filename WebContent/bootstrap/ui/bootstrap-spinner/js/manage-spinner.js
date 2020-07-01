/**
# ---------------------------------------------------------------------------------
# 							MANAGE-SPINNER.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 09/08/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
//	manageSpinner();
	
});



function manageSpinner(min, max, maxlength){
	/**
	 * ดู option เพิ่มเติม ได้จาก jQueryUI spinner
	 */
	var countId = 1;
	jQuery(".spinner").each(function(){
		var idSpinner = jQuery(this).attr("id");
		jQuery(this).addClass("input_number");
		jQuery(this).attr("maxlength", maxlength);
		if (typeof idSpinner === 'undefined') {
		   idSpinner = "spinner_"+countId;
		   jQuery(this).attr("id", idSpinner);
		   countId++;
		}
		jQuery("#"+idSpinner).spinner({
			min: min,
			max: max
		});
		
		new NumberControl();
	});
	
}
