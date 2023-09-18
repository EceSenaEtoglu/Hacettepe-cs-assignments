import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        // read property JSON file
        // Integer key is position on board
        HashMap<Integer, Property> propertiesData = new PropertyJsonReader().propertiesData;

        // read list JSON file
        ListJsonReader listReader = new ListJsonReader();

        // construct chance and community deck
        ArrayList<String> chanceList = listReader.myChanceList;
        ArrayList<String> communityChestList = listReader.myCommunityChestList;


        // construct board
        Board myBoard = new Board(propertiesData);
        // construct game
        MonopolyGame myMonopoly = new MonopolyGame(myBoard, new Player("Player 1"), new Player("Player 2"), new Banker("Banker"),chanceList,communityChestList);


        // get commands from file
        ArrayList<String> commands  = new ArrayList<>();
        try {
            commands = new CommandReader(args[0]).commands;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // process Game commands, get the content
        String fileString = GameFlow.processGame(myMonopoly,commands);

        // write contents to file

        try {
            MyFileWriter.createFile(fileString,"output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
