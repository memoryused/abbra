/**
# ---------------------------------------------------------------------------------
# 							button-sit-0.0.1.js
# ---------------------------------------------------------------------------------
#
# Update Date	: 31/07/2017
#
# ---------------------------------------------------------------------------------
*/
+function($) {
	'use predefine button';
	var symbolSpllit = "|";
	var buttonType = {
		search : "SEARCH",
		clear :	"CLEAR",
		edit :	"EDIT",
		refresh : "REFRESH",
		save : "SAVE",
		cancel : "CANCEL",
		print : "PRINT",
		ok : "OK",
		close : "CLOSE"
	}
	
	var styleDefault = "btn-fixsize";
	

	var SitButtonPredefine = function(element, options) {
		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id');
		this.buttonType = options.buttonType;
		this.auth = options.auth;
		this.func = options.func;
		this.container = options.container;
		this.style = options.style;
	}
	
	SitButtonPredefine.VERSION = ''
	SitButtonPredefine.DEFAULTS = {
	}
	
	SitButtonPredefine.prototype.create = function() {
		this.createSitButtonPredefineBox();
	}
	
	
	//Prototype Function สำหรับ change Disabled / Enabled Button
	SitButtonPredefine.prototype.changeStateButton = function(btntype, disabled) {	
		var btnid = "btn_"+ btntype.toUpperCase();
	    obj = jQuery("#"+this.id).find('#'+btnid);
	    obj.prop('disabled', disabled);
	}
	
	
	SitButtonPredefine.prototype.createSitButtonPredefineBox = function() {	
		var messageButton = {
			msgSearch : validateMessage.CODE_SEARCH,
			msgClear : validateMessage.CODE_CLEAR,
			msgSave : validateMessage.CODE_SAVE,
			msgCancel : validateMessage.CODE_CANCEL,
			msgEdit : validateMessage.CODE_EDIT,
			msgClose : validateMessage.CODE_CLOSE,
			msgPrint : validateMessage.CODE_PRINT,
			msgOk : validateMessage.CODE_OK	
		}	
		
		var tableHead = "<div class='row'><div class='div-btn col-12 col-xl-11 text-right'>";
		var tableFooter = "</div></div>";
		
		var typeData = this.buttonType;
	    var type = typeData.split(symbolSpllit);
	    
	    var funcData = this.func;
	    var funcArray = funcData.split(symbolSpllit);
	    
	    var authData = this.auth;
	    if (typeof(authData) == "undefined" || !authData){
	    	authData = "";
	    }
	    var authArray = authData.split(symbolSpllit);
	    
	    var styleData = this.style;
	    if (typeof(styleData) == "undefined" || !styleData){
	    	styleData = "";
	    }
	    var styleArray = styleData.split(symbolSpllit);
	    
	    var button ="";
	    for(var j=0;j<type.length;j++){
	    	if (typeof(styleArray[j]) == "undefined" || !styleArray[j]){
	    		styleArray[j] = styleDefault;
	    	}	    	

	    	if(type[j].toUpperCase() == buttonType.search){
	    		if(authArray[j]=="true"){	
		    		button = button + "<button id=\"btn_"+buttonType.search+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
		    		"<i class='fa fa-search' aria-hidden='true'></i>&nbsp;&nbsp;" +
		    		messageButton.msgSearch +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btn_"+buttonType.search+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
		    		"<i class='fa fa-search' aria-hidden='true'></i>&nbsp;&nbsp;" +
		    		messageButton.msgSearch +
					"</button> "
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.save){
	    		if(authArray[j]=="true"){	
		    		button = button + "<button id=\"btn_"+buttonType.save+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
		    		"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
		    		messageButton.msgSave +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btn_"+buttonType.save+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
		    		"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
		    		messageButton.msgSave +
					"</button> "
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.clear){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.clear+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"<i class='fa fa-eraser' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button>"
	    		}else{
					button = button + "<button id=\"btn_"+buttonType.clear+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-eraser' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button>"
	    		}
	    	}	  
	    	if(type[j].toUpperCase() == buttonType.refresh){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.refresh+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"<i class='fa fa-refresh' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button>"
	    		}else{
					button = button + "<button id=\"btn_"+buttonType.refresh+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\">" +
					"<i class='fa fa-refresh' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClear +
					"</button>"
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.edit){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.edit+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgEdit +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.edit+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-save' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgEdit +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.cancel){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.cancel+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.cancel+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgCancel +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.print){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.print+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"<i class='fa fa-print' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgPrint +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.print+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-print' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgprint +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.ok){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.ok+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"<i class='fa fa-check' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgOk +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.ok+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-check' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgOk +
					"</button> "		    	
	    		}
	    	}	    	
	    	if(type[j].toUpperCase() == buttonType.close){
	    		if(authArray[j]=="true"){	
					button = button + "<button id=\"btn_"+buttonType.close+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\""+funcArray[j]+";\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClose +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btn_"+buttonType.close+"\" class=\"btn "+styleArray[j]+" font-weight-bold\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					"<i class='fa fa-close' aria-hidden='true'></i>&nbsp;&nbsp;" +
					messageButton.msgClose +
					"</button> "		    	
	    		}
	    	}	    	
	    }
	    
	    //check contrainer ว่าเท่ากับ true หรือไม่
	    if(this.container == "true"){	
	    	$(this.$element).html(tableHead + button + tableFooter);
	    }else{
	    	$(this.$element).html(button);
	    }
	}
	
	
	// PLUGIN DEFINITION
	// =======================
	function Plugin(option, _relatedTarget, _data1) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data('sitButtonPredefine')
			var options = $.extend({}, SitButtonPredefine.DEFAULTS, $this.data(), typeof option == 'object' && option)
					
			if (!data) {
				$this.data('sitButtonPredefine', (data = new SitButtonPredefine(this, options)));
				data.create(_relatedTarget);
			}
			
			if (typeof option == 'string') {
				data[option](_relatedTarget,_data1);
			}
		})
	}
	
	var old = $.fn.sitButtonPredefine

	$.fn.sitButtonPredefine = Plugin
	$.fn.sitButtonPredefine.Constructor = SitButtonPredefine

}(jQuery);

/*
 * function สำหรับการทำงานเปลี่ยน disable/enable button
 * param divid : id ของ div ที่มีการวาด button
 * param buttonnType : ปุ่มที่ต้องการให้มีการ Disable/Enabled
 * param disabled : true/false 
 *  - กรณี true จะทำการ Disable
 *  - กรณี false จะทำการ Enabled 
 * 
 * */
function changeStateButton(divid, buttonType, disabled) {
    $("#"+divid).sitButtonPredefine("changeStateButton",buttonType,disabled);
    
}