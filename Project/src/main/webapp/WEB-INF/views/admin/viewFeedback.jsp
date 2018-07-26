<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="right_col" role="main">
	<div class="x_panel">
		<div class="x_title">
			<h2>
				Feedback Management
			</h2>
			<div class="clearfix"></div>
		</div>
		<div class="x_content">
			<div class="" role="tabpanel" data-example-id="togglable-tabs">
				<ul id="myTab1" class="nav nav-tabs bar_tabs left" role="tablist">
				<li role="presentation" class="active"><a href="#tab_content22"
						role="tab" id="unread-tabb" data-toggle="tab"
						aria-controls="profile" aria-expanded="false">Unread</a></li>
					<li role="presentation" class=""><a
						href="#tab_content11" id="read-tabb" role="tab" data-toggle="tab"
						aria-controls="home" aria-expanded="true">Read</a></li>
				</ul>
				<div id="myTabContent2" class="tab-content">
				<!-- Modal -->
				  <div class="modal fade" id="myModalRead" role="dialog">
				    <div class="modal-dialog">
				      <!-- Modal content-->
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal">&times;</button>
				          <h4 class="modal-title" id="feedbackHeader"></h4>
				        </div>
				        <div class="modal-body" id="feedbackContent">
				        </div>
				        <div class="modal-footer">
				          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        </div>
				      </div>
				    </div>
				  </div>
					<div role="tabpanel" class="tab-pane fade "
						id="tab_content11" aria-labelledby="read-tabb">
						<select id="ddl-readDate" class="btn btn-success dropdown-toggle" style="float: right;">
						<option value="0" selected="selected" >Sort by ascending created time</option>
						<option value="1" >Sort by descending created time</option>
						</select>
						<div id="loadingOverlay" style="min-height: 50vh;">
						<table class="table table-striped table-bordered" id="tbl-read-feedback">
							<thead>
								<tr>
									<th class="text-center">Customer Name</th>
									<th class="text-center">Content</th>
									<th class="text-center">Created Time</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
						<ul class="pagination" id="pgt-read-feedback">
						</ul>
						</div>
					</div>
					<div role="tabpanel" class="tab-pane fade active in" id="tab_content22"
						aria-labelledby="unread-tabb">
						<select id="ddl-unReadDate"  class="btn btn-success dropdown-toggle" style="float: right;">
						<option value="0" selected="selected" >Sort by ascending created time</option>
						<option value="1" >Sort by descending created time</option>
						</select>
						<div id="loadingOverlay2" style="min-height: 50vh;">
						<table class="table table-striped table-bordered" id="tbl-unRead-feedback">
							<thead>
								<tr>
									<th class="text-center">Customer Name</th>
									<th class="text-center">Content</th>
									<th class="text-center">Created Time</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<ul class="pagination" id="pgt-unRead-feedback">
						</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>
<script>
</script>
<!-- /page content -->