package jdbcmodule;

import java.util.Scanner;


public class Extensions {
	
	static DbMethods dbmethod = new DbMethods();
	
	public void logIn() {
		
		System.out.println("Log In");
		
		
		boolean ok = false;
		
		do {
			ok =  UserIdentification();		
		} while (ok != true);
								
		if (ok == true) {
			//System.out.println("Futhat a program");
			dbmethod.driveRegistration();
		}
		
	}
	
	
	
	public boolean UserIdentification() {
		String username = "User";
		String password = "user";
		boolean logintrue = false;
		
		
		 while(!logintrue){
	            Scanner sc = new Scanner(System.in);

	            System.out.print("Username: ");
	            String inputUsername = sc.nextLine();

	            System.out.print("Password: ");
	            String inputPassword = sc.nextLine();

	            if(inputUsername.equals(username) && inputPassword.equals(password)){
	                System.out.println("Login is successful!");
	                logintrue = true;
	            } else {
	                System.out.println("Incorrect username or password!");
	            }
	      }
		return logintrue;
	}

	
	
	


}
