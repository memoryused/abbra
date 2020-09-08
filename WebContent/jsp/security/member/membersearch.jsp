<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript">
	
function sf(){}

jQuery(document).ready(function(){
	jQuery("#icon-add").click(function(e){
		addPage();
	});
	
	jQuery("#icon-active").click(function(e){
		updateActiveStatus("Y","<s:text name='50001'/>");
	});
	
	jQuery("#icon-inactive").click(function(e){
		updateActiveStatus("N","<s:text name='50002'/>");
	});
	
	jQuery("#icon-delete").click(function(e){
		updateDeleted("Y","<s:text name='50005'/>");
	});
	
	if(("<s:property value='criteriaKeyTemp' />" != "")){
		searchPage();
	}
	
	jQuery("#btnCancel").click(function(e){
		clearForm();
	});
});

var dtOptions = {
	criteriaContainerId: "criteriaContainer",
	criteriaKeyTmp: "criteriaKeyTemp",
	criteriaOnSession : '<s:property value="#session[criteriaKeyTemp].toJson()" escapeHtml="false" />',
	urlSearch: "<s:url value='/jsp/security/searchMember.action' />",
	urlInit: "<s:url value='/jsp/security/searchMember.action' />",
	pk: "userId",
	createdRowCallback: function(row, data) {
		manageRow(row, data);
	},
	pageJump: true
}

function searchPage() {
	jQuery("#tableResult").sitDataTable(dtOptions);
}

function manageRow(row, data){

	//Bind event dblclick to row
	jQuery(row).on("dblclick", function(){
		editPage(data.userId);
	});
	

	jQuery(".status", row).html(data.status === 'Y'? "Active":"Inactive");
	jQuery(".lockStatus", row).html(data.lockStatus === '1'? "Ready":"Locked");
}

function clearPage() {
    submitPage("<s:url value='/jsp/security/initMember.action' />");
}

function updateActiveStatus(status, msg) {
	var action = "<s:url value='/jsp/security/changeActiveMember.action' />";
	updateStatus(status, msg, action);
}

function updateDeleted(status, msg) {
	var action = "<s:url value='/jsp/security/changeDeleteMember.action' />";
	updateStatus(status, msg, action);
}

function updateStatus(status, confirmMsg, action) {
	
	if(!jQuery("#tableResult").sitDataTable('hasSelectedIds')) {
		alert(validateMessage.CODE_10001);
		return false
	}

	if(confirm(confirmMsg)) {
		var extraData = { }
		jQuery("#tableResult").sitDataTable('updateStatus', action, status, extraData)
	}
}

function addPage(){
	submitPage("<s:url value='/jsp/security/gotoAddMember.action' />");
}

function editPage(selectedId){
	jQuery("[name='member.memberData.userId']").val(selectedId);
	submitPage("<s:url value='/jsp/security/gotoEditMember.action' />");
}

</script>
</head>
<body>
<s:form id="searchForm" name="searchForm" method="post" cssClass="form-search" onsubmit="return false;">
	<%-- ———————————————————————————————————————————————————————————————————————
		CRITERIA
	———————————————————————————————————————————————————————————————————————— --%>		
	<div class="row">
		<div id="criteriaContainer" class="div-criteria">
			<div class="row">
				<div class="col-std"></div>	
				<div class="col-std-2">
				
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="criteria_userCode" name="criteria.userCode" cssClass="form-control clearform"/>
							<label for="criteria_userCode"><s:text name='sec.user_code'/></label> 
						</div>
					</div>
				
				</div>
				<div class="col-std-2">
				
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="criteria_username" name="criteria.username" cssClass="form-control clearform"/>
							<label for="criteria_username"><s:text name='sec.username'/></label> 
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
							<s:textfield id="criteria_forename" name="criteria.forename" cssClass="form-control clearform"/>
							<label for="criteria_forename"><s:text name='sec.forename'/></label> 
						</div>
					</div>
				
				</div>
				<div class="col-std-2">
				
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="criteria_surname" name="criteria.surname" cssClass="form-control clearform"/>
							<label for="criteria_surname"><s:text name='sec.surname'/></label> 
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
							<s:select id="listOrganization"
								list="listOrganization"
								name="criteria.organizationId"
								headerKey=""
								headerValue="%{getText('all')}"
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<label for="listOrganization"><s:text name="sec.organization"></s:text></label>
						</div>
					</div>
				
				</div>
				<div class="col-std-2">
				
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="criteria_positionName" name="criteria.positionName" cssClass="form-control clearform"/>
							<label for="criteria_positionName"><s:text name='sec.position'/></label>
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
							<s:select id="listLockStatus"
								list="listLockStatus"
								name="criteria.lockStatus"
								headerKey=""
								headerValue="%{getText('all')}"
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<label for="listLockStatus"><s:text name="sec.lock_status"></s:text></label>
						</div>
					</div>
				
				</div>
				<div class="col-std-2">
				
					<div class="col-md-12">
						<div class="md-form">
							<s:select id="listStatus"
								list="listStatus"
								name="criteria.status"
								headerKey=""
								headerValue="%{getText('all')}"
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<label for="listStatus"><s:text name="prd.status"></s:text></label>
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
							<s:select id="criteria_linePerPage" 
								cssClass="lpp-style form-control clearform"
								name="criteria.linePerPage" 
								list="LPP" 
							/>
							<label for="criteria_linePerPage"><s:text name="recordsPerPage"></s:text></label>
						</div>
					</div>
				
				</div>
				<div class="col-std-2"></div>
				<div class="col-std"></div>
			</div>
			
			<%--———————————————————————————————————————————————————————————————————————
				BUTTON
			————————————————————————————————————————————————————————————————————————--%>
			<div id="divButtonSearch" class="ui-sitbutton"  
				data-divCriteriaId="criteriaContainer"  
				data-divTableResultId="tableContainer"
			   	data-buttonType="search" 
			   	data-auth="<s:property value='ATH.search'/>" 
			   	data-funcSearch="searchPage" >
			</div>
			
		</div>
	</div>
	
	<%--———————————————————————————————————————————————————————————————————————
		TABLE
	————————————————————————————————————————————————————————————————————————--%>
	<div class="row">
		<div id="tableContainer" class="col-12" style="display: none;">
			<div class="col-std div-icon">
				<a href="javascript:void(0);" id="icon-add"><i class="fa fa-plus-square-o fa-3 info-text" aria-hidden="true"></i>Add</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" id="icon-delete"><i class="fa fa-minus-square-o fa-3 red-text" aria-hidden="true"></i>Delete</a>
				<a href="javascript:void(0);" id="icon-inactive"><i class="fa fa-check-square-o fa-3 yellow-text" aria-hidden="true"></i><s:text name='inactive' /></a>
				<a href="javascript:void(0);" id="icon-active"><i class="fa fa-check-square-o fa-3 green-text" aria-hidden="true"></i><s:text name='active' /></a>
			</div>
			<table id="tableResult">
				<thead>
					<tr>
						<th class="col-order" data-orderable="false"><s:text name="no"/></th>
						<th class="col-checkbox"></th>
						<th data-name="userCode"><s:text name="sec.user_code"/></th>
						<th data-name="username"><s:text name="sec.username"/></th>
						<th data-name="fullname"><s:text name="sec.fullName"/></th>
						<th data-name="organizationName"><s:text name="sec.organization"/></th>
						<th data-name="positionName"><s:text name="sec.position"/></th>
						<th class="lockStatus" data-name="lockStatus"><s:text name="sec.lock_status"/></th>
						<th class="status" data-name="status"><s:text name="prd.status"/></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<s:hidden name="member.memberData.userId"/>
	<s:token/>
	
</s:form>
</body>
</html>