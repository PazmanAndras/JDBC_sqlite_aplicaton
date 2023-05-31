package jdbcmodule;

import java.sql.SQLException;
import java.util.Scanner;

public class Employee {
	
	
	class Emp
    {
        //Létrehozuk az objektumot amit perzisztálunk az adatbázisa
		String Vnev;
		String Knev;
		String szulev;
		String Beosztas;
		int Fizetes;
		String AlkTel;
		
		
		public Emp(String vnev, String knev, String szulev, String beosztas, int fizetes, String alkTel) {
			super();
			Vnev = vnev;
			Knev = knev;
			this.szulev = szulev;
			Beosztas = beosztas;
			Fizetes = fizetes;
			AlkTel = alkTel;
		}

		
        
    }
	


	public static void main(String[] args) throws SQLException {
		
		DbMethods dbm = new DbMethods();
		
		
		
		Scanner in = new Scanner(System.in);

		Employee prog = new Employee();
		dbm.connect();  //A SAJAT DB NEVE KELL
		

		//az objektumot létrehozom 
		System.out.println("Knev:"); //beolvasom a termék nevét 
		String tnev = in.next(); //viszaadja akövetkező stirngt
		System.out.println("Vnev:");
		String tkat = in.next();
		System.out.println("beosztas:");
		String beosztas = in.next();
		System.out.println("termek ear:");
		int tear = in.nextInt(); //inte konvertálva teszi be

		//Beágyazott osztálya a .Termek
		Emp dolgozo = prog.new Emp(tnev,tkat,tkat, tkat, tear, tkat); //uj termék objektum
		//a new operator elé be kell teni a prog. peldanyt 
		/*
		 * A JDBC_termek a fő osztály amelynek a példány a prog
		 * a beágyazot osztályt amikor példányosítom kell a befoglalo osztály pédánya
		 * prog.new Termek( );
		 * 
		 */

		//Emp.add_new_termek(dolgozo); // a newtermek igy fogja hasznaéni aconectiont 
		
		dbm.disconnect(null);
		
		
		

	}

}
