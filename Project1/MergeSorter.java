public class MergeSorter {
    private int[] arr;
    private long inversions;

    
    public MergeSorter(int[] arr){
        // Strong copy of array
        this.arr = new int[arr.length];

        for (int i=0; i<arr.length; i++){
            this.arr[i] = arr[i];
        }

        inversions = 0;
    }

    public String toString(){
        String ret = "";

        for (int i : arr){
            ret += i + " ";
        }

        return ret;
    }

    public long getInversions(){
        return this.inversions;
    }
 

    public void sort(){
        this.inversions = this.helper(0, arr.length - 1);
    }

    public long helper(int left, int right){
        long inversions = 0;

        if (right > left){
            int mid = (right+left)/2;
            inversions += helper(left, mid) + helper(mid+1, right) + merge(left, mid+1, right);
        }

        return inversions;
    }

    public long merge(int left, int mid, int right){
        // Regular merge with some minor modifications to count the inversions in 
        // each step, then recursively sum
        int i = left, j = mid, k = 0;
        long inversions = 0;
        int[] temp = new int[right - left + 1];


        while ((i < mid) && (j <= right)){
            if (arr[i] <= arr[j]){
            // if (arr[i] < arr[j]){              We can use this equality if desired
                // Merge from left
                temp[k++] = arr[i++];

                // If we are merging from the left then it is in the correct order
            }
            else{
                // Merge from right
                temp[k++] = arr[j++];

                // Need to count inversions here because you are merging from right
                inversions += mid - i;
            }
        }

        while (i < mid){
            // Ran out of right elements
            temp[k++] = arr[i++];
        }

        while (j <= right){
            // Ran out of left elements
            temp[k++] = arr[j++];
        }

        // Copy back to original array
        for (k = 0, i = left; i <= right; k++, i++){
            arr[i] = temp[k];
        }

        return inversions;
    }


}
