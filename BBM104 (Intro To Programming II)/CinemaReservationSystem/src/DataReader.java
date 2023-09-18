import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DataReader {


    /**
     * Reads file, returns hashmaps
     * @param fileName name of the file
     * @return fileData hashmap key: DataBaseType value, empty databases (not null) if no data exists
     * @throws IOException
     */
    public static void readBackup(String fileName ) throws IOException {

        HashMap<String,User> userData = new HashMap<>();
        HashMap<String,Film> filmData = new HashMap<>();
        HashMap<String,Hall> hallData = new HashMap<>();



        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while((line = reader.readLine()) != null) {

            // eliminate whitespace
            line = line.trim();

            // FIX TO ELIMINATE \T
            if(line.length() > 1) {

                String[] data = line.split("\t");

                String prefix = data[0];

                switch (prefix) {

                    // if a user data
                    case "user":

                        // data[1] name
                        // data[2] password
                        // data[3] boolean (in string) club
                        // data[4] boolean admin
                        User user = new User(data[1],data[2],data[3],data[4]);

                        // fill hashmap
                        userData.put(user.getUserName(),user);
                        break;

                    case "film":

                        // data[1] name
                        // data[2] trailerPath
                        // data[3] durationMin

                        Film film = new Film(data[1],data[2],Integer.parseInt(data[3]));
                        filmData.put(film.getName(),film);
                        break;



                    case "hall":
                        // hall Avengers: Endgame	S1	70	10	8

                        // data[1] film name
                        // data[2] hallname
                        // data[3] price per seat
                        // data[4] row (number)
                        // data[5] column (number)

                        Hall hall = new Hall(data[1],data[2],data[3],data[4],data[5]);
                        filmData.get(data[1]).addHall(hall);
                        hallData.put(hall.getHallName(),hall);
                        break;

                    /*seat[tab]filmname[tab]hallname[tab]row_of_seat[tab]column_of_seat[tab]
                    owner_name(null_if_not_owned)[tab]price_that_it_has_been_bought
                            (can_be_any_number_if_seat_is_not_bought_yet*/

                    case "seat":

                        Film seatsFilm = filmData.get(data[1]);
                        Hall seatsHall = hallData.get(data[2]);

                        int row = Integer.parseInt(data[3]);
                        int column = Integer.parseInt(data[4]);
                        int price = Integer.parseInt(data[6]);

                        //data[5] username

                        Seat seat = new Seat(seatsFilm,seatsHall,row,column,data[5],price);
                        seatsHall.addSeat(seat);
                        break;
                }

            }

        } // end of while

        reader.close();

        UserDataBase.initializeDataBase(userData);
        FilmDataBase.initializeFilms(filmData);
        HallDataBase.initHalls(hallData);
    }

    public static Properties readProperties(String propertiesFile) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
        Properties p =new Properties();
        p.load(reader);
        reader.close();
        return p;
    }

    public static void loadrailers() {

        ArrayList<String> trailers = new ArrayList<>();

        //Creating a File object for directory
        File trailersPath = new File("assets\\trailers");

        // list of all files and directories
        String[] myList = trailersPath.list();

        if (myList != null) {
            for(String fileName:myList) {

                trailers.add(fileName);

            }
        }

        FilmDataBase.initTrailers(trailers);
    }
}
