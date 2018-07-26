
$(function() {
	/* Start */
	var baseUrl = rootBaseUrl;
	var $tblPreparedBody = $('#tbl-prepared-queue tbody');
	var $tblLowBody = $('#tbl-low-queue tbody');
	var $tblNormalBody = $('#tbl-normal-queue tbody');
	var $tblHighBody = $('#tbl-high-queue tbody');
	var $tblFinalBody = $('#tbl-final-queue tbody');
	var $tblRunningThreadsBody = $('#tbl-running-threads tbody');
	var $alertMessage = $('#alert-message');
	var $contentBody = $('#content-body');
	
	var $finalCount = $('#final-count');
	var $runningCount = $('#running-count');
	var $preparedCount = $('#prepared-count');
	var $lowCount = $('#low-count');
	var $normalCount = $('#normal-count');
	var $highCount = $('#high-count');

	var $btnRemoveRe = $('#btn-remove-re');
	var $btnSwapRe = $('#btn-swap-re');
	var $removeModal = $('#remove-modal');
	var $swapModal = $('#swap-modal');
	var $tblSwapBody = $('#tbl-prepared-queue-swap tbody');
	var $btnSwapSave = $('#btn-swap-save');
	var $btnSwapCancel = $('#btn-swap-cancel');
	
	$btnRemoveRe.click(function() {
		$removeModal.modal({backdrop: 'static', keyboard: false});
	});
	
	$btnSwapRe.click(function() {
		updateSwapDataTable();
		$tblSwapBody.sortable().disableSelection();

	});
	
	$btnSwapSave.click(function() {
		var ids = [];
		$tblSwapBody.children('tr').each(function(i, row) {
			ids.push(parseInt($(row).attr('data-rid')));
		});
		saveSwappedRecommendations(ids);
	});
	
	$btnSwapCancel.click(function() {
		cancelSwappedRecommendations();
	});

	updateQueueInfo();
	updateRunningThreadsTable();
	updateQueueDataTable();
	
	setInterval(function(){ 
		updateRunningThreadsTable();
		updateQueueDataTable();
		updateQueueInfo();
	}, 10000);
	
	function updateQueueDataTable(){
		$.ajax({
			url: baseUrl+'/admin/queue/getQueueData',
			type : 'GET',
			dataType : 'json',
			cache : false,
			success : function(res){
				/* low queue */
				var arr = res.lowQueue;
				var html = '';
				for (var i = 0; i < arr.length; i++) {
					html += buildHtmlSubmittedTableRow(i, arr[i]);
				}
				$tblLowBody.html(html);
				
				/* normal queue */
				arr = res.normalQueue;
				html = '';
				for (var i = 0; i < arr.length; i++) {
					html += buildHtmlSubmittedTableRow(i, arr[i]);
				}
				$tblNormalBody.html(html);
				
				/* high queue */
				arr = res.highQueue;
				html = '';
				for (var i = 0; i < arr.length; i++) {
					html += buildHtmlSubmittedTableRow(i, arr[i]);
				}
				$tblHighBody.html(html);
				
				/* prepared queue */
				arr = res.preparedQueue;
				html = '';
				for (var i = 0; i < arr.length; i++) {
					html += buildHtmlPreparedTableRow(i, arr[i]);
				}
				$tblPreparedBody.html(html);
				
				/* final queue */
				arr = res.finalQueue;
				html = '';
				for (var i = 0; i < arr.length; i++) {
					html += buildHtmlFinalTableRow(i, arr[i]);
				}
				$tblFinalBody.html(html);
			},
			error : function(err){
				console.log(err);
			}
		});
	}
	
	function updateSwapDataTable() {
		$.LoadingOverlay("show");
		$.ajax({
			url: baseUrl+'/admin/queue/getSwapQueueData',
			type : 'GET',
			dataType : 'json',
			cache : false,
			success : function(res){
				$swapModal.modal({backdrop: 'static', keyboard: false});
				var arr = res.preparedQueue;
				if(res.queuePaused){
					html = '';
					for (var i = 0; i < arr.length; i++) {
						html += buildHtmlSwapTableRow(i, arr[i]);
					}
					$tblSwapBody.html(html);
				}
				$.LoadingOverlay("hide");
			},
			error : function(err){
				console.log(err);
				$.LoadingOverlay("hide");
			}
		});
	}
	
	function saveSwappedRecommendations(ids) {
		$.LoadingOverlay("show");
		$.ajax({
			url: baseUrl+'/admin/queue/saveSwappedRecommendations',
			type : 'POST',
			dataType : 'json',
			cache : false,
			data : {
				'ids' : ids
			},
			success : function(res){
				if(res.success){
					$swapModal.modal('hide');
					var arr = res.preparedQueue;
					var html = '';
					for (var i = 0; i < arr.length; i++) {
						html += buildHtmlPreparedTableRow(i, arr[i]);
					}
					$tblPreparedBody.html(html);
				}
				$.LoadingOverlay("hide");
			},
			error : function(err){
				console.log(err);
				$.LoadingOverlay("hide");
			}
		});
	}
	
	function cancelSwappedRecommendations() {
		$.LoadingOverlay("show");
		$.ajax({
			url: baseUrl+'/admin/queue/cancelSwappedRecommendations',
			type : 'POST',
			dataType : 'json',
			cache : false,
			success : function(res){
				if(res.success){
					$swapModal.modal('hide');
				}
				$.LoadingOverlay("hide");
			},
			error : function(err){
				console.log(err);
				$.LoadingOverlay("hide");
			}
		});
	}
	
	
	function updateRunningThreadsTable(){
		$.ajax({
			url: baseUrl+'/admin/queue/getRunningThreads',
			type : 'GET',
			dataType : 'json',
			cache : false,
			success : function(res){
				var arr = res.running;
				var html = '';
				for (var i = 0; i < arr.length; i++) {
					html += buildHtmlRunningThreadsTableRow(i, arr[i]);
				}
				$tblRunningThreadsBody.html(html);
			},
			error : function(err){
				console.log(err);
			}
		});
	}


	function updateQueueInfo(){
		$.ajax({
			url: baseUrl+'/admin/queue/getQueueInfo',
			type : 'GET',
			dataType : 'json',
			cache : false,
			success : function(res){
				if (res.queueStatus === 1 || res.queueStatus === 2) {
					$alertMessage.hide();
					$contentBody.show();
					
					$runningCount.html(res.runningCount);
					$finalCount.html(res.finalCount);
					$preparedCount.html(res.preparedCount);
					$lowCount.html(res.lowSubmittedCount);
					$normalCount.html(res.normalSubmittedCount);
					$highCount.html(res.highSubmittedCount);
				} else {
					$alertMessage.show();
					$contentBody.hide();
				}
				
			},
			error : function(err){
				console.log(err);
			}
		});
	}

	function buildHtmlPreparedTableRow(index, row) {
		return '<tr>' + '<td>'+(index+1)+'</td>'
				+ '<td><a href="#">'+row.name+'</a></td>'
				+ '<td><a href="#">'+row.customerName+'</a></td>' 
				+ '<td>'+getPriority(row.priority)+'</td>' 
				+ '<td>'+row.estimatedDuration+'</td>'
				+ '</tr>';
	}
	
	function buildHtmlSwapTableRow(index, row) {
		return '<tr data-rid="'+row.recommendationId+'">' + '<td>'+(index+1)+'</td>'
				+ '<td><a href="#">'+row.name+'</a></td>'
				+ '<td><a href="#">'+row.customerName+'</a></td>' 
				+ '<td>'+getPriority(row.priority)+'</td>' 
				+ '</tr>';
	}
	
	function buildHtmlFinalTableRow(index, row) {
		return '<tr>' + '<td>'+(index+1)+'</td>'
				+ '<td><a href="#">'+row.name+'</a></td>'
				+ '<td><a href="#">'+row.customerName+'</a></td>' 
				+ '<td>'+getPriority(row.priority)+'</td>' 
				+ '<td>'+row.estimatedDuration+'</td>'
				+ '</tr>';
	}
	function buildHtmlSubmittedTableRow(index, row) {
		return '<tr>' + '<td>'+(index+1)+'</td>'
				+ '<td><a href="#">'+row.name+'</a></td>'
				+ '<td><a href="#">'+row.customerName+'</a></td>' 
				+ '<td>'+row.estimatedDuration+'</td>'
				+ '</tr>';
	}
	function buildHtmlRunningThreadsTableRow(index, row) {
		return '<tr>' + '<td>'+(index+1)+'</td>'
				+ '<td><a href="#">'+row.name+'</a></td>'
				+ '<td><a href="#">'+row.customerName+'</a></td>' 
				+ '<td>'+getPriority(row.priority)
				+ '<td>'+row.threshold+'</td>'
				+ '</tr>';
	}
	
	function getPriority(priority){
		if(priority === 1){
			return 'LOW';
		}else if(priority === 2){
			return 'NORMAL';
		}else {
			return 'HIGH';
		}
	}
	/* End */
});