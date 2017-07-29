<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*,
 dashboard.*
"%>

<%@ include file="header.jsp" %>
<div class="container">
	<div class="row"><h1 class="text-center">Fabflix Dashboard</h1></div>
	<div class="row">

		<!-- Index begins here -->
		<div class="col-sm-4 col-sm-offset-4">
			<div class="row"><hr></div>
			<%
				if(session != null && session.getAttribute("employeeLoginStatus") != null && !(Boolean)session.getAttribute("employeeLoginStatus")) {
			%>
					<div class="row"><div class="alert alert-danger">Wrong password entered.</div>
			<%
				}
			%>

			<p> Employee Login </p>
			<form class="form-signin" action="<%= request.getContextPath()%>/_dashboard/login" method="post"> 
				<label for="inputEmail" class="sr-only">Email Address</label>
				<input type="email" id="inputEmail" class="form-control" placeholder="EmailAddress" name="email" required autofocus/></br>
				<label for="inputPassword" class="sr-only">Password</label>
				<input type="password" id="inputPassword" class="form-control" placeholder="Password" name="password" required/></br>
				<button class="btn btn-lg btn-default btn-block" type="submit" value="Login">Sign In</button>					
			</form>
		</div>

	</div>
</div>		
</body>
</html>