$(function() {
	var baseUrl = rootBaseUrl;
	var $firstName = $("#txtFirstName");
	var $lastName = $("#txtLastName");
	var $dateOfBirth = $("#datepicker");
	var $website = $("#txtWebsite");
	var $company = $("#txtCompany");
	var $phone = $("#txtPhone");
	var $province = $("#ddlProvince");
	var $country = $("#ddlCountry");
	var $package = $("#ddlPackage");
	var $address = $("#txtAddress");
	var $gender = $("#ckGender");
	var packageSelected = $package.val();
	var flagPackage = packageSelected;

	$("#formProfile").validate({

	});

	$("#datepicker").datepicker({
		changeMonth : true,
		changeYear : true,
		yearRange : "1930:2010",
		dateFormat : 'dd/mm/yy'
	});

	// $("#ddlPackage option[value='" + packageId + "']").attr('selected',
	// 'selected');
	$("#ddlCountry option[value='" + countryId + "']").attr('selected',
			'selected');
	console.log(countryId);
	$("#ddlProvince option[value='" + provinceId + "']").attr('selected',
			'selected');

	$("#ddlCountry").change(function() {
		console.log($(this).val());
		loadProvinces($(this).val());
	});

	$("#btnEdit").click(function() {
		enableAll();
		$("#divEdit").hide();
		$("#divSave").show();
	});
	$("#datepicker").datepicker('disable');
	$("#btnSave").click(function() {
		if ($("#formProfile").valid()) {
			$.ajax({
				url : baseUrl + '/portal/account/saveProfile',
				type : 'post',
				dataType : 'json',
				cache : false,
				data : {
					'customerId' : customerId,
					'firstName' : $firstName.val(),
					'lastName' : $lastName.val(),
					'dateOfBirth' : $dateOfBirth.val(),
					'website' : $website.val(),
					'company' : $company.val(),
					'phone' : $phone.val(),
					'provinceId' : $province.val(),
					'address' : $address.val(),
					'gender' : $gender.parent().hasClass("btn-success"),
				},
				success : function(res) {
					console.log("doi thanh cong");
					disableAll();
					$("#divSave").hide();
					$("#divEdit").show();
					$.notify({
						message : 'Your profile had been updated !'
					}, {
						type : 'success',
					});
				}
			});

			
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
	
	

	function disableAll() {
		$firstName.attr('readonly', true);
		$lastName.attr('readonly', true);
		$dateOfBirth.attr('readonly', true);
		$website.attr('readonly', true);
		$company.attr('readonly', true);
		$phone.attr('readonly', true);
		$province.attr('disabled', true);
		$country.attr('disabled', true);
		$package.attr('disabled', true);
		$address.attr('readonly', true);
		$gender.attr('disabled', true);
		$("#datepicker").datepicker('disable');
	}

	function enableAll() {
		$firstName.attr('readonly', false);
		$lastName.attr('readonly', false);
		$dateOfBirth.attr('readonly', true);
		$website.attr('readonly', false);
		$company.attr('readonly', false);
		$phone.attr('readonly', false);
		$province.attr('disabled', false);
		$country.attr('disabled', false);
		// $package.attr('disabled', false);
		$address.attr('readonly', false);
		$gender.attr('disabled', false);
		$("#datepicker").datepicker('enable');

	}

	function upgradePackage(id) {
		$.ajax({
			url : baseUrl + '/account/upgradePackage',
			type : 'GET',
			dataType : 'json',
			cache : false,
			data : {
				'packageId' : id,
			},
			success : function(res) {
				console.log("nang cap");
			},
			error : function(err) {
				console.log(err);
			}
		});
	}

	function loadProvinces(countryId) {

		$.ajax({
			url : baseUrl + '/account/signup/getProvinces',
			type : 'GET',
			dataType : 'json',
			data : {
				'countryId' : countryId
			},
			cache : false,
			success : function(res) {
				var arr = res.provinces;
				var html = '';
				for (var i = 0; i < arr.length; i++) {
					html += "<option value='" + arr[i].provinceId + "'>"
							+ arr[i].provinceName + "</option>";
				}
				$province.html(html);

			},
			error : function(err) {
				console.log(err);
			}
		});
	}


});