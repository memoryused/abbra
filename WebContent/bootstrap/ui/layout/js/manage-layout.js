/**
# ---------------------------------------------------------------------------------
# 							MANAGE-LAYOUT.JS
# ---------------------------------------------------------------------------------
#
# Create Date	: 07/08/2017
# Author 		: Saranyu.s
#
# ---------------------------------------------------------------------------------
*/

function manageColCriteria(){
//	console.info("[ manageColCriteria ]");
	
	
	/*jQuery(".col-std-1").each(function(index){
    	if(jQuery(this).next().next(".col-std-3").length > 0){
    		jQuery(this).addClass("col-sm-4 col-md-1  col-lg-2 col-xl-2 text-xs-left text-sm-right text-md-right text-lg-right");
    	}else{
    		jQuery(this).addClass("col-sm-4 col-md-1 col-lg-2 col-xl-2 text-xs-left text-sm-right text-md-right text-lg-right");
    	}
	
	});
    
	jQuery(".col-std-2").each(function(){
    	if(jQuery(this).next(".col-std-3").length > 0){
    		jQuery(this).addClass("col-sm-8 col-md-8 col-lg-4  col-xl-3");
    	}else{
    		jQuery(this).addClass("col-sm-8 col-md-8 col-lg-10 col-xl-9");
    	}
	});
		    
	jQuery(".col-std-3").each(function(){
		jQuery(this).addClass("col-1 text-xs-left text-sm-right text-md-right text-lg-right");
	});
	
	jQuery(".col-std-4").each(function(){
		jQuery(this).addClass("col-sm-8 col-md-1 col-lg-4 col-xl-3");
	});
	
	jQuery(".col-cen-1").each(function(){
    	jQuery(this).addClass("col-sm-4 col-md-4 col-lg-4 col-xl-4 text-xs-left text-sm-right text-md-right text-lg-right");
	});
	
	jQuery(".col-cen-2").each(function(){
		if(jQuery(this).next(".col-cen-3").length > 0){
    		jQuery(this).addClass("col-sm-8 col-md-8 col-lg-4 col-xl-4");
    	}else{
    		jQuery(this).addClass("col-sm-8 col-md-8 col-lg-8 col-xl-7");
    	}
		
	});
	
	jQuery(".col-cen-3").each(function(){
    	jQuery(this).addClass("col-sm-8 col-md-8 col-lg-3 col-xl-3 hidden-md-down");
	});*/
	
	jQuery(".col-std").each(function(index){
		if(jQuery(this).next(".col-std-2").length > 0){
			jQuery(this).addClass("col-sm-0 col-md-0 col-lg col-xl");
		}else if(jQuery(this).next(".col-std-3").length > 0){
			jQuery(this).addClass("col-sm-0 col-md-0 col-lg-0 col-xl");
		}else if(jQuery(this).next(".col-std-4").length > 0){
			jQuery(this).addClass("col-sm-0 col-md-0 col-lg-0 col-xl-0");
		}else{
			jQuery(this).addClass("col");
		}
		
	});
	
	jQuery(".col-std-1").each(function(index){
		jQuery(this).addClass("col-sm-12 col-md-8 col-lg-5 col-xl-3 ");
	});
	
	jQuery(".col-std-2").each(function(index){
		if(jQuery(this).hasClass("col-std-span-1")){
			jQuery(this).addClass("col-sm-12 col-md-12 col-lg-8 col-xl-6");
		}else{
			jQuery(this).addClass("col-sm-12 col-md-6 col-lg-4 col-xl-3");
		}
	});
	
	jQuery(".col-std-3").each(function(index){
		if(jQuery(this).hasClass("col-std-span-1")){
			jQuery(this).addClass("col-sm-12 col-md-12 col-lg-8 col-xl-6");
		}else if(jQuery(this).hasClass("col-std-span-2")){
			jQuery(this).addClass("col-sm-12 col-md-12 col-lg-12 col-xl-9");
		}else{
			jQuery(this).addClass("col-sm-12 col-md-6 col-lg-4 col-xl-3");
		}
		
	});
	
	jQuery(".col-std-4").each(function(index){
		if(jQuery(this).hasClass("col-std-span-1")){
			jQuery(this).addClass("col-sm-12 col-md-12 col-lg-6 col-xl-6");
		}else if(jQuery(this).hasClass("col-std-span-2")){
			jQuery(this).addClass("col-sm-12 col-md-9 col-lg-9 col-xl-9");
		}else if(jQuery(this).hasClass("col-std-span-3")){
			jQuery(this).addClass("col-sm-12 col-md-12 col-lg-12 col-xl-12");
		}else{
			jQuery(this).addClass("col-sm-12 col-md-3 col-lg-3 col-xl-3 ");
		}
		
	});
    
	
	
	jQuery(".col-cen-1").each(function(){
    	jQuery(this).addClass("col-sm-4 col-md-4 col-lg-4 col-xl-4 text-xs-left text-sm-right text-md-right text-lg-right");
	});
	
	jQuery(".col-cen-2").each(function(){
		if(jQuery(this).next(".col-cen-3").length > 0){
    		jQuery(this).addClass("col-sm-8 col-md-8 col-lg-4 col-xl-4");
    	}else{
    		jQuery(this).addClass("col-sm-8 col-md-8 col-lg-8 col-xl-7");
    	}
		
	});
	
	jQuery(".col-cen-3").each(function(){
    	jQuery(this).addClass("col-sm-8 col-md-8 col-lg-3 col-xl-3 hidden-md-down");
	});
	
	
	manageAutoCol12();
}


function manageAutoCol12(){
	jQuery(".div-criteria-modal, .div-criteria").each(function(){
		jQuery(this).addClass("col");
	})
}



function manageLabel(){
	var txt = "";
	var labelFor = "";
	var labelStr = "";
	jQuery(".col-std-1, .col-std-3, .col-cen-1").each(function(index){
		txt = jQuery(this).text().trim();
		if(txt != ""){
			labelFor = jQuery(this).next(".col-std-2, .col-std-4, .col-cen-2").find("input:not([type=hidden]), select:not([type=hidden]), textarea:not([type=hidden])").attr("id");
//			console.log(jQuery(this).text().trim() + ' FOR '+labelFor);
			
			//ADD CLASS REQUIREINPUT
			if(checkStarRequire(txt)){
				jQuery(this).next(".col-std-2, .col-std-4, .col-cen-2").find("input:not([type=hidden]), select:not([type=hidden]), textarea:not([type=hidden])").addClass("requireInput");
			}
			
			//STAR RED
			txt = starRed(txt);
			labelStr = '<label for="'+ labelFor +'">'+ txt +'</label>';
			if (typeof labelFor === "undefined") {
				labelStr = '<label>'+ txt +'</label>';
			}
			jQuery(this).html(labelStr);
		}
	});
}



function starRed(txt){
	txt = txt.trim();
	if(checkStarRequire(txt)){
		txt = txt.substr(0 , txt.length -1);
		txt += "<span class='star-red'>*</span>";
//		txt = txt.replace("*", "<span class='star-red'>*</span>");
	}
	
	return txt;
}



function checkStarRequire(txt){
	var result = false;
	txt = txt.trim();
	if(txt.slice(-1) == "*"){
		result = true;
	}
	
	return result;
}
