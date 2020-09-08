<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">
function sf() {	
	initInformationDetail();
	initPermissionInfo();
	initUsers();
	
	jQuery(".nav-tabs-4 a[href='#tab-information']").tab("show");
}

function checkValidAdd(){
	if (!validateFormInputAll()) {
		jQuery(".nav-tabs-4 a[href='#tab-information']").tab("show");
        return false;
    }
}

function saveAdd() {
	removeCriteriaPopup();
    submitPage("<s:url value='/jsp/security/addMemberGroup.action' />");
}

function saveEdit() {
    if (!validateFormInputAll()) {
    	jQuery(".nav-tabs-4 a[href='#tab-information']").tab("show");
        return false;
    }

    removeCriteriaPopup();
    submitPage("<s:url value='/jsp/security/editMemberGroup.action' />");
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
        action = "<s:url value='/jsp/security/initMemberGroup.action' />";
    } else {
        action = "<s:url value='/jsp/security/cancelMemberGroup.action' />";
    }
    submitPage(action);
}

function removeCriteriaPopup(){
   	jQuery("#userSearchDialog").remove();
}

</script>
</head>
<body>
<s:form id="addEditViewForm" name="addEditViewForm" method="post" cssClass="margin-zero" onsubmit="return false;">
	<div id="tabs">
		<ul class="nav nav-tabs nav-tabs-4">
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-information"><s:text name="sec.infoDetail" /></a></li>
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-permission"><s:text name="sec.permissionInfo" /></a></li>
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-user"><s:text name="sec.users" /></a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane " id="tab-information">
				<s:include value="/jsp/security/membergroup/include/informationTab.jsp" />
			</div>
			<div class="tab-pane " id="tab-permission">
				<s:include value="/jsp/security/membergroup/include/permissionTab.jsp" />
			</div>
			<div class="tab-pane " id="tab-user">
				<s:include value="/jsp/security/membergroup/include/userTab.jsp" />
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
			
	<s:hidden name="data.group.id" />
	<s:hidden name="criteriaKeyTemp" />
	<s:hidden name="P_CODE"/>
	<s:hidden name="F_CODE"/>
	<s:hidden name="page"/>
	<s:token />
</s:form>

</body>
</html>