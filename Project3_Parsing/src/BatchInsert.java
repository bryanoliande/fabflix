import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

import movies.*;


public class BatchInsert {
	private ArrayList<Star> starList;
	private ArrayList<Movie> movieList;
	private ArrayList<String> genreList; 
	private HashMap<String, ArrayList<Star>> titleStarMap;
	
	private HashMap<String, Integer> movieToIdMap = new HashMap<String,Integer>();
	//private HashMap<String, Integer> starToIdMap;
	private HashMap<String, Integer> starToIdMap = new HashMap<String,Integer>();
	private HashMap<String,Integer> genreToIdMap = new HashMap<String,Integer>();
	
	private Connection dbcon;
	private String loginUser; 
	private String loginPassword; 
	private String loginUrl; 
	
	BatchInsert(String user, String pass,String url) {
	}
	
	BatchInsert(ArrayList<Star> starList, ArrayList<Movie> movieList, ArrayList<String>genreList, HashMap<String, ArrayList<Star>> titleStarMap,String user, String pass,String url) {
		this.starList = starList;
		this.movieList = movieList;
		this.genreList = genreList; 
		this.titleStarMap = titleStarMap;
		this.loginUser = user;
		this.loginPassword = pass;
		this.loginUrl = url;
	}
	
	public void insertStarList() {
		// call list to batch insert.
		int count = 0; 
		try{ 
			Statement statement = dbcon.createStatement(); 
			String batchInsert = "Insert into stars(first_name, last_name,dob) values ";
			Pattern p = Pattern.compile("[0-9]{4}");
			for(Star star:starList){ 
				count++;
				Matcher m = p.matcher(star.getDOB());
				if(m.matches()){
					String dob = "'"+star.getDOB()+"-01-01'";
					batchInsert += "('"+star.getFirstName()+"','"+star.getLastName()+"',DATE("+dob+")),";
				}
				else{
					batchInsert += "('"+star.getFirstName()+"','"+star.getLastName()+"',null),";
				}
			}
			if(count > 0){
				batchInsert = batchInsert.substring(0,batchInsert.length()-1)+";";
			}
			statement.executeUpdate(batchInsert);
			statement.close();
		}
		catch(Exception e){ 
			System.out.println("Error: insertStar");
			e.printStackTrace();
		}
	}
	
	public void insertMovieList() {
		// call list to batch insert.
		int count = 0; 
		try{ 
			Statement statement = dbcon.createStatement(); 
			String batchInsert = "Insert into movies values ";
			Pattern p = Pattern.compile("[0-9]{4}");
			for(Movie movie:movieList){ 
				String title = "'" + movie.getTitle() + "'"; 
				String dir = "'" + movie.getDirector() + "'";
				String year = movie.getYear(); 
				Matcher m = p.matcher(year);
				if(!m.matches()){
					System.out.println(year + " is not a valid year");
					continue;
				}
				String banner = "'" + movie.getBannerURL() + "'";
				String trailer = "'" + movie.getTrailerURL() +"'";
				count++;
				batchInsert += "(0,"+title+","+year+","+dir+","+banner+","+trailer+"),";
			}
			if(count > 0){
				batchInsert = batchInsert.substring(0,batchInsert.length()-1)+";";
			}
			statement.executeUpdate(batchInsert);
			statement.close();
		}
		catch(Exception e){ 
			System.out.println("Error: insertMovie");
			e.printStackTrace();
		}
	}
	
	public void insertGenreList(){ 
		int count = 0;
		try{ 
			Statement statement = dbcon.createStatement();
			String batchInsert = "Insert into genres values "; 
			
			for(String genre:genreList){
				count++; 
				batchInsert += "(0,'"+genre+"'),";
			}
			/*
			for(Movie movie:movieList){
				for(String name:movie.getGenreList()){
					if(genreToIdMap.containsKey(name)){
						System.out.println(name + " already exists in the database");
						continue;
					}
					if(name.equals("")){
						System.out.println("No genre given");
						continue;
					}
					count++;
					batchInsert += "(0,'"+name+"'),"; 
				}
			}*/
			if(count > 0){
				batchInsert = batchInsert.substring(0,batchInsert.length()-1)+";";
				statement.executeUpdate(batchInsert);
			}
			statement.close();
		}
		catch(Exception e){ 
			System.out.println("Error: insertGenre");
			e.printStackTrace();
		}
	}
	
	public void insertTitleStarMap() {
		getStarToIdMap();
		getMovieToIdMap();
		int count = 0; 
		// 1. Check if star exists
		// 2. Check if movie exists
		// 3. Insert (Star in Movie)
		try{ 
			Statement statement = dbcon.createStatement(); 
			String batchInsert = "Insert into stars_in_movies values "; 
			for(String movie : titleStarMap.keySet()){ 
				for(Star star: titleStarMap.get(movie)){
					String starName = star.getFirstName() + " " + star.getLastName();
					if(movieToIdMap.containsKey(movie)){ 
						if(starToIdMap.containsKey(starName)){
							count++;
							batchInsert += "("+starToIdMap.get(starName)+","+movieToIdMap.get(movie)+"),";
						}
						else{
							System.out.println(starName + " does not exist in the database");
							continue;
						}
					}
					else{ 
						System.out.println(movie + " does not exist in the database");
						continue;
					}
				}
			}
			if(count >0){
				batchInsert = batchInsert.substring(0,batchInsert.length()-1)+";";
				statement.executeUpdate(batchInsert);
			}
			statement.close();
		}
		catch(Exception e){ 
			e.printStackTrace();
		}
		
	}
	
	public void insertGenresInMovies(){ 
		getGenreToIdMap(); 
		getMovieToIdMap(); 
		
		int count = 0;
		
		try{
			Statement statement = dbcon.createStatement();
			String batchInsert = "Insert into genres_in_movies values ";
			
			for(Movie movie : movieList){
				for(String genre : movie.getGenreList()){
					if(movieToIdMap.get(movie.getTitle()) == null){
						System.out.println(movie.getTitle() + " does not exist in the database");
						continue;
					}
					else{
						if(genreToIdMap.containsKey(genre)){
							count++;
							batchInsert += "("+genreToIdMap.get(genre)+","+movieToIdMap.get(movie.getTitle())+"),";
						}
						else{
							System.out.println(genre + " does not exist in the database");
							continue;
						}
					}
				}
			}
			if(count >0){
				batchInsert = batchInsert.substring(0,batchInsert.length()-1)+";";
				statement.executeUpdate(batchInsert);
			}
			statement.close();
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void getStarToIdMap() {
		// call to database
		// Store to map (StarName, id)
		try{ 
			Statement statement = dbcon.createStatement(); 
			ResultSet rs = statement.executeQuery("Select s.* from stars as s;");
			while(rs.next()){ 
				String name = rs.getString("first_name") + " " + rs.getString("last_name"); 
				int id = rs.getInt("id"); 
				if(starToIdMap.containsKey(name)){
					System.out.println(name + " has duplicates in the database");
					starToIdMap.remove(name);
					continue;
				}
				starToIdMap.put(name, id); 
			}
			rs.close(); 
			statement.close();
		}
		catch(Exception e){ 
			e.printStackTrace();
		}
	}
	
	private void getMovieToIdMap() {
		// call to database
		// Store to map (MovieName, id)
		try{ 
			Statement statement = dbcon.createStatement(); 
			ResultSet rs = statement.executeQuery("Select m.* from movies as m;");
			while(rs.next()){ 
				movieToIdMap.put(rs.getString("title"), rs.getInt("id")); 
			}
			rs.close();
			statement.close();
		}
		catch(Exception e){ 
			e.printStackTrace();
		}
	}
	
	public void getGenreToIdMap(){
		try{
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select g.* from genres as g;");
			while(rs.next()){
				genreToIdMap.put(rs.getString("name"), rs.getInt("id"));
			}
			rs.close();
			statement.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	private void connect() {
		
		try {
				System.out.println("Creating Driver");
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				System.out.println("Created Driver");
				System.out.println("Getting connection");
				dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPassword);
				System.out.println("Got connection");
		}
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
				ex = ex.getNextException ();
			}  
		} 
		catch (Exception e) {
			System.out.println(e.toString());
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
	
	public void commitUpdate(){ 
		connect(); 
		try {		
			dbcon.setAutoCommit(false);
			insertStarList();
			insertMovieList(); 
			insertGenreList(); 
			dbcon.commit();
			insertTitleStarMap();
			insertGenresInMovies();
			dbcon.commit();
			dbcon.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disconnect();
		
	}
}