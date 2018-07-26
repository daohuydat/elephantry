/**
 * 
 */
$(function() {
	/* Start */
	var baseUrl = rootBaseUrl;

	var $mapChart = $('#map-chart');
	var $reduceChart = $('#reduce-chart');
	var $heapChart = $('#heap-chart');

	var $mapChartTitle = $('#map-chart-title');
	var $reduceChartTitle = $('#reduce-chart-title');
	var $heapChartTitle = $('#heap-chart-title');

	$('.chart').easyPieChart({

	});

	setInterval(function() {
		updateClusterInfo();
	}, 10000);

	function updateClusterInfo() {
		$.ajax({
					url : baseUrl + '/admin/hadoop/getClusterInfo',
					type : 'GET',
					dataType : 'json',
					cache : false,
					success : function(res) {

						var percent = Math.ceil(100.0 * res.mapTasks
								/ res.maxMapTasks);
						$mapChart.data('easyPieChart').update(percent);
						$mapChartTitle.html(res.mapTasks + " / "
								+ res.maxMapTasks);

						percent = Math.ceil(100.0 * res.reduceTasks
								/ res.maxReduceTasks);
						$reduceChart.data('easyPieChart').update(percent);
						$reduceChartTitle.html(res.reduceTasks + " / "
								+ res.maxReduceTasks);

						percent = Math.ceil(100.0 * res.usedMemory
								/ res.maxMemory);
						$heapChart.data('easyPieChart').update(percent);
						$heapChartTitle.html(res.usedMemory + " / "
								+ res.maxMemory + " MB");
					},
					error : function(err) {
						console.log(err);
					}
				});
	}


	/* End */
});