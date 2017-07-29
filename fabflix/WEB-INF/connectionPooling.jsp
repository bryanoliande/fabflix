<%@page import="
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%@ include file="header.jsp" %>
<div class="container">
<div class="row"><h1 class="text-center">Connection Pooling</h1></div>
<div class="row">
<pre>
Connection Pooling Write Up

Connection pooling is having a cache of database connections to reuse when there are future 
requests to the database. It is a very powerful tool that help increase the speed of executing 
commands to the database. However, there are some downsides to connection pooling. Having too many
connections saved can actually slow down your requests and making changes to the saved connections 
will not only slow down your speed, but has the potential of causing errors when you try to connect. 
Nonetheless, connection pooling is a powerful tool that can be a great asset to developers 

Connection pooling can also be utilized with backend servers. Sending requests to the backend server 
serves the powerful purpose of speeding up execution time. But, there are times where you may have 
two backend servers and will need to figure a way to utilize both. In order to that you would need to
have one server perform only perform writing while the other one will handle both reading and writing. 
This serves to not only speed up write requests, but also read requests.


</pre>
</div>
</div>
</body>
</html>