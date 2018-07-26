/**
 * 
 */
$(function() {
	var baseUrl = rootBaseUrl;

	var $startQueueBtn = $('#start-queue');
	var $stopQueueBtn = $('#stop-queue');
	var $resetQueueBtn = $('#reset-queue');
	var $resumeQueueBtn = $('#resume-queue');
	var $pauseQueueBtn = $('#pause-queue');
	
	var $queueStatus = $('#queue-status');
	
	var $tblConfig = $('#tbl-config');
	var $rowConfigs = $('#tbl-config tr.config');
	var $inputConfigs = $('#tbl-config input.config-val');
	var $btnSaveConfigs = $('#tbl-config input.btn');
	
	var $warningModal = $('#warning-modal');
	var $modalMsg = $('#modal-msg');
	
	
	
	$inputConfigs.change(function(e) {
		$(this).parent().next().children('input.btn').attr('disabled', false);
	});
	
	$btnSaveConfigs.click(function(e) {
		var $input = $(this).parent().prev().children('input.config-val');
		var min = 0;
		var max = 1;
		var valid = false;
		 if("elephantry.training.percentage" ===  $input.attr('name')){
			min = 0;
			max = 1;
			var value = parseFloat($input.val());
			
			if (min <= value && value <= max) {
				valid = true;
			}
		} else if("elephantry.threshold" ===  $input.attr('name')){
			min = 0;
			max = 1;
			var value = parseFloat($input.val());
			
			if (min <= value && value <= max) {
				valid = true;
			}
		} else {
			min = parseInt($input.attr('min'));
			max = parseInt($input.attr('max'));
			var value = parseInt($input.val());
			
			if (min <= value && value <= max) {
				valid = true;
			}
		}
		
		if(valid){
			var config = {
				'key' : $input.attr('name'),
				'value': $input.val()
			};
			
			saveConfig(config, $(this));
		} else {
			$warningModal.modal('show');
			$modalMsg.html('Value must be with in range [' + min + "," + max + "]");
		}
		
	});

	var queueStatus = 0;
	
	
	$(document).ajaxSend(function(event, jqxhr, settings){
	    $.LoadingOverlay("show");
	});
	$(document).ajaxComplete(function(event, jqxhr, settings){
	    $.LoadingOverlay("hide");
	});
	
	updateQueueStatusHtml();
	
	$startQueueBtn.click(function(e) {
		if(!$startQueueBtn.attr("disabled")){
			doQueueAction("start");
		}
	});
	
	$stopQueueBtn.click(function(e) {
		if(!$stopQueueBtn.attr("disabled")){
			doQueueAction("stop");
		}
	});
	
	$resetQueueBtn.click(function(e) {
		if(!$resetQueueBtn.attr("disabled")){
			doQueueAction("reset");
		}
	});
	
	$resumeQueueBtn.click(function(e) {
		if(!$resumeQueueBtn.attr("disabled")){
			doQueueAction("resume");
		}
	});
	
	$pauseQueueBtn.click(function(e) {
		if(!$pauseQueueBtn.attr("disabled")){
			doQueueAction("pause");
		}
	});
	
	function saveConfig(config, $this) {
		$.ajax({
			url : baseUrl + '/admin/queue/saveConfig',
			type : 'POST',
			dataType : 'json',
			cache : false,
			data : config,
			success : function(res) {
				if(res.success){
					$this.attr('disabled', true);
				}
			},
			error : function(err) {
				console.log(err);
			}
		});
	}
	
	function doQueueAction(action) {
		$.ajax({
			url : baseUrl + '/admin/queue/doQueueAction',
			type : 'POST',
			dataType : 'json',
			cache : false,
			data : {
				'action' : action
			},
			success : function(res) {
				queueStatus = res.queueStatus;
				$queueStatus.html(getStatusString(queueStatus));
				disableButtonBaseOnStatus(queueStatus);
				
			},
			error : function(err) {
				console.log(err);
			}
		});
	}
	function updateQueueStatus(status) {
		$.ajax({
			url : baseUrl + '/admin/queue/updateQueueStatus',
			type : 'POST',
			dataType : 'json',
			cache : false,
			data : {
				'status' : status
			},
			success : function(res) {
				queueStatus = res.queueStatus;
				
				$queueStatus.html(getStatusString(queueStatus));
				disableButtonBaseOnStatus(queueStatus);
			},
			error : function(err) {
				console.log(err);
			}
		});
	}
	
	function updateQueueStatusHtml() {
		$.ajax({
			url : baseUrl + '/admin/queue/getQueueStatus',
			type : 'GET',
			dataType : 'json',
			cache : false,
			success : function(res) {
				queueStatus = res.queueStatus;
				console.log(queueStatus);
				$queueStatus.html(getStatusString(queueStatus));
				disableButtonBaseOnStatus(queueStatus);
			},
			error : function(err) {
				console.log(err);
			}
		});
	}

	
	function getStatusString(status) {
		if (status === 1) {
			return 'STARTED (RUNNING)';
		} else if (status === 2) {
			return 'PAUSED';
		} else if (status === 3) {
			return 'STOPPED';
		} 
		return '';
	}
	
	function disableButtonBaseOnStatus(status){
		if (status === 1) {
			$startQueueBtn.attr( "disabled", true).removeClass('setting-green');
			$resumeQueueBtn.attr( "disabled", true).removeClass('setting-green');
			
			$stopQueueBtn.attr( "disabled", false).addClass('setting-red');
			$resetQueueBtn.attr( "disabled", false).addClass('setting-red');
			$pauseQueueBtn.attr( "disabled", false).addClass('setting-orange');
		} else if (status === 2) {
			$startQueueBtn.attr( "disabled", true).removeClass('setting-green');
			$pauseQueueBtn.attr( "disabled", true).removeClass('setting-orange');
			
			$resumeQueueBtn.attr( "disabled", false).addClass('setting-green');
			$stopQueueBtn.attr( "disabled", false).addClass('setting-red');
			$resetQueueBtn.attr( "disabled", false).addClass('setting-red');
		} else if (status === 3) {
			$stopQueueBtn.attr( "disabled", true).removeClass('setting-red');
			$resetQueueBtn.attr( "disabled", true).removeClass('setting-red');
			$resumeQueueBtn.attr( "disabled", true).removeClass('setting-green');
			$pauseQueueBtn.attr( "disabled", true).removeClass('setting-orange');
			
			$startQueueBtn.attr( "disabled", false).addClass('setting-green');
		} 
		
	}

});