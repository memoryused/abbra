<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<style type="text/css">
	<s:include value="/css/autocomplete/autocomplete.css"/>
</style>
<script type="text/javascript">
	// กำหนด config ต่างๆ ของ autocomplate
	var autocompleteConfig = {
		contextPath: '<%=request.getContextPath()%>',
		msgShowAll: '<s:text name="30091"/>',
		msgNotMatch: '<s:text name="30090"/>',
		msgMinChar: '<s:text name="60003"/>'
	};

	<s:include value="/js/autocomplete/autocomplete.js"/>
	<s:include value="/js/autocomplete/autocomplete-ajax.js"/>
	<s:include value="/js/autocomplete/autocomplete-ajax-custom.js"/>	

	/** Nationality */
	jQuery(document).ready(function(){
		jQuery('.nationality_autocomplete_ajax').removeClass('nationality_autocomplete_ajax').each(function() {
			var element = this;
			if (jQuery(this).attr('id') != undefined) {
				element = "#" + jQuery(this).attr('id');
			}
			
			jQuery(element).autocompletezAjax([{
				url: "<s:url value='/nationalitySelectItemServlet'/>",	// url สำหรับ request ข้อมูล Nationality
				defaultKey: "",
			    defaultValue: ""
			}]);
		});
		
		jQuery('.nationality_autocomplete').removeClass('nationality_autocomplete').each(function() {
			var element = this;
			if (jQuery(this).attr('id') != undefined) {
				element = "#" + jQuery(this).attr('id');
			}
			
			jQuery(element).autocompletez([{
				url: "<s:url value='/nationalitySelectItemServlet'/>",	// url สำหรับ request ข้อมูล Nationality
				defaultKey: "",
			    defaultValue: ""
			}]);	
		});
	});
	
	/** Airport */
	jQuery(document).ready(function(){
		jQuery('.airportId_autocomplete_ajax').removeClass('airportId_autocomplete_ajax').each(function() {
			var element = this;
			if (jQuery(this).attr('id') != undefined) {
				element = "#" + jQuery(this).attr('id');
			}
			
			jQuery(element).autocompletezAjax([{
				url: "<s:url value='/airportSelectItemServlet'/>",	// url สำหรับ request ข้อมูล Airport
				defaultKey: "",
			    defaultValue: ""
			}]);
		});
		
		jQuery('.airportId_autocomplete').removeClass('airportId_autocomplete').each(function() {
			var element = this;
			if (jQuery(this).attr('id') != undefined) {
				element = "#" + jQuery(this).attr('id');
			}
			
			jQuery(element).autocompletez([{
				url: "<s:url value='/airportSelectItemServlet'/>",	// url สำหรับ request ข้อมูล Airport
				defaultKey: "",
			    defaultValue: ""
			}]);	
		});
		
	});
</script>