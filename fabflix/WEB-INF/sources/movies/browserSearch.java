package movies;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class browserSearch extends HttpServlet {
	
    private Connection dbcon;
    private String loginUser = "root";
    private String loginPasswd = "cs122b";//change back to cs122b
    private String loginUrl ="jdbc:mysql://localhost:3306/moviedb"; //"jdbc:mysql://localhost:3306/moviedb";

    public String getServletInfo()
    {
       return "browserSearch servlet";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");    // Response mime type
        
        String genreCheck = request.getParameter("genre");
        String titleCheck = request.getParameter("title");
        String sortCheck = request.getParameter("sort");
        String previousPage = request.getRequestURI() + "?" + request.getQueryString(); 
        int itemlimitCheck = 5; 
        int pageNumber = 1; 
        if(request.getParameter("ipp")!= null){ 
            itemlimitCheck = Integer.parseInt(request.getParameter("ipp"));
            pageNumber = Integer.parseInt(request.getParameter("page"));
        }
		int offset = (itemlimitCheck * (pageNumber-1)); 

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        ArrayList<Movie>movieList = new ArrayList<Movie>(); 

        out.println("<HTML><HEAD><TITLE>SEARCH RESULT </TITLE></HEAD>");
        out.println("<BODY><H1>MovieDB</H1>");

        
        try {
                
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
                // Declare our statement
                Statement statement = dbcon.createStatement();
                
                
                
                if(genreCheck!=null){ 
                	ResultSet rs = null; 
                	if(sortCheck == null){
                		if(itemlimitCheck ==0){ 
                			rs = statement.executeQuery("Select m.* " 
                									+ "from movies as m, genres as g, genres_in_movies as gm "
                									+"where g.name = '" + genreCheck + "' "
                									+"and g.id = gm.genre_id "
                									+"and gm.movie_id = m.id "
                									+ "order by m.title;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
									+ "from movies as m, genres as g, genres_in_movies as gm "
									+"where g.name = '" + genreCheck + "' "
									+"and g.id = gm.genre_id "
									+"and gm.movie_id = m.id "
									+"order by m.title "
									+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	else if(sortCheck.equals("titleasc")){ 
                		if(itemlimitCheck == 0){
                			rs = statement.executeQuery("Select m.* " 
                					+ "from movies as m, genres as g, genres_in_movies as gm "
                					+"where g.name = '" + genreCheck + "' "
                					+"and g.id = gm.genre_id "
                					+"and gm.movie_id = m.id "
                					+ "order by m.title;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
                					+ "from movies as m, genres as g, genres_in_movies as gm "
                					+"where g.name = '" + genreCheck + "' "
                					+"and g.id = gm.genre_id "
                					+"and gm.movie_id = m.id "
                					+ "order by m.title "
                					+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	
                	else if(sortCheck.equals("titledesc")){ 
                		if(itemlimitCheck ==0){ 
                			rs = statement.executeQuery("Select m.* " 
								+ "from movies as m, genres as g, genres_in_movies as gm "
								+"where g.name = '" + genreCheck + "' "
								+"and g.id = gm.genre_id "
								+"and gm.movie_id = m.id "
								+ "order by m.title desc;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
								+ "from movies as m, genres as g, genres_in_movies as gm "
								+"where g.name = '" + genreCheck + "' "
								+"and g.id = gm.genre_id "
								+"and gm.movie_id = m.id "
								+ "order by m.title desc "
            					+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	
                	else if(sortCheck.equals("yearasc")){ 
                		if(itemlimitCheck == 0){ 
                			rs = statement.executeQuery("Select m.* " 
								+ "from movies as m, genres as g, genres_in_movies as gm "
								+"where g.name = '" + genreCheck + "' "
								+"and g.id = gm.genre_id "
								+"and gm.movie_id = m.id "
								+ "order by m.year;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
								+ "from movies as m, genres as g, genres_in_movies as gm "
								+"where g.name = '" + genreCheck + "' "
								+"and g.id = gm.genre_id "
								+"and gm.movie_id = m.id "
								+ "order by m.year "
            					+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	else if(sortCheck.equals("yeardesc")){ 
                		if(itemlimitCheck ==0){
                			rs = statement.executeQuery("Select m.* " 
								+ "from movies as m, genres as g, genres_in_movies as gm "
								+"where g.name = '" + genreCheck + "' "
								+"and g.id = gm.genre_id "
								+"and gm.movie_id = m.id "
								+ "order by m.year desc;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
								+ "from movies as m, genres as g, genres_in_movies as gm "
								+"where g.name = '" + genreCheck + "' "
								+"and g.id = gm.genre_id "
								+"and gm.movie_id = m.id "
								+ "order by m.year desc "
            					+ "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}

                
                	try{ 
                		while(rs.next()){ 
                			int m_id = rs.getInt("id");
                			String m_title = rs.getString("title");
                			String m_year = Integer.toString(rs.getInt("year")); 
                			String m_director = rs.getString("director"); 
                			String m_banner = rs.getString("banner_url"); 
                			String m_trailer = rs.getString("trailer_url"); 
                    		
                        	Movie addMovie = new Movie(m_id,m_title,m_year,m_director,m_banner,m_trailer); 
                    		
                    		ArrayList<String> genreList = getgenreList(m_title); 
                    		ArrayList<Star> starList = getstarList(m_title);

                    		addMovie.setGenreList(genreList);
                    		addMovie.setStarList(starList);
                    		
                    		movieList.add(addMovie); 
                    		
                    		out.println("<p>" + movieList.size() + "</p>");
                    				
                    		
                		}
                		rs.close();
                		dbcon.close();
                	}
                	catch(Exception e){ 
                    	out.println("<p>"+ e.getMessage() +"</p>");
                	}

                	
                }
                else if(titleCheck !=null){ 
                	ResultSet rs = null; 
                	
                	if(sortCheck == null){
                		if(itemlimitCheck == 0){ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.title;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  +"order by m.title "
          					  + "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	else if(sortCheck.equals("titleasc")){
                		if(itemlimitCheck ==0){ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.title;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.title "
          					  + "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	else if(sortCheck.equals("titledesc")){ 
                		if(itemlimitCheck == 0){
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.title desc;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.title desc "
          					  + "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	else if(sortCheck.equals("yearasc")){ 
                		if(itemlimitCheck==0){ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.year;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.year "
          					  + "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	else if(sortCheck.equals("yeardesc")){ 
                		if(itemlimitCheck == 0){ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.year desc;");
                		} 
                		else{ 
                			rs = statement.executeQuery("Select m.* " 
  							  + "from movies as m "
  							  +"where m.title LIKE '"+titleCheck+"%' "
  							  + "order by m.year desc "
          					  + "limit "+ itemlimitCheck+ " offset "+ offset+";");
                		}
                	}
                	try{ 
                		while(rs.next()){ 
                			int m_id = rs.getInt("id");
                			String m_title = rs.getString("title");
                			String m_year = Integer.toString(rs.getInt("year")); 
                			String m_director = rs.getString("director"); 
                			String m_banner = rs.getString("banner_url"); 
                			String m_trailer = rs.getString("trailer_url"); 
            
                    		
                        	Movie addMovie = new Movie(m_id,m_title,m_year,m_director,m_banner,m_trailer); 
                    		
                    		ArrayList<String> genreList = getgenreList(m_title); 
                    		ArrayList<Star> starList = getstarList(m_title);
                    		
                    		addMovie.setGenreList(genreList);
                    		addMovie.setStarList(starList);
                    		
                    		movieList.add(addMovie);
                    		out.println("<p>" + movieList.size() + "</p>");
                    		
                		}
                		rs.close();
                		dbcon.close();
                	}
                	catch(Exception e){ 
                    	out.println("<p>Exception</p>");
                	}
                }

            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "MovieDB: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
        
        HttpSession session = request.getSession();                              
        session.setAttribute("search-results", movieList); 
        session.setAttribute("genre-check", genreCheck);
        session.setAttribute("title-check", titleCheck);
        session.setAttribute("sort-check", sortCheck);
        session.setAttribute("itemlimit-check", itemlimitCheck);
        session.setAttribute("pagenumber", pageNumber);
        session.setAttribute("previous-page", previousPage);
        getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/showMovies.jsp").forward(request,response);
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       doGet(request, response);
    }
    
    public ArrayList<String> getgenreList(String movie){ 
    	//return a list of genres in a movie
		ArrayList<String> genreList = new ArrayList<String>(); 
		 try {
             Class.forName("com.mysql.jdbc.Driver").newInstance();

             Statement statement = dbcon.createStatement(); 
         	 ResultSet rs = statement.executeQuery("Select g.name " 
						  + "from movies as m, genres as g, genres_in_movies as gm "
						  +"where m.title = \"" + movie + "\" "
						  +"and g.id = gm.genre_id "
						  +"and gm.movie_id = m.id;");
         	
         	while(rs.next()){ 
         		genreList.add(rs.getString(1)); 
         	}
         	rs.close();
             
		 } 
		 catch(Exception e){ 
			 System.out.println(e.getMessage());
		 }
		
		
		return genreList; 
    }
    
    public ArrayList<Star>getstarList(String movie){ 
		ArrayList<Star> starList = new ArrayList<Star>(); 
		try{
			Statement statement = dbcon.createStatement();
			ResultSet rs = statement.executeQuery("Select s.* " 
				  + "from movies as m, stars as s , stars_in_movies as sm "
				  +"where m.title = \"" + movie + "\" "
				  +"and s.id = sm.star_id "
				  +"and sm.movie_id = m.id;");
			while(rs.next()){ 
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name"); 
				int star_id = rs.getInt("id"); 
				String star_dob = rs.getString("dob"); 
				String star_photo = rs.getString("photo_url"); 
				Star addStar = new Star(star_id, first_name,last_name,star_dob,star_photo); 
				starList.add(addStar);
			}
		}
		catch(Exception e){ 
			System.out.println(e.getMessage());
		}
    return starList;
    }
}
