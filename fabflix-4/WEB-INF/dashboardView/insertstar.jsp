<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.*"
%>
<%@ page import="movies.*,
 dashboard.*
"%>

<%@ include file="header.jsp" %>
<div class="container">
	<div class="row"><h1 class="text-center">Dashboard</h1></div>

	<!-- Home info begins here -->
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<div class="row"><hr></div>
			<div class="row">
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/metadata">Metadata</a></div>
				<div class="col-sm-3"><a class="btn btn-info center-block" href="<%=request.getContextPath()%>/_dashboard/insertstar">Insert Star</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/addmovie">Add movie</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/addinfo">Add info</a></div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-4 col-sm-offset-4">	
			<div class="row"><hr></div>
			<div class="row">
				<%
				if(request.getAttribute("insert-check") == "passed") {
				%>
					<div class="alert alert-success">Sucess! Star inserted</div>
				<%
				}
				else if(request.getAttribute("insert-check") == "star exists"){
				%>
					<div class="alert alert-danger">Error! Star already exists</div>
				<%
				}
				%>
			</div>
			<form action="<%=request.getContextPath()%>/_dashboard/InsertStarResults" method="post"> 
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Star's First Name</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="starFirstName" /></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Star's Last Name</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="starLastName" required /></div>
				</div>	
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Star's Date of Birth</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="starDOB" placeholder="YYYY-MM-DD" /></div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4">Star's Photo URL</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-4"><input type="text" name="starPhotoUrl" /></div>
				</div>
				</br>
				<div class="row">
					<div class="col-sm-6 col-sm-offset-0 col-xs-4 col-xs-offset-2">
						<input class="btn btn-default center-block" type="reset" value="Reset" class="form-control" />
					</div>
					<div class="col-sm-6 col-sm-offset-0 col-xs-4"><input class="btn btn-success center-block" type="submit" value="Insert"/></div>
				</div>
			</form>
		</div>
	</div>

</div>
</body>
</html>