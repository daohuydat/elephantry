<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
function isNumberKey(txt, evt) {

    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode == 46) {
        //Check if the text already contains the . character
        if (txt.value.indexOf('.') === -1) {
            return true;
        } else {
            return false;
        }
    } else {
        if (charCode > 31
             && (charCode < 48 || charCode > 57))
            return false;
    }
    return true;
}
</script>
<!-- page content -->
<div class="right_col" role="main">
	<div class="">

		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>
						<i class="fa fa-bars"></i> ${translator.get('queueSetting')}
					</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">

					<div class="row">
						<h2>
							${translator.get('queueStatus')} :  <span id="queue-status"></span>
						</h2>
					</div>
					<div class="row">

						<a id="start-queue" class="btn btn-app"> <i class="fa fa-play"></i>
							${translator.get('startQueue')}
						</a> <a id="stop-queue" class="btn btn-app"> <i
							class="fa fa-pause"></i> ${translator.get('stopQueue')}
						</a> <a id="reset-queue" class="btn btn-app"> <i
							class="fa fa-repeat"></i> ${translator.get('resetQueue')}
						</a> <a id="resume-queue" class="btn btn-app"> <i
							class="fa fa-play"></i> ${translator.get('resumeQueue')}
						</a> <a id="pause-queue" class="btn btn-app"> <i
							class="fa fa-pause"></i> ${translator.get('pauseQueue')}
						</a>

					</div>

				</div>
			</div>
		</div>

		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>
						<i class="fa fa-bars"></i> ${translator.get('queueConfiguration')}
					</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<table class="table" id="tbl-config">
						<thead>
							<tr>
								<th>#</th>
								<th>Key</th>
								<th>Value</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr class="config">
								<th scope="row">1</th>
								<td>Priority Coefficient</td>
								<td><input type="number" class="config-val" name="elephantry.priority.scale" min="2" max="10" value="${priorityScale.value}" /></td>
								<td><input type="button" class="btn btn-success" value="Save change" disabled="disabled"/></td>
							</tr>
							<tr class="config">
								<th scope="row">2</th>
								<td>Final Queue Max Size</td>
								<td><input type="number"  class="config-val" name="elephantry.queue.final.size.max"  min="1" max="10" value="${finalQueueMaxSize.value }" /></td>
								<td><input type="button" class="btn btn-success" value="Save change" disabled="disabled"/></td>
							</tr>
							<tr class="config">
								<th scope="row">3</th>
								<td>Prepared Queue Min Size</td>
								<td><input type="number" class="config-val"  name="elephantry.queue.prepared.size.min"  min="1" max="20" value="${preparedQueueMinSize.value }" /></td>
								<td><input type="button" class="btn btn-success" value="Save change" disabled="disabled"/></td>
							</tr>
							<tr class="config">
								<th scope="row">4</th>
								<td>Running Recommendations Max Size</td>
								<td><input type="number" class="config-val"  name="elephantry.running.size.max"  min="1" max="10" value="${runningMaxSize.value }"/></td>
								<td><input type="button" class="btn btn-success" value="Save change" disabled="disabled"/></td>
							</tr>
							<tr class="config">
								<th scope="row">5</th>
								<td>Training percentage</td>
								<td><input type="text"  class="config-val" name="elephantry.training.percentage" value="${trainingPercentage.value }" size="4" onkeypress="return isNumberKey(this, event);" /></td>
								<td><input type="button" class="btn btn-success" value="Save change" disabled="disabled"/></td>
							</tr>
							<tr class="config">
								<th scope="row">6</th>
								<td>Threshold</td>
								<td><input type="text"  class="config-val" name="elephantry.threshold" value="${threshold.value }" size="4" onkeypress="return isNumberKey(this, event);" /></td>
								<td><input type="button" class="btn btn-success" value="Save change" disabled="disabled"/></td>
							</tr>
						</tbody>
					</table>

				</div>
			</div>
		</div>



	</div>
</div>
<!-- /page content -->

<!-- Modal -->
<div id="warning-modal" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Error!</h4>
			</div>
			<div class="modal-body">
				<p id="modal-msg"></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">${translator.get('close')}</button>
			</div>
		</div>

	</div>
</div>
<!-- End Modal -->