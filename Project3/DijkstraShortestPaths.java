import java.util.Stack;

public class DijkstraShortestPaths {

    private int source;
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraShortestPaths(WeightedDigraph g, int source) {
        this.source = source;

        // construct necessary arrays, priority queues..
        edgeTo = new DirectedEdge[g.numVertices()];
        distTo = new double[g.numVertices()];
        pq = new IndexMinPQ<Double>(g.numVertices());

        // initialize distances to source..
        for (int v = 0; v < g.numVertices(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;

        pq.insert(source, 0.0);

        while (!pq.isEmpty()) {
            int v = pq.delMin();

            for (DirectedEdge e : g.adj(v)) {
                relax(e);
            }
        }
    }

    public void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w]);
            } else {
                pq.insert(w, distTo[w]);
            }
        }
    }

    public int source() {
        return this.source;
    }

    public Stack<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> edges = new Stack<DirectedEdge>();

        int w = v;
        while (w != this.source()) {
            DirectedEdge edge = edgeTo[w];
            edges.push(edge);
            w = edge.from();
        }
        return edges;
    }

    public static String makeEdgeString(DijkstraShortestPaths sp, int s, int t) {
        String ret = "";

        ret += String.format("%d to %d\t(%.3f)\t", s, t, sp.distTo[t]);

        Stopwatch sw_internal = new Stopwatch();
        Stack<DirectedEdge> path = sp.pathTo(t);
        System.out.println(sw_internal.check() + String.format(" milliseconds for shortest path retrieval between vertex %d and %d", s, t));

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
                // "./Rome.txt",
                // "./10000EWD.txt"
        };

        for (String fpath : fpaths) {
            Stopwatch sw = new Stopwatch();
            Stopwatch sw2 = new Stopwatch();
            // Q1 - get shortest paths from vertex 1

            sw.newStart();
            WeightedDigraph g = new WeightedDigraph(fpath);
            System.out.println(g);
            System.out.println(sw.check() + " milliseconds for weighted digraph construction");

            int source = 1;

            sw.newStart();
            DijkstraShortestPaths sp = new DijkstraShortestPaths(g, source);
            System.out.println(sw.check() + " milliseconds for Disjkstra shortest path generation");

            for (int i = 0; i < g.numVertices(); i++) {
                System.out.println(makeEdgeString(sp, source, i) + "\n");
            }

            System.out.println("\n==============================");
            System.out.println(sw2.check() + " ms for processing Q1 for " + fpath);
            System.out.println("==============================\n");

            // Q2 - get all shortest pair paths and weights
            sw2.newStart();
            for (int i = 0; i < g.numVertices(); i++) {
                System.out.println(String.format("Shortest path from vertex:\t%d --------------", i));
                sw.newStart();
                DijkstraShortestPaths c = new DijkstraShortestPaths(g, i);
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