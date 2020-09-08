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
							<s:textfield id="item_itemCode" name="item.itemCode" maxlength="45" cssClass="form-control requireInput clearform" validName="%{getText('prd.productCode')}"/>
							<label for="item_itemCode"><s:text name="prd.productCode"/><em>*</em></label>
						</div>
					</div>
				</div>
				<div class="col-std-2">
					<div class="col-md-12">
						<div class="md-form">
							<s:textfield id="item_itemShortName" name="item.itemShortName" maxlength="200" cssClass="form-control requireInput clearform" validName="%{getText('prd.productName')}"/>
							<label for="item_itemShortName"><s:text name="prd.productName"/><em>*</em></label>
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
							<s:select id="item_listStatus"
								list="listStatus"
								name="item.status"
								headerKey=""
								headerValue=""
								listKey="key"
								listValue="value" 
								cssClass="form-control clearform" />
							<label for="item_listStatus"><s:text name="prd.status"></s:text></label>
						</div>
					</div>
				</div>
				<div class="col-std-2"></div>
				<div class="col-std"></div>
			</div>
		</div>
	</div>