import java.util.HashMap;

class Main {

    static HashMap<Integer, double[]> datas = new HashMap<>();

    public static void init() {
        datas.put(0, new double[500]);
        datas.put(1, new double[1000]);
        datas.put(2, new double[2000]);
        datas.put(3, new double[4000]);
        datas.put(4, new double[8000]);
        datas.put(5, new double[16000]);
        datas.put(6, new double[32000]);
        datas.put(7, new double[64000]);
        datas.put(8, new double[128000]);
        datas.put(9, new double[250000]);

        FileOperations.readCsv("TrafficFlowDataset.csv");
    }

    public static void main(String args[]) {

        init();

        //check sort functions
        HashMap<Integer,double[]> copy = Helpers.getCopy(datas);
        boolean bucketSortIsValid = BucketSort.isValid(copy.get(9));
        boolean quickSortIsValid = QuickSort.isValid(copy.get(8));

        boolean isValid = bucketSortIsValid && quickSortIsValid;

        if(isValid) {

            System.out.println("Starting experiments....");
            Experiments.start(datas);

            System.out.println("Generating charts...");
            ChartOperations.generateCharts();
        }

        else{
            System.out.println("Your sort implementations are not valid.");
        }

    }

}




