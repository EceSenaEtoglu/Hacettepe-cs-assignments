public class Land extends Property {

    public Land(int id, String name, int cost) {
        super(id, name, cost);
    }

    public int calcRent() {
        if ( this.getCost() >= 0 && this.getCost() <= 2000) {
            return this.getCost() * 2 / 5;
        }
        else if (this.getCost() >= 2001 && this.getCost() <= 3000) {
            return this.getCost() *3 / 10;
        }
        else if (this.getCost() >= 3001 && this.getCost() <= 4000) {
            return this.getCost() * 7 / 20;
        }
        else
            return 0;
        }
    }