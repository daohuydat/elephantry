<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="right_col" role="main">
	<div class="clearfix"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h2>
							Customer Management <small></small>
						</h2>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-expanded="false"><i
									class="fa fa-wrench"></i></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="#">Settings 1</a></li>
									<li><a href="#">Settings 2</a></li>
								</ul>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
						<div class="table-responsive">
							<div class="page-title">
								<div class="row">
									<div
										class="col-md-4 col-sm-5 col-xs-12 form-group pull-left top_search">
										<div class="input-group">
											<input type="text" id="txtSearchEmail" class="form-control">
											<span class="input-group-btn">
												<button class="btn btn-default" type="button"
													disabled="disabled">Email Search</button>
											</span>
										</div>
									</div>
									<div class="col-md-4 col-sm-5 col-xs-12 form-group top_search">
										<div class="input-group">
											<input type="text" id="txtSearch" class="form-control">
											<span class="input-group-btn">
												<button class="btn btn-default" type="button"
													disabled="disabled">Keyword Search</button>
											</span>
										</div>
									</div>
								</div>
								<div class="col-md-4 col-sm-2 col-xs-12 form-group top_search">
								</div>
							</div>

							<div id="loadingOverlay" style="min-height: 50vh;">
								<table class="table table-striped jambo_table "
									id="tbl-customer">
									<thead>
										<tr class="headings">
											<th class="text-center">Customer ID</th>
											<th class="text-center">Full Name</th>
											<th class="text-center">Email</th>
											<th class="text-center">Gender</th>
											<th class="text-center">Date of Birth</th>
											<th class="text-center">Website</th>
											<th class="text-center">Company</th>
											<th class="text-center">Phone</th>
											<th class="text-center">Province</th>
											<th class="text-center">Package</th>
											<th class="text-center">Address</th>
											<th class="text-center">Action</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
								<ul class="pagination" id="pgt-customer">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->