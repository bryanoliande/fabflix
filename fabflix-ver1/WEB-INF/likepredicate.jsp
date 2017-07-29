<%@page import="
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
<div class="row"><h1 class="text-center">Like Predicate</h1></div>
<div class="row">
<pre>
The Like Predicate is used to match partial strings in a query so that users may be given recommended matches, 
if possible. We primarily use the Like Predicate in out MovieList.java functions which is connected to our 
search.jsp file that asks the user to input Title, Year, Director, and First and Last Name for a star. The 
MovieList.java file uses the Like Predicate to match partial queries for all 5 of the user's queries so that they 
may be able to find some results that partially match their intended one. We use %A_Z% in order to ensure that the 
recommended results are better relevant of the users queries. 

We also use the Like Predicate in browserSearch.java where we use ABC% in order to match 0-9 or A-Z with queries that
begin with the chosen letter or number. This ensures that all of the results start with the chosen letter or number.

These are the only two places where we need to use the Like Predicate, however it is a very powerful tool for all 
search engines to use when needed.

</pre>
</div>
</div>
</body>
</html>