<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript" src="<s:url value='/js/dropdownlist/dropdownlist.js'/>"></script>
<script type="text/javascript">
	jQuery(document).ready(function(){
		jQuery('#modalAddEdit').on('show.bs.modal', function (e) {
			//alert('Modal show');
		}).on('hidden.bs.modal', function (e) {
			//alert('Modal closed');
			postSearchActiveTab();
		});
		
		jQuery("#btnCancel").click(function(e){
			clearForm();
		});
	});

	var cacheArr = [];
	function sf(){
		jQuery(".nav-tabs-4 a[href='#tab-1']").tab("show");
		searchByStandard(1);
		
		jQuery('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
			var target = $(e.target).attr("href").split('-'); // activated tab
			searchByStandard(target[target.length-1]);
		})
	}
	
	function searchByStandard(docId) {
		jQuery("[name='productInfo.documentId']").val(docId);

		if(cacheArr.indexOf(docId) < 0){
			cacheArr.push(docId);
			postSearchActiveTab();
		} 
	}
	function postSearchActiveTab(){
		jQuery.ajax({
			type : "POST",
			url : "<s:url value='/jsp/product/searchByStandardProductInfo.action' />",
			data: jQuery('form :input').serialize(),
			dataType: "json",
			async : false,
			success : function(data) {
				//console.log(data);
				if(data.lstResult.length > 0){
					draw(data, jQuery("[name='productInfo.documentId']").val());
				}
			}
		});
	}
	
	function draw(data, docId){
		var html = '';
		data.lstResult.forEach(function(item){ 
			html += '<tr ondblclick="initEditModal(\''+ item.certificateId + '\', \''+ item.docTranId +'\');"><td class="text-center checkbox-datatable"><input type="checkbox" name="criteria.selectedIds" value="'+ item.docTranId +'"/></td>';
			html += '<td>' + item.certificateName + '</td>';
			
			if(docId == 1 || docId == 3){
				html += '<td>' + item.contractEmail + '</td>';
			}
			html += '<td>' + item.docExpireDate + '</td>';
			
			//PDF
			if(item.pdfPath === "Y"){
				var url = "<s:url value='/DownloadCertificateServlet'/>?refId=" + item.docTranId;
				html += '<td class="text-center"><a href="'+ url +'"><i class="fa fa-file-pdf-o" aria-hidden="true"></i></a></td>';	
			} else {
				html += '<td class="text-center"><i class="fa fa-file-pdf-o" aria-hidden="true"></i></td>';
			}
			
			//TXT
			if(item.txtPath === "Y"){
				var url = "<s:url value='/DownloadCertificateServlet'/>?refId=" + item.docTranId;
				html += '<td class="text-center"><a href="'+ url +'"><i class="fa fa-file-pdf-o" aria-hidden="true"></i></a></td>';
			} else if(item.txtPath === "N"){
				html += '<td class="text-center"><i class="fa fa-file-pdf-o" aria-hidden="true"></i></td>';		
			} else {
				html += '<td></td>';
			}
			
			html += '</tr>';
	    }); 
		
		//console.log(html);
		jQuery("#tableResult-" + docId + " > tbody").html(html);
		jQuery("#tableResult-" + docId).parent().css("display", "");
		//jQuery("#tableResult-" + docId).dataTable();
		
	}
	
	function cancelEdit() {
		if (confirm('<s:text name="50010"/>') == false) {
	        return false;
	    }
		cacheArr = [];
	    submitPageForm();
	}
	
	function submitPageForm() {
	    if (document.getElementsByName('criteriaKeyTemp')[0].value == '') {
	        action = "<s:url value='/jsp/product/initProductInfo.action' />";
	    } else {
	        action = "<s:url value='/jsp/product/cancelProductInfo.action' />";
	    }
	    submitPage(action);
	}
	
	function deletItem(){
		
		var isDone = false;
		
		chk = submitAjaxStatus("Y", "<s:text  name='50005'/>");	//FIXME
		if(chk == false){
			return false;
		}
		
		jQuery.ajax({
			type : "POST",
			url : "<s:url value='/jsp/product/removeProductInfo.action' />",
			data: jQuery('form :input').serialize(),
			dataType: "json",
			async : false,
			success : function(data) {
				//console.log(data);
				if(data.messageAjax.messageType == null){	//Delete success
					alert('<s:text name="30005" />');
					isDone = true;
				} else{
					alert(data.messageAjax.message);
				}
			}
		}).done(function(){
			if(isDone){
				postSearchActiveTab();
			}
		});
		
	}
	
	///////////////////////////////////// MODAL //////////////////////////////////////
	
	function initAddModal() {
		jQuery("[name='productInfo.certId']").val("");
		
		//Bind event for add
		jQuery("#btnSave").attr("onclick", "saveModal();");
		
		postInitModal("A");
	}
	
	function initEditModal(certId, docTransId){
		jQuery("[name='productInfo.certId']").val(certId);
		jQuery("[name='productInfo.docTransId']").val(docTransId);
		
		//Bind event for edit
		jQuery("#btnSave").attr("onclick", "editModal();");
		
		jQuery("#modalAddEdit").modal();
		postInitModal("E");
	}
	
	function postInitModal(pageType){
		var param = jQuery('div#criteriaContainer :input').serialize();		
		jQuery.ajax({
			type : "POST",
			url : "<s:url value='/jsp/product/gotoAddProductInfo.action' />",
			data: param,
			dataType: "json",
			async : false,
			success : function(data) {
				//console.log(data);
				manageModel(data);
				manageData(data);
			}
		});
	}
	
	function manageModel(data) {
		// Manage modal display 
		var docId = jQuery("[name='productInfo.documentId']").val();
		if(docId == 1 || docId == 3){
			jQuery("div#divTemplate1").show();
		} else{
			jQuery("div#divTemplate1").hide();
		}
		
		jQuery("div.bootstrap-select").remove();
		
		// Draw droupdown
		var optionDropdown = {
			inputModelId:"listStandard",
			seq:0,
			inputModelName:"productInfo.dialog.certificateId",
			defaultValue:'',
			defaultKey:"",
			cssStyle:"width: 100%;",
			cssClass:"requireInput"
		}
		
		drawingDropdownlistFromJsonSelectItem(data.listStandard, optionDropdown);
	
		// สร้าง selectmenu 
		manageDropdownlist();
	}
	
	function manageData(data){
		//Draw data for edit dialog
		jQuery("#contractEmail").val(data.productInfo.dialog.contractEmail);
		jQuery("#listStandard_DROPDOWNLIST").sitSelectpicker('dropdownlistValue', data.productInfo.dialog.certificateId);	
		jQuery("#dp_productInfo_expireDate").data("DateTimePicker").date(data.productInfo.dialog.docExpireDate);
		jQuery("#pdfFileNameDisplay").val(data.productInfo.dialog.fileMetaPdf.fileUploadFileName);
		jQuery("#txtFileNameDisplay").val(data.productInfo.dialog.fileMetaTxt.fileUploadFileName);
		jQuery("[name='productInfo.dialog.docTransId']").val(data.productInfo.dialog.docTransId);
		
		//Add class label active
		jQuery("label[for='contractEmail']").addClass("active");
		jQuery("label[for='pdfFileNameDisplay']").addClass("active");
		jQuery("label[for='txtFileNameDisplay']").addClass("active");
	}
	
	function clearForm(){
		jQuery("#contractEmail").val("");
		jQuery("#listStandard_DROPDOWNLIST").sitSelectpicker('dropdownlistValue', "");	
		jQuery("#dp_productInfo_expireDate").data("DateTimePicker").date(null);
		jQuery("#pdfFileNameDisplay").val("");
		jQuery("#pdfFileNameHide").val("");
		jQuery("#txtFileNameDisplay").val("");
		jQuery("#txtFileNameHide").val("");
		jQuery("[name='productInfo.dialog.docTransId']").val("");
	}
</script>
</head>
<body>
<s:form id="addEditForm" name="addEditForm" method="post" cssClass="margin-zero" onsubmit="return false;">
	<div class="row">
		<div id="criteriaContainer" class="div-criteria">
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="venderNo" name="productInfo.venderCode" cssClass="form-control read-only" readonly="true"/>
							<label for="venderNo"><s:text name='prd.venderNo'/></label> 
						</div>
					</div>
				</div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="venderShortName" name="productInfo.venderShortName" cssClass="form-control read-only" readonly="true"/>
							<label for="venderShortName"><s:text name='prd.venderShortName'/></label> 
						</div>
					</div>
				</div>
				<div class="col-std"></div>
			</div>
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="venderName" name="productInfo.venderName" cssClass="form-control read-only" readonly="true"/>
							<label for="venderName"><s:text name='prd.venderName'/></label> 
						</div>
					</div>
				</div>
				<div class="col-std-2">
				    <s:hidden name="productInfo.venderItemId" cssClass="requireInput"/>
   					<s:hidden name="productInfo.documentId" cssClass="requireInput"/>
   					<s:hidden name="productInfo.certId"/>
   					<s:hidden name="productInfo.docTransId"/>
				</div>
				<div class="col-std"></div>
			</div>
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="productNo" name="productInfo.productCode" cssClass="form-control read-only" readonly="true"/>
							<label for="productNo"><s:text name='prd.productNo'/></label> 
						</div>
					</div>
				</div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="productName" name="productInfo.productName" cssClass="form-control read-only" readonly="true"/>
							<label for="productName"><s:text name='prd.productName'/></label> 
						</div>
					</div>
				</div>
				<div class="col-std"></div>
			</div>
		</div>
	</div>
	
	
	<div class="row">
		<div class="col-std">
			<a href="javascript:void(0);" onclick="initAddModal();" data-toggle="modal" data-target="#modalAddEdit"><i class="fa fa-plus-square-o fa-3 info-text" aria-hidden="true"></i>Add</a>&nbsp;&nbsp;
<!-- 			<a href="javascript:void(0);" ><i class="fa fa-pencil-square-o fa-3 orange-text" aria-hidden="true"></i>Edit</a>&nbsp;&nbsp; -->
			<a href="javascript:void(0);" onclick="deletItem();"><i class="fa fa-minus-square-o fa-3 red-text" aria-hidden="true"></i>Delete</a>
			<a href="javascript:void(0);" onclick="cancelEdit();"><i class="fa fa-hand-o-left fa-3 green-text" aria-hidden="true"></i>Back</a>
		</div>
	</div>
	
	<div class="row">
		<div class="col-std">
			<div id="tabs">
				<ul class="nav nav-tabs nav-tabs-4">
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-1"><s:text name="prd.tabCert" /></a></li>
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-2"><s:text name="prd.tabSpec" /></a></li>
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-3"><s:text name="prd.tabTestReport" /></a></li>
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-4"><s:text name="prd.tabProcessFlow" /></a></li>
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-5"><s:text name="prd.tabNutrition" /></a></li>
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-6"><s:text name="prd.tabMsds" /></a></li>
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-7"><s:text name="prd.tabStatement" /></a></li>
					<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-8"><s:text name="prd.tabComposition" /></a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane" id="tab-1">
						<s:include value="/jsp/product/productinfo/include/certificate.jsp" />
					</div>
					<div class="tab-pane" id="tab-2">
						<s:include value="/jsp/product/productinfo/include/spec.jsp" />
					</div>
					<div class="tab-pane" id="tab-3">
						<s:include value="/jsp/product/productinfo/include/testreport.jsp" />
					</div>
					<div class="tab-pane" id="tab-4">
						<s:include value="/jsp/product/productinfo/include/processflow.jsp" />
					</div>
					<div class="tab-pane" id="tab-5">
						<s:include value="/jsp/product/productinfo/include/nutrition.jsp" />
					</div>
					<div class="tab-pane" id="tab-6">
						<s:include value="/jsp/product/productinfo/include/msds.jsp" />
					</div>
					<div class="tab-pane" id="tab-7">
						<s:include value="/jsp/product/productinfo/include/statement.jsp" />
					</div>
					<div class="tab-pane" id="tab-8">
						<s:include value="/jsp/product/productinfo/include/composition.jsp" />
					</div>
				</div>
			</div>
		</div>
	</div>

    <s:hidden name="criteriaKeyTemp"/>
    <s:hidden name="criteria.statusForUpdate" />
    <s:hidden name="P_CODE"/>
    <s:hidden name="F_CODE"/>
    <s:hidden name="page"/>

    <s:token />

<div id="modalAddEdit" class="modal" data-modal-close="true">
	<div class="modal-body">
		<s:include value="/jsp/product/productinfo/dialog/template1.jsp" />
	</div>
	<div class="modal-footer">
		<button id="btnSave" class="btn btn-fixsize btn-default wave-effect font-weight-bold">
			<i class="fa fa-search" aria-hidden="true"></i>&nbsp;&nbsp;<s:text name="save"/>
		</button>
		<button id="btnCancel" class="btn btn-fixsize btn-outline-secondary wave-effect font-weight-bold" onclick="">
			<i class="fa fa-refresh" aria-hidden="true"></i>&nbsp;&nbsp;<s:text name="clear"/>
		</button>
	</div>
</div>

</s:form>


	
</body>
</html>