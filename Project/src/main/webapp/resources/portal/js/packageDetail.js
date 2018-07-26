$(function() {
	var baseUrl = rootBaseUrl;
	var packagePrice;
	var $timePackage = $("#timePackage");
	var $totalPayment = $("#totalPayment");
	$timePackage.val(1);
	var packageId ;
	findPackageId();
	
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
	
	if($("#checkUpgrade").val() == 1 ){
		$.notify({
			icon: 'fa fa-paw',
			message : 'Upgrade package Premium successfully!'
		}, {
			type : 'success',
		});
		history.pushState(null, null, location.href.substring(0,location.href.indexOf('?')));
	}

	function updateSession(packageId) {
		$.ajax({
			url : baseUrl + '/portal/account/updateSession',
			type : 'GET',
			dataType : 'json',
			cache : false,
			data : {
				'packageId' : packageId
			},
			success : function(res) {
				window.location.replace(rootBaseUrl + "/account/checkout");
			}
		});
	}
	
	function updateSessionExtend(packageId) {
		$.ajax({
			url : baseUrl + '/portal/account/updateSession',
			type : 'GET',
			dataType : 'json',
			cache : false,
			data : {
				'packageId' : packageId
			},
			success : function(res) {
//				window.location.replace(rootBaseUrl + "/account/checkout");
			}
		});
	}
	
	function findPackageId() {
		$.ajax({
			url : baseUrl + '/portal/account/findPackageByCustomer',
			type : 'GET',
			dataType : 'json',
			cache : false,
			data : {
				"packageId" : packageId,
			},
			success : function(res) {
				packageId = res.packageId;
				console.log(packageId);
				if (packageId == 2) {
					$("#btnStandard").html("Extend");
					$("#btnStandard").val("extend");
					packagePrice = 100;
					$("#productTotal").html(packagePrice + "$");
					$totalPayment.val(packagePrice);
				} else if (packageId == 3) {
					$("#btnPremium").html("Extend");
					$("#btnPremium").val("extend");
					$("#btnStandard").attr("disabled", "true");
					packagePrice = 200;
					$("#productTotal").html(packagePrice + "$");
					$totalPayment.val(packagePrice);
				}
			}
		});
	}

	$("#btnStandard").click(
			function() {
				console.log(packageId);
				if (packageId == 1) {
					updateSession(2);
					
				} else {
					if ($("#btnStandard").val() == "extend") {
						updateSessionExtend(2);
						$("#myModalLabel").html("Extend package");
						$(".productName").html("Standard");
						$(".packageName").html(
								"Recommendation package: Standard");
						$(".productPrice").html("100$/month");
						console.log($("#timePackage").val());
						if ($("#timePackage").val() != "") {
							$("#productTotal").html(
									$("#timePackage").val() * packagePrice
											+ "$");
							$totalPayment.val($("#timePackage").val()
									* packagePrice);
						}
						$("#upgradeTranfer").removeAttr("style").hide();
						$("#normalUpgrade").removeAttr("style").show();
					} else if ($("#btnStandard").val() == "upgrade") {
						updateSession(2);
						$("#myModalLabel").html("Upgrade package")
//						$(".productName").html("Standard");
//						$(".packageName").html(
//								"Recommendation package: Standard");
//						$(".productPrice").html("100$/month");
//						console.log($("#timePackage").val());
//						if ($("#timePackage").val() != "") {
//							$("#productTotal").html(
//									$("#timePackage").val() * packagePrice
//											+ "$");
//							$totalPayment.val($("#timePackage").val()
//									* packagePrice);
//						}
					}
				}

			});

	$("#btnPremium").click(
			function() {
				if (packageId == 1) {
					updateSession(3);
					
				} else if ($("#btnPremium").val() == "extend") {
					updateSessionExtend(3);
					$("#myModalLabel").html("Extend package");
					$(".productName").html("Premium");
					$(".packageName").html("Recommendation package: Premium");
					$(".productPrice").html("200$/month");
					console.log($("#timePackage").val());
					if ($("#timePackage").val() != "") {
						$("#productTotal").html(
								$("#timePackage").val() * packagePrice + "$");
						$totalPayment.val($("#timePackage").val()
								* packagePrice);
					}

				} else if ($("#btnPremium").val() == "upgrade") {
					if(packageId == 2){
						$(".packageName").html("Recommendation package: Premium");
						$("#myModalLabel").html("Upgrade package")
						$("#upgradeTranfer").removeAttr("style").show();
						$("#normalUpgrade").removeAttr("style").hide();
					}else{
						updateSession(3);
					}
					
				}
			});

	$(document).on('change', '.productTime', function(e) {
		var productTotal = $(this).val() * packagePrice;
		$("#totalPayment").val(productTotal);
		$("#timePackage").val($(this).val());
		$("#productTotal").html("<div>" + productTotal + "$</div>");
	});
	
	$("#btnTransfer").click(
			function() {
				$
				.ajax({
					url : baseUrl + '/portal/account/transferUpgrade',
					type : 'POST',
					dataType : 'json',
					cache : false,
					data:{
						"daysTransfer": daysTransfer
					},
					success : function(res) {
						console.log("32323");
						$('.modal').modal('toggle');
						window.location.replace(rootBaseUrl + "/"+lang+"/portal/account/packageDetail?ss=1");
						packageId = 3;
						
					}
				});
			});
});