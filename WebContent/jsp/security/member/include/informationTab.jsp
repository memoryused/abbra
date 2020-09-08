<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
function initInformationDetail() {
	//Check ในกรณ๊ที่เป็นหน้า Add
	if(document.getElementsByName("page")[0].value  == 'ADD') {
		jQuery("#member_memberData_username").val("");
		jQuery("#passwordInfo").val("");
	}	
}
</script>

<div class="row">
	<div class="col-std"></div>
	<div class="col-std-2">
	
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield id="member_memberData_userCode" name="member.memberData.userCode" maxlength="80" cssClass="form-control requireInput clearform" validName="%{getText('sec.user_code')}" />
				<label for="member_memberData_userCode"><s:text name='sec.user_code'/><em>*</em></label> 
			</div>
		</div>
		
	</div>
	<div class="col-std-2">
	
		<div class="col-md-12">
			<div class="md-form">
				<s:select id="listPrefix"
					list="listPrefix"
					name="member.memberData.prefixId"
					headerKey=""
					headerValue=""
					listKey="key"
					listValue="value" 
					cssClass="form-control requireInput clearform" 
					validName="%{getText('sec.prefix')}"/>
				<label for="listPrefix"><s:text name="sec.prefix"></s:text><em>*</em></label>
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
				<s:textfield id="member_memberData_forename" name="member.memberData.forename" maxlength="50" cssClass="form-control requireInput clearform" validName="%{getText('sec.forename')}"/>
				<label for="member_memberData_forename"><s:text name='sec.forename'/><em>*</em></label> 
			</div>
		</div>
	
	</div>
	<div class="col-std-2">
	
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield id="member_memberData_surname" name="member.memberData.surname" maxlength="50" cssClass="form-control requireInput clearform" validName="%{getText('sec.surname')}"/>
				<label for="member_memberData_surname"><s:text name='sec.surname'/><em>*</em></label> 
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
				<s:select id="listOrganization"
					list="listOrganization"
					name="member.memberData.organizationId"
					headerKey=""
					headerValue=""
					listKey="key"
					listValue="value" 
					cssClass="form-control clearform" />
				<label for="listOrganization"><s:text name="sec.organization"></s:text></label>
			</div>
		</div>
	
	</div>
	<div class="col-std-2">
	
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield id="member_memberData_positionName" name="member.memberData.positionName" maxlength="255" cssClass="form-control clearform"/>
				<label for="member_memberData_positionName"><s:text name='sec.position'/></label>
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
				<s:textfield id="member_memberData_email" name="member.memberData.email" maxlength="100" cssClass="form-control clearform"/>
				<label for="member_memberData_email"><s:text name='sec.email'/></label>
			</div>
		</div>
		
	</div>
	<div class="col-std-2">
	
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield id="member_memberData_phone" name="member.memberData.phone" maxlength="80" cssClass="form-control clearform"/>
				<label for="member_memberData_phone"><s:text name='sec.user_tel'/></label>
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
				<s:textfield id="member_memberData_startDate" name="member.memberData.startDate" data-dp-id="userStartDate_from" data-dp-to="userEndDate_to" cssClass="form-control requireInput" data-label-text="%{getText('sec.user_start_date')}" data-invalid-feedBack="%{getText('10002')}" validName="%{getText('sec.user_start_date')}"/>
			</div>
		</div>
		
	</div>
	<div class="col-std-2">
		
		<div class="col-md-12">
			<div class="md-form">
				<s:textfield id="member_memberData_endDate" name="member.memberData.endDate" data-dp-id="userEndDate_to" data-label-text="%{getText('sec.user_end_date')}" data-invalid-feedBack="%{getText('10002')}" validName="%{getText('sec.user_end_date')}" cssClass="form-control requireInput"/>
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
				<s:checkbox name="member.memberData.active.code" cssClass="checkbox" data-label="%{getText('sec.user_active')}"/>
			</div>
		</div>
	
	</div>
	<div class="col-std-2"></div>
	<div class="col-std"></div>
</div>

<hr>

<div class="row">
	<div class="col-std"></div>
	<div class="col-std-2 col-std-span-1">
	
		<div class="col-md-12 col-lg-6">
			<div class="md-form">
				<s:textfield id="member_memberData_username" name="member.memberData.username" maxlength="50" cssClass="form-control requireInput clearform" validName="%{getText('sec.username')}"/>
				<label for="member_memberData_username"><s:text name='sec.username'/><em>*</em></label>
			</div>
		</div>
	
	</div>
	<div class="col-std"></div>
</div>
<div class="row">
	<div class="col-std"></div>
	<div class="col-std-2 col-std-span-1">
	
		<div class="col-md-12 col-lg-6">
			<div class="md-form">
				<s:hidden id="flagResetPassword" name="resetPassword"/>
				<s:textfield type="password" id="passwordInfo" name="member.memberData.password" maxlength="100" cssClass="form-control requireInput clearform" validName="%{getText('sec.password')}"/>
				<label for="passwordInfo"><s:text name='sec.password'/><em>*</em></label>
			</div>
		</div>
	
	</div>
	<div class="col-std"></div>
</div>
<div class="row">
	<div class="col-std"></div>
	<div class="col-std-2 col-std-span-1">
		
		<div class="col-md-12">
			<font class="comment">
				<s:text name="sec.remark"/><br>
				Password length must be at least (5),<br/>
				Password must contain number (0-9)
			</font>
		</div>
	
	</div>
	<div class="col-std"></div>
</div>
<div class="row">
	<div class="col-std"></div>
	<div class="col-std-2">

		<div class="col-md-12">
			<div class="md-form" style="margin-left: 20px;">
				<s:checkbox name="member.memberData.lockStatus" cssClass="checkbox" data-label="%{getText('sec.user_ready')}"/>
			</div>
		</div>	
	
	</div>
	<div class="col-std-2"></div>
	<div class="col-std"></div>
</div>