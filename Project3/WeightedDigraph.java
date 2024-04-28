import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class WeightedDigraph {

    private int numVertices;
    private int numDirectedEdges;
    private Bag<DirectedEdge>[] adj;  //<-- list of EDGES instead of vertices
                              //    adj[i] contains all of the edges incident to i
    
    public WeightedDigraph(int numVertices) {
        this.numVertices = numVertices;
        adj = (BagArray<DirectedEdge>[]) new BagArray[numVertices];
        for (int v = 0; v < numVertices; v++) {
            adj[v] = new BagArray<DirectedEdge>();
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        sb.append(numVertices + " vertices, " + numDirectedEdges + " edges " + NEWLINE);
        for (int v = 0; v < numVertices; v++) {
            sb.append(v + ": ");
            for (DirectedEdge w : adj[v]) {
                sb.append(w + "  ");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }
    
    // create graph from a file
    public WeightedDigraph(String pathToFile) {
        File f = new File(pathToFile);
        Scanner scanner;
        try {
            scanner = new Scanner(f);
            
            // read number of vertices
            int numVertices = Integer.parseInt(scanner.nextLine()); 
            initializeEmptyDigraph(numVertices);
            
            int numEdges = Integer.parseInt(scanner.nextLine());
            
            while (numEdges-- > 0) {     // read and add edges
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                double weight = scanner.nextDouble();
                DirectedEdge e = new DirectedEdge(from, to, weight);
                this.addEdge(e);
            }
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void initializeEmptyDigraph(int numVertices) {
        this.numVertices = numVertices;
        adj = (Bag<DirectedEdge>[]) new Bag[numVertices]; // <-- the necessary, ugly cast
        for (int v = 0; v < numVertices; v++) {
            adj[v] = new BagArray<DirectedEdge>();
        }
    }
    
    public void addEdge(DirectedEdge e) {
        int v = e.from(); int w = e.to();
        System.out.println(e);
        adj[v].add(e);     // <-- add edge to adjacency list
        numDirectedEdges++;
    }
    
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
    
    public int numVertices() {  
        return numVertices;
    }
    
    public int numDirectedEdges() {
        return numDirectedEdges;
    }
    
    public void printEdges() {
        for (int v = 0; v < adj.length; v++) {
            for (DirectedEdge e : adj[v]) {
                System.out.println(e.from() + "->" + e.to() + "  " + e.weight());
            }
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        WeightedDigraph g = new WeightedDigraph("/Users/nickyren/Downloads/datasets/tinyEWD.txt");
        g.printEdges();
//        System.out.println(g);
    }
}