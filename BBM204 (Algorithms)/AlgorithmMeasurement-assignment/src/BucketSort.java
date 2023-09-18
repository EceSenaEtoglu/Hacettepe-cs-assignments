import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BucketSort {

    public static double[] sort(double[] array) {

        //take the floor of the result
        int numOfBuckets = (int)Math.sqrt(array.length);

        double max = findMax(array);

        //2d list to store buckets
        ArrayList<ArrayList<Double>> buckets =new ArrayList<>();
        for(int i = 0; i<numOfBuckets;i++) {
            buckets.add(new ArrayList<>());
        }

        //map every val in array to a bucket by a hash func.
        for(double val:array) {
            int id = hash(val,max,numOfBuckets);
            buckets.get(id).add(val);
        }

        //sort every bucket
        //combine all elements in sorted buckets to a final array
        //do above operations together in a loop to avoid extra traversal of combining
        double[] sortedArr = new double[array.length];
        int counter = 0;

        for(ArrayList<Double> bucket:buckets) {
            Collections.sort(bucket);
            for (Double val:bucket) {
                sortedArr[counter] = val;
                counter++;
            }
        }

        return sortedArr;
    }

    private static double findMax(double[] arr) {

        double tempMax = arr[0];

        for(int i = 1; i<arr.length;i++) {
            if(arr[i] > tempMax) {
                tempMax = arr[i];
            }
        }

        return tempMax;
    }

    private static int hash(double i,double max,int numOfBuckets) {

        //takes the floor of the result by casting to int
        return (int)(i/max * (numOfBuckets-1));
    }

    // return true if bucketsort algorithm is correct by comparing with Arrays.sort on testData
    public static boolean isValid(double[] testData) {

        double[] copy = new double[testData.length];

        System.arraycopy(testData, 0, copy,0, testData.length);

        Arrays.sort(copy);
        testData = BucketSort.sort(testData);

        for(int i = 0; i<copy.length;i++) {

            if(copy[i] != testData[i]) {
                return false;
            }
        }
        return true;
    }
}
