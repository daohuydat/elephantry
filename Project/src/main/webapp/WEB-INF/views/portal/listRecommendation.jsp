<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="right_col" role="main">
	<div class="clearfix"></div>
	<div class="">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
				<div class="x_title">
					<h2>
						Your Recommendation 
					</h2>
					<div class="clearfix"></div>
				</div>

				<div class="x_content">

					

					<div class="table-responsive">
					<div class="title_right">

							<div class="x_content" style="text-align: end;">

								<button type="button" class="btn btn-success">
									<span><i class="fa fa-download"></i></span> Get Recommendation
								</button>

								<!-- 
								<button type="button" class="btn btn-danger">
									<span><i class="fa fa-remove"></i></span> Delete
								</button>
			 					-->
							</div>
						</div>
						<table class="table table-striped jambo_table bulk_action">
							<thead>
								<tr class="headings">
									<th><input type="checkbox" id="check-all" class="flat">
									</th>
									<th class="column-title">RecommendationId</th>
									<th class="column-title">Name</th>
									<th class="column-title">Started Time</th>
									<th class="column-title">Finished Time</th>
									<th class="column-title">Estimated Duration</th>
									<th class="column-title">Timer</th>
									<th class="column-title">Status</th>
									<th class="column-title no-link last"><span class="nobr">Action</span>
									</th>
									<th class="bulk-actions" colspan="8"><a class="antoo"
										style="color: #fff; font-weight: 500;">Bulk Actions ( <span
											class="action-cnt"> </span> ) <i class="fa fa-chevron-down"></i></a>
									</th>
								</tr>
							</thead>

							<tbody>
							
								<tr class="even pointer">
									<td class="a-center "><input type="checkbox" class="flat"
										name="table_records"></td>
									<td class=" ">121000040</td>
									<td class=" ">May 23, 2014 11:47:56 PM</td>
									<td class=" ">121000210 <i
										class="success fa fa-long-arrow-up"></i></td>
									<td class=" ">John Blank L</td>
									<td class=" ">Paid</td>
									<td class="a-right a-right ">$7.45</td>
									<td class=" ">Completed</td>
									<td class=" last"><a href="#">Get Result</a></td>
								</tr>
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

