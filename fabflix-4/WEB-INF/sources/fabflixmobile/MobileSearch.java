package fabflixmobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import movies.*;

public class MobileSearch extends HttpServlet {
    public String getServletInfo() {
        return "Search Servlet";
     }

     public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException
     {
         response.setContentType("application/json"); 
         
         PrintWriter out = response.getWriter();
         
         out.println("{movielist:[");
         
         try{ 
        	 StoredProcedures procedure = new StoredProcedures("root","cs122b","jdbc:mysql://localhost:3306/moviedb");
        	 String[] titleArr = request.getParameter("movieTitle").split("/^%20/");
        	 
        	 ArrayList<Movie> movieList = procedure.getMovieTitles(titleArr);
        	 int count = 1; 
        	 for(Movie movie:movieList){
        		 if(count < movieList.size()){
        			 out.println("\t{title:\""+movie.getTitle()+"\"},");
        		 }
        		 else{ 
        			 out.println("\t{title:\""+movie.getTitle()+"\"}");
        		 }
        		 count++;
        	 }
        	 out.println("]}");
         }
         catch(Exception e){ 
        	 out.println("{"+e.getMessage()+"}");
         }
     }

     public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException
     {
    	 doGet(request, response);
     }
     
     
}
