<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%--———————————————————————————————————————————————————————————————————————
	TABLE
————————————————————————————————————————————————————————————————————————--%>
<div class="row">
	<div id="tableContainer" class="col-12" style="display: none;">
		<table id="tableResult-4" class="table table-striped table-bordered dt-responsive nowrap dataTable no-footer" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th class="col-checkbox" data-orderable="false"></th>
					<th data-name="certificateName" data-orderable="false"><s:text name="prd.standard"/></th>
					<th data-name="docExpireDate" data-orderable="false"><s:text name="prd.effectiveDate"/></th>
					<th class="pdfPath text-center" data-orderable="false"><s:text name="prd.pdf"/></th>
					<th class="txtPath text-center" data-orderable="false"><s:text name="prd.text"/></th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</div>