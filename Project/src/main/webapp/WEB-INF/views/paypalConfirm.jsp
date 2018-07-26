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
				<li class="login"><div class="profile_info"
						style="padding-top: 0px;">
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
		<div class="col-xs-12 col-sm-12">
			<div
				class="animated fadeInRight  box-shadow panel-body panel panel-default centerField"
				style="margin-left: 10px">
				<div class="form-margin centerField " style="margin-left: 30px">
					<div>
						<h1>Confirm your payment</h1>
					</div>
					<div>
						<h2>Please check your payment information again after accept
							this payment!</h2>
					</div>
					<hr>
					<div>
						<h3>Billing information:</h3>
					</div>
					<div>
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>Product</th>
									<th>Time</th>
									<th>Price</th>
									<th>Total</th>
								</tr>
							</thead>
							<tbody>
								<tr style="color: red;">
									<td><label id="lbProduct">${lbProduct}</label></td>
									<td><label id="lbTime">${lbTime} month</label></td>
									<td><label id="lbPrice">${lbPrice}$ /month</label></td>
									<td><label id="lbTotal">${lbTotallabel} $</label></td>
								</tr>

							</tbody>
						</table>
						<!-- 
						<div class="row text-right" style="margin-right: 50px">
							<a class="btn btn-info" href="/elephantry/account/checkout" ><span><i class="fa fa-refresh"></i></span> Change Billing</a>
							
						</div> -->
					</div>
					<hr>
					<div>
						<h3>
							Payment method: <span style="color: red;">PayPal</span>
						</h3>
					</div>
					<hr>
					<div>
						<h3>Payer Info:</h3>
						<div style="margin-left: 30px">

							<h2>
								Payer name:<span style="color: red;"> ${payperFisrtName}&nbsp;${payperLastName}</span>
							</h2>
							<h2>
								Payer email:<span style="color: red;"> ${payperEmail}</span>
							</h2>

						</div>
					</div>
					<hr>
					<br> <br>
					<form
						action="${pageContext.request.contextPath}/payment/paypalExecute"
						method="post">
						<div style="display: none">
							<input type="text" name="paymentId" value="${paymentId}" /> <input
								type="text" name="payerID" value="${payerID}" /> <input
								type="text" name="token" value="${token}" />
						</div>
						<div class="row text-right" style="margin-right: 50px">

							<button type="submit"
								class="btn btn-success btnChangePackage btn-lg"
								data-toggle="modal" data-target=".bs-example-modal-lg ">
								<span><i class="fa fa-mail-forward"></i></span> Accept Payment
							</button>
						</div>

					</form>

				</div>
			</div>
		</div>
	</div>
</div>


