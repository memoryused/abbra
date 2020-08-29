<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript">
function sf() {	}

function saveAdd() {
	
	if (!validateFormInputAll()) {
        return false;
    }
    
    submitPage("<s:url value='/jsp/master/addVendor.action' />");
}

function saveEdit() {
	
    if (!validateFormInputAll()) {
        return false;
    }
    
    submitPage("<s:url value='/jsp/master/editVendor.action' />");

}

function cancelAdd() {
	submitPageForm();
}

function cancelEdit() {
	submitPageForm();
}

function submitPageForm() {
	
    if (document.getElementsByName('criteriaKeyTemp')[0].value == '') {
        action = "<s:url value='/jsp/master/initVendor.action' />";
    } else {
        action = "<s:url value='/jsp/master/cancelVendor.action' />";
    }
    submitPage(action);
}
</script>
</head>
<body>

<s:form id="addEditViewForm" name="addEditViewForm" method="post" cssClass="form-add" onsubmit="return false;">
	<div class="row">
		<div class="div-criteria">
			<div class="row">
				<div class="col-std"></div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="vendor_vendorCode" name="vendor.vendorCode" cssClass="form-control requireInput clearform"/>
							<label for="vendor_vendorCode"><s:text name="prd.venderCode"/></label>
						</div>
					</div>
				</div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="vendor_vendorName" name="vendor.vendorName" cssClass="form-control requireInput clearform"/>
							<label for="vendor_vendorName"><s:text name="prd.venderName"/></label>
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
							<s:textfield id="vendor_vendorShortName" name="vendor.vendorShortName" cssClass="form-control requireInput clearform"/>
							<label for="vendor_vendorShortName"><s:text name="prd.venderShortName"/></label>
						</div>
					</div>
				</div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:select id="vendor_listStatus"
								list="listStatus"
								name="vendor.status"
								headerKey=""
								headerValue=""
								listKey="key"
								listValue="value" 
								cssClass="form-control requireInput clearform" />
							<label for="vendor_listStatus"><s:text name="prd.status"></s:text></label>
						</div>
					</div>
				</div>
				<div class="col-std"></div>
			</div>
			
			<%--———————————————————————————————————————————————————————————————————————
				BUTTON
			————————————————————————————————————————————————————————————————————————--%>
			<s:if test="page.getPage() == 'add'">
				<div id="divButtonAdd" class="ui-sitbutton"  
					data-buttonType="add" 
					data-auth="<s:property value='ATH.add'/>"  >
				</div>
			</s:if>
			<s:elseif test="page.getPage() == 'edit'">
				<div id="divButtonAdd" class="ui-sitbutton"  
					data-buttonType="edit" 
					data-auth="<s:property value='ATH.edit'/>"  >
				</div>
			</s:elseif>
			
			<s:hidden name="vendor.vendorId" />
			<s:hidden name="criteriaKeyTemp" />
			<s:hidden name="P_CODE"/>
			<s:hidden name="F_CODE"/>
			<s:hidden name="page"/>
			<s:token />
		</div>
	</div>
</s:form>

</body>
</html>
