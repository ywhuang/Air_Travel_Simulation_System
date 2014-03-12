import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
// need to linked to external jar 



public class UpdateCount {
	   
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	// REMEMBER to add external jar linked to MySQL Connector
	   
	static final String DB_URL = "jdbc:mysql://localhost/testcity";
	//static final String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";
	 
	
	
	  public void updateCount(int v, int w) {
	   
		  Connection conn = null;
		  Statement stmt = null;	  
		  String table = "counts";	    		    		    	
		  int batch = 1;
		  	    		    	    			
    		//======= JDBC Operation ========
    	try{
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String header1 = "UPDATE "+table + " SET outbound = outbound + 1 ";		      
	      String tail1 = "WHERE id = " + v;
	      String header2 = "UPDATE "+table + " SET outbound = inbound + 1 ";		      
	      String tail2 = "WHERE id = " + w;
	
	      
	      String sql1 = header1+tail1;
	      System.out.println("exe: " + sql1);
	      stmt.executeUpdate(sql1);
	      
	      String sql2 = header2+tail2;
	      System.out.println("exe: " + sql2);
	      stmt.executeUpdate(sql2);  		
		
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
		   System.out.println("Connection Close");
		   
	    			
	    			
	    			
	    		}
	    	}
	
	   
	   
	


