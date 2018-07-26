$(function() {
	var baseUrl = rootBaseUrl;
	var html = '';
	var result;
	var packageSelected = 1;
	var flagPackage = packageSelected;
	var $province = $("#ddlProvince");
	var $package = $("#ddlPackage");
	var $pricingTable = $("#pricingTable");
	var packageName = [];
	
	if($("#packageCode").val() == "freetrial"){
		$package.val("Free trial");
		packageSelected = 1;
	}else if($("#packageCode").val() == "standard"){
		$package.val("Standard");
		packageSelected = 2;
	}else if($("#packageCode").val() == "premium"){
		$package.val("premium");
		packageSelected = 3;
	};
	$("#ddlCountry option[value='VNM']").attr('selected', 'selected');
//	$("#ddlPackage option[value='" + packageSelected + "']").attr('selected','selected');
	loadProvinces('VNM');
	loadPackage();

	$("#ddlCountry").change(function() {
		console.log($(this).val());
		loadProvinces($(this).val());
	});

	$("#ddlPackage").click(function(e){
		e.preventDefault();
		console.log()
	});
	
	$(document).on('click', '.btnChoosePackage', function(e) {
		if ($(this).val() != flagPackage) {
			$('#packageTable' + flagPackage).removeClass("selected-package");
			$('#packageTable' + $(this).val()).addClass("selected-package");
			flagPackage = $(this).val();
			$("#ddlPackage").val(packageName[flagPackage - 1]);
			$("#packageId").val(flagPackage);
//			$("#ddlPackage option[value='" + packageSelected + "']").removeAttr('selected', 'selected');
//			$("#ddlPackage option[value='" + flagPackage + "']").attr('selected', 'selected');
			packageSelected = flagPackage;
		}
		console.log(flagPackage +"----" + packageSelected);
		$('.modal').modal('toggle');
	});
	$(document).on('click', '.btnChangePackage', function(e) {
		if (flagPackage != packageSelected ) {
			$('#packageTable' + flagPackage).removeClass("selected-package");
			$('#packageTable' + packageSelected).addClass("selected-package");
			flagPackage = packageSelected;
			
		}
		console.log(flagPackage +"----" + packageSelected);
	});
	
//	$(document).on('click', '.btnSaveChange', function(e) {
//		
//		
//		if (flagPackage != packageSelected) {
//			
//		
//		}
//		console.log(flagPackage +"----" + packageSelected);
//		
//	});

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

	function loadPackage() {
		$
				.ajax({
					url : baseUrl + '/account/signup/getPackages',
					type : 'GET',
					dataType : 'json',
					cache : false,
					success : function(res) {
						var oPackage = '';
						var oPricingTable = '';

						for (var i = 0; i < res.length; i++) {
							if (res[i].packageId == packageSelected) {
//								$("#ddlPackage").val(res[i].packageName);
								$("#packageId").val(res[i].packageId);
								oPricingTable += "<div id='packageTable"
										+ res[i].packageId
										+ "' class='col-md-4 col-sm-6 col-xs-12 selected-package'>";
								

							} else {
								oPricingTable += "<div id='packageTable"
										+ res[i].packageId
										+ "' class='col-md-4 col-sm-6 col-xs-12'>";

							}
							packageName.push(res[i].packageName);
							oPricingTable += "<div class='pricing'>";
							oPricingTable += "<div class='title '>";
							oPricingTable += "<h2>" + res[i].packageName
									+ "</h2>";
							oPricingTable += "<h1>" + res[i].price + "$</h1>";
							oPricingTable += "<span>Monthly</span>";
							oPricingTable += "</div><div class='x_content'><div class=''><div class='pricing_features'>";
							oPricingTable += "<ul class='list-unstyled text-left'><li><i class='fa fa-check text-success'></i> ";
							if(i == 0){
								oPricingTable += "<strong>1 recommendation"
								+ "</strong> </li>";
								oPricingTable += "<li><i class='fa fa-check text-success'></i>Priority: <strong>Trial</strong></li>";
							}
							else if(i == 1){
								oPricingTable += "<strong>30 recommendations"
									+ "</strong></li>";
								oPricingTable += "<li><i class='fa fa-check text-success'></i>Priority: <strong>Standard</strong></li>";
								oPricingTable += "<li><i class='fa fa-check text-success'></i><strong>Support 24/7</strong></li>";
							}else{
								oPricingTable += "<strong>Unlimited recommendations"
									+ "</strong></li>";
								oPricingTable += "<li><i class='fa fa-check text-success'></i>Priority: <strong>Premium</strong></li>";
								oPricingTable += "<li><i class='fa fa-check text-success'></i><strong>Professional support 24/7</strong></li>";
							}
							
							
							oPricingTable += "</ul></div></div>";
							oPricingTable += "<div class='pricing_footer '><button type='button' value='"
									+ res[i].packageId
									+ "' class='btn btn-success btn-block btnChoosePackage'>Choose</button></div></div></div></div>";
							oPackage += "<option value='" + res[i].packageId
									+ "'>" + res[i].packageName + "</option>";

						}
						
						$package.html(oPackage);
						$pricingTable.html(oPricingTable);
					},
					error : function(err) {
						console.log(err);
					}
				});
	}
});
