<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>

</head>
	<!-- ******************************* DIV RESULT ********************************* -->		
	
	<!-- ******************************* HEADER ********************************* -->
	<table id="headerTable_<s:property value='#divresult[0]'/>" style="display: none">
		<tr>
			<td class="LEFT"></td>
			<td class="RIGHT"></td>
		</tr>
	</table>
	<table id="datatable_<s:property value='#divresult[0]'/>" class="table table-bordered table-striped nowrap no-footer dtr-inline" >
		<!-- ******************************* COLUMN HEADER ********************************* -->
		<thead>				
			<tr class="th">
				<th class="col-order text-center"><s:text name="no" /></th>
				<th class="col-checkbox text-center">
					<div class="checkbox checkbox-datatable">
						<input value='<s:property value="#dataId"/>' id="dt-checkbox-<s:property value='#divresult[0]'/>-checkAll" type="checkbox" />
						<label for="dt-checkbox-<s:property value='#divresult[0]'/>-checkAll">&nbsp;</label>
					</div>
					
				</th>
														
				<s:iterator value="columnName" status="statusH" var="column_name">		
					<th class="<s:property value="#columnCSSClass[#statusH.index]" /> text-center" style="<s:property value="#columnStyle[#statusH.index]" />"><s:property value = "#column_name"/></th>
				</s:iterator>																
			</tr>
		</thead>
		
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
		
		<tbody>
		
			<!-- ******************************* COLUMN DETAIL ********************************* -->
			<s:iterator value="#listAddEditData" status="status" var="domain" >	
			
				<s:if test="#status.even == true">
					<s:set var="tabclass" value="%{'even'}" />
				</s:if>
				<s:else>
					<s:set var="tabclass" value="%{'odd'}" />
				</s:else>
				<s:if test='#domain.deleteFlag == "Y"'>
					<tr class="td <s:property value='tabclass'/>" style="display: none;">
				</s:if>
				<s:else>
					<tr class="td <s:property value='tabclass'/>">
				</s:else>
					<td class="col-order"><s:property value="%{#status.index + 1}"/></td>
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
							<td class="<s:property value="#columnCSSClass[#status.index]" /> <s:property value="#key" /> "><s:property value="#indexs" escapeHtml="false"/></td>				
					</s:iterator>
				</tr>
			</s:iterator>
		</tbody>	
	</table>

	<!-- ******************************* FOOTER ********************************* -->
	<table id="footerTable_<s:property value='#divresult[0]'/>" class="footer-table">
		<tr>
			<td class="LEFT">
				<a href="javascript:;" data-toggle="tooltip" title="<s:text name='delete' />" id="btnDel_<s:property value='#divresult[0]'/>" onclick="deleteRow_by_elname('<s:property value='#divresult[0]'/>');">
					<i class="fa fa-times fa-lg text-danger"></i>&nbsp;<s:text name="delete" />&nbsp;
				</a>
				<input type="hidden" id="<s:property value="#divresult[0]"/>_idsSelectedRow" />
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
		          "scrollY": 250
		        , "scrollX": true
		        , "ordering": false
		        , "bPaginate": false
		        , "bLengthChange": false
		        , "bFilter": false
		        , "bInfo": false
		        , createdRow: function(row, data, index) {
	        		jQuery("td:eq(0)",row).addClass("col-order text-center");
	        		jQuery("td:eq(1)",row).addClass("col-checkbox checkbox");
		        	<s:iterator value="#columnCSSClass" status="status" var="columnCssClass" >
		        		jQuery("td:eq(<s:property value='%{#status.index + 2}'/>)",row).addClass("<s:property value='#columnCSSClass[#status.index]' />");
					</s:iterator>
		        }
		    });
		}
		
		//3. set ids to idsSelectedRow
		var valueIds;
		jQuery("#datatable_<s:property value='#divresult[0]'/> tbody tr:visible").each(function(index){
			var that = this;
			var hiddenId = jQuery("input[type='checkbox']", that).val();
			if(index == 0){
				valueIds = hiddenId;
			}else{
				valueIds = valueIds + "," + hiddenId;
			}
		});
		jQuery('#<s:property value="#divresult[0]"/>_idsSelectedRow').val(valueIds);
	</script>		
		
	<s:token/>
</html>