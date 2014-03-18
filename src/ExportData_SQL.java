import java.io.BufferedInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class ExportData_SQL {

	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   // REMEMBER to add external jar linked to MySQL Connector
	   
	   static final String DB_URL = "jdbc:mysql://localhost/testcity";
	   //static final String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   
	public void insertFullData(int N, ST<Integer, String[]> st, 
			ST<Integer, int[]> st5, 
			ST<Integer, ArrayList<Integer>> st6out,
			ST<Integer, ArrayList<Integer>> st7in) {
	   Connection conn = null;
	   Statement stmt = null;
	   //System.out.println("Type in the name of the file to read");
	   //Scanner s = new Scanner(new BufferedInputStream(System.in));
	   
	   //String filename = s.next();
	   //s.close();
	   String table = "fullinfo";
	   
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      
	      					
			String header = "INSERT INTO "+table+" ( ID, City , State, Lat, Lon, " +
					"outcount, incount, " +
					"outcity1, outcity2, outcity3, outcity4, outcity5, outcity6, outcity7, outcity8, " +
					"incity1, incity2, incity3, incity4, incity5, incity6, incity7, incity8) VALUES ";
			int batch = 1;
			//int id = 1;
			String batchValue ="";
			String fullValue ="";		
				 for (int i = 0 ; i<N ; i++){
					 String[] info = st.get(i);
					 System.out.println("");
					 //System.out.println("ID");
					 System.out.println(i);
					 int id = i;
					 //System.out.println("city name");
					 System.out.println(info[1]);
					 String city = info[1].replaceAll("\'", "_");
					 //System.out.println("State name");
					 System.out.println(info[2]);
					 String state = info[2].replaceAll("\'", "_");
					 //System.out.println("Latitude");
					 System.out.println(info[3]);
					 String lat =info[3]; 
					 //System.out.println("Longitude");
					 System.out.println(info[4]);
					 String lon =info[4]; 
					 //System.out.println("Outbound Count");
					 System.out.println(st5.get(i)[0]);	
					 int outcount =st5.get(i)[0];
					 //System.out.println("Inbound Count");
					 System.out.println(st5.get(i)[1]);
					 int incount =st5.get(i)[1];
					 //System.out.println("Outbound City ID:");
					 
					 //ArrayList<Integer> oa = new  ArrayList<Integer>( st6out.get(i));
					 //int oaSize = oa.size();
					 int[] outcity = new int[8];
					 for (int p = 0  ; p < outcity.length ; p++) {
						// System.out.print("check p = "+p +" size = " + st6out.get(i).size());
						 if (p < st6out.get(i).size())
							 outcity[p] = st6out.get(i).get(p); 
						 else
							 outcity[p] = -1;
						 
					 }
					 System.out.println();
					 //System.out.println("Inbound City ID:");				
					 int[] incity = new int[8];
					 for (int p = 0 ; p < incity.length ; p++) {
						 //System.out.print(x + ", ");
						 if (p < st7in.get(i).size())
							 incity[p] = st7in.get(i).get(p); 
						 else
							 incity[p] = -1;
						 
					 }
			
	    		String value = "( "+id+", "+"'"+ city+"'" + ", " + "'"+state +"'"+", "+ lat + ", " + lon + ", " +
	    				outcount+", "+incount+", " + 
	    				outcity[0] +", "+outcity[1]+", "+outcity[2]+", "+outcity[3]+", "+outcity[4]+", "+outcity[5]+", "+outcity[6]+", "+outcity[7]+", "+
	    				incity[0] +", "+incity[1]+", " +incity[2]+", " +incity[3]+", " +incity[4]+", " +incity[5]+", " +incity[6]+", " +incity[7]+")";
	    		batchValue = batchValue +","+ value;
	    		fullValue= fullValue + value;
	    		
	    		if (batch ==100 ){
	    			System.out.println("batch");
	    			batchValue = batchValue.replaceAll(",$", "");
	    			batchValue = batchValue.replaceAll("^,", "");
	    			System.out.println("batch output:"+ header + batchValue);
	    			
	    			String sql = header+ batchValue;
	    		    stmt.executeUpdate(sql);
	    		    
	    			batchValue = "";
	    			batch = 1;
	    			//Thread.sleep(300);
	    		}
	    		
	    		id++;
	    		batch++;
	    		System.out.println("values:"+value);
			}
			
			
			
			//output = header + fullValue;
			System.out.println("Generating last utput:" + header+batchValue);
			if (batchValue != "") {
				batchValue = batchValue.replaceAll(",$", "");
 			batchValue = batchValue.replaceAll("^,", "");
 			String sql = header+ batchValue;
 			stmt.executeUpdate(sql);
			}
	            
	      //ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      
	      //STEP 6: Clean-up environment
	      
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
	}//end insertfull method
	
	public void insertPartData(int N, ST<Integer, String[]> st, 
			ST<Integer, int[]> st5) {
	   Connection conn = null;
	   Statement stmt = null;
	   //System.out.println("Type in the name of the file to read");
	   //Scanner s = new Scanner(new BufferedInputStream(System.in));
	   
	   //String filename = s.next();
	   //s.close();
	   String table = "halfinfo";
	   
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      
	      					
			String header = "INSERT INTO "+table+" ( ID, City , State, Lat, Lon, " +
					"outcount, incount" + ") VALUES ";
			int batch = 1;
			//int id = 1;
			String batchValue ="";
			String fullValue ="";		
				 for (int i = 0 ; i<N ; i++){
					 String[] info = st.get(i);
					 System.out.println("");
					 //System.out.println("ID");
					 System.out.println(i);
					 int id = i;
					 //System.out.println("city name");
					 System.out.println(info[1]);
					 String city = info[1].replaceAll("\'", "_");
					 //System.out.println("State name");
					 System.out.println(info[2]);
					 String state = info[2].replaceAll("\'", "_");
					 //System.out.println("Latitude");
					 System.out.println(info[3]);
					 String lat =info[3]; 
					 //System.out.println("Longitude");
					 System.out.println(info[4]);
					 String lon =info[4]; 
					 //System.out.println("Outbound Count");
					 System.out.println(st5.get(i)[0]);	
					 int outcount =st5.get(i)[0];
					 //System.out.println("Inbound Count");
					 System.out.println(st5.get(i)[1]);
					 int incount =st5.get(i)[1];
					 //System.out.println("Outbound City ID:");
					
			
	    		String value = "( "+id+", "+"'"+ city+"'" + ", " + "'"+state +"'"+", "+ lat + ", " + lon + ", " +
	    				outcount+", "+incount+")";
	    		batchValue = batchValue +","+ value;
	    		fullValue= fullValue + value;
	    		
	    		if (batch ==100 ){
	    			System.out.println("batch");
	    			batchValue = batchValue.replaceAll(",$", "");
	    			batchValue = batchValue.replaceAll("^,", "");
	    			System.out.println("batch output:"+ header + batchValue);
	    			
	    			String sql = header+ batchValue;
	    		    stmt.executeUpdate(sql);
	    		    
	    			batchValue = "";
	    			batch = 1;
	    		}
	    		
	    		id++;
	    		batch++;
	    		System.out.println("values:"+value);
			}
			
			
			
			//output = header + fullValue;
			System.out.println("Generating last utput:" + header+batchValue);
			if (batchValue != "") {
				batchValue = batchValue.replaceAll(",$", "");
 			batchValue = batchValue.replaceAll("^,", "");
 			String sql = header+ batchValue;
 			stmt.executeUpdate(sql);
			}
	      
	      
	      
	      
	      
	      //ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      
	      //STEP 6: Clean-up environment
	      
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("SQL close.");
	}//end main


}
