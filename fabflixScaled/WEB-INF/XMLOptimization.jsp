<%@page import="
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
<div class="row"><h1 class="text-center">XML Parsing Optimization</h1></div>
<div class="row">
<pre>
XML Optimization are techniques that are used to speed up the parsing process of XML files. There are many 
different techniques that we can use to facilitate the optimization process, however we chose to batch insert and 
disable auto-commit. 

Batch Insert optimizes our code by being one transaction versus n-many transactions in order to insert n-number of things. 
This speeds up our parsing significantly because it lowers the overhead cost from repeatedly connecting and disconnecting to the 
server. 

Disabling auto-commit optimizes our code by committing all transactions at the same time instead of doing it for every transaction. 
This eliminates the preprocessing and network overhead that comes from auto-committing. By doing this, we experienced a significant
increase in the speed of out parsing.

</pre>
</div>
</div>
</body>
</html>