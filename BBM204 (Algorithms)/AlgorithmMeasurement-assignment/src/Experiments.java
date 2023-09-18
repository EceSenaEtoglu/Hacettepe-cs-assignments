import java.util.HashMap;
import java.util.Random;

public class Experiments {

    private static final int loopTimeForSorts = 10;
    private static final int loopTimeForSearchs = 1000;

    //0:selection sort, 1:quicksort, 2:bucketsort
    public static double[][] sortingRandomDataRunningTimes = new double[3][10];
    public static double[][] sortingAscendingDataRunningTimes = new double[3][10];
    public static double[][] sortingDescendingDataRunningTimes = new double[3][10];

    //0:linear search random, 1:linear search sorted, 2:binarysearch
    public static double[][] searchRunningTimes = new double[3][10];

    public static void start(HashMap<Integer,double[]> datas) {

        // random data measurements
        measureSelectionSort(datas,"random");
        measureQuickSort(datas,"random");
        measureBucketSort(datas,"random");
        measureLinearSearch(datas,"random");

        Helpers.sort(datas);

        // ascending sorted data
        measureSelectionSort(datas,"ascending");
        measureQuickSort(datas,"ascending");
        measureBucketSort(datas,"ascending");
        measureLinearSearch(datas,"ascending");
        measureBinarySearch(datas);

        Helpers.reverse(datas);

        // descending sorted data
        measureSelectionSort(datas,"descending");
        measureQuickSort(datas,"descending");
        measureBucketSort(datas,"descending");

    }


    private static void measureSelectionSort(HashMap<Integer, double[]> datas,String inputType) {

        double[] copy = new double[datas.get(0).length];
        System.arraycopy(datas.get(0),0,copy,0,copy.length);

        //prefill the cache
        SelectionSort.sort(copy);

        System.out.println("---selection sort on " + inputType+" input---");


        long startingTime;
        long stopTime;

        long elapsedTime = 0;
        double average;

        for (int i = 0; i < datas.size(); i++) {
            elapsedTime = 0;
            for (int j = 0; j < loopTimeForSorts; j++) {
                startingTime = System.nanoTime();
                SelectionSort.sort(datas.get(i).clone());
                stopTime = System.nanoTime();
                elapsedTime += stopTime-startingTime;
            }

            //convert to double to avoid int division
            average = elapsedTime / (double)(loopTimeForSorts*1000000);

            System.out.println("dataset" + (i + 1) + ":" + average+ "ms on average");

            switch (inputType) {
                case "random":
                    Experiments.sortingRandomDataRunningTimes[0][i] = average;
                    break;
                case "ascending":
                    Experiments.sortingAscendingDataRunningTimes[0][i] = average;
                    break;
                case "descending":
                    Experiments.sortingDescendingDataRunningTimes[0][i] = average;
                    break;
            }
        }
    }

    private static void measureQuickSort(HashMap<Integer, double[]> datas,String inputType) {

        double[] copy = new double[datas.get(0).length];

        System.arraycopy(datas.get(0),0,copy,0,copy.length);

        //prefill the cache
        QuickSort.sort(copy);

        System.out.println("---quick sort on " + inputType+" input---");

        long startingTime;
        long stopTime;

        long elapsedTime = 0;
        double average;

        for (int i = 0; i < datas.size(); i++) {
            elapsedTime = 0;
            for (int j = 0; j < loopTimeForSorts; j++) {
                startingTime = System.nanoTime();
                QuickSort.sort(datas.get(i).clone());
                stopTime = System.nanoTime();
                elapsedTime += stopTime-startingTime;
            }

            //convert to double to avoid integer divison
            average = elapsedTime / (double)(loopTimeForSorts*1000000);

            System.out.println("dataset" + (i + 1) + ":" + average+ "ms on average");

            switch (inputType) {
                case "random":
                    Experiments.sortingRandomDataRunningTimes[1][i] = average;
                    break;
                case "ascending":
                    Experiments.sortingAscendingDataRunningTimes[1][i] = average;
                    break;
                case "descending":
                    Experiments.sortingDescendingDataRunningTimes[1][i] = average;
                    break;
            }
        }
    }

    private static void measureBucketSort(HashMap<Integer, double[]> datas,String inputType) {

        System.out.println("---bucket sort on " + inputType+" input---");

        double[] copy = new double[datas.get(0).length];

        System.arraycopy(datas.get(0),0,copy,0,copy.length);

        //prefill the cache
        BucketSort.sort(copy);


        long startingTime;
        long stopTime;

        long elapsedTime = 0;
        double average;

        for (int i = 0; i < datas.size(); i++) {
            elapsedTime = 0;
            for (int j = 0; j < loopTimeForSorts; j++) {
                startingTime = System.nanoTime();
                BucketSort.sort(datas.get(i).clone());
                stopTime = System.nanoTime();
                elapsedTime += stopTime-startingTime;
            }

            //convert to double to avoid integer divison
            average = elapsedTime / (double)(loopTimeForSorts*1000000);

            System.out.println("dataset" + (i + 1) + ":" + average+ "ms on average");

            switch (inputType) {
                case "random":
                    Experiments.sortingRandomDataRunningTimes[2][i] = average;
                    break;
                case "ascending":
                    Experiments.sortingAscendingDataRunningTimes[2][i] = average;
                    break;
                case "descending":
                    Experiments.sortingDescendingDataRunningTimes[2][i] = average;
                    break;
            }
        }
    }


    private static void measureLinearSearch(HashMap<Integer,double[]> datas,String inputType) {

        double[] copy = new double[datas.get(0).length];

        System.arraycopy(datas.get(0),0,copy,0,copy.length);

        //prefill the cache
        LinearSearch.search(copy,copy[3]);


        System.out.println("---linear search on "+inputType+" data---");

        int randomId;

        long startingTime;
        long stopTime;

        long elapsedTime = 0;
        double average;

        for(int i = 0; i<datas.size();i++) {

            elapsedTime = 0;

            for(int j = 0; j<loopTimeForSearchs;j++) {

                //pick a random number in each loop and search for it
                //do this by picking a random id
                randomId = new Random().nextInt(datas.get(i).length);

                startingTime = System.nanoTime();
                LinearSearch.search(datas.get(i),datas.get(i)[randomId]);
                stopTime = System.nanoTime();
                elapsedTime += stopTime-startingTime;
            }

            //cast to double to avoid int division
            average = elapsedTime / (double)loopTimeForSearchs;

            System.out.println("dataset" + (i + 1) + ":" + average+ "ns on average");

            switch (inputType) {
                case "random":
                    Experiments.searchRunningTimes[0][i] = average;
                    break;
                case "ascending":
                    Experiments.searchRunningTimes[1][i] = average;
                    break;

            }

        }

    }

    public static void measureBinarySearch(HashMap<Integer,double[]>datas) {


        //prefill the cache
        BinarySearch.search(datas.get(3).clone(),datas.get(3)[0]);

        System.out.println("---Binary search (on sorted data)---");

        int randomId;

        long startingTime;
        long stopTime;

        long elapsedTime = 0;
        double average;

        for(int i = 0; i<datas.size();i++) {

            elapsedTime = 0;

            for(int j = 0; j<loopTimeForSearchs;j++) {

                //pick a random number in each loop and search for it
                //do this by picking a random id
                randomId = new Random().nextInt(datas.get(i).length);

                startingTime = System.nanoTime();
                BinarySearch.search(datas.get(i),datas.get(i)[randomId]);
                stopTime = System.nanoTime();
                elapsedTime += stopTime-startingTime;
            }

            //cast to double to avoid int division
            average = elapsedTime / (double)loopTimeForSearchs;

            System.out.println("dataset" + (i + 1) + ":" + average+ "ns on average");

            searchRunningTimes[2][i] = average;

            }
        }

    }








