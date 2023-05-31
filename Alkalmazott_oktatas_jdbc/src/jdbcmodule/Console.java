package jdbcmodule;

import java.util.Scanner;

public class Console {
	
	static DbMethods dbMethods = new DbMethods();
	
	public void menu() {

		
		 Scanner scanner = new Scanner(System.in);
	        int choice = 0;

	        while (choice != 7) {
	            System.out.println("\nValasszon az alabbi menupontok kozul!:");
	            System.out.println("======================================");
	            System.out.println("1. Rekodrok listazasa ");
	            System.out.println("2. Adatok felvitele");
	            System.out.println("3. Adatok mentese");
	            System.out.println("4. Szures");
	            System.out.println("5. Egyeb muveletek");
	            System.out.println("6. Meta");
	            System.out.println("7. Exit");
	            System.out.println("======================================");

	            try {
	                choice = Integer.parseInt(scanner.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("Kérem, csak számokat adjon meg!");
	                continue;
	            }

	            switch (choice) {
	                case 1:
	                     listTables();
	                    break;
	                case 2:
	                	insertData();
	                	break;
	                case 3:
	                    saveData();
	                    break;
	                case 4:
	                    filterData();
	                    break;
	                case 5:
	                    extraOptions();
	                    break;
	                case 6:
	                    showMetadata();
	                    break;
	                case 7:
	                    System.out.println("Kilepes!");
	                    break;
	                default:
	                    System.out.println("Kérem, válasszon a felsorolt menüpontok közül!");
	                    break;
	            }
	        }

	        scanner.close();
	    }
		
	
		


		//Rekordok listazasa [1]
		private static void listTables() {
			
			System.out.println("Tablak rekordjainak listazasa:");
			Scanner sc = new Scanner(System.in);

			System.out.println("Adja meg a listazando tablat: ");
			String tableName = sc.nextLine();

			dbMethods.printTable(tableName);
		
	}

		
		
		
		
		//Táblák felvitele
	    private static void createTable() { 
	        System.out.println("Táblák:");
	        Scanner sc = new  Scanner(System.in);
	        
	        
	        System.out.println("Szeretne uj tablat felvenni? (yes/no) : "); 
	        String inputDecision= sc.nextLine();
	        
	        if(inputDecision.equals("yes")) {
	        	System.out.println("Adja meg a tabla nevet:"); 
		        String newTableName= sc.nextLine();
		        if(newTableName != null) {
		        	dbMethods.createTable(newTableName);
		        }else {
		        	System.out.println("Helytelen tablanev!");
		        }
	        	
	        }
	       
	    }
	    
	    
	    
	    
	    //Rekordok felvitele [ 2 ] 
	    private static void insertData() {
	        System.out.println("Az adatok felvitele");
	        dbMethods.addRecord(); 
	        
	    }
	    
	    
	    
	    
	    
	 // Rekordok lementese txt allomanyba [3]
	    private static void saveData() {
	    	
	        Scanner sc = new  Scanner(System.in);
	    	
	        System.out.println("Az adatok mentese");
	        System.out.println("Add meg a tabla nevet: ");
	        String inputDecision= sc.nextLine();
	        
	        dbMethods.saveRecord(inputDecision);
	        
	        
	    }
	    
	    	    
	    
	      
	    //Szures [4]
	    private static void filterData() {
	        Scanner scanner = new Scanner(System.in);
	        int choice = 0;
	        
	        while (choice != 7) {
	            System.out.println("\nValasszon az alabbi al-menupontok kozul!:");
	            System.out.println("======================================");
	            System.out.println("\t 1. Tanfolyam reszvetel ");
	            System.out.println("\t 2. Legnagyobb fizetesek");
	            System.out.println("\t 3. Tanfolyamot oktato tanarok");
	            System.out.println("\t 4. Ingyenes tanfolyamkereso");
	            System.out.println("\t 5. Exit");
	            System.out.println("======================================");

	            try {
	                choice = Integer.parseInt(scanner.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("Kérem, csak számokat adjon meg!");
	                continue;
	            }

	            switch (choice) {
	                case 1:
	                     dbMethods.selectEmployeeAndCourse();
	                    break;
	                case 2:
	                	Scanner sc = new Scanner(System.in);
	                	 System.out.println("Add meg a beosztas:");
	                	 String beosztas = sc.nextLine();
	                	dbMethods.selectMaxPayment(beosztas);	
	                	break;
	                case 3:
	                	dbMethods.selectCourseTeacher();
	                    break;
	                case 4:	
	                	Scanner scanner1 = new Scanner(System.in);
	                	 System.out.println("Add meg a tanfolyam temajat (konyveles/marketing):");
	                	 String tema = scanner1.nextLine();
	                	 dbMethods.selectFreeCourse(tema);
	                	
	                    break;		                    
	                case 5:
	                    System.out.println("Kilepes sikeres!");
	                    break;
	                default:
	                    System.out.println("Kerem valasszon!");
	                    break;
	            }
	        }
	        
	        
	       
	    }
	    
	   	    	    
	    
	    //Egyeb muveletek [5]
	    private static void extraOptions() {
	    	
			
	    	Scanner scanner = new Scanner(System.in);
	        int choice = 0;
	        
	        while (choice != 7) {
	            System.out.println("\nValasszon az alabbi al-menupontok kozul!:");
	            System.out.println("======================================");
	            System.out.println("\t 1. DELETE  ");
	            System.out.println("\t 2. UPDATE ");
	            System.out.println("\t 3. CREATE TABLE ");
	            System.out.println("\t 4. MOD");
	            System.out.println("\t 5. Exit");
	            System.out.println("======================================");

	            try {
	                choice = Integer.parseInt(scanner.nextLine());
	            } catch (NumberFormatException e) {
	                System.out.println("Kérem, csak számokat adjon meg!");
	                continue;
	            }

	            switch (choice) {
	                case 1:
	                		dbMethods.deleteTable();
	                    break;
	                case 2:	 
	                	   dbMethods.updateTable();
	                	break;
	                case 3:
	                		
	                    break;
	                case 4:	
	                		                	
	                    break;		                    
	                case 5:
	                    System.out.println("Kilepes sikeres!");
	                    break;
	                default:
	                    System.out.println("Kerem valasszon!");
	                    break;
	            }
	        }
	    	   	
			
		}
	    
	    	    
	    
	    // Metaadatok [6]
	    private static void showMetadata() {
	    	
	    	dbMethods.getMeta();
	    }
		
	

}
