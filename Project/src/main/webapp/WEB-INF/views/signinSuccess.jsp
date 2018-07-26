<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

	success

<h2>
	Welcome : ${pageContext.request.userPrincipal.name} | <a
		href="<c:url value="/j_spring_security_logout" />"> Sign out</a>
		<br/>
		Role:  <sec:authentication property="principal.authorities" />
</h2>
