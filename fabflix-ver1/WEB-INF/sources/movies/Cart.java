package movies;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Cart extends HttpServlet {
	private Connection dbcon;
	private double total = 0;

	public String getServletInfo() {
		return "Cart servlet";
	}

	// Use http GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		try {
			connect();
			HttpSession session = request.getSession();
			int qty;
			int movieId;
			Movie movie;
			Pair moviePair;

			if(request.getParameter("add") != null) {
				qty = Integer.parseInt(request.getParameter("qty"));
				movieId = Integer.parseInt(request.getParameter("mid"));
				movie = getMovieInfo(movieId);      
				moviePair = new Pair(movie, qty);
				addToCart(movieId, moviePair, session);
			}
			else if(request.getParameter("update") != null) {
				qty = Integer.parseInt(request.getParameter("qty"));
				movieId = Integer.parseInt(request.getParameter("mid"));
				movie = getMovieInfo(movieId);   
				moviePair = new Pair(movie, qty);
				updateCart(movieId, moviePair, session);
			}
			else if(request.getParameter("empty") != null) {
				session.setAttribute("cart", null);
			}
			else if(request.getParameter("delete") != null) {
				movieId = Integer.parseInt(request.getParameter("mid"));
				movie = getMovieInfo(movieId);   
				moviePair = new Pair(movie, 0);
				updateCart(movieId, moviePair, session);
			}

			disconnect();
								   
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cart.jsp");
			dispatcher.forward(request, response);
		}
		catch (NumberFormatException e) {
			request.getSession().setAttribute("error-message", "invalid movie Id");
		}
		catch (java.lang.Exception ex) {
			return;
		} 
	}

	private void calculateTotal(Map<Integer, Pair> cart, HttpSession session) {
		double movieCount = 0;
		for(Pair moviePair: cart.values()) {
			movieCount += moviePair.getQty();
		}
		double total = 15.99 * movieCount;
		session.setAttribute("total", total);
	}

	private void connect() {
		String loginUser = "root";
		String loginPasswd = "cs122b";
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

	private Movie getMovieInfo(int movieId) {
		try {
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select m.* from movies as m where m.id = " + movieId + ";");

			rs.next(); //point cursor to first row
			int m_id = rs.getInt(1); 
			String m_title = rs.getString(2);
			String m_year = rs.getString(3); 
			String m_director = rs.getString(4);
			String m_banner_url = rs.getString(5);
			String m_trailer_url = rs.getString(6);

			Movie movie = new Movie(m_id, m_title, m_year, m_director, m_banner_url, m_trailer_url);

			rs.close();
			statement.close();
			return movie;
		}
		catch (Exception e) {
			System.out.println("Invalid SQL Query ");
			return null;
		}
	}
	
	// Supress for casting from session object.
	@SuppressWarnings("unchecked")
	private void addToCart(int movieId, Pair moviePair, HttpSession session) {
		Map<Integer, Pair> cart;
		if(session.getAttribute("cart") == null) {
			cart = new HashMap<Integer, Pair>();
			cart.put(movieId, moviePair);
			session.setAttribute("cart", cart);
			calculateTotal(cart, session);
		}
		else {
			cart = (HashMap<Integer, Pair>)session.getAttribute("cart");
			if(cart.containsKey(movieId)) {
				Pair storedMoviePair = cart.get(movieId);
				int newQuantity = storedMoviePair.getQty() + moviePair.getQty();
				storedMoviePair.setQty(newQuantity);
			}
			else {
				cart.put(movieId, moviePair);
			}
			calculateTotal(cart, session);
			//session.setAttribute("cart", cart);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateCart(int movieId, Pair moviePair, HttpSession session) {
		Map<Integer, Pair> cart = (HashMap<Integer, Pair>)session.getAttribute("cart");
		if(cart.containsKey(movieId)) {
			Pair storedMoviePair = cart.get(movieId);
			int newQuantity = moviePair.getQty();
			if(newQuantity <= 0) {
				cart.remove(movieId);
			}	
			else {
				storedMoviePair.setQty(newQuantity);
			}
			calculateTotal(cart, session);
		}
	}

}


