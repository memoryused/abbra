<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	function initVendorDialog() {
		
	}

	function searchVendorDialog() {
		var divid = "tableVendorContainer"
		
		jQuery("#"+divid+" input[name='criteria.criteriaKey']").val("");
		jQuery("#tableVendorResult").sitDataTable(optionsVendorDialog);
	}

	function closeSearchVendorDialog(){
		jQuery("#vendorSearchDialog").sitDataTableDialog("close");
	}
	
	function clearSearchVendorDialogCallback() {
		$("#criteriaVendorContainer").find("[name='criteria.linePerPage']").sitSelectpicker('dropdownlistValue', 3);
		//$("#criteriaVendorContainer").find("[name='criteria.group.active.code']").sitSelectpicker('dropdownlistValue', "");
	}

	function chooseSearchVendorDialog() {
		$("#vendorSearchDialog").sitDataTableDialog("multiChoose");
	}

	/*div สำหรับ กด ดุ detail Error*/
	function showErrorPopupDetail(elName) {
		var html = document.getElementById(elName).innerHTML;
		html = getMessageResponse(html);
		if (html.length > 0) {
			alert(html);
		}
	}
</script>
<%-- <s:include value="/jsp/template/message_popup.jsp"/> --%>
<div class="modal-body">
	<div class="row">
		<div id="criteriaVendorContainer" class="div-criteria-modal col-12">
	
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_vendor_vendorCode"><s:text name="prd.venderCode" /></label>
							<s:textfield id="criteria_vendor_vendorCode"
								name="criteria.vendor.vendorCode"
								cssClass="form-control clearform" maxlength="45" />
						</div>
					</div>
					
				</div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_vendor_vendorName"><s:text name="prd.venderName" /></label>
							<s:textfield id="criteria_vendor_vendorName"
								name="criteria.vendor.vendorName"
								cssClass="form-control clearform" maxlength="400" />
						</div>
					</div>
					
				</div>
				<div class="col-std"></div>
			</div>
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					
					<div class="col-md-12 ">
						<div class="md-form">
							<label for="criteria_vendor_active_code"><s:text name="sec.station_active"/></label> 
							<s:select name="criteria.vendor.active.code" list="{}" 
								headerKey="" 
								headerValue="%{getText('all')}" 
								listKey="key" 
								listValue="value" 
								cssClass="form-control select-modal clearform"
								id="criteria_vendor_active_code"/>
						</div>
					</div>
					
				</div>
				<div class="col-std-2"><s:hidden id="criteria_linePerPage" name="criteria.linePerPage"/></div>
				<div class="col-std"></div>
 			
			</div>
		</div>
	</div>
	
	<!------------------------------------- BUTTON ----------------------------------->
	
	<div id="divButtonSearchSingleDialog" class="ui-sitbutton-predefine"  
		data-divCriteriaIdDialog="criteriaVendorContainer"  
		data-divTableResultIdDialog="tableVendorContainer"
		data-buttonType="search|clear|close" 
		data-auth="<s:property value='ATH.search'/>|<s:property value='ATH.search'/>|true" 
		data-func="searchVendorDialog()|clearForm('clearSearchVendorDialogCallback','criteriaVendorContainer','tableVendorContainer' )|closeSearchVendorDialog()"  
		data-style="btn btn-fixsize btn-default|btn btn-fixsize btn-outline-secondary|btn btn-fixsize btn-outline-secondary"
		data-container="true" >
	</div>
	<!------------------------------------- BUTTON ------------------------------------->
	
	<div class="row">
		<div id="tableVendorContainer" class="col-12 hide-padding" style="display: none;">
			<table id="tableVendorResult">
				<thead>
					<tr>
						<th class="col-order">No.</th>
						<th class="col-checkbox"></th>
						<th class="col-2 text-center statusActive" data-name="active.code" data-orderable="false"><s:text name="sec.station_active"/></th>
						<th class="col-6" data-name="vendorCode" data-orderable="false"><s:text name="prd.venderCode"/></th>
						<th class="col" data-name="vendorName" data-orderable="false"><s:text name="prd.venderName"/></th>
						
						<!-- <th class="col-status">Status</th> -->
					</tr>
				</thead>
			</table>																																					
		
			<div id="divButtonChooseMultiDialog" class="ui-sitbutton"  
				   data-buttonType="choose_dialog" 
				   data-auth="<s:property value='ATH.search'/>" 
				   data-funcChooseDialog="chooseSearchVendorDialog">
			</div>
		
		</div>
	</div>
	
</div>
<div class="modal-footer"></div>
