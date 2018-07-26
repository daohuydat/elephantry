$(function() {

	var baseUrl = '/elephantry';
	var $realEmailMessage = $("#realEmailMessage");
	var access_key = '3ac091bd7f98d2f9af446d34fcb082f3';
	$("#formSignup").validate({
		rules : {
			firstName : {
				required : true

			},
			email : {
				required : true,
				remote : {
					url : baseUrl + '/account/signup/isEmailAvailiable',
					type : "post",
					dataType : 'json',
					data : {
						'email' :function() {
				            return $('#email').val();
				          } 
							
					}
				}
			},
			password : {
				required : true,
				strongPassword : true
			},
			password2 : {
				required : true,
				equalTo : "#password"
			}
		},
		messages : {
			email : {
				remote : "The username is already in use!",
			},
			password2 : {
				equalTo : "These passwords don't match. Try again?"
			}
		}
	});
	
	$.validator.addMethod('strongPassword', function(value, element) {
		return this.optional(element) || value.length >= 8;
	}, 'Your password must be at least 8 characters');

	// $email.focusout(function(){
	// $.ajax({
	// url : 'http://apilayer.net/api/check?access_key=' + access_key
	// + '&email=' + $email.val(),
	// dataType : 'json',
	// success : function(json) {
	// console.log('ddsd' + json.smtp_check);
	// if(json.smtp_check){
	// $realEmailMessage.hide();
	// }else {
	// $realEmailMessage.show();
	// }
	// }
	// });
	// });
	
	$.validator.addMethod("isRealEmail", function(value, element) {

		var a = false;
		
		$.ajax({
			url : 'http://apilayer.net/api/check?access_key=' + access_key
					+ '&email=' + value,
			dataType : 'json',
			async : false,
			success : function(json) {
				console.log('ddsd' + json.smtp_check);
				a = json.smtp_check;
			}
		});
		return this.optional(element) || a;

	}, 'This email is not real');

})
