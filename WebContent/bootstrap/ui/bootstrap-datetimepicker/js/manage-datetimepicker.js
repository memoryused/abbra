/**
# ---------------------------------------------------------------------------------
# 							MANAGE-DATETIMEPICKER.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
//	//DATETIMEPICKER
//	var configLocale 		 = 'en'; 		/* ParameterConfig applicationLocaleString*/ 	//FIXME
//	var configDatetimeLocale = 'en';		/* ParameterConfig datetimeLocaleString*/		//FIXME
//	createDateTimePicker(configLocale, configDatetimeLocale);
//	manageDateTimepicker();
	
});



function createDateTimePicker(configLocale, configDatetimeLocale){
//	console.log("[ createDateTimePicker ]");
	
	var configFormatDate = 'DD/MM/YYYY'; 	/* ParameterConfig */
	
	
	jQuery("input[data-dp-id]").each(function(index){
		var iddp = jQuery(this).attr("data-dp-id");
		var inputId = jQuery(this).attr("id");
		var inputName = jQuery(this).attr("name");
		var dataClass = jQuery(this).attr("data-class");
		var dataFeedBack = jQuery(this).attr("data-invalid-feedBack");// TODO:
		var dataLabel = jQuery(this).attr("data-label-text");// TODO:
		var htmldp = "";
		if (typeof iddp != 'undefined'){
			jQuery(this).attr("id", "input_dp_"+iddp);
			jQuery(this).attr("name", "");
			jQuery(this).attr("maxlength", 10);
			jQuery(this).attr("placeholder", configFormatDate);
			jQuery(this).addClass("datetimepicker");/*ใส่เพิ่มไว้จับตอน Disabled*/
			
			if(typeof dataLabel != 'undefined'){
				if (jQuery(this).hasClass('requireInput')) {
					htmldp += '<label for="'+jQuery(this).attr('id')+'">'+ dataLabel +'<em>*</em></label>';
				}else{
					htmldp += '<label for="'+jQuery(this).attr('id')+'">'+ dataLabel +'</label>';
				}
			}
			
			if(typeof dataClass != 'undefined'){
				htmldp += '<div class="input-group date '+ dataClass +'" id="'+ iddp +'">';
			}else{
				htmldp += '<div class="input-group date" id="'+ iddp +'">';
			}
			
			htmldp += jQuery(this).prop('outerHTML');//console.log(htmldp);
			htmldp += '<input type="hidden" id="'+inputId+'" name="'+inputName+'" class="form-control">';
			// TODO:
			if(typeof dataFeedBack != 'undefined'){
				htmldp += '<span class="input-group-addon border-right rounded-right">';
				htmldp += '<i class="fa fa-calendar"></i>';
				htmldp += '</span>';
				htmldp += '<div class="invalid-feedback">'+dataFeedBack+'</div>';
			}else{
				htmldp += '<span class="input-group-addon">';
				htmldp += '<i class="fa fa-calendar"></i>';
				htmldp += '</span>';
			}
			htmldp += '</div>';
			jQuery(this).replaceWith(htmldp);
			
			jQuery("#"+iddp).datetimepicker({
				format: configFormatDate,			/* format วันที่ที่ textfield */
	            collapse: true,						/* เลือกวันแล้วปิด calendar  */
	            locale: configLocale,	
	            datetimeLocale: configDatetimeLocale,
	            allowInputToggle : true,			/* focus textfield ก็ให้เปิด calendar */
	            icons: {
	            	time: 'fa fa-clock-o',
	                date: 'fa fa-calendar',
	                up: 'fa fa-angle-up',
	                down: 'fa fa-angle-down',
	                previous: 'fa fa-angle-left',
	                next: 'fa fa-angle-right',
	                today: 'fa fa-calendar-plus-o',
	                clear: 'fa fa-trash-o',
	                close: 'fa fa-times'
                }
	        });
			
			
			jQuery("#"+iddp).on("dp.change", function (e) {
				if(configDatetimeLocale == "th"){
					jQuery(this).find("#"+inputId).val(moment(moment(jQuery("#"+iddp).data("DateTimePicker").date())).format(configFormatDate));
				}
			});
		}
		
/**
 * OPTION BOOTSTRAP DATETIME PICKER
 */		
//		jQuery("#dp_criteria_receivingDateTo").datetimepicker({
//			format: 'MM/DD/YYYY',					/* format วันที่ที่ textfield */
//			dayViewHeaderFormat: "YYYY. MMMM",		/* format การแสดงวันที่ด้านบน */
//			minDate: moment(), 						/* วันปัจจุบันใช้ moment.js ช่วยเรื่องวันที่	| date, moment, string | */
//		    maxDate: moment().add(30, 'days'), 		/* +30วัน นับจากวันปัจจุบันใช้ moment.js ช่วยเรื่องวันที่ | date, moment, string | */
//		    defaultDate: moment().add(2,'days'),	/* กำหนดวันเริ่มต้นของ calendar */
//			toolbarPlacement: 'bottom',				/* ตำแหน่งปุ่มพวก clear | 'default', 'top', 'bottom' | */
//          locale: 'th',							/* เลิอก locale | en th | */
//          showClear: true,						/* แสดงปุ่ม clear */
//        	showTodayButton: true,					/* แสดงปุ่ม วันที่ปัจจุบัน */
//          showClose: true,						/* แสดงปุ่ม close */
//          collapse: true,							/* เลือกวันแล้วปิด calendar  */
//          daysOfWeekDisabled: [0, 6], 			/* ห้ามเลือก เสาอาทิตย์  | 0 1 2 3 4 5 6 | */
//          disabledDates: ['2017-06-10', '2017-06-20']
//        }).on('dp.change', function(e){
//        	console.log("[ DP.CHANGE ]");
//        }).on('dp.show', function(e){
//        	console.log("[ DP.SHOW ]");
//        }).on('dp.hide', function(e){
//        	console.log("[ DP.HIDE ]");
//        });
		
		
		
	});
	
	
	
	
	
	jQuery("input[data-dt-id]").each(function(index){
		var iddt = jQuery(this).attr("data-dt-id");
		var inputId = jQuery(this).attr("id");
		var inputName = jQuery(this).attr("name");
		var htmldt = "";
		
		if (typeof iddt != 'undefined'){
			jQuery(this).attr("id", "input_dt_"+iddt);
			jQuery(this).attr("name", "");
			jQuery(this).attr("maxlength", 5);
			jQuery(this).attr("placeholder", "HH:MM");
			jQuery(this).addClass("datetimepicker datetime input_timeformat");/*ใส่เพิ่มไว้จับตอน Disabled ปรับความกว้าง*/
			
			
//			htmldt += '<div class="input-group date datetime" id="'+ iddt +'">';
			htmldt += jQuery(this).prop('outerHTML');
			htmldt += '<input type="hidden" id="'+inputId+'" name="'+inputName+'" class="form-control">';
//			htmldt += '<span class="input-group-addon">';
//			htmldt += '<i class="fa fa-clock-o"></i>';
//			htmldt += '</span>';
//			htmldt += '</div>';
			jQuery(this).replaceWith(htmldt);
			
			jQuery("#input_dt_"+iddt).datetimepicker({
				format: "HH:mm",			
				allowInputToggle : true,
	            icons: {
	            	time: 'fa fa-clock-o',
	                date: 'fa fa-calendar',
	                up: 'fa fa-angle-up',
	                down: 'fa fa-angle-down',
	                previous: 'fa fa-angle-left',
	                next: 'fa fa-angle-right',
	                today: 'fa fa-calendar-plus-o',
	                clear: 'fa fa-trash-o',
	                close: 'fa fa-times'
                }
	        });
		}
	});
	
}



function manageDateTimepicker(){
//	console.log("[ manageDateTimepicker ]");
	jQuery("input[data-dp-to]").each(function(index){
		var dpid_from = jQuery(this).attr("data-dp-id");
		var dpid_to = jQuery(this).attr("data-dp-to");
		var dp_max 	= jQuery(this).attr("data-dp-max");
		if (jQuery("#"+dpid_to).length > 0){
			jQuery("#"+dpid_to).data("DateTimePicker").useCurrent(false);
			
			//INIT MAX DATE
			if(typeof dp_max !== "undefined" && dp_max != null && dp_max != ""){
				if(jQuery("#"+dpid_from).data("DateTimePicker").date() != null){
					jQuery("#"+dpid_to).data("DateTimePicker").maxDate(moment(jQuery("#"+dpid_from).data("DateTimePicker").date()).add(dp_max, 'days'));
				}
			}
			
			jQuery("#"+dpid_from).on("dp.change", function (e) {
//				console.log("#ONCHANGE FROM : ");
				var dateFrom 	= moment(jQuery("#"+dpid_from).data("DateTimePicker").date());
				var dateTo 		= moment(jQuery("#"+dpid_to).data("DateTimePicker").date());
				if(dateTo.isValid()){
//					console.log(moment(dateFrom).format("YYYYMMDD") + " -- " + moment(dateTo).format("YYYYMMDD"));
					if(moment(dateFrom).format("YYYYMMDD") > moment(dateTo).format("YYYYMMDD")){
						jQuery("#"+dpid_to).data("DateTimePicker").date(dateFrom);
					}
				}
				
				//SET MAX DATE
				if(typeof dp_max !== "undefined" && dp_max != null && dp_max != ""){
					jQuery("#"+dpid_to).data("DateTimePicker").maxDate(moment(dateFrom).add(dp_max, 'days'));
				}
				
				
				
		    });
			
			
			
			
			jQuery("#"+dpid_to).on("dp.change", function (e) {
//				console.log("#ONCHANGE TO : ");
//				jQuery("#"+dpid_from).data("DateTimePicker").maxDate(e.date);
				var dateFrom 	= moment(jQuery("#"+dpid_from).data("DateTimePicker").date());
				var dateTo 		= moment(jQuery("#"+dpid_to).data("DateTimePicker").date());
				
				if(dateFrom.isValid()){
//					console.log(moment(dateFrom).format("YYYYMMDD") + " -- " + moment(dateTo).format("YYYYMMDD"));
					if(moment(dateFrom).format("YYYYMMDD") > moment(dateTo).format("YYYYMMDD")){
//						console.log("DATE FROM > DATE TO");
						jQuery("#"+dpid_from).data("DateTimePicker").date(dateTo);
					}else{
//						console.log("DATE FROM <= DATE TO");
						
					}
				}
				
				
		    });
			
		}
	});
	
	jQuery(".datepicker-datatable").each(function(index){
		jQuery(this).parent().datetimepicker('widgetParent', jQuery(this).closest("div.dataTables_scroll"));			
	});
}