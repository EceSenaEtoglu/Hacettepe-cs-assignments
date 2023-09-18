public abstract class Property {

    // ID is position on board
    private final int ID;
    private final String NAME;
    private final int COST;

    private Player owner;

    public Property(int id, String name,int cost) {
        ID = id;
        NAME = name;
        COST = cost;
    }

    public int getId() {
        return ID;
    }

    public int getCost() {
        return COST;
    }

    public String getName(){
        return NAME;
    }

    public User getOwner() { return owner;}

    public void setOwner(Player player) { this.owner = player;}

    @Override
    public String toString(){
        return String.format("id: %d, name: %s , cost: %d,owner: %s",this.ID,this.NAME,this.COST,this.owner);
        //return String.format("%s",this.NAME);
    }
}
