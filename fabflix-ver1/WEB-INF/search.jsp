<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
	<div class="row"><h1 class="text-center">Search</h1></div>
	<div class="row">

		<!-- Seach form begines here -->
		<div class="col-sm-4 col-sm-offset-4">	
			<div class="row"><hr></div>
			<form action="<%=request.getContextPath()%>/movielist" method="get"> 
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Title</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="title" /></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Year</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="year" /></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Director</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="director" /></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Star's First Name</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="starFirstName" /></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Star's Last Name</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="starLastName" /></div>
				</div>	
				</br>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-6 col-xs-4 col-xs-offset-4"><input class="btn btn-success center-block" type="submit" value="Search"/></div>
				</div>
			</form>
		</div>

	</div>
</div>
</body>
</html>

