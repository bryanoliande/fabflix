import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ProcessLog {


	public static void main(String[] args) {
		
		
		
		double averageJDBC = 0;
		double averageServlet = 0;
		double numJDBC = 0;
		double numServlet = 0;
		
		File file = new File("/var/lib/tomcat7/logs/catalina.out");
		try{
		Scanner input = new Scanner(file);
		

		while(input.hasNext()) {
		    String nextLine = input.nextLine();
		   
		    
		     String[] result = nextLine.split("\\s"); 
		     if(result[0].equalsIgnoreCase("JDBC:"))
		     {
		    	//JDBC: 28576089
		    	 averageJDBC += Double.parseDouble(result[1]);
		    	 ++numJDBC;
		     }
		     
		    result = nextLine.split(":");
		     if(result[0].equalsIgnoreCase("Servlet"))
		     {
		    	//Servlet:399561764
		    	 averageServlet += Double.parseDouble(result[1]);
		    	 ++numServlet;
		     }
		

		}
		input.close();
		
		//average then convert ns to ms
		averageJDBC = ( (averageJDBC / numJDBC) / 1000 ); 
		averageServlet = ( (averageServlet / numServlet) / 1000 );
		
		System.out.println("Average JDBC: " + averageJDBC + "ms");
		System.out.println("Average Servlet: " + averageServlet + "ms");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

}