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
	urlSearch: "<s:url value='/jsp/master/searchProduct.action' />",
	urlInit: "<s:url value='/jsp/master/searchProduct.action' />",
	pk: "itemId",
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
		editPage(data.itemId);
	});
	

	jQuery(".status", row).html(data.status === 'Y'? "Active":"Inactive");
}

function clearPage() {
    submitPage("<s:url value='/jsp/master/initProduct.action' />");
}

function updateActiveStatus(status, msg) {
	var action = "<s:url value='/jsp/master/changeActiveProduct.action' />";
	updateStatus(status, msg, action);
}

function updateDeleted(status, msg) {
	var action = "<s:url value='/jsp/master/changeDeleteProduct.action' />";
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
	submitPage("<s:url value='/jsp/master/gotoAddProduct.action' />");
}

function editPage(selectedId){
	jQuery("[name='item.itemId']").val(selectedId);
	submitPage("<s:url value='/jsp/master/gotoEditProduct.action' />");
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
							<s:select id="criteria_itemId_autocomplete"
								list="listProduct"
								headerKey=""
								headerValue=""
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<s:hidden id="criteria_itemId" name="criteria.itemId" data-code-of="criteria_itemId_autocomplete" cssClass="form-control autocomplete clearform"/>
							<s:hidden id="criteria_itemShortName" name="criteria.itemShortName" data-text-of="criteria_itemId_autocomplete"/>
							<label for="criteria_itemId_autocomplete"><s:text name="prd.productName"></s:text></label>
						</div>
					</div>
				
				</div>
				<div class="col-std-2">
				
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="criteria_itemCode" name="criteria.itemCode" cssClass="form-control clearform"/>
							<label for="criteria_itemCode"><s:text name='prd.productCode'/></label> 
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
						<th data-name="itemCode"><s:text name="prd.productCode"/></th>
						<th data-name="itemShortName"><s:text name="prd.productName"/></th>
						<th class="status" data-name="status"><s:text name="prd.status"/></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<s:hidden name="item.itemId"/>
	<s:token/>
	
</s:form>
</body>
</html>