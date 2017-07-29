package movies;
import java.util.*;

public class Movie {
	//variables represeting attributes of a movie
	private int id;
	private String title;
	private String year;
	private String director;
	private String bannerURL;
	private String trailerURL;
	private ArrayList<String> genreList;
	private ArrayList<Star> starList;

	
	//default ctor
	public Movie() {
		this.id = -1;
		this.title = "";
		this.year = "";
		this.director = "";
		this.genreList = new ArrayList<String>();
		this.starList = new ArrayList<Star>();
		
	}

	public Movie(int id, String title, String year, String director, String bannerURL, String trailerURL) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.bannerURL = bannerURL;
		this.trailerURL = trailerURL;
		this.genreList = new ArrayList<String>();
		this.starList = new ArrayList<Star>();
	}

	//parameterized ctor
	public Movie(int id, String title, String year, String director, ArrayList<String> genreList, ArrayList<Star> starList)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.director = director;
		this.genreList = genreList;
		this.starList = starList;

	}

	public void print() {
		//System.out.println("id: " + id);
		//System.out.println("title: " + title);
		//System.out.println("year: " + year);
		//System.out.println("director: " + director);
		
		System.out.format("title: %-35s year: %-20s director: %-20s", this.title, this.year, this.director);
		String genreString = "";
		for(String genre: this.genreList) {
			genreString += genre + " ";
		}
		System.out.format("genres: %s\n", genreString);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getDirector() {
		return director;

	}

	public String getBannerURL() {
		return bannerURL;
	}

	public String getTrailerURL() {
		return trailerURL;
	}
	
	public ArrayList<String> getGenreList(){ 
		return genreList; 
	}
	
	public ArrayList<Star> getStarList(){ 
		return starList; 
	}
	
	public void setGenreList(ArrayList<String> genreList){ 
		this.genreList = genreList; 
	}
	
	public void setStarList(ArrayList<Star> starList){ 
		this.starList = starList; 
	}
	
	
}
