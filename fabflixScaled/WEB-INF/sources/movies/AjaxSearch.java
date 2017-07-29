package movies;

//import Movie;



import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONArray;

public class AjaxSearch extends HttpServlet {
	private Connection dbcon;
	public static final int MAX_NUM_MOVIES_TO_SHOW = 5; 
   private static final long serialVersionUID = 1L;
  
	public String getServletInfo() {
		return "Ajax Search servlet";
	}
	
	// Use http GET
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		try {
		
			
			String titleToTokenize = request.getParameter("term");
			System.out.println(titleToTokenize); //debugging
			
			//String titleToTokenize = "ride and P";
			
		
		     String[] tokens = titleToTokenize.split("\\s");
		     for (int i=0; i < tokens.length; i++)
		     {
		         System.out.println("Token#" +i + ": " + tokens[i]); //debugging
		     }
		    connect();
			//String[] titleArr = getMovieTitles(tokens);
		    ArrayList<Movie> movieList = getMovieTitles(tokens);
			disconnect();
			
		    response.setContentType("text/html");
	        response.setHeader("Cache-control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Expires", "-1");
	       
	        JSONArray arrayObj= new JSONArray();
	       
			PrintWriter out = response.getWriter();
			
			//while title array still has titles
			
			for(Movie movie: movieList) {
				
				/*out.println("<!DOCTYPE html><html><head></head><body><p>" 
						+ "<td><a href=\"" + request.getContextPath() + "/movie/?id=" 
						+ movie.getId() + "\">" + movie.getTitle() + "</a></td>"  
						+ "</p></body></html>"); */
					
					arrayObj.put(movie.getTitle());
			}
			
		    out.println(arrayObj.toString());
			
		}
			catch (Exception e) {
			System.out.println(e.getMessage());
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

	private  ArrayList<Movie> getMovieTitles(String[] tokens) {
		try {
			String[] titleArr = new String[MAX_NUM_MOVIES_TO_SHOW];
			Statement statement = dbcon.createStatement();
			String query = "SELECT * FROM movies WHERE MATCH(title) AGAINST ";
			
			
			// SELECT * FROM movies WHERE MATCH (title) AGAINST ('token1*' IN BOOLEAN MODE) 
			// AND MATCH(title) AGAINST ('token2*' IN BOOLEAN MODE) ... 
			// AND MATCH(title) AGAINST ('tokenN*' IN BOOLEAN MODE);
			//forming the proper query
			
			for(int i = 0; i < tokens.length; i++)
			{
			
				// "("\''" + token + "\'*\' IN BOOLEAN MODE)"
				
					query = query.concat("(\'" + tokens[i] + "*\' IN BOOLEAN MODE)");
					
				//if token is not the last token add "AND MATCH(title) AGAINST "
					if(i != (tokens.length - 1) )
					{
						query = query.concat("AND MATCH(title) AGAINST ");
					}
			}
			
			query = query.concat(";");
			
			ResultSet rs = statement.executeQuery(query);
			
		    ArrayList<Movie> movieList = new ArrayList<Movie>();
			int indexToAdd = 0;
            while (rs.next() && (indexToAdd < MAX_NUM_MOVIES_TO_SHOW) ) //Only want 5 movies to show
            {
       
            	/*
                titleArr[j] = rs.getString("title"); //String m_title = rs.getString(2);
                */
              
            	int m_id = rs.getInt(1); 
    			String m_title = rs.getString(2);
    			String m_year = rs.getString(3); 
    			String m_director = rs.getString(4);
    			String m_banner_url = rs.getString(5);
    			String m_trailer_url = rs.getString(6);

    			Movie movieToAdd = new Movie(m_id, m_title, m_year, m_director, m_banner_url, m_trailer_url);
                movieList.add(indexToAdd, movieToAdd);
    			indexToAdd++;
            }

			rs.close();
			statement.close();
			return movieList;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}

}
