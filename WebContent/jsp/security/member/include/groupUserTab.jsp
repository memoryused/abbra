<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">

function initUserGroup() {
	
	jQuery("#datatable_divGroupDialogResult tbody tr").each(function () {
		
		if(jQuery(this).find("td.checkbox input[name$='deleteFlag']") != 'Y') {
			var desc = jQuery(this).find("td.checkbox input[name$='active.desc']").val()
			var status = jQuery(this).find("td.checkbox input[name$='active.code']").val()
			jQuery(this).find("td[class*='active.code']").html(getIconActiveStatus(status, desc));
		}
	});

	// สั่งให้ขยาย เนื่องจาก header column ไม่ขยายตามขนาดที่กำหนดไว้เพราะ tab ถูกซ่อนไว้อยู่ตอน initial หน้าจอ
	jQuery(document).on('show.bs.tab', 'a[href="#tab-user"]', function (e) {
		jQuery("#tab-user table.table").resize();

	});
	
	jQuery(document).on('hidden.bs.modal', 'div#groupUserSearchDialog', function (e) {
		// do something...
		// Destroys an element’s modal.
		jQuery(this).sitDataTableDialog('dispose');
	});
	
	jQuery(document).on('shown.bs.tab', 'a[href="#tab-user"]', function (e) {
		//3. set ids to idsSelectedRow
		var valueIds;
		jQuery("#datatable_divGroupDialogResult tbody tr:visible").each(function(index){
			var that = this;
			var hiddenId = jQuery("input[type='checkbox']", that).val();
			if(index == 0){
				valueIds = hiddenId;
			}else{
				valueIds = valueIds + "," + hiddenId;
			}
		});
		jQuery('#divGroupDialogResult_idsSelectedRow').val(valueIds);
		
	});
	
	//setDisplay1600('divGroupDialogResult');
}

var optionsGroupDialog = {
		criteriaContainerId: "criteriaGroupContainer",
		table: "tableGroupResult",
		urlSearch:  "<s:url value='/jsp/dialog/searchGroupDialog.action' />",
		urlInit:  "<s:url value='/jsp/dialog/initGroupDialog.action' />",
		urlSearchByIds:  "<s:url value='/jsp/dialog/searchByIdsGroupDialog.action' />",
		
		dialogType: 'multi',
		excludeIds: 'divGroupDialogResult_idsSelectedRow',
		chosenCallback: chooseGroupDialog,
		showDialogCallback: function(data) {
			
		},
		createDialogCallback: function(data){
		
			jQuery("select#criteria_group_active_code.select-modal").sitSelectpicker('addListOption',data.listActiveStatus);

		},createdRowCallback: manageRowCallback
	};

function manageRowCallback(row, data) {
	var icon;
	
	if(data.active.code == 'Y') {// text-gray-dark
		icon = "<span class='text-success' data-toggle='tooltip' title='"+data.active.desc+"'><i class='fa fa-check'></i></span>";// <img src='<s:url value='/images/icon/i_open.png' />' title='"+ title +"'>
	} else {
		icon = "<span class='text-danger' data-toggle='tooltip' title='"+data.active.desc+"'><i class='fa fa-close'></i></span>";
	}
	
	jQuery(row).find('.statusActive').html(icon);
}
function openGroupModal(){	
	jQuery("#groupUserSearchDialog").sitDataTableDialog(optionsGroupDialog);
}

function chooseGroupDialog(json) {
	
		//1. วาด table เองเพราะ data ที่ได้ใน table จะไม่ remove จริง จะใช้แค่ flag บอกสถานะเท่านั้น
		var tblId = "datatable_divGroupDialogResult";
		var strId ="";
	
		// หาค่า index  ก่อนหน้า
		var idx = jQuery('table#' + tblId + ' tbody tr').length;
		
		if (jQuery("[name='page']").val() == 'ADD'){
			// Clear datatable
			jQuery('table#' + tblId).DataTable().clear().draw(false);
			idx = 0;
			
			//Clear selectrd ID
			jQuery('#divGroupDialogResult_idsSelectedRow').val("");
		} else{
		}
		
		for (var rowIndex = 0; rowIndex < json.length; rowIndex++) {
			// convert json string to json object
			var obj = json[rowIndex];
			
			var index = idx;
			var elmArr = new Array();
			
			//checkbox & hidden value column
			elmArr[0] = "<input type='checkbox' id='dt-checkbox-divGroupDialogResult-"+idx+"' value='"+obj.id+"' />" ;
			
			//elmArr[0] = "<input type='checkbox' id='cnkColumnId' name='cnkColumnId' value='"+ obj.id +"' />";
			elmArr[0] += "<input type='hidden' name='member.listGroup["+index+"].id' value='"+ obj.id +"' />";
			elmArr[0] += "<input type='hidden' name='member.listGroup["+index+"].deleteFlag' />";
			elmArr[0] += "<input type='hidden' name='member.listGroup["+index+"].active.code' value='"+ obj.active.code +"' />";
			elmArr[0] += "<input type='hidden' name='member.listGroup["+index+"].active.desc' value='"+ obj.active.desc +"' />";
			
			elmArr[1] = getIconActiveStatus(obj.active.code, obj.active.desc);
			elmArr[2] = obj.groupCode + "<input type='hidden' name='memberData.listGroup["+index+"].groupCode' value='"+ obj.groupCode +"'/>" ;
			elmArr[3] = obj.groupName + "<input type='hidden' name='memberData.listGroup["+index+"].groupName' value='"+ obj.groupName +"'/>" ;
	
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
	
function getIconActiveStatus(status, title) {
	
	var icon;
	
	if(status == 'Y') {// text-gray-dark
		icon = "<span class='text-success' data-toggle='tooltip' title='"+title+"'><i class='fa fa-check'></i></span>";// <img src='<s:url value='/images/icon/i_open.png' />' title='"+ title +"'>
	} else {
		icon = "<span class='text-danger' data-toggle='tooltip' title='"+title+"'><i class='fa fa-close'></i></span>";
	}
	
	return icon;
}

</script>

<!-- div สำหรับแสดงผล  -->
<div id="divGroupDialogResult"></div>
<s:set var="divresult" value='{"divGroupDialogResult"}'/> 
<s:set var="columnName" value='{getText("sec.active_status"),getText("sec.group_code"),getText("sec.group_name")}'/>
<s:set var="listTableData" value='%{"member.listGroup"}'/>
<s:set var="columnID" value='{"id"}'/> <!-- PK -->
<s:set var="columnData" value='{"active.code","groupCode","groupName"}'/>
<s:set var="columnCSSClass" value='{"col-1 text-center","col-5", "col"}'/>
<s:set var="hiddenData" value='{"active.code","active.desc","groupCode","groupName"}'/> <!-- domain properties สำหรับดึงข้อมูลมาเก็บเป็น hidden -->
<s:set var="criteriaName" value='%{"criteriaPopup"}'/>
<s:include value="/bootstrap/jsp/template/tableAddDeleteRow.jsp"/>

<script type="text/javascript">

jQuery("#datatable_<s:property value='#divresult[0]'/>_wrapper .row:first > div:nth-child(1)").append("<s:text name = 'sec.userGroup' />");

<s:if test="ATH.add || ATH.edit">
	var iconPopup = "<a  href='javascript:void(0)' onclick='openGroupModal()'><i class='fa fa-plus-circle fa-lg' style='color:green'></i>&nbsp;<s:text name = 'sec.add' /></a>";
	jQuery("#datatable_<s:property value='#divresult[0]'/>_wrapper .row:first > div:nth-child(2)").addClass("text-right").append(iconPopup);
</s:if>
	
</script>
<div id="groupUserSearchDialog" class="modal" data-modal-header='<s:text name="sec.popupGroup" />'>
	<s:include value="/jsp/dialog/security/group/groupUserSearchDialog.jsp"/>
</div>
