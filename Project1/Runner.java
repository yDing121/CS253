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
        // MergeSorter ms = new MergeSorter(arr);

        // System.out.println(ms);
        // arr[0] = 0;
        // System.out.println(ms);
        // ms.sort();
        // System.out.println("\nNumber of inversions:\t" + ms.getInversions() + "\n");
        // System.out.println("\nSorted array:\t" + ms + "\n");

        int t = 0;
        for (String fpath : fpaths) {
            // if (fpath.equals("dataset_s\\f4.txt")){
            //     continue;
            // }

            arr = getArray(readIntArraylistFromFile(fpath));

            // Mergesort
            MergeSorter ms = new MergeSorter(arr);
            long duration = ms.sort();
            System.out.println(String.format("Invcount (merge) for %s:\t%d\nDuration (ms):\t%d\n-----", fpath, ms.getInversions(), duration));
            // System.out.println(ms);
            // Insertion sort
            // InsertionSorter is = new InsertionSorter(arr);
            // is.sort();
            // System.out.println(String.format("Invcount (insertion) for %s:\t%d", fpath, is.getInversions()));

            // // Quicksort
            // QuickSorter2 qs = new QuickSorter2(arr);
            // qs.sort();
            // System.out.println(String.format("Invcount (quick) for %s:\t%d", fpath, qs.getInversions()));
            


            //System.out.println(qs);
            
            
            invcounts[t++] = ms.getInversions();
        }
        // System.out.println(
        //     String.format("Naive inversions for f2:\t%d",
        //     naiveInversions(getArray(readIntArraylistFromFile(fpaths[1]))))
        //     );

        // InsertionSorter thing = new InsertionSorter(getArray(readIntArraylistFromFile(fpaths[0])));
        // thing.sort();
        // // System.out.println("Sorting by insertion for f1:\n" + thing);
        // System.out.println("Insertion inversions:\t" + thing.getInversions());

        // int[] firstdata = getArray(readIntArraylistFromFile(fpaths[0]));

        // int[] farray = {9,1,20,6,4,5,17,9,3,6};
        // // MergeSorter thing2 = new MergeSorter(firstdata);
        // MergeSorter thing2 = new MergeSorter(firstdata);
        // long mstime = thing2.sort();
        // // System.out.println("Sorting by merge for f1:\n" + thing2);
        // System.out.println("Merge inversions:\t" + thing2.getInversions());
        // System.out.println(String.format("Mergesort duration:\t%d", mstime));

        // System.out.println("Naive count:\t" + naiveInversions(farray));

        // QuickSorter2 qs2 = new QuickSorter2(firstdata);
        // qs2.sort();
        // System.out.println("Quicksort2 inversions:\t" + qs2.getInversions());

        // BetterMergeSorter bms = new BetterMergeSorter(firstdata);
        // // bms.insertionSort(0,firstdata.length);
        // long bmstime = bms.sort();
        // System.out.println("BMS inversions:\t" + bms.getInversions());
        // System.out.println(String.format("BMS duration:\t%d", bmstime));

        // HeapSorter hs = new HeapSorter(firstdata);
        // System.out.println(hs.getNumOfInversions());
        
        
        // System.out.println(bms);
        // System.out.println(bms);


        // int[] bruh = {7,6,2,5,1,4,0,3,8};
        // System.out.println(naiveInversions(bruh));
        // // QuickSorter qs = new QuickSorter(bruh);

        // // qs.sort();
        // // System.out.println("Inversions by quicksort:\t" + qs.getInversions());

        // QuickSorter qs = new QuickSorter(bruh);
        // qs.sort2();
        // System.out.println(qs);
        // System.out.println(qs.getinvs2());
    }
}