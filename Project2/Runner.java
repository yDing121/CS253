import java.util.Scanner;
import javax.management.RuntimeErrorException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.File;

public class Runner {
    final static String FPATH = "cleandata.txt";

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, ArrayList<String>> adjlist = read(FPATH);
        HashMap<String, ArrayList<String>> revlist = reverseAdj(adjlist);
        int i = 0;
        for (String k : adjlist.keySet()){
            System.out.println(++i + "\t" + k + "\t" + adjlist.get(k));
        }

        System.out.println("-----Reverse Adjacency List-----");
        i = 0;

        for (String k : revlist.keySet()){
            System.out.println(++i + "\t" + k + "\t" + revlist.get(k));
        }

        System.out.println("----------");

        ArrayList<String> post = topSort(adjlist);
        
        for (i=post.size()-1; i>=0; i--){
            System.out.println(post.get(i));
        }

        System.out.println("-----Get Prereqs-----");
        System.out.println(getPrereqs("CSCI 4490", revlist));
        // System.out.println();
        prereqRunner(revlist);
    }

    public static ArrayList<String> getPrereqs(String course, HashMap<String, ArrayList<String>> revlist){
        HashSet<String> visited = new HashSet<String>();
        ArrayList<String> post = new ArrayList<String>();

        dfs(course, visited, revlist, post);
        return post;
    }

    public static void prereqRunner(HashMap<String, ArrayList<String>> revlist){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please input a course name and number e.g. CSCI 2440\t");
        String query = scanner.nextLine().strip();
        System.out.println();

        if (!revlist.containsKey(query)){
            scanner.close();
            throw new RuntimeErrorException(null, "Course requested is not part of catalog");
        }

        for (String s: getPrereqs(query, revlist)){
            System.out.println(s);
        }
        scanner.close();
    }

    public static void dfs(String key, HashSet<String> visited, HashMap<String, ArrayList<String>> alist, ArrayList<String> post){
        visited.add(key);

        if (alist.get(key) == null){
            post.add(key);
            return;
        }

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
        ArrayList<String> post = new ArrayList<String>();

        while (visited.size() != alist.keySet().size()){
            String target = null;
            for (String s: alist.keySet()){
                if (!visited.contains(s)){
                    target = s;
                    break;
                }
            }
            dfs(target, visited, alist, post);
        }
        return post;
    }

    public static HashMap<String, ArrayList<String>> reverseAdj(HashMap<String, ArrayList<String>> alist){
        HashMap<String, ArrayList<String>> revlist = new HashMap<String, ArrayList<String>>();

        for (String s: alist.keySet()){
            for (String key: alist.get(s)){
                if (!revlist.containsKey(key)){
                    revlist.put(key, new ArrayList<String>());
                }
                revlist.get(key).add(s);
            }
        }

        return revlist;
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
                // System.out.println(course + "\n" + prereqs + "\n-------------\n");

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