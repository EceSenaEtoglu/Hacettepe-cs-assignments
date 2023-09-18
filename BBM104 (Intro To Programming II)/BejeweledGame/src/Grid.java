import java.util.ArrayList;

public class Grid {

    private final ArrayList<ArrayList<Block>> gridDesign = new ArrayList<>();

    public Grid(ArrayList<String> gridData)  {
        fillGrid(gridData);
    }

    // fill board with jewels from given file data
    private void fillGrid(ArrayList<String> gridData)  {

        for(String data: gridData) {

            String[] blockSymbols = data.split(" ");
            gridDesign.add(getBlocksFromSymbols(blockSymbols));
        }
    }


    // get array of block objects that match in sequence of given array of symbols
    // if no match is found, put space obj
    private ArrayList<Block> getBlocksFromSymbols(String[] jewelSymbols){

        // construct blockMap
        BlockDataBase blockMap = new BlockDataBase();

        ArrayList<Block> blocks = new ArrayList<>();
        for(String s: jewelSymbols) {
            // add jewel object based on symbol
            // if key does not exist put space object
            blocks.add(blockMap.getBlockMap().getOrDefault(s,new Space()));
        }
        return blocks;
    }

    // get array of string symbols that match in sequence of given array of block objects
    private ArrayList<String> getSymbolsFromBlocks(ArrayList<Block> blocks) {

        ArrayList<String> symbols = new ArrayList<>();
        for(Block block : blocks) {
            symbols.add(block.getSymbol());
        }
        return symbols;
    }

    // get grid matrix into string
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for(ArrayList<Block> row : gridDesign) {
            sb.append(String.join(" ",getSymbolsFromBlocks(row)));
            sb.append("\n");
        }
        // remove last \n
        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

    // return block obj in specified coord.
    public Block getBlock(int row, int column) throws IndexOutOfBoundsException {
        return gridDesign.get(row).get(column);
    }

    private static void makeJewelsFall(ArrayList<Block> columnOfGrid) {

        Space tempSpace = new Space();
        ArrayList<Block> tempList = new ArrayList<>(columnOfGrid);

        for(int i = 0;i<tempList.size();i++ ) {
            Block block = tempList.get(i);

            // if is a space
            if(block.getClass() == tempSpace.getClass()) {
                // remove and place at start
                columnOfGrid.remove(i);
                columnOfGrid.add(0,block);
            }
        }
    }

    // fix grid with rule : if there is a space underneath a jewel  make that jewel fall
    public void fixGrid() {
        int numberOfColumns = gridDesign.get(0).size();
        int numberOfRows = gridDesign.size();

        for(int column = 0; column < numberOfColumns; column++) {

            // create list to store blocks at columns
            ArrayList<Block> columnList = new ArrayList<>();

            for(int row = 0; row < numberOfRows; row++) {

                columnList.add(gridDesign.get(row).get(column));
            }

            //if there is a space underneath a jewel  make that jewel fall, fix columnList with this rule
            // [D,W,SPACE,W,SPACE] turns to [SPACE,SPACE,D,W,W]
            makeJewelsFall(columnList);

            // fix gridDesign by assigning each column to columnList
            for(int row = 0; row < numberOfRows; row++) {
                gridDesign.get(row).set(column,columnList.get(row));
            }
        }
    }

    public void deleteJewel(int x, int y) {
        gridDesign.get(x).set(y,new Space());
    }

}
