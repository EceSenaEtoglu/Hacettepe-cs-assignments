import java.util.*;

public  class Jewel extends Block {

    // symbols of jewels that match this jewel
    private final ArrayList<String> MATCH_JEWELS = new ArrayList<>();
    // directions to look at for matches, regarding the order in the array
    private final ArrayList<Direction> MATCH_DIRECTIONS;
    // point of the jewel
    private final int point;

    // assigns given matchJewels to this.matchJewels
    // if a jewel matches with it's own kind it has to be specified
    public Jewel(String symbol,int point, ArrayList<String> matchJewels, ArrayList<Direction> matchDirections) {

        super(symbol.toUpperCase(Locale.ROOT));
        this.point = point;

        this.MATCH_JEWELS.addAll(matchJewels);
        this.MATCH_DIRECTIONS = matchDirections;
    }

    // if no matchJewel list is provided, default value is object's symbol
    public Jewel(String symbol,int point,ArrayList<Direction> matchDirections) {
        super(symbol.toUpperCase(Locale.ROOT));
        this.point = point;
        this.MATCH_JEWELS.add(this.getSymbol());
        this.MATCH_DIRECTIONS = matchDirections;
    }

    public Jewel(String symbol) {
        super(symbol);
        this.point = 0;
        this.MATCH_DIRECTIONS = new ArrayList<>();
    }

    public int getPoint() {
        return point;
    }

    public ArrayList<Direction> getMATCH_DIRECTIONS(){return MATCH_DIRECTIONS;}

    public boolean isAMatch(Jewel otherJewel) {
        return MATCH_JEWELS.contains(otherJewel.getSymbol());
    }
}



