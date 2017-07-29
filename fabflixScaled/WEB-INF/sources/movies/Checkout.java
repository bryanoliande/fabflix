package movies;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Checkout extends HttpServlet {
	private Connection dbcon;
	private int customerId; 

	public String getServletInfo() {
		return "Checkout servlet";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        String sessionURL = response.encodeRedirectURL(request.getContextPath());
        response.sendRedirect(sessionURL);
    }

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
		try {
			// Retrieve data from database
			String cc = (request.getParameter("cc"));
			String expDate = request.getParameter("expdate");
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
	
			

			connect();
			boolean infoChecksOut = infoChecksOut(cc, expDate, firstName, lastName);

			HttpSession session = request.getSession();  
			//get cart data 
			Map<Integer, Pair> cart = (HashMap<Integer, Pair>)session.getAttribute("cart");
			if(infoChecksOut){ 
				for(Integer movieId:cart.keySet()){ 
					int qty = cart.get(movieId).getQty(); 
					for(int i = 1; i<=qty;i++){ 
						insertSales(movieId,customerId);
					}
				}
			}
			disconnect();
			session.setAttribute("infoChecksOut", infoChecksOut);  
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ThankYou.jsp");
			dispatcher.forward(request, response);          
		}
		catch (NumberFormatException e) {
			request.getSession().setAttribute("error-message", "invalid credit card");
			out.println("<p>"+ e.getMessage() +"</p>");
		}
		catch (java.lang.Exception ex) {
			out.println("<p>"+ex.getMessage()+"</p>");
			return;
		}    
	}

	private void connect() {
		String loginUser = "root";
		String loginPasswd = "cs122b";//change back to cs122b
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  
		} 
		catch (Exception e) {
			System.out.println("Instantiation Exception");
		} 
	}

	private void disconnect() {
		try {
			dbcon.close();
		}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  
		} 
	}

	private boolean infoChecksOut(String cc, String expDate, String firstName, String lastName) {
		try {
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select c.* " 
					+"from creditcards as cc,customers as c " 
					+"where cc.id = '" + cc + "' "
					+"and cc.first_name = '" + firstName + "' "
					+"and cc.last_name = '" + lastName+"' "
					+"and cc.expiration = DATE('"+ expDate+ "');");
			if(rs.next()){ 
				customerId = rs.getInt("id"); 
				return true;
			}
			else{ 
				return false; 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return true;

	}
	
	private void insertSales(int movieId, int customerId){ 
		try {
			Statement statement = dbcon.createStatement(); 
			statement.executeUpdate("Insert into sales values(0," + customerId +","+movieId+",CURDATE());");

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}