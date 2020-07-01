<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">

//callback สำหรับ upload ไฟล์ทั่วไป
function callbackPdf(obj, file) {
	jQuery("#pdfFileNameHide").val(obj.fileUploadFileNameTmp[0]);
	jQuery("#pdfFileNameDisplay").val(obj.fileUploadFileName[0]);
	jQuery("#pdfFileNameDisplay").focus();
}

//callback สำหรับ upload ไฟล์ทั่วไป
function callbackTxt(obj, file) {
	jQuery("#txtFileNameHide").val(obj.fileUploadFileNameTmp[0]);
	jQuery("#txtFileNameDisplay").val(obj.fileUploadFileName[0]);
	jQuery("#txtFileNameDisplay").focus();
}

function postAction(surl){
	
	var param = jQuery('form :input').serialize();	
	var isDone = false;
	
	jQuery.ajax({
		type : "POST",
		url : surl,
		data: param,
		dataType: "json",
		async : false,
		success : function(data) {
			
			//console.log(data);
			if(data.messageAjax.messageType == null){	//Save success
				alert('<s:text name="30003" />');
				isDone = true;
			} else{
				alert(data.messageAjax.message);
			}
		},
		error: function(xhr, status, error){
			var err = eval("(" + xhr.responseText + ")");
			alert(err.Message);
		}
	}).done(function(){
		if(isDone){
			clearForm();
			jQuery("#modalAddEdit").modal("hide");
		}
	});
}

function saveModal(){
	if(!validateModal()){
		showNotifyMessageAlert('<s:text name="10002" />');
		return false;
	}
	
	if (confirm('<s:text name="50003" />') == false) {
        return false;
    }
	
	postAction("<s:url value='/jsp/product/saveProductInfo.action' />");
}

function editModal(){
	if(!validateModal()){
		showNotifyMessageAlert('<s:text name="10002" />');
		return false;
	}
	
	if (confirm('<s:text name="50004" />') == false) {
        return false;
    }
	
	postAction("<s:url value='/jsp/product/modalEditProductInfo.action' />");
}

function validateModal(){
	var isValid = false;
	if(jQuery("#dp_productInfo_expireDate").data("DateTimePicker").date() == null){
		isValid = false;
		return false;
	}
	
	if(jQuery("#pdfFileNameDisplay").val() === ''){
		isValid = false;
		return false;
	}
	
	jQuery('form :input.requireInput').each(function(){
		if(jQuery(this).val() == ''){
			isValid = false;
			return false;
		} else{
			isValid = true;
		}
	});
	
	var docId = jQuery("[name='productInfo.documentId']").val();
	if(docId == 1 || docId == 3){
		jQuery('div#modalAddEdit :input.requireInputx').each(function(){
			if(jQuery(this).val() == ''){
				isValid = false;
				return false;
			} else{
				isValid = true;
			}
		});
	}
	
	return isValid;
}
</script>
<%--———————————————————————————————————————————————————————————————————————
	MODAL
————————————————————————————————————————————————————————————————————————--%>
<div class="row">
	<div class="col-std">
		<div class="col-md-12">
			<div class="md-form">
				<label for="listStandard" class="md-form-active"><s:text name="prd.standard"/></label><br/>
				<input id="listStandard"/>
				<s:hidden name="productInfo.dialog.docTransId"/>
			</div>
		</div>
	</div>
	<div class="col-std">
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield data-dp-id="dp_productInfo_expireDate" id="productInfo_expireDate"
					 name="productInfo.dialog.docExpireDate" cssClass="form-control requireInput clearform" data-label-text="%{getText('prd.expireDate')}"/>
			</div>
		</div>
	</div>
</div>

<div id="divTemplate1">
	<div class="row">
		<div class="col-std">
			<div class="col-md-12">
				<div class="md-form">
					<s:textfield id="contractEmail" name="productInfo.dialog.contractEmail" cssClass="form-control requireInputx clearform"/>
					<label for="contractEmail"><s:text name="prd.contractMail"/></label>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-std">
			<div class="col-md-12">
				<div class="md-form">
					<s:textfield id="sender" name="mailControl.sender" readonly="true" cssClass="form-control read-only"/>
					<label for="sender"><s:text name="prd.sender"/></label>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-std">
			<div class="col-md-12">
				<div class="md-form">
					<s:textfield id="cc" name="mailControl.cc1" readonly="true" cssClass="form-control read-only"/>
					<label for="cc"><s:text name="prd.cc"/></label>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-std">
		<div class="col-md-12">
			<div class="md-form">
			
				<div class="div-browse-text div-browsefile" id="divBrowseFilePdf" 
					data-max-file-size="10"
					data-id-browse-btn="pickfilesPdf"
					data-id-div-container="divBrowseFilePdf"
					data-upload-url="<s:url value='/jsp/product/browseWithoutThumbnailProductInfo.action' />"
					data-callback-func="callbackPdf"
					data-filter-file-type="pdf">
				</div>
				
				<s:textfield cssClass="form-control requireInput clearform" id="pdfFileNameDisplay" autocomplete="off" name="productInfo.dialog.fileMetaPdf.fileUploadFileName" />
				<s:hidden id="pdfFileNameHide" name="productInfo.dialog.fileMetaPdf.fileUploadFileNameTmp"/>
				<label for="pdfFileNameDisplay"><s:text name="prd.pdfFileUpload"/></label>
		    </div>
		</div>
	</div>
	<div class="col-std-1" style="padding-top: 2rem;">
		<a href="javascript:void();" id="pickfilesPdf"><i class="fa fa-cloud-upload" aria-hidden="true"></i>&nbsp;Browse...</a>
	</div>
</div>

<div class="row">
	<div class="col-std">
		<div class="col-md-12">
			<div class="md-form">
			
				<div class="div-browse-text div-browsefile" id="divBrowseFileTxt" 
					data-max-file-size="10"
					data-id-browse-btn="pickfilesTxt"
					data-id-div-container="divBrowseFileTxt"
					data-upload-url="<s:url value='/jsp/product/browseWithoutThumbnailProductInfo.action' />"
					data-callback-func="callbackTxt"
					data-filter-file-type="txt">
				</div>
				
				<s:textfield cssClass="form-control clearform" id="txtFileNameDisplay" autocomplete="off" name="productInfo.dialog.fileMetaTxt.fileUploadFileName"/>
				<s:hidden id="txtFileNameHide" name="productInfo.dialog.fileMetaTxt.fileUploadFileNameTmp"/>
				<label for="txtFileNameDisplay"><s:text name="prd.txtFileUpload"/></label> 

			</div>
		</div>
	</div>
	<div class="col-std-1" style="padding-top: 2rem;">
		<a href="javascript:void();" id="pickfilesTxt"><i class="fa fa-cloud-upload" aria-hidden="true"></i>&nbsp;Browse...</a>
	</div>
</div>
