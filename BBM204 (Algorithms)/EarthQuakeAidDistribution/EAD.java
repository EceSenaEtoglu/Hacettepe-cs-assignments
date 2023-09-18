import java.util.*;

public class Main {

    public static void printTable(int[][] table,int rowSize,int colSize) {

        for(int i = 0; i<rowSize;i++) {

            for(int j = 0; j<colSize;j++) {
                
                if(table[i][j] == 0){
                    System.out.print("F");
                }
                else{
                    System.out.print("T");
                }
            }

            if(i != rowSize-1) {
                System.out.println();
            }

        }
    }

    //solution using DP approach
    //finds the max obtained kg in range [0,K] from weights arr
    public static int findMaxKg(int[][] table,int[] weights, int K) {

        int maxKg = 0;

        int rowSize = K + 1;
        int colSize = weights.length + 1;

        // create the table
        for (int w = 0; w < rowSize; w++) {

            for (int i = 0; i < colSize; i++) {

                //initial condition
                if(w == 0 && i == 0) {
                    table[w][i] = 1;
                }

                //no items so this kg is not applicable
                else if (i == 0 && w > 0) {
                    table[w][i] = 0;
                }
                
                // curr item's weight can't be taken.
                // bind previous subset's value
                else if (weights[i - 1] > w) {
                    table[w][i] = table[w][i - 1];
                }

                // curr item's weight can be taken
                else {
                    table[w][i] = table[w][i - 1] | table[w - weights[i - 1]][i - 1];
                           
                }

            }
        }

        //now we find max weight by starting traversing from table[w][colSize-1]
        for(int w = rowSize-1;w>=0;w--) {

            if(table[w][colSize-1] == 1) {
                maxKg = w;
                break;
            }
        }

        return maxKg;

    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int K = scanner.nextInt();
        int arrSize = scanner.nextInt();

        int[] weights = new int[arrSize];

        for(int i = 0; i<arrSize;i++) {
            weights[i] = scanner.nextInt();
        }

        // create a table 
        // columns consisting of item subsets
        // row consisting of possible weights
        int rowS = K+1;
        int colS = arrSize+1;
        int[][] table = new int[rowS][colS];

        //find maxKg
        int maxKg = findMaxKg(table,weights,K);
        
        //print results
        System.out.println(maxKg);
        printTable(table,rowS,colS);

    }
}