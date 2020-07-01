/**
 * ใช้ submit ไปยัง action Report ที่ต้องการ โดย default ที่ form 0
 * @param action คือ acion url
 */
function submitPageReport(action) {
	submitPageReportFromIndex(0, action);
}

/**
 * ใช้ submit ไปยัง action ที่ต้องการ โดยสามารถระบุ form ที่ต้องการได้
 * @param formIndex คือ index ของ form ที่ต้องการ submit
 * @param action คือ acion url
 * ตัด showLoaderStatus ออก  Report ไม่ return หน้า
 */
function submitPageReportFromIndex(formIndex, action) {
	document.forms[formIndex].action = action;
	document.forms[formIndex].submit();
	
}


/**
 * ใช้ submit ไปยัง action ที่ต้องการ โดย default ที่ form 0
 * @param action คือ acion url
 */
function submitPage(action) {
	submitPageFromIndex(0, action);
}

/**
 * ใช้ submit ไปยัง action ที่ต้องการ โดยสามารถระบุ form ที่ต้องการได้
 * @param formIndex คือ index ของ form ที่ต้องการ submit
 * @param action คือ acion url
 */
function submitPageFromIndex(formIndex, action) {
	document.forms[formIndex].action = action;
	//แสดง Ajax load status เพื่อทำการ lock หน้าจอ
//	showLoaderStatus();
	document.forms[formIndex].submit();
}
