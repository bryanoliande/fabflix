<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
	<div class="row"><h1 class="text-center">Customer Information</h1></div>
	<div class="row">

		<!-- Index begins here -->
		<div class="col-sm-4 col-sm-offset-4">
			<div class="row"><hr></div>
			<form action="<%= request.getContextPath()%>/thankyou" method="post"> 
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Card Number:</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="cc" required/></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Expiration Date:</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="expdate" placeholder="YYYY-MM-DD" required/></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">First Name:</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="firstname" required/></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Last Name:</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="lastname" required/></div>
				</div>
				</br>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-2">
						<input class="btn btn-default center-block" type="reset" value="Reset" class="form-control" />
					</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4">
						<input class="btn btn-success center-block" type="submit" value="Checkout" class="form-control"/>
					</div>
				</div>
			</form>
		</div>

	</div>
</div>		
</body>
</html>