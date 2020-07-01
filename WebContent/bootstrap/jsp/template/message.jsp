<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
	jQuery(document).ready(function(){
		<s:if test='getAlertMaxExceed().equals("A")'>
			alert(getMessageResponse("<s:property value='ActionMessages[0]' escapeHtml='false'/>"));
		</s:if>
		<s:elseif test='getAlertMaxExceed().equals("C")'>
			showConfrimMessage();
		</s:elseif>
		<s:else>
			<s:if test='%{ActionMessages.size() == 1}'>
				var msgx = getMessageResponse("<s:property value='ActionMessages[0]' escapeHtml='false'/>");
				if (msgx != '') {
					alert(msgx);
				}
			</s:if>
		</s:else>
	});
</script>
<s:if test='%{getAlertMaxExceed().equals("A")}'>
</s:if> 
<s:elseif test='%{getAlertMaxExceed().equals("C")}'>
</s:elseif> 
<s:elseif test='%{getAlertMaxExceed().equals("N")}'>
	<s:if test='%{ActionMessages[0] == "E"}'>
		<script type="text/javascript">
			showNotifyMessageError('<s:property value="ActionMessages[1]" escapeHtml="false"/>', '<s:property  value="ActionMessages[2]" escapeHtml="false"/>');
		</script>
	</s:if>
	<s:elseif test='%{ActionMessages[0] == "W"}'>
		<script type="text/javascript">
			showNotifyMessageWarning('<s:property value="ActionMessages[1]" escapeHtml="false"/>');
		</script>
	</s:elseif>
	<s:elseif test='%{ActionMessages[0] == "S"}'>
		<script type="text/javascript">
			showNotifyMessageSuccess('<s:property value="ActionMessages[1]" escapeHtml="false"/>');
		</script>
	</s:elseif>
	<s:else>
	</s:else>
</s:elseif>
<s:else>
</s:else> 