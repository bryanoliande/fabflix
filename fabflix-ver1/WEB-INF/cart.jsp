<%@page import="java.sql.*,
 java.util.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%>
<%@ page import="movies.*"%>

<%
String previousPage = (String)session.getAttribute("previous-page");
%>
<%@ include file="header.jsp" %>
<div class="container">
	<div class="row"><h1 class="text-center">Shopping Cart</h1></div>
	<div class="row">

		<!-- Index begins here -->
		<div class="col-sm-6 col-sm-offset-3">
			<div class="row"><hr></div>
			<div class="row">
				<div class="col-sm-12">
			<%
				if(session.getAttribute("cart") == null || ((HashMap<Integer, Pair>)session.getAttribute("cart")).isEmpty()) {
			%>
					<p>Cart is empty.</p>
			<%
				}
				else {
			%>
					<div class="row">
						<div class="col-xs-5"><h4>Movie Title</h4></div>
						<div class="col-xs-1"><h4>Price</h4></div>
						<div class="col-xs-5 col-xs-offset-1"><h4>Qty</h4></div>
					</div>	
			<%
					Map<Integer, Pair> cart = (HashMap<Integer, Pair>)session.getAttribute("cart");
					for(Map.Entry<Integer, Pair> entry : cart.entrySet()) {
						Pair moviePair = entry.getValue();
						Movie movie = moviePair.getMovie();
						int qty = moviePair.getQty();
			%>
						<!-- Form to update and remove -->
						<div class="row">
							<div class="col-xs-5">
								<a href="<%=request.getContextPath()%>/movie?id=<%=movie.getId()%>"> <%=movie.getTitle()%> </a>
							</div>
							<div class="col-xs-1">$15.99</div>

							<div class="col-xs-5 col-xs-offset-1">
								<form>
									<input name="mid" type="hidden" value="<%=movie.getId()%>" />
									<input name="qty" type="text" value="<%=qty%>" size="5" />
									<input value="Update" name="update" type="submit" />
									<input value="Remove" name="delete" type="submit" />
								</form>
							</div>
						</div>
			<%
					}
			%>
						</br>
						<div class="row">
							<div class="col-sm-12"><p><b>Grand Total: </b>$<%= (Double)session.getAttribute("total") %></p></div>
						</div>
						</br>
						<div class="row">
							<div class="col-sm-4 col-sm-offset-2"><a class="btn btn-info center-block" href="<%=previousPage%>">Continue Shopping</a></div>
							<div class="col-sm-4"><a class="btn btn-success center-block" href="<%=request.getContextPath()%>/checkout">Checkout</a></div>
						</div>
			<%
				}
			%>
				</div>
			</div>
		</div>

	</div>
</div>		
</body>
</html>