import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataWriter {


    public static void fileWriter(String content) throws IOException {

        File myFile = new File("assets\\data","backup.dat");
        BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));
        writer.write(content);
        writer.close();

    }

    public static String createContent() {

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<User> sysUsers = new ArrayList<>(UserDataBase.getUserMap().values());

        for (User user:sysUsers) {

            String line = String.format("user\t%s\t%s\t%s\t%s",user.getUserName(),user.getPassword(),user.getMemberInString(),user.getAdminInString());
            stringBuilder.append(line).append("\n");
        }

        ArrayList<Film> sysFilms = new ArrayList<>( FilmDataBase.getFilmMap().values());

        for(Film film:sysFilms) {

            String line = String.format("film\t%s\t%s\t%d",film.getName(),film.getTrailerPath(),film.getDuration());
            stringBuilder.append(line).append("\n");
        }

        ArrayList<Hall> sysHalls = new ArrayList<>(HallDataBase.getHallMap().values());

        for (Hall hall:sysHalls) {

            String line = String.format("hall\t%s\t%s\t%d\t%d\t%d",hall.getTheFilmName(),hall.getHallName(),hall.getPricePerSeat(),hall.getNumberOfRows(),hall.getNumberOfColumns());
            stringBuilder.append(line).append("\n");

            ArrayList<Seat> seats = hall.getSeatArray();

            /*seat[tab]filmname[tab]hallname[tab]row_of_seat[tab]column_of_seat[tab]
              owner_name(null_if_not_owned)[tab]price_that_it_has_been_bought
            (can_be_any_number_if_seat_is_not_bought_yet)*/
            for(Seat seat: seats) {

                line = String.format("seat\t%s\t%s\t%d\t%d\t%s\t%d",seat.getFilmName(),seat.getHallName(),seat.getRowIndex(),seat.getColumnIndex(),seat.getOwnerName(),seat.getBoughtPrice());
                stringBuilder.append(line).append("\n");
            }
        }

        // remove last \n
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

}
