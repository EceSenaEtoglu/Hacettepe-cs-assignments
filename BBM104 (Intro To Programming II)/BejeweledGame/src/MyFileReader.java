import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileReader {


    // read file data into string array
    public static ArrayList<String> getData(String fileName) throws IOException {

        ArrayList<String> data = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            data.add(line);
        }

        return data;

    }
}
