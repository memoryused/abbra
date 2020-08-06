<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript">

function sf(){}

jQuery(document).ready(function(){
	
	jQuery("#icon-delete").click(function(e){
		updateDeleted("Y","<s:text name='50005'/>");
	});
	
	jQuery("#icon-active").click(function(e){
		updateActiveStatus("Y","<s:text name='50001'/>");
	});
	
	jQuery("#icon-inactive").click(function(e){
		updateActiveStatus("N","<s:text name='50002'/>");
	});
	
	if("<s:property value='criteriaKeyTemp' />" != ""){
		searchPage();
	}
});

var dtOptions = {
	criteriaContainerId: "criteriaContainer",
	criteriaKeyTmp: "criteriaKeyTemp",
	criteriaOnSession : '<s:property value="#session[criteriaKeyTemp].toJson()" escapeHtml="false" />',
	urlSearch: "<s:url value='/jsp/product/searchProductInfo.action' />",
	urlInit: "<s:url value='/jsp/product/searchProductInfo.action' />",
	pk: "venderItemId",
	createdRowCallback: function(row, data) {
		manageRow(row, data);
	},
	pageJump: true
}

function searchPage() {
	jQuery("#tableResult").sitDataTable(dtOptions);
}

function clearPage() {
    submitPage("<s:url value='/jsp/product/initProductInfo.action' />");
}

function manageRow(row, data){
	//Bind event dblclick to row
	jQuery(row).on("dblclick", function(){
		jQuery("input[name='productInfo.venderItemId']").val(data.venderItemId);
		submitPage("<s:url value='/jsp/product/gotoEditProductInfo.action' />");
	});
	
	jQuery(".status", row).html(data.status === 'Y'? "Active":"Inactive");
}

function updateDeleted(status, msg) {
	var action = "<s:url value='/jsp/product/changeDeleteProductInfo.action' />";
	updateStatus(status, msg, action);
}

function updateActiveStatus(status, msg) {
	var action = "<s:url value='/jsp/product/changeActiveProductInfo.action' />";
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
</script>
</head>
<body>
<s:form id="searchForm" name="searchForm" method="post" namespace="/jsp/product" action="initProductInfo" cssClass="form-search" onsubmit="return false;">
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
							<s:select id="criteria_productId_autocomplete"
								list="listProduct"
								headerKey=""
								headerValue=""
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<s:hidden id="criteria_productId" name="criteria.productId" data-code-of="criteria_productId_autocomplete" cssClass="form-control autocomplete clearform"/>
							<s:hidden id="criteria_productName" name="criteria.productName" data-text-of="criteria_productId_autocomplete"/>
							<label for="criteria_productId_autocomplete"><s:text name="prd.productName"></s:text></label>
						</div>
					</div>
					
				</div>
				<div class="col-std-2">
					
					<div class="col-md-12">
						<div class="md-form">
							<s:select id="criteria_venderId_autocomplete"
								list="listVender"
								headerKey=""
								headerValue=""
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<s:hidden id="criteria_venderId" name="criteria.venderId" data-code-of="criteria_venderId_autocomplete" cssClass="form-control autocomplete clearform"/>
							<s:hidden id="criteria_venderName" name="criteria.venderName" data-text-of="criteria_venderId_autocomplete"/>
							<label for="criteria_venderId_autocomplete"><s:text name="prd.venderShortName"></s:text></label>
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
				<a href="#" id="icon-delete"><i class="fa fa-minus-square-o fa-3 red-text" aria-hidden="true"></i><s:text name='delete' /></a>&nbsp;&nbsp;
				<a href="#" id="icon-inactive"><i class="fa fa-check-square-o fa-3 yellow-text" aria-hidden="true"></i><s:text name='inactive' /></a>
				<a href="#" id="icon-active"><i class="fa fa-check-square-o fa-3 green-text" aria-hidden="true"></i><s:text name='active' /></a>
			</div>
	
			<table id="tableResult">
				<thead>
					<tr>
						<th class="col-order" data-orderable="false"><s:text name="no"/></th>
						<th class="col-checkbox"></th>
						<th data-name="productName"><s:text name="prd.productName"/></th>
						<th data-name="venderShortName"><s:text name="prd.venderShortName"/></th>
						<th class="status" data-name="status"><s:text name="prd.status"/></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<s:hidden name="productInfo.venderItemId"/>
	<s:token/>
</s:form>
</body>
</html>