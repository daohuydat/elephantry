$(function() {

	var baseUrl = '/elephantry';
	$("#email").focus();
	$("#formForgot").validate({
		onkeyup : false,
		rules : {
			emailForgot : {
				required : true,
				email : true,
				remote : {
					url : baseUrl + '/account/signin/isEmailExist',
					type : "post",
					dataType : 'json',
					data : {
						'email' : function() {
							return $('#emailForgot').val();
						}
					}
				}
			}

		},
		messages : {
			emailForgot : {
				remote : "Couldn't find your Elephantry Account.",
			}
		}
	});
	var a = $("#formSignin").validate({
		onkeyup : false,
		onfocusout : false,
		rules : {
			email : {
				required : true,
				email : true,
				remote : {
					url : baseUrl + '/account/signin/isEmailExist',
					type : "post",
					dataType : 'json',
					data : {
						'email' : function() {
							return $('#email').val();
						}
					}
				}
			},
			password : {

				required : true,
				remote : {
					url : baseUrl + '/account/signin/checkPassword',
					type : "post",
					dataType : 'json',
					data : {
						'password' : function() {
							return $('#password').val();
						},
						'email' : function() {
							return $('#email').val();
						}
					}
				}
			}
		},
		messages : {
			email : {
				remote : "Couldn't find your Elephantry Account.",
			},
			password : {
				remote : "Wrong password. Try again.",
			}

		}
	});

	

	$(document).on('click', '.btnForgotPass', function(e) {
		if ($("#formForgot").valid()) {
			$.ajax({
				url : baseUrl + '/account/signin/forgotPassword',
				type : 'POST',
				dataType : 'json',
				cache : false,
				data : {
					'email' : function() {
						return $('#emailForgot').val();
					}
				},
				success : function(res) {
					window.location.href = baseUrl + "/account/signin#signin";
					$.notify({
						message : 'New password had been send to your email !'
					}, {
						type : 'success',
					});
				}
			});
		}
		;

	});
})