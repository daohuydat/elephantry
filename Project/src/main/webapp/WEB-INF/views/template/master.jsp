<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
<%-- <link href="<c:url value='/resources/css/bootstrap-theme.min.css' />"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/sticky-footer.css' />"
	rel="stylesheet" />
<!-- Custom css here -->
<link
	href="<c:url value='/resources/vendors/bootstrap/dist/css/bootstrap.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/font-awesome/css/font-awesome.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/nprogress/nprogress.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/vendors/animate.css/animate.min.css' />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/build/css/custom.min.css' />"
	rel="stylesheet" />
	 --%>


<c:forEach var="css" items="${stylesheets}">
	<link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
</c:forEach>
</head>
<body class="login">
	<div class="">
		<tiles:insertAttribute name="header" />

		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer" />
	</div>

	


	<script src="<c:url value='/resources/js/jquery-3.2.1.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
	<script src="<c:url value='/resources/js/bootstrap-notify.min.js' />"></script>
	<script type="text/javascript">
		var rootBaseUrl = '${pageContext.request.contextPath}';
		console.log("rootBaseUrl:" + rootBaseUrl);
	</script>
	<!-- Custom javascript here -->
	<c:forEach var="script" items="${javascripts}">
		<script src="<c:url value="${script}"/>"></script>
	</c:forEach>
	<!--Start of Tawk.to Script-->
	<script type="text/javascript">
	var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
	(function(){
	var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
	s1.async=true;
	s1.src='https://embed.tawk.to/5941f55250fd5105d0c811ac/default';
	s1.charset='UTF-8';
	s1.setAttribute('crossorigin','*');
	s0.parentNode.insertBefore(s1,s0);
	})();
	</script>
	<!--End of Tawk.to Script-->
</body>
</html>