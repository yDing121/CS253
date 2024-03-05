public class BetterMergeSorter extends Sorter {
    public BetterMergeSorter(int[] arr) {
        super(arr);
    }

    public long sort() {
        long start = System.currentTimeMillis();
        helper(0, arr.length - 1);
        long end = System.currentTimeMillis();
        System.out.println(start);
        System.out.println(end);

        return end - start;
    }

    // public void insertionSort(int lo, int hi) {
    // for (int i = lo; i <= hi; i++) {
    // for (int j = i; j > lo; j--) {
    // if (arr[j - 1] > arr[j]) {
    // int temp = arr[j - 1];
    // arr[j - 1] = arr[j];
    // arr[j] = temp;
    // inversions++;
    // }
    // }
    // }
    // }

    public void insertionSort(int lo, int hi) {
        for (int i = lo + 1; i < hi; ++i) {
            int key = arr[i];
            int j = i - 1;

            // boolean swapped = false;
            while (j > lo && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                // inversions += 1;
                // swapped = true;
            }

            arr[j + 1] = key;

            // if (swapped) {
            // inversions += 1;
            // }
        }
    }

    private void helper(int left, int right) {
        if ((right > left) && (right - left > 10)) {
            int mid = (right + left) / 2;
            helper(left, mid);
            helper(mid + 1, right);
            merge(left, mid + 1, right);
        } else if ((right > left) && (right - left <= 10)) {
            // System.out.println("insertion");
            insertionSort(left, right);
        }
    }

    private void merge(int left, int mid, int right) {
        // Regular merge with some minor modifications to count the inversions in
        // each step, then recursively sum
        int i = left, j = mid, k = 0;
        int[] temp = new int[right - left + 1];

        while ((i < mid) && (j <= right)) {
            if (arr[i] <= arr[j]) {
                // if (arr[i] < arr[j]){ We can use this equality if desired
                // Merge from left
                temp[k++] = arr[i++];

                // If we are merging from the left then it is in the correct order
            } else {
                // Merge from right
                temp[k++] = arr[j++];

                // Need to count inversions here because you are merging from right
                inversions += mid - i;
            }
        }

        while (i < mid) {
            // Ran out of right elements
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            // Ran out of left elements
            temp[k++] = arr[j++];
        }

        // Copy back to original array
        for (k = 0, i = left; i <= right; k++, i++) {
            arr[i] = temp[k];
        }
    }

}
