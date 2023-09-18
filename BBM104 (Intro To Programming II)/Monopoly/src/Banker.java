public class Banker extends User {

    // initial money value is 100_000 Tl
    public Banker(String name) {
        super(name,100_000);
    }

    // override toString method in format "name\tmoney"
    @Override
    public String toString() {
        return String.format("%s\t%d",this.getName(),this.getMoney());
    }
}
