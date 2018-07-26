$(function() {
	if (successMessage==="done"){
		$.notifyDefaults({
			placement: {
				from: "top",
				align: "center"
			},
			animate:{
				enter: "animated fadeInUp",
				exit: "animated fadeOutDown"
			}
		});
		$.notify({
			// options
			icon: 'fa fa-paw',
			message: 'Your Feedback has been send !!!' 
		},{
			// settings
			type: 'success'
		});
		
		history.pushState(null,null,location.href.substring(0,location.href.indexOf("?")));
	}
	var $btnSend = $("#sendFeedback");
	var $txtfeedback = $("#txtfeedback");
	$txtfeedback.keyup(function() {
		if ($txtfeedback.val().length) {
			$btnSend.attr("disabled", false);
		}else{
			$btnSend.attr("disabled", true);
		}
	});
	
})