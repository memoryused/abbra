+function($) {
	'use strict';
	
	var SitCheckBoxTreeDialog = function(element, options) {
		//console.log("id =", element);
		var that = this
		
		options.strTreeId = $("#" + options.containerId +" #treeId").val();
		options.strTreeType = $("#" + options.containerId +" #treeType").val();
		options.strEvent = $("#" + options.containerId +" #event").val();
		
		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id')
		this.criteria = $("#" + options.criteriaContainerId)
		
		this.data = undefined;
		this.wrapper = $(this.$element).find('div.modal-body > div.row:last > div:first')
		this.table = $(this.wrapper).find('table:first')
		
		this.fnInitialWidget = function () {
			manageDropdownlistModal()
	    	manageAutocompleteAjaxModal()
	    	manageAutocompleteModal()
		}
		
		this.fnAjaxInit = function() {
			
			var result
			
//			$.ajax({
//				type : "POST",
//				url : options.urlInit,
//				async : false,
//				dataType: "json",
//				data: { "criteria.criteriaType" : options.criteriaSortsType },
//				success : function(dataModel) {
//					result = dataModel
//				}
//		    })
		    
		    var params = {
				id : $("#" + options.excludeIds).val(),
	 			treeId :options.strTreeId, 
				treeType:options.strTreeType,
				event: options.strEvent
			};
			
			//console.log("id",options.excludeIds);
			
		    jQuery.ajax({
				type : "POST",
				url : options.urlInit,
				data : $.param(params),
				async : false,
				dataType: "json",
				success : function(data) {
					
					
					if (data.mapTree == null) {
						alert('<s:text name="30011"/>'); //APPS2015-1339,APPS2015-1338
					} else {
						// เก็บข้อมูล json
						result = data.mapTree;
	
						// นำ html ของ tree ไปใส่ใน div
						$("#" +options.strTreeId + "_VIEW").html(data.htmlTree);
	
						var dialogHeigth = $( window  ).height();
						dialogHeigth = dialogHeigth - 120
						// กำหนด ความกว้าง-ความสูงของ tree ให้เท่ากับ dialog
						$("#" +options.strTreeId + "_VIEW").css("width", "100%");
						$("#" +options.strTreeId + "_VIEW").css("height", dialogHeigth);
	
						// ผูก event click ที่ checkbox
						$("input[name='" +options.strTreeId +"_CHECKBOX']").click(function() {
							checkNodeTreeFn(this);
						});
	
						// set background
						$("ul[id='" +options.strTreeId +"'] li ul").addClass('ui-widget-content').css("border", "0");
						
						// นำมาสร้างเป็น tree
						$("#" +options.strTreeId + "").treeview({
							collapsed : false,
							control : "#" +options.strTreeId + "_CONTROL",
							treeviewId : options.strTreeId
						});
						
						$("." +options.strTreeId + "_SPAN").each(function() {
							/* กรณีที่ไม่มีการ set ค่า attribute description ของ tree ให้ทำการ remove attribute data-tooltip และ data-tooltip-position ออกจาก span tag */
							if ($(this).attr("data-tooltip") == null || $(this).attr("data-tooltip") == "null" || $(this).attr("data-tooltip") == "") {
								$(this).removeAttr('data-tooltip');
								$(this).removeAttr('data-tooltip-position');
							}
							
							// set style mouse over
							$(this).mouseover(function(e) {
							    //cursorX = e.pageX;
							    //cursorY = e.pageY;
							    
							 	// ย้ายตำแหน่ง tooltip จากด้านขวา เป็น ด้านบน
							 	// console.info("divPopup width: " + yWidth+" Cursor at: " + cursorX + ", " + cursorY);
							 	//if (cursorX > yWidth) {
							 		// กรณีที่ตำแหน่งที่แสดง มากกว่า ขนาด dialog ย้ายแสดง tooltip ด้านบน
							 		//$(this).removeAttr("data-tooltip-position");
							 		//$(this).attr("data-tooltip-position", "top");
							 	//}
							 	
								$(this).removeClass("hover");
								$(this).addClass("ui-state-tree-hover");
								$(".collapsable > ." +options.strTreeId + "_SPAN").css("cursor", "pointer");
	
								$(this).click(function() {
									// Clear all selected states
								    //$("." +options.strTreeId + "_SPAN").removeClass('ui-state-tree-active');
	
								 	// Set current as selected
								    //$(this).addClass("ui-state-tree-active");
								});
							});
	
							// set style mouse out
							$(this).mouseout(function() {
								$(this).removeClass("ui-state-tree-hover");
							});
						});
						
						//fnCheckNodeChoose();
						checkNodeChoosefn(element,options);
					}
				}
			});
		    
		    
		    return result
		}
		
		
		this.fnSetRequiredCriteria = function(data) {
			
//			$(that.criteria).find("[name='criteria.headerSortsSelect']").val(data.criteria.headerSortsSelect)
//			$(that.criteria).find("[name='criteria.defaultHeaderSortsSelect']").val(data.criteria.headerSortsSelect)
//			$(that.criteria).find("[name='criteria.orderSortsSelect']").val(data.criteria.orderSortsSelect)
//			$(that.criteria).find("[name='criteria.defaultOrderSortsSelect']").val(data.criteria.orderSortsSelect)
//			$(that.criteria).find("[name='criteria.start']").val(data.criteria.start)
//			$(that.criteria).find("[name='criteria.checkMaxExceed']").val(data.criteria.checkMaxExceed)
//			$(that.criteria).find("[name='criteria.alertMaxExceed']").val(data.criteria.alertMaxExceed)
//			
//			//Set excludeIds
//			$(that.criteria).find("[name='criteria.selectedIds']").val($("#" + options.excludeIds).val())
		}
		
		/*function fnCheckNodeChoose() {
			//console.log("ddddd============", options);
				
			var strUrl = options.urlInit;
			var strTreeId = options.strTreeId;
			var strTreeType = options.strTreeType;
			var strEvent = options.strEvent;
			var ids = $("#" + options.excludeIds).val();
			
			if (strTreeType == "CHECKBOX") {
				 ทำการเลือกค่า checkbox ที่อยู่ในตาราง 
				//checkboxToggle(strTreeId+ "_CHECKBOX", this.checked);
				if (trim(ids) != "") {
					var idsArray = ids.split(',');
					for (var i = 0; i < idsArray.length; i++) {
						var value = jQuery("input[name='" + strTreeId + "_CHECKBOX'][id='" + idsArray[i] + "']").val();
						if (value == undefined) {
							continue;
						}
						var valueIds = value.split('_');
						for ( var j = valueIds.length - 1; j >= 0; j--) {
							jQuery("input[name='" + strTreeId + "_CHECKBOX'][id='" + valueIds[j]+ "']").prop("checked", true);
						}
					}
				}

				//setStyleTree();
			} else {
				if (trim(ids) != "") {
					var idsArray = ids.split(',');
					for(var i = 0; i < idsArray.length; i++){
						var value = jQuery("input[name='HIDDEN_"+idsArray[i]+"']").val();
						if (value == undefined) {
							continue;
						}
						var arrValue = value.split('_');
						for(var j = 0; j < arrValue.length; j++){
							jQuery("#"+arrValue[j]).addClass("ui-state-active");
						}
					}
				}
			}
		}*/
		
		
		
	}
	
	

	SitCheckBoxTreeDialog.VERSION = '0.0.1'
		
	SitCheckBoxTreeDialog.DEFAULTS = {
		urlInit: undefined, // Url ที่ใช้ในการ load required criteria
		chosenCallback: undefined, // Callback หลังจากเลือกข้อมูลที่ Dialog
		createDialogCallback: undefined, // Callback ตอน create
		showDialogCallback: undefined, // Callback ตอน Dialog Initial (ตอนเปิด)
		excludeIds: undefined, // ID ของ Input ที่จะเอาไว้  Where not in
		
		strTreeId: undefined,
		strTreeType: undefined,
		strEvent: undefined
	}
	
	SitCheckBoxTreeDialog.prototype.create = function() {
		
		var that = this
		
		//Append required criteria input
//		$(this.criteria).append('<input type="hidden" name="criteria.criteriaKey" />' +
//				'<input type="hidden" name="criteria.headerSortsSelect" />' +
//				'<input type="hidden" name="criteria.orderSortsSelect" />' +
//				'<input type="hidden" name="criteria.start"/>' +
//				'<input type="hidden" name="criteria.checkMaxExceed"/>' +
//				'<input type="hidden" name="criteria.alertMaxExceed"/>' +
//				'<input type="hidden" name="criteria.defaultHeaderSortsSelect" />' +
//				'<input type="hidden" name="criteria.defaultOrderSortsSelect" />' +
//				'<input type="hidden" name="criteria.criteriaType" value="' + this.options.criteriaSortsType + '" />' +
//				'<input type="hidden" name="criteria.selectedIds"/>')
		
		
		// Fire ajax to initial criteria
		this.data = this.fnAjaxInit()

		// Initial widget on dialog
		//this.fnInitialWidget()
		
		// Create dialog callback
		if(this.options.createDialogCallback != undefined) {
			this.options.createDialogCallback(this.data)
		}
		
		/* SHOW */
		$(this.$element).on('show.bs.modal', function (e) {
			
			
			//onclick="checkboxToggle('<s:property value='#treeId[0]'/>_CHECKBOX', this.checked); setStyleTree();"
				
			//Hide dataTable wrapper
			//$(that.wrapper).hide()
			
			//Get data if it is undefined
			if(that.data == undefined)
				that.data = that.fnAjaxInit()
				
			// Set required criteria
			//that.fnSetRequiredCriteria(that.data)
				
			//Show dialog callback
			if(that.options.showDialogCallback != undefined){
				that.options.showDialogCallback(that.data)
				
				//Set data to undefined
				that.data = undefined
			}
			
		})
		
		/* SHOWN */
		$(this.$element).on('shown.bs.modal', function (e) {
			// do something...
			if(that.options.strTreeType === "CHECKBOX"){
				$("#" +that.options.strTreeId + "_CONTROL input[name='checkAll']").on('click', function (e) {
					checkboxToggle(that.options.strTreeId +"_CHECKBOX", this.checked); 
					setStyleTreefn(that.id, that.options);
				});
				
				
			}
		})		
		
		/* HIDE */
		$(this.$element).on('hide.bs.modal', function (e) {
			// do something...
		})		
		
		/* HIDDEN */
		$(this.$element).on('hidden.bs.modal', function (e) {
			// do something...
		})		
			
		//Show dialog
		$(this.$element).modal('show')		

	}
	
	SitCheckBoxTreeDialog.prototype.close = function() {
		$(this.$element).modal('hide')
	}
	
	SitCheckBoxTreeDialog.prototype.dispose = function() {
		//Set data to undefined
		this.data = undefined
		$(this.$element).modal('dispose')
	}
	
	SitCheckBoxTreeDialog.prototype.choose = function() {
		var that = this
		var elName = that.options.strTreeId+"_CHECKBOX";
		//'<s:property value='#treeId[0]'/>_CHECKBOX'
		
		if (that.options.containerId != "" && $("#" +that.options.containerId) !== undefined && $("#" +that.options.containerId).css("display") !== undefined && $("#" +that.options.containerId).css("display") !== 'none') {
			if ($("#" +that.options.containerId).parent() !== undefined && $("#" +that.options.containerId).parent().css("display") !== undefined && $("#" +that.options.containerId).parent().css("display") !== 'none') {
				// ตรวจสอบการเลือกแบบธรรม
				if (validateSelect(elName) == false) {
					return false;
				}
				// clear table result
				//jQuery('#' + tblId).empty();
				$("#" + that.options.excludeIds).val('');
				
				// function call back
				//callFunc(elName, that.data);
			
				//jQuery(divPopup).dialog('destroy');	
				that.close();
				
				if(that.options.chosenCallback != undefined){
					that.options.chosenCallback(elName, that.data)
				}
			}
		}
		
		
	
	/*var selectIds = that.data;
		console.log("ggggggg=====",selectIds);
		this.close();
		
		if(this.options.chosenCallback != undefined)
			this.options.chosenCallback(data)*/
			
	}

	// PLUGIN DEFINITION
	// =======================

	function Plugin(option, _relatedTarget) {
		
		var args = arguments
		var value
		
		var chain = this.each(function() {
			var $this = $(this)
			var data = $this.data('bs.sitCheckBoxTreeDialog')

			// Call plugin function
			if (typeof option === 'string') {
		        //Copy the value of option, as once we shift the arguments
		        //it also shifts the value of option.
		        if (data[option] instanceof Function) {
		            [].shift.apply(args);
		            value = data[option].apply(data, args)
		        }
		        
		    // Create plugin   
		    } else if (!data) {
		    	var options = $.extend({}, SitCheckBoxTreeDialog.DEFAULTS, $this.data(), typeof option == 'object' && option)
				$this.data('bs.sitCheckBoxTreeDialog', (data = new SitCheckBoxTreeDialog(this, options)))
				data.create(_relatedTarget)
				
			// Reuse plugin
			} else {
				$(data.$element).modal('show')
			}
		})
		
		if (typeof value !== 'undefined')
			return value
			
	    else
	    	return chain
	}
	

	var old = $.fn.sitCheckBoxTreeDialog

	$.fn.sitCheckBoxTreeDialog = Plugin
	$.fn.sitCheckBoxTreeDialog.Constructor = SitCheckBoxTreeDialog

	// MODAL NO CONFLICT
	// =================

	$.fn.sitCheckBoxTreeDialog.noConflict = function() {
		$.fn.sitCheckBoxTreeDialog = old
		return this
	}
	
}(jQuery);

function checkNodeChoosefn(element, options){
	
	//console.log("ddddd============", options);
	
	var strUrl = options.urlInit;
	var strTreeId = options.strTreeId;
	var strTreeType = options.strTreeType;
	var strEvent = options.strEvent;
	var ids = jQuery("#" + options.excludeIds).val();
	
	if (strTreeType == "CHECKBOX") {
		/* ทำการเลือกค่า checkbox ที่อยู่ในตาราง */
		//checkboxToggle(strTreeId+ "_CHECKBOX", this.checked);
		if (trim(ids) != "") {
			var idsArray = ids.split(',');
			for (var i = 0; i < idsArray.length; i++) {
				var value = jQuery("input[name='" + strTreeId + "_CHECKBOX'][id='" + idsArray[i] + "']").val();
				if (value == undefined) {
					continue;
				}
				var valueIds = value.split('_');
				for ( var j = valueIds.length - 1; j >= 0; j--) {
					jQuery("input[name='" + strTreeId + "_CHECKBOX'][id='" + valueIds[j]+ "']").prop("checked", true);
				}
			}
		}

		setStyleTreefn(element, options);
	} else {
		if (trim(ids) != "") {
			var idsArray = ids.split(',');
			for(var i = 0; i < idsArray.length; i++){
				var value = jQuery("input[name='HIDDEN_"+idsArray[i]+"']").val();
				if (value == undefined) {
					continue;
				}
				var arrValue = value.split('_');
				for(var j = 0; j < arrValue.length; j++){
					jQuery("#"+arrValue[j]).addClass("ui-state-tree-active");
				}
			}
		}
	}
}

function getParentNamefn(ids, obj) {
	var parentName = "";
	var arrayIds = ids.split('_');
	for (var i = 1; i < arrayIds.length -1; i++) {
		if (parentName.length > 0) {
			parentName += "&nbsp;&rsaquo;&nbsp;";
		}
		parentName += obj[arrayIds[i]].label;
	}
	return parentName;
}

function setStyleTreefn(element, options){
	
	var strUrl = options.urlInit;
	var strTreeId = options.strTreeId;
	var strTreeType = options.strTreeType;
	var strEvent = options.strEvent;
	
	/* high ligth รายการที่ทำการเลือก */
	jQuery("input[name='" + strTreeId + "_CHECKBOX']").each(function () {
		if (this.checked) {
			// Set current as selected
		    jQuery(this).next().next().addClass("ui-state-tree-active");
		} else {
			// Clear all selected states
		    jQuery(this).next().next().removeClass('ui-state-tree-active');
		}
		
		jQuery(this).click(function() {
			// checkbox ขึ้น
			var value = jQuery(this).val();
			var valueIds = value.split('_');
			 for ( var j = valueIds.length - 1; j >= 0; j--) {
				 if(jQuery("input[name='" + strTreeId + "_CHECKBOX'][id='" + valueIds[j]+ "']").prop('checked') == true){
					// Set current as selected
					jQuery("input[name='" + strTreeId + "_CHECKBOX'][id='" + valueIds[j]+ "']").next().next().addClass("ui-state-tree-active");
				} else {
					// Clear all selected states
				    jQuery("input[name='" + strTreeId + "_CHECKBOX'][id='" + valueIds[j]+ "']").next().next().removeClass('ui-state-tree-active');
				}
			}
				
			// checkbox ลง
			 var id = jQuery(this).attr('id');
			 if(jQuery("input[name='" + strTreeId + "_CHECKBOX'][type=checkbox][value*='" + id + "']").prop('checked') == true){
				// Set current as selected
				jQuery("input[name='" + strTreeId + "_CHECKBOX'][value*='" + id + "']").next().next().addClass("ui-state-tree-active");
			} else {
				// Clear all selected states
			    jQuery("input[name='" + strTreeId + "_CHECKBOX'][value*='" + id + "']").next().next().removeClass('ui-state-tree-active');
			}
		});
	});
}

/*
 * detail: สำหรับ check box ในรูปแบบ Tree การสร้าง check box - ให้ทำการ กำหนด id ของ
 *            check box เป็น value id ของตัวเอง - ค่า value ของ check box ให้ทำการ ค่า id
 *            ของ parent node concat กันด้วย comma จนถึง id ตัวเอง ex: clickNodeTree (this)
 */
function checkNodeTreeFn(checkboxElement) {
	// check ลง
	jQuery("input[name='" + checkboxElement.name + "'][type=checkbox][value*='" + checkboxElement.id + "']").prop("checked", checkboxElement.checked);

	// check ขึ้น ด้วยเงือนไขถ้าไม่มีแม่ตัวไหนไม่มีลูก check ให้ uncheck ทิ้งไป
	var valueIds = (checkboxElement.value.split('-')[0]).split('_');
	for ( var i = valueIds.length - 1; i >= 0; i--) {
		var queryIds = "";
		if (checkboxElement.checked) {
			queryIds = "[id='" + valueIds[i] + "']";
		} else {
			if (jQuery("input[name='" + checkboxElement.name + "'][type=checkbox][value*='" + valueIds[i] + "']:checked[id!='" + valueIds[i] + "']").length == 0) {
				queryIds = "[id='" + valueIds[i] + "']";
			}
		}

		if (queryIds.length > 0) {
			jQuery("input[type=checkbox]" + queryIds).prop("checked", checkboxElement.checked);
		}
	}
}