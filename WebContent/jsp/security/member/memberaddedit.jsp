<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<script type="text/javascript">
var password;

function sf() {	
	password = jQuery("#passwordInfo").val();
	
	//initUserGroup() is in [User group] tab
	initUserGroup();

	//initPermissionInfo() is in [Permission Information group] tab
	initPermissionInfo();
	
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
    submitPage("<s:url value='/jsp/security/addMember.action' />");
}

function saveEdit() {
	if(password != jQuery("#passwordInfo").val()) {
		jQuery("#flagResetPassword").val("Y");
	} else {
		//jQuery("#passwordInfo").removeClass("input_password_format");
	}
	
    if (!validateFormInputAll()) {
    	jQuery(".nav-tabs-4 a[href='#tab-information']").tab("show");
		//jQuery("#passwordInfo").addClass("input_password_format");
        return false;
    }

    removeCriteriaPopup();
    submitPage("<s:url value='/jsp/security/editMember.action' />");
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
        action = "<s:url value='/jsp/security/initMember.action' />";
    } else {
        action = "<s:url value='/jsp/security/cancelMember.action' />";
    }
    submitPage(action);
}

function removeCriteriaPopup(){
   	jQuery("#groupUserSearchDialog").remove();
}

</script>
</head>
<body>

<s:form id="addEditViewForm" name="addEditViewForm" method="post" cssClass="form-add" onsubmit="return false;">
	<div id="tabs">
		<ul class="nav nav-tabs nav-tabs-4">
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-information"><s:text name="sec.infoDetail" /></a></li>
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-permission"><s:text name="sec.permissionInfo" /></a></li>
			<li class="nav-item"><a class="nav-link " data-toggle="tab" href="#tab-user"><s:text name="sec.userGroup" /></a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane " id="tab-information">
				<s:include value="/jsp/security/member/include/informationTab.jsp" />
			</div>
			<div class="tab-pane " id="tab-permission">
				<s:include value="/jsp/security/member/include/permissionTab.jsp" />
			</div>
			<div class="tab-pane " id="tab-user">
				<s:include value="/jsp/security/member/include/groupUserTab.jsp" />
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
			
	<s:hidden name="member.memberData.userId" />
	<s:hidden name="criteriaKeyTemp" />
	<s:hidden name="P_CODE"/>
	<s:hidden name="F_CODE"/>
	<s:hidden name="page"/>
	<s:token />
</s:form>

</body>
</html>
