/**
# ---------------------------------------------------------------------------------
# 							MANAGE-BUTTON.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
//	manageButton();
	
});



function manageButton(){
	jQuery("div.ui-sitbutton").each(function(){
		jQuery("#"+jQuery(this).attr("id")).sitButton({
			buttonType : jQuery(this).attr("data-buttontype"),
		    auth : jQuery(this).attr("data-auth"),
		    funcSearch : jQuery(this).attr("data-funcsearch"),
		    funcClearFormCallBack : jQuery(this).attr("data-funcclearformcallback"),
		    funcValidSearch : jQuery(this).attr("data-funcvalidsearch"),
		    divCriteriaId : jQuery(this).attr("data-divcriteriaId"),
		    divTableResultId : jQuery(this).attr("data-divtableresultid"),
		    messageConfirmAdd : jQuery(this).attr("data-messageconfirmadd"),
		    messageCancelAdd : jQuery(this).attr("data-messagecanceladd"),
		    funcSaveAdd : jQuery(this).attr("data-funcsaveadd"),
		    funcValidAdd : jQuery(this).attr("data-funcvalidadd"),
		    funcCancelAdd : jQuery(this).attr("data-funccanceladd"),
		    messageConfirmEdit : jQuery(this).attr("data-messageconfirmedit"),
		    messageCancelEdit : jQuery(this).attr("data-messagecanceledit"),
		    funcSaveEdit : jQuery(this).attr("data-funcsaveedit"),
		    funcValidEdit : jQuery(this).attr("data-funcvalidedit"), 
		    funcCancelEdit : jQuery(this).attr("data-funcCancelEdit"),
		    messageCancelView : jQuery(this).attr("data-messagecancelview"),
		    funcCancelView : jQuery(this).attr("data-funccancelview"),
		    funcPrint : jQuery(this).attr("data-funcprint"),
		    funcCancelPrint : jQuery(this).attr("data-funccancelprint"),
		    divCriteriaIdDialog : jQuery(this).attr("data-divcriteriaiddialog"),
		    divTableResultIdDialog : jQuery(this).attr("data-divtableresultiddialog"),
		    funcSearchDialog : jQuery(this).attr("data-funcsearchdialog"),
		    funcValidSearchDialog : jQuery(this).attr("data-funcvalidsearchdialog"),
		    funcClearFormCallBackDialog : jQuery(this).attr("data-funcclearformcallbackdialog"),
		    funcCloseDialog : jQuery(this).attr("data-funcclosedialog"),
		    funcChooseDialog : jQuery(this).attr("data-funcchoosedialog")
		});
	});		
	
	
	
	jQuery("div.ui-sitbutton-predefine").each(function(){
		jQuery("#"+jQuery(this).attr("id")).sitButtonPredefine({
			buttonType : jQuery(this).attr("data-buttontype"),
			auth : jQuery(this).attr("data-auth"),
			func : jQuery(this).attr("data-func"),
			container : jQuery(this).attr("data-container"),
			style : jQuery(this).attr("data-style")
		});
	});
}



