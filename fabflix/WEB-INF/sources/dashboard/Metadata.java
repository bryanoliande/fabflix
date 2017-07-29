package dashboard;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import movies.*;

public class Metadata extends HttpServlet {
	private Connection dbcon;

	public String getServletInfo() {
		return "Metadata servlet";
	}

	// Use http GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		try {
			// Retrieve data from database
			//int movieId = Integer.parseInt(request.getParameter("id"));
			//connect();
			//Movie movie = getMovieInfo(movieId);
			//ArrayList<Star> starList = getStarsInMovie(movieId);
			//ArrayList<String> genreList = getGenreList(movieId);
			//disconnect();
			ArrayList<String> tables = new ArrayList<String>();
			tables.add("stars"); 
			tables.add("movies"); 
			tables.add("stars_in_movies"); 
			tables.add("genres"); 
			tables.add("genres_in_movies");
			tables.add("creditcards"); 
			tables.add("customers"); 
			tables.add("sales");
			tables.add("employees");
			StoredProcedures newStatements = new StoredProcedures("root","cs122b","jdbc:mysql://localhost:3306/moviedb"); 
			List<HashMap<String,HashMap<String,String>>> metaList = new ArrayList<HashMap<String,HashMap<String,String>>>();
			for(String table:tables){ 
				metaList.add(newStatements.getMetaData(table));
			}
			// Pass data to session
			HttpSession session = request.getSession();                              
			//session.setAttribute("single-movie", movie);  
			//session.setAttribute("star-list", starList);
			//session.setAttribute("genre-list", genreList);
			//session.setAttribute("price", price);
			//session.setAttribute("previous-page", previousPage);
			session.setAttribute("meta-list", metaList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dashboardView/metadata.jsp");
			dispatcher.forward(request, response);              
		}
		catch (java.lang.Exception ex) {
			return;
		}
	}

	// Time to hook this up to dbmanager to stop repeating because we are connected to different/multiple databases?
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

}

