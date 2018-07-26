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
						<h2>View Evaluation</h2>
						
						<div class="clearfix"></div>
					</div>
					<div class="x_content">

                    <table class="table table-striped">
                      <thead>
                        <tr>
                          <th>#</th>
                          <th>Recommendation Name</th>
                          <th>Threshold</th>
                          <th>RMSE</th>
                          <th>MAE</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="eval" items="${evaluations}">
                        <tr>
                          <th scope="row">${eval.recommendationId }</th>
                          <td><c:out value="${eval.name }" /></td>
                          <td>${eval.threshold }</td>
                          <td>${String.format( "%.4f", eval.RMSE ) }</td>
                          <td>${String.format( "%.4f", eval.MAE ) }</td>
                        </tr>
                        </c:forEach>
                       
                      </tbody>
                    </table>

                  </div>
				</div>
			</div>
		</div>
	</div>
</div>