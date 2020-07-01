
$(document).ajaxStart(function(){
	//divLoader = new ajaxLoader(jQuery("body"));
	if(typeof showLoaderStatusAjax == 'function') {
		showLoaderStatusAjax();
	}
});

$(document).ajaxComplete(function(){
	if(typeof stopLoaderStatusAjax == 'function') {
		stopLoaderStatusAjax();
	}
});

$(document).ajaxError(function(jqXHR,textStatus,errorThrown) {
	if (textStatus.status === 0 || textStatus.status === undefined) {
        alert("Not connect. Verify Network. Please contact administrator.");
    } else {
		if (textStatus.status != 200) {
	    	alert(textStatus.status + " " + textStatus.statusText);
		}
    }
	submitPage(window.location.pathname);
});

jQuery.ajaxSetup({
	dataType: "json"
});


