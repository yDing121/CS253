import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class HeapSorter {
    ArrayList<Integer> arr;

    public HeapSorter(int[] arr) {
        this.arr = new ArrayList<>();

        for (int i : arr) {
            this.arr.add(i);
        }
    }

    public String toString() {
        return this.arr.toString();
    }

    public int getNumOfInversions() {
        int N = this.arr.size();
        if (N <= 1) {
            return 0;
        }

        PriorityQueue<int[]> sortList = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int result = 0;

        // Heapsort, O(N*log(N))
        for (int i = 0; i < N; i++) {
            sortList.add(new int[] { this.arr.get(i), i });
        }

        // Create a sorted list of indexes
        ArrayList<Integer> x = new ArrayList<>();
        while (!sortList.isEmpty()) {
            // O(log(N))
            int[] v = sortList.poll();

            // Find the current minimum's index
            // the index y can represent how many minimums
            // on the left
            int y = x.size()
                    - x.subList(0, x.size()).indexOf(v[1]) - 1;
            int z = 0;
            if (!x.isEmpty()) {
                z = binarySearch(x, 0, x.size() - 1, v[1]);
                if (z < 0) {
                    z = -(z + 1);
                }
            }

            // i can represent how many elements on the left
            // i - y can find how many bigger nums on the
            // left
            result += v[1] - z;

            x.add(v[1]);
            x.sort(null);
        }

        return result;
    }

    private static int binarySearch(ArrayList<Integer> tarr, int start, int end, int key) {
        while (start <= end) {
            int mid = (start + end) / 2;
            if (tarr.get(mid) == key) {
                return mid;
            } else if (tarr.get(mid) > key) {
                // Look left
                end = mid - 1;
            } else {
                // Look right
                start = mid + 1;
            }
        }
        return -(start + 1);
    }
}
