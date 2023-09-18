import java.util.ArrayList;

public class Displayer {

    public  ArrayList<String> loggings = new ArrayList<>();

    // data for file
    @Override
    public String toString() {
        return String.join("\n\n",loggings);
    }


    // functions for game strings.
    public static String getScoreString(int score) {
        return String.format("Score: %d points",score);
    }

    public static String getCommandString(String command){
        return String.format("Select coordinate or enter E to end the game: %s",command);
    }
    public static String getTotalScoreString(int totalScore) {
        return String.format("Total score: %d points",totalScore);

    }
    public static String getNameString(String name) {
        return String.format("Enter name: %s", name);
    }


    // return string in either forms:
    // 5 points lower than Burak
    // 35 points higher than Ali
    public static String getComparisonString(Player otherPlayer, Player currentPlayer) {

        int diff = currentPlayer.getPoint() - otherPlayer.getPoint();

        // if currentPlayer is better
        if(diff > 0) {
            return String.format("%d points higher than %s",diff,otherPlayer.getName());
        }
        else {
            diff = -1*diff;
            return String.format("%d points lower than %s",diff,otherPlayer.getName());
        }
    }

    // return the string in form : 5 points lower than Burak and 35 points higher than Ali
    public static String getComparisonString(Player betterPlayer, Player worsePlayer,Player currentPlayer) {

        String comp1 = getComparisonString(betterPlayer,currentPlayer);
        String comp2 = getComparisonString(worsePlayer,currentPlayer);

        return String.format("%s and %s",comp1,comp2);
    }

}
