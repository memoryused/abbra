
/*
* modify perapol.p
* Date 26/05/2017
*/

/*กำหนดค่าสำหรับส่วน Loader ทั้งส่วน sendAjax และ submitForm*/
var objProp = {
	bgColor         : '#000',
    opacity         : 0.3,
    position		: 'absolute',
    top				: '0px',
    left			: '0px',
    zindex			: 99999
}


function showLoaderStatusAjax(){
	divLoader = new ajaxLoader(jQuery("body"),objProp);
}

function stopLoaderStatusAjax(){
	divLoader.remove();
}

function showLoaderStatus(){
	// Create the overlay
	var overlay = jQuery('<div></div>').css({
			'background-color': objProp.bgColor,
			'opacity': objProp.opacity,
			'width':"99.9%",
			'height':(jQuery("#BODY_TR_CONTENT").height() + jQuery("#BODY_TR_HEADER").height()),
			'position':objProp.position,
			'top':objProp.top,
			'left':objProp.left,
			'z-index':objProp.zindex
	}).addClass('ajax_overlay');

	jQuery("body").append(overlay.append(
			jQuery('<div></div>').addClass('ajax_loader')
		).fadeIn(200)
	);
}

