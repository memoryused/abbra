/**
# ---------------------------------------------------------------------------------
# 							MANAGE-MODAL.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
	manageModal();
	
});



function manageModal(){
	
	jQuery(".modal").each(function(index){
		var modalHTML = "";
		var id = "";
		var modaldialog	= jQuery(this).find(".modal-dialog").attr("class");
		var modalBody	= jQuery(this).find(".modal-body").prop("outerHTML");
		var modalFooter = jQuery(this).find(".modal-footer").prop("outerHTML");
		
		var msgHeader   = jQuery(this).attr("data-modal-header");
		var btnClose   	= jQuery(this).attr("data-modal-close");
		
		if(typeof jQuery(this).attr("id") === "undefined"){
			
		}else{
			id = jQuery(this).attr("id");
		}

		modalHTML += '<div class="'+ jQuery(this).attr("class") +' fade" id="'+ id +'" tabindex="-1" role="dialog" aria-hidden="true">';
		if(typeof modaldialog === "undefined"){
			modalHTML += '<div class="modal-dialog modal-lg">';
		} else{
			modalHTML += '<div class="'+ modaldialog +'" role="document">';
		}
		modalHTML += '<div class="modal-content">';
		if(typeof msgHeader === "undefined"){
			modalHTML += '<div class="modal-header hide-border">';
			modalHTML += '<h5 class="modal-title">&nbsp;</h5>';
		}else{
			modalHTML += '<div class="modal-header">';
			modalHTML += '<h5 class="modal-title"><strong>'+ msgHeader +'</string></h5>';
		}
		
		if(typeof btnClose === "undefined" || btnClose == "true"){
			modalHTML += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
			modalHTML += '<span aria-hidden="true">&times;</span>';
			modalHTML += '</button>';
		}
		
		modalHTML += '</div>';
		if(typeof modalBody === "undefined"){
			
		}else{
			modalHTML += modalBody;
		}
		if(typeof modalFooter === "undefined"){
			modalHTML += '<div class="modal-footer hide-border">&nbsp;</div>'
		}else{
			modalHTML += modalFooter;
		}
		modalHTML += '</div></div></div>';
			
			
		jQuery(this).replaceWith(modalHTML);
		
	});
	
}



