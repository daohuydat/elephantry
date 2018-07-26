$(function() {
	var baseUrl = rootBaseUrl;
	var $oldPassword = $("#oldPassword");
	var $newPassword = $("#newPassword");
	var $reNewPassword = $("#reNewPassword");
	$("#formChangePassword").validate({
		rules : {
			oldPassword : {
				required : true,
				remote : {
					url : baseUrl + '/portal/account/checkPassword',
					type : "post",
					dataType : 'json',
					data : {
						'oldPassword' : function() {
							return $('#oldPassword').val();
						}

					}
				}
			},
			newPassword : {
				required : true,
				strongPassword : true
			},

			reNewPassword : {
				required : true,
				equalTo : "#newPassword"
			}
		},
		messages : {
			oldPassword : {
				remote : "Password not match!",
			},
			reNewPassword : {
				equalTo : "These passwords don't match. Try again?"
			}
		}
	});

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
	
	$.validator.addMethod('strongPassword', function(value, element) {
		return this.optional(element) || value.length >= 8;
	}, 'Your password must be at least 8 characters');

	$('#btnChangePassword').click(function() {
		if ($("#formChangePassword").valid()) {
			$.ajax({
				url : baseUrl + '/portal/account/changePassword',
				type : 'POST',
				dataType : 'json',
				data : {
					'reNewPassword' : function() {
						return $('#reNewPassword').val();
					}
				},
				success : function(res) {
					$oldPassword.val("");
					$newPassword.val("");
					$reNewPassword.val("");
					$.notify({
						message: 'Your password had been changed !' 
					},{
						type: 'success',
					});
				},
				error : function(err) {
					console.log(err);
				}
			});
			
		}
	});
});