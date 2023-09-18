public class SelectionSort {
    public static void sort(double[] arr) {
        for(int i = 0; i<arr.length-1;i++) {

            int min = i;

            for(int j = i+1; j<arr.length;j++) {
                if(arr[j] < arr[min]) {
                    min = j;
                }
            }
            //swap if min element has changed
            if(min != i) {
                double temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }
}