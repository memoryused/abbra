	var objFocus;
	var msgLabel = "";
	function validateFormInputAll() {
		/* clear msg */
		clearMessage();
		msgLabel = "";
		objFocus = null;
		
		/** validate require */
		jQuery("input, select, textarea").each(function (index) {
			validateInputAll(jQuery(this), "requireInput");
		});
		/** end validate require */
		
		return focusFirstInput();
	}
	
	function validateDivInputAll(divId) {
		/* clear msg */
		clearMessage();
		msgLabel = "";
		objFocus = null;

		if (divId != undefined && divId != "") {
			/** validate require */
			jQuery("#" + divId).find("input, select, textarea").each(function() {
				validateInputAll(jQuery(this), "requireFill");
			});
		}
		
		return focusFirstInput();
	}
	
	/**
	 * Validate require input
	 * @param obj
	 * @param requireClass
	 */
	function validateInputAll(obj, requireClass) {
		var className = jQuery(obj).attr("class");
		if (className != undefined) {
//			if (className.indexOf('readonly') == -1) {
			if (!jQuery(obj).prop("disabled")) {
				if (className.indexOf(requireClass) > -1) {
					var value;
					if ((jQuery(obj).attr('type') == 'radio') || (jQuery(obj).attr('type') == 'checkbox')) {
						var name = jQuery(obj).attr( "name" );
						if(jQuery("input[name='" + name + "']:checked").length > 0) {
							if (jQuery("input[name='" + name + "']").val() != undefined && jQuery("input[name='" + name + "']").val() != "") {
								value = jQuery("input[name='" + name + "']").val();	
							} else {
								value = "Y";
							}
						} else {
							value = "";
						}
					} else if (jQuery(obj).attr('type') == 'button') {
						// validate button uploadfile require data
						var validFile = jQuery(obj).attr("validFile");
						value = trim(jQuery("input[name='" + validFile + "']").val());
					} else {
						value = trim(jQuery(obj).val())
					}

					if (value == "") {
						var validName;
						
						if (jQuery(obj).attr("validName") != undefined) {
							validName = jQuery(obj).attr("validName");
							if (validName == undefined || validName == '') {
								// set default message if type button
								if (jQuery(obj).attr('type') == 'button') {
									validName = "Browse";
								}
							}
						} else {
							var type = jQuery(obj).attr("type");
							var className = jQuery(obj).attr("class");
							
							if ((type == 'radio') || (type == 'checkbox')) {
								validName = jQuery(obj).parent().attr('validName');
							} else {
								if (className.indexOf('input_dateformat_') > -1) {
									var eleId = jQuery(obj).attr("id").replace("_dd_sl_mm_sl_yyyy","");
									validName = jQuery("#"+eleId).attr("validName");
								} else if (className.indexOf('input_timeformat_') > -1) {
									var eleId = jQuery(obj).attr("id").replace("_hh_cl_mm","");
									validName = jQuery("#"+eleId).attr("validName");
								}
							}
						}
						
						if (validName != undefined) {
							if ((jQuery(obj).attr('type') == 'radio') || (jQuery(obj).attr('type') == 'checkbox')) {
								msg = validName + " must select at least one item.";//validateMessage.CODE_10029.replace("xxx", validName); // xxx must select at least one item.
							} else {
								msg = validName + " cannot be blank.";//validateMessage.CODE_10003.replace("xxx", validName); // xxx cannot be blank.
							}
							setMessageValid(type, msg, jQuery(obj));
						}
					} else {
						validateFormatInputAll(type, jQuery(obj));
					}
				} else {
					if (trim(jQuery(obj).val()) != "") {
						validateFormatInputAll(type, jQuery(obj));
					}
				}
			}
		}

		if (jQuery(obj).css("text-transform") == "uppercase") {
			jQuery(obj).val(jQuery(obj).val().toUpperCase());
		}
	}
	
	function focusFirstInput() {
		if (objFocus != undefined && msgLabel != "") {
			tabFocus();				// focus tab
			accordionFocus();		// focus accordion
			objFocus.focus();		// fofus object
			
			showNotifyMessageValidate(msgLabel);		// show message
			
//			calContentHeight();
			
			return false;
		} else {
			return true;
		}
	}

	function clearMessage() {
		clearNotifyMessage();
		jQuery(".msgLabel").each(function (index) {
			jQuery(this).html("");	
		});
		
		jQuery(".is-invalid").each(function (index) {
			jQuery(this).removeClass("is-invalid");
		});
		
		jQuery(".border_select").each(function (index) {
			jQuery(this).removeClass("border_select");
		});
	}

	function validateFormatInputAll(type, obj) {
		/** validate format */
		var className = obj.attr("class");
		
		if (className.indexOf('input_email') > -1) {
			if (validateEmailFormatFormIndex(obj) == false) {
				setMessageValid(type, validateMessage.CODE_10005, obj);
			}
		} else if (className.indexOf('input_dateformat_') > -1) {
			var status = inputValidateDateFormatValueName(jQuery(obj)[0]);
			if ((status == 'empty') || status == 'ok') {

			} else {
				msg = validateMessage.CODE_10004.replace("xxx", "Date");
				setMessageValid(type, msg, obj);
			}
		} else if (className.indexOf('input_timeformat_') > -1) {
			var status = inputValidateTimeFormatValueName(jQuery(obj)[0]);
			if ((status == 'empty') || status == 'ok') {

			} else {
				msg = validateMessage.CODE_10004.replace("xxx", "Time");
				setMessageValid(type, msg, obj);
			}
		}
		/** end validate format */
		
		var validation = obj.attr("cp_validation");
		if (validation != undefined) {
			validateFormatInput (type, obj, validation);
		}
		
	}
		
	function validateFormatInput (type, obj, validation) {
		if (validation.indexOf('input_aircraft_callsign') > -1) {
			if (validateAircraftCallsign(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Aircraft call sign");
				setMessageValid(type, msg, obj);
			} else {
				if (validateLengthInputAircraftCallsign(obj) == false) {
					msg = validateMessage.CODE_10022.replace("xxx", "Aircraft call sign");
					setMessageValid(type, msg, obj);
				} else {
					
				}
			}
			
		} else if (validation.indexOf('input_flight_number') > -1) {
			if (validateFlightNumber(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Flight number");
				setMessageValid(type, msg, obj);
			} else {
				if (validateLengthInputFlightNumber(obj) == false) {
					msg = validateMessage.CODE_10021.replace("xxx", "Flight number");
					setMessageValid(type, msg, obj);
				} else {
					
				}
			}

		} else if (validation.indexOf('input_format_flight_number') > -1) {
			if (validateFlightNumber(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Flight number");
				setMessageValid(type, msg, obj);
			} else {
				if (validateLengthInputFlightNumber(obj) == false) {
					msg = validateMessage.CODE_10021.replace("xxx", "Flight number");
					setMessageValid(type, msg, obj);
				} else {
					if (validateFlightNumberFormat(obj) == false) {
						msg = validateMessage.CODE_10012.replace("xxx", "Flight number");
						setMessageValid(type, msg, obj);
					} else {
						
					}
				}
			}

		} else if (validation.indexOf('input_flight_format_number') > -1) {
			if (validateFlightNumber(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Flight number");
				setMessageValid(type, msg, obj);
			} else {
//				if (validateFlightFormatNumber(obj) == false) {
//					msg = validateMessage.CODE_10012.replace("xxx", "Flight number");
//					setMessageValid(type, msg, obj);
//				} else {
//					
//				}
			}

		} else if (validation.indexOf('input_service_number') > -1) {
			if (validateServiceNumber(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Service number");
				setMessageValid(type, msg, obj);
			} else {
				if (validateLengthInputServiceNumber(obj) == false) {
					msg = validateMessage.CODE_10021.replace("xxx", "Service number");
					setMessageValid(type, msg, obj);
				} else {
					
				}
			}
			
		} else if (validation.indexOf('input_carrier_code') > -1) {
			if (validateCarrierCode(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Carrier code");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		} else if (validation.indexOf('input_user_id') > -1) {
			if (validateUserId(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "User ID");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		} else if (validation.indexOf('input_family_name_user') > -1) {
			if (validateFamilyNameUser(obj) == false) {
				msg = validateMessage.CODE_10018.replace("xxx", "Family name");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		} else if (validation.indexOf('input_given_name_user') > -1) {
			if (validateGivenNameUser(obj) == false) {
				msg = validateMessage.CODE_10017.replace("xxx", "Given name(s)");
				setMessageValid(type, msg, obj);
			} else {
				
			}

		} else if (validation.indexOf('input_family_name_pax') > -1) {
			if (validateFamilyNamePax(obj) == false) {
				msg = validateMessage.CODE_10018.replace("xxx", "Family name");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		} else if (validation.indexOf('input_given_name_pax') > -1) {
			if (validateGivenNamePax(obj) == false) {
				msg = validateMessage.CODE_10017.replace("xxx", "Given name(s)");
				setMessageValid(type, msg, obj);
			} else {
				
			}

		} else if (validation.indexOf('input_document_number') > -1) {
			if (validateDocumentNumber(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Document number");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		} else if (validation.indexOf('input_telephone_contact') > -1) {
			if (validateTelephoneContact(obj) == false) {
				msg = validateMessage.CODE_10019.replace("xxx", "Telephone contact");
				setMessageValid(type, msg, obj);
			}
		
		} else if (validation.indexOf('input_ext') > -1) {
			if (validateExt(obj) == false) {
				msg = validateMessage.CODE_10020.replace("xxx", "Ext.");
				setMessageValid(type, msg, obj);
			}
			
		} else if (validation.indexOf('input_facsimile_number') > -1) {
			if (validateFacsimileNumber(obj) == false) {
				msg = validateMessage.CODE_10019.replace("xxx", "Facsimile number");
				setMessageValid(type, msg, obj);
			}
			
		} else if (validation.indexOf('input_port') > -1) {
			if (validatePort(obj) == false) {
				msg = validateMessage.CODE_10010.replace("xxx", "Port");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		} else if (validation.indexOf('input_reservation_sys_code') > -1) {
			if (validateReservationSysCode(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Reservation system code");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		} else if (validation.indexOf('input_record_locator') > -1) {
			if (validateRecordLocator(obj) == false) {
				msg = validateMessage.CODE_10001.replace("xxx", "Record locator");
				setMessageValid(type, msg, obj);
			} else {
				
			}
			
		}
	}

	/**
	 * validation carrier code [A-Z and 0-9]
	 * */
	function validateCarrierCode(el) {
		var regularExpression = new RegExp(input_carrier_code);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation family name user [A-Z]
	 * */
	function validateFamilyNameUser(el) {
		var regularExpression = new RegExp(input_family_name_user);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation given name(s) user [A-Z or (space)]
	 * */
	function validateGivenNameUser(el) {
		var regularExpression = new RegExp(input_given_name_user);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation family name pax [A-Z]
	 * */
	function validateFamilyNamePax(el) {
		var regularExpression = new RegExp(input_family_name_pax);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation given name(s) pax [A-Z or (space)]
	 * */
	function validateGivenNamePax(el) {
		var regularExpression = new RegExp(input_given_name_pax);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation telephone contract [0-9 or +]
	 * */
	function validateTelephoneContact(el) {
		var regularExpression = new RegExp(input_telephone_contact);
		
		// alert(regularExpression.test(trim(jQuery(el).val())));
		if (regularExpression.test(trim(jQuery(el).val())) == true) {
			// ตรวจสอบกรณีมีการระบุเครื่องหมาย + ต้องอยู่ตัวแรก
			var count = (trim(jQuery(el).val()).match(/[+]/g) || []).length;
			// alert(count);
			if (count <= 1 && trim(jQuery(el).val()).indexOf('+') <= 0 ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;			
		}
	}

	/**
	 * validation aircraft call sign [A-Z and 0-9]
	 * */
	function validateAircraftCallsign(el) {
		var regularExpression = new RegExp(input_aircraft_callsign);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation service number [A-Z and 0-9]
	 * */
	function validateServiceNumber(el) {
		var regularExpression = new RegExp(input_service_number);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation flight number [A-Z and 0-9]
	 * */
	function validateFlightNumber(el) {
		var regularExpression = new RegExp(input_flight_number);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * check min char aircraft call sign 3 to 8
	 * */
	function validateLengthInputAircraftCallsign(el) {
		if (trim(jQuery(el).val().toUpperCase()).length < min_aircraft_callsign || trim(jQuery(el).val().toUpperCase()).length > max_aircraft_callsign) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check min char service number 3 to 8
	 * */
	function validateLengthInputServiceNumber(el) {
		if (trim(jQuery(el).val().toUpperCase()).length < min_service_number || trim(jQuery(el).val().toUpperCase()).length > max_service_number) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * check min char flight number 3 to 8
	 * */
	function validateLengthInputFlightNumber(el) {
		if (trim(jQuery(el).val().toUpperCase()).length < min_flight_number || trim(jQuery(el).val().toUpperCase()).length > max_flight_number) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * validation user id [A-Z or 0-9]
	 * */
	function validateUserId(el) {
		var regularExpression = new RegExp(input_user_id);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation document number [A-Z, 0-9]
	 * */
	function validateDocumentNumber(el) {
		var regularExpression = new RegExp(input_document_number);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation port [A-Z]
	 * */
	function validatePort(el) {
		var regularExpression = new RegExp(input_port);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation facsimile number [0-9, +]
	 * */
	function validateFacsimileNumber(el) {
		var regularExpression = new RegExp(input_facsimile_number);
		if (regularExpression.test(trim(jQuery(el).val())) == true) {
			// ตรวจสอบกรณีมีการระบุเครื่องหมาย + ต้องอยู่ตัวแรก
			var count = (trim(jQuery(el).val()).match(/[+]/g) || []).length;
			if (count <= 1 && trim(jQuery(el).val()).indexOf('+') <= 0 ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;			
		}
	}

	/**
	 * validation ext [0-9]
	 * */
	function validateExt(el) {
		var regularExpression = new RegExp(input_ext);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation reservation system code [A-Z, 0-9]
	 * */
	function validateReservationSysCode(el) {
		var regularExpression = new RegExp(input_reservation_sys_code);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation record locator [A-Z, 0-9]
	 * */
	function validateRecordLocator(el) {
		var regularExpression = new RegExp(input_record_locator);
		return regularExpression.test(trim(jQuery(el).val().toUpperCase()));
	}

	/**
	 * validation date of birth < current date
	 * */
	function validateDateOfBirth(el) {
		if(trim(jQuery(el).val()) != "") {
			var arrDob = trim(jQuery(el).val().toUpperCase()).split("/");
			var dob = new Date(arrDob[2], parseInt(arrDob[1]) - 1, arrDob[0]);
			
			var q = new Date();
			var m = q.getMonth();
			var d = q.getDate();
			var y = q.getFullYear();
			var currentDate = new Date(y,m,d);
			
			if(dob > currentDate) {
			    return false;
			} else {
			    return true;
			}
		}
	}
	
	function validateFlightNumberFormat(el) {
		var val = trim(jQuery(el).val().toUpperCase())
		arr1 = val.substr(0, 2);
		arr2 = val.substr(2, val.length);
		// console.log(arr1 + " : " + arr2);
		// console.log(arr1.length + " : " + arr2.length);
		if (arr1.length == 2) {
			var regularExpression = new RegExp("^[A-Z]+$");
			// console.log(regularExpression.test(trim(arr1.toUpperCase())));
			if (regularExpression.test(trim(arr1.toUpperCase()))) {
				regularExpression = new RegExp("^[0-9]+$");
				if (regularExpression.test(trim(arr2))) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
//	function validateFlightFormatNumber(el) {
//		// (X)NNNN(X) 
//		// X: Optional
//		var regularExpression;
//		var val = trim(jQuery(el).val().toUpperCase())
//		if (val.length < 4 || val.length > 6) {
//			return false;
//		} else if (val.length == 4) {
//			// ต้องเป็น ตัวเลขเท่านั้น
//			regularExpression = new RegExp("^[0-9]+$");
//			if (regularExpression.test(trim(val))) {
//				return true;
//			} else {
//				return false;
//			}
//		} else if (val.length == 5) {
//			regularExpression1 = new RegExp("^[A-Z]+$");
//			regularExpression2 = new RegExp("^[0-9]+$");
//			
//			var first = val.substr(0, 1);
//			var second = val.substr(1, val.length);
//			if (regularExpression1.test(trim(first))) {
//				// 1. กรณีขึ้นต้นด้วยตัวอักษร
//				if (regularExpression2.test(trim(second))) {
//					return true;
//				} else {
//					return false;
//				}
//			} else if (regularExpression2.test(trim(first))) {
//				// 2. กรณีขึ้นต้นด้วยตัวเลข
//				first = val.substr(0, 4);
//				second = val.substr(4, val.length);
//				if (regularExpression2.test(trim(first)) && regularExpression1.test(trim(second))) {
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				
//			}
//		} else if (val.length == 6) {
//			// ขึ้นต้นและลงท้ายด้วยตัวอักษร
//			// 4 ตัวกลางเป็นตัวเลข
//			var a = val.substr(0, 1);
//			var b = val.substr(1, 4);
//			var c = val.substr(5, val.length);
//			
//			regularExpression1 = new RegExp("^[A-Z]+$");
//			regularExpression2 = new RegExp("^[0-9]+$");
//			a1 = regularExpression1.test(trim(a))
//			b1 = regularExpression2.test(trim(b))
//			c1 = regularExpression1.test(trim(c))
//			if (a1 && b1 && c1) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//	}
	
	function setMessageValid(type, msg, obj) {
		if (type == 'part') {
			msgLabel = msg;
			obj.nextAll(".msgLabel").html("<font color='red'><strong>" + msgLabel + "</strong></font>");
		} else if (type == 'sum') {
			obj.addClass("border_select");
		} else {
			if (msgLabel.indexOf(msg) == -1) {
				msgLabel += msg + "<br/>";
			}
			
			if (jQuery(obj).css("display") == "none" && jQuery(obj).prop("tagName") == "SELECT") {
				jQuery(obj).next().addClass("border_select").addClass("is-invalid");
			} else if ((jQuery(obj).attr('type') == 'radio') || (jQuery(obj).attr('type') == 'checkbox')) {
//				jQuery(obj).parent().addClass("border_select");
				if (jQuery(obj).parent().parent().hasClass("requireGroup")) {
					jQuery(obj).parent().parent().addClass("border_select").addClass("is-invalid");
				}else{
					jQuery(obj).parent().addClass("is-invalid");
				}
			} else if (jQuery(obj).css("display") == "none" && jQuery(obj).prop("tagName") == "INPUT") {
				jQuery(obj).next().addClass("border_select").addClass("is-invalid");
			} else if(jQuery(obj).is("select")){
				var idSelect = jQuery(obj).attr("id");
				jQuery(obj).parent().find("[data-id='"+ idSelect +"']").addClass("border_select").parent().addClass("is-invalid");
			} else {
				obj.addClass("is-invalid");
			}
		}
		
		if (objFocus == undefined) {
			if (jQuery(obj).css("display") == "none" && jQuery(obj).prop("tagName") == "SELECT") {
				objFocus = jQuery(obj).next();
			} else {
				objFocus = obj;
			}
		}
	}
	
	
	
	
	function tabFocus(){
		jQuery(objFocus).parents(".tab-pane").each(function(index){
			jQuery(".nav-tabs-4 a[href='#"+ jQuery(this).attr("id") +"']").tab("show");
		});
	}
	
	
	function accordionFocus(){
		jQuery(objFocus).parents(".collapse").each(function(index){
			jQuery("#"+jQuery(this).attr("id")).collapse("show");
		});
	}
	
	
	
	/**
	* ตรวจสอบการเลือกข้อมูล
	* elName คือ ชื่อของ input ที่ต้องการตรวจสอบ
	* return 0 เมื่อไม่มีการเลือกข้อมูล
	* return >= 1  เมื่อเลือกข้อมูล
	*/
	function findChecked(checkboxName) {
			return jQuery("input[name='" + checkboxName + "']:checked");
	}

	/**
	* ตรวจสอบการเลือกข้อมูล
	* elName คือ ชื่อของ input ที่ต้องการตรวจสอบ
	* return false เมื่อไม่พบการเลือก
	* return true เมื่อเลือกข้อมูล
	*/
	function validateSelect(elName){
		if (findChecked(elName).length == 0) {
			alert(validateMessage.CODE_10001);
			return false;
		} else {
			return true;
		}
	}	
	
	
//	function tabFocus() {
//		var isLastTab = false;
//		var objCurrent = jQuery(objFocus);
//		
//		// Last tab ?
//		while (!isLastTab) {
//			// find	selected tab id		
//			var tabId = jQuery(objCurrent).parent().closest("div.ui-tabs").attr("id");
//			if (tabId != undefined && tabId != "") {
//				// find tab panel index
//				var panelId = jQuery(objCurrent).closest("div.ui-tabs-panel").attr("id");
//				if (panelId != undefined && panelId != "") {
//					// find div id and index of selected tab
//					var index = 0;
//					jQuery("#" + tabId).children("div.ui-tabs-panel").each(function() {
//						if (jQuery(this).attr("id") == panelId) {
//							index = jQuery(this).index() - 1;
//							return false;
//						}
//					});
//					
//					// focus selected tab
//					jQuery("#" + tabId).tabs("option", "active", index);
//					
//					// find upper tab 
//					objCurrent = jQuery("#" + tabId);
//				}
//				
//			} else {
//				// find not found upper tab. stop working focus tab
//				isLastTab = true;
//			}
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	