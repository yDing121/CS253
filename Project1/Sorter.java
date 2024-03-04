public class Sorter {
    protected int[] arr;
    protected long inversions;

    public Sorter(int[] arr) {
        // Strong copy of array
        this.arr = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            this.arr[i] = arr[i];
        }

        this.inversions = 0;
    }

    public String toString() {
        String ret = "";

        for (int i : arr) {
            ret += i + " ";
        }

        return ret;
    }

    public long getInversions(){
        return this.inversions;
    }
}
