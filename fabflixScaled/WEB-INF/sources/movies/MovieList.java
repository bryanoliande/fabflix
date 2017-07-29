package movies;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class MovieList extends HttpServlet
{
	private Connection dbcon;
	private boolean pooledConnection = true;
	private boolean prepared = true;
	

	public String getServletInfo()
	{
	   return "MovieList servlet";
	}

	// Use http GET

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		try {
			getConnection(pooledConnection);
			// Declare our statement
			Statement statement = dbcon.createStatement();

			String title = request.getParameter("title");
			String year = request.getParameter("year");
			String director = request.getParameter("director");
			String starFirstName = request.getParameter("starFirstName");
			String starLastName = request.getParameter("starLastName");
			
			String sortCheck = request.getParameter("sort");
			String previousPage = request.getRequestURI() + "?" + request.getQueryString(); 
			int itemlimitCheck = 50; //for testing  jmeter 
			int pageNumber = 1; 
			if(request.getParameter("ipp")!= null){ 
				itemlimitCheck = Integer.parseInt(request.getParameter("ipp"));
				pageNumber = Integer.parseInt(request.getParameter("page"));
			}
			int offset = (itemlimitCheck * (pageNumber-1)); 
			
			String genreCheck = request.getParameter("genre");
			String titleCheck = request.getParameter("title");
				
			  
			// Perform the query
			ResultSet rs = null;
			
			if(sortCheck == null){
				if(itemlimitCheck == 0) { 
					
					if(prepared)
					{
					long startTime = System.nanoTime();

					PreparedStatement pstatement;
					pstatement = dbcon.prepareStatement("Select m.* " 
							  + "from movies as m, stars AS s, stars_in_movies AS sm "
							  + "WHERE s.first_name LIKE ? AND s.last_name LIKE ? "
							  +  "AND m. title LIKE ? AND m.year LIKE ? AND "
							  +  "m.director LIKE ? AND s.id = sm.star_id AND "
							  + "m.id = sm.movie_id ");
					pstatement.setString(1, "%" + likeSanitize(starFirstName) + "%");
					pstatement.setString(2, "%" + likeSanitize(starLastName) + "%");
					pstatement.setString(3, "%" + likeSanitize(title) + "%");
					pstatement.setString(4, "%" + likeSanitize(year) + "%"); //might need to setInt
					pstatement.setString(5, "%" + likeSanitize(director) + "%"); 
					rs = pstatement.executeQuery();
					
					long endTime = System.nanoTime();
					long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
					System.out.println("JDBC: " + elapsedTime);
					}
					else
					{
				  		long startTime = System.nanoTime();

            			rs = statement.executeQuery("SELECT m.* "
						+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
						+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
						+  "s.last_name LIKE '%" + starLastName + "%' AND "
						+  "m.title LIKE '%" + title + "%' AND "
						+  "m.year LIKE '%" + year + "%' AND "
						+  "m.director LIKE '%" + director + "%' AND "
						+  "s.id = sm.star_id AND "
                        +  "m.id = sm.movie_id;");
            			
            			long endTime = System.nanoTime();
    					long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
    					System.out.println("JDBC: " + elapsedTime);
					}
					
					} 
				else { 
					
					if(prepared)
					{
					long startTime = System.nanoTime();

					PreparedStatement pstatement;
					pstatement = dbcon.prepareStatement("Select m.* " 
							  + "from movies as m, stars AS s, stars_in_movies AS sm "
							  + "WHERE s.first_name LIKE ? AND s.last_name LIKE ? "
							  +  "AND m. title LIKE ? AND m.year LIKE ? AND "
							  +  "m.director LIKE ? AND s.id = sm.star_id AND "
							  + "m.id = sm.movie_id limit ? offset ?");
					pstatement.setString(1, "%" + likeSanitize(starFirstName) + "%");
					pstatement.setString(2, "%" + likeSanitize(starLastName) + "%");
					pstatement.setString(3, "%" + likeSanitize(title) + "%");
					pstatement.setString(4, "%" + likeSanitize(year) + "%"); //might need to setInt
					pstatement.setString(5, "%" + likeSanitize(director) + "%"); 
					pstatement.setInt(6, itemlimitCheck);
					pstatement.setInt(7, offset);
					rs = pstatement.executeQuery();
					
					long endTime = System.nanoTime();
					long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
					System.out.println("JDBC: " + elapsedTime);
					}
					else{
						long startTime = System.nanoTime();
            			rs = statement.executeQuery("SELECT m.* "
						+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
						+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
						+  "s.last_name LIKE '%" + starLastName + "%' AND "
						+  "m.title LIKE '%" + title + "%' AND "
						+  "m.year LIKE '%" + year + "%' AND "
						+  "m.director LIKE '%" + director + "%' AND "
						+  "s.id = sm.star_id AND "
                        +  "m.id = sm.movie_id "
                        + "limit "+ itemlimitCheck+ " offset "+ offset+";");
                	
            			long endTime = System.nanoTime();
    					long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
    					System.out.println("JDBC: " + elapsedTime);
					}
				}
			}
			else if(sortCheck.equals("titleasc")){ 
				if(itemlimitCheck == 0){ 
				
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.title;");
				}
				else{ 
					
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.title "
							+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
				}
			}
			else if(sortCheck.equals("titledesc")){ 
				if(itemlimitCheck == 0){ 
					
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.title desc;");
				}
				else{ 
					
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.title desc "
							+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
					
				}
			}
			else if(sortCheck.equals("yearasc")){ 
				if(itemlimitCheck == 0){ 
					
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.year;");
				}
				else{ 
 
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.year "
							+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
				}
			}
			else if(sortCheck.equals("yeardesc")){ 
				if(itemlimitCheck == 0){ 
			
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.year desc;");
				} 
				else{ 
			
					rs = statement.executeQuery("SELECT m.* "
							+ "FROM movies AS m, stars AS s, stars_in_movies AS sm "
							+ "WHERE s.first_name LIKE '%" + starFirstName + "%' AND "
							+  "s.last_name LIKE '%" + starLastName + "%' AND "
							+  "m.title LIKE '%" + title + "%' AND "
							+  "m.year LIKE '%" + year + "%' AND "
							+  "m.director LIKE '%" + director + "%' AND "
							+  "s.id = sm.star_id AND "
							+  "m.id = sm.movie_id "
							+ " order by m.year desc "
							+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
				}
			}
			
			ArrayList<Movie> movieList = new ArrayList<Movie>();
			int index = 0;
			int previous = 0; 

			// Iterate through each row of rs
			while (rs.next()) {
				int m_id = rs.getInt("id"); //might need to be getInt
				String m_title = rs.getString("title");
				String m_year = Integer.toString(rs.getInt("year"));
				String m_director = rs.getString("director");
				ArrayList<String> m_genreList = getgenreList(m_title); 
				ArrayList<Star> m_actorList = getstarList(m_title);
				if(m_id == previous){ 
					continue;
				}
				previous = rs.getInt("id");
		   
						   

				Movie movieToAdd = new Movie(m_id, m_title, m_year, m_director,
											m_genreList, m_actorList);
				
				movieList.add(index, movieToAdd);
			   ++index;
				
			} 

			HttpSession session = request.getSession();                              
			session.setAttribute("search-results", movieList); 
			session.setAttribute("genre-check", null);
			session.setAttribute("title-check2", title);
			session.setAttribute("year-check", year);
			session.setAttribute("director-check", director);
			session.setAttribute("firstName-check", starFirstName);
			session.setAttribute("lastName", starLastName);
			session.setAttribute("sort-check", sortCheck);
			session.setAttribute("itemlimit-check", itemlimitCheck);
			session.setAttribute("pagenumber", pageNumber);
			session.setAttribute("previous-page", previousPage);
			
			
			getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/showMovies.jsp").forward(request,response);
			
			rs.close(); 
			statement.close();
			dbcon.close();   
	  
		}
		catch (SQLException ex) {
		 	while (ex != null) {
				System.out.println ("SQL Exception in MovieList:  " + ex.getMessage ());
				ex = ex.getNextException ();
			} 
		}
		catch(java.lang.Exception ex) {
		}  
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	   doGet(request, response);
	}
	
	public ArrayList<String> getgenreList(String movie){ 
		ArrayList<String> genreList = new ArrayList<String>(); 
		try {
			PreparedStatement statement  = dbcon.prepareStatement("Select g.name " 
					  + "from movies as m, genres as g, genres_in_movies as gm "
					  +"where m.title = ? "
					  +"and g.id = gm.genre_id "
					  +"and gm.movie_id = m.id");
			statement.setString(1, movie);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){ 
				genreList.add(rs.getString(1)); 
			}
			rs.close();
			statement.close();
			 
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		return genreList; 
	}
	
	public ArrayList<Star>getstarList(String movie){ 
		ArrayList<Star> starList = new ArrayList<Star>(); 
		try{
			  PreparedStatement statement  = dbcon.prepareStatement("Select s.* " 
					  + "from movies as m, stars as s , stars_in_movies as sm "
					  +"where m.title = ? "
					  +"and s.id = sm.star_id "
					  +"and sm.movie_id = m.id");
			statement.setString(1, movie);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){ 
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name"); 
				int star_id = rs.getInt("id"); 
				String star_dob = rs.getString("dob"); 
				String star_photo = rs.getString("photo_url"); 
				Star addStar = new Star(star_id, first_name,last_name,star_dob,star_photo); 
				starList.add(addStar);
			}
			rs.close();
			statement.close();
		}
		catch(Exception e){ 
			System.out.println(e.getMessage());
		}
	return starList;
	}
	
	//source: http://stackoverflow.com/questions/327765/wildcards-in-java-preparedstatements
	public static String likeSanitize(String input) {
		return input
		   .replace("!", "!!")
		   .replace("%", "!%")
		   .replace("_", "!_")
		   .replace("[", "![");
	}

	private synchronized void getConnection(boolean pooledConnection) {
		if(pooledConnection) {
			try {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource ds = (DataSource) envCtx.lookup("jdbc/Moviedb");
				dbcon = ds.getConnection();
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else {
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
	}
}

   