import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/******
 * @author YiweiHuang
 * 
 * email: yh2565@columbia.edu
 * Version 1.02
 * 
 * Content:
 * A menu system
 * 
 * N: the number of the cities
 * G: the graph computed by the given algorithm in the project guidelines
 * 
 * A balanced search tree st - Key:City ID  Value: City Info
 * A balanced search tree st2 - Key:State name Value: City Info
 * A balanced search tree st5 - Key:City ID  Value: In/Out info
 * 
 * tree st:
 * Key: City ID
 * st.Value[0]:City ID
 * st.Value[1]:City Name
 * st.Value[2]:State of the city
 * st.Value[3]:Latitude of the City
 * st.Value[4]:Longitude of the City (Regardless the correctness of the worldcities.txt data)
 * st.Value[5]:Reserved
 * st.Value[6]:Reserved
 * st.Value[7]:Reserved
 * 
 * tree st2:
 * Key: State name
 * st2.Value[0]:City Count
 * st2.Value[1]:City Name 1...
 * st2.Value[2]:City Name 2...
 * st2.Value[100]:City Name 101 ; Null if no city 101
 * 
 * tree st3:
 * Key: City Name
 * st3.Value[0]:City ID
 * st3.Value[1]:City Name
 * st3.Value[2]:State of the city
 * st3.Value[3]:Lat of the City
 * st3.Value[4]:Long of the City
 * st3.Value[5]:Reserved
 * st3.Value[6]:Reserved
 * st3.Value[7]:Reserved
 * 
 * 
 * tree st5:
 * Key: City ID
 * st5.Value[0]:Outbound flight Count
 * st5.Value[1]:Inbound flight Count
 * st5.Value[2]:Reserved
 * 
 * tree st6out:
 * Key: City ID
 * st6.Value.get(0): Outbound city1
 * st6.Value.get(1): Outbound city2
 * 
 * tree st7in:
 * Key: City ID
 * st7.Value.get(0): Inbound city1
 * st7.Value.get(1): Inbound city2
 * 
 * 
 * Class distGPS support option h.
 * Calculating the distance with the coordinates given by the txt file.
 * 
 * txt files:
 * fict100.txt
 * worldcities.tt
 * test1.txt   - a small test file to demo the feasibility of the program
 * Readme.txt 
 * 
 * Case-sensitve for user input
 * The closest city includes itself
 * 
 */
public class main {
	
	private static int N;
	private static int theID ; // make default random number if user not put in an N 
	private static String theCity;
	public static MyGraphMap G;
	
	static ST<Integer, String[]> st = new ST<Integer, String[]>(); //blanced stree st
	static ST<String, String[]> st2 = new ST<String, String[]>();  //blanced stree st2
	static ST<String, String[]> st3 = new ST<String, String[]>();  //blanced stree st3
	static ST<Integer, int[]> st5 = new ST<Integer, int[]>();  //blanced stree st5
	static ST<Integer, ArrayList<Integer>> st6out= new ST<Integer, ArrayList<Integer>>(); 
	static ST<Integer, ArrayList<Integer>> st7in= new ST<Integer, ArrayList<Integer>>();
					
	
	public static void optionA(String filename) {
		File file = new File(filename);
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//In in = new In(file);    		 	  
    	 // read one line at a time from file in current directory
    	System.out.println("-----------------------------------------------------------");
        System.out.println("Load up the List ");
        
        try {
            
        	N = Integer.parseInt(in.nextLine());
        	System.out.println("N is " + N);            
            int id=0;
            int outCount = 0;
            int inCount = 0;
        	while (in.hasNext()) {
        		        		
        		String[] info = new String[8];       // create string array to store info of a city
        		info[0] = String.valueOf(id);          // info[0] = id                                
                               
        		String[] field = in.nextLine().trim().split(", "); 
        		info[1] = field[0];        		
                
                in.useDelimiter("\\s");                 // reset Delimiter to space
                               
                
                if (field.length > 1) {             	
                info[2] = field[1];
                } else {
                	info[2] = "X";
                	info[1] = info[1].substring(0, info[1].length() - 1);
                }   //info[2] = State                
                
                info[3] = in.nextLine();                 
                info[4] = in.nextLine();                
                info[5] = String.valueOf(outCount);                      
                info[6] = String.valueOf(inCount);                       
                info[7] = "-";                        
                
                st.put(id, info);                  //st key=id                
                st3.put(info[1], info);            //st3 key = city
                
                String[] cities = new String[101];    //st2 key = State (Assume each State has max 101 cities)
                									//create array for cities in the state
                cities[0] = "1";
               
                if (st2.contains(info[2])) {                	
                	cities[0] = String.valueOf((Integer.valueOf(st2.get(info[2])[0])+1));
                	int cc =  Integer.valueOf(cities[0]); //cc = city counts
                	for (int j=1; j < cc  ;j++) {
                		cities[j] =st2.get(info[2])[j];            			                		
                	}
                	cities[cc]=info[1];	
                	
                }
                else cities[1] = info[1];                
                st2.put(info[2], cities);                
                id++;  //define id of a city
            }
        	         
        	//==================== option a ============================
            System.out.println("====== a ========"); 
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("Print ST trees");
                        
            System.out.println("ST tree- Key:city id  Value:info");
            for (int s : st.keys()){
          
            StdOut.println(s + " : " + st.get(s)[0]+" "+st.get(s)[1]+" "+st.get(s)[2]+" "+st.get(s)[3]+" "+st.get(s)[4]+" "+st.get(s)[5]+" "+st.get(s)[6]+" "+st.get(s)[7]);
            }
            System.out.println();
                     
            //================== construct Graph====================
            System.out.println("======Construct Graph========");  // should change 2~8 random number s later
            
            G = new MyGraphMap(N,0); // == MyGraphMap(N)

            for(int k=0;k<G.st4().size();k++){
            	int k_out = G.st4out(k);
            	int k_in = G.st4in(k);
            	int[] update ={k_out, k_in, 0};
            	st5.put(k, update);
            	
            }
                        
            StdOut.println(G);
            System.out.println();
            System.out.println("number of edges E :" + G.E());
            System.out.println("number of veritcies V :" + G.V());   
            
        }catch (Exception e) { System.out.println(e); }
        System.out.println("==========================");
        System.out.println();
		
	}
	private static void optionB(String state) {

		//==================== option b ============================
        System.out.println("====== b ========");
        System.out.println("option b. search cities in a State");
        
        String ss = state;          // search state - "ss"    
        
        if (st2.contains(ss)){  // check if state "ss" is in the st2 tree
        	int cc1 = Integer.valueOf(st2.get(ss)[0]); // city counts of state ss
        	
                    String[] resultB = st2.get(ss); // get the value/info of ss state  
                    System.out.println("State - "+ss+" - has" + cc1 +"cities : ");
                    for (int i1=1; i1< cc1+1;i1++){ //print out the value/info of state ss in st2 tree
                    	int cityID1 =Integer.valueOf(st3.get(resultB[i1])[0]);
                    	int[] info1 = st5.get(cityID1); //load in/out count from tree st5
                    	System.out.println("*"+resultB[i1]+" : "+"outbound " + info1[0] + "/ inbound " + info1[1]);
                    	
        	}
        	System.out.println();
        } else System.out.println("The data does not contains " + ss);
        System.out.println("=======================");
	}
	private static void optionC(String city) {
		
		//=================== option c ================
        System.out.println("====== c ========");
        System.out.println("option c. search city using st3");
               
        String sc = city;   // search city "sc"
        if (st3.contains(sc)){   // check if city "sc" is in the st3 tree
        	System.out.println("search for: "+sc);
        
        	String[] resultC = {st3.get(sc)[0], st3.get(sc)[1], st3.get(sc)[2], st3.get(sc)[3], st3.get(sc)[4], String.valueOf((Integer.valueOf(st3.get(sc)[5])+1)),st3.get(sc)[6]+" "+st3.get(sc)[7]};
        	System.out.println("city id: " +resultC[0]);
        	System.out.println("State:" + resultC[2]);
        	System.out.println("Outbound count: " + st5.get(Integer.valueOf(resultC[0]))[0]); //get in/out data from st5 tree
        	System.out.println("Inbound count: " + st5.get(Integer.valueOf(resultC[0]))[1]);
        	//System.out.println(resultC);
        } else System.out.println("city not found");
        System.out.println("=====================");
        
		
	} 
	private static void optionD(int ccID) {

		//==================== option d ============================
        System.out.println("====== d ========");
        System.out.println("option d. set curretn city");
        int setid = ccID; // setid = the id of the city we want to set as the starting point 
                       // need to make this public static
        theID = ccID;
        String resultD = st.get(setid)[1]; //show the city use st tree
        theCity = resultD;
        System.out.println("ID :" + setid);
        System.out.println("The city is :" + resultD);
        System.out.println("=================================");
	}
	
	private static void optionE() {
		
		//==================== option e ============================
	            System.out.println("====== e ========");
	            System.out.println("option e. show curretn city info");
	            String currentCity = theCity;
	            if (theID == -1) System.out.println("Please set current city ID..");
 	            String[] resultE = st.get(theID);
	            //System.out.println("Current City" + currentCity +" info: ");
	            System.out.println(" City: " + resultE[1]);
	            System.out.println(" City ID: " + theID);
	            System.out.println(" State: " + resultE[2]);
	            System.out.println(" GPS Lat: " + resultE[3]);
	            System.out.println(" GPS Long: " + resultE[4]);
	           //System.out.println(" Out count: " + resultE[5]);
	            
            	int[] info1 = st5.get(theID); //load in/out count from tree st5
            	System.out.println(" Outbound/Inbound: " + info1[0] + " / " + info1[1]);
	            System.out.println("=================================");
	}
	
	private static void optionF(int neighborN) {
		
		System.out.println("====== f ========");
		System.out.println("option f. find nearest N neighborN");
		ST<Double, String[]> st4 = new ST<Double, String[]>();  //Initialize treemap4 ; maybe should put on top
		//String[] listF = new String[ (neighborN + 1)];  // first item is source itself
		if (theID == -1) theID = (int)(Math.random()*(N+1)); // if user didn't specify the current city
		double lat1 = Double.parseDouble(st.get(theID)[3]);
		System.out.println("s.lat= " + lat1);
		double lon1 = Double.parseDouble(st.get(theID)[4]);
		System.out.println("s.lon= " + lon1);		
		// The calculation of the following takes O(N lgN)
		for (int i=0 ; i < N ; i++){						
			
			double lat2 = Double.parseDouble(st.get(i)[3]);
			double lon2 = Double.parseDouble(st.get(i)[4]);									
			double d = distGPS(lat1, lon1, lat2, lon2);  //d is not the actual distance, just a relative value			
		    st4.put(d, st.get(i)); //copy infor in st or st4.put(d, i)   
		    //listF[i] = d;
		    
		}
		//Arrays.sort(listF);
		
		System.out.println( neighborN + " nearest neighbors are...");		
		for (int i=0 ; i < neighborN ; i++) {    // i = cityID
			
			double minDist = st4.min();			
			System.out.println("*" + st4.get(minDist)[1]);
			st4.delete(minDist);			
			
		}
		
		System.out.println("=================================");
	}
	

	private static double distGPS(double lat1, double lon1, double lat2, double lon2) {
		   
		  //System.out.println(" distGPS working..");
		  //char unit;
		  double theta = lon1 - lon2;
		  double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
		  dist = Math.acos(dist);//acos(dist);
		  dist = Math.toDegrees(dist);
		  
		  dist =Math.round(dist*10000.0)/10000.0;
		 	  
		  return (dist);
		}

	private static void optionG(int closestN) {
		
		//========================== option G  ================
		System.out.println("====== g. ========");
		if (theID == -1) {
			theID = (int)(Math.random()*(N+1)); // if user didn't specify the current city
			System.out.println("The current city ID is set to be : " + theID);
		}
		int ids = theID;		
		int[] resultG2 = new int[closestN];
		//DijkstraSP2 sp2 = new DijkstraSP2(G, ids);
		DijkstraSP sp2 = new DijkstraSP(G, ids);
		resultG2 = sp2.nClosest(closestN, theID);    // the result of G (cityIDs)
		System.out.println("The "+ closestN + " closest cities ID are...");
		for (int i=0;i<resultG2.length;i++){ //start form i=1 since the first closest one is itself
			System.out.println("id: "+resultG2[i] + " - " + st.get(resultG2[i])[1]);
		}
		sp2.cleanQueue(); // in case the option g is chosen again, the result should be 
 
		System.out.println("=================================");
	}

	private static void optionH(int destinationID) {
		
		//================== option h.====================
        System.out.println("====== h. ========"); 
        if (theID == -1) theID = (int)(Math.random()*(N+1)); // if user didn't specify the current city
        int ids = theID ; // the source id = setid
        int idt = destinationID; // the destination id number - scan from the client
        System.out.println("Find the shortest path from " + ids + " to " + idt + " : ");
        System.out.println();
        DijkstraSP sp = new DijkstraSP(G, ids);
        if (sp.hasPathTo(idt)) {
        	StdOut.printf("%d to %d (%.2f)  ", ids, idt, sp.distTo(idt));
        	System.out.println();
        	if (sp.hasPathTo(idt)) {
                for (DirectedEdge e1 : sp.pathTo(idt)) {
                	System.out.println( st.get(e1.from())[1] + " -> " +st.get(e1.to())[1]);
                    StdOut.println(e1 + "   ");
                    
                }    
        	}    
        } else {System.out.println("No path found");}
        System.out.println();
        System.out.println("=================================");
		
	}
	
	
		
	
	public static void main(String[] args) throws IOException {
		
		printMenu();
		
		//theID = (int);
		theID = -1; // change to random number later
		
		
		while (true) {	
		
	
		
		System.out.println("Choose your option:   (m for menu)");
		String swValue = StdIn.readString();
		
		
		int swValue2 = toSwitchNum(swValue);	
		
		
		switch (swValue2) {
		 case 1:
			 System.out.println("Option a selected");
			 System.out.println("Input the filename");
			 try {
			 String file = StdIn.readString();
			 
			 System.out.println("Load up " + file+ "...");
			 long startTime0 = System.currentTimeMillis();
			 optionA(file);
			 long endTime0   = System.currentTimeMillis();
			 long totalTime0 = endTime0 - startTime0;
			 System.out.println("Running Time: " + totalTime0 +"mSec");
			
			 } catch (Exception e) { System.out.println(e); }
			 
			 
			 break;
		
		 case 2:
			 System.out.println("Option b selected");
			 System.out.println("Input a state");
			 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		      String state = null;
	
			 try {
			 state = br.readLine();
			 
			 System.out.println("List cities of  " + state + "...");
			 optionB(state);
			 } catch (Exception e) { System.out.println(e); }
			 break;
			 
		 case 3:
			 System.out.println("Option c selected");
			 System.out.println("Input a city");
			 BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
	
		      String city = null;
	
			 try {
			 city = br2.readLine();
			 
			 System.out.println("Info of  " + city + "...");
			 optionC(city);
			 } catch (Exception e) { System.out.println(e); }
			 break;
			 
		 case 4:
			 System.out.println("Option d selected");
			 System.out.println("Input an ID");
			 BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
	
		      int ccID = 0;
	
			 try {
			 ccID = StdIn.readInt();
			 
			 
			 optionD(ccID);
			 System.out.println("Current City ID is  " + theID + "...");
			 System.out.println("Current City is  " + theCity + "...");
			 } catch (Exception e) { System.out.println(e); }
			 break;
			 
		 case 5:	 
			 System.out.println("Option e selected");
			 System.out.println("Show current city info");		 
	
			 System.out.println("ID:" + theID + "...");
			 
			 optionE();
	
			 break;
			 
		 case 6:	 
			 System.out.println("Option f selected");
			 System.out.println("Input N for number of neighbors");		 
			 
			 try {
			  int neighborN = StdIn.readInt();
	
			 optionF(neighborN);
			 
			 
			 
			 } catch (Exception e) { System.out.println(e); }
			 break;
			 
		 case 7:
			 System.out.println("OPtion g selected");
			 System.out.println("Find N closest cities(by weight)");
			 System.out.println("Input N..");
			 try {
				 int closestN = StdIn.readInt();
				 optionG(closestN);
				 
			 } catch (Exception e) { System.out.println(e); }
			 break;
			 
		 case 8:
			 System.out.println("Option h selected");
			 System.out.println("Find the shortest path");		 
			 long startTime = System.currentTimeMillis();
			 try {
				 System.out.println("Input destination city ID..");
				 int destinationID = StdIn.readInt();
	
			 optionH(destinationID);
			 
			 
			 
			 } catch (Exception e) { System.out.println(e); }
			 long endTime   = System.currentTimeMillis();
			 long totalTime = endTime - startTime;
			 System.out.println("Running Time: " + totalTime + "mSec");
			 break;
			 
		 case 9:
			 System.out.println("Option i selected - Quit");
			 System.exit(0);
			 break;
			 
		 case 10:
			 printMenu();
			 break;
			 
		 case 20:
			 System.out.println("Output the calculated graph in local trees");
			 //Initialize st6out, st7in trees
			 for (int i = 0 ; i<N ; i++){
				 ArrayList<Integer> value = new ArrayList<Integer>();
				 st6out.put(i, value);
				 st7in.put(i, value);
			 }
			 
			 for (DirectedEdge e : G.edges()){
				 //get id
				 int from = e.from();
				 int to = e.to();
				 System.out.println("from: "+from+ " to: "+ to);
				 
				 //update
				 System.out.println("add st6 id:" + from);
				 ArrayList<Integer> al6c = new ArrayList<Integer> ( st6out.get(from));
				 if (!al6c.contains(from)) al6c.add(to);
				 st6out.put(from, al6c);
				 for (int x1:st6out.get(from)) {
					 System.out.print(x1 + ", ");
				 }
				 System.out.println();
				 ArrayList<Integer> al7c = new ArrayList<Integer> ( st7in.get(to));
				 if (!al7c.contains(to)) al7c.add(from);
				 st7in.put(to, al7c);
				 for (int x2:st7in.get(to)) {
					 System.out.print(x2 + ", ");
				 }
				 System.out.println();
			 }
			 
			 System.out.println("Full information");
			 System.out.println("=============");
			 System.out.println("");
			 System.out.println("ID");
			 System.out.println("city name");
			 System.out.println("State name");
			 System.out.println("Latitude");
			 System.out.println("Longitude");
			 System.out.println("Outbound Count");
			 System.out.println("Inbound Count");		 
			 System.out.println("Outbound City ID:");
			
			 System.out.println("Inbound City ID:");
			 System.out.println();
			 		 
			 System.out.println(N);
			 System.out.println();
			 System.out.println("Type in the filename for the output file:");
			 Scanner s = new Scanner(new BufferedInputStream(System.in));
			 String filename= s.next();
			 
			 PrintWriter out = new PrintWriter(new FileWriter(filename));
			
			 //city =city.replaceFirst("\'", "\'\'");	
			 
			 for (int i = 0 ; i<N ; i++){
				 String[] info = st.get(i);
				 System.out.println("");
				 //System.out.println("ID");
				 System.out.println(i);
				 //System.out.println("city name");
				 System.out.println(info[1]);
				 //System.out.println("State name");
				 System.out.println(info[2]);
				 //System.out.println("Latitude");
				 System.out.println(info[3]);
				 //System.out.println("Longitude");
				 System.out.println(info[4]);
				 //System.out.println("Outbound Count");
				 System.out.println(st5.get(i)[0]);			
				 //System.out.println("Inbound Count");
				 System.out.println(st5.get(i)[1]);
				 //System.out.println("Outbound City ID:");
				 
				 ArrayList<Integer> oa = st6out.get(i);
				 for (int x:oa) {
					 System.out.print(x + ", ");
				 }
				 System.out.println();
				 //System.out.println("Inbound City ID:");
				 ArrayList<Integer> ia = st7in.get(i);
				 for (int x2:ia) {
					 System.out.print(x2 + ", ");
				 }
				 System.out.println();
				 
				 //out.println();
				 out.println(i+1);
				 out.println(info[1].replaceAll("\'", "_"));
				 out.println(info[2].replaceAll("\'", "_"));
				 out.println(info[3]);
				 out.println(info[4]);
				 out.println(st5.get(i)[0]);
				 out.println(st5.get(i)[1]);
				 for (int x:oa) {
					 out.print(x + ", ");
				 }
				 out.println();
	
				 for (int x2:ia) {
					 out.print(x2 + ", ");
				 }
				 out.println();
				 
				 
			 }
			 out.close();
			 System.out.println();
			 break;
			 
		 case 30:
			 System.out.println("Output to Database");
			 
			 //=== Construst st6 , sy7 =======
			 for (int i = 0 ; i<N ; i++){
				 ArrayList<Integer> value = new ArrayList<Integer>();
				 st6out.put(i, value);
				 st7in.put(i, value);
			 }
			 
			 for (DirectedEdge e : G.edges()){
				 //get id
				 int from = e.from();
				 int to = e.to();
				 System.out.println("from: "+from+ " to: "+ to);
				 
				 //update
				 System.out.println("add st6 id:" + from);
				 ArrayList<Integer> al6c = new ArrayList<Integer> ( st6out.get(from));
				 if (!al6c.contains(from)) al6c.add(to);
				 st6out.put(from, al6c);
				 for (int x1:st6out.get(from)) {
					 System.out.print(x1 + ", ");
				 }
				 System.out.println();
				 ArrayList<Integer> al7c = new ArrayList<Integer> ( st7in.get(to));
				 if (!al7c.contains(to)) al7c.add(from);
				 st7in.put(to, al7c);
				 for (int x2:st7in.get(to)) {
					 System.out.print(x2 + ", ");
				 }
				 System.out.println();
			 }
			 
			 ExportData_SQL exeFull = new ExportData_SQL();
			 exeFull.insertFullData(N, st, st5, st6out, st7in);
			 //exeFull.insertPartData(N, st, st5);
			 break;
			 
		 default:
			 System.out.println("Invalid Selection - please try again");
			 printMenu();
			 break;			 			 			 
		}		
    }	
 }	
	public static void printMenu(){
		
		System.out.println("======================================");
		System.out.println("|               MENU                 |");
		System.out.println("|                                    |");
		System.out.println("|          YI-WEI HUANG              |");
		System.out.println("======================================");
		System.out.println("| Options:                           |");
		System.out.println("|        a. Load Up txt File         |");		
		System.out.println("|        b. Search for a State       |");
		System.out.println("|        c. Search a city            |");
		System.out.println("|        d. Set Current City(ID)     |");
		System.out.println("|        e. Show Current City        |");
		System.out.println("|        f. Find N closest(Geo)      |");
		System.out.println("|        g. Find N closest(by weight)|");
		System.out.println("|        h. Find the shortest path   |");
		System.out.println("|        i. Output Graph             |");
		System.out.println("|        j. Output to Database       |");
		System.out.println("|                                    |");
		System.out.println("|                                    |");
		System.out.println("|        x. exit                     |");
		System.out.println("|        m. menu                     |");
		System.out.println("======================================");
		
		
	}
	
	public static int toSwitchNum (String sw){
		int sw2 = 10;
		
		if (sw.equalsIgnoreCase("a")) sw2 = 1;
		else if (sw.equalsIgnoreCase("b")) sw2 = 2 ;
		else if (sw.equalsIgnoreCase("c")) sw2 = 3 ;
		else if (sw.equalsIgnoreCase("d")) sw2 = 4 ;
		else if (sw.equalsIgnoreCase("e")) sw2 = 5 ;
		else if (sw.equalsIgnoreCase("f")) sw2 = 6 ;
		else if (sw.equalsIgnoreCase("g")) sw2 = 7 ;
		else if (sw.equalsIgnoreCase("h")) sw2 = 8 ;
		else if (sw.equalsIgnoreCase("i")) sw2 = 20 ;		
		else if (sw.equalsIgnoreCase("j")) sw2 = 30 ;
		else if (sw.equalsIgnoreCase("x")) sw2 = 9 ;
		else if (sw.equalsIgnoreCase("m")) sw2 = 10 ;
		
		return sw2;
		
		
	}
	
}

