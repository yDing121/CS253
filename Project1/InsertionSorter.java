public class InsertionSorter {
    private int[] arr;
    private long inversions;

    public String toString() {
        String ret = "";

        for (int i : arr) {
            ret += i + " ";
        }

        return ret;
    }

    public InsertionSorter(int[] arr) {
        // Strong copy of array
        this.arr = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            this.arr[i] = arr[i];
        }

        inversions = 0;
    }

    public long getInversions(){
        return this.inversions;
    }

    public long sort(){
        long inversions = 0;

        for (int i=0; i<arr.length; i++){
            for (int j=i; j>0; j--){
                if (arr[j-1] > arr[j]){
                    int temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                    inversions += 1;
                }
            }
        }

        this.inversions = inversions;
        return inversions;
    }
}
