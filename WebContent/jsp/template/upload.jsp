<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<script type="text/javascript" src="<s:url value='/js/plupload-2.1.2/js/plupload.full.min.js' />"></script>
<script type="text/javascript" src="<s:url value='/js/plupload-2.1.2/js/jquery.ui.plupload/jquery.ui.plupload.js' />"></script>
<!-- ====================================================== -->
<script type="text/javascript">
	jQuery(function() {
		try{
			var uploader = new plupload.Uploader({
				runtimes : 'html5,flash,silverlight,html4',
				url : '<s:property value="#urlUpload"/>',
				file_data_name:'fileMeta.fileUpload',
				max_file_size : '<s:property value="#properties['maxFileSize']"/>' + 'mb',
				browse_button : '<s:property value="#btnPickfiles"/>', // you can pass in id...
				container: document.getElementById('<s:property value="#divresult[0]"/>'), // ... or DOM Element itself
					
				// Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
				drop_element : '<s:property value="#divresult[0]"/>',
					
				// กรณีที่ทำการ browse single file
				multi_selection: false,	

				// Comment เนื่องจากกระทบต่อขนาดรูปที่ upload
				/* resize : {
					width : 50,
					height : 37,
					quality: 70
			    }, */
			      
			   	filters : [
		            {title : "files", extensions : "<s:property value='#filters' />"}
		        ],
		        
		        // Flash settings
		      	flash_swf_url : '<s:url value="/js/plupload-2.1.2/js/Moxie.swf" />',
				silverlight_xap_url : '<s:url value="/js/plupload-2.1.2/js/Moxie.xap" />',
		        
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
		            	if(files.length > 1){
							alert("ไม่สามารถเลือกหลายรายการได้");
						
							jQuery.each(files, function(i, file) {
								up.removeFile(file);
							});
						} else{
							jQuery.each(files, function(i, file) {
								// if(file.size > 3145728 || file.status == 4) !Fix
								if(file.size > '<s:property value="#properties['maxFileSize']"/>'*1048576 || file.status == 4){ //ตรวจสอบกรณีที่เลือกหลายๆไฟล์
									//Nothing
									alert('<s:text name="10021" /> : ' + plupload.formatSize(file.size));
									up.refresh(); // Reposition Flash/Silverlight
								} else{ 
									//TODO Set Display
			
									up.refresh(); // Reposition Flash/Silverlight
									up.start(); // action
								}
							});
						}
	            	},
	  
	            	FilesRemoved: function(up, files) {
	                	// Called when files are removed from queue
	            	},
	  
	            	FileUploaded: function(up, file, response) {
		           		var obj = jQuery.parseJSON(response.response);
						//Set value CallBack
						//callback(obj, file);	//get file
						var cb = '<s:property value="#callback"/>';
						if(cb != ""){
			    			window[cb](obj, file);
			    		}
						divLoader.remove();
	            	},
	  
	            	ChunkUploaded: function(up, file, info) {
		                // Called when file chunk has finished uploading
		            },
	 
	 	           UploadComplete: function(up, files) {
	    	            // Called when all files are either uploaded or failed
	        	    },
	 
	            	Destroy: function(up) {
	                	// Called when uploader is destroyed
	            	},
	  
		            Error: function(up, err) {
					   if(err.code == -601){
							alert('<s:text name="10013" />');
					   }else if(err.code == -600){
							alert('<s:text name="10021" /> : ' + plupload.formatSize(err.file.size));
					   }

		               up.refresh(); // Reposition Flash/Silverlight
	            	}
	        	}
		    });
			
	    	uploader.init();
		}catch(e){
	    
	    }
	});
	
	function callbackPlupload(files){
		// console.info(files);

		jQuery("#fileUploadFileName"+varbRow).val(files.fileUploadFileName[0]);
		jQuery("#fileUploadFileNameTmp"+varbRow).val(files.fileUploadFileNameTmp[0]);
		jQuery("#fileUploadContentType"+varbRow).val(files.fileUploadContentType[0]);
		
		jQuery("#fileThumbnail"+varbRow).val(files.fileThumbnail[0]);
		jQuery("#deleteFlag"+varbRow).val("N");
		
		//var tmpPath = "<s:property value='@com.cct.strutsii.core.config.parameter.domain.ParameterConfig@getAttachFile().getTmpPath()' />";
		jQuery("#fileUploadedPath"+varbRow).val(tmpPathUrl);
	}

</script>
