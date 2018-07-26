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
						<h2>Change password</h2>
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
					<form
						id="formChangePassword">
						<div class="x_content">
							<br />
							<div class="form-horizontal form-label-left">

								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="first-name">Your old password <span style="color: red">*</span></label>
									<div class="col-md-5 col-sm-6 col-xs-12">
										<input id="oldPassword" name="oldPassword" maxlength="500"
											class="form-control col-md-7 col-xs-12" required="required"
											type="password" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12"
										for="last-name">Your new password <span style="color: red">*</span></label>
									<div class="col-md-5 col-sm-6 col-xs-12">
										<input id="newPassword"
											class="form-control col-md-7 col-xs-12" name="newPassword" maxlength="500"
											required="required" type="password" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3 col-sm-3 col-xs-12">Confirm
										your new password <span style="color: red">*</span></label>
									<div class="col-md-5 col-sm-6 col-xs-12">
										<input id="reNewPassword" name="reNewPassword"
											class="date-picker form-control col-md-7 col-xs-12" maxlength="500"
											required="required" type="password">
									</div>
								</div>
								<div class="ln_solid"></div>
								<div class="form-group">

									<div id="divEdit"
										class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
										<button id="btnChangePassword" type="button"
											class="btn btn-success btnChangePassword">
											<i class="fa fa-refresh"></i> Change password
										</button>

										<br />
									</div>
								</div>

								 
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>