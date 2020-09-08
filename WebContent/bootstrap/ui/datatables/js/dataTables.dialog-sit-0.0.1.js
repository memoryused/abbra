+function($) {
	'use strict';

	var SitDataTableDialog = function(element, options) {
		
		var that = this
		
		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id')
		this.criteria = $("#" + options.criteriaContainerId)
		
		this.data = undefined
		this.wrapper = $(this.$element).find('div.modal-body > div.row:last > div:first')
		this.table = $(this.wrapper).find('table:first')
		
		this.fnInitialWidget = function () {
			manageDropdownlistModal()
	    	manageAutocompleteAjaxModal()
	    	manageAutocompleteModal()
		}
		
		this.fnAjaxInit = function() {
			
			var result
			
			$.ajax({
				type : "POST",
				url : options.urlInit,
				async : false,
				dataType: "json",
				data: { "criteria.criteriaType" : options.criteriaSortsType },
				success : function(dataModel) {
					result = dataModel
				}
		    })
		    
		    return result
		}
		
		this.fnSetRequiredCriteria = function(data) {
			
			$(that.criteria).find("[name='criteria.headerSortsSelect']").val(data.criteria.headerSortsSelect)
			$(that.criteria).find("[name='criteria.defaultHeaderSortsSelect']").val(data.criteria.headerSortsSelect)
			$(that.criteria).find("[name='criteria.orderSortsSelect']").val(data.criteria.orderSortsSelect)
			$(that.criteria).find("[name='criteria.defaultOrderSortsSelect']").val(data.criteria.orderSortsSelect)
			$(that.criteria).find("[name='criteria.start']").val(data.criteria.start)
			$(that.criteria).find("[name='criteria.checkMaxExceed']").val(data.criteria.checkMaxExceed)
			$(that.criteria).find("[name='criteria.alertMaxExceed']").val(data.criteria.alertMaxExceed)
			
			//Set excludeIds
			$(that.criteria).find("[name='criteria.selectedIds']").val($("#" + options.excludeIds).val())
		}
	}

	SitDataTableDialog.VERSION = '0.0.1'
		
	SitDataTableDialog.DEFAULTS = {
		urlInit: undefined, // Url ที่ใช้ในการ load required criteria
		urlSearchByIds: undefined, // Url เอาไว้ search ข้อมูลของ multi choose dialog
		chosenCallback: undefined, // Callback หลังจากเลือกข้อมูลที่ Dialog
		createDialogCallback: undefined, // Callback ตอน create
		showDialogCallback: undefined, // Callback ตอน Dialog Initial (ตอนเปิด)
		excludeIds: undefined, // ID ของ Input ที่จะเอาไว้  Where not in
		appendSelectIds: true
	}
	
	SitDataTableDialog.prototype.create = function() {
		
		var that = this
		
		//Append required criteria input
		$(this.criteria).append('<input type="hidden" name="criteria.criteriaKey" />' +
				'<input type="hidden" name="criteria.headerSortsSelect" />' +
				'<input type="hidden" name="criteria.orderSortsSelect" />' +
				'<input type="hidden" name="criteria.start"/>' +
				'<input type="hidden" name="criteria.checkMaxExceed"/>' +
				'<input type="hidden" name="criteria.alertMaxExceed"/>' +
				'<input type="hidden" name="criteria.defaultHeaderSortsSelect" />' +
				'<input type="hidden" name="criteria.defaultOrderSortsSelect" />' +
				'<input type="hidden" name="criteria.criteriaType" value="' + this.options.criteriaSortsType + '" />' +
				'<input type="hidden" name="criteria.selectedIds"/>')
		
		
		// Fire ajax to initial criteria
		this.data = this.fnAjaxInit()
		
		// Initial widget on dialog
		this.fnInitialWidget()
		
		// Create dialog callback
		if(this.options.createDialogCallback != undefined) {
			this.options.createDialogCallback(this.data)
		}
		
		/* SHOW */
		$(this.$element).on('show.bs.modal', function (e) {
			
			//Hide dataTable wrapper
			$(that.wrapper).hide()
			
			//Get data if it is undefined
			if(that.data == undefined)
				that.data = that.fnAjaxInit()
				
			// Set required criteria
			that.fnSetRequiredCriteria(that.data)
				
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
	
	SitDataTableDialog.prototype.close = function() {
		$(this.$element).modal('hide')
	}
	
	SitDataTableDialog.prototype.singleChoose = function(data) {
		
		if(this.options.appendSelectIds) {
			var ids = $("#" + this.options.excludeIds).val().length == 0 ? [] : $("#" + this.options.excludeIds).val().split(',')

			ids.push(data.id)
			
			$("#" + this.options.excludeIds).val(ids)
		}
		
		this.close()
		
		if(this.options.chosenCallback != undefined)
			this.options.chosenCallback(data)
	}
	
	SitDataTableDialog.prototype.multiChoose = function() {
		var that = this
		var selectIds = $("#" + that.options.table).sitDataTable("getSelectedIds")
		
		if(selectIds.length == 0){
			alert(validateMessage.CODE_10001);
			return
		}
		
		$.ajax({
			type : "POST",
			url : that.options.urlSearchByIds,
			async : false,
			dataType: "json",
			data: { "criteria.selectedIds" : (selectIds + "") },
			success : function(data) {
				
				if(that.options.appendSelectIds) {
					var ids = $("#" + that.options.excludeIds).val().length == 0 ? [] : $("#" + that.options.excludeIds).val().split(',')

					data.lstResult.forEach(function(item, index) {
						ids.push(item.id)
					})
					
					$("#" + that.options.excludeIds).val(ids)
				}
				
				that.close()
				
				if(that.options.chosenCallback != undefined)
					that.options.chosenCallback(data.lstResult)
			}
	    }) 
	}

	// PLUGIN DEFINITION
	// =======================

	function Plugin(option, _relatedTarget) {
		
		var args = arguments
		var value
		
		var chain = this.each(function() {
			var $this = $(this)
			var data = $this.data('bs.sitDataTableDialog')

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
		    	var options = $.extend({}, SitDataTableDialog.DEFAULTS, $this.data(), typeof option == 'object' && option)
				$this.data('bs.sitDataTableDialog', (data = new SitDataTableDialog(this, options)))
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
	

	var old = $.fn.sitDataTableDialog

	$.fn.sitDataTableDialog = Plugin
	$.fn.sitDataTableDialog.Constructor = SitDataTableDialog

	// MODAL NO CONFLICT
	// =================

	$.fn.sitDataTableDialog.noConflict = function() {
		$.fn.sitDataTableDialog = old
		return this
	}
	
}(jQuery);