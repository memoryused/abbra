+function($) {
	'use predefine button';
	var symbolSpllit = ",";
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

	var SitButtonPredefine = function(element, options) {
		this.options = options
		this.$body = $(document.body)
		this.$element = $(element)
		this.id = $(element).attr('id');
		this.buttonType = options.buttonType;
		this.buttonEnable = options.buttonEnable;
		this.buttonFunction = options.buttonFunction;
	}
	
	SitButtonPredefine.VERSION = '0.0.1'
	SitButtonPredefine.DEFAULTS = {
			
	}
	
	SitButtonPredefine.prototype.create = function() {
		this.createSitButtonPredefineBox();
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
		
		var tableHead = "<table class=\"BUTTON\"><tr class=\"\"><td class=\"LEFT RIGHT_70\"></td><td class=\"RIGHT LEFT_30\">";
		var tableFooter = "</td></tr></table>";

		var typeData = this.buttonType;
	    var type = typeData.split(symbolSpllit);
	    
	    var funcData = this.buttonFunction;
	    var func = funcData.split(symbolSpllit);
	    
	    var authData = this.buttonEnable;
	    var auth = authData.split(symbolSpllit);
	    
	    var button ="";
	    for(var j=0;j<type.length;j++){
	    	if(type[j].toUpperCase() == buttonType.search){
	    		if(auth[j]=="true"){	
		    		button = button + "<button id=\"btnSearch\" class=\"btnSearch btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgSearch +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btnSearch\" class=\"btnSearch btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgSearch +
					"</button> "
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.save){
	    		if(auth[j]=="true"){	
		    		button = button + "<button id=\"btnAdd\" class=\"btnAdd btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgSave +
					"</button> "
	    		}else{
		    		button = button + "<button id=\"btnAdd\" class=\"btnAdd btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgSave +
					"</button> "
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.clear){
	    		if(auth[j]=="true"){	
					button = button + "<button id=\"btnClear\" class=\"btnClear btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgClear +
					"</button>"
	    		}else{
					button = button + "<button id=\"btnClear\" class=\"btnClear btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgClear +
					"</button>"
	    		}
	    	}	  
	    	if(type[j].toUpperCase() == buttonType.refresh){
	    		if(auth[j]=="true"){	
					button = button + "<button id=\"btnRefresh\" class=\"btnRefresh btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgClear +
					"</button>"
	    		}else{
					button = button + "<button id=\"btnRefresh\" class=\"btnRefresh btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\">" +
					messageButton.msgClear +
					"</button>"
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.edit){
	    		if(auth[j]=="true"){	
					button = button + "<button id=\"btnEdit\" class=\"btnEdit btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgEdit +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btnEdit\" class=\"btnEdit btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgEdit +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.cancel){
	    		if(auth[j]=="true"){	
					button = button + "<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgCancel +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgCancel +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.print){
	    		if(auth[j]=="true"){	
					button = button + "<button id=\"btnPrint\" class=\"btnPrint btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgPrint +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btnPrint\" class=\"btnPrint btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgprint +
					"</button> "		    	
	    		}
	    	}
	    	if(type[j].toUpperCase() == buttonType.ok){
	    		if(auth[j]=="true"){	
					button = button + "<button id=\"btnOk\" class=\"btnOK btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgOk +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btnOk\" class=\"btnOK btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgOk +
					"</button> "		    	
	    		}
	    	}	    	
	    	if(type[j].toUpperCase() == buttonType.close){
	    		if(auth[j]=="true"){	
					button = button + "<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\""+func[j]+"();\"> " +
					messageButton.msgClose +
					"</button> "	
	    		}else{	    		
					button = button + "<button id=\"btnCancel\" class=\"btnCancel btn-fixsize\" type=\"button\" onclick=\"return false;\" tabindex=\"-1\" disabled=\"disabled\"> " +
					messageButton.msgClose +
					"</button> "		    	
	    		}
	    	}	    	
	    }
	    
	    $(this.$element).html(tableHead + button + tableFooter);
	    this.initButton();
	}
	
	SitButtonPredefine.prototype.initButton = function() {	
		//Default button
		jQuery(":button").button();

		//ปุ่มและ icon
		jQuery(".btnSearch").button({
			icons: {
				primary: "ui-icon-search"
		    }
		});

		jQuery(".btnRefresh").button({
		    icons: {
		        primary: "ui-icon-refresh"
		    }
		});

		jQuery(".btnEdit").button({
		    icons: {
		        primary: "ui-icon-disk"
		    }
		});

		jQuery(".btnCancel").button({
		    icons: {
		        primary: "ui-icon-close"
		    }
		});

		jQuery(".btnAdd").button({
		    icons: {
		        primary: "ui-icon-disk"
		    }
		});

		jQuery(".btnPrint").button({
		    icons: {
		        primary: "ui-icon-print"
		    }
		});

		jQuery(".btnClear").button({
		    icons: {
		        primary: "ui-icon-close"
		    }
		});
		
		jQuery(".btnClose").button({
		    icons: {
		        primary: "ui-icon-close"
		    }
		});
		
		jQuery(".btnSave").button({
		    icons: {
		        primary: "ui-icon-disk"
		    }
		});
		
		jQuery(".btnRegister").button({
			icons: {
				primary: "ui-icon-disk"
			}
		});
		
		jQuery(".btnBack").button({
			icons: {
				primary: "ui-icon-triangle-1-w"
			}
		});
		
		jQuery(".btnOK").button({
			icons: {
				primary: "ui-icon-disk"
			}
		});
		
		jQuery(".btnDeleteCustom").button({
			icons: {
				primary: "ui-icon-trash"
			}
		});
		
		jQuery(".btnBilling").button({
			icons: {
				primary: "ui-icon-circle-triangle-e"
			}
		});
		
		jQuery(".btnSale").button({
			icons: {
				primary: "ui-icon-circle-triangle-w"
			}
		});
	}
	
	
	// PLUGIN DEFINITION
	// =======================
	function Plugin(option, _relatedTarget) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data('sitButtonPredefine')
			var options = $.extend({}, SitButtonPredefine.DEFAULTS, $this.data(), typeof option == 'object' && option)
					
			if (!data) {
				$this.data('sitButtonPredefine', (data = new SitButtonPredefine(this, options)));
				data.create(_relatedTarget);
			}
			
			if (typeof option == 'string') {
				data[option](_relatedTarget);
			}
		})
	}
	
	var old = $.fn.sitButtonPredefine

	$.fn.sitButtonPredefine = Plugin
	$.fn.sitButtonPredefine.Constructor = SitButtonPredefine

}(jQuery);




















