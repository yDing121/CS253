import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.File;
import java.util.Stack; 

public class Runner {
    final static String FPATH = "cleandata.txt";

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, ArrayList<String>> adjlist = read(FPATH);
        int i = 0;
        for (String k : adjlist.keySet()){
            System.out.println(++i + "\t" + k + adjlist.get(k));
        }

        // topSort(adjlist);
        // HashSet<String> visited = new HashSet<String>();
        // ArrayList<String> post = new ArrayList<String>();
        // dfs("MATH 1591", visited, adjlist, post);
        ArrayList<String> post = topSort(adjlist);
        
        for (i=post.size()-1; i>=0; i--){
            System.out.println(post.get(i));
        }
    }

    public static void dfs(String key, HashSet<String> visited, HashMap<String, ArrayList<String>> alist, ArrayList<String> post){
        visited.add(key);
        for (String s: alist.get(key)){
            if (!visited.contains(s)){
                dfs(s, visited, alist, post);
            }
        }
        
        post.add(key);
    }

    public static ArrayList<String> topSort(HashMap<String, ArrayList<String>> alist)
    {
        HashSet<String> visited = new HashSet<String>();
        // Stack<String> stack = new Stack<String>();
        ArrayList<String> post = new ArrayList<String>();

        while (visited.size() != alist.keySet().size()){
            // Find first unvisited element
            String target = null;
            for (String s: alist.keySet()){
                if (!visited.contains(s)){
                    target = s;
                    break;
                }
            }

            dfs(target, visited, alist, post);


            // if (stack.empty()){
            //     // Find first unvisited element
            //     for (String s: alist.keySet()){
            //         if (!visited.contains(s)){
            //             stack.add(s);
            //             visited.add(s);
            //             break;
            //         }
            //     }

            //     System.out.println(stack);
            // }
            // // stack.pop();
        }
        return post;
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