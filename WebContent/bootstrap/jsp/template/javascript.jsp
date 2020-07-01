<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sit.common.CommonAction"%>
<%@ page import="com.sit.app.core.config.parameter.domain.ParameterConfig"%>

<script type="text/javascript">
	var autocompleteConfig = {
		contextPath: "<s:url value='/' />",
		msgShowAll: 'Click Infomation',
		msgNotMatch: 'Invalid Data. Please check again.',
		msgMinChar: 'Invalid Data. Please check again.'
	};
	
	const WEB_CONTEXT = "<%=request.getContextPath()%>"; 
	const CONTEXT_JS_CSS = "<%=request.getContextPath()%>" + "/bootstrap";
	
	var attachFileConfig = {
		tmpPath: "<s:property value='@com.sit.app.core.config.parameter.domain.ParameterConfig@getParameter().getAttachFile().getTmpPath()' />"
	};

	//Check length Date calendar for spinner
	var maxDay  =  "<s:property value='@com.sit.app.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getMaxDay()'/>";
	var minDay  =  "<s:property value='@com.sit.app.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getMinDay()'/>";
	var maxDayCustom  =  "366";
	
	/* กำหนดค่าการกรอกข้อมูลอย่างน้อย ก่อนค้นหาของ autocomplete*/
	var fillAtLeast  =  "<s:property value='@com.sit.app.core.config.parameter.domain.ParameterConfig@getParameter().getApplication().getFillAtLeast()'/>";
	
	//สำหรับ config show updateVersion
	var showUpdateV = "<s:property value='@com.sit.app.core.config.parameter.domain.ParameterConfig@getParameter().getShowUpdateversion().getShow()'/>";

	var validateMessage = {
			CODE_10002: '<s:text name="10002"/>',
			CODE_10033: '<s:text name="10033"/>',
			CODE_10001: '<s:text name="10001"/>',
			CODE_10008: '<s:text name="10008"/>',
			CODE_10034: '<s:text name="10034"/>',
			CODE_50027: '<s:text name="50027"/>',
			CODE_10040: '<s:text name="10040"/>',
			CODE_10039: '<s:text name="10039"/>',
			CODE_30011: '<s:text name="30011"/>',	
			CODE_numberOfSearch: '<s:text name="numberOfSearch"/>',
			CODE_order: '<s:text name="order"/>',
			CODE_10036: '<s:text name="10036"/>',
			CODE_30074: '<s:text name="30074"/>',
			CODE_EDIT: '<s:text name="edit"/>',
			CODE_VIEW: '<s:text name="view"/>',
			CODE_ACTIVE: '<s:text name="active"/>',
			CODE_INACTIVE: '<s:text name="inactive"/>',
			CODE_50041: '<s:text name="50041"/>',
			CODE_50005: '<s:text name="50005"/>',
			CODE_10066: '<s:text name="10066"/>',
			CODE_10068: '<s:text name="10068"/>',
			CODE_10079: '<s:text name="10079"/>',
			CODE_50026: '<s:text name="50026"/>',
					
			CODE_50003: '<s:text name="50003"/>',  	// msgConfirmSaveAdd
			CODE_50004: '<s:text name="50004"/>',  	// msgConfirmSaveEdit
			CODE_50006: '<s:text name="50006"/>',  	// msgCancelAdd
			CODE_50007: '<s:text name="50007"/>', 	// msgCancelEdit
			CODE_50010: '<s:text name="50010"/>', 	// msgCancelView
			CODE_50012: '<s:text name="50012"/>',	
		
			CODE_ACTIVE: '<s:text name="active"/>',
			CODE_INACTIVE: '<s:text name="inactive"/>',		
			CODE_numberOfSearch: '<s:text name="numberOfSearch"/>',
			CODE_SEARCH: 'Search',
			CODE_CLEAR: 'Clear',
			CODE_SAVE: 'Save',
			CODE_CANCEL: 'Cancel',
			CODE_EDIT: 'Edit',
			CODE_CLOSE: 'Close',
			CODE_PRINT: 'Print',
			CODE_OK: 'Ok'
	};

    function reloadEditPage(){
		if (typeof(document.getElementsByName("urlEdit")[0]) != "undefined"){
			var url =document.getElementsByName("urlEdit")[0].value ;
			submitPage(url);
		}
	}  
</script>

<!-- jQuery first, then Tether, then Bootstrap JS. -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/jquery/jquery-3.2.1.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap/js/tether.js'/>"></script> 
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap/js/bootstrap.js'/>"></script>


<!-- OTHER -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/charactor/checkCharactor.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/inputmethod.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/validate/inputvalidate.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/javascript-validate.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/ajax-event-sit_0.0.1.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/submit/submit.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/control/control.js'/>"></script>

<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/common-javascript-bootstrap.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/maldives.js'/>"></script>


<!-- ACCORDION ต้อง GEN ก่อน COMPONENT อื่น (จำเป็น)-->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/accordion/js/manage-accordion.js'/>"></script>



<!-- NOTIFY -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/jquery-notify/js/jquery.notify.min.js'/>"></script>

<!-- DATATABLE -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/jquery.dataTables.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/dataTables.bootstrap4.min.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/dataTables.responsive.min.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/responsive.bootstrap4.min.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/dataTables-sit-0.0.1.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/dataTables.dialog-sit-0.0.1.js'/> "></script>

<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/dataTables.fixedColumns.min.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/dataTables.select.min.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/dataTables.buttons.min.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/buttons.bootstrap4.min.js'/> "></script>

<!-- TABLE -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/datatables/js/tables.js'/>"></script>

<!-- Select Picker & DROPDOWNLIST  -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-select-master/js/bootstrap-select.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-select-master/js/bootstrap-select-filter.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-select-master/js/i18n/defaults-en_US.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-select-master/js/manage-select.js'/> "></script>

<!-- Autocomplete -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/jquery-ui/js/jquery-ui-1.12.1.custom.min.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-autocomplete/js/bootstrap-autocomplete.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-autocomplete/js/bootstrap-autocomplete-ajax.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-autocomplete/js/manage-autocomplete.js'/> "></script>



<!-- BOOTSTRAP AJAX LOADER -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/loader/js/ajax-loader.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/loader/js/loader-sit_0.0.1.js'/> "></script>


<!-- Button -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/button/bootstrap/js/button-sit-0.0.1.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/button/bootstrap/js/button-predefine-sit.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/button/bootstrap/js/manage-button.js'/> "></script>


<!-- BOOTSTRAP DATETIME_PICKER & MOMENT-->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/moment/moment.js'/> "></script>
<%-- <script type="text/javascript" src="<s:url value='/js/moment/moment-range.js'/> "></script> --%>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/js/moment/local/th.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-datetimepicker/js/manage-datetimepicker.js'/> "></script>


<!-- CHECKBOX -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/checkbox/js/manage-checkbox.js'/> "></script>


<!-- RADIO -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/radio/js/manage-radio.js'/> "></script>


<!-- MODAL -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/modal/js/manage-modal.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/modal/js/treeDialog-sit-0.0.1.js'/> "></script>


<!-- UNIT -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/unit/js/manage-unit.js'/> "></script>


<!-- SPINNER -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-spinner/js/bootstrap-spinner.js'/> "></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/bootstrap-spinner/js/manage-spinner.js'/> "></script>



<!-- Plupload -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/plupload-2.1.2/js/plupload.full.min.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/plupload-2.1.2/js/jquery.ui.plupload/jquery.ui.plupload.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/plupload-2.1.2/js/upload-sit-0.0.1.js'/>"></script>
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/plupload-2.1.2/js/manage-plupload.js'/>"></script>


<!-- LAYOUT -->
<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/layout/js/manage-layout.js'/>"></script>

<script type="text/javascript" src="<s:url value='%{#application.CONTEXT_JS_CSS}/ui/mdb-free-4-14-0/js/mdb.js'/>"></script>



