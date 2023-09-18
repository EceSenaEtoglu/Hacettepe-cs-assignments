import java.util.ArrayList;

public class BejeweledGame {

    private int totalScore;
    Grid grid;
    Jewel currentJewel;
    int[] currentPosition = new int[2];

    public BejeweledGame(Grid grid) {
        this.grid = grid;
    }


    // space block and out of grid is not a valid posiiton
    /*if position is a valid pos sets currentjewel, else throws exception */
    public void setPosition(int x, int y) throws RuntimeException {
        this.currentPosition[0] = x;
        this.currentPosition[1] = y;
        setCurrentJewel();
    }
    public int getTotalScore() {
        return this.totalScore;
    }

    private void setCurrentJewel() throws ClassCastException {
        this.currentJewel = (Jewel) grid.getBlock(currentPosition[0],currentPosition[1]);
    }

    // bombs jewels
    public int bombJewels() {

        ArrayList<Direction> directions = currentJewel.getMATCH_DIRECTIONS();
        int score = 0;

        for(Direction direction:directions) {
            score = direction.calcScore(currentPosition,currentJewel,grid);

            // if match is found in direction, bomb jewels
            if(!(score == 0)) {

                direction.delete(currentPosition,grid);
                // add score to total score
                totalScore += score;
                break;

            }

        }
        grid.fixGrid();
        return score;


    }

    public static int calculateMatchScore(Jewel currentJewel, Jewel secondJewel, Jewel thirdJewel) {

        // if 1st and 2nd does not match, not a triple
        if(!(currentJewel.isAMatch(secondJewel))) {
            return 0;
        }

        // different matching sequence app. for Wildcard
        if ((currentJewel.getSymbol().equals("W"))) {

            if (secondJewel.isAMatch(thirdJewel) || thirdJewel.isAMatch(secondJewel)) {
                return currentJewel.getPoint() + secondJewel.getPoint() + thirdJewel.getPoint();
            } else {
                return 0;
            }

        }

        // if not a wildcard
        else {
            if(secondJewel.isAMatch(thirdJewel)) {
                return currentJewel.getPoint() + secondJewel.getPoint() + thirdJewel.getPoint();
            }
            else return 0;
        }
    }

}
