<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">

function initPermissionInfo() {

	// สั่งให้ขยาย เนื่องจาก header column ไม่ขยายตามขนาดที่กำหนดไว้เพราะ tab ถูกซ่อนไว้อยู่ตอน initial หน้าจอ
	jQuery(document).on('show.bs.tab', 'a[href="#tab-permission"]', function (e) {
		jQuery("#tab-permission table.table").resize();
		
	});
	
	jQuery(document).on('shown.bs.tab', 'a[href="#tab-permission"]', function (e) {
		//3. set ids to idsSelectedRow
		var valueIds;
		jQuery("#datatable_divProgramDialogResult tbody tr:visible").each(function(index){
			var that = this;
			var hiddenId = jQuery("input[type='checkbox']", that).val();
			if(index == 0){
				valueIds = hiddenId;
			}else{
				valueIds = valueIds + "," + hiddenId;
			}
		});
		jQuery('#divProgramDialogResult_idsSelectedRow').val(valueIds);
		
	});
	
	jQuery("#programSearchDialog").on('hidden.bs.modal', function (e) {
		// do something...
		// Destroys an element’s modal.
		jQuery(this).sitCheckBoxTreeDialog('dispose');
	});
}

function openProgramModal(){
	
	var options = {
			containerId: "programSearchDialog",
			urlInit: "<s:url value='/jsp/dialog/searchProgramDialog.action' />",

			excludeIds: 'divProgramDialogResult_idsSelectedRow',
			chosenCallback: chooseProgramDialog,
		};
	jQuery("#programSearchDialog").sitCheckBoxTreeDialog(options)
}

function chooseProgram(){// เมื่อกด ok ที่ dialog
	jQuery("#programSearchDialog").sitCheckBoxTreeDialog('choose');
}

//ฟังก์ชั่น โปรแกรม
function chooseProgramDialog(elName, obj) {
	//1. หาค่า index ก่อนหน้า
    var tableId = "datatable_divProgramDialogResult";
    jQuery("#" + tableId).DataTable().clear().draw(false);
    var idx = jQuery('table#'+tableId+' tbody tr').length;

	jQuery('input[name=' + elName + ']:checked').each(function(i) {

		if (obj[this.id].operatorType == "F") {
			var index = idx-1;
			// set element array
			var elmArr = new Array();
			elmArr[0] = "<input type='checkbox' id='dt-checkbox-divProgramDialogResult-"+idx+"' value='"+obj[this.id].currentId+"' />" ;
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].id' value='"+obj[this.id].currentId+"' />" ;
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].deleteFlag' />" ;
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].currentId' value='"+obj[this.id].currentId+"'/>";
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].parentId' value='"+obj[this.id].parentId+"'/>";
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].parentIds' value='"+obj[this.id].parentIds+"'/>";
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].operatorId' value='"+obj[this.id].currentId+"'/>";
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].systemName' value='"+obj[this.value.split('_')[0]].label+"'/>";
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].menuName' value='"+getParentNamefn(this.value, obj)+ "'/>";
			elmArr[0] += "<input type='hidden' name='data.listProgram["+index+"].functionName' value='"+obj[this.id].label+"'/>";
			
			elmArr[1] = obj[this.value.split('_')[0]].label;
			elmArr[2] = getParentNamefn(this.value, obj);
			elmArr[3] = obj[this.id].label;
			
			// call javascript กลางสำหรับวาด table
			tableCreateTableRow(tableId, elmArr);
			
			//3. เก็บค่า id ไว้ใน textfield เวลาค้นหาจะเอาไป where not in
		    var ids = jQuery("#divProgramDialogResult_idsSelectedRow").val(); //idsSelectedRow ถูกวาดมาจาก Table Template
		    if(ids != ""){
		        ids = ids + "," +obj[this.id].currentId;
		    }else{
		        ids = obj[this.id].currentId;
		    }
		    
		    jQuery("#divProgramDialogResult_idsSelectedRow").val(ids);
		    idx++;
		} else {
			
		}
	});
	
	//Remove class checkbox
	jQuery('table#'+tableId+' tbody tr td.col-checkbox').each(function(i) {
		jQuery(this).removeClass('checkbox'); 
	});
}

</script>

<!-- div สำหรับแสดงผล Program -->
<div id="divProgramDialogResult" ></div>
<s:set var="divresult" value='{"divProgramDialogResult"}'/> 
<s:set var="listTableData" value='%{"data.listProgram"}'/> <!-- domain list เช่น userData.listPermission -->
<s:set var="columnName" value='{getText("sec.systemName"),getText("sec.menuName"),getText("sec.functionName")}'/>
<s:set var="columnID" value='{"operatorId"}'/> <!-- PK -->
<s:set var="columnData" value='{"systemName","menuName","functionName"}'/>
<s:set var="columnCSSClass" value='{"col-3","col-3","col"}'/>
<s:set var="hiddenData" value='{"currentId","parentId","parentIds","systemName","menuName","functionName","operatorId"}'/> <!-- domain properties สำหรับดึงข้อมูลมาเก็บเป็น hidden -->
<%-- <s:set var="settingTable" value='{"1100:false:true"}'/> --%>
<s:include value="/bootstrap/jsp/template/tableAddDeleteRow.jsp"/>

<script type="text/javascript">
jQuery("#datatable_<s:property value='#divresult[0]'/>_wrapper .row:first > div:nth-child(1)").append("<strong><s:text name = 'sec.programPermissiom' /></strong>");

<s:if test="ATH.add || ATH.edit">
	var iconPopupProgram = "<a  href='javascript:void(0)' onclick='openProgramModal()'><i class='fa fa-plus-circle fa-lg' style='color:green'></i>&nbsp;<s:text name = 'sec.add' /></a>";
    jQuery("#datatable_<s:property value='#divresult[0]'/>_wrapper .row:first > div:nth-child(2)").addClass("text-right").append(iconPopupProgram);
   </s:if>

</script>

<div id="programSearchDialog" class="modal" data-modal-header='<s:text name="sec.programData" />'>
	<s:include value="/jsp/dialog/security/operator/programSearchDialog.jsp"/>
</div>