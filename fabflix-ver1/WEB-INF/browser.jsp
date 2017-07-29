<%@page import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*, 
 java.util.ArrayList, 
 java.util.List"
%>

<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>

<div class="container">
	<div class="row text-center"><h1>Search By Genre</h1></div>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="row">
				<div class="row"><hr></div>
				<%
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb", "root", "cs122b");//change this back to normal database path and cs122b
				Statement select = connection.createStatement();
				ResultSet result = select.executeQuery("Select genres.name from genres order by genres.name;");
				List<String> genreList = new ArrayList<String>();
				String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				while (result.next()){
					//genreList.add(result.getString(1));
					String genre = result.getString(1); 
				%>
					<div class="col-md-4"><a href = "<%=request.getContextPath()%>/browserSearch/?genre=<%= genre %>" > <%= genre %></a></div>
				<% }%>
			</div>
		</div>
	</div>
<br>


	<div class="row text-center"><h1>Search By Title</h1></div>
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="row"><hr></div>
			<div class="row">
				<%for(int i = 0;i<10;i++){ 
					int num = i; %> 
					<div class="col-md-1"><a href = "<%=request.getContextPath()%>/browserSearch/?title=<%=num%>"> <%=num%></a></div>
				<%}%>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="row">
				<%for(int i = 0;i<26;i++){ 
					char letter = alpha.charAt(i);%>
					<div class="col-md-1"><a href = "<%=request.getContextPath()%>/browserSearch/?title=<%=letter%>"> <%=letter%></a></div>
					
				<%} %>
			</div>
		</div>
	</div>
		
</div>
</body>
</html>