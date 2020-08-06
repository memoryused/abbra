<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript">

function sf(){}

jQuery(document).ready(function(){
	if("<s:property value='criteriaKeyTemp' />" != ""){
		searchPage();
	}
});
	
var dtOptions = {
	criteriaContainerId: "criteriaContainer",
	criteriaKeyTmp: "criteriaKeyTemp",
	criteriaOnSession : '<s:property value="#session[criteriaKeyTemp].toJson()" escapeHtml="false" />',
	urlSearch: "<s:url value='/jsp/product/searchProductHome.action' />",
	urlInit: "<s:url value='/jsp/product/searchProductHome.action' />",
	pk: "docTranId",
	createdRowCallback: function(row, data) {
		manageRow(row, data);
	},
	pageJump: true
}

function searchPage() {
	jQuery("#tableResult").sitDataTable(dtOptions);
}

function clearPage() {
    submitPage("<s:url value='/jsp/product/initProductHome.action' />");
}

function manageRow(row, data){
	if(data.pdfPath === "Y"){
		var url = "<s:url value='/DownloadCertificateServlet'/>?refId=" + data.docTranId;
		jQuery(".pdfPath", row).html("<a href='"+ url +"'><i class='fa fa-file-pdf-o' aria-hidden='true'></i></a>");		
	} else {
		jQuery(".pdfPath", row).html("<i class='fa fa-file-pdf-o' aria-hidden='true'></i>");		
	}
}

</script>
</head>
<body>
<s:form id="searchForm" name="searchForm" method="post" namespace="/jsp/product" action="initProductHome" cssClass="form-search" onsubmit="return false;">
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
							<s:hidden id="criteria_productName" data-text-of="criteria_productId_autocomplete"/>
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
							<s:hidden id="criteria_venderName" data-text-of="criteria_venderId_autocomplete"/>
							<label for="criteria_venderId_autocomplete"><s:text name="prd.venderName"></s:text></label>
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
							<s:select id="listDocumentType"
								list="listDocumentType"
								name="criteria.documentTypeId"
								headerKey=""
								headerValue="%{getText('all')}"
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<label for="listDocumentType"><s:text name="prd.documentType"></s:text></label>
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
			<table id="tableResult">
				<thead>
					<tr>
						<th class="col-order" data-orderable="false"><s:text name="no"/></th>
						<th data-name="productName"><s:text name="prd.productName"/></th>
						<th data-name="venderName"><s:text name="prd.venderName"/></th>
						<th data-name="documentType" data-orderable="false"><s:text name="prd.documentType"/></th>
						<th data-name="standard" data-orderable="false"><s:text name="prd.standard"/></th>
						<th data-name="expireDate"><s:text name="prd.expireDate"/></th>
						<th class="pdfPath text-center" data-orderable="false"><s:text name="prd.pdf"/></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	
	<s:token/>
</s:form>	
</body>
</html>