/**
# ---------------------------------------------------------------------------------
# 							MANAGE-PLUPLOAD.JS
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


function manageFileUpload(){
	jQuery(".div-browsefile").each(function(){
		var options = {
			maxFileSize: jQuery(this).attr("data-max-file-size") // 10 MB
			, idBrowseFileBtn: jQuery(this).attr("data-id-browse-btn")
			, idDivContainer: jQuery(this).attr("data-id-div-container")
			, uploadURL: jQuery(this).attr("data-upload-url")
			, filterFileType: jQuery(this).attr("data-filter-file-type")
			, callbackFunc: jQuery(this).attr("data-callback-func")
		}
		jQuery(this).sitFileUpload(options);
	});
}

function viewerFile(srcFile, fileName){ 
	jQuery("#fileNameTmpForViewer").val(srcFile);
	jQuery("#fileNameForViewer").val(fileName);
	window.open(CONTEXT_JS_CSS + '/jsp/template/viewer.jsp');
}