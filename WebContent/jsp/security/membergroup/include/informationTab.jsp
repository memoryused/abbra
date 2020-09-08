<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
function initInformationDetail() {
	//Check ในกรณ๊ที่เป็นหน้า Add
	if(document.getElementsByName("page")[0].value  == 'ADD') {
		jQuery("#data_group_active").prop('checked', true);
	}	
}
</script>

<div class="row">
	<div class="col-std"></div>
	<div class="col-std-2">
	
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield id="data_group_groupCode" name="data.group.groupCode" maxlength="80" cssClass="form-control requireInput clearform" validName="%{getText('sec.group_code')}"/>
				<label for="data_group_groupCode"><s:text name='sec.group_code'/><em>*</em></label> 
			</div>
		</div>
		
	</div>
	<div class="col-std-2">
	
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield id="data_group_groupName" name="data.group.groupName" maxlength="400" cssClass="form-control requireInput clearform" validName="%{getText('sec.group_name')}"/>
				<label for="data_group_groupName"><s:text name='sec.group_name'/><em>*</em></label> 
			</div>
		</div>
	
	</div>
	<div class="col-std"></div>
</div>

<div class="row">
	<div class="col-std"></div>
	<div class="col-std-2">

		<div class="col-md-12">
			<div class="md-form" style="margin-left: 20px;">
				<s:checkbox id="data_group_active" name="data.group.active.code" cssClass="checkbox" data-label="%{getText('sec.user_active')}"/>
			</div>
		</div>
	
	</div>
	<div class="col-std-2"></div>
	<div class="col-std"></div>
</div>
