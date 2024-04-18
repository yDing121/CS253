import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Digraph {
    int n;
    ArrayList<DirectedEdge>[] adj;

    public Digraph(){

    }

    public Digraph(String fpath) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(fpath));

        n = scanner.nextInt();
        int nlines = scanner.nextInt();

        for (int i = 0; i<n; i++){
            
        }

        adj = new ArrayList<DirectedEdge>()[n];

        for (int i=0; i<nlines; i++){
            System.out.println(scanner.nextLine());
        }

        scanner.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Digraph g = new Digraph("10000EWD.txt");
        
    }
}
