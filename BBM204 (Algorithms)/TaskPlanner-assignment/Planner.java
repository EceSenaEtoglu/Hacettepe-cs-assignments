import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Planner {
    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with a Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();

        // calculate compatibility before calculating max weights
        calculateCompatibility();
        System.out.println("Calculating max array");
        System.out.println("---------------------");
        calculateMaxWeight(taskArray.length-1);

    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        // YOUR CODE HERE

        int lo = 0;
        int hi = index-1;
        int mid;

        while(lo<=hi) {
            mid = lo + (hi - lo) / 2;
            int comp = taskArray[mid].getFinishTime().compareTo(taskArray[index].getStartTime());
            // if mid value is compatible check mid +1
            // if mid +1 is compatible move lo to mid+1
            // else return mid
            if (comp <= 0) {
                comp = taskArray[mid + 1].getFinishTime().compareTo(taskArray[index].getStartTime());
                if (comp <= 0) {
                    // go right
                    lo = mid + 1;
                } else {
                    return mid;
                }

            }
            else {
                //go left
                hi = mid - 1;
            }

        }
        return -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        // YOUR CODE HERE
        for(int i = 0; i<compatibility.length;i++){
            compatibility[i] = binarySearch(i);
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        // YOUR CODE HERE
        // calculate dynamic solution
        System.out.println("\nCalculating the dynamic solution");
        System.out.println("--------------------------------");
        solveDynamic(taskArray.length-1);

        //fix tasks in ascending order of finish time
        Collections.reverse(planDynamic);

        //print dynamic solution
        System.out.println("\nDynamic Schedule");
        System.out.println("----------------");
        String dynamicScheduled = planDynamic.stream().map(Object::toString)
                .collect(Collectors.joining("\n"));
        System.out.println(dynamicScheduled);
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        // YOUR CODE HERE

        System.out.println(String.format("Called solveDynamic(%s)",i));

        // base case no prev task exists
        // schedule the task
        if(i == 0){
            planDynamic.add(taskArray[i]);
        }

        // check if given task is scheduled
        else{
            // find weights of first task compatible and prev task
            double compatibleTaskWeight = compatibility[i] == -1 ? 0: maxWeight[compatibility[i]];
            double prevTaskWeight = maxWeight[i-1];

            // this task is scheduled
            if(taskArray[i].getWeight() + compatibleTaskWeight > prevTaskWeight){
                //add the task
                planDynamic.add(taskArray[i]);
                // inspect compatibility[i] if compatibility[i] is a task (!= -1)
                if(compatibility[i] != -1){
                    solveDynamic(compatibility[i]);
                }

            }

            // this task is not scheduled
            // inspect i -1
            else{
                solveDynamic(i-1);

            }
        }
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        // YOUR CODE HERE
        System.out.println(String.format("Called calculateMaxWeight(%s)",i));

        // if task was not memoized memoize task
        // recalculate 0 in each allocation
        if(i == -1 || i== 0 || maxWeight[i] == null) {
            // base case no task exists
            if(i == -1){
                return 0.0;
            }

            double currWeight = taskArray[i].getWeight();
            maxWeight[i] = Math.max(currWeight+calculateMaxWeight(compatibility[i]),calculateMaxWeight(i-1));
        }
        return maxWeight[i];
    }



    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        // YOUR CODE HERE

        // add task[0]
        // if each task is compatible with last added task, add the task
        planGreedy.add(taskArray[0]);
        for(int i = 1; i<taskArray.length;i++){
            if(Task.isCompatible(planGreedy.get(planGreedy.size()-1),taskArray[i])){
                planGreedy.add(taskArray[i]);
            }
        }
        // print operations
        System.out.println("\nGreedy Schedule");
        System.out.println("---------------");
        String greedyScheduled = planGreedy.stream().map(Object::toString)
                .collect(Collectors.joining("\n"));
        System.out.println(greedyScheduled);
        return planGreedy;
    }
}
