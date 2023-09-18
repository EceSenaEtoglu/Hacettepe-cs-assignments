import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        // YOUR CODE HERE
        String[] startTime = start.split(":");
        int hour = Integer.parseInt(startTime[0]);

        hour += duration;
        String finishTime = "";
        if(hour<10) {
            finishTime += "0";
        }
        finishTime += hour+":"+startTime[1];
        return finishTime;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        // YOUR CODE HERE
        double weight = (double) importance / duration;
        if(urgent){
            weight*= 2000;
        }
        return weight;

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        // YOUR CODE HERE
        Task taskObj = (Task) o;
        return this.getFinishTime().compareTo(taskObj.getFinishTime());
    }
    @Override
    public String toString(){
        return String.format("At %s, %s.",start,name);
    }

    // check if task1 is compatible with task2
    public static boolean isCompatible(Task task1,Task task2){
        return task1.getFinishTime().compareTo(task2.getStartTime()) <= 0;
    }
}
