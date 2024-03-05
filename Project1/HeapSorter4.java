import java.io.FileNotFoundException;

public class HeapSorter4 extends Sorter {
    public HeapSorter4(int[] arr) {
        super(arr);
    }

    private boolean larger(int a, int b) {
        return arr[a] > arr[b];
    }

    private void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private void heapify(int N, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < N && larger(l, largest))
            largest = l;

        // If right child is larger than largest so far
        if (r < N && larger(r, largest))
            largest = r;

        // If largest is not root
        if (largest != i) {
            swap(i, largest);
            // int swap = arr[i];
            // arr[i] = arr[largest];
            // arr[largest] = swap;
            this.inversions += 1;

            // Recursively heapify the affected sub-tree
            heapify(N, largest);
        }
    }

    private long sort() {
        long start = System.currentTimeMillis();
        int N = arr.length;

        // Build heap (rearrange array)
        for (int i = N / 2 - 1; i >= 0; i--)
            heapify(N, i);

        // One by one extract an element from heap
        for (int i = N - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(i, 0);
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    // private void helper(int last){
    // if (last <= 1){
    // return;
    // }

    // swap(1, last);
    // sink(1);
    // }

    // private void heapify(){
    // for (int i=1; i<=end; i++){
    // swim(i);
    // }
    // }

    // private void swim(int cur){
    // while (cur > 1){
    // if (larger(cur, cur/2)){
    // swap(cur, cur/2);
    // // inversions += cur - cur/2;
    // cur /= 2;
    // }
    // else{
    // break;
    // }
    // }
    // }

    // private void sink(int cur){
    // while (cur*2 <= end){
    // if (cur*2 + 1 <= end){
    // if (larger(cur*2 + 1, cur*2)){
    // // Right greater than left
    // swap(cur*2 + 1, cur);
    // cur = cur*2 + 1;
    // }
    // else{
    // // Left greater than right
    // swap(cur*2, cur);
    // cur = cur*2;
    // }
    // }
    // else if (cur*2 <= end){
    // if (larger(cur*2, cur)){
    // swap(cur, cur*2);
    // cur = cur*2;
    // }
    // }
    // else{
    // break;
    // }
    // }
    // }

    public static void main(String[] args) throws FileNotFoundException {
        int arr[] = { 12, 11, 13, 5, 6, 7 };
        int N = arr.length;

        // Function call
        HeapSorter4 ob = new HeapSorter4(arr);
        ob.sort();

        System.out.println("Sorted array is:\n" + ob);

        String[] fpaths = {
                "dataset_s\\f1.txt",
                // "dataset_s\\f2.txt",
                // "dataset_s\\f3.txt",
                // "dataset_s\\f4.txt",
                // "dataset_s\\f5.txt",
                // "dataset_s\\f6.txt",
        };

        for (String fpath : fpaths) {
            HeapSorter4 hs = new HeapSorter4(Runner.getArray(Runner.readIntArraylistFromFile(fpath)));

            long duration = hs.sort();
            // System.out.println(is);
            System.out.println("Heapsort inversions on " + fpath + ":\t" + hs.getInversions());
            System.out.println("Heapsort time (ms):\t" + duration);
            System.out.println(hs);
            System.out.println("-------");
        }
    }
}
