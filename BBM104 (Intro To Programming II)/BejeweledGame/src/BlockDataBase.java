import java.util.ArrayList;
import java.util.HashMap;

public class BlockDataBase {

    private static final HashMap<String,Block> blockData = new HashMap<>();

    public BlockDataBase() {
        setBlockMap();
    }

    // construct block objects for the game
    // game will acces objects with blockData attribute
    private void setBlockMap() {

        // get Direction obj. data
        ArrayList<Direction> oneNine = DirectionDataBase.getDirectionsOneNine();
        ArrayList<Direction> fourSix = DirectionDataBase.getDirectionFourSix();
        ArrayList<Direction> twoEight = DirectionDataBase.getDirectionTwoEight();
        ArrayList<Direction> threeSeven = DirectionDataBase.getDirectionThreeSeven();

        ArrayList<Direction> oneNineThreeSeven = DirectionDataBase.getDirectionOneNineThreeSeven();
        ArrayList<Direction> fourSixTwoEight = DirectionDataBase.getDirectionFourSixTwoEight();
        ArrayList<Direction> wildCardDirection = DirectionDataBase.getWildCardDirection();


        // Jewel data mathSymbols
        ArrayList<String> mathSymbols = new ArrayList<>();
        mathSymbols.add("-");
        mathSymbols.add("+");
        mathSymbols.add("|");
        mathSymbols.add("/");
        mathSymbols.add("\\");

        // otherJewels
        ArrayList<String> otherJewels = new ArrayList<>();
        otherJewels.add("T");
        otherJewels.add("D");
        otherJewels.add("S");
        otherJewels.add("W");

        // non math
        blockData.put("D",new Jewel("D",30, oneNineThreeSeven));
        blockData.put("S",new Jewel("S",15, fourSix));
        blockData.put("T",new Jewel("T",15, twoEight));
        blockData.put("W",new Jewel("W",10,otherJewels, wildCardDirection));

        // mathSymbols
        blockData.put("-",new MathSymbol("-",mathSymbols,fourSix));
        blockData.put("/",new MathSymbol("/",mathSymbols,threeSeven));
        blockData.put("+",new MathSymbol("+",mathSymbols,fourSixTwoEight));
        blockData.put("\\",new MathSymbol("\\",mathSymbols,oneNine));
        blockData.put("|",new MathSymbol("|",mathSymbols,twoEight));

        // space
        blockData.put(" ",new Space());
    }

    public HashMap<String,Block> getBlockMap() {
        return blockData;
    }


}
