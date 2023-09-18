import java.util.HashMap;

public class Board {

    // design of board
    // boardDesign is an HashMap with key> value as position of square, square
    // some squares are strings, some are property obj, some are card arrays
    // for example 11,"Jail"  40,Property obj
    final HashMap<Integer, Object> boardDesign = new HashMap<Integer, Object>();

    public Board(HashMap<Integer, Property> propertiesData) {
        fillBoard(propertiesData);
    }

    // fills boardDesign array  with 40 elements. Property positions are taken from  propertiesData.
    // Other positions are taken from Appendix B instructions

    private void fillBoard(HashMap<Integer, Property> propertiesData) {

        for (int i = 1; i <= 40; i++) {
            if (i == 3 || i == 18 || i == 34) {
                boardDesign.put(i, "Community Chest");
            } else if (i == 1) {
                boardDesign.put(i, "Go");
            } else if (i == 8 || i == 23 || i == 37) {
                boardDesign.put(i, "Chance");
            } else if (i == 11) {
                boardDesign.put(i, "Jail");
            } else if (i == 31) {
                boardDesign.put(i, "Go to Jail");
            } else if (i == 21) {
                boardDesign.put(i, "Free Parking");
            } else if (i == 39 || i == 5) {
                boardDesign.put(i, (i == 39) ? "Super Tax" : "Income Tax");
            }
            // if position is a property
            // put property object to that position,

            else {
                boardDesign.put(i, propertiesData.get(i));
            }
        }
    }

    // returns property's position based on it's given name
    // it is assured that property is in bored, else returns 27, game fails
    // used for "Advance to Leicester Square"
    public int getPropertyPos(String propertyName) {
        for (int key : boardDesign.keySet()) {
            if (boardDesign.get(key) instanceof Property) {
                Property tempProperty = (Property) boardDesign.get(key);
                if(tempProperty.getName().equals(propertyName)) {
                    return key;
                }
            }
        }
        return 27;
    }


}
