<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- page content -->
<div class="right_col" role="main">
	<div class="">




		<div class="col-md-3 col-xs-12 widget widget_tally_box">
			<div class="x_panel ui-ribbon-container ">
				<div class="x_title">
					<h2 class="text-center">Running map tasks</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div style="text-align: center;">
						<span id="map-chart" class="chart"
							data-percent="${mapTasks * 100 / maxMapTasks}"> <span
							class="percent"></span>
						</span>
					</div>
					<h3 id="map-chart-title" class="name_title">${mapTasks} / ${maxMapTasks}</h3>
				</div>
			</div>
		</div>

		<div class="col-md-3 col-xs-12 widget widget_tally_box">
			<div class="x_panel ui-ribbon-container ">
				<div class="x_title">
					<h2 class="text-center">Running reduce tasks</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div style="text-align: center;">
						<span id="reduce-chart" class="chart"
							data-percent="${reduceTasks * 100 / maxReduceTasks}"> <span
							class="percent"></span>
						</span>
					</div>
					<h3 id="reduce-chart-title" class="name_title">${reduceTasks} / ${maxReduceTasks}</h3>
				</div>
			</div>
		</div>

		<div class="col-md-3 col-xs-12 widget widget_tally_box">
			<div class="x_panel ui-ribbon-container ">
				<div class="x_title">
					<h2 class="text-center">Heap size</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div style="text-align: center;">
						<span id="heap-chart" class="chart" data-percent="${usedMemory * 100 / maxMemory}">
							<span class="percent"></span>
						</span>
					</div>
					<h3 id="heap-chart-title" class="name_title">${usedMemory} / ${maxMemory} MB</h3>
				</div>
			</div>
		</div>


	</div>
</div>