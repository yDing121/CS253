import java.io.IOException;
import java.util.Stack;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class DijkstraShortestPaths {

    private int source;
    private DirectedEdge[] edgeTo;
    double[] distTo;
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
        // System.out.println(sw_internal.check() + String.format(" milliseconds for
        // shortest path retrieval between vertex %d and %d", s, t));

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
            int[] pos = { 0, 1, 2, psize - 3, psize - 2, psize - 1 };
            ret += String.format("%d->%d->%d->...->%d->%d->%d",
                    correctpath[pos[0]].from(),
                    correctpath[pos[1]].from(),
                    correctpath[pos[2]].from(),
                    correctpath[pos[3]].to(),
                    correctpath[pos[4]].to(),
                    correctpath[pos[5]].to());
            // System.out.println("nicky is too smart");
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        String[] fpaths = {
                "./tinyEWD.txt",
                "./Rome.txt",
                "./10000EWD.txt"
        };

        for (String fpath : fpaths) {
            String sname = fpath.substring(2, fpath.length() - 4);
            Stopwatch sw = new Stopwatch();
            Stopwatch sw2 = new Stopwatch();
            // Q1 - get shortest paths from vertex 1

            BufferedWriter writerq1 = new BufferedWriter(
                    new FileWriter(String.format("dijkstra_vertex1_%s_output.txt", sname)));
            sw.newStart();
            WeightedDigraph g = new WeightedDigraph(fpath);
            System.out.println(g);
            System.out.println(sw.check() + " milliseconds for weighted digraph construction");

            int source = 1;

            sw.newStart();
            DijkstraShortestPaths sp = new DijkstraShortestPaths(g, source);
            System.out.println(sw.check() + " milliseconds for Disjkstra shortest path generation");

            for (int i = 0; i < g.numVertices(); i++) {
                String lineq1 = makeEdgeString(sp, source, i) + "\n";
                writerq1.write(lineq1);
            }

            writerq1.close();

            System.out.println("\n==============================");
            System.out.println(sw2.check() + " ms for processing Q1 for " + fpath);
            System.out.println("==============================\n");

            // Q2 - get all shortest pair paths and weights
            sw2.newStart();

            RollingPrinter printer_last500 = new RollingPrinter(500);
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(String.format("dijkstra_pairs_%s_output_full.txt", sname)));
            BufferedWriter writer_first500 = new BufferedWriter(
                    new FileWriter(String.format("dijkstra_pairs_%s_output_first_500.txt", sname)));

            for (int i = 0; i < g.numVertices(); i++) {
                System.out.println(String.format("Shortest path from vertex:\t%d --------------", i));
                sw.newStart();
                DijkstraShortestPaths c = new DijkstraShortestPaths(g, i);
                // System.out.println(sw.check() + " milliseconds for Disjkstra shortest path
                // calculation");

                if (fpath.equals("./tinyEWD.txt")) {
                    // System.out.println("FULLLLLL");
                    // Full output
                    for (int j = 0; j < g.numVertices(); j++) {
                        String line = makeEdgeString(c, i, j) + "\n";
                        // System.out.println(line);
                        writer.write(line);
                    }
                } else {
                    // First and last 500 lines

                    for (int j = 0; j < g.numVertices(); j++) {
                        String line = makeEdgeString(c, i, j);
                        // System.out.println(line);
                        printer_last500.addData(i * g.numVertices() + j , line);

                        if (i * g.numVertices() + j < 500) {
                            writer_first500.write(line + "\n");
                        }
                    }
                }
            }

            printer_last500.writeData(String.format("dijkstra_pairs_%s_output_last_500.txt", sname));

            System.out.println("\n==============================");
            System.out.println(sw2.check() + " ms for processing Q2 for " + fpath);
            System.out.println("==============================\n");

            writer.close();
            writer_first500.close();
        }

    }

}