package movies;
import java.util.*;

public class Pair {
	private int qty;
	private Movie movie;

	Pair(Movie movie, int qty) {
		this.movie = movie;
		this.qty = qty;
	}

	public Movie getMovie() {
		return movie;
	}

	public int getQty() {
		return qty;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}