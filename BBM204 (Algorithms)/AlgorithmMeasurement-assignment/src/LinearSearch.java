public class LinearSearch {
    public static  int search(double[] arr, double key) {
        for(int i = 0; i<arr.length;i++) {
            if(arr[i] == key) {
                return i;
            }
        }
        return -1;
    }
}
