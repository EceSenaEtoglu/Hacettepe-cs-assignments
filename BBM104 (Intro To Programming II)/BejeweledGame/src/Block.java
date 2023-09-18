import java.util.Objects;

public class Block {

    private final String SYMBOL;
    public Block(String symbol) {
        this.SYMBOL = symbol;
    }
    public String getSymbol() {
        return this.SYMBOL;
    }

    @Override
    public String toString() {
        return this.SYMBOL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(SYMBOL, block.SYMBOL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(SYMBOL);
    }
}
