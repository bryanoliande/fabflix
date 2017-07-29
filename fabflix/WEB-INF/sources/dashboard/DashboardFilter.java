package dashboard;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class DashboardFilter implements Filter {
	private String loginPage;
	private String dashboardIndex;

	public void init(FilterConfig config) throws ServletException {
		loginPage = config.getInitParameter("dashboardLogin");
		dashboardIndex = config.getInitParameter("dashboard");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
		throws java.io.IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		String contextPath = request.getContextPath() + "/";
		String uriPath = request.getRequestURI();

		// Boolean Checks
		//boolean loggedIn = isUserLoggedIn(session);
		boolean loggedIn = true; // for project 5
		boolean atLoginPage = uriPath.equals(request.getContextPath() + loginPage);
		boolean atDashboardIndex = uriPath.equals(request.getContextPath() + dashboardIndex);
		boolean atDashboardPage = uriPath.startsWith(request.getContextPath() + dashboardIndex);
		
		if(loggedIn && atLoginPage) {
			//System.out.println("logged in and at dashboard login page");
			String sessionURL = response.encodeRedirectURL(request.getContextPath() + dashboardIndex);
			response.sendRedirect(sessionURL);
		}
		else if(loggedIn && atDashboardIndex) {
			//System.out.println("logged in and at a dashboard index");
			String sessionURL = response.encodeRedirectURL(request.getContextPath() + dashboardIndex + "/home");
			response.sendRedirect(sessionURL);
		}
		else if(loggedIn && atDashboardPage) {
			chain.doFilter(request, response);
		}
		else if(!loggedIn && atLoginPage || !loggedIn && atDashboardIndex) {
			//System.out.println("not logged in and at login page or a dashboard page");
			chain.doFilter(request, response);
		}
		else {
			String sessionURL = response.encodeRedirectURL(request.getContextPath() + dashboardIndex);
			response.sendRedirect(sessionURL);
		}
		
	}
	public void destroy () {
	}

	private boolean isUserLoggedIn(HttpSession session) {
		return session != null && session.getAttribute("employeeLoginStatus") != null && (boolean)session.getAttribute("employeeLoginStatus");
	}

}

