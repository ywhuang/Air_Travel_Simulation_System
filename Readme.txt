README.TXT
====================

Name:Yi-Wei Huang
email: yh2565@columbia.edu
=======================

Abstract:


*********************************************
main.java

Compilation: javac main.java
Execution:     java main

 
Content:
A map travel system that can search a particular destination, display city information, 
,find shortest path based on edge costs or GPS distances.

Menu:
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
		System.out.println("|        f. Find N closest(GPS)      |");
		System.out.println("|        g. Find N closest(by weight)|");
		System.out.println("|        h. Find the shortest path   |");
		System.out.println("|        i. Output Graph to txt      |");
		System.out.println("|        j. Sync to MySql            |");
		System.out.println("|                                    |");
		System.out.println("|                                    |");
		System.out.println("|        x. exit                     |");
		System.out.println("|        m. menu                     |");
		System.out.println("======================================");
Choose your option:   (m for menu)

 *  
 * A menu system
 * 
 * N: the number of the cities
 * 
 * G: the graph computed by the given algorithm in the hw guidelines
 * 
 * A balanced search tree st - Key:City ID  Value: City Info
 * 
 * A balanced search tree st2 - Key:State name Value: City Info
 * 
 * A balanced search tree st5 - Key:City ID  Value: In/Out info
 * 
 * 
 * tree st:
 * Key: City ID
 * st.Value[0]:City ID
 * st.Value[1]:City Name
 * st.Value[2]:State of the city
 * st.Value[3]:Latitude of the City
 * st.Value[4]:Longitude of the City  
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
 * st2.Value[0]:Outbound flight Count
 * st2.Value[1]:Inbound flight Count
 * st2.Value[2]:Reserved
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
 
/*************************************************************************
MyGraphMap.java
  
 *  An edge-weighted digraph, implemented using adjacency lists.
 *  The MyGraphMap class represents an directed graph of vertices
 *  named 0 through V-1, where each edge has a real-valued weight.
 *  It supports the following operations: add an edge to the graph,
 *  iterate over all of edges leaving a vertex.
Running Time: O(8*N)   
/*************************************************************************


 *************************************************************************/
DijkstraSP.java
 *  Similar to DijkstraSP2.java
 *  Support item H - Find the shortest path for s to t
 *  In this class, we use a binary heap for priority queue operations (IndexMinPQ.java)
 *  Thus to achieve the running time for (N log N)
 *  Compilation:  javac DijkstraSP.java
 *  
 *  Dependencies: MyGraphMap.java IndexMinPQ.java Stack.java DirectedEdge.java
 *  
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
Running Time: O((N+E) lg N)
 *************************************************************************/
ST.java
 *  Support the operation a, b, c, and other operations for loading/storing city IDs and names
 *  Stores the data in a balanced search tree imported from java treemap class
 *
 *  This class represents an ordered symbol table. It assumes that
 *  the keys are Comparable.
 *  It supports the usual put, get, contains,
 *  and remove methods.
 *  It also provides ordered methods for finding the minimum,
 *  maximum, floor, and ceiling.
 *  The class implements the associative array abstraction: when associating
 *  a value with a key that is already in the table, the convention is to replace
 *  the old value with the new value.
 *  The class also uses the convention that values cannot be null. Setting the
 *  value associated with a key to null is equivalent to removing the key.
 *  
 *  This class implements the Iterable interface for compatiblity with
 *  the version from Introduction to Programming in Java: An Interdisciplinary
 *  Approach.
 *  
 *  This implementation uses a balanced binary search tree.
 *  The put, contains, remove, minimum,
 *  maximum, ceiling, and floor methods take
 *  logarithmic time.

The operation of search/input/delete: O(lgN)
 * option a,b,c,d,e use ST.java O(lgN)
 * option f - find n closest gps distance takes O(N*lgN)
 * option g - O(E*lgN)
 * option h - O(E*lgN)



 *************************************************************************/

Auxiliary classes:
  Bag.java
  Stack.java
  Queue.java
  IndexMinPQ.java
  DirectedEdge.java


  
 



