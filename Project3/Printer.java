import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    private String[] data;
    
    public Printer(int n){
        this.data = new String[n];
    }

    public void addData(int i, String input){
        data[i] = input;
    }

    public void writeData(String fname) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fname));

        for (String s : this.data){
            writer.write(s + "\n");
        }

        writer.close();
    }
}
