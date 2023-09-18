public class Seat {

    private final Film film;
    private final Hall hall;
    private String ownerName = "null";
    private int boughtPrice = -1;
    private final int rowIndex;
    private final int columnIndex;



    public Seat(Film film, Hall hall, int rowIndex, int columnIndex, String ownerName,int boughtPrice) {

        this.film = film;
        this.hall = hall;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.ownerName = ownerName;
        this.boughtPrice = boughtPrice;
    }

    public String getFilmName() {
        return film.getName();
    }
    public String getHallName() {
        return hall.getHallName();
    }

    public void setBoughtPrice(int price) {
        this.boughtPrice = price;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getBoughtPrice() {
        return boughtPrice;
    }

    @Override
    public String toString() {

        if (this.ownerName.equals("null")) {
            return "Not bought yet!";
        }

        else {
            return String.format("Bought by %s for %d tl",this.ownerName,this.boughtPrice);
        }
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public String getOwnerName() {
        return this.ownerName;
    }
}
