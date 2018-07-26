$(function() {
	"use strict";
	var slickDTP = new SlickDTP();
	$("#datasetDemo").tooltip({ content: '<img alt="" src="'+rootBaseUrl+'/resources/images/demoDataset.jpg" />' });
	$("#txtThreshold").keyup(function() {
		if (parseFloat($("#txtThreshold").val()) > 1) {
			$("#thresholdMessage").show();
			$("#btnCreate").prop("disabled", true);
		}else{
			$("#thresholdMessage").hide();
			$("#btnCreate").prop("disabled", false);
		}
	});
	$("#txtDate").click(function() {
		slickDTP.pickDate('#txtDate','');	
	});
	$('#file').bind('change', function() {
		var filename = $("#file").val();
		if (/^\s*$/.test(filename)) {
			$(".file-upload").removeClass('active');
			$("#noFile").text("No file chosen...");
		} else {
			$(".file-upload").addClass('active');
			$("#noFile").text(filename.replace("C:\\fakepath\\", ""));
		}
	});
	var $bar = $('#vbar');
	var status = $('#vstatus');
	$("#submitCheck").change(function() {
		if(this.checked){
			$("#divTimerCheckBox").fadeIn();
		}else{
			$("#divTimerCheckBox").fadeOut();
			$("#timeCheck").attr('checked', false);
			$("#divTimer").fadeOut();
			$("#txtDate").val("");
			$( "#date_time_container" ).dialog("close");
		}
	});
	$("#timeCheck").change(function() {
		if(this.checked){
			$("#divTimer").fadeIn();
		}else{
			$("#divTimer").fadeOut();
			$("#txtDate").val("");
			$( "#date_time_container" ).dialog("close");
		}
	});
	$("#file").change(function() {
		var file = this.files[0];
		var reader = new FileReader();
		var row;
		var valid = true;
		reader.onload = function(progressEvent) {
			var lines = this.result.split('\n');
			$("#txtRecordCount").val(lines.length);
			var vper = 0;
			var line = 0;
			var errMes="";
			var countErr = 0;
			for (line = 0; line < lines.length; line++) {
				if (lines[line].trim().length === 0) {
					continue;
				}
				if (countErr>=10) {
					break;
				}
				row = lines[line].split(',');
				vper = Math.ceil(100 * (line+1) / lines.length);
				if (row.length === 3 || row.length === 4) {
					if (!row[0] || isNaN(row[0]) || row[0] === "" || row[0].length > 15 || !Number.isInteger(parseFloat(row[0])) || parseFloat(row[0]) <= 0.0) {
						errMes += "Column &quotUser ID&quot invalid at row " + (line + 1) + "<br><br>";
						valid = false;
						countErr++;
						
					}
					if (!row[1] || isNaN(row[1]) || row[1] === "" || row[1].length > 15 || !Number.isInteger(parseFloat(row[1])) || parseFloat(row[1]) <= 0.0) {
						errMes += "Column &quotItem ID&quot invalid at row " + (line + 1) + "<br><br>";
						valid = false;
						countErr++;
					}
					if (!row[2] || isNaN(row[2]) || row[2] === "" || row[2].length > 15 || parseFloat(row[2]) <= 0.0) {
						errMes += "Column &quotPreference&quot invalid at row " + (line + 1) + "<br><br>";
						valid = false;
						countErr++;
					}
					if(row.length === 4){
						if(!row[3] || isNaN(row[3]) || row[3] === "" || row[3].length > 15 || !Number.isInteger(parseFloat(row[3]))){
							errMes += "Column &quotTimestamp&quot invalid at row " + (line + 1) + "<br><br>";
							valid = false;
							countErr++;
						}
					}
				} else {
					errMes += "Missing column(s) at row " + (line + 1) + "<br><br>";
					valid = false;
					countErr++;
				}
				if(line%50===0){
					$bar.attr("style", "width:" + vper + "%;");
					$bar.html(vper + "%");
					status.html(vper + "%");
				}
				
			}
			errMes+="<br><br><h3>Please choose another file!<h3>"
			if (!valid) {
				$("#errMess").html(errMes);
				$("#myModal").modal("show");
			}
			$bar.attr("style", "width:" + vper + "%;");
			$bar.html(vper + "%");
			status.html(vper + "%");
			if (valid) {
				status.html("sucess");
				$("#btnCreate").prop("disabled", false);
			}else{
				status.html("Invalid");
				$("#vbar").removeClass("progress-bar-success").addClass("progress-bar-danger");
			}
		};
		var extension = file.name.substr(file.name.lastIndexOf("."));
		if (extension === ".csv") {
			reader.readAsText(file);
		} else {
			alert("Upload cvs file please!!! Choose another file");
		}
	});
	$("#file").click(function() {
		var $el = $(this);
		   $el.wrap('<form>').closest('form').get(0).reset();
		   $el.unwrap();
		   $bar.attr("style", "width:0%;");
		   $bar.html("0%");
		   status.html("");
		   $("#noFile").text("No file chosen...");
		   $(".file-upload").removeClass('active');
		   $("#btnCreate").prop("disabled", true);
		   $("#vbar").removeClass("progress-bar-danger").addClass("progress-bar-success");
	});
	var $ubar = $('#pbar');
	var ustatus = $('#status');
	$('#uploadForm').ajaxForm({
		beforeSend : function() {
			ustatus.empty();
		},
		method : 'POST',
		uploadProgress : function(event, position, total, percentComplete) {
			var percentVal = percentComplete + '%';
			$ubar.attr("style", "width:" + percentVal + ";");
			$ubar.html(percentVal);
			ustatus.html(percentVal);
		},
		complete : function(xhr) {
			ustatus.html("sucess");
		    location.href= rootBaseUrl + xhr.responseText;
		}
	});
});
