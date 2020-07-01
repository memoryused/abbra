/**
# ---------------------------------------------------------------------------------
# 							MANAGE-CHECKBOX.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 03/07/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/
jQuery( document ).ready(function() {
	
//	manageCheckbox();
	
});



function manageCheckbox(){
	var arrComponent = ["checkbox"];
	var component;
	var componentHtml 	= "";
	var componentId	= "";
	var txt			= "";
	var color		= "";
	var inline 		= "";
	var htmlComponent = "";
	var countId = 0;
	for (i = 0; i < arrComponent.length; i++) {
		jQuery("input."+arrComponent[i]+":"+arrComponent[i]).each(function(index){
			
			component	= jQuery(this);
			component.addClass("checkbox-default");
			componentId = component.attr("id");
			//GEN ID
			if(componentId == null || typeof componentId == 'undefined'){
				componentId = arrComponent[i]+"_"+(++countId);
				component.attr("id", componentId);
			}
			componentHtml 	= component.prop("outerHTML");
			txt = component.attr("data-label") == null ? txt = "" : component.attr("data-label");
			
			//COLOR
			if(component.hasClass(arrComponent[i]+"-success")){
				color = arrComponent[i]+"-success";
			}else if(component.hasClass(arrComponent[i]+"-warning")){
				color = arrComponent[i]+"-warning";
			}else if(component.hasClass(arrComponent[i]+"-danger")){
				color = arrComponent[i]+"-danger";
			}else if(component.hasClass(arrComponent[i]+"-info")){
				color = arrComponent[i]+"-info";
			}else if(component.hasClass(arrComponent[i]+"-secondary")){
				color = arrComponent[i]+"-secondary";
			}else if(component.hasClass(arrComponent[i]+"-default")){
				color = arrComponent[i]+"-default";
			}else{
				color = "";
			}
			
			//INLINE
			if(component.hasClass(arrComponent[i]+"-inline")){
				inline = arrComponent[i]+"-inline";
			}
			
			//DRAW HTML
			htmlComponent = "";
			htmlComponent += '<div class="'+ arrComponent[i] +' '+color+' '+inline+'">';
			htmlComponent += componentHtml;
			htmlComponent += '<label for="'+ componentId +'">'+ txt +'</label>';
			htmlComponent += '</div>';
			
			component.replaceWith(htmlComponent);
		});
	}
}



