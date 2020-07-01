<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<!--
   param ใช้ส่งมาต่อ 1 column =  {"nameColumn:type:class:maxlength:nameList:valueList"}

   nameColumn คือ ชื่อ attribute ของ object ที่อยู่ใน list เพื่อจะนำมาแสดง

   type ที่จะเอา มา สร้าง input ต่างๆ
      xxxx
      I = inner text ปกติ
      T = textfield
      A = textarea
      C = combo
      R = radio
      D = datepicker
      CH = checkbok
      B = browse

   class คือ สิ่งที่กำหนดให้ทำอะไร เช่น input_number requireInput ให้พิมพ์ได้แค่ตัวเลข และ มีการ จำเป็นต้องกรอก

   maxlength คือ ขนาดจำนวนที่ input พิมได้ ใส่ ถ้าไม่ต้องการ กำหนด maxlength ให้ใส่ null มา

   nameList คือ  ชื่อ list ที่ต้องการ load มาใช้ใน  combo,radio,checkbok

   valueList คือ  value จะ set index ของ list กรณีที่ต้องการแก้ไขข้อมูล
 -->
 
<script type="text/javascript">
	var varbRow = "";
	var varbColumn = "";
	var varbFornId = "";

	// แก้ไข path temp ให้เปลี่ยนจากการรับค่าเป็น ดึงค่าจาก parameter config: nanthaporn.p 20150513
	var tmpPathUrl = "";
	var uploader = "";
// 	var delFile = "";
	
	jQuery().ready(function(){
		tmpPathUrl = "<s:property value='@com.cct.tutorial.master.core.config.parameter.domain.ParameterConfig@getParameter().getAttachFile().getTmpPath()' />";
// 		console.log("tmpPathUrl : " + tmpPathUrl);
// 		delFile = '<s:property value="#delFile"/>';
// 		console.log("delFile: " + delFile);
		
		//ใช้ alert ตอน browse ไฟล์เกินขนาดที่กำหนด
  		var fileSizeMsg = '<s:property value="#properties['maxFileSize']"/>';
		if (fileSizeMsg != "" && parseInt(fileSizeMsg) >= 1024) {
			fileSizeMsg = parseInt(fileSizeMsg)/1024 + "MB";
		} else {
			fileSizeMsg = fileSizeMsg + "KB";
		}
		
// 		console.log("tab : " + jQuery("li[role='tab']").length);
		if (jQuery("li[role='tab']").length > 0) {
// 			jQuery("li[role='tab']").click(function(event) {
	 			jQuery("#container").find("div[id$='_container']").eq(0).remove();
	 			
	 			jQuery("#<s:property value="#divresult[0]"/> .btnPickfiles").each(function () {
	 				var btnId = jQuery(this).attr("id")
	 				var uploader = new plupload.Uploader({
						runtimes : 'html5,flash,silverlight,html4',
						url : '<s:property value="#urlUpload"/>',
						file_data_name:'fileMeta.fileUpload',
						max_file_size : '<s:property value="#properties['maxFileSize']"/>' + 'kb', // nanthaporn.p 20150513: เปลี่ยนหน่อยจาก mb เป็น kb
						browse_button : btnId, // you can pass in id...
						container: document.getElementById('container'), // ... or DOM Element itself

						// nanthaporn.p 20150513: สำหรับการ browse file แบบ drag & drop from explorer
						// Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
						drop_element : '<s:property value="#divresult[0]"/>_drop-target',
						
						// nanthaporn.p 20150513: browse single file
						multi_selection: false,
						max_file_count : 1,
						
					   	filters : [
							{title : "files", extensions : "<s:property value='#filters' />"}
				        ],
				        
				     	// nanthaporn.p 20150513: แก้ไข version plupload
				        // Flash settings
				      	flash_swf_url : '<s:url value="/js/plupload-2.1.2/js/Moxie.swf" />',
						silverlight_xap_url : '<s:url value="/js/plupload-2.1.2/js/Moxie.xap" />',
			        
				        // PreInit events, bound before any internal events
				        preinit : {
							Init: function(up, info) {
								log('[Init]', 'Info:', info, 'Features:', up.features);
				            },
				 
							UploadFile: function(up, file) {
				            	log('[UploadFile]', file);
				 
				                // You can override settings before the file is uploaded
				                // up.setOption('url', 'upload.php?id=' + file.id);
				                // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
				            }
			        	},
			        	
				         // Post init events, bound after the internal events
			        	init : {
			            	PostInit: function() {
			                	// Called after initialization is finished and internal event handlers bound
			                	log('[PostInit]');
			            	},
			  
				            FilesAdded: function(up, files) {
			    	            up.refresh(); // Reposition Flash/Silverlight
			        	    	up.start(); // action
			            	},
			  
			        	    FileUploaded: function(up, file, response) {
			           			var obj = jQuery.parseJSON(response.response);
				           		
								//jQuery('#' + file.id + " b").html(tagImg);
								if (jQuery("#tr"+varbRow+" #td"+varbColumn+" #" + btnId).length > 0) {
									callbackPlupload(obj);
								}
								
				                // Called when file has finished uploading
			    	            log('[FileUploaded] File:', file, "response:", response);
			        	    },
			  
				            Error: function(up, args) {
			    	            // Called when error occurs
			    	            
			    	            //ไฟล์ไม่รองรับ
			 					if(args.code == "-601") {
			 			     		// alert('ไม่รองรับประเภทไฟล์ดังกล่าว กรุณาตรวจสอบไฟล์');
			 						alert(validateMessage.CODE_10045.replace("xxx", "<s:property value='%{#filters}' />"));
			 			  		} 
			 					
			    	            //ขนาดไฟล์เกิน
			 					else if (args.code == "-600") {
			 			     		// alert('ขนาดไฟล์เกินกว่าที่ระบบอนุญาต กรุณาตรวจสอบไฟล์');
			 			     		     		
			 						alert(validateMessage.CODE_10046.replace("xxx", fileSizeMsg));
			 					}
			    	            
			        	        log('[Error] ', args);
			            	}
			        	}
					});

			 		uploader.init();
	 			});
// 			});
		} else {
			jQuery('#<s:property value="#divresult[0]"/> .btnPickfiles').each(function () {
				var btnId = jQuery(this).attr("id")
				var uploader = new plupload.Uploader({
					runtimes : 'html5,flash,silverlight,html4',
					url : '<s:property value="#urlUpload"/>',
					file_data_name:'fileMeta.fileUpload',
					max_file_size : '<s:property value="#properties['maxFileSize']"/>' + 'kb',
					browse_button : btnId, 						// you can pass in id...
					container: document.getElementById('container'), 	// ... or DOM Element itself
						
					// nanthaporn.p 20150513: สำหรับ drag & drop from explorer
					// Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
					drop_element : '<s:property value="#divresult[0]"/>_drop-target',
						
					// nanthaporn.p 20150513: สำหรับ browse single file
					multi_selection: false,	
					
					filters : [
						{title : "files", extensions : "<s:property value='#filters' />"}
					],
				        
					// nanthaporn.p 20150513: เปลี่ยน version plupload
				    // Flash settings
				    flash_swf_url : '<s:url value="/js/plupload-2.1.2/js/Moxie.swf" />',
					silverlight_xap_url : '<s:url value="/js/plupload-2.1.2/js/Moxie.xap" />',
				        
				    // Pre init events, bound before any internal events
				    preinit : {
						Init: function(up, info) {
							log('[Init]', 'Info:', info, 'Features:', up.features);
						},
				 
						UploadFile: function(up, file) {
				        	log('[UploadFile]', file);
				 
							// You can override settings before the file is uploaded
				            // up.setOption('url', 'upload.php?id=' + file.id);
				            // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
						}
					},
				    
					// Post init events, bound after the internal events
			        init : {
			        	PostInit: function() {
			            	// Called after initialization is finished and internal event handlers bound
			            	log('[PostInit]');
						},
			  
						FilesAdded: function(up, files) {
			    	    	up.refresh(); 	// Reposition Flash/Silverlight
			        	    up.start(); 	// action
						},
			            	
			        	FileUploaded: function(up, file, response) {
			           		var obj = jQuery.parseJSON(response.response);
				           	
							//jQuery('#' + file.id + " b").html(tagImg);
							if (jQuery("#tr"+varbRow+" #td"+varbColumn+" #" + btnId).length > 0) {
								callbackPlupload(obj);
							}
			            		
				            // Called when file has finished uploading
			    	        log('[FileUploaded] File:', file, "response:", response);
						},
			  
				        Error: function(up, args) {
				        	//console.info('error');
				            // console.info(args.file.type);
				            // console.info(up);
			    	        
				            // Called when error occurs
			    	        //ไฟล์ไม่รองรับ
			 				if(args.code == "-601") {
								// alert('ไม่รองรับประเภทไฟล์ดังกล่าว กรุณาตรวจสอบไฟล์');
			 					alert(validateMessage.CODE_10045.replace("xxx", "<s:property value='%{#filters}' />"));
							} 
			 					
			    	        //ขนาดไฟล์เกิน
			 				else if (args.code == "-600") {
			 					// alert('ขนาดไฟล์เกินกว่าที่ระบบอนุญาต กรุณาตรวจสอบไฟล์');
									 			     		
			 					alert(validateMessage.CODE_10046.replace("xxx", fileSizeMsg));
							}
			    	            
			        	    log('[Error] ', args);
						}
					}
				});
					
				uploader.init();
			});
		}
	});
	
	function log() {
       	var str = "";

        plupload.each(arguments, function(arg) {
   	    	// console.info(arguments)
       		if (arg instanceof plupload.File) {
       			// console.info(arg)
			}
		});
	}

	var Map = {};
	function editPage(id,url){
		//alert(id);
		//alert(url);
	}
	
	function viewPage(id,url){
		//alert(id);
		//alert(url);
	}

	function browseFile(bRow,bColumn,bFormId){
// 		console.log("browseFile");
		this.varbRow = bRow;
		this.varbColumn = bColumn;
		this.varbFornId = bFormId;
	}

	function callbackPlupload(files){
// 		console.info("callbackPlupload");
// 		console.info(files);
		
// 		console.log("Row : " + varbRow);
// 		console.log("Col : " + varbColumn);
// 		console.log("Form : " + varbFornId);
		
		var tag = "<a href='javascript:void(0);' class='LINK' onclick='viewer(\""+ tmpPathUrl +files.fileUploadFileNameTmp[0] +"\",\"" + files.fileUploadFileName[0] + "\");' >"
			+ files.fileUploadFileName[0]
			+ "</a>";
			
		jQuery("#" + varbFornId + "_divFileName" + varbRow + varbColumn).html(tag);

		jQuery("#" + varbFornId + "_fileUploadFileName" + varbRow + varbColumn).val(files.fileUploadFileName[0]);
		jQuery("#" + varbFornId + "_fileUploadFileNameTmp" + varbRow + varbColumn).val(files.fileUploadFileNameTmp[0]);
		jQuery("#" + varbFornId + "_fileUploadContentType" + varbRow + varbColumn).val(files.fileUploadContentType[0]);
		jQuery("#" + varbFornId + "_fileUploadedPath" + varbRow + varbColumn).val(tmpPathUrl);
		jQuery("#" + varbFornId + "_deleteFlag"+varbRow+varbColumn).val("N");
		
		jQuery("#" + varbFornId + "_fileUploadFileName"+varbRow).val(files.fileUploadFileName[0]);
		jQuery("#" + varbFornId + "_fileUploadFileNameTmp"+varbRow).val(files.fileUploadFileNameTmp[0]);
		jQuery("#" + varbFornId + "_fileUploadContentType"+varbRow).val(files.fileUploadContentType[0]);
		jQuery("#" + varbFornId + "_deleteFlag"+varbRow).val("N");
		
		//var tmpPath = "<s:property value='@com.cct.strutsii.core.config.parameter.domain.ParameterConfig@getAttachFile().getTmpPath()' />";
// 		console.info(tmpPathUrl);
		jQuery("#fileUploadedPath"+varbRow).val(tmpPathUrl);
	}

	function viewer(srcFile, fileName){ 
		
		jQuery("#fileNameTmpForViewer").val(srcFile);
		jQuery("#fileNameForViewer").val(fileName);
		
		window.open('<s:url value="%{#application.CONTEXT_JS_CSS}/jsp/template/viewer.jsp" />');
	}

	function controlInput(classControl){
		var newControlStr = "";
		var newControl;
		if(classControl!="null"&&classControl!=""){
			var classes = classControl.split(" ");
			for(var i = 0; i<= classes.length; i++){
				if (classControl.indexOf("requireInput") > -1) {
					newControlStr = classControl.replace("requireInput","");
				} else {
					newControlStr = classControl;
			    }
		
				newControl = newControlStr.split("_");
			    if (classControl.indexOf("currency")>-1) {
			   		if (newControl.length == 4 ) {
			   			new CurrencyControl(parseInt(newControl[2]),parseInt(newControl[3]));
			        } else if (newControl.length == 3) {
			        	new CurrencyControl(parseInt(newControl[2]));
			        } else {
			        	new CurrencyControl();
			        }
		
			    } else if(classControl.indexOf("number")>-1) {
			    	if (newControl.length == 4 ){
			   			new NumberControl(parseInt(newControl[2]),parseInt(newControl[3]));
			        } else if (newControl.length == 3){
			        	new NumberControl(parseInt(newControl[2]));
			        } else {
			        	new NumberControl();
			        }
			    }
			}
		}
	}
	
	/** Set select combobox value **/
	function onChangeCombobox(ele) {
		var name = ele.id.split("-")[1];
		jQuery("#" + name + "n").val(jQuery("#" + ele.id + " option:selected").text());
	}
	
	/** Set select radio value **/
	function onSelectRadio(ele) {
		var name = ele.name.split("-")[1];
		jQuery("#" + name).val(ele.value);
		jQuery("#" + name + "n").val(jQuery(ele).attr("data"));
	}

	/** Set select checkbox value **/
	function onSelectCheckbox(ele) {
		var code = "";
		var desc = "";
		jQuery("input[name='" + ele.name + "']").each(function() {
			if (jQuery(this).prop("checked")) {
				code += "," + jQuery(this).val();
				desc += "," + jQuery(this).attr("data");
			}
		});
		
		if (code != "") {
			code = code.substring(1, code.lenght);
		}
		
		if (desc != "") {
			desc = desc.substring(1, desc.lenght);
		}
		
		var name = ele.name.split("-")[1];
		jQuery("#" + name).val(code);
		jQuery("#" + name + "n").val(desc);
	}
	
</script>
<style type="text/css">
	.alignLeft {
		text-align: left;
	}
	
	.alignRight {
		text-align: right;
	}
	
	.alignCenter {
		text-align: center;
	}
	
	.dragover {
        background: rgba(255, 255, 255, 0.4);
    	border-color: green;
	}
</style>
</head>
<!-- ******************************* DIV RESULT ********************************* -->

<!-- ******************************* HEADER ********************************* -->
<input type="hidden" id="<s:property value='#divresult[0]'/>_function" value="<s:property value='#preSubmitFunct'/>" />
<input type="hidden" id="<s:property value='#divresult[0]'/>_columnCheckDup" value="<s:property value='#columnCheckDup'/>" />
<input type="hidden" id="<s:property value='#divresult[0]'/>_caseCheckDup" value="<s:property value='#caseCheckDup'/>" />
<input type="hidden" id="<s:property value='#divresult[0]'/>_listTableData" value="<s:property value='#listTableData'/>" />
<input type="hidden" id="<s:property value='#divresult[0]'/>_caseCheckDup" value="<s:property value='#caseCheckDup'/>" />
<input type="hidden" id="<s:property value='#divresult[0]'/>_columnDataSize" value="<s:property value='#columnData.size()' />" />
<%-- <input type="hidden" id="<s:property value='#divresult[0]'/>_delFile" value="<s:property value='#delFile' />" /> --%>
<input type="hidden" id="<s:property value='#divresult[0]'/>_tableDisable" value="<s:property value='#tableDisable' />" />

<input type="hidden" id="fileNameTmpForViewer" />
<input type="hidden" id="fileNameForViewer" />

<div id="uploader" style="display: none;"></div>

<!-- 
<table class="TOTAL_RECORD" id="headerTable_<s:property value='#divresult[0]'/>" style="display: none">
	<tr>
		<td class="LEFT"></td>
		<td class="RIGHT"></td>
	</tr>
</table>
 -->

<table id="datatable_<s:property value='#divresult[0]'/>" class="table table-bordered table-striped nowrap no-footer dtr-inline">
	<!-- ******************************* COLUMN HEADER ********************************* -->
	<thead>
		<tr class="th">
			<th class="order"><s:text name="no" /></th>
			<th class="col-checkbox checkbox" style="display: none;">
				<div class="checkbox">
					<input value='<s:property value="#dataId"/>' id="dt-checkbox-<s:property value='#divresult[0]'/>-checkAll" type="checkbox" />
					<label for="dt-checkbox-<s:property value='#divresult[0]'/>-checkAll">&nbsp;</label>
				</div>
			</th>
			<s:iterator value="columnName" status="statusH" var="column_name">
				<th id="th<s:property value="#statusH.index" />" class="<s:property value="#columnCSSClass[#statusH.index]" />" style="<s:property value="#columnStyle[#statusH.index]" />"><s:property value="#column_name" />
					<input type="hidden" id="classColumn<s:property value="#statusH.index"/>" value="<s:property value="#columnCSSClass[#statusH.index]" />" />
				</th>
			</s:iterator>
		</tr>
	</thead>
	<!-- Loop get object from string name -->
	<s:set var="listAddEditNameArray" value='%{#listTableData.split("[.]")}' />
	<s:iterator value="listAddEditNameArray" status="statusAddEdit" var="listAddEdit">
		<s:if test="#statusAddEdit.index == 0">
			<s:set var="listAddEditData" value="[#listAddEdit]" />
		</s:if>
		<s:else>
			<s:set var="listAddEditData" value="#listAddEditData[#listAddEdit]" />
		</s:else>
	</s:iterator>
	
	<tbody>
		<!-- ******************************* COLUMN DETAIL ********************************* -->
		<s:iterator value="#listAddEditData" status="statusRow" var="domain">
			<s:if test="#statusRow.even == true">
				<s:set var="tabclass" value="%{'even'}" />
			</s:if>
			<s:else>
				<s:set var="tabclass" value="%{'odd'}" />
			</s:else>
			
			<s:if test='#domain.deleteFlag == "Y"'>
				<tr class="td <s:property value='tabclass'/>" id="tr<s:property value="#statusRow.index"/>" style="display: none;">
			</s:if>
			<s:else>
				<tr class="td <s:property value='tabclass' />" id="tr<s:property value="#statusRow.index"/>">
			</s:else>
				<td class="col-order text-center"><s:property value="%{#statusRow.index + 1}"/></td>
				<td class="col-checkbox" style="display: none;">
					<!-- columnID -->
					<s:iterator value="columnID" status="status" var="cId" >
						<s:set var="cIds" value='%{#cId.split("[.]")}'/>
							<s:iterator value="cIds" status="status" var="cIdss" >
								<s:if test="#status.index == 0">
									<s:set var="dataId"  value="#domain[#cIdss]" />
								</s:if>	
								<s:else>
									<s:set var="dataId"  value="#dataId[#cIdss]" />
								</s:else>									
						</s:iterator>
					</s:iterator>
					
					
					<div class="checkbox">
						<input value='<s:property value="#dataId"/>' id="dt-checkbox-<s:property value='#divresult[0]'/>-<s:property value="%{#statusRow.index + 1}"/>" type="checkbox">
						<label for="dt-checkbox-<s:property value='#divresult[0]'/>-<s:property value="%{#statusRow.index + 1}"/>">&nbsp;</label>
					</div>
						
					<!-- hiddenData หลัก -->
					<input type="hidden" name="<s:property value='#listTableData'/>[<s:property value='#statusRow.index ' />].id" value="<s:property value="#domain.id"/>"/> <!-- id For Check Added? -->
					<input type='hidden' name="<s:property value='#listTableData'/>[<s:property value='#statusRow.index ' />].deleteFlag" value='<s:property value="#domain.deleteFlag"/>'/> <!-- deleteFlag For Check Deleted? -->
					<input type='hidden' name="<s:property value='#listTableData'/>[<s:property value='#statusRow.index ' />].edited" value='<s:property value="#domain.edited"/>'/> <!-- edited? -->
						
					<!-- hiddenData เพิ่มเติม -->
					<s:iterator value="hiddenData" status="statusHidd" var="hidden" >	
						<s:set var="hiddens" value='%{#hidden.split("[.]")}'/>
							<s:iterator value="hiddens" status="status" var="hiddenss" >	
								<s:if test="#status.index == 0">
									<s:set var="subHidd"  value="#domain[#hiddenss]" />
								</s:if>
								<s:else>
									<s:set var="subHidd"  value="#subHidd[#hiddenss]" />
								</s:else>
						</s:iterator>						
						<input type="hidden" name="<s:property value='#listTableData'/>[<s:property value='#statusRow.index ' />].<s:property value="#hidden"/>" value="<s:property value="#subHidd"/>" />
					</s:iterator>						
				</td>
			
				<!-- columnData -->
				<s:iterator value="columnData" status="status" var="key">
					<s:set var="hiddenParam" value='%{#key.split("[:]")}' />
					<s:set var="keys" value='%{#hiddenParam[0].split("[.]")}' />
					<!-- check attribute that single or subAttribute -->
					<s:iterator value="keys" status="status" var="keyss">
					
						<!-- index = data -->
						<s:if test='!#hiddenParam[1].equals("B")'>
							<s:if test="#status.index == 0">
								<s:set var="indexs" value="[#keyss]" />
							</s:if>
							<s:else>
								<s:set var="indexs" value="#indexs[#keyss]" />
							</s:else>
						</s:if>
						<s:else>
							<s:set var="indexsBrowsefileUploadFileName" value="[#keyss].fileUploadFileName" />
							<s:set var="indexsBrowsefileThumbnail" value="[#keyss].fileThumbnail" />
							<s:set var="indexsBrowsefileUploadedPath" value="[#keyss].fileUploadedPath" />
							<s:set var="indexsBrowseFileUploadFileNameTmp" value="[#keyss].FileUploadFileNameTmp" />
							<s:set var="indexsBrowsefileUploadContentType" value="[#keyss].fileUploadContentType" />
							<s:set var="indexsBrowsedeleteFlag" value="[#keyss].deleteFlag" />
						</s:else>
						
						<!-- indexsL = data in list -->
						<s:if test='!#hiddenParam[5].equals("null")'>
							<s:set var="keysL" value='%{#hiddenParam[5].split("[.]")}' />
							<s:iterator value="keysL" status="status" var="keysLL">
								<s:if test="#status.index == 0">
									<s:set var="indexsL" value="[#keysLL]" />
								</s:if>
								<s:else>
									<s:set var="indexsL" value="#indexsL[#keysLL]" />
								</s:else>
							</s:iterator>
						</s:if>
						<s:else>
							<s:set var="indexsL" value="" />
						</s:else>
					</s:iterator>
					
					<!-- class -->
					<s:if test='#hiddenParam[2].indexOf("number")>-1 || #hiddenParam[2].indexOf("currency")>-1 '>
						<s:set var="cssAlign" value="%{'alignRight'}" />
					</s:if>
					<s:elseif test='#hiddenParam[2].indexOf("date")>-1 || #hiddenParam[1].equals("D")'>
						<s:set var="cssAlign" value="%{'alignCenter'}" />
					</s:elseif>
					<s:else>
						<s:set var="cssAlign" value="%{'alignLeft'}" />
					</s:else>
					
					<!-- Class Params -->
					<s:if test='!#hiddenParam[2].equals("null")'>
						<s:set var="myClass" value="#hiddenParam[2]" />
					</s:if>
					<s:else>
						<s:set var="myClass" value="" />
					</s:else>
					
					<!-- Draw Column Data -->
					<td class="<s:property value="#columnCSSClass[#status.index]" />" id="td<s:property value="#status.index" />" >
						<div id="drawEdit<s:property value="#statusRow.index"/><s:property value="#status.index"/>" class="<s:property value="#cssAlign"/>" style="padding-left: 5px; padding-right: 5px;">
							 <!-- List manage -->
							 <s:if test='!#hiddenParam[5].equals("null")'>
								<s:set var="keysL" value='%{#hiddenParam[5].split("[.]")}' />
								
								<!-- Find list value -->
								<s:iterator value="keysL" status="status" var="keysLL">
									<s:if test="#status.index == 0">
										<s:set var="indexsL" value="[#keysLL]" />
									</s:if>
									<s:else>
										<s:set var="indexsL" value="#indexsL[#keysLL]" />
									</s:else>
								</s:iterator>
								
								<!-- Find list name -->
								<s:set var="keysNList" value='%{#hiddenParam[4].split("[.]")}' />
								<s:iterator value="keysNList" status="status" var="keysNLL">
									<s:if test="#status.index == 0">
										<s:set var="indexsNL" value="[#keysNLL]" />
									</s:if>
									<s:else>
										<s:set var="indexsNL" value="#indexsNL[#keysNLL]" />
									</s:else>
								</s:iterator>
							</s:if>
							
							<s:set var="nameList" value='%{#hiddenParam[4].replaceAll("[.]", "_")}' />
							<s:if test='#hiddenParam[1].equals("T")'>
								<!-- T = textfield -->
								<s:if test="#tableDisable">
									<s:property value="#indexs" />
									
									<input type="hidden"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" 
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										value="<s:property value="#indexs"/>"
										/>
								</s:if>
								<s:else>
									<input type="text"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" 
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										value="<s:property value="#indexs"/>"
										class="<s:property value="#myClass"/> txt"
										style="width: 93%;"
										maxlength="<s:property value="#hiddenParam[3]"/>"
										validName="<s:text name='%{#hiddenParam[6]}'/>"
										/>
								</s:else>
							</s:if>
							
							<s:elseif test='#hiddenParam[1].equals("A")'>
								<!-- A = textarea -->
								<s:if test="#tableDisable">
									<s:property value="#indexs" />
									
									<input type="hidden"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" 
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										value="<s:property value="#indexs"/>"
										/>
								</s:if>
								<s:else>
									<textarea 
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" 
										cols="" rows="" 
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										class="<s:property value="#myClass"/> ui-corner-all" 
										style="resize: none; width: 93%;" 
										validName="<s:text name='%{#hiddenParam[6]}'/>"
										maxlength="<s:property value="#hiddenParam[3]"/>" ><s:property value="#indexs"/></textarea>
								</s:else>
							</s:elseif>
							
							<s:elseif test='#hiddenParam[1].equals("C")'>
								<!-- C = combo -->
								<s:if test="#tableDisable">
									<s:property value="#indexs" />
									
									<input type="hidden"
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[5]" />"
										value="<s:property value="#indexsL"/>"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
								</s:if>
								<s:else>
									<s:select list="#indexsNL" 
										id="combobox-%{#divresult[0]}_listData%{#statusRow.index}%{#status.index}" 
										name="%{#listTableData}[%{#statusRow.index}].%{#hiddenParam[5]}"
										headerKey="" 
										headerValue="" 
										listKey="key" 
										listValue="value" 
										cssClass="%{#myClass} select-table"
										validName="%{getText(#hiddenParam[6])}" 
										cssStyle="width: 182px;" 
										/>
								</s:else>
								
								<input type="hidden"
									name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
									id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>n"
									value="<s:property value="#indexs"/>" />
							</s:elseif>
							
							<s:elseif test='#hiddenParam[1].equals("R")'>
								<!-- R = radio -->
								<div class="requireGroup" validName="<s:text name='%{#hiddenParam[6]}'/>" style="border-radius: 6px;">
									<s:if test="#tableDisable">
										<s:property value="#indexs" />
									</s:if>
									<s:else>
										<s:iterator value="#indexsNL" status="statusR" var="keyR">
											<s:if test="#indexsL.equals(#keyR.key)">
												<input type="radio"
													name="radio-<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>"
													value="<s:property value="#keyR.key"/>"
													data="<s:property value="#keyR.value"/>" 
													checked="checked" 
													class="<s:property value="#myClass"/>"
													onclick="onSelectRadio(this);"
													/>
												<span class="margin-radio">
													<s:property value="#keyR.value" />
												</span>
											</s:if>
											<s:else>
												<input type="radio"
													name="radio-<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>"
													value="<s:property value="#keyR.key"/>"
													data="<s:property value="#keyR.value"/>" 
													class="<s:property value="#myClass"/>"
													onclick="onSelectRadio(this);"
													/>
												<span class="margin-radio">
													<s:property value="#keyR.value" />
												</span>
											</s:else>
										</s:iterator>
									</s:else>
									
									<input type="hidden"
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[5]" />"
										value="<s:property value="#indexsL"/>"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
		
									<input type="hidden"
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>n"
										value="<s:property value="#indexs"/>" />
								</div>
							</s:elseif>
							
							<s:elseif test='#hiddenParam[1].equals("CH")'>
								<!-- CH = checkbok -->
								<div class="requireGroup" validName="<s:text name='%{#hiddenParam[6]}'/>" style="border-radius: 6px;" >
									<s:if test="#tableDisable">
										<s:property value="#indexs" />
									</s:if>
									<s:else>
										<s:iterator value="#indexsNL" status="statusCH" var="keyCH">
											<s:set var="myChecked" value="false" />
											<s:iterator value='#indexsL.split(",")' status="statusChCode" var="keyChCode">
												<s:if test="#keyChCode.trim().equals(#keyCH.key)">
													<s:set var="myChecked" value="true" />
												</s:if>
											</s:iterator>
											
											<s:if test="#myChecked == true">
												<input type="checkbox"
													name="checkbox-<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>"
													value="<s:property value="#keyCH.key"/>"
													data="<s:property value="#keyCH.value"/>" 
													checked="checked" 
													class="<s:property value="#myClass"/>"
													onclick="onSelectCheckbox(this);"
													/>
												<s:property value="#keyCH.value" />
											</s:if>
											<s:else>
												<input type="checkbox"
													name="checkbox-<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>"
													value="<s:property value="#keyCH.key"/>"
													data="<s:property value="#keyCH.value"/>" 
													class="<s:property value="#myClass"/>"
													onclick="onSelectCheckbox(this);"
													/>
												<s:property value="#keyCH.value" />
											</s:else>
										</s:iterator>
									</s:else>
									
									<input type="hidden"
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[5]" />"
										value="<s:property value="#indexsL"/>"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
		
									<input type="hidden"
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>n"
										value="<s:property value="#indexs"/>" />
								</div>
							</s:elseif>
							
							<s:elseif test='#hiddenParam[1].equals("D")'>
								<s:if test="#tableDisable">
									<s:property value='@util.MVUtil@convertDateForDisplay(#indexs, #session.user.locale)' />
									
									<input type="hidden"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" 
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										value="<s:property value="#indexs"/>"
										/>
								</s:if>
								<s:else>
									<s:textfield 
										name="%{#listTableData}[%{#statusRow.index}].%{#hiddenParam[0]}"
										cssClass="%{#myClass} form-control datepicker-datatable"
										data-dp-id="datepicker-%{#divresult[0]}-%{#statusRow.index + 1}"
										validName="%{getText(#hiddenParam[6])}" 
										/>
								</s:else>
							</s:elseif>
							
							<s:elseif test='#hiddenParam[1].equals("B")'>
								<s:if test="#tableDisable">
									<div style="width: 100%; float: left; vertical-align: middle;">
										<div id="<s:property value="#divresult[0]"/>_divFileName<s:property value="#statusRow.index"/><s:property value="#status.index"/>" style="vertical-align: middle; padding-left: 5px; padding-right: 5px;">
											<a href="javascript:void(0);" 
												onclick="viewer('<s:property value="#indexsBrowsefileUploadedPath"/><s:property value='#indexsBrowseFileUploadFileNameTmp'/>','<s:property value="#indexsBrowsefileUploadFileName"/>')"
												class="LINK">
												<s:property value="#indexsBrowsefileUploadFileName"/>
											</a>
										</div>
									</div>
								</s:if>
								<s:else>
									<div class="row">
										<div class="col-7" style="word-wrap: break-word;white-space: normal;">
											<div id="<s:property value="#divresult[0]"/>_divFileName<s:property value="#statusRow.index"/><s:property value="#status.index"/>" style="vertical-align: middle; padding-left: 5px; padding-right: 5px;">
												<a href="javascript:void(0);" 
													onclick="viewer('<s:property value="#indexsBrowsefileUploadedPath"/><s:property value='#indexsBrowseFileUploadFileNameTmp'/>','<s:property value="#indexsBrowsefileUploadFileName"/>')"
													class="LINK">
													<s:property value="#indexsBrowsefileUploadFileName"/>
												</a>
											</div>
										</div>
										<div class="col">
											<div id="<s:property value="#divresult[0]"/>_divFileName<s:property value="#statusRow.index"/><s:property value="#status.index"/>" style="vertical-align: middle; padding-left: 5px; padding-right: 5px;">
												<input type="button" 
							                    	id="<s:property value="#divresult[0]"/>_pickfiles<s:property value="#statusRow.index"/><s:property value="#status.index"/>" 
							                    	value="Browse" 
							                    	class="requireInput btn btn-default btnPickfiles <s:property value="#myClass"/>"
							                    	onclick="browseFile('<s:property value="#statusRow.index"/>','<s:property value="#status.index"/>','<s:property value="#divresult[0]"/>');"
							                    	validName="<s:text name='%{#hiddenParam[6]}'/>"
							                    	validFile="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />.fileUploadFileName"
							                    	/>
											</div>
										</div>
									</div>
								</s:else>
								
								<input type="hidden"
									name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />.fileUploadFileName"
									value="<s:property value="#indexsBrowsefileUploadFileName"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadFileName<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
	
								<input type="hidden"
									name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />.fileThumbnail"
									value="<s:property value="#indexsBrowsefileThumbnail"/>"
									id="<s:property value="#divresult[0]"/>_fileThumbnail<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
	
								<input type="hidden"
									name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />.fileUploadContentType"
									value="<s:property value="#indexsBrowsefileUploadContentType"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadContentType<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
	
								<input type="hidden"
									name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />.fileUploadedPath"
									value="<s:property value="#indexsBrowsefileUploadedPath"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadedPath<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
	
								<input type="hidden"
									name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />.fileUploadFileNameTmp"
									value="<s:property value="#indexsBrowseFileUploadFileNameTmp"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadFileNameTmp<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
	
								<input type="hidden"
									name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />.deleteFlag"
									value="<s:property value="#indexsBrowseDeleteFlag"/>"
									id="<s:property value="#divresult[0]"/>_deleteFlag<s:property value="#statusRow.index"/><s:property value="#status.index"/>" />
									
								<input type="hidden"
									name="fileUploadFileName<s:property value="#statusRow.index"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadFileName<s:property value="#statusRow.index"/>"
									value="<s:property value="#indexsBrowsefileUploadFileName"/>">
									
								<input type="hidden"
									name="fileUploadFileNameTmp<s:property value="#statusRow.index"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadFileNameTmp<s:property value="#statusRow.index"/>"
									value="<s:property value="#indexsBrowsefileUploadFileNameTmp"/>">
									
								<input type="hidden"
									name="fileUploadContentType<s:property value="#statusRow.index"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadContentType<s:property value="#statusRow.index"/>"
									value="<s:property value="#indexsBrowsefileUploadedPath"/>">
									
								<input type="hidden"
									name="fileUploadedPath<s:property value="#statusRow.index"/>"
									id="<s:property value="#divresult[0]"/>_fileUploadedPath<s:property value="#statusRow.index"/>"
									value="<s:property value="#indexsBrowsefileUploadedPath"/>">
	
								<input type="hidden"
									name="fileThumbnail<s:property value="#statusRow.index"/>"
									id="<s:property value="#divresult[0]"/>_fileThumbnail<s:property value="#statusRow.index"/>"
									value="<s:property value="#indexsBrowsefileThumbnail"/>">
	
								<input type="hidden"
									name="deleteFlag<s:property value="#statusRow.index"/>"
									id="<s:property value="#divresult[0]"/>_deleteFlag<s:property value="#statusRow.index"/>"
									value="<s:property value="#indexsBrowseDeleteFlag"/>">
								
								<input type="hidden" id="valueList<s:property value="#statusRow.index"/><s:property value="#status.index"/>" value="<s:property value="#indexsL"/>" />
								<s:set var="indexColume" value="#status.index" />
							
							</s:elseif>
							
							<s:else>
								<!-- I = inner text -->
								<s:property value="#indexs" />
								<input type="hidden"
										id="<s:property value="#divresult[0]"/>_listData<s:property value="#statusRow.index"/><s:property value="#status.index"/>" 
										name="<s:property value="#listTableData"/>[<s:property value="#statusRow.index"/>].<s:property value="#hiddenParam[0]" />"
										value="<s:property value="#indexs"/>"
										/>
									
								<s:if test='!#indexs.equals("null") && #hiddenParam[2].indexOf("requireInput")>-1'>
									<span class="star-red">*</span>
								</s:if>
							</s:else>
							
						</div>
					</td>
				</s:iterator>
			</tr>
		</s:iterator>
	</tbody>
</table>

<!-- ******************************* FOOTER ********************************* -->

<s:set var="stTable" value='%{#settingTable[0].split("[:]")}' />
<script>
	<!--crate table -->
	jQuery("#dt-checkbox-" + "<s:property value='#divresult[0]'/>" + "-checkAll").click(function(){
		if(jQuery(this).is(':checked'))
			jQuery("#datatable_" + "<s:property value='#divresult[0]'/>" + " tr:visible [id^=dt-checkbox-" + "<s:property value='#divresult[0]'/>" + "-]:not(:checked)").click();
		else
			jQuery("#datatable_" + "<s:property value='#divresult[0]'/>" + " tr:visible [id^=dt-checkbox-" + "<s:property value='#divresult[0]'/>" + "-]:checked").click();
	});
	
	jQuery("#datatable_" + "<s:property value='#divresult[0]'/>").DataTable( {
          "scrollY": 200
        , "scrollX": true
        , "ordering": false
        , "bPaginate": false
        , "bLengthChange": false
        , "bFilter": false
        , "bInfo": false
    });
	
	/*
	jQuery('div#<s:property value="#divresult[0]"/>').ready(function(){
		var columnLength = "<s:property value='#columnData.size()' />"; //จำนวน Column ในตาราง ไม่รวม ลำดับ กับ Checkbok
		var colL = parseInt(columnLength);
		var tableId = "<s:property value='#divresult[0]'/>";
		for(var i = 0; i<=colL-1;i++){
			classControl = jQuery("#"+tableId+"_classX"+i).val();
            if(classControl.indexOf("requireInput")!=-1){
            	// FIXME ปิด ความสามารถการวาด * ที่ header column
//             	jQuery("div#"+tableId +" #th"+i).append("<em>*</em>");
			}
            
            // control input number & currency
   			controlInput(classControl);
		}
		
		jQuery(":button").button();
		jQuery(".spinner").spinner();
		jQuery( ".combobox" ).combobox();
		jQuery("div .radioset").buttonset();
		jQuery("div .accordion").accordion();
		jQuery(document).tooltip();
		
		
		// Bind event onchange combobox
		jQuery('div#<s:property value="#divresult[0]"/> select:visible').each(function() {
			jQuery(this).on('selectmenuchange', function() {
				onChangeCombobox(this);
			});
		});
		
		jQuery("div .slider").slider({
			range : true,
			values : [ 17, 67 ]
		});
	});
	
	jQuery('#tableId_<s:property value="#divresult[0]"/>').wrap("<div id='<s:property value='#divresult[0]'/>_drop-target'></div><div id='<s:property value='#divresult[0]'/>_debug'></div>");
	
	var filter = '<s:property value="#filters"/>';
	if (filter != "") {
		filter = "<s:text name='supportExtensionFile' /> " + filter;
	}
	
	var fileSize = '<s:property value="#properties['maxFileSize']"/>';
	if (fileSize != "" && parseInt(fileSize) >= 1024) {
		fileSize = " <s:text name='supportFileSize' /> " + parseInt(fileSize)/1024 + "MB";
	} else {
		fileSize = " <s:text name='supportFileSize' /> " + fileSize + "KB";
	}
	
	var comment = '<div>&nbsp;&nbsp;&nbsp;<font class="comment">'+filter + fileSize+'</font></div>';
	if (comment != "") {
		jQuery('#<s:property value="#divresult[0]"/>').append(comment);
	}
	*/
</script>

<div id="item" style="display: none;">
	<div id="itemTxtF"><s:textfield cssStyle="width: 93%;" /></div>
	<div id="itemTxtA"><textarea style="resize: none; width: 93%;"></textarea></div>
	<!-- ========= 15/01/2558 Browse file BANK -->

	<s:iterator value="columnData" status="status" var="key">
		<s:set var="isDup" value="%{false}" />
		<s:set var="hiddenParam" value='%{#key.split("[:]")}' />
		<s:set var="keys" value='%{#hiddenParam[0].split("[.]")}' />
		<s:iterator value="keys" status="status" var="keyss">
			<s:if test="#status.index == 0">
				<s:set var="indexs" value="[#keyss]" />
			</s:if>
			<s:else>
				<s:set var="indexs" value="#indexs[#keyss]" />
			</s:else>

			<s:if test='!#hiddenParam[5].equals("null")'>
				<s:set var="keysL" value='%{#hiddenParam[5].split("[.]")}' />
				<s:iterator value="keysL" status="status" var="keysLL">
					<s:if test="#status.index == 0">
						<s:set var="indexsL" value="[#keysLL]" />
					</s:if>
					<s:else>
						<s:set var="indexsL" value="#indexsL[#keysLL]" />
					</s:else>
				</s:iterator>
				<s:set var="keysNList" value='%{#hiddenParam[4].split("[.]")}' />
				<s:iterator value="keysNList" status="status" var="keysNLL">
					<s:if test="#status.index == 0">
						<s:set var="indexsNL" value="[#keysNLL]" />
					</s:if>
					<s:else>
						<s:set var="indexsNL" value="#indexsNL[#keysNLL]" />
					</s:else>
				</s:iterator>
			</s:if>
			<s:else>
				<s:set var="indexsL" value="" />
			</s:else>
		</s:iterator>

		<s:set var="nameList" value='%{#hiddenParam[4].replaceAll("[.]", "_")}' />
		<div id="td<s:property value="#status.index" />">
			<s:if test='!#hiddenParam[5].equals("null")'>
				<input type="hidden"
					name="<s:property value="#listTableData"/>[X].<s:property value="#hiddenParam[5]" />"
					value="<s:property value="#indexs"/>"
					id="<s:property value="#divresult[0]"/>_listDataX<s:property value="#status.index"/>" />
					
				<input type="hidden"
					name="<s:property value="#listTableData"/>[X].<s:property value="#hiddenParam[0]" />"
					value="<s:property value="#indexs"/>"
					id="<s:property value="#divresult[0]"/>_listDataX<s:property value="#status.index"/>" />
			</s:if>
			<s:else>
				<input type="hidden"
					name="<s:property value="#listTableData"/>[X].<s:property value="#hiddenParam[0]" />"
					value="<s:property value="#indexs"/>"
					id="<s:property value="#divresult[0]"/>_listDataX<s:property value="#status.index"/>" />
			</s:else>

			<input type="hidden" value="<s:property value="#listTableData"/>" id="<s:property value="#divresult[0]"/>_nameLits" />
			<input type="hidden" id="<s:property value="#divresult[0]"/>_typeX<s:property value="#status.index"/>" value="<s:property value="#hiddenParam[1]"/>" />
			<input type="hidden" id="<s:property value="#divresult[0]"/>_classX<s:property value="#status.index"/>" value="<s:property value="#hiddenParam[2]"/>" />
			<input type="hidden" id="<s:property value="#divresult[0]"/>_maxlengthX<s:property value="#status.index"/>" value="<s:property value="#hiddenParam[3]"/>" />
			<input type="hidden" id="<s:property value="#divresult[0]"/>_nameListX<s:property value="#status.index"/>" value="<s:property value="#nameList"/>" />
			<input type="hidden" id="<s:property value="#divresult[0]"/>_valueListX<s:property value="#status.index"/>" value="<s:property value="#indexsL"/>" />
		</div>

		<s:if test="#status.index !=0">
			<s:iterator value="columnData" status="statuss" var="s">
				<!-- รอบแรก ไม่เทียบ เพราะถือว่าไม่ซำ้ -->
				<!-- รอบถัดมาจะทำการเทียบ-->
				<s:if test="#status.index >= #statuss.index">
					<!-- เทียบ index ก่อนหน้า index ตัวเอง -->
					<s:set var="hiddenParam_compare" value='%{#s.split("[:]")}' />
					<s:if test="#hiddenParam_compare[4].equals(#hiddenParam[4]) && #status.index > #statuss.index && #hiddenParam_compare[1].equals(#hiddenParam[1])">
						<!-- ซ้ำ -->
						<s:set var="isDup" value="%{true}" />
					</s:if>
					<s:elseif test="#status.index == #statuss.index && #isDup == false">
						<s:if test='#hiddenParam[1].equals("C")'>
							<div id="itemC<s:property value="#nameList"/>">
								<s:select list="#indexsNL" id="%{#nameList}" headerKey="" headerValue="" listKey="key" listValue="value" cssStyle="width: 100%;" cssClass="cmd-hidden" />
							</div>
						</s:if>
						<s:elseif test='#hiddenParam[1].equals("B")'>
							<div id="container" >
								<input type="button" id="pickfiles" value="Select files"/>
							</div>
						</s:elseif>
						<s:elseif test='#hiddenParam[1].equals("R")'>
							<div id="itemR<s:property value="#nameList"/>">
								<div class="requireGroup">
									<s:iterator value="#indexsNL" status="statusR" var="keyR">
										<s:if test="#statusR.index == 0">
											<input type="radio"
												name="radio<s:property value="#hiddenParam[4]"/>"
												value="<s:property value="#keyR.key"/>"
												data="<s:property value="#keyR.value"/>" checked="checked" />
											<s:property value="#keyR.value" />
										</s:if>
										<s:else>
											<input type="radio"
												name="radio<s:property value="#hiddenParam[4]"/>"
												value="<s:property value="#keyR.key"/>"
												data="<s:property value="#keyR.value"/>" />
											<s:property value="#keyR.value" />
										</s:else>
									</s:iterator>
								</div>
							</div>
						</s:elseif>
						<s:elseif test='#hiddenParam[1].equals("CH")'>
							<div id="itemCH<s:property value="#nameList"/>">
								<div class="requireGroup">
									<s:iterator value="#indexsNL" status="statusCH" var="keyCH">
										<input type="checkbox"
											name="checkbox<s:property value="#hiddenParam[4]"/>"
											value="<s:property value="#keyCH.key"/>"
											data="<s:property value="#keyCH.value"/>" />
										<s:property value="#keyCH.value" />
									</s:iterator>
								</div>
							</div>
						</s:elseif>
					</s:elseif>
				</s:if>
			</s:iterator>
		</s:if>
		<s:else>
			<s:if test='#hiddenParam[1].equals("C")'>
				<div id="itemC<s:property value="#nameList"/>">
					<s:select list="#indexsNL" id="%{#nameList}" headerKey="" headerValue="" listKey="key" listValue="value" cssStyle="width: 100%;" cssClass="cmd-hidden" />
				</div>
			</s:if>
			<s:elseif test='#hiddenParam[1].equals("B")'>
					<div id="container" >
						<input type="button" id="pickfiles" value="Select files"/>
					</div>
			</s:elseif>
			<s:elseif test='#hiddenParam[1].equals("R")'>
				<div id="itemR<s:property value="#nameList"/>">
					<div class="requireGroup">
						<s:iterator value="#indexsNL" status="statusR" var="keyR">
							<s:if test="#statusR.index == 0">
								<input type="radio"
									name="radio<s:property value="#hiddenParam[4]"/>"
									value="<s:property value="#keyR.key"/>"
									data="<s:property value="#keyR.value"/>" checked="checked" />

									<span class="margin-radio">
										<s:property value="#keyR.value" />
									</span>
							</s:if>
							<s:else>
								<input type="radio"
									name="radio<s:property value="#hiddenParam[4]"/>"
									value="<s:property value="#keyR.key"/>"
									data="<s:property value="#keyR.value"/>" />
									
								<span class="margin-radio">
									<s:property value="#keyR.value" />
								</span>
							</s:else>
						</s:iterator>
					</div>
				</div>
			</s:elseif>
			<s:elseif test='#hiddenParam[1].equals("CH")'>
				<div id="itemCH<s:property value="#nameList"/>">
					<div class="requireGroup">
						<s:iterator value="#indexsNL" status="statusCH" var="keyCH">
							<input type="checkbox"
								name="checkbox<s:property value="#hiddenParam[4]"/>"
								value="<s:property value="#keyCH.key"/>"
								data="<s:property value="#keyCH.value"/>" />
							<s:property value="#keyCH.value" />
						</s:iterator>
					</div>
				</div>
			</s:elseif>
		</s:else>
	</s:iterator>
</div>

<s:set var="columnCheckDup" value="" />
<s:set var="caseCheckDup" value="" />
<s:set var="listTableData" value="" />
<s:set var="urlUpload" value="" />
<s:token />
</html>