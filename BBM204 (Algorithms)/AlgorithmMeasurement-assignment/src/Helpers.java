import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Helpers {

    //reverse individual arrays in given hashmap
    public static void reverse(HashMap<Integer,double[]> datas) {

        for(int i = 0; i<datas.size();i++) {

            //create copy array
            int length = datas.get(i).length;
            double[] copy = new double[length];
            System.arraycopy(datas.get(i),0, copy, 0, length);

            //put content in reverse order
            for(int j = 0; j<copy.length;j++) {
                int id = length-1-j;
                datas.get(i)[id] = copy[j];
            }

        }
    }

    public static void sort(HashMap<Integer,double[]> datas) {

        for(int i = 0; i<datas.size();i++) {
            Arrays.sort(datas.get(i));
        }
    }

    // deep copies the content of initialMap,returns the resultant map
    public static HashMap<Integer,double[]> getCopy(HashMap<Integer,double[]> initialMap) {

        HashMap<Integer, double[]> copy = new HashMap<>();
        for (HashMap.Entry<Integer, double[]> entry : initialMap.entrySet()) {

            //get key value pairs
            int key = entry.getKey();
            double[] initialArr = entry.getValue();

            //apply deep copy
            double[] copiedArr = new double[initialArr.length];
            System.arraycopy(initialArr, 0, copiedArr, 0, initialArr.length);
            copy.put(key, copiedArr);
        }

        return copy;

    }

}
