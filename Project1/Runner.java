import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Runner {
    public static int[] getArray(ArrayList<Integer> int_alist) {
        int[] arr = new int[int_alist.size()];

        for (int i = 0; i < int_alist.size(); i++) {
            arr[i] = int_alist.get(i);
        }

        return arr;
    }

    public static ArrayList<Integer> readIntArraylistFromFile(String fpath) throws FileNotFoundException {
        File f = new File(fpath);
        Scanner scanner = new Scanner(f);
        if (!f.exists()) {
            System.out.println("File does not exist");
            scanner.close();
            throw new FileNotFoundException();
        }

        ArrayList<Integer> arr = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            arr.add(scanner.nextInt());
        }

        scanner.close();
        return arr;
    }

    public static long naiveInversions(int[] arr){
        long inversions = 0;
        for (int i = 0; i < arr.length - 1; i++){
            for (int j = i+1; j < arr.length; j++){
                if (arr[j] < arr[i]) inversions++;
            }
        }

        return inversions;
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

        int[] arr = getArray(readIntArraylistFromFile(fpaths[0]));
        long[] invcounts = new long[6];

        // MergeSorter ms = new MergeSorter(new int[] {2,1,3,4});
        MergeSorter ms = new MergeSorter(arr);
        // System.out.println(ms);
        // arr[0] = 0;
        // System.out.println(ms);
        ms.sort();
        // System.out.println("\nNumber of inversions:\t" + ms.getInversions() + "\n");
        // System.out.println("\nSorted array:\t" + ms + "\n");

        int t = 0;
        for (String fpath : fpaths) {
            arr = getArray(readIntArraylistFromFile(fpath));
            ms = new MergeSorter(arr);
            ms.sort();
            System.out.println(String.format("Invcount for %s:\t%d", fpath, ms.getInversions()));
            invcounts[t++] = ms.getInversions();
        }


        System.out.println(
            String.format("Naive inversions for f2:\t%d",
            naiveInversions(getArray(readIntArraylistFromFile(fpaths[1]))))
            );
    }
}