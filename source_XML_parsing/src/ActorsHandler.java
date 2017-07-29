import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;
import movies.*;


public class ActorsHandler extends DefaultHandler {
	private ArrayList<Star> starList;
	
	private boolean stagename = false;
	private boolean dob = false;
	
	private String StageName = "";
	private String Dob = "";
	
	@Override
	public void startDocument() throws SAXException {
		starList = new ArrayList<Star>();
	}
	
	public void endDocument() throws SAXException {
		//printActors();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase("stagename")) {
			stagename = true;
		}
		else if(qName.equalsIgnoreCase("dob")) {
			dob = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equalsIgnoreCase("stagename")) {
			addStar(this.StageName, this.Dob);
			
			StageName = "";
			Dob = "";
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if(stagename) {
			StageName = new String(ch, start, length);
			StageName = StageName.replaceAll("[^a-zA-Z0-9\\s]","");
			stagename = false;
		}
		else if (dob) {
			Dob = new String(ch, start, length);
			dob = false;
		}
	}
	
	private String getFirstName(String StageName) {
		String[] splitName = StageName.split(" ");
		if(splitName.length == 1) {
			return "";
		}
		else {
			return splitName[0];
		}
	}
	
	private String getLastName(String StageName) {
		String[] splitName = StageName.split(" ");
		if(splitName.length == 1) {
			return splitName[0];
		}
		else {
			String lastName = "";
			for(int i = 1; i < splitName.length; i++) {
				lastName += splitName[i];
				if(i != splitName.length-1) {
					lastName += " ";
				}
			}
			return lastName;
		}
	}
	
	private void addStar(String StageName, String Dob) {
		String firstName = getFirstName(StageName);
		String lastName = getLastName(StageName);
		Star star = new Star(0, firstName, lastName, Dob, "");
		if(!nameExists(star)) {
			starList.add(star);
		}
		else {
			System.out.println("Error: " + star.getFirstName() + " " + star.getLastName() + " exists");
		}
		
	}
	
	private void printActors() {
		for(Star star: this.starList) {
			star.printStar();
		}
		System.out.println(starList.size());
	}
	
	private boolean nameExists(Star star) {
		for(Star s: this.starList) {
			if((s.getFirstName().compareToIgnoreCase(star.getFirstName())) == 0) {
				if((s.getLastName().compareToIgnoreCase(star.getLastName())) == 0) {
					if((s.getDOB().compareToIgnoreCase(star.getDOB())) == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public ArrayList<Star> getStarList() {
		return this.starList;
	}
}
