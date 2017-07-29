<%@page import="java.sql.*,
 java.util.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@page import="movies.*" %>

<%
Movie movie = (Movie)session.getAttribute("single-movie");
ArrayList<Star> starList = (ArrayList<Star>)session.getAttribute("star-list");
ArrayList<String> genreList = (ArrayList<String>)session.getAttribute("genre-list");
double price = (Double)session.getAttribute("price");
%>
<%@ include file="header.jsp" %>
<div class="container">
	<div class="row text-center"><h1>Movie Details</h1></div>
	<div class="row">
		<div class="col-xs-2"></div>
		<div class="col-xs-8">
		
			<!-- Movie info begins here -->
			<div class="row">
				<div class="row"><hr></div>
				<div class="col-xs-4">
					<img class="img-responsive center-block" src="<%= movie.getBannerURL() %>" alt="No Image Available"></div>
				<div class="col-xs-8">
					<div class="row">
						<div class="col-xs-2">Title:</div>
						<div class="col-xs-10"><%= movie.getTitle() %></div>
					</div>
					<div class="row">
						<div class="col-xs-2">Year:</div>
						<div class="col-xs-10"><%= movie.getYear() %></div>
					</div>
					<div class="row">
						<div class="col-xs-2">Director:</div>
						<div class="col-xs-10"><%= movie.getDirector() %></div>
					</div>
					<div class="row">
						<div class="col-xs-2">Stars:</div>
						<div class="col-xs-10">
						<%
						for(int i = 0; i < starList.size(); i++) {
							Star star = starList.get(i);
							int starId = star.getId();
							String starName = star.getFirstName() + " " + star.getLastName();;
							if(i != starList.size()-1) {
								starName += ",";
							}
						%>
							<a href="<%=request.getContextPath()%>/star?id=<%=starId%>"><%= starName %></a>
						<%
						}
						%>
						</div>
					</div>	
					<div class="row">
						<div class="col-xs-2">Genre:</div>
						<div class="col-xs-10">
						<%
						for(int i = 0; i < genreList.size(); i++) {
							String genre = genreList.get(i);
							if(i != genreList.size()-1) {
								genre += ",";
							}
						%>
							<%= genre %>
						<%
						}
						%>
						</div>
					</div>	
					<div class="row">
						<div class="col-xs-2">Trailer:</div>
						<div class="col-xs-10"><a href="<%= movie.getTrailerURL() %>">Click here </a>to watch the movie trailer</div>
					</div>
					<div class="row">
						<div class="col-xs-2">Price:</div>
						<div class="col-xs-10">$<%= price %></div>
					</div>
					</br>
					<div class="row">
						<div class="col-xs-2"></div>
						<div class="col-xs-10">
							<a class="btn btn-success btn-sm" href="<%=request.getContextPath()%>/cart?add=Add&qty=1&mid=<%=movie.getId()%>">Add To Cart</a>
						</div>
					</div>
				</div>
			</div>

	</div>
	<div class="col-xs-2"></div>
</div>
</body>
</html>

