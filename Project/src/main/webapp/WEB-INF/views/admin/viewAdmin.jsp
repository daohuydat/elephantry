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
							Admin Management <small></small>
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
								<div class="title_left">&nbsp;</div>

								<div class="title_right">
									<div
										class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
										<div class="input-group">
											<input type="text" id="txtSearch" class="form-control">
											<span class="input-group-btn">
												<button class="btn btn-default" type="button"
													disabled="disabled">Search</button>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div id="loadingOverlay" style="min-height: 50vh;">
								<table class="table table-striped jambo_table" id="tbl-admin">
									<thead>
										<tr class="headings">
											<th class="text-center">User ID</th>
											<th class="text-center">Email</th>
											<th class="text-center">Created Time</th>
											<th class="text-center">Action</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
								<ul class="pagination" id="pgt-admin">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
var successMessage = '${successMessage}';
</script>
<!-- /page content -->