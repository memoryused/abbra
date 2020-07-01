+function($) {
	'use strict';

	var SitFileUpload = function(element, options) {
		this.options = options
	}

	SitFileUpload.VERSION = '0.0.1'
		
	SitFileUpload.DEFAULTS = {
		  maxFileSize: 10 // 10 MB
		, idBrowseFileBtn: undefined
		, idDivContainer: undefined
		, filterFileType: "csv,xls,xlsx"
	}
	
	SitFileUpload.prototype.create = function() {
		var divLoader;
		var that = this;
		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',
			url : this.options.uploadURL,
			file_data_name:'fileMeta.fileUpload',
			max_file_size : this.options.maxFileSize + 'mb',
			browse_button : this.options.idBrowseFileBtn, // you can pass in id...
			container: document.getElementById(this.options.idDivContainer), // ... or DOM Element itself
				
			// Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
			drop_element : this.options.idDivContainer,
				
			// กรณีที่ทำการ browse single file
			multi_selection: false,	
			/*
			resize : {
				width : 50,
				height : 37,
				quality: 70
		    },
		      */
		   	filters : [
	            {title : "files", extensions : this.options.filterFileType}
	        ],
	        
	        // Flash settings
	      	flash_swf_url : WEB_CONTEXT + '/js/plupload-2.1.2/js/Moxie.swf',
			silverlight_xap_url : WEB_CONTEXT + '/js/plupload-2.1.2/js/Moxie.xap',
	        
	        // PreInit events, bound before any internal events
	        preinit : {
				Init: function(up, info) {
					//log('[Init]', 'Info:', info, 'Features:', up.features);
				},
		 
		        UploadFile: function(up, file) {
		        	//log('[UploadFile]', file);
					 
					// You can override settings before the file is uploaded
		            // up.setOption('url', 'upload.php?id=' + file.id);
		            // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
				}
			},
			
	        // Post init events, bound after the internal events
			init : {
            	PostInit: function() {
                	// Called after initialization is finished and internal event handlers bound
            		//log('[PostInit]');
            	},
 
            	Browse: function(up) {
                	// Called when file picker is clicked
            	},
 
            	Refresh: function(up) {
                	// Called when the position or dimensions of the picker change
            	},
  
            	StateChanged: function(up) {
                	// Called when the state of the queue is changed
            	},
  
            	QueueChanged: function(up) {
                	// Called when queue is changed by adding or removing files
            	},
 
            	OptionChanged: function(up, name, value, oldValue) {
	                // Called when one of the configuration options is changed
    	        },
 
            	BeforeUpload: function(up, file) {
                	// Called right before the upload for a given file starts, can be used to cancel it if required
                	divLoader = new ajaxLoader(jQuery("body"), {bgColor: '#000', opacity: '0.3'});
            	},
  
            	UploadProgress: function(up, file) {
                	// Called while file is being uploaded
            	},
 
            	FileFiltered: function(up, file) {
                	// Called when file successfully files all the filters
            	},
  
            	FilesAdded: function(up, files) {
            		console.log("FilesAdded");
	            	if(files.length > 1){
						alert(validateMessage.CODE_10048);
					
						jQuery.each(files, function(i, file) {
							up.removeFile(file);
						});
					} else{
						jQuery.each(files, function(i, file) {
							up.refresh(); // Reposition Flash/Silverlight
							up.start(); // action
						});
					}
            	},
  
            	FilesRemoved: function(up, files) {
                	// Called when files are removed from queue
            	},
  
            	FileUploaded: function(up, file, response) {
	           		var obj = jQuery.parseJSON(response.response);
	           		console.info(obj);
	           		console.info(that.options.callbackFunc);
					if(that.options.callbackFunc != undefined){
		    			window[that.options.callbackFunc](obj, file);
		    		}
					divLoader.remove();
            	},
  
            	ChunkUploaded: function(up, file, info) {
	                // Called when file chunk has finished uploading
	            },
 
 	           UploadComplete: function(up, files) {
 	        	  console.log("UploadComplete");
    	            // Called when all files are either uploaded or failed
        	    },
 
            	Destroy: function(up) {
            		console.log("Destroy");
                	// Called when uploader is destroyed
            	},
  
	            Error: function(up, err) {
					/*
					Generic error for example if an exception is thrown inside Silverlight.
					code: -100
					message: Generic error.
					
					HTTP transport error. For example if the server produces a HTTP status other than 200.
					code: -200
					message: HTTP Error.
					
					Generic I/O error. For exampe if it wasn't possible to open the file stream on local machine.
					code: -300
					message: IO error.
					
					Generic I/O error. For exampe if it wasn't possible to open the file stream on local machine.
					code: -400
					message: Security error.
					
					Initialization error. Will be triggered if no runtime was initialized.
					code: -500
					message: Init error.
					
					File size error. If the user selects a file that is to large it will be blocked and an error of this type will be triggered.
					code: -600
					message: File size error.
					
					File extension error. If the user selects a file that isn't valid according to the filters setting.
					code: -601
					message: File extension error.
					       
					Runtime will try to detect if image is proper one. Otherwise will throw this error.
					code: -700
					message:
					       
					While working on the image runtime will try to detect if the operation may potentially run out of memeory and will throw this error.
					code: -701code: -200
					message:
					message:
					       
					Each runtime has an upper limit on a dimension of the image it can handle. If bigger, will throw this error.
					code: -702
					message:
					*/
				   var msgErr = "";
	               if(err.code == -601){
	               		msgErr = validateMessage.CODE_10045;
	               		msgErr = msgErr.replace("xxx", that.options.filterFileType);
				   }else if(err.code == -600){
				   		msgErr = validateMessage.CODE_10046;
				   		msgErr = msgErr.replace("xxx", that.options.maxFileSize + " mb");
				   }
				   
				   alert(msgErr);
	               up.refresh(); // Reposition Flash/Silverlight
            	}
        	}
	    });
		
    	uploader.init();
	}
	
	// PLUGIN DEFINITION
	// =======================

	function Plugin(option, _relatedTarget) {
		return this.each(function() {
			var $this = $(this)
			var data = $this.data('sitFileUpload')
			var options = $.extend({}, SitFileUpload.DEFAULTS, $this.data(),
					typeof option == 'object' && option)
					
			if (!data) {
				$this.data('sitFileUpload', (data = new SitFileUpload(this, options)));
				data.create(_relatedTarget);
			}
			
			
			if (typeof option == 'string') {
				data[option](_relatedTarget);
			}
		})
	}
	

	var old = $.fn.sitFileUpload

	$.fn.sitFileUpload = Plugin
	$.fn.sitFileUpload.Constructor = SitFileUpload

	// MODAL NO CONFLICT
	// =================

	$.fn.sitFileUpload.noConflict = function() {
		$.fn.sitFileUpload = old
		return this
	}
	
}(jQuery);