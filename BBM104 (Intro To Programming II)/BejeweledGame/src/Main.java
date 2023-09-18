import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        // get data parsed from files into arraylists

        ArrayList<String> gridData = new ArrayList<>();
        try {
            gridData = MyFileReader.getData(args[0]);
        } catch (IOException e) {
            System.err.println("something is wrong with gameGrid.txt");
        }
        ArrayList<String> commands = new ArrayList<>();
        try {
            commands = MyFileReader.getData(args[1]);
        } catch (IOException e) {
            System.err.println("something is wrong with command.txt");
        }
        ArrayList<String> playerData = new ArrayList<>();
        try {
            playerData = MyFileReader.getData("leaderboard.txt");
        } catch (IOException e) {
            System.err.println("something is wrong with leaderboard.txt");
        }



        // map for outputs of the game

        HashMap<String, String> outputs = new HashMap<>();
        try {

            // start the game, get output datas
            outputs = GameFlow.controlGame(commands, gridData, playerData);
        }
        catch (Exception e) {
            System.out.println("An exception occured during game");
        }
        finally {

            // write to files
            MyFileWriter.writeToFile(outputs.get("monitoring.txt"), "monitoring.txt");
            MyFileWriter.writeToFile(outputs.get("leaderboard.txt"), "leaderboard.txt");
        }


    }

}
