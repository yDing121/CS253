import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Digraph {
    int n;
    ArrayList<ArrayList<DirectedEdge>> adj;

    public Digraph(String fpath) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(fpath));

        n = scanner.nextInt();
        int nlines = scanner.nextInt();
        adj = new ArrayList<ArrayList<DirectedEdge>>();

        for (int i = 0; i<n; i++){
            adj.add(new ArrayList<DirectedEdge>());
        }


        int from;
        int to;
        double weight;

        for (int i=0; i<nlines; i++){
            // System.out.println(scanner.nextLine());
            from = scanner.nextInt();
            to = scanner.nextInt();
            weight = scanner.nextDouble();

            addEdge(from, to, weight);
        }

        scanner.close();
        System.out.println(String.format("Read %d entries successfully.", n));
    }

    public void addEdge(int a, int b, double w){
        adj.get(a).add(new DirectedEdge(a, b, w));
    }

    // public void hasEdge(int a, int b){
    //     return adj.get(a).contains(adj)
    // }

    public static void main(String[] args) throws FileNotFoundException {
        Digraph g = new Digraph("10000EWD.txt");
    }
}
