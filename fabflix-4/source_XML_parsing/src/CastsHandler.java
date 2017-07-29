import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import movies.Movie;
import movies.Star;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CastsHandler extends DefaultHandler {

	private HashMap<String, ArrayList<Star>> titleStarMap;
	private ArrayList<Star> starList;
	private String Title = "";
	private String stageName = "";
	private String firstName = "";
	private String lastName = "";
	
	boolean title = false; // <t>
	boolean stagename = false; // <a>

	public void startDocument() throws SAXException {
		starList = new ArrayList<Star>();
		titleStarMap = new HashMap<String, ArrayList<Star>>(); //each entry is the title of a movie and a list of its stars
	}
	
	public void endDocument() throws SAXException {
		//printHashMap();
	}
	
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if(qName.equalsIgnoreCase("t")) {
				title = true;
			}
			else if(qName.equalsIgnoreCase("a")) {
				stagename = true;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if(qName.equalsIgnoreCase("filmc")) 
			{
				boolean titleINSERT = true;
				
				//check map for duplicate title before insertion
				for (String titleKey : titleStarMap.keySet() ) {
				    if(titleKey.equalsIgnoreCase(Title))
				    {
				    	//System.out.println("Duplicate title found, not inserting" + Title);
				    	/*try {
				    	    Thread.sleep(1000);                 //1000 milliseconds is one second.
				    	} catch(InterruptedException ex) {
				    	    Thread.currentThread().interrupt();
				    	}*/
				    	titleINSERT = false;
				    }
				}
				
				if(titleINSERT)
				{
				titleStarMap.put(Title, starList);
				}
				this.starList = new ArrayList<Star>(); //reset the starList
				
				Title = "";
				stageName = "";
				firstName = "";
				lastName = "";
			}
		}

		@Override
		public void characters(char ch[], int start, int length) throws SAXException {
			if(title) {
				Title = new String(ch, start, length);
				Title = Title.replaceAll("[^a-zA-Z0-9\\s]","");
				title = false;
			}
			else if(stagename) {
				stageName = new String(ch, start, length);
				stageName = stageName.replaceAll("[^a-zA-Z0-9\\s]","");
				
				//Check to see if star exists 
				ActorsHandler actorhandle = new ActorsHandler();
				
				
				//i check current star list for duplicates before insertion
				// in the addStar Function
				addStar(stageName);

				stagename = false;
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
		
		private void addStar(String StageName) {
			String firstName = getFirstName(StageName);
			String lastName = getLastName(StageName);
			Star star = new Star(0, firstName, lastName);
			boolean INSERT = true;
			
			//theres a star "s a" that pops up hundreds of times, I'm just not inserting this value
			
			if(firstName.equals("s") && lastName.equals("a"))
			{
				INSERT = false;
			}
		//CHECKING FOR DUPLICATES IN CURRENT STAR LIST
			for(Star starcheck: this.starList) 
			{
	 			if( (starcheck.getFirstName().equalsIgnoreCase(firstName) && starcheck.getLastName().equalsIgnoreCase(lastName) ))
	 			{
	 				INSERT = false;
	 			}
			}
	 		
			//if theres no duplicates in the movie's starList
			if(INSERT)
			{
	 				this.starList.add(star);
	 		}
	 	}
			
		
		//print out the titles and their corresponding starList
		private void printHashMap() {
			

		    // Get a set of the entries
		      Set set = this.titleStarMap.entrySet();
		      // Get an iterator
		      Iterator i = set.iterator();
		      // Display elements
		      while(i.hasNext()) 
		      {
		         Map.Entry me = (Map.Entry)i.next();
		         System.out.println(me.getKey() + ": ");
		         ArrayList<Star> tempStarList = (ArrayList<Star>) me.getValue();
		         
		         for(Star star: tempStarList) {
		 			star.printStar();
		 		}
		      }
				System.out.println(titleStarMap.size());
		}
		
		HashMap<String, ArrayList<Star>> getTitleStarMap()
		{
			return this.titleStarMap;
		}
		
		
}