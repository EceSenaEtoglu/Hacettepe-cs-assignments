public class LeftHorizontal extends Direction {

    @Override
    public int calcScore(int[] position,Jewel currentJewel,Grid gameGrid) {

        int row = position[0];
        int column = position[1];

        try {
            Jewel secondJewel = (Jewel) gameGrid.getBlock(row, column - 1);
            Jewel thirdJewel = (Jewel) gameGrid.getBlock(row, column - 2);

            return BejeweledGame.calculateMatchScore(currentJewel, secondJewel, thirdJewel);

        }

        // if next item is space or there is no way to go on grid
        catch (ClassCastException | IndexOutOfBoundsException a) {
            return 0;
        }

    }

    @Override
    public void delete(int[] position, Grid gameGrid) throws IndexOutOfBoundsException {
        int row = position[0];
        int column = position[1];

        gameGrid.deleteJewel(row,column);
        gameGrid.deleteJewel(row,column-1);
        gameGrid.deleteJewel(row,column-2);
    }
}
