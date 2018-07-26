<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="right_col" role="main">

	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>
					Customer Chart
				</h2>
				<div class="clearfix"></div>
			</div>
			<div class="demo-container">
                         <div id="customerChart" ></div>
                      </div>
		</div>
	</div>
	
	
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>
					Recommendation Summary Chart
				</h2>
				<div class="clearfix"></div>
			</div>
			<div class="demo-container" style="height:280px">
                        <div id="chartRecommendation" class="demo-placeholder"></div>
                      </div>
		</div>
	</div>


	<div class="col-md-6 col-sm-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>
					Package Summary Chart
				</h2>
				<div class="clearfix"></div>
			</div>
			<div class="x_content2">
				<div id="packageGraph" style="width: 100%; height: 300px;" data-package1="${package1}" data-package2="${package2}" data-package3="${package3}"></div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
<!-- 	End pie chart -->

	
	
</div>

<script>
var dataRecommendations = ${recommendationByMonths};
var dataCustomer = ${customerByMonths};
</script>