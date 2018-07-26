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
<div class="row" style="margin-top: 30px; text-align: center;">
	<div class="col-md-8 col-md-offset-2 ">
		<div class="col-xs-12 col-sm-12">
			<div
				class="animated fadeInRight  box-shadow panel-body panel panel-default centerField"
				style="margin-left: 10px">
				<h1>Thanks for believe and using our system !</h1>
				<br>
				<img alt=""
					src="<c:url value='/resources/images/thank-you-icon-3.png' />">
				<br>
				
				<div class="row text-right" style="margin-right: 50px">
					<a class="btn btn-success  btn-lg" href="/portal/home" > <span><i class="fa fa-mail-forward"></i></span> Go to portal now !</a>
				</div>
			</div>
		</div>
	</div>
</div>