public class DirectedEdge implements Comparable<DirectedEdge>{
    int a;
    int b;
    float w;


    public DirectedEdge(int a, int b, float weight){
        this.a = a;
        this.b = b;
        this.w = weight;
    }

    public String from(){
        return "" + this.a;
    }

    public String to(){
        return "" + this.b;
    }

    public float weight(){
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
