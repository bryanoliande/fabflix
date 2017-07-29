package movies;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class StoredProcedures {
	private Connection dbcon;
	private String loginUser; 
	private String loginPassword; 
	private String loginUrl; 
	public StoredProcedures(String user, String pass,String url){ 
		this.loginUser = user; 
		this.loginPassword = pass; 
		this.loginUrl = url; 
	}
	
	private void connect() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPassword);
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
	
	public HashMap<String,HashMap<String,String>> getMetaData(String table){ 
		//returns a map of the table's attributes and types 
		HashMap<String,HashMap<String,String>>metaData = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String>attributeMap = new HashMap<String,String>();
		
		connect(); 
		
		try{ 
			Statement statement = dbcon.createStatement(); 
			ResultSet rs = statement.executeQuery("Select " + table + ".* from " + table + ";"); 
			ResultSetMetaData meta = rs.getMetaData(); 
			
			for(int i = 1; i<=meta.getColumnCount();i++){ 
				attributeMap.put(meta.getColumnName(i),meta.getColumnTypeName(i));
			}
			metaData.put(meta.getTableName(1), attributeMap);
		}
		catch(Exception e){ 
			System.out.println(e.getMessage());
		}
		disconnect(); 
		return metaData; 
	}	

    public HashMap<String,Integer> getGenresInAllMovies(){ 
    	//return a list of genres that appear in movies 
		 HashMap<String,Integer> genreMap = new HashMap<String,Integer>();  
		 connect(); 
		 try {

             Statement statement = dbcon.createStatement(); 
         	 ResultSet rs = statement.executeQuery("Select g.* " 
						  + "from movies as m, genres as g, genres_in_movies as gm "
						  +" where and g.id = gm.genre_id "
						  +"and gm.movie_id = m.id;");
         	
         	while(rs.next()){ 
         		genreMap.put(rs.getString("name"), rs.getInt("id")); 
         	}
         	rs.close();
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		
		disconnect(); 
		return genreMap; 
    }
    
    public HashMap<String,Integer> getGenresInAMovie(String movie){ 
    	//return a list of genres that appear in a movie 
		 HashMap<String,Integer> genreMap = new HashMap<String,Integer>();  
		 connect(); 
		 try {

             Statement statement = dbcon.createStatement(); 
         	 ResultSet rs = statement.executeQuery("Select g.* " 
						  + "from movies as m, genres as g, genres_in_movies as gm "
						  +"where m.title = \"" + movie + "\" "
						  +" and g.id = gm.genre_id "
						  +"and gm.movie_id = m.id;");
         	
         	while(rs.next()){ 
         		genreMap.put(rs.getString("name"), rs.getInt("id")); 
         	}
         	rs.close();
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		
		disconnect(); 
		return genreMap; 
    }
    
    public ArrayList<Star>getStarsInAMovie(String movie){ 
    	//get a list of stars in a given movie
		ArrayList<Star> starList = new ArrayList<Star>(); 
		connect(); 
		try{
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select s.* " 
				  + "from movies as m, stars as s , stars_in_movies as sm "
				  +"where m.title = \"" + movie + "\" "
				  +"and s.id = sm.star_id "
				  +"and sm.movie_id = m.id;");
			while(rs.next()){ 
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name"); 
				int star_id = rs.getInt("id"); 
				String star_dob = rs.getString("dob"); 
				String star_photo = rs.getString("photo_url"); 
				Star addStar = new Star(star_id, first_name,last_name,star_dob,star_photo); 
				starList.add(addStar);
			}
		}
		catch(Exception e){ 
			System.out.println(e.getMessage());
		}
		disconnect(); 
		return starList;
    }
    
    public HashMap<String,Integer> getAllGenres(){
    	//return a list of all genres in the database
    	HashMap<String,Integer> genreMap = new HashMap<String,Integer>(); 
    	connect(); 
		 try {

             Statement statement = dbcon.createStatement(); 
         	 ResultSet rs = statement.executeQuery("Select g.* " 
						  + " from genres as g;");
         	
         	while(rs.next()){ 
         		genreMap.put(rs.getString("name"),rs.getInt("id")); 
         	}
         	rs.close();
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		
		disconnect(); 
		return genreMap;
    }
    
    public ArrayList<Star> getAllStars(){
    	//return a list of all stars in the database
		 ArrayList<Star> starList = new ArrayList<Star>(); 
		 connect(); 
		 try {

             Statement statement = dbcon.createStatement(); 
         	 ResultSet rs = statement.executeQuery("Select s.* " 
						  + " from stars as s;");
         	
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
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		
		disconnect(); 
		return starList;
    }
    
    public boolean checkIfStarExists(String firstName, String lastName,String dob){
    	//return a list of all stars in the database
		 connect(); 
		 try {

             Statement statement = dbcon.createStatement(); 
             ResultSet rs = null;
             if(dob != null){
            	 rs = statement.executeQuery("Select s.* " 
						  	+ " from stars as s "
						  	+ " where s.first_name = '"+ firstName + "' "
						  	+ " and s.last_name = '"+lastName +"' "
						  	+ " and s.dob = "+dob+";");
            }
             else{
            	 rs = statement.executeQuery("Select s.* " 
						  	+ " from stars as s "
						  	+ " where s.first_name = '"+ firstName + "' "
						  	+ " and s.last_name = '"+lastName +"';");
             }
         	if(!rs.next()){ 
         		return false; 
         	}
         	rs.close();
         	return true; 
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		return true; 
    }
    
	public int insert(String table, String insert) throws Exception { 
		int count = 0; 
		connect(); 
		Statement rs = dbcon.createStatement(); 
		
		count = rs.executeUpdate("insert into " + table + " values("+ insert+");");
		
		rs.close(); 
		disconnect(); 
		return count;
	}
	
	public void insertStar(String firstName, String lastName, String dob, String photoUrl){
		//insert a star to the table 
		connect(); 
		try {
			Statement rs = dbcon.createStatement(); 
			rs.executeUpdate("insert into stars values(0,'"+firstName+"','"+lastName+"',"+dob+","+photoUrl+");");
			rs.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disconnect(); 
	}
	
	
	public ArrayList<Movie> getMovies(){ 
		//returns a list of all the movies 
		ArrayList<Movie> movieList = new ArrayList<Movie>(); 
		connect(); 
		
		 try {

             Statement statement = dbcon.createStatement(); 
         	 ResultSet rs = statement.executeQuery("Select m.* " 
						  + " from movies as m;");
         	
 			while(rs.next()){ 
    			int m_id = rs.getInt("id");
    			String m_title = rs.getString("title");
    			String m_year = Integer.toString(rs.getInt("year")); 
    			String m_director = rs.getString("director"); 
    			String m_banner = rs.getString("banner_url"); 
    			String m_trailer = rs.getString("trailer_url"); 

        		
            	Movie addMovie = new Movie(m_id,m_title,m_year,m_director,m_banner,m_trailer); 
            	ArrayList<String>genreList = new ArrayList<String>();
            	genreList.addAll(getGenresInAMovie(m_title).keySet());
            	
            	ArrayList<Star>starList = getStarsInAMovie(m_title); 
            	
            	addMovie.setGenreList(genreList);
            	addMovie.setStarList(starList);       
            	
            	movieList.add(addMovie); 
			}
 			
         	rs.close();
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		
		
		disconnect(); 
		return movieList;
	}
	
	public ArrayList<Movie> getMoviesByTitle(String title){ 
		//returns a list of all the movies by title (or similar title)
		ArrayList<Movie> movieList = new ArrayList<Movie>(); 
		connect(); 
		
		 try {

             Statement statement = dbcon.createStatement(); 
         	 ResultSet rs = statement.executeQuery("Select m.* " 
						  + " from movies as m"
						  + " where m.title like \""+ title + "%\";");
         	
 			while(rs.next()){ 
    			int m_id = rs.getInt("id");
    			String m_title = rs.getString("title");
    			String m_year = Integer.toString(rs.getInt("year")); 
    			String m_director = rs.getString("director"); 
    			String m_banner = rs.getString("banner_url"); 
    			String m_trailer = rs.getString("trailer_url"); 

        		
            	Movie addMovie = new Movie(m_id,m_title,m_year,m_director,m_banner,m_trailer); 
            	ArrayList<String>genreList = new ArrayList<String>();
            	genreList.addAll(getGenresInAMovie(m_title).keySet());
            	
            	ArrayList<Star>starList = getStarsInAMovie(m_title); 
            	
            	addMovie.setGenreList(genreList);
            	addMovie.setStarList(starList);       
            	
            	movieList.add(addMovie); 
			}
 			
         	rs.close();
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		
		
		disconnect(); 
		return movieList;
	}
	
	public ArrayList<Movie> getMovieTitles(String[] tokens) {
		connect();
		try {
			String[] titleArr = new String[tokens.length];
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
            while (rs.next()) //Only want 5 movies to show
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
			disconnect();
			return movieList;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
	
	
}


