<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	function initGroupDialog() {
	}

	function searchGroupDialog() {
		var divid = "tableGroupContainer"
		
		jQuery("#"+divid+" input[name='criteria.criteriaKey']").val("");
		jQuery("#tableGroupResult").sitDataTable(optionsGroupDialog);
	}

	function closeSearchGroupDialog(){
		jQuery("#groupUserSearchDialog").sitDataTableDialog("close");
	}
	
	function clearSearchGroupDialogCallback() {
		$("#criteriaGroupContainer").find("[name='criteria.linePerPage']").sitSelectpicker('dropdownlistValue', 3);
		//$("#criteriaGroupContainer").find("[name='criteria.group.active.code']").sitSelectpicker('dropdownlistValue', "");
	}

	function chooseSearchGroupDialog() {
		$("#groupUserSearchDialog").sitDataTableDialog("multiChoose");
		clearForm('clearSearchGroupDialogCallback','criteriaGroupContainer','tableGroupContainer');
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
		<div id="criteriaGroupContainer" class="div-criteria-modal col-12">
	
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_group_groupCode"><s:text name="sec.group_code" /></label>
							<s:textfield id="criteria_group_groupCode"
								name="criteria.group.groupCode"
								cssClass="form-control clearform" maxlength="80" />
						</div>
					</div>
					
				</div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_group_groupName"><s:text name="sec.group_name" /></label>
							<s:textfield id="criteria_group_groupName"
								name="criteria.group.groupName"
								cssClass="form-control clearform" maxlength="100" />
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
							<label for="criteria_group_active_code"><s:text name="sec.station_active"/></label> 
							<s:select name="criteria.group.active.code" list="{}" 
								headerKey="" 
								headerValue="%{getText('all')}" 
								listKey="key" 
								listValue="value" 
								cssClass="form-control select-modal clearform"
								id="criteria_group_active_code"/>
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
		data-divCriteriaIdDialog="criteriaGroupContainer"  
		data-divTableResultIdDialog="tableGroupContainer"
		data-buttonType="search|clear|close" 
		data-auth="<s:property value='ATH.search'/>|<s:property value='ATH.search'/>|true" 
		data-func="searchGroupDialog()|clearForm('clearSearchGroupDialogCallback','criteriaGroupContainer','tableGroupContainer' )|closeSearchGroupDialog()"  
		data-style="btn btn-fixsize btn-default|btn btn-fixsize btn-outline-secondary|btn btn-fixsize btn-outline-secondary"
		data-container="true" >
	</div>
	<!------------------------------------- BUTTON ------------------------------------->
	
	<div class="row">
		<div id="tableGroupContainer" class="col-12 hide-padding" style="display: none;">
			<table id="tableGroupResult">
				<thead>
					<tr>
						<th class="col-order">No.</th>
						<th class="col-checkbox"></th>
						<th class="col-2 text-center statusActive" data-name="active.code" data-orderable="false"><s:text name="sec.station_active"/></th>
						<th class="col-6" data-name="groupCode" data-orderable="false"><s:text name="sec.group_code"/></th>
						<th class="col" data-name="groupName" data-orderable="false"><s:text name="sec.group_name"/></th>
						
						<!-- <th class="col-status">Status</th> -->
					</tr>
				</thead>
			</table>																																					
		
			<div id="divButtonChooseMultiDialog" class="ui-sitbutton"  
				   data-buttonType="choose_dialog" 
				   data-auth="<s:property value='ATH.search'/>" 
				   data-funcChooseDialog="chooseSearchGroupDialog">
			</div>
		
		</div>
	</div>
	
</div>
<div class="modal-footer"></div>
