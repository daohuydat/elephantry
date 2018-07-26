$(function() {
	var access_key = '101d7b1cccbc7b015b7830778aa26d18';
	var baseUrl = rootBaseUrl;
	var $mail = $("#txtEmail");
	var $errMessage = $("#realEmailMessage");
	var $btnSend = $("#send");
	$mail.keyup($.debounce(1000,function(){
		console.log($mail.val());
		isEmailAvailable($mail.val());
	}));
	function disableSubmit(){
		$errMessage.show();
		$btnSend.attr('disabled','disabled');
	}
	
	function isEmailReal(email){
		$.ajax({
			url: 'http://apilayer.net/api/check?access_key=' + access_key + '&email=' + email,   
			dataType: "jsonp",
			cache: false,
			success: function(json) {
				$errMessage.hide();
				if (json.format_valid){
					if (!json.smtp_check) {
						$errMessage.html("This email is not real!");
						disableSubmit();
					}else{
						$btnSend.removeAttr('disabled');
					}
				}else{
					$errMessage.html("This email's format is not valid!");
					disableSubmit();
				}
			}
		});
	}
	
	function isEmailAvailable(email){
		$.ajax({
			url: baseUrl + "/admin/adminManagement/isEmailAvailable", 
			dataType: "json",
			type: "POST",
			cache: false,
			data: {
				"email": email
			},
			success: function(json) {
				$errMessage.hide();
				if (json.available) {
					isEmailReal(email);
				}else{
					$errMessage.html("This email has been used!");
					disableSubmit();
				}
			},
			error: function(err) {
				console.log(err);
			}
		});
	}
});