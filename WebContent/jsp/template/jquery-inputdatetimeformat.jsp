<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sit.app.core.config.parameter.domain.ParameterConfig"%>
<%@ page import="com.sit.common.CommonAction"%>
<%
	String appLocale = new CommonAction(true).getLocale().getLanguage();
	String dateLocale = ParameterConfig.getParameter().getApplication().getDatetimeLocale().getLanguage();
%>
<script type="text/javascript" src="<s:url value='/js/datepicker/lang/jquery.ui.datepicker-'/><%=appLocale%>.js"></script>
<script type="text/javascript" src="<s:url value='/js/datepicker/datetime/'/><%=dateLocale%>.js"></script>
<script type="text/javascript" src="<s:url value='/js/datepicker/inputdateformat.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/datepicker/inputdatepicker_0.0.1.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/datepicker/inputtimeformat.js' />"></script>
<script type="text/javascript">

		/*JAI
		ไม่แสดง datePicker ตอน Load Page กรณีเป็นหน้าค้น
		*/
		var bControlShowDateFirst=true;

		var datepickerConfig = {
				dateFormat: new Array(),
				contextPath: '<%=request.getContextPath()%>'
		};

		jQuery( document ).ready(function() {
				
			/*JAI
			ไม่แสดง datePicker ตอน Load Page กรณีเป็นหน้าค้น
			*/
			if (window.location.pathname.indexOf("/search")>-1){
				bControlShowDateFirst=false;
			}
			
			
			
			// กำหนด config ต่างๆ ของ datepicker
			var dateFormat = new Array();
			dateFormat["DD/MM/YYYY"] = '<s:text name="dateformatDD/MM/YYYY"/>';
	        dateFormat["MM/DD/YYYY"] = '<s:text name="dateformatMM/DD/YYYY"/>';
	        dateFormat["YYYY/MM/DD"] = '<s:text name="dateformatYYYY/MM/DD"/>';
	        dateFormat["YYYY/DD/MM"] = '<s:text name="dateformatYYYY/DD/MM"/>';
	        dateFormat["MM/YYYY"] = '<s:text name="dateformatMM/YYYY"/>';
	        dateFormat["YYYY/MM"] = '<s:text name="dateformatYYYY/MM"/>';
	        dateFormat["YYYY"] = '<s:text name="dateformatYYYY"/>';
	        datepickerConfig.dateFormat = dateFormat;
	        
			// dd/mm/yyyy show datepicker
			/*jQuery(".input_datepicker").removeClass('input_datepicker').each(function() {
				if (this.id == undefined) {
					jQuery(this).addClass('input_dateformat_dd_sl_mm_sl_yyyy').input_dateformat();
				} else {
					jQuery("#" + this.id).addClass('input_dateformat_dd_sl_mm_sl_yyyy').input_dateformat();
				}
			});*/
			reloadInputDatePicker();
		});

		var timeConfig = {
				timeFormat: new Array()
		};

		jQuery( document ).ready(function() {
			// กำหนด config ต่างๆ ของ input date format
			var timeFormat = new Array();
			timeFormat["HH:MM"] = '<s:text name="timeformatHH_cl_MM"/>';
	        timeConfig.timeFormat = timeFormat;

	        jQuery(".input_timeformat").removeClass('input_timeformat').each(function() {
				// jQuery(this).input_timeformat({
				jQuery(this).css('width', '40px').input_timeformat({
					timeformat :  "hh_cl_mm"
				});
			});
			
			jQuery(".input_timeformat_from_to").removeClass('input_timeformat_from_to').each(function() {
				jQuery("#" + jQuery(this).attr("time-id-from")).css('width', '40px').input_timeformat({
					timeformat :  "hh_cl_mm",
					selectTimeRange: {
						timeTo: jQuery(this).attr("time-id-to")
	                }
				});
				
				jQuery("#" + jQuery(this).attr("time-id-to")).css('width', '40px').input_timeformat({
					timeformat :  "hh_cl_mm",
					selectTimeRange: {
						timeFrom: jQuery(this).attr("time-id-from")
	                }
				});
			});
			
			
			
			jQuery(".input_datetime_from_to").removeClass('input_datetime_from_to').each(function() {
				
				jQuery("#" + jQuery(this).attr("time-id-from")).css('width', '40px').input_timeformat({
					timeformat :  "hh_cl_mm",
					selectTimeRange: {
						timeTo: jQuery(this).attr("time-id-to")
	                },
	                selectDateRange: {
						dateTo: jQuery(this).attr("datepicker-id-to"),
						dateFrom: jQuery(this).attr("datepicker-id-from")
	                }
				});
				
				jQuery("#" + jQuery(this).attr("time-id-to")).css('width', '40px').input_timeformat({
					timeformat :  "hh_cl_mm",
					selectTimeRange: {
						timeFrom: jQuery(this).attr("time-id-from")
	                },
	                selectDateRange: {
						dateTo: jQuery(this).attr("datepicker-id-to"),
						dateFrom: jQuery(this).attr("datepicker-id-from")
	                }
				});
				
				jQuery("#" + jQuery(this).attr("datepicker-id-from")).css('width', '90px').input_dateformat({
					dateformat :  "dd_sl_mm_sl_yyyy",
					selectDateRange: {
					    dateTo: jQuery(this).attr("datepicker-id-to")
	                },
	                selectTimeRange: {
	                	timeTo: jQuery(this).attr("time-id-to"),
						timeFrom: jQuery(this).attr("time-id-from")
	                }
				});
				
				jQuery("#" + jQuery(this).attr("datepicker-id-to")).css('width', '90px').input_dateformat({
					dateformat :  "dd_sl_mm_sl_yyyy",
					selectDateRange: {
					    dateFrom: jQuery(this).attr("datepicker-id-from")
	                },
	                selectTimeRange: {
	                	timeTo: jQuery(this).attr("time-id-to"),
						timeFrom: jQuery(this).attr("time-id-from")
	                }
				});
			});
			
			jQuery(".input_day_month_year").each(function() {
				jQuery("#" + jQuery(this).attr("month-id")).removeClass('input_number').addClass('input_number');
				jQuery("#" + jQuery(this).attr("day-id")).removeClass('input_number').addClass('input_number');
			})
		});

		function reloadInputDatePicker() {
			// dd/mm/yyyy show datepicker
			/*jQuery(".input_datepicker").removeClass('input_datepicker').each(function() {
				jQuery(this).addClass('input_dateformat_dd_sl_mm_sl_yyyy').input_dateformat();
			});*/
			jQuery(".input_datepicker").removeClass('input_datepicker').each(function() {
				jQuery("#" + this.id).css('width', '90px').input_dateformat({
					dateformat :  "dd_sl_mm_sl_yyyy"
				});
			});
			
			jQuery(".input_datepicker_from_to").removeClass('input_datepicker_from_to').each(function() {
				jQuery("#" + jQuery(this).attr("datepicker-id-from")).css('width', '90px').input_dateformat({
					dateformat :  "dd_sl_mm_sl_yyyy",
					selectDateRange: {
					    dateTo: jQuery(this).attr("datepicker-id-to")
	                }
				});
				
				jQuery("#" + jQuery(this).attr("datepicker-id-to")).css('width', '90px').input_dateformat({
					dateformat :  "dd_sl_mm_sl_yyyy",
					selectDateRange: {
					    dateFrom: jQuery(this).attr("datepicker-id-from")
	                }
				});
			});
		}
</script>