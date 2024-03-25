import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.io.File;

public class Runner {
    final static String FPATH = "cleandata.txt";

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, ArrayList<String>> adjlist = read(FPATH);
        HashMap<String, ArrayList<String>> revlist = reverseAdj(adjlist);
        // int i = 0;
        // for (String k : adjlist.keySet()){
        // System.out.println(++i + "\t" + k + "\t" + adjlist.get(k));
        // }

        System.out.println("-----Reverse Adjacency List-----");
        int i = 0;

        for (String k : revlist.keySet()) {
            System.out.println(++i + "\t" + k + "\t" + revlist.get(k));
        }

        System.out.println("----------");

        ArrayList<String> post = topSort(adjlist);

        for (i = post.size() - 1; i >= 0; i--) {
            System.out.println(post.get(i));
        }

        System.out.println("-----Get Prereqs-----");
        System.out.println(getPrereqs("CSCI 4490", revlist));
        // System.out.println();
        prereqRunner(revlist);

        System.out.println("Question 3 -------------------------------");

        // ask users how many courses to take per semester

        ClassSelector(revlist, adjlist);
    }

    public static void ClassSelector(HashMap<String, ArrayList<String>> revlist,
            HashMap<String, ArrayList<String>> adjlist) {

        // Logic: 1. make a sorted math list according to its prereq (DFS)
        // 2. find the most frequent prereq CS class EX: CSCI 2320
        // 3. then take the prereqes of that class (DFS) EX: CSCI 1480 and CSCI 1470,
        // along adding with math

        // 4. Take all courses that uses the class as a prereq All courses that only
        // requires CSCI 2320
        // 5. Finish the rest unvisited courses with DFS

        // first 3 semesters, must only have 2 courses until math 2330 is taken

        // a hashmap to store all courses from regular list
        Map<String, Boolean> selected = new HashMap<>();

        for (String key : adjlist.keySet()) {
            selected.put(key, false);
        }

        // test of selected map ( to see if I have 31 courses )
        // int i = 1;
        // for (Map.Entry<String, Boolean> entry : selected.entrySet())
        // {
        // System.out.println(i++ + entry.getKey() + ": " + entry.getValue());
        // }

        // find whats the most frequent preq and store to hashmap

        Map<String, Integer> keyFrequency = new HashMap<>();
        for (ArrayList<String> values : revlist.values()) {
            for (String key : values) {
                int frequency = keyFrequency.getOrDefault(key, 0) + 1;
                keyFrequency.put(key, frequency);
            }
        }

        String mostFrequentPrerequisite = "";
        int maxFrequency = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : keyFrequency.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequentPrerequisite = entry.getKey();
            }
        }
        // System.out.println(mostFrequentPrerequisite);

        // test of frequency table

        // System.out.println("Frequency of Prerequisites:");
        // for (Map.Entry<String, Integer> entry : keyFrequency.entrySet())
        // {
        // System.out.println(entry.getKey() + ": " + entry.getValue());
        // }

        // Sorting starts

        ArrayList<String> Clist = new ArrayList<>(); // my sorted list of CSCI and can break by number of semesters
        ArrayList<String> Mlist = new ArrayList<>(); // sorted list of Math
        ArrayList<String> Flist = new ArrayList<>(); // Combined list

        // step 1,2,3

        for (String s : getPrereqs(mostFrequentPrerequisite, revlist)) {
            Clist.add(s);

            selected.put(s, true);
        }

        for (String s : getPrereqs("MATH 3320", revlist)) {

            if (!selected.getOrDefault(s, false)) { // Check if s is not already selected
                Mlist.add(s);
                selected.put(s, true);
            }
        }

        // step 4

        for (Map.Entry<String, ArrayList<String>> entry : revlist.entrySet()) {
            String course = entry.getKey();

            ArrayList<String> prerequisites = entry.getValue();

            // Check if the course has only CSCI 2320 as a prerequisite
            if (prerequisites.size() == 1 && prerequisites.contains(mostFrequentPrerequisite)) {
                // Add the course to Clist and mark it as selected
                Clist.add(course);
                selected.put(course, true);
            }

        }

        // combining two lists

        int size = Math.min(Clist.size(), Mlist.size());

        // Iterate over both lists simultaneously and add elements to the combined list
        for (int i = 0; i < size; i++) {
            Flist.add(Clist.get(i));
            Flist.add(Mlist.get(i));
        }

        // Add remaining elements from the longer list
        if (Mlist.size() > size) {
            Flist.addAll(Mlist.subList(size, Mlist.size()));
        } else if (Clist.size() > size) {
            Flist.addAll(Clist.subList(size, Clist.size()));
        }

        // step 5 rest with DFS
        for (Map.Entry<String, ArrayList<String>> entry : adjlist.entrySet()) {
            String course = entry.getKey();
            ArrayList<String> prerequisites = entry.getValue();

            // Check if the course has not been selected yet
            if (!selected.getOrDefault(course, false)) {
                boolean prerequisitesMet = true;

                // Check if all prerequisites are fulfilled
                for (String prereq : prerequisites) {
                    // If any prerequisite is not selected, mark prerequisitesMet as false
                    if (!selected.getOrDefault(prereq, false)) {
                        prerequisitesMet = false;
                        break;
                    }
                }

                // If all prerequisites are fulfilled, add the course to Flist
                if (prerequisitesMet) {
                    Flist.add(course);
                    selected.put(course, true);
                } else {
                    ArrayList<String> coursePrereqs = getPrereqs(course, revlist);
                    // Add prerequisites to Flist only if they are not already selected
                    for (String prereq : coursePrereqs) {
                        if (!selected.getOrDefault(prereq, false)) {
                            Flist.add(prereq);
                            selected.put(prereq, true);
                        }
                    }
                    // Add the course itself
                    Flist.add(course);
                    selected.put(course, true);
                }
            }
        }

        // Remove duplicates
        Set<String> set = new LinkedHashSet<>(Flist); // Using LinkedHashSet to maintain order
        Flist.clear();
        Flist.addAll(set);

        // print list

        // System.out.println("Contents of Clist:");
        // for (String course : Clist) {
        // System.out.println(course);
        // }

        // System.out.println("Contents of Mlist:");
        // for (String course : Mlist) {
        // System.out.println(course);
        // }

        System.out.println("Contents of Flist:");
        int label = 1;
        for (String course : Flist) {

            System.out.println(label++ + " " + course);
        }

        // ?? somehow wouldnt work?
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Please input a number, for the number of classes a
        // student may take per semester");
        // int n = scanner.nextInt();

        // make decision based on input now
        displayStudyPlan(Flist, 3);

    }

    public static void displayStudyPlan(ArrayList<String> courses, int n) {
        if (n == 1) {
            for (int i = 0; i < courses.size(); i++) {
                System.out.println("Semester " + (i + 1));
                System.out.println(courses.get(i));
                System.out.println("-----------------");

            }
        } else if (n >= 2) {
            // Handle first three semesters
            for (int semester = 1; semester <= 3; semester++) {
                System.out.println("Semester " + semester);
                for (int i = (semester - 1) * 2; i < semester * 2; i++) {
                    if (i < courses.size()) {
                        System.out.println(courses.get(i));
                    }
                }
                System.out.println("-----------------");
            }

            // Handle remaining semesters
            int remainingCourses = courses.size() - 6; // Subtract the first three semesters
            int remainingSemesters = (int) Math.ceil((double) remainingCourses / n);

            for (int semester = 4; semester < 4 + remainingSemesters; semester++) {
                System.out.println("Semester " + semester);
                int startIndex = 6 + (semester - 4) * n;
                int endIndex = Math.min(startIndex + n, courses.size());

                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println(courses.get(i));
                }

                System.out.println("-----------------");
            }
        } else {
            System.out.println("Please enter valid number");
        }

    }

    public static ArrayList<String> getPrereqs(String course, HashMap<String, ArrayList<String>> revlist) {
        HashSet<String> visited = new HashSet<String>();
        ArrayList<String> post = new ArrayList<String>();

        dfs(course, visited, revlist, post);
        return post;
    }

    public static void prereqRunner(HashMap<String, ArrayList<String>> revlist) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input a course name and number e.g. CSCI 2440");
        String query = scanner.nextLine().strip();

        for (String s : getPrereqs(query, revlist)) {
            System.out.println(s);
        }
        scanner.close();
    }

    public static void dfs(String key, HashSet<String> visited, HashMap<String, ArrayList<String>> alist,
            ArrayList<String> post) {
        visited.add(key);

        if (alist.get(key) == null) {
            post.add(key);
            return;
        }

        for (String s : alist.get(key)) {
            if (!visited.contains(s)) {
                dfs(s, visited, alist, post);
            }
        }

        post.add(key);
    }

    public static ArrayList<String> topSort(HashMap<String, ArrayList<String>> alist) {
        HashSet<String> visited = new HashSet<String>();
        ArrayList<String> post = new ArrayList<String>();

        while (visited.size() != alist.keySet().size()) {
            String target = null;
            for (String s : alist.keySet()) {
                if (!visited.contains(s)) {
                    target = s;
                    break;
                }
            }
            dfs(target, visited, alist, post);
        }
        return post;
    }

    public static HashMap<String, ArrayList<String>> reverseAdj(HashMap<String, ArrayList<String>> alist) {
        HashMap<String, ArrayList<String>> revlist = new HashMap<String, ArrayList<String>>();

        for (String s : alist.keySet()) {
            for (String key : alist.get(s)) {
                if (!revlist.containsKey(key)) {
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

            if (!data.containsKey(course)) {
                data.put(course, new ArrayList<String>());
            }

            if (pos >= 0) {
                String prereqs = line.substring(pos + 1, line.length() - 1);
                String[] plist = prereqs.split(",");
                // System.out.println(course + "\n" + prereqs + "\n-------------\n");

                for (String prereq : plist) {
                    prereq = prereq.strip();
                    if (!data.containsKey(prereq)) {
                        data.put(prereq, new ArrayList<String>());
                    }
                    data.get(prereq).add(course);
                }
            }
        }
        return data;
    }
}