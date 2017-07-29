<%@page import="java.sql.*,
 java.util.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@page import="movies.*" %>

<%
Star star = (Star)session.getAttribute("single-star");
ArrayList<Movie> movieList = (ArrayList<Movie>)session.getAttribute("movie-list");
String starFullName = star.getFirstName() + " " + star.getLastName();
%>
<%@ include file="header.jsp" %>

<div class="row text-center"><h1>Star Details</h1></div>
<div class="container">
	<div class="row">
		<div class="col-xs-2"></div>
		<div class="col-xs-8">

			<!-- Star info begins here -->
			<div class="row">
				<div class="row"><hr></div>
				<div class="col-xs-4">
					<img class="img-responsive center-block" src="<%= star.getPhotoURL() %>" alt="No Image Available"></div>
				<div class="col-xs-8">
					<div class="row">
						<div class="col-xs-3">Star name:</div>
						<div class="col-xs-9"><%= starFullName %></div>
					</div>
					<div class="row">
						<div class="col-xs-3">Date of Birth:</div>
						<div class="col-xs-9"><%= star.getDOB() %></div>
					</div>
					<div class="row">
						<div class="col-xs-3">Star id:</div>
						<div class="col-xs-9"><%= star.getId() %></div>
					</div>
					<div class="row">
						<div class="col-xs-3">Starred in:</div>
						<div class="col-xs-9">
						<%
						for(Movie movie: movieList) {
							int movieId = movie.getId();
							String movieTitle = movie.getTitle();
						%>
							<a href="<%=request.getContextPath()%>/movie/?id=<%=movieId%>"><%= movieTitle %></a></br>
						<%
						}
						%>
						</div>
					</div>	
				</div>
			</div>

		</div>
		<div class="col-xs-2"></div>
	</div>
</div>
</body>
</html>

