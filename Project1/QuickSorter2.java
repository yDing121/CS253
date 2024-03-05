public class QuickSorter2 extends Sorter {
    public static void main(String[] args) {
        // int[] thing =
        // Runner.getArray(Runner.readIntArraylistFromFile("dataset_s\\f1.txt"));
        int[] thing = { 9, 1, 20, 6, 4, 5, 17, 9, 3, 6 };
        QuickSorter2 qs2 = new QuickSorter2(thing);
        // qs2.partition(0, thing.length-1);
        qs2.sort();
        System.out.println(qs2);
        System.out.println(qs2.getInversions());
    }

    public QuickSorter2(int[] arr) {
        super(arr);
    }

    public void sort() {
        sort(0, arr.length - 1);
    }

    private void sort(int lo, int hi) {
        if (lo < hi) {
            int pivot_idx = partition(lo, hi);
            sort(lo, pivot_idx - 1);
            sort(pivot_idx + 1, hi);
        }
    }

    private int partition(int lo, int hi) {
        int pivot = arr[lo];

        int next = lo + 1;
        int tracer = lo + 1;

        while (tracer <= hi) {
            // System.out.println(this + "\ttracer:\t" + arr[tracer] + "\tnext:\t" +
            // arr[next] + "\tinversions:\t" + getInversions());
            if (arr[tracer] <= pivot) {
                inversions += tracer - next;
                swap(next, tracer);

                for (int i = next; i <= tracer; i++) {
                    if ((arr[i] == pivot) && (next != tracer)) {
                        // System.out.println("Dup " + pivot);
                        inversions--;
                    }
                }

                next++;
            }
            // if (arr[tracer] == pivot){
            // swap(next, tracer);
            // next++;
            // }
            // if (arr[tracer] == pivot){
            // inversions--;
            // }

            tracer++;
        }

        // System.out.println(this + "\ttracer (out of bounds):\t" + tracer +
        // "\tnext:\t" + arr[next] + "\tinversions:\t" + getInversions());
        for (int i = lo + 1; i <= next - 1; i++) {
            if (arr[i] == pivot) {
                inversions--;
            }
        }

        inversions += (next - 1) - lo;
        swap(lo, next - 1);

        return next - 1;
    }

    private void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
