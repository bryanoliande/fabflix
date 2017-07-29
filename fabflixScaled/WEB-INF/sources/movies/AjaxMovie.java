package movies;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AjaxMovie extends HttpServlet {
	private Connection dbcon;

	public String getServletInfo() {
		return "Ajax Movie servlet";
	}

	// Use http GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		try {
			// Retrieve data from database
			int movieId = Integer.parseInt(request.getParameter("id"));
			connect();
			Movie movie = getMovieInfo(movieId);
			disconnect();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			printMovie(out, movie);								   	 
		}
		catch (NumberFormatException e) {
			request.getSession().setAttribute("error-message", "invalid movie Id");
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

	private void printMovie(PrintWriter out, Movie movie) {
		out.println("<b><u>" + movie.getTitle() + " (" + movie.getYear() + ")</u></b></br></br>");
		out.println("<b><i>Banner URL:</i></b></br>" + "<a href='" + movie.getBannerURL() + "'target='_blank'>" + movie.getBannerURL() + "</a></br>");
		out.println("<b><i>Trailer URL:</i></b></br>" + "<a href='" + movie.getTrailerURL() + "'target='_blank'>" + movie.getTrailerURL() + "</a></br>");
		out.println("</br></br><input type='button' onclick='ajax_hideTooltip()' value='close'/>");
	}

}

