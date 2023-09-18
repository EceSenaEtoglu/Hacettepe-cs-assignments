import java.util.Objects;

public class Player implements Comparable<Player> {

    private Integer point;
    private final String name;

    public Player(String name, int point) {
        this.point = point;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int x) {
        this.point = x;
    }

    // compare by points
    @Override
    public int compareTo(Player o) {
        return this.point.compareTo(o.getPoint());
    }

    @Override
    public String toString() {
        return String.format("%s %d", this.name, this.point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(point, player.point) && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, name);
    }
}
