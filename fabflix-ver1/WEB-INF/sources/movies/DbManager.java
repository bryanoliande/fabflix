package movies;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*; 

public class DbManager {
	String user; 
	String pass;
	String loginURL;
	Connection connection; 
	public DbManager(String user, String pass, String loginURL){ 
		this.user = user; 
		this.pass = pass; 
		this.loginURL = loginURL; 
		try{ 
			connect(user,pass,loginURL);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public boolean connect(String username, String password, String loginURL)throws Exception{ 
		// Incorporate mySQL driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	
		// Connect to the test database
		try {
			connection = DriverManager.getConnection(loginURL,username, password);
			return true; 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(); 
		}
		
	}
	
	/*
	public ArrayList<Movie> select(String columns, String tables, String condition )throws Exception{ 
		ArrayList<Movie> movieList = new ArrayList<Movie>(); 
		Statement rs = connection.createStatement(); 
		ResultSet data = null; 
		
		if(condition.equals("")){ 
			data = rs.executeQuery("select " + columns +" from " + tables + ";"); 
		}
		else{ 
			data = rs.executeQuery("select " + columns + " from " + tables +" where " + condition + ";"); 
		}
		
	}*/
	
	public void closeConnection(){ 
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
