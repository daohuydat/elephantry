$(function(){
	var baseUrl = rootBaseUrl;
	var packageSelected ;
	if(packageIdFromCheckout == 1){
		packageSelected = 2;
	}
	else{
		packageSelected = packageIdFromCheckout;
	}
	var flagPackage = packageSelected;
	var $pricingTable = $("#pricingTable");
	var packageName;
	var productPrice;
	var productTotal;
	var totalOrder;
	
	var $timePackage = $("#timePackage");
	var $totalPayment = $("#totalPayment");
	var $packageName = $(".packageName");
	var $productName = $(".productName");
	var $productPrice = $(".productPrice");
	var $productTotal = $(".productTotal");
	var $productTime = $(".productTime");
	
	var $totalOrder = $(".totalOrder");
	$("#timePackage").val($productTime.val())  ;
	
	getPackageById(packageSelected);
	loadPackageTable();
	
	$(document).on('click', '.btnChoosePackage', function(e) {
		if ($(this).val() != flagPackage) {
			$('#packageTable' + flagPackage).removeClass("selected-package");
			$('#packageTable' + $(this).val()).addClass("selected-package");
			flagPackage = $(this).val();
			
			packageSelected = flagPackage;
			getPackageById(packageSelected);
			updatePackage(packageSelected);
		}
		$('.modal').modal('toggle');
	});
	$(document).on('click', '.btnChangePackage', function(e) {
		if (flagPackage != packageSelected ) {
			$('#packageTable' + flagPackage).removeClass("selected-package");
			$('#packageTable' + packageSelected).addClass("selected-package");
			flagPackage = packageSelected;
			
		}
	});
	
//	$(document).on('click', '.btnSaveChange', function(e) {
//		if (flagPackage != packageSelected) {
//			
//			
//		
//		}
//		
//	});
	
	$(document).on('change', '.productTime', function(e) {
		productTotal = $(this).val() * productPrice;
		$("#totalPayment").val(productTotal); 
		$("#timePackage").val($(this).val());
		$productTotal.html("<div>"+productTotal +"$</div>");
		$totalOrder.html("<h3>Total order: "+productTotal +"$</h3>");
	});
	
	function updatePackage(packageId){
		$.ajax({
			url : baseUrl + '/account/signup/updatePackage',
			type : 'GET',
			dataType : 'json',
			cache : false,
			data:{
				'packageId' : packageId
			},
			success : function(res) {
				console.log("doi thanh cong");
			}
		});
	}
	
	function getPackageById(id){
		$
		.ajax({
			url : baseUrl + '/account/signup/getPackageById',
			type : 'GET',
			dataType : 'json',
			cache : false,
			data:{
				'id' : id
			},
			success : function(res) {
				
				packageName = res.packageName;
				productPrice = res.price;
				$packageName.html("<h1>Recommendation package: "+packageName+"</h1>");
				$productName.html("<div>"+packageName +"</div>");
				$productPrice.html("<div>"+productPrice +"$/Month</div>");
				productTotal = $productTime.val() * productPrice;
				$totalPayment.val($productTime.val() * productPrice) ;
				$productTotal.html("<div>"+productTotal +"$</div>");
				$totalOrder.html("<h3>Total order: "+productTotal +"$</h3>");
				
				
			}
		});
	}
	function loadPackageTable() {
		$
				.ajax({
					url : baseUrl + '/account/signup/getPackages',
					type : 'GET',
					dataType : 'json',
					cache : false,
					success : function(res) {
						console.log('123456');
						var oPackage = '';
						var oPricingTable = '';

						for (var i = 0; i < res.length; i++) {
							if (res[i].packageId == packageSelected) {
								oPricingTable += "<div id='packageTable"
										+ res[i].packageId
										+ "' class='col-md-4 col-sm-6 col-xs-12 selected-package'>";
							} else {
								oPricingTable += "<div id='packageTable"
										+ res[i].packageId
										+ "' class='col-md-4 col-sm-6 col-xs-12'>";

							}
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
							if(i == 0){
							oPricingTable += "<div class='pricing_footer '><button type='button' disabled='disabled' value='"
									+ res[i].packageId
									+ "' class='btn btn-success btn-block btnChoosePackage'>Choose</button></div></div></div></div>";
							}
							else{
								oPricingTable += "<div class='pricing_footer '><button type='button' value='"
									+ res[i].packageId
									+ "' class='btn btn-success btn-block btnChoosePackage'>Choose</button></div></div></div></div>";
							}
							
							oPackage += "<option value='" + res[i].packageId
									+ "'>" + res[i].packageName + "</option>";

						}
						$pricingTable.html(oPricingTable);
						
					},
					error : function(err) {
						console.log(err);
					}
				});
	}
})