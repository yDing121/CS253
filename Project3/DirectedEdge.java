public class DirectedEdge implements Comparable<DirectedEdge>{
    int a;
    int b;
    double w;

    
    public DirectedEdge(int a, int b, double weight){
        this.a = a;
        this.b = b;
        this.w = weight;
    }

    public int from(){
        return this.a;
    }

    public int to(){
        return this.b;
    }

    public double weight(){
        return this.w;
    }

    public int compareTo(DirectedEdge e2){
        if (this.w > e2.weight()){
            return 1;
        }
        else if (this.w < e2.weight()){
            return -1;
        }
        else{
            return 0;
        }
    }
}
