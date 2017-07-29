package fabflixmobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MobileLogin extends HttpServlet{
    private Connection dbcon;
    private boolean loginStatus = false;

    public String getServletInfo() {
       return "Mobile Login Servlet";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
       // HttpSession session = request.getSession(false);
        
        response.setContentType("application/json"); 
        
        PrintWriter out = response.getWriter();
        

        // If Loggined in already redirect to home page.

        try {
            connect();
            //String email = request.getParameter("email");
            //String password = request.getParameter("password");
            //updateLoginStatus(email, password);
            HashMap<String,String> loginInfo = getLoginInfo();
            disconnect();
            out.println("{loginList:[");
            
            int count = 1; 
            for(String email:loginInfo.keySet()){
            	if(count <loginInfo.size()){
            		out.println("{email:"+email+",\npassword:"+loginInfo.get(email)+"},");
            	}
            	else{ 
            		out.println("{email:"+email+",\npassword:"+loginInfo.get(email)+"}");
            	}
            	count++;
            }
            out.println("]}");
         /*
            if(loginStatus) {
            	out.println("{LoginStatus:'true'}");
            }
            else{ 
                out.println("{LoginStatus:'false'}");
            }
           */
            // Redirect to main page
        }
        catch(java.lang.Exception ex) {
        	out.println("{" + ex.getMessage()+ "}");
            return;
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

    private void updateLoginStatus(String email, String password) {
        try {
            Statement statement = dbcon.createStatement();       
            ResultSet rs = statement.executeQuery("SELECT * from customers where customers.email = '" + email 
                                                        + "' and customers.password = '" + password + "';");          
            loginStatus = rs.next();    
            rs.close();
            statement.close();
        }
        catch (Exception e) {
            System.out.println("Invalid SQL Query ");
        }
    }
    
    private HashMap<String,String> getLoginInfo(){ 
    	HashMap<String,String> loginInfo = new HashMap<String,String>();
        try {
            Statement statement = dbcon.createStatement();       
            ResultSet rs = statement.executeQuery("SELECT * from customers;");    
            
            while(rs.next()){ 
            	loginInfo.put(rs.getString("email"), rs.getString("password"));
            }
            rs.close();
            statement.close();
        }
        catch (Exception e) {
            System.out.println("Invalid SQL Query ");
        }
        return loginInfo;
    }
}
