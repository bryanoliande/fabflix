package dashboard;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import movies.*;

public class AddInfo  extends HttpServlet {
	
	// Use http GET
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{ 
		
		String movieID = "0"; //keep
		String movieTitle = request.getParameter("movieTitle"); //required
		String movieYear = request.getParameter("year");	//required
		String movieDirector = request.getParameter("director"); //not required
		
		String movieBannerURL = "";
		String movieTrailerURL = "";
		
		String starID = "0"; //keep
		String starFirstName = request.getParameter("starFirstName");
		String starLastName = request.getParameter("starLastName"); 
		String starDOB= request.getParameter("starDOB"); //set null if no DOB given As a string in either 'YYYY-MM-DD' 
		String starPhotoURL = request.getParameter("starPhotoUrl"); //not required
		String genreID = "0"; //keep
		String genreName = request.getParameter("genre"); //required
		String log; 
		
	
		
		if(starDOB == ""){ 
			starDOB = null; 
		}
			/*dont need this for stored proc call
		else{ 
			starDOB = "'" + starDOB + "'";
		}
		if(starPhotoURL == ""){ 
			starPhotoURL = null;
		}
		else{ 
			starPhotoURL = "'" + starPhotoURL + "'";
		}*/
		
		
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedb","root", "cs122b");
			//StoredProcedures newStatements = new StoredProcedures("root","cs122b","jdbc:mysql://localhost:3306/moviedb");
		
			CallableStatement myStmt = connection.prepareCall("{call add_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		myStmt.setString(1, movieID);
		myStmt.setString(2, movieTitle);
		myStmt.setString(3, movieYear);
		myStmt.setString(4, movieDirector);
		myStmt.setString(5, movieBannerURL);
		myStmt.setString(6, movieTrailerURL);
		myStmt.setString(7, starID);
		myStmt.setString(8, starFirstName);
		myStmt.setString(9, starLastName);

		myStmt.setString(10, starDOB);
		myStmt.setString(11, starPhotoURL);
		myStmt.setString(12, genreID);
		myStmt.setString(13, genreName);

		myStmt.registerOutParameter(14, java.sql.Types.VARCHAR);
		
		//call
		myStmt.execute();
		
		log = myStmt.getString(14);
		//System.out.println(log);
		log.trim();


		//StoredProcedures newStatements = new StoredProcedures("root","cs122b","jdbc:mysql://localhost:3306/moviedb"); 
		HttpSession session = request.getSession();
		
		session.setAttribute("form-title", movieTitle);
		session.setAttribute("form-year", movieYear);
		session.setAttribute("form-director", movieDirector);
		request.setAttribute("log", log);
		request.setAttribute("insert-check", "passed");
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/_dashboard/addinfo");
		dispatcher.forward(request, response);
		}
		catch(Exception e){System.out.println(e.getMessage());}
		
		
	}
	
}