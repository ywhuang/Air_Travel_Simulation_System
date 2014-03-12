import java.io.File;
import java.sql.*;
import java.util.Scanner;
public class InsertData_SQL {
	

	
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   // REMEMBER to add external jar linked to MySQL Connector
	   
	   static final String DB_URL = "jdbc:mysql://localhost/testcity";
	   //static final String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   
	   public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   String filename = "worldcities.txt";
	   String table = "cityinfo";
	   
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      

			File file = new File(filename);
			 if (!file.exists()) System.out.println( "file not exist" );
			Scanner scanner = new Scanner(file);
			int n = Integer.parseInt(scanner.nextLine()); // city count
			System.out.println("n = "+n);
			String output = "" ;
			String header = "INSERT INTO "+table+" ( id, city , country, lon, lat ) VALUES ";
			int batch = 1;
			int id = 1;
			String batchValue ="";
			String fullValue ="";
			while (scanner.hasNext()) {
				
				String[] field = scanner.nextLine().trim().split(","); 
				//int id = line/3 +1;
	    		String city= field[0];
	    		// need ot deal with " city's " exp
	    		city =city.replaceFirst("\'", "\'\'");
	    		
	    		String country = "";
	    		if (field.length > 1) 
	    			country = field[1];
	    		else
	    			city = city.replaceAll(",$","");
	    			
	    		String lon = scanner.nextLine();
	    		String lat = scanner.nextLine();
	    		
	    		String value = "( "+id+", "+"'"+ city+"'" + ", " + "'"+country +"'"+", "+ lon + ", " + lat + " )";
	    		batchValue = batchValue +","+ value;
	    		fullValue= fullValue + value;
	    		
	    		if (batch ==100 ){
	    			System.out.println("batch");
	    			batchValue = batchValue.replaceAll(",$", "");
	    			batchValue = batchValue.replaceAll("^,", "");
	    			System.out.println("batch output:"+ header + batchValue);
	    			
	    			sql = header+ batchValue;
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
    			sql = header+ batchValue;
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
	}//end main

}
