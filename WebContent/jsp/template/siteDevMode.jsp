<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<s:if test="%{@com.cct.app.core.config.parameter.domain.ParameterConfig@getParameter().getSiteControl().isSiteDevMode()}">
	<script type="text/javascript">
		var counterDevIp = 0;
		jQuery(document).ready(function() {
			checkDevReady();
			jQuery("#dialogDevIp").dialog({ 
				autoOpen: false
				,beforeClose: function( event, ui ) {
					counterDevIp = 0;
				}
			});
		});
		
		jQuery(document).on("dblclick",function(event){
			++counterDevIp;
			if (counterDevIp == 5) {
				jQuery("#dialogDevIp").dialog("open");
			}
		});
		
		function checkDevReady(response) {
			if (response == undefined) {
				jQuery("#devIp").val("<s:property value='#attr[\"devIp\"]'/>");
			} else {
				jQuery("#devIp").val(response);
			}
		}
	
		function checkIpDev(status) {
			if (status == 'ON') {
				if (jQuery("#devIp").val() == "") {
					alert("<s:text name='10002'/>");
					jQuery("#devIp").focus();
					return;
				}
			} else if (status == 'OFF') {
				jQuery("#devIp").val("");
			} else {
				return;
			}
			jQuery.ajax({
				  method: "POST",
				  url: "<s:url value='/devIpServlet' />",
				  data: { devIp: jQuery("#devIp").val(), devIpStatus: status }
			}).done(function(response) {
				checkDevReady(response);
			});
		}
	</script>
	<div id="dialogDevIp" style="width: 200px; margin: auto; display: none;">
		<table style="margin: auto;">
			<tr>
				<td><div style="float: left;">IP Dev</div><div style="float: right;"><a href="javascript:void(0);" onclick="checkIpDev('ON');" style="color: lime; font-weight: bold;">ON</a><em>&nbsp;&nbsp;</em><a href="javascript:void(0);" onclick="checkIpDev('OFF');" style="color: red; font-weight: bold;">OFF</a></div></td>
			</tr>
			<tr>
				<td><input type="text" style="width: 150px;" id="devIp"/></td>
			</tr>
		</table>
	</div>
</s:if>
