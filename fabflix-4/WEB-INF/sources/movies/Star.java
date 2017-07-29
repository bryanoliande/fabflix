package movies;
import java.util.*;

public class Star {
	private int id;
	private String firstName;
	private String lastName;
	private String dob;
	private String photoURL;

	Star(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = "";
		this.photoURL = "";
	}

	Star(int id, String firstName, String lastName, String dob, String photoURL) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.photoURL = photoURL;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDOB() {
		return dob;
	}

	public String getPhotoURL() {
		return photoURL;
	}
}