<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<link href="<s:url value='/css/jquery.treeview.css' />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value='/bootstrap/js/jquery.treeview.js' />"></script>
<script type="text/javascript">

</script>
</head>

<div id="<s:property value='#treeId[0]'/>_CONTROL" class="row" sytle="margin-bottom:5px;">
	<s:if test="#treeType[0] == 'CHECKBOX'">
		
		<input type="checkbox" name="checkAll" class="checkbox  ml-3" data-label="<strong><s:text name="all"/></strong>"/>
		<div class="ml-3">
			<a href="#"><i class="fa fa-minus-square fa-sm tree-icon-head-color" ></i>&nbsp;<span class="text-dark"><strong><s:text name="closeAll"/></strong></span></a>
		</div>
		<div class="ml-3">
			<a href="#" ><i class="fa fa-plus-square  tree-icon-head-color" ></i>&nbsp;<span class="text-dark"><strong><s:text name="openAll"/></strong></span></a>
		</div>
	</s:if>
	
	
</div>

<div id="<s:property value='#treeId[0]'/>_VIEW" style="overflow: hidden; overflow-y: auto;"></div>

<%-- <input type="text" style="display:none;" value="<s:property value='#url[0]'/>" id="URL" /> --%>
<input type="text" style="display:none;" value="<s:property value='#treeId[0]'/>" id="treeId" />
<input type="text" style="display:none;" value="<s:property value='#treeType[0]'/>" id="treeType" />
<input type="text" style="display:none;" value="<s:property value='#event[0]'/>" id="event" />

<s:if test="#treeType[0] == 'CHECKBOX'">
	<!------------------------------------- BUTTON ----------------------------------->
	<%-- <table id="buttonPopup_<s:property value='#treeId[0]'/>" style="table-layout: fixed;" class="FORM" >
		<tr>
			<td style="width: 60%; height: 5px;"></td>
			<td style="width: 40%; height: 5px;">
		</tr>
		<tr>
			<td style="width: 60%;"></td>
			<td style="width: 40%;">
				<button id="btnOk"  class="btnAdd" onclick="dialogChooseNodeTree('<s:property value='#treeId[0]'/>_CHECKBOX');">
					<s:text name="ok"/>
				</button>
				<button id="bntCloseDialog_<s:property value='#divresult[0]'/>" class="btnCancel"   type="button" onclick="return dialogClose();" >
					<s:text name="closePage"/>
				</button>
			</td>
		</tr>
	</table> --%>
	<div id="buttonPopup_<s:property value='#treeId[0]'/>" class="ui-sitbutton"  
	data-buttonType="choose_dialog" 
	data-auth="<s:property value='ATH.search'/>" 
	data-funcChooseDialog="<s:property value='#fnOk[0]'/>" >
	</div>
	<!------------------------------------- BUTTON ------------------------------------>
</s:if>
<s:else>
	<!------------------------------------- BUTTON ----------------------------------->
	<%-- <s:include value="/jsp/template/button.jsp">
		<s:param name="buttonType" value="%{'view,enable'}" />
		<s:param name="function" value="%{'dialogClose()'}" />
	</s:include> --%>
	<!------------------------------------- BUTTON ------------------------------------>
</s:else>