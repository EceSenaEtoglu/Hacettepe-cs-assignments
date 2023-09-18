public abstract class User {

    private final String NAME;
    private int money;

    public User(String name,int money) {
        NAME = name;
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
    public String getName() { return NAME;}

    public void setMoney(int newMoney) {money = newMoney;}

    // update money with given amount
    public void addMoney(int money) {this.money += money;}

    @Override
    public abstract String toString();
}
