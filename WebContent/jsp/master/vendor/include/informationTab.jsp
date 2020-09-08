<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
function initInformationDetail() {

}
</script>

<div class="row">
	<div class="div-criteria">
		<div class="row">
			<div class="col-std"></div>
			<div class="col-std-2">
				<div class="col-md-12">
					<div class="md-form">
						<s:textfield id="vendor_vendorCode" name="vendor.vendorCode" maxlength="45" cssClass="form-control requireInput clearform" validName="%{getText('prd.venderCode')}"/>
						<label for="vendor_vendorCode"><s:text name="prd.venderCode"/><em>*</em></label>
					</div>
				</div>
			</div>
			<div class="col-std-2">
				<div class="col-md-12">
					<div class="md-form">
						<s:textfield id="vendor_vendorName" name="vendor.vendorName" maxlength="500" cssClass="form-control requireInput clearform" validName="%{getText('prd.venderName')}"/>
						<label for="vendor_vendorName"><s:text name="prd.venderName"/><em></em></label>
					</div>
				</div>
			</div>
			<div class="col-std"></div>
		</div>
		<div class="row">
			<div class="col-std"></div>
			<div class="col-std-2">
				<div class="col-md-12">
					<div class="md-form">
						<s:textfield id="vendor_vendorShortName" name="vendor.vendorShortName" maxlength="500" cssClass="form-control requireInput clearform" validName="%{getText('prd.venderShortName')}" />
						<label for="vendor_vendorShortName"><s:text name="prd.venderShortName"/></label>
					</div>
				</div>
			</div>
			<div class="col-std-2">
				<div class="col-md-12">
					<div class="md-form">
						<s:select id="vendor_listStatus"
							list="listStatus"
							name="vendor.status"
							headerKey=""
							headerValue=""
							listKey="key"
							listValue="value" 
							cssClass="form-control clearform" />
						<label for="vendor_listStatus"><s:text name="prd.status"></s:text></label>
					</div>
				</div>
			</div>
			<div class="col-std"></div>
		</div>
	</div>
</div>	