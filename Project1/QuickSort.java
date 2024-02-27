package Project1;
import java.io.File;
import java.util.Scanner;


public class QuickSort{
    public static int[] readData(String fname){
        Scanner scanner = new Scanner(new File("dataset_s\\" + fname));

        while (scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }

        return new int[5];
    }
    
    public static void main(String[] args) {
        System.out.println("hi");
        readData("f1.txt");
    }
}

