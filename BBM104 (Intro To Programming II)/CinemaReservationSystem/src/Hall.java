import java.util.ArrayList;

public class Hall {

    private String hallName = "";
    private String filmOfHall = "";
    private int pricePerSeat;
    private int numberOfRows;
    private int numberOfColumns;
    private Film currentFilm;

    private Seat[][] seatsOfHall;


    public Hall(String filmName, String hallName, String pricePerSeat,String row, String column) {


        this.filmOfHall = filmName;

        this.currentFilm = FilmDataBase.getFilm(filmName);

        this.hallName = hallName;
        //this.hallsFilm = Records.getFilmRecords().get(filmName);

        try {
            this.pricePerSeat = Integer.parseInt(pricePerSeat);
        }
        catch (NumberFormatException e) {
            this.pricePerSeat = 0;
        }

        try {
            numberOfRows = Integer.parseInt(row);
        }
        catch (NumberFormatException e) {
            numberOfRows = 0;
        }
        try {
            numberOfColumns = Integer.parseInt(column);
        }
        catch (NumberFormatException e) {
            numberOfColumns = 0;
        }

        seatsOfHall =  new Seat[numberOfRows][numberOfColumns];
        initiliazeSeats();

    }

    private void initiliazeSeats() {

        for(int i = 0; i<numberOfRows;i++) {

            for(int j = 0;j<numberOfColumns;j++) {
                Seat seat = new Seat(currentFilm,this,i,j,"null",-1);
                seatsOfHall[i][j] = seat;
            }
        }

    }

    public ArrayList<Seat> getSeatArray() {

        ArrayList<Seat> linearSeatArray = new ArrayList<>();

        for(Seat[] row: seatsOfHall) {
            for(Seat column:row) {
                linearSeatArray.add(column);
            }
        }

        return linearSeatArray;
    }

    public String getHallName() {
        return hallName;
    }

    public String getTheFilmName() {
        return filmOfHall;
    }

    public void addSeat(Seat seat) {
        seatsOfHall[seat.getRowIndex()][seat.getColumnIndex()] = seat;
    }

    public Seat getSeat(int rowIndex,int columnIndex) {

        return seatsOfHall[rowIndex][columnIndex];
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public String toString() {
        return String.format("Hall: %s",this.hallName);
    }

    public int getPricePerSeat() {
        return pricePerSeat;
    }
}
