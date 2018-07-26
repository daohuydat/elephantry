<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="right_col" role="main">

	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>
					Recommendation Summary Chart
				</h2>
				<div class="clearfix"></div>
			</div>
			<div class="demo-container" style="height:280px">
                        <div id="chartCustomerRecommendation" class="demo-placeholder"></div>
                      </div>
		</div>
	</div>
		
</div>
<script>
var dataCustomerRecommendations = ${customerRecommendationByMonths};
</script>