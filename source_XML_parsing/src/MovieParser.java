import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


import java.util.*;
import movies.*;

public class MovieParser{

	public static void main(String[] args) {
		ArrayList<Star> starList = null;
		ArrayList<Movie> movieList = null ;
		ArrayList<String> genreList = null;
		HashMap<String, ArrayList<Star>> titleStarMap = null;
		
		try {	
			File inputFile = new File("actors63.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			SAXParser saxParser = factory.newSAXParser();
			ActorsHandler userhandler = new ActorsHandler();
			saxParser.parse(inputFile, userhandler);
			starList = userhandler.getStarList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}

		try {	
			File inputFile = new File("mains243.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			SAXParser saxParser = factory.newSAXParser();
			MainsHandler userhandler = new MainsHandler();
			saxParser.parse(inputFile, userhandler);    
			movieList = userhandler.getMovieList();
			genreList = userhandler.getGenreList(); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {	
			File inputFile = new File("casts124.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			SAXParser saxParser = factory.newSAXParser();
			CastsHandler userhandler = new CastsHandler();
			saxParser.parse(inputFile, userhandler); 
			titleStarMap = userhandler.getTitleStarMap();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		BatchInsert batchInsert= new BatchInsert(starList, movieList,genreList,titleStarMap,"root","cs122b","jdbc:mysql://localhost:3306/moviedb");
		batchInsert.commitUpdate();
	}



}
