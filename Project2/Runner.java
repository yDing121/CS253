import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class Runner {
    final static String FPATH = "cleandata.txt";

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, ArrayList<String>> adjlist = read(FPATH);
        int i = 0;
        for (String k : adjlist.keySet()){
            System.out.println(++i + "\t" + k + adjlist.get(k));
        }
    }

    public static String[] topSort(HashMap alist){
        
    }

    public static HashMap<String, ArrayList<String>> read(String fpath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fpath));
        HashMap<String, ArrayList<String>> data = new HashMap();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            int pos = line.indexOf("(");
            String course = line.substring(0, 9).strip();

            if (!data.containsKey(course)){
                data.put(course, new ArrayList<String>());
            }

            if (pos >= 0) {
                String prereqs = line.substring(pos + 1, line.length() - 1);
                String[] plist = prereqs.split(",");
                System.out.println(course + "\n" + prereqs + "\n-------------\n");

                for (String prereq : plist){
                    prereq = prereq.strip();
                    if (!data.containsKey(prereq)){
                        data.put(prereq, new ArrayList<String>());
                    }
                    data.get(prereq).add(course);
                }
            }
        }
        return data;
    }
}