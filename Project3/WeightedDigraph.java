import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WeightedDigraph {

    private int numVertices;
    private int numDirectedEdges;
    private Bag<DirectedEdge>[] adj;

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

    public WeightedDigraph(String pathToFile) throws FileNotFoundException {
        File f = new File(pathToFile);
        Scanner scanner;
        scanner = new Scanner(f);

        int numVertices = Integer.parseInt(scanner.nextLine());
        initializeEmptyDigraph(numVertices);

        int numEdges = Integer.parseInt(scanner.nextLine());

        while (numEdges-- > 0) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            double weight = scanner.nextDouble();
            DirectedEdge e = new DirectedEdge(from, to, weight);
            this.addEdge(e);
        }

        scanner.close();
    }

    private void initializeEmptyDigraph(int numVertices) {
        this.numVertices = numVertices;
        adj = (Bag<DirectedEdge>[]) new Bag[numVertices];
        for (int v = 0; v < numVertices; v++) {
            adj[v] = new BagArray<DirectedEdge>();
        }
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        // System.out.println(e);
        adj[v].add(e);
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

    public static void main(String[] args) throws FileNotFoundException {
        WeightedDigraph g = new WeightedDigraph("/Users/nickyren/Downloads/datasets/tinyEWD.txt");
        g.printEdges();
        // System.out.println(g);
    }
}