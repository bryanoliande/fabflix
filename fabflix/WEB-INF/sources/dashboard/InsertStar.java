package dashboard;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import movies.*;

public class InsertStar extends HttpServlet {
	
	// Use http GET
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{ 
		
		String firstName = request.getParameter("starFirstName");
		String lastName = request.getParameter("starLastName"); 
		String dob = request.getParameter("starDOB"); 
		String photoUrl = request.getParameter("starPhotoUrl"); 
		
		if(dob == ""){ 
			dob = null; 
		}
		else{ 
			dob = "'" + dob + "'";
		}
		if(photoUrl == ""){ 
			photoUrl = null;
		}
		else{ 
			photoUrl = "'" + photoUrl + "'";
		}

		StoredProcedures newStatements = new StoredProcedures("root","cs122b","jdbc:mysql://localhost:3306/moviedb"); 
		HttpSession session = request.getSession();                              
		if(newStatements.checkIfStarExists(firstName, lastName, dob)){ 
			request.setAttribute("insert-check", "star exists");
		}
		else{
			newStatements.insertStar(firstName, lastName, dob, photoUrl);
			request.setAttribute("insert-check", "passed");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/_dashboard/insertstar");
		dispatcher.forward(request, response); 
	}
	
}