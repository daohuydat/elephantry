<%@page import="com.elephantry.util.JspHelper"%>
<%@page import="com.elephantry.entity.Notification"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${sessionScope.userid == null}">
	<spring:eval
		expression="T(com.elephantry.util.JspHelper).findCustomer('${pageContext.request.userPrincipal.name}')"
		var="customer"></spring:eval>
	<c:set var="userid" value="${customer.customerId}" scope="session" />
	<c:set var="customername"
		value='${customer.firstName} ${ customer.lastName}' scope="session" />
</c:if>
<%
	int userId = 0;
	if(session.getAttribute("userid") != null){
		try{
			userId = Integer.parseInt(session.getAttribute("userid").toString());
		}catch(Exception e){
			
		}
	}
	List<Notification> ln = JspHelper.findNotificationByCustomerId(userId);
	
%>
<style>
.noPaddingRight{
	padding-right: 0px !important;
}
</style>
<div class="col-md-3 left_col">
	<div class="left_col scroll-view">
		<div class="navbar nav_title" style="border: 0;">
			<a href="${pageContext.request.contextPath}/portal/home"
				class="site_title"><i class="fa fa-paw"></i> <span>Elephantry
					!</span></a>
		</div>

		<div class="clearfix"></div>

		<!-- menu profile quick info -->
		<div class="profile clearfix">
			<div class="profile_pic">
				<img src=" <c:url value='/resources/images/avatar.png' />" alt="..."
					class="img-circle profile_img">
			</div>
			<div class="profile_info">
				<span>Welcome,</span>
				<div data-toggle="tooltip" data-placement="bottom"
					title="<c:out value='${sessionScope["customername"]}' />">
					<h2
						style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"><c:out value='${sessionScope["customername"]}' /></h2>
				</div>
			</div>
		</div>
		<!-- /menu profile quick info -->

		<br />

		<!-- sidebar menu -->
		<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">

			<div class="menu_section">
				<h3>Customer dashboard</h3>
				<ul class="nav side-menu">
					<li><a class="noPaddingRight"
						href="<c:url value='/${language}/portal/home/index"' />"><i
							class="fa fa-home"></i> ${translator.get('dashboard')} <span
							class="fa fa-chevron-right"></span></a></li>
					
						<li><a class="noPaddingRight"
						href="<c:url value='/${language}/portal/file/upload' />"><i
							class="fa fa-comments"></i> ${translator.get('createRecommendation')}<span
							class="fa fa-chevron-right"></span></a></li>
							
					<li><a class="noPaddingRight"><i class="fa fa-edit"></i>${translator.get('manageRecommendation')} <span
							class="fa fa-chevron-right"></span></a>
						<ul class="nav child_menu">
							<li><a
								href="<c:url value='/${language}/portal/recommendation/waiting' />">
									Waiting Recommendations</a></li>
							<li><a
								href="<c:url value='/${language}/portal/recommendation/submitted' />">
									 Submitted Recommendations</a></li>
							<li><a
								href="<c:url value='/${language}/portal/recommendation/running' />">
									Running Recommendations</a></li>
							<li><a
								href="<c:url value='/${language}/portal/recommendation/completed' />">
									Completed Recommendations</a></li>
						</ul></li>
						
					<li><a class="noPaddingRight"
						href="<c:url value='/${language}/portal/recommendation/evaluation' />"><i
							class="fa fa-comments"></i>View Evaluation<span
							class="fa fa-chevron-right"></span></a></li>
							
					<li><a class="noPaddingRight" href="<c:url value='/${language}/portal/chart/view' />"><i class="fa fa-line-chart"></i> <c:out value="${translator.get('analysisReport')}" /> <span class="fa fa-chevron-right"></span></a>
						</li>
					<li><a class="noPaddingRight"
						href="<c:url value='/${language}/portal/home/feedback' />"><i
							class="fa fa-comments"></i>${translator.get('sendFeedback')}<span
							class="fa fa-chevron-right"></span></a></li>
					<li><a class="noPaddingRight" href="#"><i class="fa fa-gears"></i>${translator.get('yourAccount')}<span
							class="fa fa-chevron-right"></span></a>
						<ul class="nav child_menu">
							<li><a
								href="<c:url value='/${language}/portal/account/profile' />">${translator.get('profile')}</a></li>
							<li><a
								href="<c:url value='/${language}/portal/account/changePassword' />">${translator.get('changePassword')}</a></li>
								<li><a
								href="<c:url value='/${language}/portal/account/packageDetail' />">${translator.get('packageDetail')}</a></li>
							<!--  <li><a
								href="<c:url value='/account/checkout' />">Upgrade account</a></li>-->
						</ul></li>
				</ul>
			</div>

		</div>
		<!-- /sidebar menu -->

		<!-- /menu footer buttons -->
		<div class="sidebar-footer hidden-small">
			<a data-toggle="tooltip" data-placement="top" title="Settings"> <span
				class="glyphicon glyphicon-cog" aria-hidden="true"></span>
			</a> <a data-toggle="tooltip" data-placement="top" title="FullScreen">
				<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
			</a> <a data-toggle="tooltip" data-placement="top" title="Lock"> <span
				class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
			</a> <a data-toggle="tooltip" data-placement="top" title="Logout"
				href="<c:url value='/${language}/account/signout' />"> <span
				class="glyphicon glyphicon-off" aria-hidden="true"></span>
			</a>
		</div>
		<!-- /menu footer buttons -->
	</div>
</div>
<!-- top navigation -->
<div class="top_nav">
	<div class="nav_menu">
		<nav>
			<div class="nav toggle">
				<a id="menu_toggle"><i class="fa fa-bars"></i></a>
			</div>

			<ul class="nav navbar-nav navbar-right">
				<li class=""><a href="javascript:;"
					class="user-profile dropdown-toggle" data-toggle="dropdown"
					aria-expanded="false"> <img
						src="<c:url value='/resources/images/avatar.png' />" alt=""><c:out value='${sessionScope["customername"]}' />
						<span class=" fa fa-angle-down"></span>
				</a>
					<ul class="dropdown-menu dropdown-usermenu pull-right">
						<li><a
							href="<c:url value='/${language}/portal/account/profile' />">${translator.get('profile')}</a></li>
						<li><a href="<c:url value='/${language}/account/signout' />"><i
								class="fa fa-sign-out pull-right"></i>${translator.get('signOut')}</a></li>
					</ul></li>

				<li role="presentation" class="dropdown"><a href="javascript:;"
					class="dropdown-toggle info-number" data-toggle="dropdown"
					aria-expanded="false"> <i class="fa fa-envelope-o"></i> 
					<%if(ln.size()!=0){
					%>
					<span class="badge bg-green"><%=ln.size() %></span>
					<%
					}
					%>
				</a>
					<ul id="menu1" class="dropdown-menu list-unstyled msg_list"
						role="menu">
						<%
						int count =0;
						for(Notification n : ln){
							count++;
							if(count>5){
								break;
							}
						%>
							<li><a> 
							 <span class="message"><%=n.getMessage() %></span>
							 <span ><%=JspHelper.calculateDate(n.getCreatedTime()) %></span>
						</a></li>
						<%
						} 
						%>
						<li>
							<div class="text-center">
								<a> <strong>See All Alerts</strong> <i
									class="fa fa-angle-right"></i>
								</a>
							</div>
						</li>
					</ul></li>

				<li><a href="#" id="en-lang-anchor"><img alt=""
						style="width: 25px; height: 25px"
						src="<c:url value='/resources/images/EN.png' />"></a></li>
				<li><a href="#" id="vi-lang-anchor"><img alt=""
						style="width: 25px; height: 25px;"
						src="<c:url value='/resources/images/VN.png' />"></a></li>
			</ul>
		</nav>
	</div>
</div>
<!-- /top navigation -->