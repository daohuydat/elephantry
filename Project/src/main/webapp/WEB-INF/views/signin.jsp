<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<div>
	<a class="hiddenanchor" id="signup"></a> <a class="hiddenanchor"
		id="signin"></a> <a class="hiddenanchor" id="forgotPassword"></a>
	<!-- Login page -->
	<div class="login_wrapper">
		<div class="animate form login_form">
			<section class="login_content">
				<form id="formSignin"
					action="${pageContext.request.contextPath}/j_spring_security_check"
					method="POST">
					<h1>Signin System</h1>
					<div style="text-align: left;">
						<input type="text" id="email" style="margin-bottom: 0px;"
							class="form-control" placeholder="Email" name="email"
							required="required" />
					</div>
					<br>
					<div style="text-align: left;">
						<input type="password" id="password" style="margin-bottom: 0px;"
							class="form-control" placeholder="Password" name="password"
							required="required" />
					</div>
					<br>
					<div>
						<button class="btn btn-default submit btnSignin" type="submit">Sign
							in</button>
						<p class="change_link">
							<a href="#signup" class="to_register"> Lost your password? </a>
						</p>
					</div>

					<div class="clearfix"></div>
					<div class="separator">
						<p class="change_link">
							New to site? <a
								href="${pageContext.request.contextPath}/account/signup?p=freetrial"
								class="to_register"> Create Account </a>
						</p>

						<div class="clearfix"></div>
						<br />

						<div>
							<h1>
								<i class="fa fa-paw"></i> Recommendation system!
							</h1>
							<p>©2017 All Rights Reserved. Recommendation system! is a
								best service recommend. Privacy and Terms</p>
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</section>
		</div>
		<!-- Register page -->
		<div id="register" class="animate form registration_form">
			<section class="login_content">
				<form id="formForgot">
					<h1>Elelphantry account</h1>
					<div style="text-align: left;">
						<input type="text" id="emailForgot" name="emailForgot"
							style="margin-bottom: 0px;" class="form-control"
							placeholder="Email" required="required" />
					</div>
					<br>
					<div>
						<button class="btn btn-default submit btnForgotPass" type="submit">Send</button>
					</div>

					<div class="clearfix"></div>

					<div class="separator">
						<p class="change_link">
							Already a member ? <a href="#signin" class="to_register">
								Sign in </a>
						</p>

						<div class="clearfix"></div>
						<br />

						<div>
							<h1>
								<i class="fa fa-paw"></i> Recommendation system!
							</h1>
							<p>©2017 All Rights Reserved. Recommendation system! is a
								best service recommend. Privacy and Terms</p>
						</div>
					</div>
				</form>
			</section>
		</div>
	</div>
</div>