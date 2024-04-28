import java.util.LinkedList;
import java.util.Queue;

public class QueueBasedBellmanFord {
    private double[] distTo; // length of path to v
    private DirectedEdge[] edgeTo; // last edge on path to v
    private boolean[] onQ; // Is this vertex on the queue?
    private Queue<Integer> queue; // vertices being relaxed
    private int cost; // number of calls to relax()

 public QueueBasedBellmanFord(WeightedDigraph G, int s)
    {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new LinkedList<Integer>();
        for (int v = 0; v < G.V(); v++)
        distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue.enqueue(s);
        onQ[s] = true;
        while (!queue.isEmpty())
        {
        int v = queue.dequeue();
        onQ[v] = false;
        relax(G, v);
        }
    }

 private void relax(WeightedDigraph G, int v)
 // See page 673.
 public double distTo(int v) // standard client query methods

 public boolean hasPathTo(int v) // for SPT implementatations

 public Iterable<Edge> pathTo(int v) // (See page 649.)

 // See page 677.
}