import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class jdbcMenu {
	private static Connection connection; 
	private Scanner lineScanner;
	jdbcMenu() {
		connection = null; //maybe change this 
	}
	
	public void enterMenu(Scanner lineScanner) {
		try {
			this.lineScanner = lineScanner;			
			while(true) {
				System.out.println("Please enter a command: " +
						"\nprintStar" +
						"\ninsertStar" +
						"\ninsertCustomer " +
						"\ndeleteCustomer" +
						"\nmetadata" +
						"\nexitMenu");
				String command = lineScanner.nextLine();	
				String firstName;
				String lastName;
				String id; 
				String dob; 
				String address; 
				String email; 
				String customerPassword; 
				String url; 
				String ccid;
				String table;
				String columns;
				String condition;
				String insert;
				String query;
				//Scanner S = new Scanner(System.in);
				Scanner wordScanner;
				String validSQL;
				// Execute Valid mySQL statements. No error checking is done here.
				if(!detectValidSql(command)) {
				switch(command) {
					case "printStar":	
						// Print out to screen the movies featuring a given star.
						System.out.println("Would you like to query by\na)first name and last name"
								+"\nb)first name or last name"
								+"\nc)ID");
							
						query = lineScanner.nextLine();
						
						if(query.equals("a"))
						{
							System.out.println("Enter first name: ");
							firstName = lineScanner.nextLine();
							System.out.println("Enter last name: ");
							lastName = lineScanner.nextLine();
							
							try {							 
								Statement get = connection.createStatement(); 
								ResultSet data = get.executeQuery("Select m.* "
										+ "from movies as m, stars as s, stars_in_movies as sm "
										+ "where s.first_name = '" + firstName + "' "
										+ "and s.last_name = '" + lastName + "' "
										+ "and s.id = sm.star_id "
										+ "and m.id = sm.movie_id;"); 
								while(data.next()){ 
									System.out.println("ID = " + data.getInt(1));
									System.out.println("Title = " + data.getString(2));
									System.out.println("Year = " + data.getInt(3));
									System.out.println("Director = " + data.getString(4));
									System.out.println("Banner URL = " + data.getString(5));
									System.out.println("Trailer URL = " + data.getString(6));
								}
							} catch (Exception e) {e.printStackTrace();}
						}
						if(query.equals("b"))
						{
							System.out.println("Select 1 for firstName, 2 for lastName");
							String choice = lineScanner.nextLine();
							
							if(choice.equals("1"))
							{
								System.out.println("Enter first name:");
								firstName = lineScanner.nextLine();
								
								try {							 
									Statement get = connection.createStatement();								
									ResultSet data = get.executeQuery("Select m.* "
											+ "from movies as m, stars as s, stars_in_movies as sm "
											+ "where s.first_name = '" + firstName + "' "
											+ "and s.id = sm.star_id "
											+ "and m.id = sm.movie_id;");
									while(data.next()){ 
										System.out.println("ID = " + data.getInt(1));
										System.out.println("Title = " + data.getString(2));
										System.out.println("Year = " + data.getInt(3));
										System.out.println("Director = " + data.getString(4));
										System.out.println("Banner URL = " + data.getString(5));
										System.out.println("Trailer URL = " + data.getString(6));										
									}
								} catch (Exception e) {e.printStackTrace();}
							}
							else
							{
								System.out.println("Enter last name:");
								lastName = lineScanner.nextLine();
								
								try {							 
									Statement get = connection.createStatement(); 
									ResultSet data = get.executeQuery("Select m.* "
											+ "from movies as m, stars as s, stars_in_movies as sm "
											+ "where s.last_name = '" + lastName + "' "
											+ "and s.id = sm.star_id "
											+ "and m.id = sm.movie_id;"); 
									while(data.next()){ 
										System.out.println("ID = " + data.getInt(1));
										System.out.println("Title = " + data.getString(2));
										System.out.println("Year = " + data.getInt(3));
										System.out.println("Director = " + data.getString(4));
										System.out.println("Banner URL = " + data.getString(5));
										System.out.println("Trailer URL = " + data.getString(6));
									}
								} catch (Exception e) {e.printStackTrace();}
							}
								
							
						}
						if(query.equals("c"))
						{
							System.out.println("Enter ID: ");
							String ID = lineScanner.nextLine();
							
							try {							 
								Statement get = connection.createStatement(); 
								ResultSet data = get.executeQuery("Select m.* "
										+ "from movies as m, stars as s, stars_in_movies as sm "
										+ "where s.id = " + ID + " "
										+ "and s.id = sm.star_id "
										+ "and m.id = sm.movie_id;"); 
								while(data.next()){ 
									System.out.println("ID = " + data.getInt(1));
									System.out.println("Title = " + data.getString(2));
									System.out.println("Year = " + data.getInt(3));
									System.out.println("Director = " + data.getString(4));
									System.out.println("Banner URL = " + data.getString(5));
									System.out.println("Trailer URL = " + data.getString(6));
								}
							} catch (Exception e) {e.printStackTrace();}
						}
						break;
					case "insertStar":
						System.out.println("Enter the star's name: "); 
						wordScanner = new Scanner(lineScanner.nextLine());

						// Insert a new star into the database.
						firstName = wordScanner.next();
						try {							
							lastName = wordScanner.next(); //checking if star has only one name
						} catch (Exception e) {
							lastName = firstName;
							firstName = "";
						}
						wordScanner.close();
						System.out.println("Enter the star's id: ");
						id = lineScanner.nextLine();
						System.out.println("Enter the star's date of birth(optional): ");
						dob = lineScanner.nextLine();
						System.out.println("Enter the star's photo url(optional): ");
						url = "'"+ lineScanner.nextLine()+"'"; 
						// TODO: create SQL select to insert star into database.
						System.out.println("inserting star");
						table = "stars"; 
						if(dob.equals("")){ 
							dob = null; 
						}
						if(url.equals("")){ 
							url = null;
						}
						insert = id + ", '"+firstName+"', '"+lastName+"',"+dob+","+url;
						
						try{ 
							insert(table,insert);
						} 
						catch(Exception e){ 
							System.out.println("Wrong Input. Please Try Again.");
							break;
						}
						break;
					case "insertCustomer":
						System.out.println("Enter customer name:");
						wordScanner = new Scanner(lineScanner.nextLine());
						firstName = wordScanner.next();
						try {							
							lastName = wordScanner.next(); //checking if customer has only one name
						} catch (Exception e) {
							lastName = firstName;
							firstName = "";
						}
						wordScanner.close();
						// TODO: check if Customer credit card exists in table
						table = "customers";
						System.out.println("Enter the customers id: ");
						id = lineScanner.nextLine(); 
						System.out.println("Enter the customer creditcard id: ");
						ccid = lineScanner.nextLine();
						Statement testGet = connection.createStatement(); 
						ResultSet check = testGet.executeQuery("Select creditcards.id from creditcards where creditcards.id = '" + ccid + "';");
						if(check.next() == false){ 
							System.out.println("Credit card id does not exist. Please try again.");
							break;
						}
						System.out.println("Enter the customer address: ");
						address = lineScanner.nextLine();
						System.out.println("Enter the customer email: ");
						email = lineScanner.nextLine();
						System.out.println("Enter the customer password: ");
						customerPassword = lineScanner.nextLine(); 
						System.out.println("inserting customer");
						
						insert = id + ", '"+firstName+"', '"+lastName+"','"+ccid+"','"+address+"','"+email+"','"+customerPassword+"'";
						try{ 
							insert(table,insert);
						} 
						catch(Exception e){ 
							System.out.println("Wrong Input. Please Try Again.");
							break;
						}
						break;				
					case "deleteCustomer":
						System.out.println("Please enter customer ccid to delete:");
						ccid = lineScanner.nextLine();
						// TODO: delete customer from database (if exists)
						//I added this based on the examples he gave us 
						System.out.println("deleting customer");
						Statement update = connection.createStatement(); 
						//INVALID SQL update.executeUpdate("delete from customers " + fullName);
						table = "customers"; 
						insert = " customers.cc_id =  '" + ccid + "'";
						
						try{ 
							delete(table,insert);
						} 
						catch(Exception e){ 
							System.out.println("Wrong Input. Please Try Again.");
							break;
						}						
						break;					
					case "metadata":	
						// TODO: print out metadata
						System.out.println("printing out metadata...");
						//added this
						Statement get = connection.createStatement(); 
						ResultSet data = get.executeQuery("Select movies.* from movies;");
					 	ResultSetMetaData metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
						data = get.executeQuery("Select stars.* from stars;");
					 	metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
					 	data = get.executeQuery("Select stars_in_movies.* from stars_in_movies;");
					 	metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
						data = get.executeQuery("Select genres.* from genres;");
					 	metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
						data = get.executeQuery("Select genres_in_movies.* from genres_in_movies;");
					 	metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
						data = get.executeQuery("Select creditcards.* from creditcards;");
					 	metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
						data = get.executeQuery("Select customers.* from customers;");
					 	metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
						data = get.executeQuery("Select sales.* from sales;");
					 	metadata = data.getMetaData(); 
				 		System.out.println(metadata.getTableName(1));
					 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
					 		System.out.println(metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i));
					 	}
					 	System.out.println(" ");
					 	data.close();
					 	get.close();
						break;
					case "exitMenu":	
						//lineScanner.close();
						connection.close();
						return;
					default:
						System.out.println("ERROR\n");
				}
				}
							
			}
		} 
		catch (Exception e) {
		}
	}
	
	public boolean validateUser(String username, String password) throws Exception {
		// Incorporate mySQL driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	
		// Connect to the test database
		try {
			connection = DriverManager.getConnection("jdbc:mysql:///moviedb",username, password); // change this based on your password
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception();
			//return false;
		}
	}
		
	public static List<HashMap<String, Object>> select(String table, String columns, String condition) throws Exception { 
		//returns a list of maps that contains a column name and it's data 
		List<HashMap<String,Object>> endResult = new ArrayList<HashMap<String,Object>>(); 
		Statement get = connection.createStatement(); 
		
		ResultSet data = null; 
		if(condition.equals("")){ 
			data = get.executeQuery("select " + columns +" from " + table + ";"); 
		}
		else{ 
			data = get.executeQuery("select " + columns + " from " + table +" where " + condition + ";"); 
		}
		
		ResultSetMetaData meta = data.getMetaData(); 
		
		while(data.next()){ 
			HashMap<String,Object> rowAdd = new HashMap<String,Object>(); 
			for(int i = 0; i<meta.getColumnCount();++i){ 
				rowAdd.put(meta.getColumnName(i), data.getObject(i));
			}
			endResult.add(rowAdd);
		}
		get.close();
		data.close(); 
		
		return endResult;
	}
	
	public static int delete(String table, String condition) throws Exception { 
		int count = 0; 
		Statement get = connection.createStatement(); 
		
		if(condition.equals("")){ //not sure if this is allowed
			count = get.executeUpdate("delete from " + table + ";");
		}
		else{ 
			count = get.executeUpdate("delete from " + table + " where " + condition + ";");
		}
		
		get.close(); 
		
		return count; 
	}
	
	public static int insert(String table, String insert) throws Exception { 
		int count = 0; 
		Statement get = connection.createStatement(); 
		
		count = get.executeUpdate("insert into " + table + " values("+ insert+");");
		
		get.close(); 
		return count;
	}

	private void executeSqlUpdate(String sql) throws Exception {
		// Update, no resultSet
		Statement update = connection.createStatement();
		int count = update.executeUpdate(sql);
		update.close();
		System.out.println(count + " of record(s) have changed");	
	}

	private void executeSqlQuery(String sql) throws Exception {
		Statement get = connection.createStatement();
		ResultSet data = get.executeQuery(sql);
		printData(data);
		data.close();
		get.close();
	}

	// Needs different versions of print data
	private void printData(ResultSet data) throws Exception {
		System.out.print("---------------------------------------------------------------------------\n");
		ResultSetMetaData metadata = data.getMetaData(); 
		//printMetadata(metadata);
		while(data.next()) {
		 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
		 		System.out.println(metadata.getColumnName(i) + ": " +  data.getString(i) + "\t");
		 	}
		 	System.out.print("------------------------------------\n");
		}
		System.out.println();
	}

	private void printMetadata(ResultSetMetaData metadata) {
		try {
			System.out.println(metadata.getTableName(1));
		 	for(int i = 1; i<=metadata.getColumnCount();++i){ 
		 		System.out.print(metadata.getColumnName(i) + "\t");
		 	}
		 	System.out.println(); 
	 	}
	 	catch (Exception e) {
	 	}
	}

	private boolean detectValidSql(String command) {
		try {
			Scanner validSqlScanner = new Scanner(command); 
			String keyword = validSqlScanner.next();
			if(keyword.toLowerCase().equals("select")) {
				executeSqlQuery(command);
				return true;
			}
			else if(keyword.toLowerCase().equals("update")) {
				executeSqlUpdate(command);
				return true;
			}
			else if(keyword.toLowerCase().equals("insert")) {
				executeSqlUpdate(command);
				return true;
			}	
			else if(keyword.toLowerCase().equals("delete")) {
				executeSqlUpdate(command);
				return true;
			}
			validSqlScanner.close();
			return false;
		}
		catch (Exception e) {
			System.out.print("Invalid SQL Query ");
			return false;
		}	
	}

}

