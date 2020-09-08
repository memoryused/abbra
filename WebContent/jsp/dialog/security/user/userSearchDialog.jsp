<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">

	function searchUserDialog(){
		var divid = "tableUserContainer"
			
		jQuery("#"+divid+" input[name='criteria.criteriaKey']").val("");
		jQuery("#tableUserResult").sitDataTable(optionsUserDialog);
	}
	
	function closeSearchUserDialog(){
		jQuery("#userSearchDialog").sitDataTableDialog("close");
	}
	
	function clearSearchUserDialogCallback() {
		$("#criteriaUserContainer").find("[name='criteria.linePerPage']").sitSelectpicker('dropdownlistValue', 3);
		//$("#criteriaGroupContainer").find("[name='criteria.group.active.code']").sitSelectpicker('dropdownlistValue', "");
	}

	function chooseSearchUserDialog() {
		$("#userSearchDialog").sitDataTableDialog("multiChoose");
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
		<div id="criteriaUserContainer" class="div-criteria-modal col-12">
	
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_user_officeCode"><s:text name="sec.user_code" /></label>
							<s:textfield id="criteria_user_officeCode"
								name="criteria.user.officeCode"
								cssClass="form-control clearform" maxlength="80" />
						</div>
					</div>
					
				</div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_user_userName"><s:text name="sec.username" /></label>
							<s:textfield id="criteria_user_userName"
								name="criteria.user.userName"
								cssClass="form-control clearform" maxlength="50" />
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
							<label for="criteria_user_foreName"><s:text name="sec.forename" /></label>
							<s:textfield id="criteria_user_foreName"
								name="criteria.user.foreName"
								cssClass="form-control clearform" maxlength="50" />
						</div>
					</div>
					
				</div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<label for="criteria_user_surName"><s:text name="sec.surname" /></label>
							<s:textfield id="criteria_user_surName"
								name="criteria.user.surName"
								cssClass="form-control clearform" maxlength="50" />
						</div>
						<s:hidden id="criteria_linePerPage" name="criteria.linePerPage"/>
					</div>
					
				</div>
				<div class="col-std"></div>
			</div>
			
		</div>
	</div>
	
	<!------------------------------------- BUTTON ----------------------------------->
	
	<div id="divButtonSearchSingleDialog" class="ui-sitbutton-predefine"  
		data-divCriteriaIdDialog="criteriaUserContainer"  
		data-divTableResultIdDialog="tableUserContainer"
		data-buttonType="search|clear|close" 
		data-auth="<s:property value='ATH.search'/>|<s:property value='ATH.search'/>|true" 
		data-func="searchUserDialog()|clearForm('clearSearchUserDialogCallback','criteriaUserContainer','tableUserContainer' )|closeSearchUserDialog()"  
		data-style="btn btn-fixsize btn-default|btn btn-fixsize btn-outline-secondary|btn btn-fixsize btn-outline-secondary"
		data-container="true" >
	</div>
	<!------------------------------------- BUTTON ------------------------------------->
	
	<div class="row">
		<div id="tableUserContainer" class="col-12 hide-padding" style="display: none;">
			<table id="tableUserResult">
				<thead>
					<tr>
						<th class="col-order">No.</th>
						<th class="col-checkbox"></th>
						<th class="col-2" data-name="officeCode" data-orderable="false"><s:text name="sec.user_code"/></th>
						<th class="col-2" data-name="userName" data-orderable="false"><s:text name="sec.username"/></th>
						<th class="col" data-name="fullName" data-orderable="false"><s:text name="sec.fullname"/></th>
						<th class="col-3" data-name="orgName" data-orderable="false"><s:text name="sec.organization"/></th>
						<th class="col-3" data-name="position" data-orderable="false"><s:text name="sec.position"/></th>
						<th class="col-2 text-center lockActive" data-name="lockStatusId" data-orderable="false"><s:text name="sec.lock_status"/></th>
						<th class="col-2 text-center statusActive" data-name="active.code" data-orderable="false"><s:text name="sec.station_active"/></th>
						
						<!-- <th class="col-status">Status</th> -->
					</tr>
				</thead>
			</table>																																					
		
			<div id="divButtonChooseMultiDialog" class="ui-sitbutton"  
				   data-buttonType="choose_dialog" 
				   data-auth="<s:property value='ATH.search'/>" 
				   data-funcChooseDialog="chooseSearchUserDialog">
			</div>
		
		</div>
	</div>
	
</div>
<div class="modal-footer"></div>
