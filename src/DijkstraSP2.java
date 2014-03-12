/*************************************************************************
 *  UNI:YH2565
 *  Modified by Yi-wei Huang
 *  Support item G - Find n closest city by weight
 *  Compilation:  javac DijkstraSP2.java
 *  
 *  Dependencies: MyGraphMap.java IndexMinPQ.java Stack.java DirectedEdge.java
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *  The code below is modified from the book, Algorithms, 4th Edition,
 *  by Robert Sedgewick and Kevin Wayne.
 *  For more info, see http://algs4.cs.princeton.edu/
 *
 *************************************************************************/

public class DijkstraSP2 {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices
    static  Queue<Integer> queue1 = new Queue<Integer>();

    public DijkstraSP2(MyGraphMap G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
        	
            int v = pq.delMin(); 
            queue1.enqueue(v);  //put the closest vertex into the queue 
            
            //System.out.println("=====GO=======");
            //System.out.println("Extract "+ v + "from pq using delMin");
            
            for (DirectedEdge e : G.adj(v)) {
            	//System.out.println(" - ");
                relax(e);}
        }

        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
       
        
        if (distTo[w] > distTo[v] + e.weight()) {
        	
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
           
            if (pq.contains(w)) {pq.decreaseKey(w, distTo[w]);
          
            }
            else    {            pq.insert(w, distTo[w]);               
            }
            												 //deletemin will extract the min key value which is the shortes distance
        } //else System.out.println("distTo[w] < distTo[v] + e.weight(), other vertex has shorter path to " + w  );
    }

    // length of shortest path from s to v
    public double distTo(int v) {
        return distTo[v];
    }

    // is there a path from s to v?
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // shortest path from s to v as an Iterable, null if no such path
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(MyGraphMap g, int s) {

        // check that edge weights are nonnegative
        for (DirectedEdge e : g.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < g.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < g.V(); v++) {
            for (DirectedEdge e : g.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < g.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }
    public int[] nClosest(int inputN, int StartingID) {
    	//inputN = 5;
        int queueN = inputN + 1 ; 
        int closev = StartingID ;
        int [] resultG = new int[queueN];
        
        if (!queue1.isEmpty()) {
     	      
     	   for (int i=0;i< queueN;i++) {
     		   
     		        closev =queue1.dequeue();
     		        resultG[i] = closev; // the first key is the source itself
     	   			
     	   }		
        } else  System.out.println("run dijkstra first");
        
        return resultG;
       
    }
    public void cleanQueue(){
    	//clean stack1
        //for (int i=0;i<queue1.size();i++) 
         while ( !queue1.isEmpty())
        	{queue1.dequeue();}
     	   //stack1.pop();
        
    	
    }

    public static void main(String[] args) {
       
    }

}
