package movies;
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet {
    private Connection dbcon;
    private boolean loginStatus = false;

    public String getServletInfo() {
       return "Login Servlet";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        String sessionURL = response.encodeRedirectURL(request.getContextPath());
        response.sendRedirect(sessionURL);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);
        boolean loggedIn = isUserLoggedIn(session);

        // If Loggined in already redirect to home page.
        if(loggedIn) {
            String sessionURL = response.encodeRedirectURL(request.getContextPath()+"/home");
            response.sendRedirect(sessionURL);
        }

        try {
            connect();
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            updateLoginStatus(email, password);
            disconnect();
         
            // Redirect to main page
            request.getSession().setAttribute("loginStatus", loginStatus);
            String sessionURL = response.encodeRedirectURL(request.getContextPath()+"/home");
            response.sendRedirect(sessionURL);
        }
        catch(java.lang.Exception ex) {
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

    private boolean isUserLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("loginStatus") != null && (boolean)session.getAttribute("loginStatus");
    }
}

