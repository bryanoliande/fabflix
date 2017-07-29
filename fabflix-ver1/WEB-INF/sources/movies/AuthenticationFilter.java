package movies;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class AuthenticationFilter implements Filter {
	private String loginPage;

	public void init(FilterConfig config) throws ServletException {
		loginPage = config.getInitParameter("loginServlet");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
		throws java.io.IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		String contextPath = request.getContextPath() + "/";
		String uriPath = request.getRequestURI();

		// Boolean Checks
		boolean loggedIn = isUserLoggedIn(session);
		boolean atIndexPage = uriPath.equals(contextPath);
		boolean atLoginPage = uriPath.equals(request.getContextPath() + loginPage);
		
		// If loggined in, allow filter to pass through.
		if(loggedIn && !atIndexPage) {
			//System.out.println("logged in and not at index page");
			chain.doFilter(request, response);
		}
		else if(loggedIn && atIndexPage) {
			//System.out.println("logged in and at index page");
			String sessionURL = response.encodeRedirectURL(request.getContextPath()+"/home");
			response.sendRedirect(sessionURL);
		}
		else if(!loggedIn && atLoginPage) {
			//System.out.println("not logged in and at login page");
			chain.doFilter(request, response);
		}
		else if(!loggedIn && atIndexPage) {
			//System.out.println("not logged in and at index page");
			chain.doFilter(request, response);
		}
		else if(!loggedIn && !atIndexPage) {
			//System.out.println("not logged in and not at index page");
			String sessionURL = response.encodeRedirectURL(request.getContextPath());
			response.sendRedirect(sessionURL);
		}
	}
	public void destroy () {
	}

	private boolean isUserLoggedIn(HttpSession session) {
		return session != null && session.getAttribute("loginStatus") != null && (boolean)session.getAttribute("loginStatus");
	}

}

