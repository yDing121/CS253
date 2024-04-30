import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class BellmanFordSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] onQueue;
    private QueueArray<Integer> queue;

    public BellmanFordSP(WeightedDigraph G, int s) {
        distTo = new double[G.numVertices()];
        edgeTo = new DirectedEdge[G.numVertices()];
        onQueue = new boolean[G.numVertices()];

        // Init distance list
        for (int v = 0; v < G.numVertices(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0;

        // Queue
        queue = new QueueArray<Integer>();
        queue.enqueue(s);
        onQueue[s] = true;

        // Solve
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }
    }

    private void relax(WeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();

            // Exists better path from v->w than whatever was stored before
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;

                // Throw new vertex onto queue
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Stack<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))
            return null;

        // Union find-esque path trace
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public static String makeEdgeString(BellmanFordSP sp, int s, int t) {
        String ret = "";

        ret += String.format("%d to %d\t(%.3f)\t", s, t, sp.distTo[t]);

        Stopwatch sw_internal = new Stopwatch();
        Stack<DirectedEdge> path = sp.pathTo(t);
        // System.out.println(sw_internal.check()
        //         + String.format(" milliseconds for shortest path retrieval between vertex %d and %d", s, t));

        // Invert path trace to get the correct order
        int psize = path.size();
        DirectedEdge[] correctpath = new DirectedEdge[path.size()];
        for (int i = 0; i < psize; i++) {
            correctpath[i] = path.pop();
        }

        // // Less than 10 steps - display all edges
        // if (psize <= 10) {
        // ret += s;
        // for (DirectedEdge e : correctpath) {
        // ret += "->" + e.to();
        // }
        // } else {
        // // More than 10 steps - display first 3 and last 3 edges
        // int[] pos = { 0, 1, 2, psize - 4, psize - 3, psize - 2 };
        // ret += String.format("%d->%d->%d->...->%d->%d->%d",
        // correctpath[pos[0]].to(),
        // correctpath[pos[1]].to(),
        // correctpath[pos[2]].to(),
        // correctpath[pos[3]].to(),
        // correctpath[pos[4]].to(),
        // correctpath[pos[5]].to());
        // // System.out.println("Bruh");
        // }
        // return ret;

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

        int source = 1;
        for (String fpath : fpaths) {
            // Q1 - get shortest paths from vertex 1
            String sname = fpath.substring(2, fpath.length() - 4);
            WeightedDigraph g;
            BellmanFordSP sp;
            Stopwatch sw = new Stopwatch();
            Stopwatch sw2 = new Stopwatch();

            //////// Sep
            g = new WeightedDigraph(fpath);
            sp = new BellmanFordSP(g, source);

            BufferedWriter writerq1 = new BufferedWriter(
                    new FileWriter(String.format("bellman_vertex1_%s_output.txt", sname)));

            for (int v = 0; v < g.numVertices(); v++) {
                if (sp.hasPathTo(v)) {
                    String lineq1 = makeEdgeString(sp, source, v) + "\n";
                    writerq1.write(lineq1);
                }
            }
            writerq1.close();

            System.out.println("\n==============================");
            System.out.println(sw2.check() + " ms for processing Q1 for " + fpath);
            System.out.println("==============================\n");

            // Q2 - get all shortest pair paths and weights
            
            sw2.newStart();
            g = new WeightedDigraph(fpath);
            RollingPrinter printer_last500 = new RollingPrinter(500);
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(String.format("bellman_pairs_%s_output_full.txt", sname)));
            BufferedWriter writer_first500 = new BufferedWriter(
                    new FileWriter(String.format("bellman_pairs_%s_output_first_500.txt", sname)));
            
            
            for (int i = 0; i < g.numVertices(); i++) {
                // System.out.println(String.format("Shortest path from vertex:\t%d--------------", i));

                sw.newStart();
                sp = new BellmanFordSP(g, i);
                // System.out.println(sw.check() + " milliseconds for Bellman Ford shortest path calculation");

                if (fpath.equals("./tinyEWD.txt")){
                    // Full output for q2
                    for (int j = 0; j < g.numVertices(); j++) {
                        String line = makeEdgeString(sp, i, j) + "\n";
                        writer.write(line);
                        // System.out.println(makeEdgeString(c, i, j) + "\n");
                    }
                }
                else{
                    // First and last 500 output for q2
                    for (int j = 0; j < g.numVertices(); j++) {
                        String line = makeEdgeString(sp, i, j);
                        printer_last500.addData(i * g.numVertices() + j , line);

                        if (i * g.numVertices() + j < 500) {
                            writer_first500.write(line + "\n");
                        }
                        // System.out.println(makeEdgeString(c, i, j) + "\n");
                    }
                }


            }

            printer_last500.writeData(String.format("bellman_pairs_%s_output_last_500.txt", sname));

            System.out.println("\n==============================");
            System.out.println(sw2.check() + " ms for processing Q2 for " + fpath);
            System.out.println("==============================\n");

            writer.close();
            writer_first500.close();
        }

    }
}
