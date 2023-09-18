public class UpwardVertical extends Direction{
    @Override
    public int calcScore(int[] position,Jewel currentJewel,Grid gameGrid) {

        int row = position[0];
        int column = position[1];

        try {
            Jewel secondJewel = (Jewel) gameGrid.getBlock(row - 1, column);

            Jewel thirdJewel = (Jewel) gameGrid.getBlock(row - 2,column);

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
        gameGrid.deleteJewel(row - 1,column);
        gameGrid.deleteJewel(row - 2,column);
    }
}
