import java.util.Stack;

public class BellmanFordSP {
    // for floating-point precision issues

    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] onQueue;
    private QueueArray<Integer> queue;

    public BellmanFordSP(WeightedDigraph G, int s) {
        distTo = new double[G.numVertices()];
        edgeTo = new DirectedEdge[G.numVertices()];
        onQueue = new boolean[G.numVertices()];
        for (int v = 0; v < G.numVertices(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // Bellman-Ford algorithm
        queue = new QueueArray<Integer>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }
    }

    private void relax(WeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
        }
    }

    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Stack<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    private boolean check(WeightedDigraph G, int s) {
        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distanceTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.numVertices(); v++) {
            if (v == s)
                continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.numVertices(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] +
        // e.weight()
        for (int w = 0; w < G.numVertices(); w++) {
            if (edgeTo[w] == null)
                continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to())
                return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }

        System.out.println("Satisfies optimality conditions");
        System.out.println();
        return true;
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static String makeEdgeString(BellmanFordSP sp, int s, int t) {
        String ret = "";

        ret += String.format("%d to %d\t(%.3f)\t", s, t, sp.distTo[t]);

        Stopwatch sw_internal = new Stopwatch();
        Stack<DirectedEdge> path = sp.pathTo(t);
        System.out.println(sw_internal.check()
                + String.format(" milliseconds for shortest path retrieval between vertex %d and %d", s, t));

        int psize = path.size();
        DirectedEdge[] correctpath = new DirectedEdge[path.size()];

        for (int i = 0; i < psize; i++) {
            correctpath[i] = path.pop();
        }

        if (psize <= 10) {
            ret += s;
            for (DirectedEdge e : correctpath) {
                ret += "->" + e.to();
            }
        } else {
            int[] pos = { 0, 1, 2, psize - 4, psize - 3, psize - 2 };
            ret += String.format("%d->%d->%d->...->%d->%d->%d",
                    correctpath[pos[0]].to(),
                    correctpath[pos[1]].to(),
                    correctpath[pos[2]].to(),
                    correctpath[pos[3]].to(),
                    correctpath[pos[4]].to(),
                    correctpath[pos[5]].to());
            // System.out.println("Bruh");
        }
        return ret;
    }

    public static void main(String[] args) {
        String[] fpaths = {
                "./tinyEWD.txt",
                "./Rome.txt",
                "./10000EWD.txt"
        };

        
        int source = 0;
        for (String fpath : fpaths) {
            // Q1 - get shortest paths from vertex 1
            Stopwatch sw = new Stopwatch();
            Stopwatch sw2 = new Stopwatch();

            WeightedDigraph g = new WeightedDigraph(fpath);


            BellmanFordSP sp = new BellmanFordSP(g, source);

            for (int v = 0; v < g.numVertices(); v++) {
                if (sp.hasPathTo(v)) {
                    System.out.println(makeEdgeString(sp, source, v));
                }
            }

            System.out.println("\n==============================");
            System.out.println(sw2.check() + " ms for processing Q1 for " + fpath);
            System.out.println("==============================\n");


            // Q2 - get all shortest pair paths and weights
            sw2.newStart();
            for (int i = 0; i < g.numVertices(); i++) {
                System.out.println(String.format("Shortest path from vertex:\t%d --------------", i));
                sw.newStart();
                BellmanFordSP c = new BellmanFordSP(g, i);
                System.out.println(sw.check() + " milliseconds for Disjkstra shortest path calculation");
    
                for (int j = 0; j < g.numVertices(); j++) {
                    System.out.println(makeEdgeString(c, i, j) + "\n");
                }
            }

            System.out.println("\n==============================");
            System.out.println(sw2.check() + " ms for processing Q2 for " + fpath);
            System.out.println("==============================\n");
        }

    }
}
