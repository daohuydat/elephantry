<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<tiles:importAttribute name="stylesheets" />
<tiles:importAttribute name="javascripts" />

<!doctype html>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="icon" 
      type="image/png" 
      href="<c:url value='/resources/images/elephantfavicon.png' />">
<!-- Bootstrap -->
<link
	href="<c:url value='/resources/vendors/bootstrap/dist/css/bootstrap.min.css' />"
	rel="stylesheet" />
<!-- Font Awesome -->
<link
	href="<c:url value='/resources/vendors/font-awesome/css/font-awesome.min.css' />"
	rel="stylesheet" />

<!-- NProgress -->
<link
	href="<c:url value='/resources/vendors/nprogress/nprogress.css' />"
	rel="stylesheet" />
<!-- iCheck -->
<link
	href="<c:url value='/resources/vendors/iCheck/skins/flat/green.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/jqvmap/dist/jqvmap.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/bootstrap-daterangepicker/daterangepicker.css' />"
	rel="stylesheet" />
<!-- Custom Theme Style -->
<link href="<c:url value='/resources/build/css/custom.min.css' />"
	rel="stylesheet" />
	<link href="<c:url value='/resources/vendors/animate.css/animate.min.css' />"
	rel="stylesheet" />
<!-- Custom css here -->
<c:forEach var="css" items="${stylesheets}">
	<link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
</c:forEach>

</head>
<body class="nav-md">
	<div class="container body">
		<div class="main_container">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="body" />
			<tiles:insertAttribute name="footer" />
		</div>
	</div>


	<script
		src="<c:url value='/resources/vendors/jquery/dist/jquery.min.js' />"></script>
	<!-- Bootstrap -->
	<script
		src="<c:url value='/resources/vendors/bootstrap/dist/js/bootstrap.min.js' />"></script>

	<!-- FastClick -->
	<script
		src="<c:url value='/resources/vendors/fastclick/lib/fastclick.js' />"></script>
	<!-- NProgress -->
	<script
		src="<c:url value='/resources/vendors/nprogress/nprogress.js' />"></script>
	<!-- Chart.js -->
	<script
		src="<c:url value='/resources/vendors/Chart.js/dist/Chart.min.js' />"></script>
	<!-- gauge.js -->
	<script
		src="<c:url value='/resources/vendors/gauge.js/dist/gauge.min.js' />"></script>
	<!-- bootstrap-progressbar -->
	<script
		src="<c:url value='/resources/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js' />"></script>
	<!-- iCheck -->
	<script src="<c:url value='/resources/vendors/iCheck/icheck.min.js' />"></script>
	<!-- Skycons -->
	<script src="<c:url value='/resources/vendors/skycons/skycons.js' />"></script>
	<!-- Flot -->
	<script src="<c:url value='/resources/vendors/Flot/jquery.flot.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/Flot/jquery.flot.pie.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/Flot/jquery.flot.time.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/Flot/jquery.flot.stack.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/Flot/jquery.flot.resize.js' />"></script>
	<!-- Flot plugins -->
	<script
		src="<c:url value='/resources/vendors/flot.orderbars/js/jquery.flot.orderBars.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/flot-spline/js/jquery.flot.spline.min.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/flot.curvedlines/curvedLines.js' />"></script>
	<!-- DateJS -->
	<script src="<c:url value='/resources/vendors/DateJS/build/date.js' />"></script>
	<!-- JQVMap -->
	<script
		src="<c:url value='/resources/vendors/jqvmap/dist/jquery.vmap.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/jqvmap/dist/maps/jquery.vmap.world.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js' />"></script>
	<!-- bootstrap-daterangepicker -->
	<script
		src="<c:url value='/resources/vendors/moment/min/moment.min.js' />"></script>
	<script
		src="<c:url value='/resources/vendors/bootstrap-daterangepicker/daterangepicker.js' />"></script>

	<script type="text/javascript">
		var rootBaseUrl = '${pageContext.request.contextPath}';
		console.log("rootBaseUrl:" + rootBaseUrl);
	</script>
	<!-- Custom Theme Scripts -->
	<script src="<c:url value='/resources/build/js/custom.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap-notify.min.js' />"></script>
	<!-- Custom javascript here -->

	<c:forEach var="script" items="${javascripts}">
		<script src="<c:url value="${script}"/>"></script>
	</c:forEach>
	<script>
		var href = window.location.href;
		var areaIndex = href.indexOf('/admin');
		
		document.getElementById("vi-lang-anchor").href = rootBaseUrl + '/vi' + href.substring(areaIndex);
		document.getElementById("en-lang-anchor").href = rootBaseUrl + '/en' + href.substring(areaIndex);
	</script>
</body>
</html>