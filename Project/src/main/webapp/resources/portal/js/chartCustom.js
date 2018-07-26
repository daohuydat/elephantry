$(function() {
	var dataR = [];
	var toDay = new Date();
	var thisMonth = toDay.getUTCMonth() + 1;
	var thisYear = toDay.getUTCFullYear();
	if (thisMonth >= 12) {
		for (var i = 0; i < dataCustomerRecommendations.length; i++) {
			dataR.push({
				"period" : thisYear + "-" + (i + 1),
				"recommendation" : dataCustomerRecommendations[i]
			});
		}
	} else {
		for (var i = thisMonth; i < 12; i++) {
			dataR.push({
				"period" : (thisYear - 1) + "-" + (i + 1),
				"recommendation" : dataCustomerRecommendations[i - thisMonth ]
			});
		}
		for (var i = 0; i < thisMonth; i++) {
			dataR.push({
				"period" : (thisYear) + "-" + (i + 1),
				"recommendation" : dataCustomerRecommendations[12 - thisMonth + i]
			});
		}
	}
	
	Morris.Bar({
		element : 'chartCustomerRecommendation',
		data : dataR,
		xkey : 'period',
		barColors : [ '#26B99A', '#34495E', '#ACADAC', '#3498DB' ],
		ykeys : [ 'recommendation' ],
		labels : [ 'recommendation' ],
		hideHover : 'auto',
		xLabelAngle : 60,
		resize : true
	});


});