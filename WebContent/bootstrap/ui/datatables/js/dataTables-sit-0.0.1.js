+function($) {
	'use strict';

	var SitDataTable = function(element, options) {
		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id')
		
		this.colData = undefined
		this.wrapper = $(element).parent()
		this.criteria = $("#" + options.criteriaContainerId)
		
		this.confirmMaxExceed = false
		this.initComplete = false
		this.initCompleteFixedColumns = false
		this.order = []
		this.selectedIds = []
		
		//Default checkbox id name
		this.defaultCheckboxId = "dt-checkbox-" + this.id
		
		//Use when update status
		this.updateData = undefined
		
		//Keep checkbox index to use on responsive child row draw
		this.colCheckboxIndex = -1
		
		// Convert criteria to json
		this.fnCriteriaToJSON = function() {
			var criteriaArray = $("#" + options.criteriaContainerId).find(":input").serializeArray()
			var json = { }
			criteriaArray.forEach(function(item, index) {
				if(item.value.length > 0 && item.value != undefined && item.value != 'undefined')
					json[item.name] = item.value
			})
			return json
		}
		
		// Jump to page
		this.fnJumpToPage = function(page) {
			if($(element).DataTable().page.info().page != (page - 1))
				$(element).DataTable().page(page - 1).draw('page')
		}
		
		
		// DOM generator
		this.fnGenerateDOM = function() {
			
			var top, bottom = false
			
			if(this.options.pageNavi == 'top')
				top = true
			else if(this.options.pageNavi == 'bottom')
				bottom = true
			else
				top = bottom = true
			
			var dom = "<'row'<'col-sm-12 col-md-8'" + (this.options.totalDataInfo ? 'i':'') + "><'col-sm-12 col-md-4'"
			
			if(top) {
				dom += 'p>>'
				if(this.options.pageJump)
					dom += "<'row jumpToolbar'<'col-sm-5 col-md-9'><'col-sm-7 col-md-3 jumpDiv'>>"
			} else {
				dom += '>>'
			}
			
			dom += "<'row'<'col-sm-12 checkboxToolbar'>>"	
					
			dom += "<'row'<'col-sm-12'tr>>"
			
			dom += "<'row'<'col-sm-5 col-md-8 dt-footer'><'col-sm-7 col-md-4'"
			
			if(bottom) {
				dom += "p>>"
				if(this.options.pageJump)
					dom += "<'row jumpToolbar'<'col-sm-6 col-md-9'><'col-sm-6 col-md-3 jumpDiv'>>"
			} else
				dom += '>>'
					
			return dom
		}
		
		// Windows resize
		var that = this;
		this.rtime;
		this.timeout = false;
		this.delta = 200;
		this.fnRecalculateColumns = function() {
        	
			 // Do something when window resizing is done.
			 if (new Date() - that.rtime < that.delta) {
				 setTimeout(that.fnRecalculateColumns, that.delta);
				 
			 } else {
				 that.timeout = false;
					
				// Recalculate columns only when table wrapper is visible
				if($(that.wrapper).is(":visible"))
					$(that.$element).DataTable().columns.adjust().responsive.recalc();
			 }               
       }
		
		
		// Set page length of pageNavigate
		if(typeof DATATABLE_CONFIG == 'undefined') {
			$.fn.DataTable.ext.pager.numbers_length = 5
		} else {
			$.fn.DataTable.ext.pager.numbers_length = DATATABLE_CONFIG.pageLength == undefined 
			|| DATATABLE_CONFIG.pageLength == null
			|| DATATABLE_CONFIG.pageLength < 5 ? 5 : DATATABLE_CONFIG.pageLength
		}
		
		// Not dialog
		if(this.options.dialogType == undefined) {
			
			//Append required criteria input
			$(this.criteria).append('<input type="hidden" name="criteria.criteriaKey" />' +
					'<input type="hidden" name="criteria.headerSortsSelect" />' +
					'<input type="hidden" name="criteria.orderSortsSelect" />' +
					'<input type="hidden" name="criteria.start"/>' +
					'<input type="hidden" name="criteria.checkMaxExceed"/>' +
					'<input type="hidden" name="criteria.alertMaxExceed"/>' +
					'<input type="hidden" name="criteria.defaultHeaderSortsSelect" />' +
					'<input type="hidden" name="criteria.defaultOrderSortsSelect" />' +
					'<input type="hidden" name="' + this.options.criteriaKeyTmp + '" class="clearform" />' + 
					'<input type="hidden" name="criteria.criteriaType" value="' + this.options.criteriaSortsType + '" />')
					
			
			//Append pk input for submit to edit/view
			if(this.options.pk != undefined && $(this.criteria).closest('form').find("[name='" + this.options.pk + "']").length == 0)
				$(this.criteria).closest('form').append('<input type="hidden" name="' + this.options.pk + '" />')
			
			//Initial required criteria
			if(this.options.criteriaOnSession == undefined || this.options.criteriaOnSession == '') {
				
				var that = this
				
				$.ajax({
					type : "POST",
					url : this.options.urlInit,
					async : false,
					dataType: "json",
					data: { "criteria.criteriaType": that.options.criteriaSortsType },
					success : function(data) {
						
						$(that.criteria).find("[name='criteria.headerSortsSelect']").val(data.criteria.headerSortsSelect)
						$(that.criteria).find("[name='criteria.defaultHeaderSortsSelect']").val(data.criteria.defaultHeaderSortsSelect)
						$(that.criteria).find("[name='criteria.orderSortsSelect']").val(data.criteria.orderSortsSelect)
						$(that.criteria).find("[name='criteria.defaultOrderSortsSelect']").val(data.criteria.defaultOrderSortsSelect)
						$(that.criteria).find("[name='criteria.start']").val(data.criteria.start)
						$(that.criteria).find("[name='criteria.checkMaxExceed']").val(data.criteria.checkMaxExceed)
						$(that.criteria).find("[name='criteria.alertMaxExceed']").val(data.criteria.alertMaxExceed)
					}
			    })
				
			} else {
				
				var criteria = JSON.parse(this.options.criteriaOnSession)
				
				$(this.criteria).find("[name='criteria.criteriaKey']").val(criteria.criteriaKey)
				$(this.criteria).find('[name="criteria.defaultHeaderSortsSelect"]').val(criteria.defaultHeaderSortsSelect)
		    	$(this.criteria).find('[name="criteria.defaultOrderSortsSelect"]').val(criteria.defaultOrderSortsSelect)
		    	$(this.criteria).find('[name="criteria.headerSortsSelect"]').val(criteria.headerSortsSelect)
		    	$(this.criteria).find('[name="criteria.orderSortsSelect"]').val(criteria.orderSortsSelect)
		    	$(this.criteria).find('[name="criteria.start"]').val(criteria.start)
		    	$(this.criteria).find('[name="criteria.linePerPage"]').val(criteria.linePerPage)
		    	$(this.criteria).find('[name="criteria.checkMaxExceed"]').val(criteria.checkMaxExceed)
		    	
		    	if(this.options.initCriteriaCallback != undefined)
		    		this.options.initCriteriaCallback(criteria)
			}
			
		} else {
			
		}
	}

	SitDataTable.VERSION = '0.0.1'

	SitDataTable.DEFAULTS = {
			
		criteriaContainerId: undefined,
		criteriaSortsType: "",		// ตัวแปรตัวนึงที่อยู่ใน class Criteria ของแต่ละคน ใช้ในการแยก Default HeaderSorts
		criteriaKeyTmp: "",		// 
		criteriaOnSession: undefined,
		initCriteriaCallback: undefined,
		urlInit: "",
		urlSearch: "",
		totalDataInfo: true,	// รายการที่ค้นหาทั้งหมด
		pageNavi: 'both', // 'top', 'bottom', 'both'
		pageJump: false, // true = has pageJump bar, false = no pageJump bar 
		showDetailImmediately: false,
		dialogType: undefined, // 'multi' หรือ 'single'
		retainPageAfterSort: true,
		edit: {
			url: "",
			auth: false //AUTHORIZE
		},
		view: {
			url: "",
			auth: false //AUTHORIZE
		},
		pk: "id",
		//height: 300, // Default Height
		fixedColumns: undefined, //
		/*
		 	fixedColumns : {
		 		leftColumns: 0,
		 		rightColumns: 0
		 	}
		 */
		
		createdRowCallback: undefined, // Callback ตอน Row ได้ถูกสร้างแล้ว
		searchCompleteCallback: undefined, // Callback หลังจากกดปุ่ม Search
		selectCallback: undefined, // Callback ตอน check สำหรับ radio, checkbox และ row
		deselectCallback: undefined, // Callback ตอน uncheck สำหรับ checkbox และ        row 
		footerIcon: undefined,
		select: {
			style: undefined, // 'single', 'multi' and 'os'
			info: true
		}
	}
	
	SitDataTable.prototype.create = function() {
		this.initSorting()
		this.createSitDataTableBox()
		this.createSitDataTableControl()
	}
	
	SitDataTable.prototype.defaultSorting = function() {
		
		$(this.criteria).find("[name='criteria.orderSortsSelect']").val($(this.criteria).find("[name='criteria.defaultOrderSortsSelect']").val())
		$(this.criteria).find("[name='criteria.headerSortsSelect']").val($(this.criteria).find("[name='criteria.defaultHeaderSortsSelect']").val())
		this.initSorting()
	}

	SitDataTable.prototype.initSorting = function() {

		var hSelect = $(this.criteria).find("[name='criteria.headerSortsSelect']").val()
		var oSelect = $(this.criteria).find("[name='criteria.orderSortsSelect']").val()
		
		if(hSelect.length > 0 && oSelect.length > 0){
	    	var hs = hSelect.split(",")
	    	var os = oSelect.split(",")
	    	for ( var i = 0; i < hs.length; i++)
	    		this.order[i] = [hs[i], os[i].toLowerCase()]
		}
	}
	
	SitDataTable.prototype.createSitDataTableBox = function() {
		
		var that = this
		
		var colData = [];
		var dataContent;
		
		$(this.$element).append('<tbody><tr> <td colspan="' + $(this.$element).find("th").length + '" class="dataTables_empty">Loading data from server</td> </tr></tbody>')
		
		$(this.$element).find("th").each(function(index) {
			
			/* ORDER */
			if($(this).attr('class') == "col-order"){
				
				dataContent = {
						data: null, 
						className: "text-center",
						orderable: false, 
						defaultContent: "",
						render: function (data, type, full, meta) {
				            return meta.row + ($(that.criteria).find("[name='criteria.linePerPage']").val() * that.$element.DataTable().page()) + 1
				        }
				}
				
			
			/* CHECKBOX */	
			} else if($(this).attr('class') == "col-checkbox"){
				
				//Keep checkbox index to use on responsive child row draw
				that.colCheckboxIndex = index
				
				//สร้าง checkbox สำหรับ th
				$(this).html("<div class='checkbox checkbox-datatable'> <input id='" + that.defaultCheckboxId + "' type='checkbox'> <label for='" + that.defaultCheckboxId + "'>&nbsp;</label> </div>")
				
				//Bind header checkbox on change event
				var headerCheckbox = $("#" + that.id + " #dt-checkbox-" + that.id)
				$(headerCheckbox).click(function(){
					if($(this).is(':checked'))
						$("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:not(:checked)").click()
					else
						$("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:checked").click()
				})
				
				dataContent = {
								data: null, 
								className: "col-checkbox", 
								orderable: false, 
								defaultContent: "",
								render: function (data, type, full, meta) {
									
						             return "<div class='checkbox checkbox-datatable'>" + 
					 							"&nbsp;" +
					 							"<input val='" + data.id + "' id='" + that.defaultCheckboxId + "-" + meta.row + "' type='checkbox' " +
					 							(that.selectedIds.indexOf(parseInt(data.id, 10)) > -1 ? "checked='' " : '') + ">" + // ถ้ามี id อยู่ใน selectedIds
					 							"<label for='" + that.defaultCheckboxId + "-" + meta.row + "'>&nbsp;</label>" +
					 						"</div>"
					 							
						        }, createdCell: function (cell, cellData, rowData, rowIndex, colIndex ) {
						        	
						        	//Bind on change event
						        	$(cell).find(':checkbox').click(function() {
						        		
						        		//Find index of id
						        		var index = that.selectedIds.indexOf(parseInt(rowData.id, 10))
						        		
						        		//Checked
						        		if($(this).is(':checked')) {
						        			if(index == -1) {
						        				that.selectedIds.push(parseInt(rowData.id, 10))
						        				if(that.options.selectCallback != undefined)
						        					that.options.selectCallback(rowData, $(that.$element).DataTable().row(rowIndex).node(), rowIndex)
						        			}
						        		//Uncheck		
						        		} else {
						        			if(index > -1) {
						        				that.selectedIds.splice(index, 1)
						        				if(that.options.deselectCallback != undefined)
						        					that.options.deselectCallback(rowData, $(that.$element).DataTable().row(rowIndex).node(), rowIndex)
						        			}
						        		}
						        		
						        		//ถ้า checkbox ทั้งหมดเป็น checked
						        		if($("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:checked").length == $("#" + that.id + " [id^=" + that.defaultCheckboxId+ "-]").length)
						        			$(headerCheckbox).prop("checked", true)
						        		else
						        			$(headerCheckbox).prop("checked", false)

						        		//Update the data shown in the FixedColumns	
										if(that.options.fixedColumns != undefined)
											$(that.$element).DataTable().fixedColumns().update()
						        	})
								}
				}
			
			/* RADIO */	
			} else if($(this).attr('class') == "col-radio"){
				
				dataContent = { data: null, 
								className: "radio",
								orderable: false,
								defaultContent: "",
								render: function (data, type, full, meta) {
						             return "<div class='radio'>" + 
					 							"<input name='radio-" + that.id + "' id='dt-radio-" + that.id + "-" + meta.row + "' type='radio'>" +
					 							"<label for='dt-radio-" + that.id + "-" + meta.row + "'>&nbsp;</label>" +
					 						"</div>"
						        },
								createdCell: function ( cell, cellData, rowData, rowIndex, colIndex ) {
									
										$(cell).find(':radio').change(function() {
						        		
						        		var index = that.selectedIds.indexOf(parseInt(rowData.id, 10))
						        		
						        		if($(this).is(':checked')) {
						        			that.selectedIds.splice(0, that.selectedIds.length)
					        				that.selectedIds.push(parseInt(rowData.id, 10))
					        				
					        				if(that.options.selectCallback != undefined)
					        					that.options.selectCallback(rowData, $(that.$element).DataTable().row(rowIndex).node(), rowIndex)
						        		}
						        	})
								}
							}
				
			/* VIEW */	
			} else if($(this).attr('class') == "col-view"){
				
				dataContent = {
						data: null, 
						className: "text-center",
						orderable: false, 
						defaultContent: "",
						render: function (data, type, full, meta) {
				            
				            if(that.options.view != undefined && that.options.view.auth)
				            	return "<a href='#' title='view'><span class='text-default'><i class='fa fa-search fa-flip-horizontal'></i></span></a>"
			    	    	else
			    	    		return "<span class='text-muted'><i class='fa fa-search fa-flip-horizontal'></i></span>";
				            
				        }, 
				        createdCell: function ( cell, cellData, rowData, rowIndex, colIndex ) {
							
				        	$(cell).find('a').click(function(e) {
				        		//If this method is called, the default action of the event will not be triggered.
				        		e.preventDefault()
				        		submitAction(that.options.view.url, that.options.pk, rowData.id)
				        	})
			        	}
					}
			
			/* EDIT */
			} else if($(this).attr('class') == "col-edit"){
				dataContent = {
						data: null, 
						className: "text-center",
						orderable: false, 
						defaultContent: "",
						render: function (data, type, full, meta) {
				            
				            if(that.options.edit != undefined && that.options.edit.auth)
				            	return "<a href='#' title='edit'> <span class='text-default'> <i class='fa fa-pencil'> </i></span></a>"
			    	    	else
			    	    		return "<span class='text-muted'><i class='fa fa-lg fa-pencil'></i></span>";
				        }, 
				        createdCell: function ( cell, cellData, rowData, rowIndex, colIndex ) {
							
				        	$(cell).find('a').click(function(e) {
				        		e.preventDefault() //If this method is called, the default action of the event will not be triggered.
				        		submitAction(that.options.edit.url, that.options.pk, rowData.id)
				        	})
			        	}
					}
				
			/* STATUS */	
			} else if($(this).attr('class') == 'col-status'){
				
				dataContent = {
						data: null, 
						className: "text-center",
						orderable: $(this).attr('data-orderable') == 'false' ? false : true, 
						defaultContent: "",
						render: function (data, type, full, meta) {
				            if(data.active.code == 'Y')
				            	return "<span class='green-text' title='"+validateMessage.CODE_ACTIVE+"'><i class='fa fa-check'></i></span>"
			    	    	else
			    	    		return "<span class='deep-orange-text' title='"+validateMessage.CODE_INACTIVE+"'><i class='fa fa-ban'></i></span>"
				        }, 
				        createdCell: function ( cell, cellData, rowData, rowIndex, colIndex ) {
						
			        	}
					}
				
			} else {
				dataContent = { 
						data: $(this).attr('data-name'), 
						className: $(this).attr('class'), 
						defaultContent: "", 
						orderable: $(this).attr('data-orderable') == 'false' ? false : true
				}
			}
			
			colData.push(dataContent)
		})
		
		this.colData = colData
	}
	
	
	SitDataTable.prototype.createSitDataTableControl = function() {
		
		var that = this;
		
		var options = {
				autoWidth: true,
				renderer: "bootstrap",
				scrollCollapse: false, // Allow the table to reduce in height when a limited number of rows are shown.
				processing: true,
				serverSide: true,
				scrollY: that.options.height,
				scrollX: false, //false - No horizontal scrolling, true - Enable horizontal scrolling in the table
				dom: that.fnGenerateDOM(),
				order: that.order,
				columns: that.colData,
				pagingType: "full_numbers",	// style pageNavigate
				pageLength: $(that.criteria).find("[name='criteria.linePerPage']").val(),
				displayStart: parseInt($(that.criteria).find("[name='criteria.start']").val()),
				language: that.getLanguage(),
				ajax: {
					type: "POST",
					url : that.options.urlSearch,
					data : that.fnCriteriaToJSON(),
					dataSrc : "result"
				},
				customs: {
					criteriaContainer: that.criteria,
					retainPageAfterSort: that.options.retainPageAfterSort
				},
		        createdRow: function(row, data, index) {
		        	
		        	//Bind event on row for single choose dialog
		        	if(that.options.dialogType == 'single') {
		        		$(row).on('dblclick', function () {
		        			var modalId = $(that.$element).closest('.modal').attr('id')
		        			$('#' + modalId).sitDataTableDialog('singleChoose', data)
		        		}).css('cursor', 'pointer')
		        		
		        	} else if(that.options.dialogType == 'multi') {
		        		//Select row if it has been selected before
		        		if(that.options.select.style != undefined && that.selectedIds.indexOf(parseInt(data.id, 10)) > -1)
		        			$(that.$element).DataTable().row(row).select()
		        	}
		        	
		        	// Not fixed Columns
		        	if(that.options.fixedColumns == undefined) {
		        		//Hover event
			        	$(row).hover(
			        			  //handlerIn
			        			  function(e) {
			        				$(this).addClass('bg-info color-white')
			        			  }, 
			        			  
			        			  //handlerOut
			        			  function(e) {
			        				  $(this).removeClass('bg-info color-white')
			        			  }
			        	)
		        	}
		        	
		    		if(that.options.createdRowCallback != undefined)
		    			that.options.createdRowCallback(row, data)
		    		
		            return row;
		        },
		        initComplete: function(settings) {
		        	
		        	//Add class hide-padding ให้กับ wrapper ตาม requirement Saranyu.s
		        	$($(that.$element).DataTable().table().container()).addClass("hide-padding")
		        	
		        	//Footer HTML
		        	if(that.options.footerIcon != undefined) {
		        		
		        		//Footer dom
		        		var footer =  $(that.wrapper).find(".dt-footer")
		        			
		        		that.options.footerIcon.forEach(function(json) {
		        			
		        			var html = '<a href="javascript:void(0)">'
		        				html += '<span class="">'
		        				
		        				if(json.icon.indexOf('/') > -1)
		        					html += '<img style="vertical-align: sub;" src="' + json.icon + '" height="15">'
		        				else
		        					html += '<i class="fa ' + json.icon + ' '+ (json.color == undefined ? '' : json.color) +'"></i>'
		        				
		        				html += json.text
		        				html += '</span>'
		        				html += '</a>&nbsp;&nbsp;&nbsp;'
		        					
		        			//Append icon
		    		        $(footer).append(html)
		    		        
		    		        //Bind event click
		    		        if(json.event != undefined && json.event instanceof Function && json.auth == 'true')
		    		        	$(footer).find('a:last').click(json.event)
		    		        else
		    		        	$(footer).find('a:last').addClass('icon-disabled')
		        		})
		        	}
		        		
    				//Create jumpToolbar
		    		if(that.options.pageJump) {
		    			
			    		//Loop paginate to draw jump bar
			    		$(that.wrapper).find('div.dataTables_paginate').each(function() {
			    			
			    			var divPaginate = this
			    			var divJumpToolbar = $(divPaginate).closest('div.row').next()
			    			
			    			//Toggle icon show/hide jump bar
			    			$(this).before('<i class="fa fa-chevron-circle-down float-right" data-show="false" aria-hidden="true" style="cursor:pointer; padding-left:10px; padding-top:12px;"></i>')
			    			var icon = $(this).parent().find('i')
			    			
			    			$(icon).click(function() {
				    			var div = $(divPaginate).closest('div.row').next()
				    			
				    			if($(this).attr('data-show') == 'false') {
				    				$(this).removeClass("fa-chevron-circle-down").addClass("fa-chevron-circle-up").attr('data-show', 'true')
				    				$(div).slideDown()
				    			} else {
				    				$(this).removeClass("fa-chevron-circle-up").addClass("fa-chevron-circle-down").attr('data-show', 'false')
									$(div).slideUp()
								}
			    			})
				    		
			    			var jumpInputHTML = '<div class="input-group" style="padding-right: 25px;">' +
		    			     '<input class="form-control" placeholder="Jump to page..">' +
		    			     '<span class="input-group-btn">' +
		    			     	'<button class="btn btn-default btn-datatable-jump" disabled="" type="button">Jump.</button>' +
		    			     '</span>' +
		    			     '</div>'
			    			
				    		//Jump input
			    			$(divJumpToolbar).find('div.jumpDiv').html(jumpInputHTML)
						    		
				    		var input = $(divJumpToolbar).find('div.jumpDiv').find('input')
				    		var button = $(divJumpToolbar).find('div.jumpDiv').find('button')
				    		
				    		button.css('margin', '0') // Delete custom margin
				    		
				    		button.click(function() {
				    			that.fnJumpToPage($(input).val())
				    			$(input).val('')
				    			$(this).attr('disabled', '')
				    		})
				    		
				    		input.keypress(function (e) {
				    			
				    			 //Trigger jump if this key is ENTER
				    			 if(e.which == 13 && this.value > 0) {
				    				 e.preventDefault()
				    		    	 $(button).click()
				    		    	 $(input).val('')
				    			 }
				    			 
				    			 //Filter number
				    		     else if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57))
				    		    	 return false
				    		})
				    		
				    		input.keyup(function (e) {
				    			
				    			//Empty value
				    			if(this.value.length == 0) {
				    				 //Disable jump button
				    				$(button).attr('disabled', '')
				    			} else {
				    				
				    				//Value is less than 1
				    				if(this.value < 1)
					    				this.value = 1
					    				
					    			//Value is more than last page
					    			else if(this.value > $(that.$element).DataTable().page.info().pages)
					    				this.value = $(that.$element).DataTable().page.info().pages
					    			
					    			//Enable jump button
					    			$(button).removeAttr('disabled')
				    			}
				    		})
				    		
				    		//Hide toggle jumpbar icon
				    		if($(that.wrapper).find('.dataTables_paginate').css('visibility') == 'hidden')
				    			$(that.wrapper).find('.dataTables_paginate').parent().find('i').hide()
				    		
				    		//Hide jump toolbar (Default)
				    		$(divJumpToolbar).hide()
			    		})
		    		}
		        },
				drawCallback: function( settings ) {
					
					if($(that.$element).DataTable().page.info().pages == '0') {
						$(that.wrapper).hide()
						
					} else {
						
						if(!that.initComplete) {
							
							// set flag init
							that.initComplete = true
							
							// show table
							$(that.wrapper).show()
							
							// set criteriaKey
					    	$(that.criteria).find("[name='criteria.criteriaKey']").val(settings.json.criteria.criteriaKey)
					    	
					    	// set tmp criteriaKey
					    	if(that.options.dialogType == undefined) 
					    		$(that.criteria).find("[name='" + that.options.criteriaKeyTmp + "']").val(settings.json.criteria.criteriaKey)
							
					    	//dom divPaginate	
					    	var divPaginate	= $(that.wrapper).find('.dataTables_paginate')
					    		
					     	// ซ่อน page navigate ถ้ามีแค่ 1 page
					    	if($(that.$element).DataTable().page.info().pages == '1') {
					    		
					    		$(divPaginate).css('visibility', 'hidden')
					    		
					    		$(divPaginate).parent().each(function(i) {
					    			$(this).find('i').hide()
					    			$(that.wrapper).find('.jumpToolbar:eq(' + i + ')').hide()
					    		})
					    		
					    	} else {
					    		$(divPaginate).css('visibility', 'visible')
					    		
					    		$(divPaginate).parent().each(function(i) {
						    		if($(this).find('i').attr('data-show') == 'false')
						    			$(this).find('i').show()
						    		else {
						    			$(this).find('i').show()
						    			$(that.wrapper).find('.jumpToolbar:eq(' + i + ')').show()
						    		}
					    		})
					    	}
							
					    	// ถ้าเป็น table แบบปกติ
							if(that.options.fixedColumns == undefined) {
								
								// คำนวนความกว้างของ columns ใหม่เพราะตอนค้นหาครั้งแรก table แบบธรรมดา (responsive) จะเบี้ยว ไม่รู้ทำไม -*-
					    		$(that.$element).DataTable().columns.adjust().responsive.recalc() 
								
					    		// ผูก event windows resize เพื่อ recalculate ความกว้างของ columns ใหม่
								$(window).resize(function() {
									
									that.rtime = new Date();
								    
								    if (that.timeout === false) {
								    	that.timeout = true;
								        setTimeout(that.fnRecalculateColumns, that.delta);
								    }
								});
								
						    	// trigger searchCompleteCallback
						    	if(that.options.searchCompleteCallback != undefined)
						    		that.options.searchCompleteCallback(settings)
							}
							
							//Manage header checkbox
				    		if($("[id^=" + that.defaultCheckboxId + "-]:checked").length == $("[id^=" + that.defaultCheckboxId+ "-]").length)
				    			$("#" + that.defaultCheckboxId).prop("checked", true)
				    		else
				    			$("#" + that.defaultCheckboxId).prop("checked", false)
							
						} else {
							
							//Manage header checkbox
				    		if($("[id^=" + that.defaultCheckboxId + "-]:checked").length == $("[id^=" + that.defaultCheckboxId+ "-]").length)
				    			$("#" + that.defaultCheckboxId).prop("checked", true)
				    		else
				    			$("#" + that.defaultCheckboxId).prop("checked", false)
						}
					}
				}
			}
		
		//Select options
		if(this.options.select.style != undefined) {
			
			options.select = {
					style: this.options.select.style,
					info: this.options.select.info
			}
			
			if(this.options.select.style != 'single') {
				
				options.buttons = [
			        'selectAll',
			        'selectNone'
			    ]
				
				options.dom =   "<'row'<'col-sm-6'" + (that.options.totalDataInfo ? 'i':'') + "><'col-sm-6'" + (that.options.pageNavi.top ? 'p':'') + ">>" +
				   "<'row'<'col-sm-12 text-left'B>>" +
				   "<'row'<'col-sm-12'tr>>" +
				   "<'row'<'col-sm-6'><'col-sm-6'" + (that.options.pageNavi.bottom ? 'p':'') + ">>"
			}
		}
		
		//table default style
		$(this.$element).addClass("table table-striped table-bordered dt-responsive nowrap").attr('width', '100%').attr('cellspacing', '0')
		
		//If there's fixedColumns options
		if(this.options.fixedColumns != undefined) {
			
			// Remove responsive class
			$(this.$element).removeClass('dt-responsive')
			
			options.fixedColumns = this.options.fixedColumns
			options.scrollX = true	//false - No horizontal scrolling, true - Enable horizontal scrolling in the table
			options.fixedColumns.fnDrawCallback = function(leftTable, rightTable) {
				
				if(that.initComplete) {
					
					// Hide right fixedColumns
					$(that.wrapper).find('.DTFC_RightWrapper').hide()
					
					// Table แบบ fixedColums ตอนกดค้นหาครั้งแรกจะเบี้ยว เลยต้องสั่ง relayout ใหม่ 1 ครั้งเพื่อให้มันตรง
					if(!that.initCompleteFixedColumns) {
						
						that.initCompleteFixedColumns = true
						
						$.fn.dataTable
					    .tables( { visible: true, api: true } )
					    .columns.adjust()
					    .fixedColumns().relayout();
					}
					
					// DataTable Instance
					var table = $(that.$element).DataTable()
					
					// Row count
					var count = table.rows().count()
					
					// Add hover event
					for (var i = 0; i < count; i++) {
						
						$(table.row(i).node()).hover(
			        			  function(e) {
										$(this).addClass('bg-default color-white')
										$(leftTable.body).find('tr[data-dt-row="' + table.row(this).index() + '"]').addClass('bg-info color-white')
			        			  }, 
			        			  function(e) {
										$(this).removeClass('bg-default color-white')
										$(leftTable.body).find('tr[data-dt-row="' + table.row(this).index() + '"]').removeClass('bg-info color-white')
			        			  }
			        	)
			        	
			        	$(leftTable.body).find('tbody > tr:eq(' + i + ')').hover(
			        			  function(e) {
										$(this).addClass('bg-info color-white')
										$(table.row($(this).attr('data-dt-row')).node()).addClass('bg-info color-white')
			        			  }, 
			        			  function(e) {
										$(this).removeClass('bg-info color-white')
										$(table.row($(this).attr('data-dt-row')).node()).removeClass('bg-info color-white')
			        			  }
			        	)
			        	
			        	//Bind event on row for single choose dialog
			        	if(that.options.dialogType == 'single') {
			        		$(leftTable.body).find('tr:eq(' + i + ')').on('dblclick', function () {
			        			var modalId = $(that.$element).closest('.modal').attr('id')
			        			$('#' + modalId).sitDataTableDialog('singleChoose', table.row($(this).attr('data-dt-row')).data())
			        		}).css('cursor', 'pointer')
			        		
			        		$(table.row(i).node()).css('cursor', 'pointer')
			        	}
					}
					
					//ถ้าเป็น table fixedColumns จะ add event ในการซ่อน / แสดงข้อมูลระหว่าง resize
					$(window).bind("resize", function() {
						
			    		if($(window).width() <= ($(that.wrapper).find('.DTFC_LeftBodyWrapper').width() + 250)) {
			    			
			    		if($(that.wrapper).find('.DTFC_LeftHeadWrapper, .DTFC_LeftBodyWrapper').css("visibility") == 'visible') {
			    				//console.log('going to hide.')
			    				$(that.wrapper).find('.DTFC_LeftHeadWrapper, .DTFC_LeftBodyWrapper').css('visibility', 'hidden')
			    			} else {
			    				//console.log('still hidden')
			    			}
			    		
			    		} else {
			    			if($(that.wrapper).find('.DTFC_LeftHeadWrapper, .DTFC_LeftBodyWrapper').css("visibility") == 'hidden') {
				    			
			    				//console.log('going to show.')
			    				$(that.wrapper).find('.DTFC_LeftHeadWrapper, .DTFC_LeftBodyWrapper').css('visibility', 'visible')
			    				$(that.$element).DataTable().fixedColumns().update()
			    			} else {
			    				//console.log('still shown')
			    			}
			    		}
			    	})
			    	
			    	// trigger searchCompleteCallback
			    	if(that.options.searchCompleteCallback != undefined)
			    		that.options.searchCompleteCallback(settings)
				}
			}
			
		} else {
			
			options.responsive = true

			var fnRenderer = function ( api, rowIdx, columns ) {
            	
                var data = $.map( columns, function ( col, i ) {
                	
                	var html = ''
                		
                	if(col.hidden) {
                		
                		if(i == that.colCheckboxIndex) {
                    		html = '<li data-dtr-index="' + i + '" data-dt-row="' + col.rowIndex + '" data-dt-column="' + i + '">'
                    		//html += '<span class="dtr-title">' + col.title + '</span> ' 
                    		html += '<span class="dtr-title">Checkbox</span> ' 
                    		html += '<span class="dtr-data">' + col.data + '</span>'
                    		html += '</li>'
                    		
                    		//Create header checkbox for rowChild
                    		if($(that.wrapper).find('div.checkboxToolbar').html().length == 0) {
                    			
                    			var cHTML = "<strong>Check All</strong><div class='checkbox checkbox-datatable checkbox-inline'> " +
                    					"<input id='rowChild-" + that.defaultCheckboxId + "' type='checkbox'>" +
                    					"<label for='rowChild-" + that.defaultCheckboxId + "'>&nbsp;</label> </div>"
                    					
                    			$(that.wrapper).find('div.checkboxToolbar').html(cHTML)
                    			
                    			//Select header checkbox of rowChild
                    			var headerCheckboxRowChild = $(that.wrapper).find('div.checkboxToolbar :checkbox')
                    			
                    			if($("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:checked:hidden").length == $("#" + that.id + " [id^=" + that.defaultCheckboxId+ "-]:hidden").length)
                    				$(headerCheckboxRowChild).prop("checked", true)
                    			else
                    				$(headerCheckboxRowChild).prop("checked", false)
                    				
                    			//Bind click event for header checkbox of rowChild
                    			$(headerCheckboxRowChild).click(function() {
                    				if($(this).is(":checked"))
                    					$("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:not(:checked):visible").click()
                    				else 
                    					$("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:checked:visible").click()
                    			})
                    		}
                    		
                		} else {
                    		html = '<li data-dtr-index="' + i + '" data-dt-row="' + col.rowIndex + '" data-dt-column="' + i + '">'
                    		html += '<span class="dtr-title">' + col.title + '</span> ' 
                    		html += '<span class="dtr-data">' + col.data + '</span>'
                    		html += '</li>'
                		}
                	}
                	
                    return html
                }).join('')
                
                var result = data ? $('<ul data-dtr-index="' + rowIdx + '" class="dtr-details" />').append(data) : false
               
                // Bind event to checkbox
                if(result) {
                	
                	if(that.colCheckboxIndex > -1) {
                		
                		//Find checkbox rowChild
                		var checkboxContainer = $(result).find('[data-dtr-index="' + that.colCheckboxIndex + '"]')
                		
                		if($(checkboxContainer).attr('data-handle') == undefined) {
                			
                			//Add custom attribute 
                			$(checkboxContainer).attr('data-handle', true)
                			
                			//Add class inline to rowChild checkbox
                			$(checkboxContainer).find('div.checkbox').addClass('checkbox-inline')
                			
                			var input = $(checkboxContainer).find('input')
                			var label = $(checkboxContainer).find('label')
                			
                			//Get real checkbox id
                			var id = $(input).attr('id')
                			
                			//Change rowChild checkbox id
                			$(input).attr('id', id + "-rowChild").addClass('checkbox-rowChild')
                			$(label).attr('for', id + "-rowChild")
                			
                			//When click rowChild checkbox also click the real checkbox
                			$(input).click(function() {
                				
                				//Click the real checkbox
                				$("#" + id).click()
                				
                				//Toggle header checkbox of rowChild
                				if($("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:checked:hidden").length == $("#" + that.id + " [id^=" + that.defaultCheckboxId+ "-]:hidden").length)
                    				$(that.wrapper).find('div.checkboxToolbar :checkbox').prop("checked", true)
                    			else
                    				$(that.wrapper).find('div.checkboxToolbar :checkbox').prop("checked", false)
                			})
                		}
                	}
                }		
                		
                return result
            }
			
			if(this.options.showDetailImmediately) {
				options.responsive = {
						// Show detail immediately
						details: {
			                display: $.fn.dataTable.Responsive.display.childRowImmediate,
			                type: '',
			                renderer: fnRenderer
			            }
					}
				
			} else {
				options.responsive = {
						details: {
			                renderer: fnRenderer
			            }
					}
			}
		}
		
		/* SHOW DATATABLE WRAPPER (ถ้าไม่สั่งโชว์ก่อน Table แบบ FixedColumns สไตล์จะเบี้ยวในตอนค้นหาครั้งแรก) */
		$(this.wrapper).show()
		
		/* INITIALIZE DATATABLE */
		$(this.$element).DataTable(options)
		
		/* BIND DATATABLE EVENTS */
		
		//Ajax event - fired before an Ajax request is made.
		$(this.$element).DataTable().on('preXhr.dt', function (e, settings, data) {
			
			//clear selected ids (เมื่อไม่ใช่ dialog)
			if(that.options.dialogType == undefined) 
				$("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:visible:checked").click()
			
			if($(that.criteria).find("[name='criteria.criteriaKey']").val().length == 0)
				settings.ajax.data = that.fnCriteriaToJSON()
			else {
				
				//เก็บค่า sort ใหม่
				// - กรณี sort หลาย column
				var orders = []
				var headers = []
				
				data.order.forEach(function(element) {
					orders.push(element.dir)
					headers.push(element.column)
				})
				
				$(that.criteria).find("[name='criteria.headerSortsSelect']").val(headers)
				$(that.criteria).find("[name='criteria.orderSortsSelect']").val(orders)
				//$(that.criteria).find("[name='criteria.start']").val((parseInt($(that.criteria).find("[name='criteria.linePerPage']").val(), 10) * that.$element.DataTable().page())+1)
				
				var criteriaType = $(that.criteria).find("[name='criteria.criteriaType']").val()

				settings.ajax.data = {
					"criteria.criteriaKey": $(that.criteria).find("[name='criteria.criteriaKey']").val(),
					"criteria.headerSortsSelect": $(that.criteria).find("[name='criteria.headerSortsSelect']").val(),
					"criteria.orderSortsSelect": $(that.criteria).find("[name='criteria.orderSortsSelect']").val(),
					"criteria.start": (parseInt($(that.criteria).find("[name='criteria.linePerPage']").val(), 10) * that.$element.DataTable().page()),
					"criteria.criteriaType": criteriaType == undefined || criteriaType == 'undefined' ? 0 : criteriaType
				}
				
				//If this ajax request is start from updateStatus()
				if(that.updateData != undefined) {

					//Merege ajax data
					var json = that.updateData
					$.extend(json, settings.ajax.data)
					
					//Set url to update
					settings.ajax.url = that.updateData['urlUpdateStatus']
					
					//Delete update url
					delete json['urlUpdateStatus']
					
					//Set ajax data
					settings.ajax.data = json
					
					//Clear update data
					that.updateData = undefined
				}
			}
	    })
		
		//Ajax event - fired when an Ajax request is completed.
		$(this.$element).DataTable().on('xhr.dt', function ( e, settings, json, xhr ) {
			
			//Set back search url
			settings.ajax.url = that.options.urlSearch
			
			//alert maxExeed
			if(json.messageAjax.messageType == 'C'){
				
				if(confirm(json.messageAjax.message)) {
					
					//set flag ว่า confirm maxExceed แล้ว เพื่อที่ตอน Reload (การกดปุ่ม search ครั้งต่อๆ มา)
					that.confirmMaxExceed = true
					
					//เซ็ต checkMaxExceed เป็น false เพื่อที่ตอนเข้าไป search จะได้ไม่ติด maxExceed
					$(that.criteria).find("[name='criteria.checkMaxExceed']").val(false)
					
					//unbind init event
					$(that.$element).DataTable().off('init.dt')
					
					//reload dataTable
					$(that.$element).DataTable().ajax.reload(function(json) {
						that.confirmMaxExceed = false
						
						if(that.options.fixedColumns == undefined)
							$(that.$element).DataTable().columns.adjust().responsive.recalc()
					})
				}
			} else if(json.messageAjax.messageType == "W"){
				showNotifyMessageInfo(json.messageAjax.message);
			} else if(json.messageAjax.messageType == "D"){
				showNotifyMessageInfo(json.messageAjax.message);
			} else if(json.messageAjax.messageType == "S"){
				showNotifyMessageSuccess(json.messageAjax.message);
			} else if(json.messageAjax.messageType == "A"){
				alert(json.messageAjax.message);
			} else if(json.messageAjax.messageType == "E"){
				showNotifyMessageError(json.messageAjax.message, json.messageAjax.messageDetail);
			} else {
			}
	    })
	    
	    // Items (rows, columns or cells) have been selected.
		$(this.$element).DataTable().on('select', function (e, dt, type, indexes) {
			
		    if (type === 'row') {
		    	
		    	indexes.forEach(function(i) {
		    		
		    		var data = $(that.$element).DataTable().rows(i).data()
			    	var index = that.selectedIds.indexOf(parseInt(data[0].id, 10))
			    	
			    	if(index == -1)
			    		that.selectedIds.push(parseInt(data[0].id, 10))
			    		
			    	// selectCallback
			        if(that.options.select.selectCallback != undefined)
			        	that.options.select.selectCallback(data[0], $(that.$element).DataTable().row(i).node(), i)
				})
		    }
		})
		
		// Items (rows, columns or cells) have been deselected.
		$(this.$element).DataTable().on('deselect', function ( e, dt, type, indexes ) {
		    if ( type === 'row' ) {
		    	indexes.forEach(function(i) {
		    		var data = $(that.$element).DataTable().rows(i).data()
				    var index = that.selectedIds.indexOf(parseInt(data[0].id, 10))
				        
				    if(index > -1)
				    	 that.selectedIds.splice(index, 1)
				        
			        // deselectCallback
			        if(that.options.select.deselectCallback != undefined)
			        	that.options.select.deselectCallback(data[0], $(that.$element).DataTable().row(i).node(), i)
				})
		    }
		})
		
		$(this.$element).DataTable().on( 'init.dt', function (e, settings, json ) {

		})
		
		$(this.$element).DataTable().on( 'order.dt', function () {

		})
		    
	    $(this.$element).DataTable().on( 'column-sizing.dt', function ( e, settings ) {
	    	
	    	/* console.log('column-sizing.dt')
	    	if($(that.wrapper).find('.dataTables_scrollHeadInner > table').css('height') > $(that.wrapper).find('.dataTables_scrollHeadInner').css('height')) {
	    		console.log($(that.wrapper).find('.dataTables_scrollHeadInner > table').css('width'))
		    	$(that.wrapper).find('.dataTables_scrollHeadInner > table').css('width', '+=13px')
		    	console.log($(that.wrapper).find('.dataTables_scrollHeadInner > table').css('width'))
		    	
		    	console.log($(that.wrapper).find('.dataTables_scrollHeadInner > table.dataTable > thead > tr > th:last').css('width'))
		    	$(that.wrapper).find('.dataTables_scrollHeadInner > table.dataTable > thead > tr > th:last').css('width', '+=13px')
		    	console.log($(that.wrapper).find('.dataTables_scrollHeadInner > table.dataTable > thead > tr > th:last').css('width'))
	    	}
	    	
	    	*/
	    })
	    
	    $(this.$element).DataTable().on('responsive-resize.dt', function ( e, datatable, columns ) {
	    	
	    })
	    
	    $(this.$element).DataTable().on('responsive-display.dt', function (  e, datatable, row, showHide, update  ) {
	    	
	    	if(row.child() == undefined){
	    		$(that.wrapper).find('div.checkboxToolbar').hide()
	    		
	    	} else {
	    		//Find rowChild checkbox
	    		var rowChildCheckbox = $(row.child()).find('[data-dtr-index="' + that.colCheckboxIndex + '"]')
	    		
	    		//If rowChild checkbox is hidden
	    		if(rowChildCheckbox.length == 0)
	    			$(that.wrapper).find('div.checkboxToolbar').hide()
	    		else {
	    			$(that.wrapper).find('div.checkboxToolbar').show()
	    			
	    			//ถ้า checkbox ทั้งหมดเป็น checked
	        		if($("#" + that.id + " [id^=" + that.defaultCheckboxId + "-]:checked:visible").length == $("#" + that.id + " [id^=" + that.defaultCheckboxId+ "-]:visible").length)
	        			$(that.wrapper).find('div.checkboxToolbar :checkbox').prop("checked", true)
	        		else
	        			$(that.wrapper).find('div.checkboxToolbar :checkbox').prop("checked", false)
	    		}
	    	}
	    })
		
		//Processing event - fired when DataTables is processing data.
		$(this.$element).DataTable().on('processing.dt', function ( e, settings, processing ) {

	    })
		
		//Page change event - fired when the table's paging is updated.
		$(this.$element).DataTable().on('page.dt', function () {

		})
		
	}
	
	SitDataTable.prototype.getLanguage = function() {
		var oLanguage = {
				zeroRecords: "",
		        info: "<strong>รายการที่ค้นพบ  _TOTAL_ รายการ</strong>",	//รายการที่ค้นพบ xxx รายการ	ใช้ bundle ดึงมา
		        infoEmpty: "<strong>รายการที่ค้นพบ  _TOTAL_ รายการ</strong>",	//รายการที่ค้นพบ xxx รายการ	ใช้ bundle ดึงมา
		        paginate: {
			        first: "‹‹",
			        last: "››",
			        next: "›",
			        previous: "‹"
			    }
		};
		return oLanguage;
	}
	
	//Get selected ids
	SitDataTable.prototype.getSelectedIds = function() {
		return this.selectedIds
	}
	
	//Has selected ids
	SitDataTable.prototype.hasSelectedIds = function() {
		return this.selectedIds.length == 0 ? false : true
	}
	
	//Clear selected ids
	SitDataTable.prototype.clearSelectedIds = function() {
		return this.selectedIds = []
	}
	
	//Update status
	SitDataTable.prototype.updateStatus = function(url, flag, data) {
		
		if(data == undefined)
			data = { }
		
		data["urlUpdateStatus"] = url
		data["criteria.statusForUpdate"] = flag
		data["criteria.selectedIds"] = this.selectedIds + ''
		
		//Set json to global data
		this.updateData = data
		
		//Redraw current page
		var currentPage = $(this.$element).DataTable().page.info().page
		$(this.$element).DataTable().page(currentPage).draw('page')
	}

	SitDataTable.prototype.reload = function() {
		
		var that = this
		
		// Clear criteriaKey
		if($(this.criteria).find("[name='criteria.criteriaKey']").val().length > 0)
			$(this.criteria).find("[name='criteria.criteriaKey']").val('')
			
		//Clear selected ids บน dialog
		if(that.options.dialogType != undefined)
			that.selectedIds = []
		
		// Set checkMaxceed to true
		$(this.criteria).find("[name='criteria.checkMaxExceed']").val(true)
		
		// Set ค่า headerSort และ orderSort ให้กลับเป็นค่า default
		this.defaultSorting()
		
		// Set sort column ใหม่ตาม default (อันเดียวกันกับ order ที่อยู่ใน options ตอน initial dataTable)
		$(this.$element).DataTable().order(this.order)
		
		// Set line per page ใหม่ตาม criteria (อันเดียวกันกับ pageLength ที่อยู่ใน options ตอน initial dataTable)
		$(this.$element).DataTable().page.len($(this.criteria).find("[name='criteria.linePerPage']").val())
		
		// Set flag initComplete กลับเป็น false เพราะต้อง init ใหม่
		this.initComplete = false
		this.initCompleteFixedColumns = false
		
		$(this.$element).DataTable().ajax.reload(function(json) {
			
			//ถ้ามีการกด confirm maxExceed ก็ไม่ต้องทำงาน
			if(that.confirmMaxExceed)
				return
				
			if(that.options.fixedColumns == undefined)
				$(that.$element).DataTable().columns.adjust().responsive.recalc()
		})
	}
	
	// PLUGIN DEFINITION
	// =======================

	function Plugin(option, _relatedTarget) {
		
		var args = arguments
		var value
		
		var chain = this.each(function() {
			var $this = $(this)
			var data = $this.data('bs.sitDataTable')

			// Call function
			if (typeof option === 'string') {
		        //Copy the value of option, as once we shift the arguments
		        //it also shifts the value of option.
		        if (data[option] instanceof Function) {
		            [].shift.apply(args);
		            value = data[option].apply(data, args)
		        }
		        
		    // Create plugin    
		    } else if (!data) {
		    	
		    	var options = $.extend(true, {}, SitDataTable.DEFAULTS, $this.data(), typeof option == 'object' && option)
				$this.data('bs.sitDataTable', (data = new SitDataTable(this, options)))
				data.create(_relatedTarget)
				
			// Reuse plugin
			} else {
				
				//Call reload function
				data.reload()
			}
		})
		
		if (typeof value !== 'undefined')
			return value
	    else
	    	return chain
	}
	

	var old = $.fn.sitDataTable

	$.fn.sitDataTable = Plugin
	$.fn.sitDataTable.Constructor = SitDataTable

	// MODAL NO CONFLICT
	// =================

	$.fn.sitDataTable.noConflict = function() {
		$.fn.sitDataTable = old
		return this
	}
	
}(jQuery);