import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DriverProgram {

	public static void main(String[] args) {
		jdbcMenu Menu = new jdbcMenu();
		//Menu.enterMenu("hello", "world");
		System.out.println("Enter username and password or exitProgram");
		Scanner lineScanner = new Scanner(System.in);			
		while(true) {
			try {
				String lineInput = lineScanner.nextLine();	
				Scanner wordScanner = new Scanner(lineInput);
				String word = wordScanner.next();
				if(word.equals("exitProgram")) {
					//lineScanner.close();
					System.out.println("Progam Exited");
					return;
				}
				String username = word;
				String password = wordScanner.next();
				wordScanner.close();

				if(Menu.validateUser(username, password)) {
					Menu.enterMenu(lineScanner);
					System.out.println("Enter username and password or exitProgram");
				}		
			}
			catch (Exception e) {
				System.out.println("Enter username and password or exitProgram");
				//e.printStackTrace();
				//System.exit(-2);
			}
		}	
	}	
}
