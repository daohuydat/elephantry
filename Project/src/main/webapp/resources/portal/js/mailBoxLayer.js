// set endpoint and your access key


$(function() {
	var access_key = '3ac091bd7f98d2f9af446d34fcb082f3';
	
	function validateEmail(email) {
		$.ajax({
		    url: 'http://apilayer.net/api/check?access_key=' + access_key + '&email=' + email,   
		    dataType: 'jsonp',
		    success: function(json) {
		    	console.log(json.smtp_check);
		    }
		});
	}
});