public class DirectedEdge implements Comparable<DirectedEdge>{

    private int from, to;
    private double weight;
    
    public DirectedEdge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    
    public int from() {
        return from;
    }
    
    public int to() {
        return to;
    }
    
    public double weight() {
        return weight;
    }
    
    public int compareTo(DirectedEdge that) {
        if      (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return +1;
        else                                return  0;
    }
    
    public String toString() {
        return (String.format("%d--%.2f-->%d",from,weight,to));
    }
}