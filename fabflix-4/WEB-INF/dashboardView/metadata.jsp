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
				<div class="col-sm-3"><a class="btn btn-info center-block" href="<%=request.getContextPath()%>/_dashboard/metadata">Metadata</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/insertstar">Insert Star</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/addmovie">Add movie</a></div>
				<div class="col-sm-3"><a class="btn btn-default center-block" href="<%=request.getContextPath()%>/_dashboard/addinfo">Add info</a></div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
		<% 
		List<HashMap<String,HashMap<String,String>>> metaList = (List<HashMap<String,HashMap<String,String>>>) session.getAttribute("meta-list"); 
		
		for(int i = 0;i<metaList.size();i++){ 
			HashMap<String,HashMap<String,String>> table = metaList.get(i); 
			for(String key:table.keySet()){ %>
				<h3><%=key %></h3>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Attribute</th>
							<th>Attribute Type</th>
						</tr>
					</thead>
					<tbody>

				<% 
					for(String attribute:table.get(key).keySet()){ %> 
						<tr>
							<td><%=attribute %></td>
							<td><%=table.get(key).get(attribute)%></td>
						</tr>
					
				<% 	} %>
				</tbody>
				</table>	
						
			<%	
			}
		}	
		%>
		</div>
	</div>
</div>
</body>
</html>