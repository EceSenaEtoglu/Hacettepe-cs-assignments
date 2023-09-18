import java.util.ArrayList;

public class Player extends User {

    // property array to display at show command
    private final ArrayList<String> properties = new ArrayList<>();
    private int railRoadNum = 0;
    private int jailCount = 0;
    private int freeParkingCount = 0;

    // position on board
    private int position  = 1;

    public Player(String name){
        super(name,15_000);
    }

    public void setPosition(int pos) {this.position = pos;}
    public int getPosition() { return this.position;}
    public int getRailRoadNum() {return this.railRoadNum;}

    // adds property to, properties array
    public void addProperty(Property property) {
        properties.add(property.getName());
        if (property instanceof RailRoad) {
            railRoadNum++;
        }
    }
    // Player 1 13080 have: Pentonville Road, Whitehall
    @Override
    public String toString() {
        String propertyString = String.join(",",properties);
        return String.format("%s\t%d\thave: %s",this.getName(),this.getMoney(),propertyString);

    }
    // returns free parking situation
    public int getFreeParkingCount() {
        return this.freeParkingCount;
    }
    // changes freeparking situation
    // in sequence 0,1,0,1
    public void setFreeParkingCount(int count) {
        this.freeParkingCount = count % 2;
    }

    // returns jail situation
    public int getJailCount() {
        return this.jailCount;
    }

    // changes injail situation
    //  in sequence 0,1,2,3,0,1,2,3...
    public void setInJailCount(int count) {
        this.jailCount = count % 4;
    }


}
