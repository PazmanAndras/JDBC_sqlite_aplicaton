package jdbcmodule;

import java.sql.*;
import java.util.Scanner;
import java.io.*;
import java.security.interfaces.RSAKey;

public class DbMethods {
	
	
		//Metaaadat 
		public void getMeta() {
			
			 try {
		           	Connection conn = connect();
		           	
		            // Metaadatok lekérdezése
		            DatabaseMetaData metaData = conn.getMetaData();

		            // Táblák lekérdezése
		            ResultSet tables = metaData.getTables(null, null, "%", null);

		            // Táblák adatainak kiírása a konzolra
		            while (tables.next()) {
		                String tableName = tables.getString("TABLE_NAME");
		                System.out.println("TABLE NAME: " + tableName);

		                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
		                int columnCount = 0;

		                while (columns.next()) {
		                    String columnName = columns.getString("COLUMN_NAME");
		                    String columnType = columns.getString("TYPE_NAME");
		                    System.out.println("Column name: " + columnName + ", type: " + columnType);
		                    columnCount++;
		                }

		                System.out.println("Columns:: " + columnCount + "\n");
		            }

		            // Kapcsolat lezárása
		            conn.close();
		            disconnect(conn);
		            
		        } catch (SQLException e) {
		            System.out.println("Error accessing the database");
		            e.printStackTrace();
		        }
		    
			
		}
		
		
		
		//Adat felvitel majd torles, tranzakcio
 		public void deleteTable() {
 			
			System.out.println("Record torlese");
			Scanner sc = new Scanner(System.in);
			
			//Elsőnek leennőrizzük hogy megfelelő táblát ad e mega felhasznalo:
			
			boolean ok = false;
			String tableName = null;
			
			
			do {
				System.out.println("Adja meg a tablat melyben torolni szeretne: ");
				 tableName = sc.nextLine();
				if(chechTableExist(tableName)==true) {
					ok = true;
				}else {
					System.out.println("A megadott tábla nem letezik!");
					ok=false;
				}
							
			} while (ok!=true);
			
			
			System.out.println("Adja meg az alkalmazott ID melyet torni szeretne ");
			String alkId = sc.nextLine();
			
			
		
			
			
			//Ellenörzes utan az alkalmazott tablaban lehet  törölni
			
			if(tableName.equals("Alkalmazott")) {
				
				Connection conn = connect();
				PreparedStatement insertStmt = null;
				PreparedStatement updateStmt = null;
				PreparedStatement deleteStmt = null;
				
				String tableName1 = "Alkalmazott";
				int alkid = Integer.parseInt(alkId);
				
				Statement stmt = null;
				try {
					stmt = conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				//TRranzakció megkezdése
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				
								
				
				//delete ustasiats a tabla elemenek torlesere
				try {
					deleteStmt = conn.prepareStatement("DELETE FROM " +tableName1 +" WHERE ALkID = ?");
				} catch (SQLException e1) {
						e1.printStackTrace();
				}
								
				try {
										
			        //törlés
			        deleteStmt.setInt(1,alkid);
			        
			        		         			       
			        //Tranzakció végleegsítése
			        conn.commit();
			        
			        disconnect(conn);
			        
			        if (conn != null) {
			            try {
			                if (!conn.isClosed()) {
			                    conn.close();
			                }
			            } catch (SQLException e) {
			                e.printStackTrace();
			            }
			        }			        			
			        
			    } catch (SQLException e) {			        
			        
			    			e.printStackTrace();	        
			    
			    } finally {			    	
					
			    	if (insertStmt != null) {
					    try {
							  insertStmt.close();
						} catch (SQLException e) {
								e.printStackTrace();
						}
					 }	        
			    }
			}
						
			
		}
	
 		
 		public void selectFreeCourse(String tema) {
 			
			Connection conn = connect();

			try {

				// Lekérdezés végrehajtása
				String query = "SELECT Tanfolyam.Tema, Oktato.OktNev, Oktato.OktEmail  FROM Alkalmazott JOIN Alkalmazottkepesites ON Alkalmazott.AlkID = Alkalmazottkepesites.ALKID   JOIN AlkTanf ON Alkalmazott.AlkID = AlkTanf.TanID  JOIN Tanfolyam ON AlkTanf.TanID = Tanfolyam.TanID JOIN Oktato ON Oktato.oktid = Tanfolyam.oktid\r\n"
						+ "WHERE Tanfolyam.tanfar = 0 AND Tanfolyam.tema LIKE '%"+tema+"%';";
				
				
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(query);

				// Eredmény kiírása a konzolra
				while (rs.next()) {
					System.out.println(
							rs.getString("Tema") + " Oktato:" + rs.getString("OktNev") + ", " + "elerhetoseg:" + rs.getString("OktEmail") );
				}

				// Kapcsolat lezárása
				rs.close();
				statement.close();
				conn.close();
				disconnect(conn);

			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
 			
 		}
 			
 		
 				
	
		//egyes tanfolyamokat tarto tanar neve, kurzor
		 public void selectCourseTeacher() {
			 
			 Connection conn = connect();
			 
			 try {
		                     
		            String query = "SELECT Tanfolyam.tema, Oktato.OktNev FROM Tanfolyam INNER JOIN OKTATO ON Tanfolyam.TanID = Oktato.OktID ";
		            Statement statement = conn.createStatement();
		            ResultSet resultSet = statement.executeQuery(query);
		            
		            // Eredmény kiírása a konzolra kurzorral történő bejárás segítségével
		            while (resultSet.next()) {
		                String courseThema = resultSet.getString("tema");
		                String teacherName = resultSet.getString("OktNev");
		                System.out.println("Tema: " + courseThema + ", Oktato nev: " + teacherName );
		            }
		            
		            // Kapcsolat lezárása
		            resultSet.close();
		            statement.close();
		            conn.close();
		            
		        } catch (Exception e) {
		            System.err.println(e.getClass().getName() + ": " + e.getMessage());
		            System.exit(0);
		        }
			 disconnect(conn);
			 
		 }
		
		
		 
		 
		 
		//A megadott beosztasban megadja kinek a legtobb a fizetese
		public void selectMaxPayment(String beosztas) {
			
			Connection conn = connect();
			
			try {

				// Lekérdezés végrehajtása
				String query = "SELECT Alkalmazott.Vnev, Alkalmazott.Knev, Alkalmazott.Fizetes FROM Alkalmazott WHERE Alkalmazott.beosztas = '"+beosztas+"'  ORDER BY fizetes DESC LIMIT 1;";
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(query);

				// Eredmény kiírása a konzolra
				while (rs.next()) {
					System.out.println(rs.getString("Vnev") + " "  + rs.getString("Knev") + ", " + rs.getString("Fizetes") + "Ft");
				}

				// Kapcsolat lezárása
				rs.close();
				statement.close();
				conn.close();
				disconnect(conn);

			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}

		}
	
	
		//Alkalmazott melyik tanfolyamra jar
		public  void selectEmployeeAndCourse() {
			
			Connection conn = connect();
			
			try {
	            
	            
	            // Lekérdezés végrehajtása
	            String query = "SELECT Alkalmazott.Vnev, Alkalmazott.Knev, Tanfolyam.Tema FROM Alkalmazott JOIN AlkTanf ON Alkalmazott.AlkID = AlkTanf.TanID  JOIN Tanfolyam ON AlkTanf.TanID = Tanfolyam.TanID";
	            Statement statement = conn.createStatement();
	            ResultSet rs = statement.executeQuery(query);
	            
	            // Eredmény kiírása a konzolra
	            while (rs.next()) {
	                System.out.println(rs.getString("Vnev") + rs.getString("Knev") + ", " + rs.getString("Tema"));
	            }
	            
	            // Kapcsolat lezárása
	            rs.close();
	            statement.close();
	            conn.close();
	            disconnect(conn);
	            
	        } catch (Exception e) {
	            System.err.println(e.getClass().getName() + ": " + e.getMessage());
	            System.exit(0);
	        }
			
			
		}
		
	
	
		//Ez a függvény megnézi hogy a felhasznalo altal bekert tábla létezik-e (meta) 
		public  boolean chechTableExist(String maybeExistTable) {
					        
	        Connection conn = connect();

	        try (conn) {
	            DatabaseMetaData meta = conn.getMetaData();
	            ResultSet rs = meta.getTables(null, null, maybeExistTable, new String[]{"TABLE"});
	            return rs.next();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            
	            return false;							
	        }
		}
		
		
		
		//Rekord hazzadasa kivalasztott tablahoz:
		public void addRecord() {
			
			
			Scanner sc = new Scanner(System.in);
			
			//Elsőnek leennőrizzük hogy megfelelő táblát ad e mega felhasznalo:
			
			boolean ok = false;
			String tableName;
			
			do {
				System.out.println("Adja meg melyik tablaba szeretne rekordot felvinni: ");
				 tableName = sc.nextLine();
				if(chechTableExist(tableName)==true) {
					ok = true;
				}else {
					System.out.println("A megadott tábla nem letezik!");
					ok=false;
				}
							
			} while (ok!=true);
			
			Connection conn = connect();
			PreparedStatement preparedStatement = null;
			
			if(tableName.equals("Alkalmazott")) {
				
				
				String tableName1 = "Alkalmazott";
				int alkid = 0;
				
				Statement stmt = null;
				try {
					stmt = conn.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				//TRranzakció megkezdése
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				
				
				//Insert utasítás a record felvitelése
				String insertTableSQL = "INSERT INTO " + tableName1 + " VALUES (?, ?, ?, ?, ?, ?, ?)";
				
				try {
					
					//megeressük az id -t  az Id-t 
					String CommandText = "SELECT max(AlkID) FROM alkalmazott"; 
		        	try {
						ResultSet rs = stmt.executeQuery(CommandText);
						if(rs.next()) {
							alkid = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
			        preparedStatement = conn.prepareStatement(insertTableSQL);

			        // Adatok beolvasása a konzolról
			        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			        System.out.print("Vezeteknev: ");
			        String vnev = br.readLine();
			        System.out.print("Keresztnev: ");
			        String knev = br.readLine();
			        System.out.print("Szuetesi ev: ");   //Itt ellenőrzés hogy biztos szám-e
			        String szulev = br.readLine();
			        System.out.print("Beosztas: ");
			        String beosztas = br.readLine();
			        System.out.print("Fizetes: ");
			        String fizetes = br.readLine();
			        System.out.print("Telefon:: ");
			        String alktel = br.readLine();

			        // Paraméterek beállítása
			        preparedStatement.setInt(1, alkid+1); //Automatikusan növeli az ID-t
			        preparedStatement.setString(2, vnev);
			        preparedStatement.setString(3, knev);
			        preparedStatement.setInt(4, Integer.parseInt(szulev));
			        preparedStatement.setString(5, beosztas);
			        preparedStatement.setInt(6, Integer.parseInt(fizetes));
			        preparedStatement.setString(7, alktel);

			        // Kétfázisú parancs végrehajtása
			        preparedStatement.executeUpdate();	 
			       
			        //Tranzakció végleegsítése
			        conn.commit();
			        
			        disconnect(conn);
			        
			        if (conn != null) {
			            try {
			                if (!conn.isClosed()) {
			                    conn.close();
			                }
			            } catch (SQLException e) {
			                e.printStackTrace();
			            }
			        }			        			
			        
			    } catch (SQLException | IOException e) {			        
			        
			    			e.printStackTrace();	        
			    
			    } finally {			    	
					
			    	if (preparedStatement != null) {
					    try {
							  preparedStatement.close();
						} catch (SQLException e) {
								e.printStackTrace();
						}
					 }	        
			    }
			       
				
				
			}else if(tableName.equals("Oktato")){
				
				disconnect(conn);
				
				Connection conn2 = connect();
				PreparedStatement preparedStatement2 = null;
				
				//TRranzakció megkezdése
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				
				
				String tableName1 = "Oktato";
				int oktID = 0;
				
				Statement stm = null;
				try {
					stm = conn2.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				
				
				String insertTableSQL = "INSERT INTO " + tableName1 + " VALUES (?, ?, ?, ?)";
				
				try {
					
					//megkeressük  az utolso Id-t 
					String CommandText = "SELECT max(OktID) FROM Oktato";  
		        	try {
						ResultSet rs = stm.executeQuery(CommandText);
						if(rs.next()) {
							oktID = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
			        preparedStatement2 = conn2.prepareStatement(insertTableSQL);

			        // Adatok beolvasása a konzolról
			        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			        System.out.print("Teljes nev:: ");
			        String teljesnev = br.readLine();
			        System.out.print("Szakterulet: ");
			        String szakterulet = br.readLine();
			        System.out.print("Email: ");
			        String email = br.readLine();
			       
			      

			        // Paraméterek beállítása
			        preparedStatement2.setInt(1, oktID+1); //Automatikusan növeli az ID-t
			        preparedStatement2.setString(2, teljesnev);
			        preparedStatement2.setString(3, szakterulet);
			        preparedStatement2.setString(4, email);
		        

			        // Kétfázisú parancs végrehajtása
			        preparedStatement2.executeUpdate();
			        
			        //tranzakcio veg.
			        conn2.commit();
			        
			        disconnect(conn2);
			        
			        if (conn2 != null) {
			            try {
			                if (!conn.isClosed()) {
			                    conn.close();
			                }
			            } catch (SQLException e) {
			                e.printStackTrace();
			            }
			        }
			        
			    } catch (SQLException e) {			        
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    } finally {
			        if (preparedStatement != null) {
			            try {
							preparedStatement.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
			        }
			    }
				
			}
						
					
			
		}
		
		
			
		//Megadott tabla adatainak recordjainak lementese txtbe
		public void saveRecord(String tableName) {
			
			Connection conn = connect();
			Statement statement = null;
			ResultSet rs = null;
			//String file = "table.txt";
			
			try {
				statement = conn.createStatement();
				String sql = "SELECT * FROM " + tableName;
				rs = statement.executeQuery(sql);
				
				//metaadat segitsegevel oszlopok szamanak meghatarozasa
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				
				//Filewriter
				 File file = new File(""+tableName+".txt");
			     FileWriter fwrite = new FileWriter(file);
			     
			     // Megszámoljuk az oszlopok számát és kiírjuk a fejlécet
			      ResultSetMetaData rsmd = rs.getMetaData();
			      columnCount = rsmd.getColumnCount();
			      for (int i = 1; i <= columnCount; i++) {
			        String columnName = rsmd.getColumnName(i);
			        fwrite.write("\t"+"|"+columnName+"\t" + "|"+  "\t");
			      }
			      fwrite.write("\n");

			      // Kiírjuk a tábla elemeit a fájlba
			      while (rs.next()) {
			        for (int i = 1; i <= columnCount; i++) {
			          String value = rs.getString(i);
			          fwrite.write("\t"+ "|"+value +"\t" + "|"+ "\t");
			        }
			        fwrite.write("\n");
			      }
			      fwrite.close();				
	
			} catch (SQLException e) {
				System.out.println("Hiba az adatbazis eleresekor!");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
	
				try {
					disconnect(conn);
				} catch (Exception exc) {
					System.out.println("Hiba a lecsatlakozas soran:");
					System.out.println(exc.getMessage());
				}
			}
			
			
			
			
		}
		
		
		//kiválasztott tábla elemeinek  listázás
		public void printTable(String tableName) {
	
			Connection conn = connect();
			Statement statement = null;
			ResultSet rs = null;
	
			try {
	
				// SQL lekérdezés végrehajtása
				statement = conn.createStatement();
				String sql = "SELECT * FROM " + tableName;
				rs = statement.executeQuery(sql);
	
				//meta
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				
				//kiiratjuk a tabla elemeit
				while (rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						System.out.print(String.format(("|"+rs.getString(i)+ "|" + "\t")));
					}
					System.out.println();
				}
	
			} catch (SQLException e) {
				System.out.println("Hiba az adatbazis eleresekor!");
				e.printStackTrace();
			} finally {
	
				try {
					disconnect(conn);
				} catch (Exception exc) {
					System.out.println("Hiba a lecsatlakozas soran:");
					System.out.println(exc.getMessage());
				}
			}
		}
		
	
		
		//Felhasznalo altal megadot tabla letrehozasa	
		
		public void createTable(String tableName) {
			
			Connection conn = connect();
			Statement stm = null;

			try {
				// Tábla létrehozása
				stm = conn.createStatement();
				String sql = "CREATE TABLE IF NOT EXISTS " + tableName
						+ " (id int PRIMARY KEY, name varchar2(20), email varchar2(30) NOT NULL);";
				
				stm.execute(sql);
				System.out.println("A tabla letrehozasa sikeres.");

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
					
					disconnect(conn); // Kapcsolat bontása
			}

		}
	
		
		
		// metodus egyirányú parancsoka kiadására, pl: create tabe
		public void commandExec(String command) {
			Connection conn = connect();
			String sqlp = command;
			
			System.out.println("Command:" + sqlp);
			try {
				 Statement stm = conn.createStatement();
				 stm.execute(sqlp);
				 System.out.println("Command OK!");
				 
			} catch (Exception exc) {
				System.out.println("Command Exec:" + exc.getMessage());
			}
			disconnect(conn);			
		}
				
		
		//Felhasznalo azonosyyitas
		 public static boolean authenticate(String username, String password) {
			 	
			 
			 	//Felhasználónév egy másik adatbázisban tárolódik
				String url = "jdbc:sqlite:C:/appsqlite3/users.db";
				
				String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

				try (Connection connect = DriverManager.getConnection(url);
						PreparedStatement pstmt = connect.prepareStatement(sql)) {

					pstmt.setString(1, username);
					pstmt.setString(2, password);

					ResultSet rs = pstmt.executeQuery();
					
					// igazzal tér vissza ha létezik
					return rs.next();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}

				return false;
			}

		// Kapcsolodas a db hez
		public Connection connect() {
			
			Connection conn = null;
			String url = "jdbc:sqlite:C:/appsqlite3/kepzesdb.db";
			
				try {
						conn = DriverManager.getConnection(url);
						//message("Succesfull connection!");
						return conn;
					
				} catch (Exception ex) {
					message(ex.getMessage());
					return conn;
					
				}
			
		}
		
		
		// Lekapcsolodas
		public void disconnect(Connection conn) {
			if(conn != null) {
				try {
					 conn.close();
					 //message("Succesfull disconnect!");
				}catch(Exception ex) {
					message(ex.getMessage());
				}
			}
		}
		
		
		// Driver regisztrálás
		public void driveRegistration() {
	
			try {
				Class.forName("org.sqlite.JDBC");
				//message("Driver registration: OK");
	
			} catch (Exception ex) {
	
				message(ex.getMessage());
			}
	
		}
		
	
		// üzenet kiirása 
		public void message(String s) {
	
			System.out.println(s + "\n");
		}



		public void updateTable() {
			
			
		}

		

}
