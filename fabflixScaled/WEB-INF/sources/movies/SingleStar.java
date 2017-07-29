package movies;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleStar extends HttpServlet {
	private Connection dbcon;

	public String getServletInfo() {
		return "Single Star servlet";
	}

	// Use http GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		try {
			// Retrieve data from database
			int starId = Integer.parseInt(request.getParameter("id"));
			connect();
			Star star = getStarInfo(starId);
			ArrayList<Movie> movieList = getStarredMovies(starId);
			disconnect();

			// Pass data to session
			HttpSession session = request.getSession();
			session.setAttribute("single-star", star);  
			session.setAttribute("movie-list", movieList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/singleStar.jsp");
			dispatcher.forward(request, response);          
		}
		catch (NumberFormatException e) {
			request.getSession().setAttribute("error-message", "invalid star Id");
		}
		catch (java.lang.Exception ex) {
			return;
		}    
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

	private Star getStarInfo(int starId) {
		try {
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select * from stars where stars.id= " + starId + ";"); 

			rs.next(); //point cursor to first row
			int s_id = rs.getInt(1); 
			String s_first_name = rs.getString(2);
			String s_last_name = rs.getString(3);
			String s_dob = rs.getString(4);
			String s_photo = rs.getString(5);

			Star star = new Star(s_id, s_first_name, s_last_name, s_dob, s_photo);

			rs.close();
			statement.close();
			return star;
		}
		catch (Exception e) {
			System.out.println("Invalid SQL Query ");
			return null;
		}
	}

	private ArrayList<Movie> getStarredMovies(int starId) {
		try {
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select m.* "
													+ "from movies as m, stars_in_movies as sm "
													+ "where sm.star_id = " + starId + " "
													+ "and m.id = sm.movie_id;");

			ArrayList<Movie> movieList = new ArrayList<Movie>();
			while(rs.next()) {
				movieList.add(new Movie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}

			rs.close();
			statement.close();
			return movieList;
		}
		catch (Exception e) {
			System.out.println("Invalid SQL Query ");
			return null;
		}
	}
}

