<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">

	jQuery(document).ready(function(){
		// table result
		<%--
		var el = jQuery('.RESULT_BOX_TITAL.BACKGROUND_COLOR');
		el.removeClass('BACKGROUND_COLOR');
		el.addClass('ui-widget-header');
		
		el = jQuery('.RESULT_HEADER.BACKGROUND_COLOR th');
		el.addClass('ui-state-default');
		
		el = jQuery('.RESULT_HEADER.BACKGROUND_COLOR');
		el.removeClass('BACKGROUND_COLOR');

		el = jQuery('.RESULT_CONTENT th');
		el.addClass('ui-state-default');
		// table result
		--%>
		//Input Type Text
		text = jQuery('input:text');
		text.addClass('ui-corner-all');

		//Input Type Select
		text = jQuery('select');
		text.addClass('ui-corner-all');
		
		//Textarea
		text = jQuery('textarea');
		text.addClass('ui-corner-all');
		
		//Input Type Password
		text = jQuery('input:password');
		text.addClass('ui-corner-all');

		//Input Type a
		text = jQuery('.custom-combobox-corner-right');
		text.removeClass('custom-combobox-corner-right');
		text.addClass('ui-corner-right');					

	});
</script>