import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
import movies.*;


public class MainsHandler extends DefaultHandler {
	private ArrayList<Movie> movieList;
	private ArrayList<String> movieGenreList;
	private ArrayList<String> genreList;
	
	private boolean title = false;
	private boolean year = false;
	private boolean dir = false;
	private boolean genre = false;

	private String Title = "";
	private String Year = "";
	private String Dir = "";
	
	@Override
	public void startDocument() throws SAXException {
		movieList = new ArrayList<Movie>();
		movieGenreList = new ArrayList<String>();
		genreList = new ArrayList<String>();
	}
	
	public void endDocument() throws SAXException {
		//printMovies();
		//printGenres();
		//printMovieGenres();
	}
			
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("t")) {
			title = true;
		}
		else if(qName.equalsIgnoreCase("year")) {
			year = true;
		}
		else if(qName.equalsIgnoreCase("dirname")) {
			dir = true;
		}
		else if(qName.equalsIgnoreCase("cat")) {
			genre = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("film")) {
			Movie movie = new Movie(0, this.Title, this.Year, this.Dir, this.movieGenreList, new ArrayList<Star>());
			addMovie(movie);
			movieGenreList = new ArrayList<String>();
			
			Title = "";
			Year = "";
			Dir = "";
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if(title) {
			Title = new String(ch, start, length);
			Title = Title.replaceAll("[^a-zA-Z0-9\\s]","");
			title = false;
		}
		else if(year) {
			Year = new String(ch, start, length);
			Year = Year.replaceAll("[^a-zA-Z0-9\\s]","");
			year = false;
		}
		else if(dir) {
			Dir = new String(ch, start, length);
			Dir = Dir.replaceAll("[^a-zA-Z0-9\\s]","");
			dir = false;
		}
		else if(genre) {
			String Genre = new String(ch, start, length);
			Genre = Genre.replaceAll("[^a-zA-Z0-9\\s]","");
			addGenre(Genre);
			addMovieGenre(Genre);
			genre = false;
		}
	}
	
	private void addMovie(Movie movie) {
		for(Movie m: this.movieList) {
			if((m.getTitle().compareToIgnoreCase(movie.getTitle())) == 0) {
				if((m.getYear().compareToIgnoreCase(movie.getYear())) == 0) {
					if((m.getDirector().compareToIgnoreCase(movie.getDirector())) == 0) {
						System.out.println("Error " + movie.getTitle() + " already exists");
					}
				}
			}
		}
		this.movieList.add(movie);
	}
	
	private void addGenre(String genre) {
		if(!this.genreList.contains(genre)) {
			this.genreList.add(genre);
		}
	}
	
	private void addMovieGenre(String genre) {
		if(!this.movieGenreList.contains(genre)) {
			this.movieGenreList.add(genre);
		}
	}
	
	private void printMovies() {
		for(Movie movie: this.movieList) {
			movie.print();
		}
		System.out.println(movieList.size());
		
	}
	
	private void printGenres() {
		for(String genre: this.genreList) {
			System.out.println(genre);
		}
	}
	
	private void printMovieGenres() {
		for(Movie m: this.movieList) {
			System.out.println("--------------------" + m.getTitle());
			for(String genre: m.getGenreList()) {
				System.out.println(genre);
			}
		}
	}
	
	public ArrayList<Movie> getMovieList() {
		return this.movieList;
	}
	
	public ArrayList<String> getGenreList() {
		return this.genreList;
	}
	
}
