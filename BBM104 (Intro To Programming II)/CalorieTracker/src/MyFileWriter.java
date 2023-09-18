import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyFileWriter {

    /* Takes data of People and Activity objects, takes commandArray, process related Activity commands for each Person obj
       Creates a data file, fileName is given as parameter
       throws IO exception if occurs */

    public static void createDataFile(String fileName,ArrayList<String> commandArray, HashMap<String,Person> peopleData, HashMap<String,Activity> activityData) throws IOException {

        // create peopleArray to hold people seen on commands
        ArrayList<Person> peopleArray = new ArrayList<>();

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        for (int i = 0; i < commandArray.size(); i++) {

            String command = commandArray.get(i);

            // case eat or exercise
            if (command.charAt(0) == '1') {
                String[] commands = command.split("\t");

                Person myPerson = peopleData.get(commands[0]);
                Activity activity =activityData.get(commands[1]);
                double duration = Double.parseDouble(commands[2]);

                // update calorie burned
                // java does implicit casting if duration is int
                myPerson.doActivity(activity, duration);
                // write activity data
                writer.write(activity.getActivityData(myPerson,duration));
                // store obj for potential command printWarn or printList
                if (myPerson.doesNotExist(peopleArray)) {
                    peopleArray.add(myPerson);
                }
            }

            else if ("printWarn".equals(command)) {
                writer.write(Person.getWarnText(peopleArray));}

            else if ("printList".equals(command)) {
                writer.write(Person.getPrintText(peopleArray));}


            // potential empty lines are extracted
            // else is assured to be print(Ä±d) command in this project
            else {
                Person myPerson = peopleData.get(command.substring(6, 11));
                writer.write(myPerson.toString());}

            // don't write star if it is the last command
            if (i != commandArray.size() - 1) {
                writer.write("\n" + "***************" + "\n");
            }
            writer.flush();

        } // end of for loop
        writer.close();
    }
}