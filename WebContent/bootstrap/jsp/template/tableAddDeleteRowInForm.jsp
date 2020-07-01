<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>

</head>

	<script>
		var arrayHid_<s:property value='#divresult[0]'/> = [];
		<s:iterator value="hiddenData" status="statusHidd" var="hidden" >
			arrayHid_<s:property value='#divresult[0]'/>.push('<s:property value='#hidden'/>');
		</s:iterator>
	</script>

	<!-- ******************************* DIV RESULT ********************************* -->		
	<table id="datatable_<s:property value='#divresult[0]'/>" class="table table-bordered table-striped nowrap no-footer dtr-inline" >
	
		<!-- ******************************* COLUMN HEADER ********************************* -->				
		<thead>
			<tr>
				<th class="col-order text-center"><s:text name="no" /></th>
				<th class="col-checkbox">
					<div class="checkbox checkbox-datatable">
						<input value='<s:property value="#dataId"/>' id="dt-checkbox-<s:property value='#divresult[0]'/>-checkAll" type="checkbox" />
						<label for="dt-checkbox-<s:property value='#divresult[0]'/>-checkAll">&nbsp;</label>
					</div>
					
				</th>
				<th class="col-edit"></th>
														
				<s:iterator value="columnName" status="statusH" var="column_name">		
					<th class="<s:property value="#columnCSSClass[#statusH.index]" />" style="<s:property value="#columnStyle[#statusH.index]" />"><s:property value = "#column_name"/></th>
				</s:iterator>																
			</tr>
		</thead>
		<tbody>
		<s:set var="listAddEditNameArray" value='%{#listTableData.split("[.]")}'/>
		<!-- Loop get object from string name -->
		<s:iterator value="listAddEditNameArray" status="statusAddEdit" var="listAddEdit" >
			<s:if test="#statusAddEdit.index == 0">
				<s:set var="listAddEditData"  value="[#listAddEdit]" />
			</s:if>	
			<s:else>
				<s:set var="listAddEditData"  value="#listAddEditData[#listAddEdit]" />
			</s:else>									
		</s:iterator>
		
		<!-- ******************************* COLUMN DETAIL ********************************* -->
		<s:iterator value="#listAddEditData" status="status" var="domain" >	
			<s:if test='#domain.deleteFlag == "Y"'>
				<tr style="display: none;">
			</s:if>
			<s:else>
				<tr>
			</s:else>
				<td class="col-order text-center"><s:property value="%{#status.index + 1}"/></td>
				<td class="col-checkbox">
					<!-- columnID -->
					<s:iterator value="columnID" status="status" var="cId" >
						<s:set var="cIds" value='%{#cId.split("[.]")}'/>
							<s:iterator value="cIds" status="status" var="cIdss" >
								<s:if test="#status.index == 0">
									<s:set var="dataId"  value="#domain[#cIdss]" />
								</s:if>	
								<s:else>
									<s:set var="dataId"  value="#dataId[#cIdss]" />
								</s:else>									
						</s:iterator>
					</s:iterator>
					
					
					<div class="checkbox checkbox-datatable">
						<input value='<s:property value="#dataId"/>' id="dt-checkbox-<s:property value='#divresult[0]'/>-<s:property value="%{#status.index + 1}"/>" type="checkbox">
						<label for="dt-checkbox-<s:property value='#divresult[0]'/>-<s:property value="%{#status.index + 1}"/>">&nbsp;</label>
					</div>
						
					<!-- hiddenData หลัก -->
					<input type="hidden" name="<s:property value='#listTableData'/>[<s:property value='#status.index ' />].id" value="<s:property value="#domain.id"/>"/> <!-- id For Check Added? -->
					<input type='hidden' name="<s:property value='#listTableData'/>[<s:property value='#status.index ' />].deleteFlag" value='<s:property value="#domain.deleteFlag"/>'/> <!-- deleteFlag For Check Deleted? -->
					<input type='hidden' name="<s:property value='#listTableData'/>[<s:property value='#status.index ' />].edited" value='<s:property value="#domain.edited"/>'/> <!-- edited? -->
						
					<!-- hiddenData เพิ่มเติม -->
					<s:iterator value="hiddenData" status="statusHidd" var="hidden" >	
						<s:set var="hiddens" value='%{#hidden.split("[.]")}'/>
							<s:iterator value="hiddens" status="status" var="hiddenss" >	
								<s:if test="#status.index == 0">
									<s:set var="subHidd"  value="#domain[#hiddenss]" />
								</s:if>
								<s:else>
									<s:set var="subHidd"  value="#subHidd[#hiddenss]" />
								</s:else>
						</s:iterator>						
						<input type="hidden" name="<s:property value='#listTableData'/>[<s:property value='#status.index ' />].<s:property value="#hidden"/>" value="<s:property value="#subHidd"/>" />
					</s:iterator>						
				</td>
				
				<!-- Column แก้ไข -->
				<td class="col-edit">
					<a href="javascript:void(0);" title="<s:text name='edit'/>" data-toggle="tooltip" onclick="prepareDataBeforeSetInForm(<s:property value='%{#status.index}'/>,'<s:property value='#divresult[0]'/>',<s:property value='#callbackFunction[0]'/>,'<s:property value='#listTableData'/>',arrayHid_<s:property value='#divresult[0]'/>);">
				 		<i class="fa fa-pencil-square-o fa-lg"></i>
				 	</a>
				</td>
				
				<!-- columnData -->
				<s:iterator value="columnData" status="status" var="key" >	
					<s:set var="keys" value='%{#key.split("[.]")}'/>
						<s:iterator value="keys" status="status" var="keyss" >		
						<s:if test="#status.index == 0">
							<s:set var="indexs"  value="#domain[#keyss]" />
						</s:if>
						<s:else>
							<s:set var="indexs" value="#indexs[#keyss]" />
						</s:else>														
						</s:iterator>	
						<td class="<s:property value="#columnCSSClass[#status.index]" /> "><s:property value="#indexs"/></td>				
				</s:iterator>
			</tr>
		</s:iterator>	
		</tbody>
	</table>

	<!-- ******************************* FOOTER ********************************* -->
	
	<table id="footerTable_<s:property value='#divresult[0]'/>">
		<tr>
			<td class="LEFT">
				<a href="javascript:;" data-toggle="tooltip" title="<s:text name='deleteSelect' />" id="btnDel_<s:property value='#divresult[0]'/>" onclick="deleteRow_by_elname('<s:property value='#divresult[0]'/>');clearCurrentRowEdited('<s:property value='#divresult[0]'/>');">
					<i class="fa fa-times fa-lg text-danger"></i>&nbsp;<s:text name="delete" />&nbsp;
				</a>
				<input type="hidden" style="" id="currentRowEdited_<s:property value='#divresult[0]'/>">
			</td>
			<td class="RIGHT"></td>
		</tr>
	</table>
	<script>
		jQuery("#dt-checkbox-" + "<s:property value='#divresult[0]'/>" + "-checkAll").click(function(){
			if(jQuery(this).is(':checked'))
				jQuery("#datatable_" + "<s:property value='#divresult[0]'/>" + " tr:visible [id^=dt-checkbox-" + "<s:property value='#divresult[0]'/>" + "-]:not(:checked)").click();
			else
				jQuery("#datatable_" + "<s:property value='#divresult[0]'/>" + " tr:visible [id^=dt-checkbox-" + "<s:property value='#divresult[0]'/>" + "-]:checked").click();
		});
		
		if(!jQuery("#datatable_" + "<s:property value='#divresult[0]'/>").parents().hasClass("modal")){	
			jQuery("#datatable_" + "<s:property value='#divresult[0]'/>").DataTable( {
		          "scrollY": 200
		        , "scrollX": true
		        , "ordering": false
		        , "bPaginate": false
		        , "bLengthChange": false
		        , "bFilter": false
		        , "bInfo": false
		        , createdRow: function(row, data, index) {
	        		jQuery("td:eq(0)",row).addClass("col-order text-center");
	        		jQuery("td:eq(1)",row).addClass("col-checkbox");
	        		jQuery("td:eq(2)",row).addClass("col-edit");
		        	<s:iterator value="#columnCSSClass" status="status" var="columnCssClass" >
		        		jQuery("td:eq(<s:property value='%{#status.index + 3}'/>)",row).addClass("<s:property value='#columnCSSClass[#status.index]' />");
					</s:iterator>
					setTimeout(function(){ 
						jQuery("#datatable_" + "<s:property value='#divresult[0]'/>").DataTable().columns.adjust().responsive.recalc();
					}, 100);
		        }
		    });
		}
	</script>
</html>