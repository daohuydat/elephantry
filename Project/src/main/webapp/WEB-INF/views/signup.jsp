<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


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
				<li class="login"><a href="<c:url value='/account/signin' />">Sign In</a></li>
			</ul>

		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid-fluid -->
</nav>


	<br>
	<div class="row">
		<div class="col-sm-6 animated fadeInLeft ">
			<div class="align-text-bottom text-center align-bottom"
				style="width: 100%">

				<!-- <div><img src=" <c:url value='/resources/images/engine.png' />" ></div> -->
				<div>
					<div class="text-center">
						<h1>Create your elephantry account</h1>
					</div>
					<br> <br>
					<div class="container">
						<div id="myCarousel" class="carousel slide" data-ride="carousel">
							<!-- Indicators -->
							<ol class="carousel-indicators">
								<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
								<li data-target="#myCarousel" data-slide-to="1"></li>
								<li data-target="#myCarousel" data-slide-to="2"></li>
							</ol>

							<!-- Wrapper for slides -->
							<div class="carousel-inner">

								<div class="item active">
									<img src="<c:url value='/resources/images/engineSignup.png' />">
								</div>
								<div class="item">
									<img
										src="<c:url value='/resources/images/Logo_3_Elephantry.png' />">
								</div>
								<div class="item">
									<img src="<c:url value='/resources/images/hadoopmahout.png' />">
								</div>

							</div>

							<!-- Left and right controls -->
							<a class="left carousel-control" href="#myCarousel"
								data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left"></span> <span
								class="sr-only">Previous</span>
							</a> <a class="right carousel-control" href="#myCarousel"
								data-slide="next"> <span
								class="glyphicon glyphicon-chevron-right"></span> <span
								class="sr-only">Next</span>
							</a>
						</div>
					</div>
					<br> <br>
					<h1>
						<i class="fa fa-paw"></i> Recommendation system!
					</h1>
					<p>©2017 All Rights Reserved. Recommendation system! is a best
						service recommend. Privacy and Terms</p>
				</div>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/account/signup"
			id="formSignup" method="post">
			<div class="col-sm-6  animated fadeInRight ">
				<div class="col-sm-10 panel-body panel panel-default box-shadow ">
					<div class="form-margin">
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12 ">Name <span style="color: red">*</span></label>

							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-6"> 
									<input type="text" name="firstName" id="firstName"  maxlength="500"
										class="form-control" placeholder="First" required="required">
								</div>
								<div class="col-sm-6">
									<input type="text" name="lastName" class="form-control" maxlength="500"
										placeholder="Last" required="required">
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12">Your username <span style="color: red">*</span></label>

							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-12">
									<input type="email" id="email" name="email" maxlength="500"
										class="form-control inputEmail" placeholder="Email"
										required="required">
									<!--  
										<label class="control-label col-xs-12 error" id="realEmailMessage" style="display: none;" >This email is not real</label>-->
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12">Create a password <span style="color: red">*</span></label>

							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-12">
									<input type="password" id="password" name="password" maxlength="500"
										class="form-control" required="required">
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12" required="required">Confirm
									your password <span style="color: red">*</span></label>

							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-12">
									<input type="password" name="password2" id="password2" maxlength="500"
										class="form-control" required="required">
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12">Your package</label>

							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-6">
								<input type="hidden" name="packageId" id="packageId" >
								<input class="form-control" name="package" id="ddlPackage" readonly="readonly">
								<input class="form-control" id="packageCode" value="${packagecode}" style="display: none;">
								</div>
								<div class="col-sm-6">
									<button type="button" class="btn btn-info btnChangePackage" data-toggle="modal"
										data-target=".bs-example-modal-lg">
										<span><i class="fa fa-refresh"></i></span> Change
									</button>
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12">Gender</label>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-12">
									<p>
										Male: <input type="radio" class="flat" name="gender"
											id="genderM" value="male" checked="checked" /> Female: <input
											type="radio" class="flat" name="gender" id="genderF"
											value="female" />
									</p>
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12">Phone <span style="color: red">*</span></label>

							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-6">
									<input type="text"  name="phone" class="form-control" maxlength="50"
										placeholder="" required="required">
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="control-label col-xs-12">Address <span style="color: red">*</span></label>

							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-12">
									<input type="text" name="address" class="form-control"
										required="required" placeholder="">
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label class="control-label col-xs-12">Country </label>

								</div>
								<div class="form-group">
									<select id="ddlCountry" class="form-control" name="country">
										<c:forEach var="country" items="${countries}">
											<option value="${country.countryId}">${country.countryName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<label class="control-label col-xs-12">Province</label>

								</div>
								<div class="form-group">
									<select id="ddlProvince" class="form-control" name="province">

									</select>
								</div>
							</div>
						</div>


						<br>
						<div class="row">
							<div class="form-group">
								<div class="col-sm-10"></div>
								<div class="col-sm-2">
									<button type="submit" class=" btn btn-success .col-md-4">
										<span><i class="fa fa-arrow-right"></i></span> Next
									</button>
								</div>
							</div>
						</div>
						<br>
					</div>
				</div>
			</div>
		</form>
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

								<div class="col-md-12" id="pricingTable">
								</div>
							</div>
					</div>
					<div class="modal-footer">
					<!-- 
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary btnSaveChange">Save
							changes</button> -->
					</div>

				</div>
			</div>
		</div>
	</div>
</div>
