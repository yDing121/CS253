import java.io.FileNotFoundException;

public class InsertionSorter extends Sorter {
    public InsertionSorter(int[] arr) {
        super(arr);
    }

    public long sort() {
        long start = System.currentTimeMillis();
        for (int j = 1; j < arr.length; j++) {
            int key = arr[j];
            int i = j - 1;

            while ((i >= 0) && (arr[i] > key)) {
                arr[i + 1] = arr[i];
                i--;
                inversions++;
            }

            arr[i + 1] = key;
        }
        long end = System.currentTimeMillis();

        return end - start;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String[] fpaths = {
                "dataset_s\\f1.txt",
                "dataset_s\\f2.txt",
                "dataset_s\\f3.txt",
                "dataset_s\\f4.txt",
                "dataset_s\\f5.txt",
                "dataset_s\\f6.txt",
        };

        for (String fpath : fpaths) {
            InsertionSorter is = new InsertionSorter(Runner.getArray(Runner.readIntArraylistFromFile(fpath)));

            long duration = is.sort();
            // System.out.println(is);
            System.out.println("Insertion inversions on " + fpath + ":\t" + is.getInversions());
            System.out.println("Insertion time (ms):\t" + duration);
            System.out.println("-------");
        }
    }
}
