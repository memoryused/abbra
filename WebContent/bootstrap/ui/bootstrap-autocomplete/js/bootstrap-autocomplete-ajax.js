//default value of combobox use in javascript.jsp
//Start create combobox widgets
(function( $ ) {
	$.widget( "custom.sitAutocompleteAjax", {
		version: "1.0.0",
		options: [{
			seq: 0,
			displayModelInput: "none",
			inputModelId: undefined,
		    url: undefined,
		    postParamsId: undefined,
		    postParameGenerate: undefined,
		    cssClass: undefined,
		    cssStyle: undefined,
		    defaultKey: undefined,
		    defaultValue: undefined,
		    submitType: "POST",
		    async: false,
		    disabled: false,
			requireInput: false,
		    beforeChangeFunction: undefined,
		    afterChangeFunction: undefined,
		    autocompleteId: undefined,
		    autocompleteCode: undefined,
		    autocompleteText: undefined,
		    autocompleteElement: undefined,
		    autocompleteInput: undefined,
		    autocompleteWrapper: undefined,
		    autocompleteButton: undefined,
		    autocompleteTimeoutToolTipId: undefined,
		    minLength: 1,
		    delay: 0,
		    limit: "",
		    arrowDis : false,
		    clearForm: false,
	    	validName: "",
	    	invalidFeedbackName: "",
	    	label: ""
		}],

		_create: function() {
			// ตรวจสอบความถูกต้องของ config
			if (this._validateConfig() == false) {
				return false;
			}
			
			this._manageOptions();

			// นำ select ที่สร้างไว้แล้วมาทำ autocomplete
			this._createAutocompleteAll();
			this._createShowAllButtonAll();
			
		},
		
		_validateConfig: function() {
			var input = undefined;
			for (var key in this.options) {
				if (key == 0) {
					input = this.element;	
				} else {
					input = jQuery("#" + this.options[key].inputModelId);
				}
				
				// console.info(input);
				var autocompleteId = jQuery(input).attr('data-code-of');
				if (autocompleteId == undefined){
					autocompleteId = jQuery(input).attr('data-text-of');
				}
				if (autocompleteId == undefined){
					return false;
				}

				if ((jQuery("input[data-code-of='" + autocompleteId + "']").length != 1)
						|| (jQuery("input[data-text-of='" + autocompleteId + "']").length != 1)){
					return false;
				}
				
				this.options[key].autocompleteId = autocompleteId;
				this.options[key].autocompleteCode = jQuery("#" + jQuery("input[data-code-of='" + autocompleteId + "']").attr('id'));
				this.options[key].autocompleteText = jQuery("#" + jQuery("input[data-text-of='" + autocompleteId + "']").attr('id'));
				this.options[key].autocompleteElement = jQuery("#" + autocompleteId);
				this.options[key].autocompleteWrapper = jQuery( "<div>" ).addClass( "input-group" ).insertAfter( this.options[key].autocompleteElement );
			}
			return true;
		},
		
		_manageOptions: function() {
			var input = undefined;
			for (var key in this.options) {
				if (key == 0) {
					input = this.element;	
				} else {
					input = jQuery("#" + this.options[key].inputModelId);
				}
				
				// กำหนดค่า sequence ให้ dropdownlist
				this.options[key].seq = key;

				// กำหนดค่า modelId โดยใช้ id จาก input
				if (this.options[key].inputModelId == undefined) {
					this.options[key].inputModelId = jQuery(input).attr('id');
				}
				
				// กำหนดค่า css และ style โดยยึดที่ config เป็นหลัก
				if (this.options[key].cssClass == undefined) {
					this.options[key].cssClass = jQuery(input).attr('class');
				}
				
				// กำหนดค่า css และ style โดยยึดที่ config เป็นหลัก
				if (this.options[key].cssStyle == undefined) {
					this.options[key].cssStyle = jQuery(input).attr('style');
				}

				// กำหนดค่า readonly โดยยึดที่ค่า true เสมอ
				if (this.options[key].disabled == undefined) {
					this.options[key].disabled = false;
				}
				if (this.options[key].disabled == false) {
					if (jQuery(input).attr('disabled')) {
						this.options[key].disabled = true;	
					}
				}

				// กำหนดค่า requireInput โดยยึดที่ค่า true เสมอ
				if (this.options[key].requireInput == undefined) {
					this.options[key].requireInput = false;
				}
				if (this.options[key].requireInput == false) {
					if (jQuery(input).hasClass('requireInput')) {
						this.options[key].requireInput = true;	
					}
				}
				
				// กำหนดค่า post parameter
				if (this.options[key].postParamsId != undefined) {
					//this.options[key].postParameGenerate = generatePostParameters(this.options[key].postParamsId);
				}
				
				if (this.options[key].submitType == undefined) {
					this.options[key].submitType = "POST";
				}

				if (this.options[key].async == undefined) {
					this.options[key].async = false;
				}

				if (this.options[key].displayModelInput == undefined) {
					this.options[key].displayModelInput = "none";
				}
				
				if (this.options[key].minLength == undefined) {
					this.options[key].minLength = 0;
				}
				
				if (this.options[key].delay == undefined) {
					this.options[key].delay = 750;
				}
				
				if (this.options[key].limit == undefined) {
					this.options[key].limit = "";
				}
				
				if (this.options[key].arrowDis == undefined) {
					this.options[key].arrowDis = false;
				}
				if (this.options[key].arrowDis == true) {
					this.options[key].arrowDis = true;
				}
				
				if (this.options[key].validName == undefined) {
					this.options[key].validName = "";
				}
				
				if (this.options[key].clearForm == undefined) {
					this.options[key].clearForm = false;
				}
				
				// TODO:
				if (this.options[key].invalidFeedbackName == undefined) {
					this.options[key].invalidFeedbackName = "";
				}
				
				if (this.options[key].label == undefined) {
					this.options[key].label = "";
				}
				
			}
		},

		_getLastSeq: function() {
			var lastSeq = 0;
			for (var key in this.options) {
				lastSeq = key;
			}
			return lastSeq;
		},
		
		_manageChangeFunction: function(newSeq) {
			// console.info(newSeq);
			var that = this;
			var options = this.options;
			var lastSeq = this._getLastSeq();
			var thisSeq = 0;
			if (newSeq != undefined) {
				thisSeq = parseInt(newSeq, 10);
			}
			
			var currentOptions = options[thisSeq];
			for (; thisSeq <= lastSeq; thisSeq++) {
				
				var nextOptions = that.options[thisSeq + 1];
				if (nextOptions != undefined) {
					nextOptions.autocompleteCode.val("");
					nextOptions.autocompleteText.val("");
					that._createAutocomplete(nextOptions);		
					that._createShowAllButton(nextOptions);
					that._manageChangeFunction(nextOptions.seq);
				}
				
			}
		},
		
		_createAutocompleteAll: function() {
			for (var key in this.options) {
				this._createAutocomplete(this.options[key]);
			}
		},

		_createAutocomplete: function(currentOptions) {
			// console.info(currentOptions);
			
			// สร้าง post parameter ก่อน เพื่อไว้ตรวจสอบว่าจะ call ไปยัง servlet หรือไม่
			currentOptions.postParameGenerate = generatePostParameters(currentOptions.postParamsId);
			
			if (currentOptions.postParamsId == undefined) {
				// กรณีไม่มี post parameter ให้ load ปกติ			
				if (currentOptions.url != undefined) {
					drawingAutocompleteAjaxFromJsonSelectItem(undefined, currentOptions);
					currentOptions.autocompleteElement.removeAttr('disabled').removeClass('readonly');
				}
				this._createDataAutocomplete(currentOptions);
			} else {
				if (haveEmptyPostParameters(currentOptions.postParameGenerate)) {
					// กรณีมี post parameter แต่เป็นค่าว่างตัวใดตัวหนึ่งให้ clear autocomplete
					// console.info('กรณีมี post parameter แต่เป็นค่าว่างตัวใดตัวหนึ่งให้ clear autocomplete');
					this._createEmptyAutocomplete(currentOptions);
				} else {
					// กรณี post parameter มีค่าครบทุกตัว ให้โหลดข้อมูลได้
					// console.info('กรณี post parameter มีค่าครบทุกตัว ให้โหลดข้อมูลได้');
					// console.info(currentOptions);
					if (currentOptions.url != undefined) {
						drawingAutocompleteAjaxFromJsonSelectItem(undefined, currentOptions);
						currentOptions.autocompleteElement.removeAttr('disabled').removeClass('readonly');
					}
					this._createDataAutocomplete(currentOptions);
				}
			}	
		},
		
		_createDataAutocomplete: function(options) {
			options.autocompleteElement.css('display', options.displayModelInput);
			options.autocompleteCode.css('display', options.displayModelInput);
			options.autocompleteText.css('display', options.displayModelInput);
			
			var that = this;
			var selected = options.autocompleteElement.children( ":selected" );
			var value = selected.text() ? selected.text() : "";
			var msgMinChar = "";
			if (autocompleteConfig.msgMinChar != undefined) {
				msgMinChar = autocompleteConfig.msgMinChar.replace("xxx", options.minLength);
			}
			
			options.autocompleteInput = jQuery( "<input type='text'>" )
			.appendTo( options.autocompleteWrapper )
			.val( value )
			.attr( "id", options.autocompleteId + "_input_id" )
			.attr( "name", options.autocompleteId + "_input_name" )
			.attr( "title", "" )
			.attr("class", options.cssClass)
			.attr("style", options.cssStyle)
			.attr( "seq", options.seq )
			.addClass( "form-control" )
			//.addClass( "custom-combobox-input" )
			//.tooltip({tooltipClass: "ui-state-highlight"})
			.attr( "validName", options.validName )
			
			/**Nanthanat.l 2016-07-05  */
			.click(function(){
				this.select();
				
			})
			.keyup(function(){
				//console.info('keyup : ' + options.autocompleteCode.val());
				//console.info('keyup : ' + options.autocompleteText.val());
				if (options.minLength == 0) {
					return true;
				}
				if (options.autocompleteInput.val().length < options.minLength) {
					options.autocompleteTimeoutToolTipId = that._delay(function() {
						/*jQuery(this).attr( "title", msgMinChar).tooltip( "open" );*/
					}, 100);
				} else {
					options.autocompleteInput.tooltip( "dispose" ).attr( "title", "" );
					clearTimeout(options.autocompleteTimeoutToolTipId);
				}
			})
			.blur(function() {
				if (options.autocompleteInput.val() == '') {
					//console.log("blur");
					//clear ค่า เพื่อเข้าเงื่อนไข ตอน autocomplete change
					options.autocompleteCode.val('');
					options.autocompleteText.val('');
					options.autocompleteElement.val('');
					
				}
			})
			.change(function(){
				// console.info('text change');
				if (options.autocompleteInput.val() == '') {
					options.autocompleteCode.val('');
					options.autocompleteText.val('');
					that._manageChangeFunction(options.seq);
					
					if (options.afterChangeFunction != undefined) {
						options.afterChangeFunction();
					}
				}
				options.autocompleteInput.tooltip( "dispose" ).attr( "title", "" );
				clearTimeout(options.autocompleteTimeoutToolTipId);
				
				//console.info('change : ' + options.autocompleteCode.val());
				//console.info('change : ' + options.autocompleteText.val());
			})
			.autocomplete({
				delay: options.delay,
				minLength: options.minLength,
				source: jQuery.proxy( this, "_source", options.seq ),
				select: function( event, ui ) {
					// console.info('select:---');
					if ((options.autocompleteCode.val() == ui.item.option.value)
							&& (options.autocompleteText.val() == ui.item.label)) {
						return;
					}
					options.autocompleteCode.val(ui.item.option.value);
					options.autocompleteText.val(ui.item.label);
					
					//console.info('autocomplete : ' + options.autocompleteCode.val());
					//console.info('autocomplete : ' + options.autocompleteText.val());
					
					// ----------------------------------------------
					that._manageChangeFunction(parseInt(options.seq, 10));
					
					if (options.afterChangeFunction != undefined) {
						options.afterChangeFunction();
					}
				},
				close : function() {
					// console.info('close:---');
					if (this.value != '') {
						if(this.value.length < options.minLength){
							options.autocompleteCode.val('');
							options.autocompleteText.val('');
						}
						return;
					}
					
					if(this.value == ''){
						if(options.minLength == 0){
							options.autocompleteCode.val('');
							options.autocompleteText.val('');
						}
					}
					
					if ((options.autocompleteCode.val() == options.autocompleteElement.val())
							&& (options.autocompleteText.val() == options.autocompleteInput.val())) {
						return;
					}
					
					// ----------------------------------------------
					that._manageChangeFunction(parseInt(options.seq, 10));
					
					if (options.afterChangeFunction != undefined) {
						options.afterChangeFunction();
					}
				}
			});
			
			if (options.autocompleteElement.attr("disabled") == "disabled") {
				options.autocompleteInput.removeAttr('disabled').attr('disabled', 'disabled').removeClass('readonly').addClass('readonly');
			}
			
			if (options.disabled) {
				options.autocompleteInput.removeAttr('disabled').attr('disabled', 'disabled').removeClass('readonly').addClass('readonly');
			}
			
			if (options.requireInput) {
				options.autocompleteInput.removeClass('requireInput').addClass('requireInput');
			}
			
			if (options.clearForm) {
				options.autocompleteInput.removeClass('clearform').addClass('clearform');
			}
			
			this._on( options.autocompleteInput, {
				autocompleteselect: function( event, ui ) {
					ui.item.option.selected = true;
					this._trigger( "select", event, {
						item: ui.item.option
					});
					options.autocompleteElement.trigger("change");
				},
				autocompletechange: function( event, ui ) {
					// console.info('autocompletechange');
					if ((options.autocompleteCode.val() == options.autocompleteElement.val())
							&& (options.autocompleteText.val() == options.autocompleteInput.val())) {
						return;
					} else {
						if ((options.autocompleteCode.val() == options.autocompleteElement.val())
								&& (options.autocompleteText.val() == options.autocompleteInput.val())) {
							return;
						} else {
							options.postParameGenerate = generatePostParameters(options.postParamsId);
							options.postParameGenerate.term = options.autocompleteInput.val();
							this._loadOption(options);
							options.autocompleteInput.data( "ui-autocomplete" ).term = options.autocompleteInput.val();
							return this._removeIfInvalid(event, ui, options.seq);
						}
					}
				}
			});
			
			// กรณีมีค่าอยู่ model ให้ set ลง autocomplete ด้วย
			options.autocompleteElement.val(options.autocompleteCode.val());
			options.autocompleteInput.val(options.autocompleteText.val());
			options.autocompleteInput.data( "ui-autocomplete" ).term = options.autocompleteText.val();
			
			//TODO:
			if(options.invalidFeedbackName !==""){
				jQuery("<div>").addClass("invalid-feedback").text(options.invalidFeedbackName).insertAfter(options.autocompleteWrapper);
			}
			if(options.label !==""){
				var labelHtml = "";
				if (options.requireInput) {
					labelHtml = options.label + "<em>*</em>";
				}else{
					labelHtml = options.label;
				}
				jQuery("<label for='"+options.autocompleteId+"_input_id'>").html(labelHtml).insertAfter( options.autocompleteWrapper );
			}

		},

		_createEmptyAutocomplete: function(options) {
			// ใช้กรณีไม่มีข้อมูล หรือ post parameter มีค่าเป็นว่าง
			drawingAutocompleteAjaxFromJsonSelectItem(undefined, options);
			this._createDataAutocomplete(options);
		},
		
		_createShowAllButtonAll: function() {
			for (var key in this.options) {
				this._createShowAllButton(this.options[key]);
			}
		},
		
		_createShowAllButton: function(currentOptions) {
			var that = this;
			var input = currentOptions.autocompleteInput, wasOpen = false;
			var defaultMinLength = currentOptions.minLength;
			
			if(currentOptions.arrowDis){
				currentOptions.autocompleteButton = jQuery("<span>")
					.attr( "tabIndex", -1 )
					.addClass("input-group-addon")
					.html("<i class='fa fa-angle-down' aria-hidden='true'></i>")
					.appendTo( currentOptions.autocompleteWrapper )
					.mousedown(function() {
						wasOpen = input.autocomplete( "widget" ).is( ":visible" );
					});
				
				/*
				currentOptions.autocompleteButton = jQuery( "<a>" )
				.attr( "tabIndex", -1 )
				.attr( "seq", currentOptions.seq )
				.tooltip()
				.appendTo( currentOptions.autocompleteWrapper )
				.button({
					icons: {primary: "ui-icon-triangle-1-s"},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "custom-combobox-toggle ui-corner-right" )
				.mousedown(function() {
					wasOpen = input.autocomplete( "widget" ).is( ":visible" );
				});
				*/
			}else{
				
				currentOptions.autocompleteButton = jQuery("<span>")
					.attr( "tabIndex", -1 )
					.attr( "title", autocompleteConfig.msgShowAll ) //"Show All Items"
					.addClass("input-group-addon")
					.html("<i class='fa fa-angle-down' aria-hidden='true'></i>")
					.appendTo( currentOptions.autocompleteWrapper )
					.mousedown(function() {
						wasOpen = input.autocomplete( "widget" ).is( ":visible" );
					});
				
				/*
				currentOptions.autocompleteButton = jQuery( "<a>" )
				.attr( "tabIndex", -1 )
				.attr( "title", autocompleteConfig.msgShowAll ) //"Show All Items"
				.attr( "seq", currentOptions.seq )
				.tooltip()
				.appendTo( currentOptions.autocompleteWrapper )
				.button({
					icons: {primary: "ui-icon-triangle-1-s"},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "custom-combobox-toggle ui-corner-right" )
				.mousedown(function() {
					wasOpen = input.autocomplete( "widget" ).is( ":visible" );
				});
				*/
			}

			if (currentOptions.autocompleteElement.attr("disabled") == "disabled") {
				currentOptions.autocompleteButton.attr( "disabled", "disabled");
			}
			
			if (currentOptions.disabled) {
				currentOptions.autocompleteButton.removeAttr('disabled').attr('disabled', 'disabled');
			}
			
			//ARROW DIS ให้ปิดปุ่มลูกศรขวาด้วย
			if (currentOptions.arrowDis) {
				currentOptions.autocompleteButton.attr('disabled', 'disabled');
			}
			
			if(!currentOptions.arrowDis){
				currentOptions.autocompleteButton.click(function() {
					if (currentOptions.autocompleteButton.attr("disabled") != "disabled") {
						input.focus();
	
						// Close if already visible
						if ( wasOpen ) {
							return;
						}
	
						if (input.val() == '') {
							input.data( "ui-autocomplete" ).term = '';
						}
	
						var term = "";
						if (input.data( "ui-autocomplete" ).term != undefined) {
							term = input.data( "ui-autocomplete" ).term;
						}
	
						input.autocomplete( "option","minLength", 0 ); //set minLength: 0 to search all items.
						input.autocomplete( "search", term ); // Pass empty string as value to search for, displaying all results
						input.autocomplete( "option","minLength", defaultMinLength ); //set minLength to default
					}
				});
			}//currentOptions.arrowDis
			//jQuery("<em>").html("&nbsp;&nbsp;&nbsp;&nbsp;").appendTo( currentOptions.autocompleteWrapper );
		},
		
		_source: function( seq, request, response ) {
			var config = this.options[seq];
			var select = config.autocompleteElement;
			var input = config.autocompleteInput;

			if (config.delay > 0) {
				if (input.hasClass( "working" )) {
					return;
				}
				input.autocomplete( "widget" ).hide();
				// alert('url("' + autocompleteConfig.contextPath + '/images/loading.gif") no-repeat right center;');
				// input.addClass('working');
				input.css('background', 'url("' + autocompleteConfig.contextPath + '/ui/loader/images/loading.gif") no-repeat right center');
				// alert(1);
				input.attr('readonly', 'readonly');
			}

			var dataObject = config.postParameGenerate;
			dataObject.term = request.term;
			dataObject.limit = config.limit;

			jQuery.ajax({
				error: function( jqXHR, textStatus, errorThrown) {
					//console.info( jqXHR, textStatus, errorThrown);
					//console.info( textStatus);
					//console.info( errorThrown);
					//submitPage(autocompleteConfig.contextPath + "/jsp/security/initMainpage.action");
					submitPage(window.location.pathname);
				},
		        url: config.url,
		        type: config.submitType,
		        data: dataObject,
		        dataType: "json",
		        async : config.async,
		        global: false,
		        success: function(data) {
		        	select.find('option')
		            .remove()
		            .end()
		            .append(jQuery('<option></option>').val(config.headerKey).html(config.headerText));

		        	jQuery.each(data, function(val, item) {
		        		// autocomplete-ajax_2.1.0.js 2017-05-11 : sittipol.m add json string 
		        		if (item.object == undefined) {
		        			select.append(jQuery('<option></option>').val(item.key).html(item.value));	
		        		} else {
		        			select.append(jQuery('<option></option>').val(item.key).attr("json", JSON.stringify(item.object)).html(item.value));
		        		}
		        			
		        	});

		        	response( select.children( "option" ).map(function() {
		        		var text = jQuery( this ).text();
		        			return {
		        				label: text,
		        				value: text,
		        				option: this
		        			};
		        		})
		        	);

		        	if (config.delay > 0) {
		        		input.removeClass('working');
		        		input.css('background', '');
		        		input.removeAttr('readonly', '');
		        	}
		        }
		   });
		},

		_removeIfInvalid: function( event, ui, seq ) {
			// console.info("_removeIfInvalid");
			// Search for a match (case-insensitive)
			var value = this.options[seq].autocompleteInput.val(), valueLowerCase = value.toLowerCase(), valid = false;
			var key = "";
			var text = "";
			this.options[seq].autocompleteElement.children( "option" ).each(function() {
				if ( jQuery( this ).text().toLowerCase() === valueLowerCase ) {
					this.selected = valid = true;
					text = jQuery( this ).text();
					key = jQuery( this ).val();
					return false;
				}
			});

			// Found a match, nothing to do
			if ( valid ) {
				this.options[seq].autocompleteCode.val(key);
				this.options[seq].autocompleteText.val(text);
				this._manageChangeFunction(seq);
				return;
			}

			// Remove invalid value

			this.options[seq].autocompleteInput
				.val( "" )
				.attr( "title", value + " " + autocompleteConfig.msgNotMatch.replace("xxx",""))
				.tooltip( "show" );

			this.options[seq].autocompleteElement.val( "" );
			this._delay(function() {this.options[seq].autocompleteInput.tooltip( "dispose" ).attr( "title", "" );}, 2000);
			this.options[seq].autocompleteInput.data( "ui-autocomplete" ).term = "";

			text = "";
			key = "";

			this.options[seq].autocompleteCode.val(key);
			this.options[seq].autocompleteText.val(text);
			this._manageChangeFunction(seq);
		},

		_loadOption: function(options) {
			
			// console.info('_loadOption');
			jQuery.ajax({
				type: options.submitType,
				url: options.url,
				data: options.postParameGenerate,
				async: options.async,
				success: function(jsonData) {
					jQuery("#" + options.autocompleteId).find('option').remove();	
					if ((options.defaultKey != undefined) && (options.defaultValue != undefined)) {
						jQuery("#" + options.autocompleteId).append('<option value="' + options.defaultKey + '">' + options.defaultValue + '</option>');
					}
					
					if (jsonData != undefined) {
						for (var index = 0; index < jsonData.length; index++) {
							// autocomplete-ajax_2.1.0.js 2017-05-11 : sittipol.m add json string
							if (jsonData[index].object == undefined) {
								jQuery("#" + options.autocompleteId).append('<option value="' + jsonData[index].key + '" >' + jsonData[index].value + '</option>');
							} else {
								jQuery("#" + options.autocompleteId).append('<option value="' + jsonData[index].key + '" json="' + JSON.stringify(jsonData[index].object) + '">' + jsonData[index].value + '</option>');
							}
						}
					}
				}
			});
		},
		
		_loadData: function(options) {
			// console.info('_loadData');
			jQuery.ajax({
				type: options.submitType,
				url: options.url,
				data: options.postParameGenerate,
				async: options.async,
				success: function(jsonData) {
					drawingAutocompleteAjaxFromJsonSelectItem(jsonData, options);
				}
			});
		},

		autocompleteValue: function(selectSeq, newValue) {
			console.info('autocompleteValue ajax');
			selectSeq = parseInt(selectSeq, 10);
			if (newValue == undefined) {
				return {codeValue: this.options[selectSeq].autocompleteCode.val(), textValue: this.options[selectSeq].autocompleteText.val()};
			} else {
				if ((newValue.codeValue == "") || (newValue.textValue == "")) {
					var lastSeq = this._getLastSeq();
					
					this.options[selectSeq].autocompleteInput.val( "" );
					this.options[selectSeq].autocompleteInput.data( "ui-autocomplete" ).term = "";
					this.options[selectSeq].autocompleteElement.val("");
					this.options[selectSeq].autocompleteCode.val("");
					this.options[selectSeq].autocompleteText.val("");
					
					this.options[selectSeq].postParameGenerate = generatePostParameters(this.options[selectSeq].postParamsId);
					if (this.options[selectSeq].postParamsId == undefined) {
						this.options[selectSeq].autocompleteElement.removeAttr('disabled').removeClass('readonly'); 
						this.options[selectSeq].autocompleteInput.removeAttr('disabled').removeClass('readonly');
						this.options[selectSeq].autocompleteButton.removeAttr('disabled').removeClass('ui-state-disabled');
						//KAM  2016-11-11
						//console.info(this.options[selectSeq].arrowDis);
						if (this.options[selectSeq].arrowDis == true) {
							this.options[selectSeq].autocompleteButton.removeAttr('disabled').attr('disabled', 'disabled').removeClass('ui-state-disabled').addClass('ui-state-disabled');
						}
					} else {
						//console.info(this.options[selectSeq].arrowDis);
						if (haveEmptyPostParameters(this.options[selectSeq].postParameGenerate)) {
							this.options[selectSeq].autocompleteElement.attr('disabled', 'disabled').addClass('readonly'); 
							this.options[selectSeq].autocompleteInput.attr('disabled', 'disabled').addClass('readonly');
							this.options[selectSeq].autocompleteButton.attr('disabled', 'disabled').addClass('ui-state-disabled');
							
							//KAM 2016-11-11
							if (this.options[selectSeq].arrowDis == true) {
								this.options[selectSeq].autocompleteButton.removeAttr('disabled').attr('disabled', 'disabled').removeClass('ui-state-disabled').addClass('ui-state-disabled');
							}
						} else {
							//console.info(this.options[selectSeq].arrowDis);
							
							this.options[selectSeq].autocompleteElement.removeAttr('disabled').removeClass('readonly'); 
							this.options[selectSeq].autocompleteInput.removeAttr('disabled').removeClass('readonly');
							this.options[selectSeq].autocompleteButton.removeAttr('disabled').removeClass('ui-state-disabled');
							//KAM 2016-11-11
							if (this.options[selectSeq].arrowDis == true) {
								this.options[selectSeq].autocompleteButton.removeAttr('disabled').attr('disabled', 'disabled').removeClass('ui-state-disabled').addClass('ui-state-disabled');
							}
						}
					}

					selectSeq++;
					for (; selectSeq <= lastSeq; selectSeq++) {
						this.options[selectSeq].autocompleteInput.val( "" );
						this.options[selectSeq].autocompleteInput.data( "ui-autocomplete" ).term = "";
						this.options[selectSeq].autocompleteElement.val("");
						this.options[selectSeq].autocompleteCode.val("");
						this.options[selectSeq].autocompleteText.val("");
						this._createEmptyAutocomplete(this.options[selectSeq]);
						this._createShowAllButton(this.options[selectSeq]);
					}
					
				} else {
					
					this.options[selectSeq].postParameGenerate = generatePostParameters(this.options[selectSeq].postParamsId);
					this.options[selectSeq].postParameGenerate.term = newValue.textValue
					this._loadData(this.options[selectSeq]);
					
					this.options[selectSeq].autocompleteElement.removeAttr('disabled').removeClass('readonly'); 
					this.options[selectSeq].autocompleteInput.removeAttr('disabled').removeClass('readonly');
					this.options[selectSeq].autocompleteButton.removeAttr('disabled').removeClass('ui-state-disabled');
					this._createDataAutocomplete(this.options[selectSeq]);
					this._createShowAllButton(this.options[selectSeq]);
					
					if (jQuery("#" + this.options[selectSeq].autocompleteId + " option[value='" + newValue.codeValue + "']").text() != newValue.textValue) {
						this.autocompleteValue(selectSeq, {codeValue: "", textValue: ""});
					} else {
						this.options[selectSeq].autocompleteInput.val( newValue.textValue );
						this.options[selectSeq].autocompleteInput.data( "ui-autocomplete" ).term = newValue.textValue;
						this.options[selectSeq].autocompleteElement.val( newValue.codeValue);
						this.options[selectSeq].autocompleteCode.val(newValue.codeValue);
						this.options[selectSeq].autocompleteText.val(newValue.textValue);
						this._manageChangeFunction(selectSeq);
					}
				}
			}
		},

		toDisabled: function(selectSeq) {
			var firstSeq = 0;
			var thisSeq = this._getLastSeq();
			if (selectSeq != undefined) {
				thisSeq = parseInt(selectSeq, 10);
			}
			
			for (; thisSeq >= firstSeq; thisSeq--) {
				this.options[thisSeq].autocompleteButton.attr('disabled', true).addClass("ui-state-disabled").attr('tabindex', -1);
				this.options[thisSeq].autocompleteElement.attr('disabled', true).addClass("readonly").attr('tabindex', -1);
				this.options[thisSeq].autocompleteInput.attr('disabled', true).addClass("readonly").attr('tabindex', -1);
			}
		},

		toEnabled: function(selectSeq) {
			var lastSeq = this._getLastSeq();
			var thisSeq = 0;
			if (selectSeq != undefined) {
				thisSeq = parseInt(selectSeq, 10);
			}
			
			for (; thisSeq <= lastSeq; thisSeq++) {
				this.options[thisSeq].postParameGenerate = generatePostParameters(this.options[thisSeq].postParamsId);
				if (this.options[thisSeq].postParamsId == undefined) {
					this.options[thisSeq].autocompleteButton.removeAttr('disabled').removeClass("ui-state-disabled").removeAttr('tabindex');
					this.options[thisSeq].autocompleteElement.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
					this.options[thisSeq].autocompleteInput.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
				} else {
					if (haveEmptyPostParameters(this.options[thisSeq].postParameGenerate)) {

					} else {
						this.options[thisSeq].autocompleteButton.removeAttr('disabled').removeClass("ui-state-disabled").removeAttr('tabindex');
						this.options[thisSeq].autocompleteElement.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
						this.options[thisSeq].autocompleteInput.removeAttr('disabled').removeClass("readonly").removeAttr('tabindex');
					}
				}
			}
		}
	});

}) ( jQuery );

function drawingAutocompleteAjaxFromJsonSelectItem(jsonData, options) {
	var autocompleteId = options.autocompleteId;
	
	// 1. เคลียร์ Autocomplete ออกก่อน
	jQuery("#" + autocompleteId).remove();
	jQuery(options.autocompleteWrapper).remove();
	jQuery(options.autocompleteElement).remove();
	jQuery(options.autocompleteInput).remove();

	// 2.1. สร้าง tag select
	var html = "<select id='" + autocompleteId + "' seq='" + options.seq + "'>";
	if ((options.defaultKey != undefined) && (options.defaultValue != undefined)) {
		html += "<option value='" + options.defaultKey + "'>" + options.defaultValue + "</option>";
	}
	
	// 2.2. วนสร้าง tag option
	if (jsonData != undefined) {
		for (var index = 0; index < jsonData.length; index++) {
			//if (msieversion() >= 8) {
			//
			//} else {
			//
			//}
			// autocomplete-ajax_2.1.0.js 2017-05-11 : sittipol.m add json string
			if (jsonData[index].object == undefined) {
				html += "<option value='" + jsonData[index].key + "'>" + jsonData[index].value + "</option>";
			} else {
				html += "<option value='" + jsonData[index].key + "' json='" + JSON.stringify(jsonData[index].object) + "'>" + jsonData[index].value + "</option>";	
			}
			
			
		}
	}
	
	// 2.3. ปิด tag
	html += "</select>";

	// นำ autocompleteId วาดลงใน tag แม่
	jQuery("#" + options.inputModelId).parent().append(html);
	
	html = jQuery("#" + autocompleteId);	
	
	// ใส่ class
	if (options.cssClass != undefined) {
		jQuery(html).addClass(options.cssClass);
	}
	
	// ใส่ style
	if (options.cssStyle != undefined) {
		jQuery(html).attr('style', options.cssStyle);
	}
	
	// disabled เมื่อไม่มีข้อมูล
	if ((jsonData == undefined) || (jsonData.length == 0)) {
		jQuery(html).removeAttr('disabled').removeClass('readonly').attr('disabled', 'disabled').addClass('readonly');
	}
	
	// disabled เมื่อถูกกำหนดให้เป็น disable
	if (options.disabled) {
		jQuery(html).removeAttr('disabled').removeClass('readonly').attr('disabled', 'disabled').addClass('readonly');
	}
	
	// กรณีเป็น require input ให้ใส่ class requireInput ด้วย
	if (options.requireInput) {
		jQuery(html).removeClass('requireInput').addClass('requireInput');
	}
	
	// กรณีมีค่าอยู่ใน model แล้วให้ set ลง autocompleteId ด้วย
	jQuery(html).val(jQuery("#" + options.inputModelId).val());
	
	options.autocompleteElement = jQuery("#" + options.autocompleteId);
	options.autocompleteWrapper = jQuery( "<div>" ).addClass( "input-group" ).insertAfter( options.autocompleteElement );
}