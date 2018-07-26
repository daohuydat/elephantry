<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="right_col" role="main">
	<div class="">
		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
						<h3>Upgrade package</h3>
						<ul class="nav navbar-right panel_toolbox">
							<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-expanded="false"><i
									class="fa fa-wrench"></i></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="#">Settings 1</a></li>
									<li><a href="#">Settings 2</a></li>
								</ul></li>
						</ul>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
						<!-- 
						<h2 style="color: red" id="notification">${notification}</h2>
						 -->
						<br>
						<div class="col-md-3">
							<div class="row">
								<h2>
									Your package: <span>${packageName}</span>
								</h2>
							</div>
							<div class="row">
								<h2>
									Expired credit date: <span>${expiredCredit}</span>
								</h2>
							</div>
							<div class="row">
								<h2>
									Days Remaining: <span>${dayExpiredCredit}</span>
								</h2>
							</div>
						</div>
						<div class="col-md-9">
							<div class="col-md-4">
								<div class="pricing">
									<div class="title">
										<h2>Free trial</h2>
										<h1>0$</h1>
										<span>Monthly</span>
									</div>
									<div class="x_content">
										<div class="">
											<div class="pricing_features">
												<ul class="list-unstyled text-left">
													<li><i class='fa fa-check text-success'></i><strong>1
															recommendation</strong></li>
													<li><i class='fa fa-check text-success'></i> <strong>Priority:
															Trial</strong></li>
												</ul>
											</div>
										</div>
										<div class="pricing_footer">
											<button type="button" class="btn btn-success btn-block"
												disabled="disabled">Upgrade</button>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="pricing">
									<div class="title">
										<h2>Standard</h2>
										<h1>100$</h1>
										<span>Monthly</span>
									</div>
									<div class="x_content">
										<div class="">
											<div class="pricing_features">
												<ul class="list-unstyled text-left">
													<li><i class='fa fa-check text-success'></i><strong>30
															recommendations</strong></li>
													<li><i class='fa fa-check text-success'></i> <strong>Priority:
															Standard</strong></li>
													<li><i class='fa fa-check text-success'></i><strong>Support
															24/7</strong></li>
												</ul>
											</div>
										</div>
										<div class="pricing_footer">
											<button type="button" id="btnStandard"
												class=" btn btn-success btn-block" data-toggle="modal"
												data-target=".bs-example-modal-lg" value="upgrade">Upgrade
											</button>

										</div>
									</div>
								</div>
							</div>
							<div class="col-md-4">
								<div class="pricing">
									<div class="title">
										<h2>Premium</h2>
										<h1>200$</h1>
										<span>Monthly</span>
									</div>
									<div class="x_content">
										<div class="">
											<div class="pricing_features">
												<ul class="list-unstyled text-left">
													<li><i class='fa fa-check text-success'></i><strong>Unlimited
															recommendations</strong></li>
													<li><i class='fa fa-check text-success'></i> <strong>Priority:
															Premium</strong></li>
													<li><i class='fa fa-check text-success'></i><strong>
															Professional support 24/7</strong></li>
												</ul>
											</div>
										</div>
										<div class="pricing_footer">
											<button type="button" id="btnPremium" data-toggle="modal"
												data-target=".bs-example-modal-lg"
												class="btn btn-success btn-block" value="upgrade">Upgrade
											</button>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
	aria-hidden="true">
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
							<div class="form-margin centerField ">
								<h1>
									<span class="packageName"></span>
								</h1>
								<br>
								<div class="row" id="upgradeTranfer" style="display: none;">
									<div>
										<h2 style="color: red;">Your days remaining is
											${dayExpiredCredit} will be transfer to
											${daysRemainingTranfer} days with Premium account!</h2>
										<h2 style="color: red;">You need to transfer first. If
											you want to buy more, you can extend later thanks!</h2>
									</div>
									<br>
									<div style="text-align: right;">
										<button type="submit" id="btnTransfer"
											class=" btn btn-success .col-md-4 btn-lg" name="" value="">
											<i class="fa fa-exchange"></i>&nbsp Change Now
										</button>
									</div>
								</div>
								<br>
								<div id="normalUpgrade">
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
										<div id="productTotal" class="col-xs-2 col-xs-offset-1 "></div>
									</div>
									<br>
									<div class="row">
										<div id="expiredDayAfter"></div>
									</div>
									<br>
									<div class="row text-right">
										<form
											action="${pageContext.request.contextPath}/payment/create"
											method="post">
											<input type="hidden" id="timePackage" name="timePackage"
												value=""> <input type="hidden" id="totalPayment"
												name="totalPayment" value="">
											<button type="submit"
												class=" btn btn-success .col-md-4 btn-lg" name="" value="">
												<i class="fa fa-paypal"></i>&nbsp Payment Now
											</button>
										</form>
									</div>
								</div>
								<br> <br> <br> <input type="hidden"
									id="checkUpgrade" value="${check}">
							</div>
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
<script>
	var daysTransfer = "${daysRemainingTranfer}";
	var lang = "${language}";
</script>