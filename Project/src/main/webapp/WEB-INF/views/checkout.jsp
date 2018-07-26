<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${sessionScope.customerid == null}">
	<spring:eval
		expression="T(com.elephantry.util.JspHelper).findCustomer('${pageContext.request.userPrincipal.name}')"
		var="customer"></spring:eval>
	<c:set var="customerid" value="${customer.customerId}" scope="session" />
	<c:set var="customername"
		value='${customer.firstName} ${ customer.lastName}' scope="session" />
</c:if>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<a class="navbar-brand" href="<c:url value='/' />"> <span>ELEPHANTRY</span> <!-- <img src="<c:url value='/resources/images/Logo_3_Elephantry.png' />" > -->
			</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav navbar-right">
				<li class="login"><div class="profile_info" style=" padding-top: 0px;">
						<span>Welcome, </span>${sessionScope["customername"]}

					</div></li>
			</ul>

		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid-fluid -->
</nav>
<div class="row" style="margin-top: 30px">
	<div class="col-md-8 col-md-offset-2 ">
		<div class="col-xs-12 col-sm-8">
			<div
				class="animated fadeInRight  box-shadow panel-body panel panel-default centerField"
				style="margin-left: 10px">
				<div class="form-margin centerField ">
					<h1>
						<span class="packageName"></span>
					</h1>
					<br> <br>
					<div class="row">
						<div class="col-xs-2">
							<h2>Product</h2>
						</div>
						<div class="col-xs-4">
							<h2>Time</h2>
						</div>
						<div class="col-xs-3">
							<h2>Price</h2>
						</div>
						<div class="col-xs-2 col-xs-offset-1">
							<h2>Total</h2>
						</div>
					</div>
					<hr>
					<div class="row">
						<div class="col-xs-2 productName"></div>
						<div class="col-xs-4">
							<div class="productTimeMargin">
								<select class="form-control productTime" name="productTime"
									id="productTime">
									<option value="1" selected="selected">1 Month</option>
									<option value="3">3 Month</option>
									<option value="6">6 Month</option>
									<option value="12">12 Month</option>
								</select>
							</div>
						</div>
						<div class="col-xs-3 productPrice"></div>
						<div class="col-xs-2 col-xs-offset-1 productTotal"></div>
					</div>
					<br>
					<div class="row text-right">

						<button type="button" class="btn btn-info btnChangePackage"
							data-toggle="modal" data-target=".bs-example-modal-lg">
							<span><i class="fa fa-refresh"></i></span> Change package
						</button>
					</div>
					<br>
					<!-- 
					<div class="row">
						<div class="form-group">
							<div class="col-sm-4">
								<label class="control-label col-xs-12">Promotion code: </label>
							</div>
							<div class="col-sm-4">
								<input class="form-control">
							</div>
							<div class="col-sm-4"></div>
						</div>

					</div> -->
					<br> <br>

				</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-4  ">
			<div
				class="animated fadeInRight  box-shadow panel-body panel panel-default centerField"
				style="margin-right: 10px">
				<div class="form-margin  ">
					<h2>Summary order  </h2>
					<hr>
					<div class="totalOrder"></div>
					<br>
					<div class="row">
						<form action="${pageContext.request.contextPath}/payment/create"
							method="post">
							<input type="hidden" id="timePackage" name="timePackage" value="" >
							<input type="hidden" id="totalPayment" name="totalPayment" value="" >
							<button type="submit"  class=" btn btn-success .col-md-4 btn-lg" name="" value="" ><i class="fa fa-paypal"></i>&nbsp Payment Now</button> 
						</form>
						<!-- 
						<button type="submit" class=" btn btn-success .col-md-4">
							<span><i class="fa fa-check"></i></span> Payment
						</button> -->
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade bs-example-modal-lg" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Our Package</h4>
					</div>
					<div class="modal-body">
						<div class="x_content">
							<div class="row">

								<div class="col-md-12" id="pricingTable"></div>
							</div>
						</div>
						<div class="modal-footer">
							<!-- 
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary btnSaveChange">Save
								changes</button> -->
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	var packageIdFromCheckout = ${packageId}; 
</script>