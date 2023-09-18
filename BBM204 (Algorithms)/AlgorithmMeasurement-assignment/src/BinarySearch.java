public class BinarySearch {
    public static  int search(double[] arr,double key) {

        int low = 0;
        int high = arr.length-1;

        while(high - low > 1) {
            int mid = (low + high) / 2;

            //go right
            if(arr[mid] < key) {
                low = mid + 1;
            }
            else {
                high = mid;
            }
        }
        if(arr[low] == key) {
            return low;
        }
        else if (arr[high] == key) {
            return high;
        }

        return -1;
    }
}
