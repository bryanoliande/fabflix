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
	<div class="row"><h1 class="text-center">Dashboard</h1></div>
	<div class="row">

		<!-- Home info begines here -->
		<div class="col-sm-8 col-sm-offset-2">
			<div class="row"><hr></div>
			<div class="row">
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/metadata">Metadata</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/insertstar">Insert Star</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/addmovie">Add movie</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/addinfo">Add info</a></div>
			</div>
		</div>

	</div>
</div>
</body>
</html>

