<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<script src='https://www.google.com/recaptcha/api.js'></script>

<div class="container">
	<div class="row"><h1 class="text-center">Fabflix</h1></div>
	<div class="row">

		<!-- Index begins here -->
		<div class="col-sm-4 col-sm-offset-4">
			<div class="row"><hr></div>
			<%
				if(session != null && session.getAttribute("loginStatus") != null && !(Boolean)session.getAttribute("loginStatus")) {
			%>
					<div class="row"><div class="alert alert-danger">Wrong password entered.</div></div>
			<%
				}
			%>

			<p> Before we begin, please log into your account. </p>
			<form class="form-signin" action="<%= request.getContextPath()%>/login" method="post"> 
				<label for="inputEmail" class="sr-only">Email Address</label>
				<input type="email" id="inputEmail" class="form-control" placeholder="EmailAddress" name="email" required autofocus/></br>
				<label for="inputPassword" class="sr-only">Password</label>
				<input type="password" id="inputPassword" class="form-control" placeholder="Password" name="password" required/></br>
			<!--  	<div class="g-recaptcha" data-sitekey="6LcTOB4TAAAAALqbPZ7RyOe1qUmWRkqw8pjEK2KA"></div></br> Disabling for proj5-->
				<button class="btn btn-lg btn-primary btn-block" type="submit" value="Login">Sign In</button>
			</form>
		</div>

	</div>
</div>		
</body>
</html>