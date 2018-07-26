<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- page content -->
<div class="right_col" role="main">
	<!-- top tiles -->
	<div class="row tile_count">
		<div class="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i> Total Recommendations</span>
			<div class="count">${totalRecommendations}</div>
			<span class="count_bottom"><i class="green"><i
					class="fa fa-sort-asc"></i>${compareRecommendation}%</i> From last
				Week</span>
		</div>
		<div class="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i>
				Running Recommendations</span>
			<div class="count">${runningJobs}</div>
			
		</div>
		<div class="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i>Submitted Recommendations</span>
			<div class="count">${submitJobs}</div>
		</div>
		
		<div class="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i> Total Customers </span>
			<div class="count green">${totalCustomer}</div>
			<span class="count_bottom"><i class="green"><i
					class="fa fa-sort-asc"></i>${compareCustomer}%</i> From last Week</span>
		</div>
		
	</div>
	<!-- /top tiles -->

	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">

				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="x_panel">
						<div class="x_title">
							<h2>Customer Chart</h2>
							<div class="clearfix"></div>
						</div>
						<div class="demo-container">
							<div id="customerChart"></div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="x_panel">
						<div class="x_title">
							<h2>Recommendation Summary Chart</h2>
							<div class="clearfix"></div>
						</div>
						<div class="demo-container" style="height: 280px">
							<div id="chartRecommendation" class="demo-placeholder"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>
<!-- /page content -->
<script>
	var dataRecommendations = ${recommendationByMonths};
	var dataCustomer = ${customerByMonths};
</script>