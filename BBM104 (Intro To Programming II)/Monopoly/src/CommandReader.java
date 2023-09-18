import java.io.*;
import java.util.ArrayList;

public class CommandReader {

    final ArrayList<String> commands = new ArrayList<>();

    // processes commands from file
    public CommandReader(String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line;

        while((line = br.readLine())!= null) {
            line = line.trim();
            commands.add(line);

        }
    }

}
