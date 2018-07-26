<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- page content -->
<div class="right_col" role="main">
	<div class="">

		<div class="clearfix"></div>
		<div id="alert-message" class="row"
			style="margin: 0px 10px; display: none;">
			<div class="alert alert-danger">
				<strong>${translator.get('queueReport1')} </strong>
				${translator.get('queueReport2')} &nbsp;
				<c:if test="${'ROLE_ROOT' eq sessionScope.userrole}">
					<a href="<c:url value='/${language}/admin/queue/setting'/>">
						${translator.get('queueReport3')}</a>
				</c:if>
				<c:if test="${'ROLE_ADMIN' eq sessionScope.userrole}">
					<i>Please contact ROOT admin!</i>
				</c:if>
			</div>
		</div>

		<div id="content-body" style="display: none;">
			<div class="row top_tiles" style="margin: 10px 0;">
				<div class="col-md-2 col-sm-2 col-xs-6 tile">
					<span>RUNNING</span>
					<h2 id="running-count">0</h2>
					<span class="sparkline_one" style="height: 160px;">
						<canvas width="200" height="60"
							style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
					</span>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-6 tile">
					<span>FINAL</span>
					<h2 id="final-count">0</h2>
					<span class="sparkline_one" style="height: 160px;">
						<canvas width="200" height="60"
							style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
					</span>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-6 tile">
					<span>PREPARED</span>
					<h2 id="prepared-count">0</h2>
					<span class="sparkline_one" style="height: 160px;">
						<canvas width="200" height="60"
							style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
					</span>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-4 tile">
					<span>LOW SUBMITTED</span>
					<h2 id="low-count">0</h2>
					<span class="sparkline_two" style="height: 160px;">
						<canvas width="200" height="60"
							style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
					</span>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-4 tile">
					<span>NORMAL SUBMITTED</span>
					<h2 id="normal-count">0</h2>
					<span class="sparkline_one" style="height: 160px;">
						<canvas width="200" height="60"
							style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
					</span>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-4 tile">
					<span>HIGH_SUBMITTED</span>
					<h2 id="high-count">0</h2>
					<span class="sparkline_one" style="height: 160px;">
						<canvas width="200" height="60"
							style="display: inline-block; vertical-align: top; width: 94px; height: 30px;"></canvas>
					</span>
				</div>
			</div>

			<div class="clearfix"></div>

			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2>
							<i class="fa fa-bars"></i> ${translator.get('queueInformation') }
						</h2>
						<div class="clearfix"></div>
					</div>

					<div class="x_content">
						<div class="" role="tabpanel" data-example-id="togglable-tabs">
							<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
								<li role="presentation" class="active"><a
									href="#tab_content1" id="running-tab" role="tab"
									data-toggle="tab" aria-expanded="true">${translator.get('running')}</a></li>

								<li role="presentation" class=""><a href="#tab_content2"
									id="final-queue-tab" role="tab" data-toggle="tab"
									aria-expanded="true">${translator.get('finalQueue')}</a></li>

								<li role="presentation" class=""><a href="#tab_content3"
									role="tab" id="prepared-queue-tab" data-toggle="tab"
									aria-expanded="false">${translator.get('preparedQueue')}</a></li>

								<li role="presentation" class=""><a href="#tab_content4"
									role="tab" id="submitted-queue-tab" data-toggle="tab"
									aria-expanded="false">${translator.get('submittedQueue')}</a></li>
							</ul>

							<div id="myTabContent" class="tab-content">
								<div role="tabpanel" class="tab-pane fade active in"
									id="tab_content1" aria-labelledby="running-tab">
									<!-- Start Running thread -->
									<table class="table" id="tbl-running-threads">
										<thead>
											<tr>
												<th>#</th>
												<th>Name</th>
												<th>Customer</th>
												<th>Priority</th>
												<th>Threshold</th>
											</tr>
										</thead>
										<tbody>
											<!-- Ajax come here -->
										</tbody>
									</table>
									<!-- End Running thread -->
								</div>

								<div role="tabpanel" class="tab-pane fade" id="tab_content2"
									aria-labelledby="final-queue-tab">
									<!-- Start Final queue -->
									<table class="table table-queue" id="tbl-final-queue">
										<thead>
											<tr>
												<th>#</th>
												<th>Name</th>
												<th>Customer</th>
												<th>Priority</th>
												<th>Estimated(min)</th>
											</tr>
										</thead>
										<tbody>
											<!-- Ajax come here -->
										</tbody>
									</table>
									<!-- End Final queue -->
								</div>

								<div role="tabpanel" class="tab-pane fade" id="tab_content3"
									aria-labelledby="prepared-queue-tab">
									<!-- Start Prepared queue -->
									<table class="table table-queue" id="tbl-prepared-queue">
										<thead>
											<tr>
												<th>#</th>
												<th>Name</th>
												<th>Customer</th>
												<th>Priority</th>
												<th>Estimated(min)</th>
											</tr>
										</thead>
										<tbody>
											<!-- Ajax come here -->
										</tbody>
									</table>
									<!-- End Prepared queue -->
								</div>

								<div role="tabpanel" class="tab-pane fade" id="tab_content4"
									aria-labelledby="submitted-queue-tab">
									<h3>LOW</h3>
									<!-- Start LOW queue -->
									<table class="table table-queue" id="tbl-low-queue">
										<thead>
											<tr>
												<th>#</th>
												<th>Name</th>
												<th>Customer</th>
												<th>Estimated(min)</th>
											</tr>
										</thead>
										<tbody>
											<!-- Ajax come here -->
										</tbody>
									</table>
									<!-- End LOW queue -->
									<h3>NORMAL</h3>
									<!-- Start NORMAL queue -->
									<table class="table table-queue" id="tbl-normal-queue">
										<thead>
											<tr>
												<th>#</th>
												<th>Name</th>
												<th>Customer</th>
												<th>Estimated(min)</th>
											</tr>
										</thead>
										<tbody>
											<!-- Ajax come here -->
										</tbody>
									</table>
									<!-- End NORMAL queue -->
									<h3>HIGH</h3>
									<!-- Start HIGH queue -->
									<table class="table table-queue" id="tbl-high-queue">
										<thead>
											<tr>
												<th>#</th>
												<th>Name</th>
												<th>Customer</th>
												<th>Estimated(min)</th>
											</tr>
										</thead>
										<tbody>
											<!-- Ajax come here -->
										</tbody>
									</table>
									<!-- End HIGH queue -->
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>

			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2>
							<i class="fa fa-bars"></i> Action
						</h2>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">

						<!-- <input type="button" class="btn btn-danger"
							value="Remove recommendations" id="btn-remove-re" />  -->
							<input
							type="button" class="btn btn-warning"
							value="Swap recommendations" id="btn-swap-re" />
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<!-- /page content -->


<!-- Modal -->
<div id="swap-modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">

				<h4 class="modal-title">Swap Recommendations</h4>
			</div>
			<div class="modal-body">

				<table class="table table-queue" id="tbl-prepared-queue-swap">
					<thead>
						<tr>
							<th>#</th>
							<th>Name</th>
							<th>Customer</th>
							<th>Priority</th>
						</tr>
					</thead>
					<tbody>
						<!-- Ajax come here -->
					</tbody>
				</table>
				<!-- End Prepared queue -->

				<!-- End modal body -->
			</div>
			<div class="modal-footer">
				<button type="button" id="btn-swap-cancel" class="btn btn-default">Cancel</button>
				<button type="button" id="btn-swap-save" class="btn btn-primary">Save
					changes</button>
			</div>
		</div>
	</div>
</div>
<!-- End Modal -->

<!-- Modal -->
<div id="remove-modal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">

				<h4 class="modal-title">Modal Header</h4>
			</div>
			<div class="modal-body">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="x_panel">


						<div class="x_content">
							<div class="" role="tabpanel" data-example-id="togglable-tabs">
								<ul id="myTab1" class="nav nav-tabs bar_tabs" role="tablist">

									<li role="presentation" class=""><a href="#tab_content5"
										role="tab" id="prepared-queue-tab1" data-toggle="tab"
										aria-expanded="false">Prepared queues</a></li>

									<li role="presentation" class=""><a href="#tab_content6"
										role="tab" id="submitted-queue-tab1" data-toggle="tab"
										aria-expanded="false">Submitted Queue</a></li>
								</ul>

								<div id="myTabContent" class="tab-content">

									<div role="tabpanel" class="tab-pane fade" id="tab_content5"
										aria-labelledby="prepared-queue-tab1">
										<!-- Start Prepared queue -->
										<table class="table table-queue" id="tbl-prepared-queue1">
											<thead>
												<tr>
													<th>#</th>
													<th>Name</th>
													<th>Customer</th>
													<th>Priority</th>
													<th>Estimated(min)</th>
												</tr>
											</thead>
											<tbody>
												<!-- Ajax come here -->
											</tbody>
										</table>
										<!-- End Prepared queue -->
									</div>

									<div role="tabpanel" class="tab-pane fade" id="tab_content6"
										aria-labelledby="submitted-queue-tab1">
										<h3>LOW</h3>
										<!-- Start LOW queue -->
										<table class="table table-queue" id="tbl-low-queue1">
											<thead>
												<tr>
													<th>#</th>
													<th>Name</th>
													<th>Customer</th>
													<th>Estimated(min)</th>
												</tr>
											</thead>
											<tbody>
												<!-- Ajax come here -->
											</tbody>
										</table>
										<!-- End LOW queue -->
										<h3>NORMAL</h3>
										<!-- Start NORMAL queue -->
										<table class="table table-queue" id="tbl-normal-queue1">
											<thead>
												<tr>
													<th>#</th>
													<th>Name</th>
													<th>Customer</th>
													<th>Estimated(min)</th>
												</tr>
											</thead>
											<tbody>
												<!-- Ajax come here -->
											</tbody>
										</table>
										<!-- End NORMAL queue -->
										<h3>HIGH</h3>
										<!-- Start HIGH queue -->
										<table class="table table-queue" id="tbl-high-queue1">
											<thead>
												<tr>
													<th>#</th>
													<th>Name</th>
													<th>Customer</th>
													<th>Estimated(min)</th>
												</tr>
											</thead>
											<tbody>
												<!-- Ajax come here -->
											</tbody>
										</table>
										<!-- End HIGH queue -->
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
				<!-- End modal body -->
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Send message</button>
			</div>
		</div>

	</div>
</div>
<!-- End Modal -->