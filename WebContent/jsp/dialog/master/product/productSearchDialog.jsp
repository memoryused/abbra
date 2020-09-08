<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	function initProductDialog() {
		
	}

	function searchProductDialog() {
		var divid = "tableProductContainer"
		
		jQuery("#"+divid+" input[name='criteria.criteriaKey']").val("");
		jQuery("#tableProductResult").sitDataTable(optionsProductDialog);
	}

	function closeSearchProductDialog(){
		jQuery("#productSearchDialog").sitDataTableDialog("close");
	}
	
	function clearSearchProductDialogCallback() {
		$("#criteriaProductContainer").find("[name='criteria.linePerPage']").sitSelectpicker('dropdownlistValue', 3);
		//$("#criteriaProductContainer").find("[name='criteria.product.active.code']").sitSelectpicker('dropdownlistValue', "");
	}

	function chooseSearchProductDialog() {
		$("#productSearchDialog").sitDataTableDialog("multiChoose");
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
		<div id="criteriaProductContainer" class="div-criteria-modal col-12">
	
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_product_itemCode"><s:text name="prd.productCode" /></label>
							<s:textfield id="criteria_product_itemCode"
								name="criteria.product.itemCode"
								cssClass="form-control clearform" maxlength="45" />
						</div>
					</div>
					
				</div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_product_itemName"><s:text name="prd.productName" /></label>
							<s:textfield id="criteria_product_itemName"
								name="criteria.product.itemName"
								cssClass="form-control clearform" maxlength="200" />
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
							<label for="criteria_product_active_code"><s:text name="sec.station_active"/></label> 
							<s:select name="criteria.product.active.code" list="{}" 
								headerKey="" 
								headerValue="%{getText('all')}" 
								listKey="key" 
								listValue="value" 
								cssClass="form-control select-modal clearform"
								id="criteria_product_active_code"/>
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
		data-divCriteriaIdDialog="criteriaProductContainer"  
		data-divTableResultIdDialog="tableProductContainer"
		data-buttonType="search|clear|close" 
		data-auth="<s:property value='ATH.search'/>|<s:property value='ATH.search'/>|true" 
		data-func="searchProductDialog()|clearForm('clearSearchProductDialogCallback','criteriaProductContainer','tableProductContainer' )|closeSearchProductDialog()"  
		data-style="btn btn-fixsize btn-default|btn btn-fixsize btn-outline-secondary|btn btn-fixsize btn-outline-secondary"
		data-container="true" >
	</div>
	<!------------------------------------- BUTTON ------------------------------------->
	
	<div class="row">
		<div id="tableProductContainer" class="col-12 hide-padding" style="display: none;">
			<table id="tableProductResult">
				<thead>
					<tr>
						<th class="col-order">No.</th>
						<th class="col-checkbox"></th>
						<th class="col-2 text-center statusActive" data-name="active.code" data-orderable="false"><s:text name="sec.station_active"/></th>
						<th class="col-6" data-name="itemCode" data-orderable="false"><s:text name="prd.productCode"/></th>
						<th class="col" data-name="itemName" data-orderable="false"><s:text name="prd.productName"/></th>
						
						<!-- <th class="col-status">Status</th> -->
					</tr>
				</thead>
			</table>																																					
		
			<div id="divButtonChooseMultiDialog" class="ui-sitbutton"  
				   data-buttonType="choose_dialog" 
				   data-auth="<s:property value='ATH.search'/>" 
				   data-funcChooseDialog="chooseSearchProductDialog">
			</div>
		
		</div>
	</div>
	
</div>
<div class="modal-footer"></div>
