<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">

function initUsers() {
	
	jQuery("#datatable_divUserDialogResult tbody tr").each(function () {
		
		if(jQuery(this).find("td.checkbox input[name$='deleteFlag']") != 'Y') {
			var desc = jQuery(this).find("td.checkbox input[name$='active.desc']").val()
			var status = jQuery(this).find("td.checkbox input[name$='active.code']").val()
			jQuery(this).find("td[class*='active.code']").html(getIconActiveStatus(status, desc));
			
			var desc2 = jQuery(this).find("td.checkbox input[name$='lockStatusName']").val()
			var status2 = jQuery(this).find("td.checkbox input[name$='lockStatus']").val()
			jQuery(this).find("td[class*='lockStatus']").html(getIconLockStatus(status2, desc2));
		}
	});

	// สั่งให้ขยาย เนื่องจาก header column ไม่ขยายตามขนาดที่กำหนดไว้เพราะ tab ถูกซ่อนไว้อยู่ตอน initial หน้าจอ
	jQuery(document).on('show.bs.tab', 'a[href="#tab-user"]', function (e) {
		jQuery("#tab-user table.table").resize();

	});
	
	jQuery(document).on('hidden.bs.modal', 'div#userSearchDialog', function (e) {
		// do something...
		// Destroys an element’s modal.
		jQuery(this).sitDataTableDialog('dispose');
	});
	
	jQuery(document).on('shown.bs.tab', 'a[href="#tab-user"]', function (e) {
		//3. set ids to idsSelectedRow
		var valueIds;
		jQuery("#datatable_divUserDialogResult tbody tr:visible").each(function(index){
			var that = this;
			var hiddenId = jQuery("input[type='checkbox']", that).val();
			if(index == 0){
				valueIds = hiddenId;
			}else{
				valueIds = valueIds + "," + hiddenId;
			}
		});
		jQuery('#divUserDialogResult_idsSelectedRow').val(valueIds);
		
	});
	
	//setDisplay1600('divGroupDialogResult');
}

var optionsUserDialog = {
		criteriaContainerId: "criteriaUserContainer",
		table: "tableUserResult",
		urlSearch:  "<s:url value='/jsp/dialog/searchUserDialog.action' />",
		urlInit:  "<s:url value='/jsp/dialog/initUserDialog.action' />",
		urlSearchByIds:  "<s:url value='/jsp/dialog/searchByIdsUserDialog.action' />",
		
		dialogType: 'multi',
		excludeIds: 'divUserDialogResult_idsSelectedRow',
		chosenCallback: chooseUserDialog,
		showDialogCallback: function(data) {
			
		},
		createDialogCallback: function(data){
		
			jQuery("select#criteria_user_active_code.select-modal").sitSelectpicker('addListOption',data.listActiveStatus);

		},createdRowCallback: manageRowCallback
	};

function manageRowCallback(row, data) {
	
	var icon, icon2;

	if(data.active.code == 'Y') {// text-gray-dark
		icon = "<span class='text-success' data-toggle='tooltip' title='"+data.active.desc+"'><i class='fa fa-check'></i></span>";// <img src='<s:url value='/images/icon/i_open.png' />' title='"+ title +"'>
	} else {
		icon = "<span class='text-danger' data-toggle='tooltip' title='"+data.active.desc+"'><i class='fa fa-close'></i></span>";
	}
	if(data.lockStatusId == '1') {
		icon2 = "<span class='text-success' data-toggle='tooltip' title='"+data.lockStatusName+"'><i class='fa fa-unlock'></i></span>";
	} else {
		icon2 = "<span class='text-danger' data-toggle='tooltip' title='"+data.lockStatusName+"'><i class='fa fa-lock'></i></span>";
	}
	
	jQuery(row).find('.statusActive').html(icon);
	jQuery(row).find('.lockActive').html(icon2);
	
	
	
}
function openUserModal(){	
	jQuery("#userSearchDialog").sitDataTableDialog(optionsUserDialog);
}

function chooseUserDialog(json) {
		console.log(json);
		//1. วาด table เองเพราะ data ที่ได้ใน table จะไม่ remove จริง จะใช้แค่ flag บอกสถานะเท่านั้น
		var tblId = "datatable_divUserDialogResult";
		var strId ="";
	
		// หาค่า index  ก่อนหน้า
		var idx = jQuery('table#' + tblId + ' tbody tr').length;
		
		if (jQuery("[name='page']").val() == 'ADD'){
			// Clear datatable
			jQuery('table#' + tblId).DataTable().clear().draw(false);
			idx = 0;
			
			//Clear selectrd ID
			jQuery('#divUserDialogResult_idsSelectedRow').val("");
		} else{
		}
		
		for (var rowIndex = 0; rowIndex < json.length; rowIndex++) {
			// convert json string to json object
			var obj = json[rowIndex];
			
			var index = idx;
			var elmArr = new Array();
			
			//checkbox & hidden value column
			elmArr[0] = "<input type='checkbox' id='dt-checkbox-divUserDialogResult-"+idx+"' value='"+obj.id+"' />" ;
			
			//elmArr[0] = "<input type='checkbox' id='cnkColumnId' name='cnkColumnId' value='"+ obj.id +"' />";
			elmArr[0] += "<input type='hidden' name='data.listUser["+index+"].id' value='"+ obj.id +"' />";
			elmArr[0] += "<input type='hidden' name='data.listUser["+index+"].deleteFlag' />";
			elmArr[0] += "<input type='hidden' name='data.listUser["+index+"].active.code' value='"+ obj.active.code +"' />";
			elmArr[0] += "<input type='hidden' name='data.listUser["+index+"].lockStatus' value='"+ obj.lockStatusId +"' />";
			
			elmArr[1] = getIconLockStatus(obj.lockStatusId, obj.lockStatusName);
			elmArr[2] = getIconActiveStatus(obj.active.code, obj.active.desc);
			elmArr[3] = obj.officeCode + "<input type='hidden' name='data.listUser["+index+"].userCode' value='"+ obj.officeCode +"'/>" ;
			elmArr[4] = obj.userName + "<input type='hidden' name='data.listUser["+index+"].username' value='"+ obj.userName +"'/>" ;
			elmArr[5] = obj.fullName + "<input type='hidden' name='data.listUser["+index+"].fullName' value='"+ obj.fullName +"'/>" ;
			elmArr[6] = obj.orgName + "<input type='hidden' name='data.listUser["+index+"].organizationName' value='"+ obj.orgName +"'/>" ;
			elmArr[7] = obj.position + "<input type='hidden' name='data.listUser["+index+"].positionName' value='"+ obj.position +"'/>" ;
	
			//2. create row	
			tableCreateTableRow(tblId, elmArr);
	
			if(strId == ""){
				strId = obj.id;
			} else {
				strId += ","+obj.id;
			}
			idx++;
		}
		
		//Remove class checkbox
		jQuery('table#'+ tblId +' tbody tr td.col-checkbox').each(function(i) {
			jQuery(this).removeClass('checkbox'); 
		});
}
	
function getIconLockStatus(status, title) {
	
	var icon;
	
	if(status == '1') {// text-gray-dark
		icon = "<span class='text-success' data-toggle='tooltip' title='"+title+"'><i class='fa fa-unlock'></i></span>";
	} else {
		icon = "<span class='text-danger' data-toggle='tooltip' title='"+title+"'><i class='fa fa-lock'></i></span>";
	}
	
	return icon;
}

function getIconActiveStatus(status, title) {
	
	var icon;
	
	if(status == 'Y') {// text-gray-dark
		icon = "<span class='text-success' data-toggle='tooltip' title='"+title+"'><i class='fa fa-check'></i></span>";
	} else {
		icon = "<span class='text-danger' data-toggle='tooltip' title='"+title+"'><i class='fa fa-close'></i></span>";
	}
	
	return icon;
}

</script>

<!-- div สำหรับแสดงผล  -->
<div id="divUserDialogResult"></div>
<s:set var="divresult" value='{"divUserDialogResult"}'/> 
<s:set var="columnName" value='{"","",getText("sec.user_code"),getText("sec.username"),getText("sec.fullName"),getText("sec.organization"),getText("sec.position")}'/>
<s:set var="listTableData" value='%{"data.listUser"}'/>
<s:set var="columnID" value='{"id"}'/> <!-- PK -->
<s:set var="columnData" value='{"lockStatus","active.code","userCode","username","fullname","organizationName","positionName"}'/>
<s:set var="columnCSSClass" value='{"col-1 text-center","col-1 text-center","col-1","col-2","col-5","col-2","col"}'/>
<s:set var="hiddenData" value='{"lockStatusName","active.code","lockStatus","active.desc","userCode","username","fullname","organizationName","positionName"}'/> <!-- domain properties สำหรับดึงข้อมูลมาเก็บเป็น hidden -->
<s:set var="criteriaName" value='%{"criteriaPopup"}'/>
<s:include value="/bootstrap/jsp/template/tableAddDeleteRow.jsp"/>

<script type="text/javascript">

jQuery("#datatable_<s:property value='#divresult[0]'/>_wrapper .row:first > div:nth-child(1)").append("<s:text name = 'sec.users' />");

<s:if test="ATH.add || ATH.edit">
	var iconPopup = "<a  href='javascript:void(0)' onclick='openUserModal()'><i class='fa fa-plus-circle fa-lg' style='color:green'></i>&nbsp;<s:text name = 'sec.add' /></a>";
	jQuery("#datatable_<s:property value='#divresult[0]'/>_wrapper .row:first > div:nth-child(2)").addClass("text-right").append(iconPopup);
</s:if>
	
</script>
<div id="userSearchDialog" class="modal" data-modal-header='<s:text name="sec.popupUser" />'>
	<s:include value="/jsp/dialog/security/user/userSearchDialog.jsp"/>
</div>
