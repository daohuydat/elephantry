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
						<h2>Update your profile</h2>
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
						<br />
						<form id="formProfile" class="form-horizontal form-label-left">

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="first-name">First Name <span style="color: red">*</span></label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<input id="txtFirstName" name="txtFirstName"
										readonly="readonly" class="form-control col-md-7 col-xs-12" maxlength="500"
										required="required" value="<c:out value='${customer.firstName}' />" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="last-name">Last Name <span style="color: red">*</span></label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<input id="txtLastName" class="form-control col-md-7 col-xs-12"  maxlength="500"
										name="txtLastName" required="required" readonly="readonly"
										value=" <c:out value='${customer.lastName}' />" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12"
									for="last-name">Email </label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<input id="txtLastName" class="form-control col-md-7 col-xs-12"
										name="txtLastName" required="required" readonly="readonly"
										value="${email}" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Gender</label>
								<div class="col-md-6 col-sm-6 col-xs-12">
									<div id="gender" class="btn-group" data-toggle="buttons">
										<input id="ckGender" type="checkbox" disabled="disabled"
											${(customer.gender) eq 'male' ? 'checked': ''}
											data-toggle="toggle" data-on="Male" data-off="Female"
											data-onstyle="success" data-offstyle="info">
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Date
									Of Birth </label>

								<div class="col-md-2 col-sm-6 col-xs-12">
									<input id="datepicker"
										value="<fmt:formatDate pattern = "dd/MM/yyyy" value = "${customer.doB}" />"
										name="txtBirthday" data-inputmask="'mask': '99/99/9999'"
										readonly="readonly"
										class="date-picker form-control col-md-7 col-xs-12"
										type="text">
								</div>
							</div>
							<div class="form-group"></div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Website

								</label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<input id="txtWebsite" value="<c:out value='${customer.website}' />" 
										name="txtWebsite" readonly="readonly" maxlength="500"
										class="date-picker form-control col-md-7 col-xs-12"
										type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Company

								</label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<input id="txtCompany" value="<c:out value='${customer.company}' />"
										name="txtCompany" readonly="readonly" maxlength="500"
										class="date-picker form-control col-md-7 col-xs-12"
										type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Phone <span style="color: red">*</span>

								</label>
								<div class="col-md-2 col-sm-6 col-xs-12"> 
									<input id="txtPhone" value="<c:out value='${customer.phone}' />" name="txtPhone"
										class="date-picker form-control col-md-7 col-xs-12"  maxlength="50"
										readonly="readonly" required="required" type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Country

								</label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<select id="ddlCountry" class="form-control col-md-7 col-xs-12"
										disabled="disabled">
										<c:forEach var="country" items="${countries}">
											<option value="${country.countryId}">${country.countryName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Province

								</label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<select id="ddlProvince"
										class="form-control col-md-7 col-xs-12" disabled="disabled">
										<c:forEach var="province" items="${provinces}">
											<option value="${province.provinceId}">${province.provinceName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Address <span style="color: red">*</span>
 
								</label>
								<div class="col-md-5 col-sm-6 col-xs-12">
									<input id="txtAddress" value="<c:out value='${customer.address}' />" maxlength="500"
										class="date-picker form-control col-md-7 col-xs-12"
										readonly="readonly" required="required" type="text">
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3 col-sm-3 col-xs-12">Package

								</label>
								<div class="col-md-3 col-sm-6 col-xs-12">
									<input id="ddlPackage" class="form-control col-md-7 col-xs-12"
										disabled="disabled" value="${packageName}">
									<!-- 
									<select id="ddlPackage" class="form-control col-md-7 col-xs-12"
										disabled="disabled">
										<c:forEach var="package1" items="${packages}">
											<option value="${package1.packageId}">${package1.packageName}</option>
										</c:forEach>
									</select> -->
								</div>
								<div class="col-md-3 col-sm-6 col-xs-12">
									<a class="btn btn-round btn-info"
										href="<c:url value='/${language}/portal/account/packageDetail' />"><span><i
											class="fa fa-eye m-right-xs"></i></span> View more</a> <br />
								</div>
							</div>
							<div class="ln_solid"></div>
							<div class="form-group">
								<div id="divSave"
									class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 "
									style="display: none">

									<button id="btnSave" type="button" class="btn btn-success">Save
										changes</button>
									<!-- <button class="btn btn-primary" type="reset">Reset</button> -->
									<br />
								</div>
								<div id="divEdit"
									class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
									<button id="btnEdit" type="button" class="btn btn-success">
										<i class="fa fa-edit m-right-xs"></i> Edit
									</button>

									<br />
								</div>
							</div>

							 
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var customerId = ${customer.customerId};
	var packageId = ${customer.package1.packageId};
	var provinceId = ${customer.province.provinceId};
	var countryId = '${province.country.countryId}';
</script>
