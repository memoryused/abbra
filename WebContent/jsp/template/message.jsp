<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
	/* แสดง message แล้วจางหายไป */
	$(document).ready(function(){
		//			$("#messFed").fadeIn(1000,  function(){
		//			$('#messFed').delay(2000).fadeOut("slow"); });
		
		//หมายเหตุ:: เหตุผลที่ใช้ fadeIn ในกรณีนี้ไม่ได้เนื่องจาก หากใช้ fadeIn แล้วก่อนที่จะ fadeIn เข้ามานั้นจะต้องทำการ display: hidden; ในส่วนนั้นก่อน  ซึ่งเมื่อทำ fadeIn เสร็จแล้วยรรทัด message ก็จหายไป
		//	  	 -แก้ไข : โดยการใน animate แทน ซึ่งจะทำการ  set opacity แทนการใช้ display: hidden; ซึ่งจะไม่ทำการซ่อนบรร่ทัด message แต่เป็นการ set ความเรือนรางแทน

		var $messFedSel = jQuery('.messFed');
		<s:if test='%{ActionMessages[0] != "E"}'> //ถ้าไม่ใช่ Error ให้ FadeIn และ FadeOut หายไป
			//Warning > แสดงข้อความค้างไว้ จนกว่าจะทำรายการใหม่
			<s:if test='%{ActionMessages[0] == "W"}'>
				console.log("W");
				$messFedSel.stop().css("opacity", "0").animate({opacity: 1},2500,"","");
			</s:if>
			<s:else>//Success > เพิ่มเวลาแสดงผลจากเดิม +10 วินาที
				console.log("!W");
				$messFedSel.stop().css("opacity", "0").animate({opacity: 1},2500,"",function(){
					
					//แสดง message ไปอีก 10 วิ  แล้วให้ message ค่อยๆ หายไป
					$(this).animate({opacity: 1},10000,"",function(){
						$(this).animate({opacity: 0},4500,"",function(){
							jQuery('.messFed #tblMessage > div.col-std-2').html('<span class="MESSAGE">&nbsp;</span>');
						});
					});
				});
			</s:else> 
			
		</s:if>
		<s:else>// ถ้ามี Error ให้ message นั้นค้างไว้ เพื่อสามารถดู Detail ที่ error ได้ 
			$messFedSel.animate({opacity: 1},900,"","");
			console.log("E");
		</s:else> 

		
		<s:if test='getAlertMaxExceed().equals("A")'>
			alert(getMessageResponse("<s:property value='ActionMessages[0]' escapeHtml='false'/>"));
		</s:if>
		<s:elseif test='getAlertMaxExceed().equals("C")'>
			showConfrimMessage();
		</s:elseif>
		<s:else>
			<s:if test='%{ActionMessages.size() == 1}'>
				console.log("ActionMessages");
				var msgx = getMessageResponse("<s:property value='ActionMessages[0]' escapeHtml='false'/>");
				if (msgx != '') {
					jQuery("span.MESSAGE").html(msgx);
				}
			</s:if>
		</s:else>
	});
</script>
<style type="text/css">
	.messFed {
		opacity: 0;
		padding-bottom: 7px; 
		padding-top: 7px;
	}
</style>
<div class="messFed">

	<s:if test='%{getAlertMaxExceed().equals("A")}'>
		<div class="row">
			<div class="col-md-12">
				<span class="MESSAGE">&nbsp;</span>
			</div>
		</div>
	</s:if> 
	<s:elseif test='%{getAlertMaxExceed().equals("C")}'>
		<div class="row">
			<div class="col-md-12">
				<span class="MESSAGE">&nbsp;</span>
			</div>
		</div>
	</s:elseif> 
	<s:elseif test='%{getAlertMaxExceed().equals("N")}'>
		
		<s:if test='%{ActionMessages[0] == "E"}'>
			<div id="tblMessage" class="row">
				<div class="col-std">
					<span class="ui-icon ui-icon-alert" style="float: right; margin-right: .3em;"></span>
				</div>
				<div class="col-std-2">
					<s:property value="ActionMessages[1]" escapeHtml="false"/>
					<s:if test='%{ActionMessages[2] != null}'>
						<a class="LINK" href="javascript:void(0);" onclick="showErrorDetail('messageDetail')">detail</a>	
							<div id='messageDetail' style="display: none;"><s:property  value="ActionMessages[2]" escapeHtml="false"/></div>
					</s:if>
				</div>
			</div>
		
		</s:if>
		<s:elseif test='%{ActionMessages[0] == "W"}'>
			<div id="tblMessage" class="row">
				<div class="col-std">
					<span class="ui-icon ui-icon-alert" style="float: right; margin-right: .3em;"></span>
				</div>
				<div class="col-std-2">
					<s:property value="ActionMessages[1]" escapeHtml="false"/>
					<s:if test='%{ActionMessages[2] != null}'>
						<a class="LINK" href="javascript:void(0);" onclick="showErrorDetail('messageDetail')">detail</a>	
							<div id='messageDetail' style="display: none;"><s:property  value="ActionMessages[2]" escapeHtml="false"/></div>
					</s:if>
				</div>
			</div>
		</s:elseif>
		<s:elseif test='%{ActionMessages[0] == "S"}'>
			<div id="tblMessage" class="row">
				<div class="col-std">
					<span class="ui-icon ui-icon-circle-check" style="float: right; margin-right: .3em;"></span>
				</div>
				<div class="col-std-2">
					<s:property value="ActionMessages[1]" escapeHtml="false"/>
					<s:if test='%{ActionMessages[2] != null}'>
						<a class="LINK" href="javascript:void(0);" onclick="showErrorDetail('messageDetail')">detail</a>	
							<div id='messageDetail' style="display: none;"><s:property  value="ActionMessages[2]" escapeHtml="false"/></div>
					</s:if>
				</div>
			</div>
		</s:elseif>
		<s:else>
			<div id="tblMessage" class="row">
				<div class="col-md-12">
					<span class="MESSAGE">&nbsp;</span>
				</div>
			</div>
		</s:else>
	</s:elseif>
	<s:else>
		<div id="tblMessage" class="row">
			<div class="col-md-12">
				<span class="MESSAGE">&nbsp;</span>
			</div>
		</div>
	</s:else> 

</div>