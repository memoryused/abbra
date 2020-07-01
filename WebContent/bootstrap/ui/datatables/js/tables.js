function deleteRow_by_elname(divId){
	var tableId = "datatable_" + divId;
	tableDeleteRow( jQuery('table#' + tableId + ' tr:visible [id^=dt-checkbox-' + divId + '-]:checked'), tableId, divId);
	//jQuery("#dt-checkbox-" + divId + "-checkAll").prop('checked', false);
}

function prepareDataBeforeSetInForm(index, divId, callFunc, listName, hiddenName){

	var tableId = "datatable_" + divId;

	//เลิก Highlight Row ที่ถูกเลือก
	jQuery('table#'+tableId+' tbody tr:eq(' + jQuery("#currentRowEdited_" + divId).val() + ')').removeClass("bg-info color-white");

	
	//Set ค่า Row ปัจจุบันที่กำลังแก้ไขอยู่
	jQuery("#currentRowEdited_" + divId).val(index);
	
	//Highlight Row ที่ถูกเลือก
	jQuery('table#'+tableId+' tbody tr:eq(' + index + ')').addClass("bg-info color-white");

	//Set ค่าที่เก็บไว้ที่ Hidden ใส่ Object เพื่อส่งไปให้ Callback วาดใส่ Form
	var object = new Object;
	
    object['id'] = jQuery("input[name='"+ listName + "["+index+"].id']").val();
	jQuery(hiddenName).each(function(i, value){
	    object[value] = jQuery("input[name='"+ listName + "["+index+"]." + value + "']").val();
	});
	
	//เรียกไปยัง Callback function
	callFunc(object);
}

function clearCurrentRowEdited(divId){
	jQuery("#currentRowEdited_" + divId).val("");
}
	

/**
 * -------------------------- สร้าง table --------------------------
 */
function tableCreateTableRow(tableId, elmArr) {
	
	//dataTables_empty
	var index = jQuery('table#'+tableId+' tbody tr:visible').length + 1;
	
	if(jQuery('table#'+tableId+' tbody tr:visible td.dataTables_empty').length > 0){
		jQuery("#" + tableId).DataTable().row(jQuery('table#'+tableId+' tbody tr:visible')).remove().draw();
		index = 1;
	}
	
	// เพิ่ม column order
	elmArr.unshift(index);
	
	jQuery("#" + tableId).DataTable().row.add(elmArr).draw(false);

	jQuery("#" + tableId).parent().scrollTop(jQuery("#" + tableId).height());
	
}

/**
 * ลบข้อมูลในตาราง
 * @param lstCheckbox
 * @param tbResultCon
 * @param ids
 * @returns {Boolean}
 */
function tableDeleteRow(lstCheckbox, tbResultCon, ids) {
	
	if(lstCheckbox.length < 1){
		alert(validateMessage.CODE_10001);
		return false;
	}
	
	if (confirm(validateMessage.CODE_50005) == false) {
		return false;
	}
	
	lstCheckbox.each(function(){
		var tr = jQuery(this).closest("tr");
		tr.hide();
		tr.find("td > input[name$='.deleteFlag']").val("Y"); //element ที่ชื่อลงท้ายด้วย .deleteFlag ให้ value=Y
		tr.find("td > input[type=checkbox]:checked").prop("checked" ,false).prop("disabled" ,true); //disable & uncheck the checkbox
	});
	
	var idsComma = "";
	//[3] reOrder & set lineE lineO
	jQuery("table#" + tbResultCon + " tbody > tr:visible").each(function(order) { //loop each row
		order++;
		var thisRow = jQuery(this);
		// reset lineO,lineE class & // update order
		thisRow.removeClass('odd even').find('td.col-order').text(order);
		//update CSS
		if (order%2 == 0) {
			thisRow.addClass("odd");
		} else {
			thisRow.addClass("even");
		}

		//[4] set ids in textfield
		var id = jQuery("input[type='checkbox']", thisRow).val();
		
		if(idsComma == ""){
			idsComma = id;
		}else{
			idsComma = idsComma + "," + id;
		}
	});
	
	if (typeof ids === 'object') {
		ids.val(idsComma);
	}else{
		if(ids != ""){
			jQuery("#"+ids + "_idsSelectedRow").val(idsComma);
		}
	}
}
