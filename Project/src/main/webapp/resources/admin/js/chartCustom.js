$(function() {
	var dataR = [];
	var dataC = [];
	var toDay = new Date();
	var thisMonth = toDay.getUTCMonth() + 1;
	var thisYear = toDay.getUTCFullYear();
	if (thisMonth >= 12) {
		for (var i = 0; i < dataRecommendations.length; i++) {
			dataR.push({
				"period" : thisYear + "-" + (i + 1),
				"recommendation" : dataRecommendations[i]
			});
		}
	} else {
		for (var i = thisMonth; i < 12; i++) {
			dataR.push({
				"period" : (thisYear - 1) + "-" + (i + 1),
				"recommendation" : dataRecommendations[i - thisMonth ]
			});
		}
		for (var i = 0; i < thisMonth; i++) {
			dataR.push({
				"period" : (thisYear) + "-" + (i + 1),
				"recommendation" : dataRecommendations[12 - thisMonth + i]
			});
		}
	}
//	console.log(dataRecommendations)
	if($("#packageGraph").length){
		Morris.Donut({
			element : 'packageGraph',
			data : [ {
				label : 'Free Trial',
				value : parseInt($("#packageGraph").attr("data-package1"))
			}, {
				label : 'Standard',
				value : parseInt($("#packageGraph").attr("data-package2"))
			}, {
				label : 'Premium',
				value : parseInt($("#packageGraph").attr("data-package3"))
			} ],
			colors : [ '#ACADAC', '#1ABB9C', '#e1b541' ],
			formatter : function(y) {
				return y + "%";
			},
			resize : true
		});
	}
	

	Morris.Bar({
		element : 'chartRecommendation',
		data : dataR,
		xkey : 'period',
		barColors : [ '#26B99A', '#34495E', '#ACADAC', '#3498DB' ],
		ykeys : [ 'recommendation' ],
		labels : [ 'recommendation' ],
		hideHover : 'auto',
		xLabelAngle : 60,
		resize : true
	});
	
	if (thisMonth >= 12) {
		for (var i = 0; i < dataCustomer.length; i++) {
			dataC.push({
				"period" : thisYear + "-" + (i + 1),
				"total-customer" : dataCustomer[i]
			});
		}
	} else {
		for (var i = thisMonth; i < 12; i++) {
			dataC.push({
				"period" : (thisYear - 1) + "-" + (i + 1),
				"total-customer" : dataCustomer[i - thisMonth ]
			});
		}
		for (var i = 0; i < thisMonth; i++) {
			dataC.push({
				"period" : (thisYear) + "-" + (i + 1),
				"total-customer" : dataCustomer[12 - thisMonth + i]
			});
		}
	}
	
	Morris.Line({
		element : 'customerChart',
		xkey : 'period',
		ykeys : [ 'total-customer' ],
		labels : [ 'Total Customers' ],
		hideHover : 'auto',
		lineColors : [ '#26B99A', '#34495E', '#ACADAC', '#3498DB' ],
		data : dataC,
		resize : true
	});

	$MENU_TOGGLE.on('click', function() {
		$(window).resize();
	});
});