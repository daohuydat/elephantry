<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section id="social" class="social">
	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="social-wrapper">
				<div class="col-md-6">
					<div class="social-icon">
						<a href="#"><i class="fa fa-facebook"></i></a> 
						<a href="#"><i class="fa fa-twitter"></i></a> 
						<a href="#"><i class="fa fa-google-plus"></i></a> 
						<a href="#"><i class="fa fa-linkedin"></i></a>
					</div>
				</div>
				<div class="col-md-6">
					<div class="social-contact">
						<a href="#"><i class="fa fa-phone"></i>+84 1663023295</a> <a
							href="mailto:elephantry.corp@gmail.com"><i
							class="fa fa-envelope"></i>elephantry.corp@gmail.com</a> 
							<a href="<c:url value='/vi/index' />"
					 style="margin-right: 0px !important;"><img
							alt="" style="width: 25px; height: 25px;"
							src="<c:url value='/resources/images/VN.png' />"></a> <a
							href="<c:url value='/en/index' />" ><img alt=""
							style="width: 25px; height: 25px"
							src="<c:url value='/resources/images/EN.png' />"></a>


					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</section>

<!-- Home -->
<nav class="navbar navbar-default ">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value='/' />"><img
				src="<c:url value='/resources/assets/images/Logo_3_Elephantry.png' />"
				height="90" width="180" /></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav navbar-right">
				<!-- <li class="active"><a href="#home">Home</a></li> -->
				<li><a href="#whyus">Why Us</a></li>
				<li><a href="#introduce">Introduce</a></li>
				<li><a href="#services">Services</a></li>
				<li><a href="#ourteam">About Us</a></li>
				<li><a href="#contact">Contact</a></li>
				<c:if test="${sessionScope.userid == null}">
					<li class="login"><a
						href="<c:url value='/${language}/account/signin' />">Sign In</a></li>
				</c:if>
				<c:if test="${sessionScope.userid != null}">
					<li class="login"><a
						href="<c:url value='/${language}/account/success' />">Dash board</a></li>
				</c:if>
				<li class="login"><a href="#services">Sign Up</a></li>
			</ul>

		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>

<header id="home" class="home">
	<div class="overlay-fluid-block">
		<div class="container text-center">
			<div class="row">
				<div class="home-wrapper">
					<div class="col-md-10 col-md-offset-1">
						<div class="home-content">

							<h1 style="color: #e1b541" class=" animated fadeInUp">${translator.get('elepahntrySystem')}</h1>
							<p class=" animated fadeInUp">${translator.get('mainDescription')}</p>
							<a href="#whyus" class="btn btn-default">${translator.get('findOut')}</a>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</header>

<!-- Why us -->
<section id="whyus" class="features sections">
	<div class="container-fluid">
		<div class="row">
			<div class="main_service2">
				<div class="head_title text-center">
					<h2 STYLE="COLOR: #e1b541">${translator.get('mainWhyus')}</h2>
					<p>________________________</p>
				</div>

				<div>
					<div class="service_content">
						<div class="row">
							<div class="col-md-4">
								<div class="single_service2" style="text-align: center;">
									<div class="single_service_top">
										<img
											src="<c:url value='/resources/assets/images/computer.png' />"
											height="250" width="280" alt="" />
									</div>
									<h5>
										<b>Recommendation as a service</b>
									</h5>
									<p style="text-align: justify;">${translator.get('whyusOnescript')}</p>
								</div>
							</div>

							<div class="col-md-4">
								<div class="single_service2" style="text-align: center;">
									<div class="single_service_top">
										<img
											src="<c:url value='/resources/assets/images/browser.png' />"
											height="250" width="280" alt="" />
									</div>
									<h5>
										<b>${translator.get('whyusTwo')}</b>
									</h5>
									<p style="text-align: justify;">${translator.get('whyusTwoscript')}</p>
								</div>
							</div>
							<div class="col-md-4">
								<div class="single_service2" style="text-align: center;">
									<div class="single_service_top">
										<img
											src="<c:url value='/resources/assets/images/media.png' />"
											width="280" alt="" />
									</div>

									<h5>
										<b>${translator.get('whyusThree')}</b>
									</h5>
									<p style="text-align: justify;">${translator.get('whyusThreescript')}</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<div class="single_service2_bottom" style="text-align: center;">
									<div class="single_service_top">
										<img
											src="<c:url value='/resources/assets/images/laptop.png' />"
											height="250" width="280" alt="" />
									</div>
									<h5>
										<b>User Dashboard</b>
									</h5>
									<p style="text-align: justify;">${translator.get('whyusFourscript')}</p>
								</div>
							</div>
							<div class="col-md-4">
								<div class="single_service2_bottom" style="text-align: center;">
									<div class="single_service_top">
										<img
											src="<c:url value='/resources/assets/images/server.png' />"
											height="250" width="280" alt="" />
									</div>
									<h5>
										<b>${translator.get('whyusFive')}</b>
									</h5>
									<p style="text-align: justify;">${translator.get('whyusFivescript')}</p>
								</div>
							</div>
							<div class="col-md-4">
								<div class="single_service2_bottom" style="text-align: center;">
									<div class="single_service_top">
										<img
											src="<c:url value='/resources/assets/images/travel.png' />"
											height="250" width="280" alt="" />
									</div>

									<h5>
										<b>${translator.get('whyusSix')}</b>
									</h5>
									<p style="text-align: justify;">${translator.get('whyusSixscript')}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- End Why us -->

<!-- Start Introduce -->
<section id="introduce" class="service2 sections lightbg">
	<div class="container-fluid">
		<div class="row">
			<div class="main_features_content2">

				<div class="head_title text-center">
					<h2 STYLE="COLOR: #e1b541">WE WERE BORN FOR EACH OTHER</h2>
					<p>________________________</p>
				</div>

				<div class="col-md-10 col-md-offset-1">
					<div class="row">
						<div class="col-sm-6">
							<div class="text-center">
								<img
									style="width: 70%; height: 70%; padding: 5px; border: solid 1px #CCC;"
									src="<c:url value='/resources/assets/images/engine.png' />"
									alt="" />
							</div>
						</div>

						<div class="col-sm-6 margin-top-10">
							<div class="single_features_right ">
								<h2 STYLE="COLOR: #e1b541" class="text-center">${translator.get('aboutElephantry')}</h2>
								<p>${translator.get('aboutScript1')}
									<br><p>${translator.get('aboutScript2')}</p>

								<!-- <div class="features_buttom">
                                    <a href="" class="btn btn-default">Read More</a>
                                </div> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--End of Features 2 Section -->
<section id="features" class="features sections">
	<div class="container">
		<div class="row">
			<div class="main_features_content2"></div>
		</div>
	</div>
</section>

<!--End introduce-->



<!-- Start Services -->
<section id="services" class="price sections">


	<div class="head_title text-center">
		<h1 STYLE="COLOR: #e1b541">${translator.get('packagesYouneed')}</h1>
		<p>${translator.get('packagesScript')}</p>
	</div>
	<!-- Example row of columns -->
	<div class="cd-pricing-container cd-has-margins"
		style="border: color:red">

		<ul class="cd-pricing-list cd-bounce-invert">
			<li>
				<ul class="cd-pricing-wrapper">
					<li data-type="monthly" class="is-visible">
						<header class="cd-pricing-header">
							<h2>Free Trial</h2>

							<div class="cd-price">
								<span class="cd-currency">Free</span> <span class="cd-value"></span>
							</div>
						</header> <!-- .cd-pricing-header -->

						<div class="cd-pricing-body">
							<ul class="cd-pricing-features">
								<li><em><i class="fa fa-check-circle"></i></em>1
									recommendation</li>
								<li><em><i class="fa fa-check-circle"></i></em>Trial
									priority</li>
								<li><em></em></li>
							</ul>
						</div> <!-- .cd-pricing-body --> <footer class="cd-pricing-footer">
							<a class="cd-select"
								href="<c:url value='/${language}/account/signup?p=freetrial' />">Register
								a free account</a>
						</footer> <!-- .cd-pricing-footer -->
					</li>

				</ul> <!-- .cd-pricing-wrapper -->
			</li>

			<li>
				<ul class="cd-pricing-wrapper">
					<li data-type="monthly" class="is-visible"><header
							class="cd-pricing-header_blue">
							<h2>Standard</h2>
							<div class="cd-price">
								<span class="cd-currency_blue">$</span> <span
									class="cd-value_blue">100</span> <span class="cd-duration_blue">month</span>
							</div>
						</header> <!-- .cd-pricing-header -->

						<div class="cd-pricing-body-blue">
							<ul class="cd-pricing-features">
								<li><em><i class="fa fa-check-circle"></i></em>30
									recommendations/month</li>
								<li><em><i class="fa fa-check-circle"></i></em>Standard
									priority</li>
								<li><em><i class="fa fa-check-circle"></i></em>Support 24/7</li>
							</ul>
						</div> <!-- .cd-pricing-body --> <footer class="cd-pricing-footer_blue ">
							<a class="cd-select"
								href="<c:url value='/${language}/account/signup?p=standard' />">Register
								from $100/month</a>
						</footer> <!-- .cd-pricing-footer --></li>


				</ul> <!-- .cd-pricing-wrapper -->
			</li>

			<li>
				<ul class="cd-pricing-wrapper">
					<li data-type="monthly" class="is-visible"><header
							class="cd-pricing-header_royal">
							<h2>Premium</h2>

							<div class="cd-price">
								<span class="cd-currency_royal">$</span> <span
									class="cd-value_royal">200</span> <span
									class="cd-duration_royal">month</span>
							</div>
						</header> <!-- .cd-pricing-header -->

						<div class="cd-pricing-body-royal">
							<ul class="cd-pricing-features">
								<li><em><i class="fa fa-check-circle"></i></em>Unlimited
									recommendations/month</li>
								<li><em><i class="fa fa-check-circle"></i></em>Premium
									priority</li>
								<li><em><i class="fa fa-check-circle"></i></em>Professional
									support 24/7</li>

							</ul>
						</div> <!-- .cd-pricing-body --> <footer class="cd-pricing-footer_royal">
							<a class="cd-select"
								href="<c:url value='/${language}/account/signup?p=premium' />">Register
								from $200/month</a>
						</footer> <!-- .cd-pricing-footer --></li>


				</ul> <!-- .cd-pricing-wrapper -->
			</li>
		</ul>
		<!-- .cd-pricing-list -->
	</div>
	<!-- .cd-pricing-container -->

</section>
<!-- End Services -->

<!--Start about us-->


<section id="ourteam" class="service2 sections lightbg">
	<div class="container-fluid">
		<div class="row">
			<div class="main_features_content2">

				<div class="head_title text-center">
					<h2 STYLE="COLOR: #e1b541">BAMBOO TEAM</h2>
					<p>${translator.get('teamScript')}</p>
				</div>

			</div>

			<div class="container-fluid">
				<div class="service_content">
					<div class="row ">
						<div class="text-center ">
							<div class="single_service2_team">
								<div class="single_service_top">
									<img src="<c:url value='/resources/images/SonNT5.jpg' />"
										class="" height="250" width="150" alt=""
										style="border-radius: 5%" /> 
								</div>

								<h5 STYLE="COLOR: #e1b541">
									<b>Son Ngo Tung</b>
								</h5>
								<p>Married, Lecture, Teacher</p>

							</div>
						</div>
					</div>
					<div class="row text-center ">
						<div class="col-md-1"></div>
						<div class="col-md-2">
							<div class="single_service2_team">
								<div class="single_service_top">

									<img src="<c:url value='/resources/images/TrungNQ.jpg' />"
										class="img-circle" height="150" width="150" alt="" />
								</div>
								<h5 STYLE="COLOR: #e1b541">
									<b>Trung Nguyen Quang</b>
								</h5>
								<p style="color: green">Beautiful, Teamlead, Research</p>
							</div>
						</div>

						<div class="col-md-2">
							<div class="single_service2_team">
								<div class="single_service_top">

									<img src="<c:url value='/resources/images/DatDH.jpg' />"
										class="img-circle" height="150" width="150" alt="" />
								</div>
								<h5 STYLE="COLOR: #e1b541">
									<b>Dat Dao Huy</b>
								</h5>
								<p>Urly, Engineer, Computing</p>
							</div>
						</div>
						<div class="col-md-2">
							<div class="single_service2_team">
								<div class="single_service_top">
									<img src="<c:url value='/resources/images/DongNA.jpg' />"
										class="img-circle" height="150" width="150" alt="" />
								</div>

								<h5 STYLE="COLOR: #e1b541">
									<b>Dong Nguyen A</b>
								</h5>
								<p>Talkative, Develop, computing</p>
							</div>
						</div>
						<div class="col-md-2">
							<div class="single_service2_team">
								<div class="single_service_top">
									<img src="<c:url value='/resources/images/HieuNM.jpg' />"
										class="img-circle" height="150" width="150" alt="" />
								</div>
								<h5 STYLE="COLOR: #e1b541">
									<b>Hieu Nguyen Manh</b>
								</h5>
								<p>Taciturn, Develop, Steaming</p>
							</div>
						</div>
						<div class="col-md-2">
							<div class="single_service2_team">
								<div class="single_service_top">
									<img src="<c:url value='/resources/images/DoPK.jpg' />"
										class="img-circle" height="150" width="150" alt="" />
								</div>
								<h5 STYLE="COLOR: #e1b541">
									<b>Do Pham Khac</b>
								</h5>
								<p>Dissipated, Design UI, Research</p>
							</div>
						</div>
						<div class="col-md-1"></div>
					</div>
				</div>
			</div>

		</div>
	</div>
</section>
<!--End of Features 2 Section -->

<section id="features" class="features sections">
	<div class="container">
		<div class="row">
			<div class="main_features_content2"></div>
		</div>
	</div>
</section>

<!--End About us-->



<!-- Start Contact -->
<section id="contact" class="contact sections">
	<div class="container-fluid">
		<div class="row">
			<div class="main_contact whitebackground">
				<div class="head_title text-center">
					<h2 STYLE="COLOR: #e1b541">${translator.get('getIn')}</h2>
					<p>${translator.get('getInscript')}</p>
				</div>
				<div class="contact_content">
					<div class="row">
						<div class="col-md-6">
							<div class="single_left_contact">
								<form action="${pageContext.request.contextPath}/sendUsEmail"
									id="formid" method="post">

									<div class="form-group">
										<input type="text" class="form-control" name="fullName"
											placeholder="Full name" required>
									</div>

									<div class="form-group">
										<input type="email" class="form-control" name="email"
											placeholder="Email" required>
									</div>


									<div class="form-group">
										<textarea class="form-control" name="message" rows="8"
											placeholder="Message"></textarea>
									</div>

									<div class="center-content">
										<input type="submit" value="Send us an email"
											class="btn btn-default">
									</div>
								</form>
							</div>
						</div>
						<div class="col-md-6">
							<div class="single_right_contact">


								<div class="contact_address ">
									<span>FPT University, Hoa Lac High-tech Park,</span> 
									<span>Thach That, Ha Noi, Viet Nam.</span> 
									<span class="margin-top-20">+84 166 302 3295</span> 
									<span>+84 915 402 033</span>
								</div>

								<div class="contact_socail_bookmark">
									<a href=""><i class="fa fa-facebook"></i></a> <a href=""><i
										class="fa fa-twitter"></i></a> <a href=""><i
										class="fa fa-google"></i></a>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- End of Contact Section -->

