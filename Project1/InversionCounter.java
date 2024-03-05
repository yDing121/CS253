import java.util.Arrays;

public class InversionCounter {

    public static int countInversions(int[] arr) {
        int[] temp = Arrays.copyOf(arr, arr.length);
        return quicksortAndCount(arr, temp, 0, arr.length - 1);
    }

    private static int quicksortAndCount(int[] arr, int[] temp, int low, int high) {
        int inversions = 0;

        if (low < high) {
            int pivotIndex = partition(arr, temp, low, high);
            inversions += pivotIndex - low;  // Count inversions in the left subarray
            inversions += quicksortAndCount(arr, temp, pivotIndex + 1, high);  // Count inversions in the right subarray
        }

        return inversions;
    }

    private static int partition(int[] arr, int[] temp, int low, int high) {
        int pivot = arr[low];
        int i = low + 1;

        for (int j = low + 1; j <= high; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }

        swap(arr, low, i - 1);

        return i - 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1, 20, 6, 4, 5};
        int inversions = countInversions(arr);
        System.out.println("Number of inversions: " + inversions);
    }
}
