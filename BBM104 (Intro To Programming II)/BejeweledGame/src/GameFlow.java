import java.util.ArrayList;
import java.util.HashMap;

public class GameFlow {


    // constructs the game with given data, controls the game
    // returns file data in a hashmap
    // Key: file name Value: String to write to file
    public static HashMap<String, String> controlGame(ArrayList<String> commands, ArrayList<String> gridData, ArrayList<String> playerData) {

        // initializations for game
        Grid grid = new Grid(gridData);
        LeaderBoard leaderBoard = new LeaderBoard(playerData);
        BejeweledGame myGame = new BejeweledGame(grid);

        // for loggings
        Displayer myDisplayer = new Displayer();
        String leaderBoardTxtData = null;

        myDisplayer.loggings.add("Game grid:");
        myDisplayer.loggings.add(grid.toString());

        // process commands
        for (String command : commands) {

            myDisplayer.loggings.add(Displayer.getCommandString(command));

            // if game is ended
            if (command.equals("E")) {

                // "Total score: %d points"
                myDisplayer.loggings.add(Displayer.getTotalScoreString(myGame.getTotalScore()));

                // get name,  the command after "E"
                String name = commands.get(commands.indexOf(command) + 1);

                // add "Enter name: name"
                myDisplayer.loggings.add(Displayer.getNameString(name));

                // add player to leaderboard
                Player currentPlayer = new Player(name, myGame.getTotalScore());
                leaderBoard.addPlayerToLeaderBoard(currentPlayer);

                // get leadorboardtxt string without sorting
                leaderBoardTxtData = leaderBoard.toString();

                leaderBoard.sortLeaderBoard();

                // get string of rank and point difference between closest players
                String comparisonString = leaderBoard.getRankString(currentPlayer);

                // add string to loggings
                myDisplayer.loggings.add(comparisonString);
                // end of game !
                break;
            }

            // if game continiues
            else {

                int x = Integer.parseInt(command.split(" ")[0]);
                int y = Integer.parseInt(command.split(" ")[1]);
                try {
                    //  throws (IndexOutOfBounds, ClassCast exception):
                    // if position is out of grid or position is space
                    myGame.setPosition(x, y);

                    // calculate score to have when current position is selected
                    int score = myGame.bombJewels();

                    // display modified string
                    myDisplayer.loggings.add(grid.toString());

                    // display score string
                    myDisplayer.loggings.add(Displayer.getScoreString(score));
                } catch (ClassCastException | IndexOutOfBoundsException e) {
                    myDisplayer.loggings.add("Please enter a valid coordinate");
                }
            }
        }
        myDisplayer.loggings.add("Good bye!");

        // output data of the game
        HashMap<String, String> outputs = new HashMap<>();
        outputs.put("monitoring.txt", myDisplayer.toString());
        outputs.put("leaderboard.txt", leaderBoardTxtData);
        return outputs;


    }

}
