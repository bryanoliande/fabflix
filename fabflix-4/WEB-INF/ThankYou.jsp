<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
	
	<div class="row">
		<%
			if(session.getAttribute("infoChecksOut") != null && (Boolean)session.getAttribute("infoChecksOut")) {
		%>
				<div class="row"><h1 class="text-center">Thank you for shopping Fabflix.</h1></div>
		<%
			}
			else {
		%>
				<div class="row"><h1 class="text-center">Customer Info Not Found.</h1></div>
		<%
			}
		%>
		<a class="btn btn-sucess center-block" href="<%=request.getContextPath()%>/home">Back to Fabflix</a>
	</div>
</div>
</body>
</html>