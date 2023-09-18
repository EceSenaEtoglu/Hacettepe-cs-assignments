public class RightUpperDiagonal extends Direction{

    // direction 3
    @Override
    public int calcScore(int[] position,Jewel currentJewel,Grid gameGrid) {

        int row = position[0];
        int column = position[1];

        try {
            Jewel secondJewel = (Jewel) gameGrid.getBlock(row - 1, column+1);

            Jewel thirdJewel = (Jewel) gameGrid.getBlock(row - 2,column+2);

            return BejeweledGame.calculateMatchScore(currentJewel,secondJewel,thirdJewel);
        }

        // if next item is space or no way to go
        catch (ClassCastException | IndexOutOfBoundsException a) {
            return 0;
        }

    }

    @Override
    public void delete(int[] position, Grid gameGrid) throws IndexOutOfBoundsException {
        int row = position[0];
        int column = position[1];

        gameGrid.deleteJewel(row,column);
        gameGrid.deleteJewel(row - 1,column+1);
        gameGrid.deleteJewel(row - 2,column+2);
    }
}
