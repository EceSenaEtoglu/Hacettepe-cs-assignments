import java.util.HashMap;

public class HallDataBase {


    private static HashMap<String,Hall> hallMap = new HashMap<>();

    public static void initHalls (HashMap<String,Hall> halls) {
        hallMap = halls;
    }


    public static void addHallToDataBase(Hall hall) {
        hallMap.put(hall.getHallName(),hall);
    }

    public static void deleteHallFromDataBase(String hallName) {
        hallMap.remove(hallName);
    }

    // check hall database for given hall
    public static boolean isHallExist (String hallName) {
        return hallMap.containsKey(hallName);
    }

    // when a film is deleted, also delete related halls from hallMap
    // halls are uniqiue for films !!!!

    public static void removeRelatedHalls(Film film) {

       try {
           for(String hallName: film.getHallNames()) {
               hallMap.remove(hallName);
           }
       }

       catch (Exception ignored) {

       }

    }

    // film of existing hall!
    // return filmname of the hall from given hall name
    public static String getFilmNameOfHall(String hallName) {

        String filmName = "";

        if(hallMap.get(hallName) != null) {

            Hall hall = hallMap.get(hallName);
            if(hall.getTheFilmName()!= null) {
                return hall.getTheFilmName();
            }
        }

        return filmName;
    }

    public static HashMap<String,Hall> getHallMap() {
        return hallMap;
    }


}
