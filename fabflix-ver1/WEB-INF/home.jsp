<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
	<div class="row"><h1 class="text-center">Fabflix</h1></div>
	<div class="row">

		<!-- Home info begines here -->
		<div class="col-sm-4 col-sm-offset-4">
			<div class="row"><hr></div>
			<div class="row">
				<div class="col-xs-6"><a class="btn btn-info center-block" href="<%=request.getContextPath()%>/browse">Browse</a></div>
				<div class="col-xs-6"><a class="btn btn-success center-block" href="<%=request.getContextPath()%>/search">Search</a></div>
			</div>
		</div>

	</div>
</div>
</body>
</html>

