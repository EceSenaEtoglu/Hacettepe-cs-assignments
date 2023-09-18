import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class MyFileReader {


    /*reads people data from file, constructs Person objects, creates hashmap: value= object, key = obj.PERSON_ID
     returns the hashmap */

    public static HashMap<String, Person> getPeopleData(String fileName) {
        // Hashmap key: peopleObj.PERSON_ID, value: peopleObj

        HashMap<String, Person> peopleData = new HashMap<>();

        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {

                String[] lineData = line.trim().split("\t");
                // lineData[0], lineData[1], lineData[2], lineData[3], lineData[4], lineData[5] = personId, name, gender, weight, height,  dateOfBirth

                peopleData.put(lineData[0], new Person(lineData[0], lineData[1], lineData[2], lineData[3], lineData[4], lineData[5]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return peopleData;
    }


    /* read data from given file, constructs activity objects based on Ids
       manipulate given Hashmap, key = obj.ID value= obj */

    public static void  getActivityData(String fileName,HashMap<String,Activity> activityMap)  {

        BufferedReader reader;
        String line;

        try {
            reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {

                String[] lineData = line.trim().split("\t");

                // lineData[0] is ID of Sport or Food; sport Id's start with 2, food Id's start with 1

                // if given activity is food, construct Food obj, add to Hashmap
                if(lineData[0].charAt(0) == '1') {
                    activityMap.put(lineData[0],new Food(lineData[0],lineData[1],Integer.parseInt(lineData[2])));}


                // if sport, construct Sport obj, add to Hashmap
                else if (lineData[0].charAt(0) == '2'){
                    activityMap.put(lineData[0],new Sport(lineData[0],lineData[1],Integer.parseInt(lineData[2])));}
                }
 
            } // end of try block

        catch (IOException e) {
            e.printStackTrace();
        }
    }



    // read lines from file, trims, adds to ArrayList, returns the ArrayList

    public static ArrayList<String> getCommands(String fileName) throws IOException {

        // create ArrayList to hold commands
        ArrayList<String> commandArray = new ArrayList<>();

        // read file
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while((line = reader.readLine()) != null) {
            line = line.trim();

            // eliminate empty line
            if(line.length()!=0) {
                commandArray.add(line);}
        }

        return commandArray;
    }
}