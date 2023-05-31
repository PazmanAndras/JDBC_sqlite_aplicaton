package jdbcmodule;

import java.io.IOException;
import java.util.Scanner;

public class Program {
		
	static DbMethods dbm = new DbMethods();
	static Extensions ext = new Extensions();
	static Console con = new Console();
	

	public static void main(String[] args) {
		
	 dbm.driveRegistration(); /*Ez a menubol fog indulni*/
		
	 
	 /*LogIN akkor kapcsoljuk be ha mukodik az osszes menu*/
	 //ext.logIn(); 
	 
	
		
	 //dbm.createTable("");
	 
	 /*ez a mentes txt be*/
	 //dbm.saveRecord("Oktato");
	 
	 
	 /*Ez megy majd a felvitel menube*/
	  //bm.addRecord(); 
	 
	 /*melyik alkalmazott milyen temaju tanfolyamra jár:*/
	 //dbm.selectEmployeeAndCourse();
	 
	 /*Legnagyobb fizetes*/
	 //dbm.selectMaxPayment("számvitel");
	 
	 /*Tanfolyamot tarto tanar neve*/
	 //dbm.selectCourseTeacher();
	 
	 /*deletaTable*/
	 //dbm.deleteTable();
	 
	 /*table Metadata*/
	 //dbm.getMeta();
	 
	 boolean ok = false;
	 
	 while(!ok){
         Scanner sc = new Scanner(System.in);
         
         System.out.print("  Username: ");
         String inputUsername = sc.nextLine();

         System.out.print("  Password: ");
         String inputPassword = sc.nextLine();
        
         ok = dbm.authenticate(inputUsername, inputPassword);
         
         if(ok == false) {
        	 System.out.println("Incorrect username or password!");
         }
                                                    
       
	 }
				 
	 
	 con.menu();
	 
	 
	
	 
	 
	 
	}

}
