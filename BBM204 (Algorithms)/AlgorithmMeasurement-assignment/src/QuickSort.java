import java.util.Arrays;

public class QuickSort {

    public static void sort(double[] input) {

        sort(input,0,input.length-1);
    }
    private static void sort(double[] input,int lowPointer,int highPointer) {

        // iterative quicksort using stack
        int[] stack = new int[highPointer - lowPointer + 1];
        int top = -1;
        int pivot;

        // put initial pointers
        stack[++top] = lowPointer;
        stack[++top] = highPointer;

        // keep popping until stack is empty
        while (top >= 0) {

            // Pop highPointer and lowPointer
            highPointer = stack[top--];
            lowPointer = stack[top--];

            // put pivot in correct position
            pivot = partition(input, lowPointer, highPointer);

            // push elements left side of the pivot to the left of stack
            if (pivot - 1 > lowPointer) {
                stack[++top] = lowPointer;
                stack[++top] = pivot - 1;
            }

            // push elements right side of the pivot to the right of stack
            if (pivot + 1 < highPointer) {
                stack[++top] = pivot + 1;
                stack[++top] = highPointer;
            }
        }
    }

    private static int partition(double[] input,int low,int high) {

        //pick last element as pivot
        double pivot  = input[high];

        int i = low -1;
        for(int j = low; j<=high-1;j++) {

            if(input[j] <= pivot) {
                i++;
                //swap input[i] with input[j]
                double temp = input[i];
                input[i] = input[j];
                input[j] = temp;
            }
        }

        //swap input[i+1] input[high]
        double temp = input[i+1];
        input[i+1] = input[high];
        input[high] = temp;

        return i+1;

    }

    // return true if quicksort algorithm is correct by comparing with Arrays.sort on testData
    public static boolean isValid(double[] testData) {

        double[] copy = new double[testData.length];

        System.arraycopy(testData, 0, copy,0, testData.length);

        Arrays.sort(copy);
        QuickSort.sort(testData);

        for(int i = 0; i<copy.length;i++) {

            if(copy[i] != testData[i]) {
                return false;
            }
        }
        return true;
    }
}
