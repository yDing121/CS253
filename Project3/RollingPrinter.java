import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RollingPrinter {
    private String[] data;
    private int n;
    private int roller;

    public RollingPrinter(int n){
        this.data = new String[n];
        this.n = n;
        this.roller = 0;
    }

    public void addData(int i, String input){
        data[i%n] = input;
        roller = (roller + 1)%this.n;
    }

    public void writeData(String fname) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fname));

        // for (String s : data){
        //     writer.write(s + "\n");
        // }

        for (int i=roller; i < roller + this.n; i++){
            writer.write(data[i%this.n] + "\n");
        }

        writer.close();
    }
}
