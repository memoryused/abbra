<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="modal-body">
<script type="text/javascript">

</script>


<%-- <s:set var="url" value='{"/jsp/dialog/searchProgramDialog.action"}'/> --%>
<s:set var="treeId" value='{"treeProgramOperator"}'/>
<s:set var="treeType" value='{"CHECKBOX"}'/> <!-- SPAN/IMAGE/CHECKBOX -->
<s:set var="event" value=''/> <!-- even onclick last level of each node. -->
 
 <s:set var="fnOk" value='{"chooseProgram"}'/>
<!-- 2. ส่วนของการ include template tree -->
<s:include value="/bootstrap/jsp/template/tree-full-height.jsp"/>


</div>