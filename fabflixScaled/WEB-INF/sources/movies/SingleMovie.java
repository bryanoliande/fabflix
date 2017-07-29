package movies;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleMovie extends HttpServlet {
	private Connection dbcon;
	private double price = 15.99;

	public String getServletInfo() {
		return "Single Movie servlet";
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
			ArrayList<Star> starList = getStarsInMovie(movieId);
			ArrayList<String> genreList = getGenreList(movieId);
			disconnect();
			String previousPage = request.getRequestURI() + "?" + request.getQueryString();
								   
			// Pass data to session
			HttpSession session = request.getSession();                              
			session.setAttribute("single-movie", movie);  
			session.setAttribute("star-list", starList);
			session.setAttribute("genre-list", genreList);
			session.setAttribute("price", price);
			session.setAttribute("previous-page", previousPage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/singleMovie.jsp");
			dispatcher.forward(request, response);              
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

	private ArrayList<Star> getStarsInMovie(int movieId) {
		try {
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select s.* "
													+ "from stars as s, stars_in_movies as sm "
													+ "where sm.movie_id = " + movieId + " "
													+ "and s.id = sm.star_id;");

			ArrayList<Star> starList = new ArrayList<Star>();
			while(rs.next()) {
				starList.add(new Star(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}

			rs.close();
			statement.close();
			return starList;
		}
		catch (Exception e) {
			System.out.println("Invalid SQL Query ");
			return new ArrayList<Star>();
		}
	}

	private ArrayList<String> getGenreList(int movieId) {
		try {
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select genres.* "
													+ "from genres_in_movies as gm, genres "
													+ "where gm.movie_id = " + movieId + " "
													+ "and genres.id = gm.genre_id;");

			ArrayList<String> genreList = new ArrayList<String>();
			while(rs.next()) {
				String genre = rs.getString(2);
				genreList.add(genre);
			}

			rs.close();
			statement.close();
			return genreList;
		}
		catch (Exception e) {
			System.out.println("Invalid SQL Query ");
			return new ArrayList<String>();
		}
	}
}

