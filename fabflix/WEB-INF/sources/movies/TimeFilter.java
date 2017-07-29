package movies;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;


public class TimeFilter implements Filter {
	 //private FilterConfig filterConfig;

	  public void init( FilterConfig filterConfig ) {
	    //this.filterConfig = filterConfig;
	  }
	  

	  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain ) 
	  {
			try 
			{
			long startTime = System.nanoTime();
			
			filterChain.doFilter( request, response ); //run the search
			
			
			long endTime = System.nanoTime();
			long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
			
			//String requestURI = ( (HttpServletRequest) request ).getRequestURI();
			//System.out.println( requestURI + " took -> " + ( endTime - startTime ) + " ms " );
			System.out.println("Servlet:" + elapsedTime); //search servlet total execution time
			}
			catch(Exception e) {
			System.out.println( e.getMessage() );
			}
			
	 }
	public void destroy () {
	}



}