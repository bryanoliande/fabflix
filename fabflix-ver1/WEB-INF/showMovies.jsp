<%@page import="java.sql.*,
 java.util.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>

<%@ page import="movies.*"%>


<%
ArrayList<Movie> movies = (ArrayList<Movie>)session.getAttribute("search-results");
if(movies == null) {
	movies = new ArrayList<Movie>();
}
%>

<%@ include file="header.jsp" %>
<div class="container">
	<div class="row text-center"><h1>Search Results</h1></div>
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">
				<div class="row">
					<div class="row"><hr></div>
		  <%String genreCheck = (String) session.getAttribute("genre-check"); 
			String titleCheck = (String) session.getAttribute("title-check");
			String titleCheck2 = (String) session.getAttribute("title-check2");
			if(titleCheck2 == null){ 
				titleCheck2 = ""; 
			}
			String yearCheck = (String) session.getAttribute("year-check");
			if(yearCheck == null){ 
				yearCheck = "";
			}
			String directorCheck = (String) session.getAttribute("director-check");
			if(directorCheck == null){ 
				directorCheck = ""; 
			}
			String firstNameCheck = (String) session.getAttribute("firstName");
			if(firstNameCheck == null){ 
				firstNameCheck = ""; 
			}
			String lastNameCheck = (String) session.getAttribute("lastName");
			if(lastNameCheck == null){ 
				lastNameCheck = "";
			}
			String sortCheck = (String) session.getAttribute("sort-check");
			String url = (String) session.getAttribute("previous-page");
			int ippCheck = (Integer) session.getAttribute("itemlimit-check");
			int pageNum = (Integer) session.getAttribute("pagenumber");
			int nextPage = pageNum +1; 
			int previousPage = 1;
			if(pageNum>1){ 
				previousPage = pageNum-1; 
			}%>
			
			
		 <% if(genreCheck!=null){ 
			  
			  %>
			
				  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=titleasc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Title Ascending</a></div>
				  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=titledesc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Title Descending</a></div>
				  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=yearasc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Year Ascending</a></div>
				  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=yeardesc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Year Descending</a></div>
					
		<%
		  }
		  else if(titleCheck!=null){ %>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=titleasc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Title Ascending</a></div>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=titledesc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Title Descending</a></div>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=yearasc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Year Ascending</a></div>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=yeardesc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Year Descending</a></div>
		 <% }
		  else{ %>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=titleasc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Title Ascending</a></div>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=titledesc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Title Descending</a></div>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=yearasc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Year Ascending</a></div>
			  <div class="col-sm-3"><a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=yeardesc&page=<%=pageNum%>&ipp=<%=ippCheck%>">Year Descending</a></div>
		 <% }
		%>
				
				</div> <!-- row end-->
			</div> <!-- col-sm-6 div end-->
		</div> <!-- row end-->
		</br>

		<div class="col-xs-12">
		<div class="table-responsive">
		<table class="table table-hover">
			<tr>
				<th>ID</th>
				<th>Title</th>
				<th>Year</th>
				<th>Director</th>
				<th>Genres</th>
				<th>Stars</th>
				</tr>
		<%
			for(Movie movie: movies) {
				ArrayList<String> genreList = movie.getGenreList(); 
				ArrayList<Star> starList = movie.getStarList(); 
		%>
			<tr>
				<%//@ include file="movieRowView.jsp" %>
				<td><%=movie.getId() %></td>
				<td><a href = "<%=request.getContextPath()%>/movie/?id=<%=movie.getId()%>"> <%=movie.getTitle() %></a></td>
				<td><%=movie.getYear()%></td>
				<td><%=movie.getDirector()%></td>
				<td>
				<%for(String genre : genreList){ 
					%> <%=genre + " "%>
				<% }%>
				</td>
				<td>
				<%for(Star star : starList){ 
					%> <a href = "<%=request.getContextPath()%>/star/?id=<%=star.getId()%>"><%=star.getFirstName() + " " + star.getLastName() + " " %></a>
				<% }%>
			</td>
				<td style = "text-align:right"><a class="btn btn-success btn-sm" href="<%=request.getContextPath()%>/cart?add=Add&qty=1&mid=<%=movie.getId()%>">Add To Cart</a></td>
				
			</tr>
		<%
			}
			session.setAttribute("search-results", null);
		%>
		</table>
		</div>
		</div> <!-- col-xs-10 div end-->


		<div class="row">
		<div class="col-sm-6 col-sm-offset-3">
		<%if(genreCheck!=null){
			if(sortCheck!=null){%>
				Results Per Page
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=<%=sortCheck%>&page=1&ipp=5">5</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=<%=sortCheck%>&page=1&ipp=10">10</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=<%=sortCheck%>&page=1&ipp=25">25</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=<%=sortCheck%>&page=1&ipp=50">50</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=<%=sortCheck%>&page=1&ipp=100">100</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=<%=sortCheck%>&page=<%=previousPage %>&ipp=<%=ippCheck%>">Previous Page</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&sort=<%=sortCheck%>&page=<%=nextPage %>&ipp=<%=ippCheck%>">Next Page</a>
			<%} 
			else{ 
				%>
				Results Per Page
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&page=1&ipp=5">5</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&page=1&ipp=10">10</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&page=1&ipp=25">25</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&page=1&ipp=50">50</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&page=1&ipp=100">100</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&page=<%=previousPage %>&ipp=<%=ippCheck%>">Previous Page</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?genre=<%=genreCheck%>&page=<%=nextPage %>&ipp=<%=ippCheck%>">Next Page</a>
			<%}
			%>
		<%}
		
		else if(titleCheck!=null){
			if(sortCheck!=null){%>
				Results Per Page
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=<%=sortCheck%>&page=1&ipp=5">5</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=<%=sortCheck%>&page=1&ipp=10">10</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=<%=sortCheck%>&page=1&ipp=25">25</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=<%=sortCheck%>&page=1&ipp=50">50</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=<%=sortCheck%>&page=1&ipp=100">100</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=<%=sortCheck%>&page=<%=previousPage %>&ipp=<%=ippCheck%>">Previous Page</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&sort=<%=sortCheck%>&page=<%=nextPage %>&ipp=<%=ippCheck%>">Next Page</a>
				
			<%} 
			else{ 
				%>
				Results Per Page
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&page=1&ipp=5">5</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&page=1&ipp=10">10</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&page=1&ipp=25">25</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&page=1&ipp=50">50</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&page=1&ipp=100">100</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&page=<%=previousPage%>&ipp=<%=ippCheck%>">Previous Page</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/browserSearch/?title=<%=titleCheck%>&page=<%=nextPage%>&ipp=<%=ippCheck%>">Next Page</a>
			<%}
			%>

		<%} 
		
		else{ 
			if(sortCheck != null){%>{
				Results Per Page
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=<%=sortCheck%>&page=1&ipp=5">5</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=<%=sortCheck%>&page=1&ipp=10">10</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=<%=sortCheck%>&page=1&ipp=25">25</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=<%=sortCheck%>&page=1&ipp=50">50</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=<%=sortCheck%>&page=1&ipp=100">100</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=<%=sortCheck%>&page=<%=previousPage %>&ipp=<%=ippCheck%>">Previous Page</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&sort=<%=sortCheck%>&page=<%=nextPage %>&ipp=<%=ippCheck%>">Next Page</a>
			<%} 
			else{ %>
				Results Per Page
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&page=1&ipp=5">5</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&page=1&ipp=10">10</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&page=1&ipp=25">25</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&page=1&ipp=50">50</a>
				<a class="btn btn-default btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&page=1&ipp=100">100</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&page=<%=previousPage %>&ipp=<%=ippCheck%>">Previous Page</a>
				<a class="btn btn-info btn-sm" href="<%=request.getContextPath()%>/movielist/?title=<%=titleCheck2%>&year=<%=yearCheck%>&director=<%=directorCheck%>&starFirstName=<%=firstNameCheck%>&starLastName=<%=lastNameCheck%>&page=<%=nextPage %>&ipp=<%=ippCheck%>">Next Page</a>
			<%} %>
		<%}%>
		
		</div> <!-- div col-sm-6 offset-3 end -->
		</div> <!-- row end -->
</div> <!-- container end -->
</br>
</body>
</html>