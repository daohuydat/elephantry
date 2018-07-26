<%@page import="com.elephantry.util.JspHelper"%>
<%@page import="com.elephantry.entity.Notification"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${sessionScope.userid == null}">
	<spring:eval
		expression="T(com.elephantry.util.JspHelper).findUser('${pageContext.request.userPrincipal.name}')"
		var="user"></spring:eval>
	<c:set var="userid" value="${user.userId}" scope="session" />
	<c:set var="userrole" value="${user.roleId}" scope="session" />
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
<div class="col-md-3 left_col">
	<div class="left_col scroll-view">
		<div class="navbar nav_title" style="border: 0;">
			<a href="<c:url value='/${language}/admin/home/index' />"
				class="site_title"><i class="fa fa-paw"></i> <span>Elephantry
					!</span></a>
		</div>

		<div class="clearfix"></div>

		<!-- menu profile quick info -->
		<div class="profile clearfix">
			<div class="profile_pic">
				<img src="<c:url value='/resources/images/avatar.png' />" alt="..."
					class="img-circle profile_img">
			</div>
			<div class="profile_info">
				<span>${translator.get('welcome')}</span>
				<div data-toggle="tooltip" data-placement="bottom"
					title="${pageContext.request.userPrincipal.name}">
					<h2
						style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">${pageContext.request.userPrincipal.name}</h2>
				</div>

			</div>
		</div>
		<!-- /menu profile quick info -->

		<br />

		<!-- sidebar menu -->
		<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">

			<div class="menu_section">
				<h3>${translator.get('adminDashboard')}</h3>
				<ul class="nav side-menu">
					<li><a href="<c:url value='/${language}/admin/home/index' />"><i
							class="fa fa-home"></i>${translator.get('dashboard')} <span
							class="fa fa-chevron-right"></span></a></li>
					<c:if test="${'ROLE_ROOT' eq sessionScope.userrole}">
						<li><a><i class="fa fa-edit"></i>
								${translator.get('queueManagement')} <span
								class="fa fa-chevron-right"></span></a>
							<ul class="nav child_menu">
								<li><a
									href="<c:url value='/${language}/admin/queue/info' />">${translator.get('queueInformation')}</a></li>
								<li><a
									href="<c:url value='/${language}/admin/queue/setting' />">${translator.get('queueSetting')}</a></li>
							</ul></li>
					</c:if>
					<c:if test="${'ROLE_ADMIN' eq sessionScope.userrole}">
						<li><a href="<c:url value='/${language}/admin/queue/info' />"><i
								class="fa fa-edit"></i> ${translator.get('queueInformation')}<span
								class="fa fa-chevron-right"></span></a>
							<ul class="nav child_menu">
							</ul></li>
					</c:if>

					<li><a><i class="fa fa-list-ul"></i>
							${translator.get('customerManagement')} <span
							class="fa fa-chevron-right"></span></a>
						<ul class="nav child_menu">

							<li><a
								href="<c:url value='/${language}/admin/customerManagement/view' />">${translator.get('viewCustomers')}</a></li>

						</ul></li>
					<c:if test="${'ROLE_ROOT' eq sessionScope.userrole}">
						<li><a><i class="fa fa-list-ul"></i>
								${translator.get('adminManagement')} <span
								class="fa fa-chevron-right"></span></a>
							<ul class="nav child_menu">
								<li><a
									href="<c:url value='/${language}/admin/adminManagement/view' />">${translator.get('viewAdmin')}</a></li>
								<li><a
									href="<c:url value='/${language}/admin/adminManagement/add' />">${translator.get('newAdmin')}</a></li>
							</ul></li>
					</c:if>
					<li><a href="<c:url value='/${language}/admin/chart/view' />"><i class="fa fa-line-chart"></i> <c:out value="${translator.get('analysisReport')}" /> <span class="fa fa-chevron-right"></span></a>
						</li>
					<li><a href="<c:url value='/${language}/admin/hadoop/info' />"><i
							class="fa fa-gears"></i>Hadoop engine<span
							class="fa fa-chevron-right"></span></a></li>
					<li><a
						href="<c:url value='/${language}/admin/home/feedback/view' />"><i
							class="fa fa-comments"></i>${translator.get('viewFeedback')}<span
							class="fa fa-chevron-right"></span></a></li>
					<li><a
						href="<c:url value='/${language}/admin/home/changePasswordAdmin' />"><i
							class="fa fa-exchange"></i>${translator.get('changePassword')}<span
							class="fa fa-chevron-right"></span></a></li>
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
						src="<c:url value='/resources/images/avatar.png' />" alt="">${sessionScope["username"]}
						<span class=" fa fa-angle-down"></span>
				</a>
					<ul class="dropdown-menu dropdown-usermenu pull-right">
						<!-- <li><a href="#"> ${translator.get('profile')}</a></li> -->
						<li><a href="<c:url value='/${language}/account/signout' />"><i
								class="fa fa-sign-out pull-right"></i>
								${translator.get('signOut')}</a></li>
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