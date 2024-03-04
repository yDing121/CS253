public class InsertionSorter extends Sorter{
    public InsertionSorter(int[] arr) {
        super(arr);
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
