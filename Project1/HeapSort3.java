import java.io.IOException;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class HeapSort3 {
    public static int getNumOfInversions(int[] A) {
        int N = A.length;
        if (N <= 1) {
            return 0;
        }

        PriorityQueue<Pair> sortList = new PriorityQueue<>();
        int result = 0;

        // Heapsort, O(N*log(N))
        for (int i = 0; i < N; i++) {
            sortList.add(new Pair(A[i], i));
        }

        // Create a sorted list of indexes
        TreeSet<Integer> x = new TreeSet<>();
        while (!sortList.isEmpty()) {
            // O(log(N))
            Pair pair = sortList.poll();
            int v = pair.value;
            int i = pair.index;

            // Find the current minimum's index
            // the index y can represent how many minimums on the left
            int y = x.headSet(i).size();

            // i can represent how many elements on the left
            // i - y can find how many bigger nums on the left
            result += i - y;

            x.add(i);
        }

        return result;
    }

    static class Pair implements Comparable<Pair> {
        int value;
        int index;

        public Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }

        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.value, other.value);
        }
    }

    public static void main(String[] args) throws IOException {
        // BufferedReader reader = new BufferedReader(new FileReader("dataset_s/f2.txt"));
        // String line;
        // int[] arr = new int[100]; // Assuming the size of the array

        // int i = 0;
        // while ((line = reader.readLine()) != null) {
        //     arr[i++] = Integer.parseInt(line);
        // }

        int[] stuff = Runner.getArray(Runner.readIntArraylistFromFile("dataset_s/f1.txt"));

        int result = getNumOfInversions(stuff);
        System.out.println("Number of inversions are " + result);
    }
}
