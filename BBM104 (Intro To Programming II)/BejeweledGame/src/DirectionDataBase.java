import java.util.ArrayList;

public class DirectionDataBase {

    // This class returns ArrayList<Direction> in specified order

    // 1: left upper diagonal
    // 9 : left lower diagonal

    public static ArrayList<Direction> getDirectionsOneNine() {
        ArrayList<Direction> oneNine = new ArrayList<>();
        oneNine.add(new LeftUpperDiagonal());
        oneNine.add(new LeftLowerDiagonal());
        return oneNine;
    }

    // 3: right upper diagonal
    // 7 right lower diagonal

    public static ArrayList<Direction> getDirectionThreeSeven() {
        ArrayList<Direction> threeSeven = new ArrayList<>();
        threeSeven.add(new RightUpperDiagonal());
        threeSeven.add(new RightLowerDiagonal());
        return threeSeven;
    }

    // 2: upward vertical
    // 8: downward vertical
    public static ArrayList<Direction> getDirectionTwoEight() {
        ArrayList<Direction> twoEight = new ArrayList<>();
        twoEight.add(new UpwardVertical());
        twoEight.add(new DownwardVertical());
        return twoEight;
    }

    // 4: left horizontal
    // 6 : right horizontal

    public static ArrayList<Direction> getDirectionFourSix() {
        ArrayList<Direction> fourSix = new ArrayList<>();
        fourSix.add(new LeftHorizontal());
        fourSix.add(new RightHorizontal());
        return fourSix;
    }

    public static ArrayList<Direction> getDirectionOneNineThreeSeven() {
        ArrayList<Direction> oneNine = getDirectionsOneNine();
        ArrayList<Direction> threeSeven = getDirectionThreeSeven();
        oneNine.addAll(threeSeven);
        return oneNine;
    }

    public static ArrayList<Direction> getDirectionFourSixTwoEight() {
        ArrayList<Direction> fourSix = getDirectionFourSix();
        ArrayList<Direction> twoEight = getDirectionTwoEight();
        fourSix.addAll(twoEight);
        return fourSix;
    }

    // 2-8, 4-6, 1-9 , 3-7
    public static ArrayList<Direction> getWildCardDirection() {
        ArrayList<Direction> wildCard = getDirectionTwoEight();
        wildCard.addAll(getDirectionFourSix());
        wildCard.addAll(getDirectionOneNineThreeSeven());
        return wildCard;
    }


}
