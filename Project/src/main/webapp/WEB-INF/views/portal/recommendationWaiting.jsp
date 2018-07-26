<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="right_col" role="main">
	<div class="">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>Recommendation Waiting</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false"><i
								class="fa fa-wrench"></i></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">Settings 1</a></li>
								<li><a href="#">Settings 2</a></li>
							</ul></li>

					</ul>
					<div class="clearfix"></div>
				</div>

				<div class="x_content">
					<div class="table-responsive">
						<div class="title_right">
							<div class="x_content" style="text-align: end;">
								<!--
								<button type="button" class="btn btn-success">
									<span><i class="fa fa-download"></i></span> Your Recommendation  Waiting
								</button>
								
								<button type="button" class="btn btn-success">
									<span><i class="fa fa-edit"></i></span> Edit
								</button>-->
								<button type="button" class="btn-submitmultiple btn btn-success">
									<span><i class="fa fa-paper-plane"></i></span> Submit
								</button>
								<button type="button" class="btn-deletemultiple btn btn-danger">
									<span><i class="fa fa-trash"></i></span> Delete
								</button>

							</div>
							<div
								class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
								<div class="input-group">
									<input type="text" id="txtSearch" class="form-control">
									<span class="input-group-btn">
										<button class="btn btn-default" type="button"
											disabled="disabled">Search Keyword</button>
									</span>
								</div>
							</div>
							<div>
								<div class="col-md-4">
									<div id="reportrange_right" class="pull-left"
										style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc">
										<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
											<span>December 30, 2014 - January 28, 2015</span>
										<b class="caret"></b>
									</div>
								</div>
							</div>

						</div>
						<div id="loadingOverlay" style="min-height: 50px;">
							<table class="table table-striped jambo_table bulk_action"
								id="tbl-recommendationWaiting">
								<thead>
									<tr class="headings">
										<th><input type="checkbox" id="check-all" class="flat">
										</th>
										<th class="column-title">ID</th>
										<th class="column-title">Name</th>
										<th class="column-title">Created Date</th>
										<th class="column-title">Timer</th>
										<th class="column-title">Status</th>
										<th class="column-title no-link last centerField"><span
											class="nobr">Submit</span></th>
										<th class="column-title centerField">Delete</th>
										<th class="bulk-actions" colspan="9"><a class="antoo"
											style="color: #fff; font-weight: 500;">Bulk Actions ( <span
												class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
										</th>
									</tr>
								</thead>
								<!-- 							<tbody>
								<c:set value="0" var="currentId"></c:set>
								<c:forEach var="lsRe" items="${listRecommendation}">

									<tr class="even pointer">
										<td class="a-center "><input type="checkbox"
											class="flat checkrecords" name="table_records"
											value="${lsRe.recommendationId}"></td>
										<td>${lsRe.recommendationId}</td>
										<td>${lsRe.name}</td>
										<td>${lsRe.startedTime}</td>
										<td>${lsRe.finishedTime}</td>
										<td>${lsRe.estimatedDuration}</td>
										<td>${lsRe.timer}</td>
										<td>${lsRe.recommendationStatusId}</td>
										<td class=" last centerField">
											<button data-rid="${lsRe.recommendationId}"
												class="btn-submitonly fa fa-arrow-right" type="button"></button>
										</td>
										<td class=" last centerField">
											<button data-rid="${lsRe.recommendationId}"
												class="btn-deleteonly fa fa-remove fa-lg" type="button"></button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							 -->
								<tbody>
								</tbody>
							</table>
							<div class="pagination btn-group" id="pgt-recommendationWaiting">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" id="modalDetail"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title " id="myModalLabel">Recommendation
						Detail</h4>
				</div>
				<div>
					<div class="x_content">
						<div class="row">

							<div class="col-md-12" id="detailRecommendation"></div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<!-- 
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary btnSaveChange">Save
							changes</button> -->
				</div>

			</div>
		</div>
	</div>
</div>
<script>
var successMessage = '${successMessage}';
</script>
