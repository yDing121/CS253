public class QuickSorter extends Sorter {
    private int[] temp;
    private long invs;

    public long getinvs2() {
        return invs;
    }

    public QuickSorter(int[] arr) {
        super(arr);
        temp = new int[arr.length];
        this.invs = 0;
    }

    public void sort() {
        helper(0, arr.length - 1);
    }

    public void sort2() {
        helper2(0, arr.length - 1);
    }

    private int partition2(int lo, int hi) {
        int pivot = arr[hi];
        int i = (lo - 1); // index of smaller element
        for (int j = lo; j < hi; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j] < pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                invs += j - i + 1;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[hi];
        arr[hi] = temp;
        invs += 1;

        return i + 1;
    }

    void helper2(int lo, int hi) {
        if (lo < hi) {
            /*
             * pi is partitioning index, arr[pi] is
             * now at right place
             */
            int pi = partition2(lo, hi);

            // Recursively sort elements before
            // partition and after partition
            helper2(lo, pi - 1);
            helper2(pi + 1, hi);
        }
    }

    private int partition(int lo, int hi) {
        int p = arr[lo];
        int left = lo;
        int right = hi;

        // Using auxiliary array then copying back
        for (int i = lo + 1; i <= hi; i++) {
            if (arr[i] <= p) {
                temp[left++] = arr[i];
                this.inversions += hi - right + 1;
            } else {
                temp[right--] = arr[i];
            }
        }

        for (int i = lo; i < left; i++) {
            arr[i] = temp[i];
        }

        arr[left] = p;

        for (int i = left + 1; i <= right; i++) {
            arr[i] = temp[right - i + right + 1];
        }

        return left;
    }

    private void helper(int lo, int hi) {
        if (lo < hi) {
            int mid = partition(lo, hi);
            helper(lo, mid - 1);
            helper(mid + 1, hi);
        }
    }

}
