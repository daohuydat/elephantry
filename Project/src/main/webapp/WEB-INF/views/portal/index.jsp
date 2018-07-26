<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- page content -->
<div class="right_col" role="main">

	<!-- top tiles -->
	<div class="row tile_count">
		<div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i> Total Recommendations</span>
			<div class="count">${totalRecommendations}</div>
		</div>
		<div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i> Waiting Recommendations</span>
			<div class="count">${waitingRecommendations}</div>
		</div>
		<div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i> Submitted Recommendations</span>
			<div class="count">${submittedRecommendations}</div>
		</div>
		<div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-clock-o"></i> Running Recommendations</span>
			<div class="count">${runningRecommendations}</div>
		</div>
		<div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i> Completed Recommendations</span>
			<div class="count green">${completedRecommendations}</div>
		</div>
		<div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
			<span class="count_top"><i class="fa fa-user"></i>
				My Account</span>
			<div class="count" style="font-size: 30px;">${currentPackage}</div>
			<c:if test="${currentPackage eq 'Free Trial'}">
			
			</c:if>
			<c:if test="${currentPackage eq 'Standard'}">
			<span class="count_bottom"><i class="green"></i>Expired Date ${expiredDate }</span>
			</c:if>
			<c:if test="${currentPackage eq 'Premium'}">
			<span class="count_bottom"><i class="green"><i
					class="fa fa-sort-asc"></i>Expired Date ${expiredDate }</i></span>
			</c:if>
		</div>
	</div>
	
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>
					Recommendation Summary Chart <small>Sessions</small>
				</h2>
				<div class="clearfix"></div>
			</div>
			<div class="demo-container" style="height: 280px">
				<div id="chartCustomerRecommendation" class="demo-placeholder"></div>
			</div>
		</div>
	</div>
	
	<div class="clearfix"></div>
</div>
<script>
var dataCustomerRecommendations = ${customerRecommendationByMonths};
</script>
<!-- /page content -->