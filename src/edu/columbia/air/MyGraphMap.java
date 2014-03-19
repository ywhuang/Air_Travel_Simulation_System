package edu.columbia.air;
import edu.columbia.air.helper.*;
import java.util.ArrayList;
import java.util.Collections;

/*************************************************************************
 *  UNI: yh2565
 *  Yi-wei Huang
 *  yh2565@columbia.edu
 *  Compilation:  javac MyGraphMap.java
 *  Execution:    java MyGraphMap V E
 *  Dependencies: Bag.java DirectedEdge.java
 *
 *  
 *
 *************************************************************************/

/**
 *  The <tt>MyGraphMap</tt> class represents an directed graph of vertices
 *  named 0 through V-1, where each edge has a real-valued weight.
 *  It supports the following operations: add an edge to the graph,
 *  iterate over all of edges leaving a vertex.
 *  <p>
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */



public class MyGraphMap {
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    static ST<Integer, int[]> st4 = new ST<Integer, int[]>();  //a balanced tree to store in/out count
        
    /**
     * Create an empty edge-weighted digraph with V vertices.
     */
    
    public MyGraphMap(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

   /**
     * Create a edge-weighted digraph with V vertices and E edges.
     * @param V  V should equal to N
     * @param s  s doesnt not affect the program, simply set to zero
     * 
     *
     */
    public MyGraphMap(int V, int s) {  // Construct the graph using the algorithm given by instructions
    	                                        // s  is not used (?)
       
    	this(V);
    	
    	UpdateCount sqlOp = new UpdateCount();
    	if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
    	
    	ArrayList<Integer> numbers = new ArrayList<Integer>(); // The lottery for the random edges from 0 to N
    	for(int i = 0; i < V; i++) {
    		numbers.add(i);   
        }
    	Collections.shuffle(numbers);  // shuffle the lottery pool
    	
    	
    	//Initialize the tree st4 to store in/out count
    	for(int i=0;i<V;i++ ){
    	    //int[] count = new int[3];
    	    int[] count = {0, 0, 0};
    		st4.put(i, count);
    		
    	}
    	
    	//create & add edges
    	for (int i = 0; i < V; i++) {            // v starts from vertex id=0
    		
    		int r1 = (int)(Math.random()*7)+2;   //Random number = min 2 ~ Max 8  directed edges from v  
    		//System.out.println("r1 = " + r1 );
    		
    		int r2 = (int)(Math.random()*V)-1;     //Random number r2 to determine the destination w from the Lottery list
    		//System.out.println("original r2 = " + r2 );
    		//System.out.println(); 
    		
    		int v = i;                          
    		for (int i3 = 0; i3 < r1 ; i3++ ){    	
    			
    			
    			r2++;
    			if (r2 >= V) {//System.out.println("r2 >= V");
    			r2 = r2 - V ;   
    			}     			// constantly check if random number r2 out of bound    			
    			
    			int w = numbers.get(r2);                // w is determined from Lottery(r2)
    			//System.out.println("after w is determined r2 = " + r2 );    
    			//System.out.println(">>add destination = " + w + "<<");   
    			//System.out.println(); 
    			
    			
    			if (r2 >= V) {
    			//System.out.println("r2 >= V"); 
    			r2 = r2 - V ;        
    			}       // constantly check if random number r2 out of bound   
    			
    			while (v == w) {
    				int r3 = r2 +1;
    				if (r3 >= V) { 
    				//System.out.println("r3 >= V");
    				r3 = r3 - V ;        
    				} w = numbers.get(r3);  //cannot self-loop
    				 
    			}
    			
    			double weight = (int)(Math.random()*1901)+100;  // weight max = 2000, min=100 
    			                                             //(int)(Math.random()*1901)+100;
				DirectedEdge e = new DirectedEdge(v, w, weight);
				//System.out.println(">>>add edge : "+ v +" -> " + w +"<<<");												
				addEdge(e);
				//System.out.println("===Next Edge=="); 
				
				int[] update1= {st4.get(v)[0]+1,st4.get(v)[1],st4.get(v)[2]};												
				int[] update2= {st4.get(w)[0],st4.get(w)[1]+1,st4.get(v)[2]};
				
				
				st4.put(v, update1);
				st4.put(w, update2);
				//System.out.println(" out count for "+ v +" = " + st4.get(v)[0]);
				//System.out.println(" in count for "+ w +" = " + st4.get(w)[1]);
				
				//SQLOP============
				//sqlOp.updateCount(v,w);
    		}
    		
    		
    		
    		//System.out.println("=======Next Vertex=======");
    	}
    	
    }

    /**
     * Create an edge-weighted digraph from input stream.
     
    public MyGraphMap(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }
   */
   /**
     * Copy constructor.
     */
    public MyGraphMap(MyGraphMap G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
            for (DirectedEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (DirectedEdge e : reverse) {
                adj[v].add(e);
            }
        }
    }

   /**
     * Return the number of vertices in this digraph.
     */
    public int V() {
        return V;
    }

   /**
     * Return the number of edges in this digraph.
     */
    public int E() {
        return E;
    }
    
    //return in/out count 
    public ST st4( ) {
    	
        return  st4;
    }
    
    public int st4in(int id1) {
    	
        return  st4.get(id1)[1];
    }
    public int st4out(int id1) {
    	
        return  st4.get(id1)[0];
    }

   /**
     * Add the directed edge e to this digraph.
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
        E++;
    }


   /**
     * Return the edges incident from vertex v as an Iterable.
     * To iterate over the edges incident from vertex v in digraph G, use foreach notation:
     * <tt>for (DirectedEdge e : G.adj(v))</tt>.
     */
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

   /**
     * Return all edges in this digraph as an Iterable.
     * To iterate over the edges in the digraph, use foreach notation:
     * <tt>for (DirectedEdge e : G.edges())</tt>.
     */
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    } 

   /**
     * Return number of edges incident from v.
     */
    public int outdegree(int v) {
        return adj[v].size();
    }



   /**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
    	/*
        In in = new In(args[0]);
        MyGraphMap G = new MyGraphMap(in);
        StdOut.println(G);
        */
    }

}
