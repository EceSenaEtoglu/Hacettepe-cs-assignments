import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        // create hashmaps to hold objects from file
        HashMap<String, Person> peopleData;
        HashMap<String, Activity> activityData = new HashMap<>();

        // get "people.txt" file data into hashmap key = ıd, value = object
        peopleData = MyFileReader.getPeopleData("people.txt");

        // COMBINE "food.txt" and "sport.txt" data into activityData
        MyFileReader.getActivityData("food.txt", activityData);
        MyFileReader.getActivityData("sport.txt", activityData);


        // create commandArray to hold commands from arg. file
        ArrayList<String> commandArray = new ArrayList<>();

        // get commands to commandArray from file
        try {
            commandArray = MyFileReader.getCommands(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // create monitoring.txt file with given commands and datas

        try {
            MyFileWriter.createDataFile("monitoring.txt", commandArray, peopleData, activityData);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}