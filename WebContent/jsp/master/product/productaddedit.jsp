<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript">
function sf() {	
	initVendor();
	
	jQuery(".nav-tabs-4 a[href='#tab-information']").tab("show");
	initInformationDetail();
}

function checkValidAdd(){
	if (!validateFormInputAll()) {
		jQuery(".nav-tabs-4 a[href='#tab-information']").tab("show");
        return false;
    }
}

function saveAdd() {
	removeCriteriaPopup();
    submitPage("<s:url value='/jsp/master/addProduct.action' />");
}

function saveEdit() {
	removeCriteriaPopup();
    submitPage("<s:url value='/jsp/master/editProduct.action' />");
}

function cancelAdd() {
	submitPageForm();
}

function cancelEdit() {
	submitPageForm();
}

function submitPageForm() {
	removeCriteriaPopup();
    if (document.getElementsByName('criteriaKeyTemp')[0].value == '') {
        action = "<s:url value='/jsp/master/initProduct.action' />";
    } else {
        action = "<s:url value='/jsp/master/cancelProduct.action' />";
    }
    submitPage(action);
}

function removeCriteriaPopup(){
   	jQuery("#vendorSearchDialog").remove();
}
</script>
</head>
<body>

<s:form id="addEditViewForm" name="addEditViewForm" method="post" cssClass="form-add" onsubmit="return false;">
	<div id="tabs">
		<ul class="nav nav-tabs nav-tabs-4">
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-information"><s:text name="sec.infoDetail" /></a></li>
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-vendor"><s:text name="prd.vender" /></a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane " id="tab-information">
				<s:include value="/jsp/master/product/include/informationTab.jsp" />
			</div>
			<div class="tab-pane " id="tab-vendor">
				<s:include value="/jsp/master/product/include/vendorTab.jsp" />
			</div>
		</div>
	</div>
	
	<br>


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
	
	<s:hidden name="item.itemId" />
	<s:hidden name="criteriaKeyTemp" />
	<s:hidden name="P_CODE"/>
	<s:hidden name="F_CODE"/>
	<s:hidden name="page"/>
	<s:token />
</s:form>

</body>
</html>
