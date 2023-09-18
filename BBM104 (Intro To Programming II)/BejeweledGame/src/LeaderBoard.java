import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard {

     final ArrayList<Player> players = new ArrayList<>();

    // fill players with data in file
    public LeaderBoard(ArrayList<String> playerData) {
        for(String data: playerData) {

            String[] playerArray = data.split(" ");
            // playerArray[0] = "name"
            // playerArray[1] = "point"
            players.add(new Player(playerArray[0],Integer.parseInt(playerArray[1])));
        }
    }

    public void addPlayerToLeaderBoard(Player currentPlayer) {

        ArrayList<Player> playersCopy = new ArrayList<>(players);

        for(Player player: playersCopy) {

            // if player name already exists in leaderboard logically they must be same player
            // update player's score, delete prev score
            if(player.getName().equals(currentPlayer.getName())) {

                int point = player.getPoint();
                point += currentPlayer.getPoint();
                currentPlayer.setPoint(point);
                //remove previous score

                // two same names are the same person in this code
                // but equals method is overriden with point & name
                // because remove method would delete first occurence, in case of multiple same names it would lead error
                // so equals is overridden that way
                players.remove(player);
            }
        }

        players.add(currentPlayer);
    }

    public void sortLeaderBoard() {
        Collections.sort(players);
    }


    // Your rank is 1/5, your score is 35 points higher than Ali
    // Your rank is 2/5, your score is 20 points lower than Melis
    //Your rank is 3/4, your score is 5 points lower than Burak and 5 points higher than Ali
    public String getRankString(Player currentPlayer) {

        // sorted in ascending order
        sortLeaderBoard();
        int index;

        if(players.contains(currentPlayer)) {
             index = Collections.binarySearch(players, currentPlayer);
        }
        else {
            System.out.printf("%s is not in leaderboard",currentPlayer.getName());
            throw new RuntimeException(String.format("%s is not in leaderboard",currentPlayer.getName()));
        }

        // transform ındex to descending order version
        // [0,1,2] ındex of 2 is 2 >> [2,1,0] ındex of 2 turns to  0

        // rank = (transformed ındex) + 1
        int rank = (players.size() -1 - index) + 1;


        String rankString =  String.format("Your rank is %d/%d",rank,players.size());
        String comparisonString;

        // if winner player
        if(index == players.size()-1 ) {
            //Player closest to this player
            Player otherPlayer = players.get(index -1);

            // "5 points higher than Ali"
            comparisonString = Displayer.getComparisonString(otherPlayer,currentPlayer);

        }

        // if loser player
        else if (index == 0) {
            //Player closest to this player
            Player otherPlayer = players.get(index+1);

            // 5 points lower than Burak
            comparisonString =  Displayer.getComparisonString(otherPlayer,currentPlayer);

        }

        // if neither loser or winner
        else {

            // closest players
            Player betterPlayer = players.get(index+1);
            Player worsePlayer = players.get(index - 1);

            //5 points lower than Burak and 35 points higher than Ali
            comparisonString = Displayer.getComparisonString(betterPlayer,worsePlayer,currentPlayer);

        }

        return String.format("%s, your score is %s",rankString,comparisonString);
    }


    // returns players in leaderboard in string
    // !without sorting! the players
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for(Player player : players){
            sb.append(player.toString()).append("\n");
        }
        return sb.toString();
    }

}
