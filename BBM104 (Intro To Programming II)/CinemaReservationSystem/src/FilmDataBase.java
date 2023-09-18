import java.util.ArrayList;
import java.util.HashMap;

public class FilmDataBase  {

    // class to hold films

    private static HashMap<String,Film> films = new HashMap<>();

    private static ArrayList<String> filmTrailerPaths = new ArrayList<>();

    public static void initializeFilms(HashMap<String,Film> filmData) {
        films = filmData;
    }

    // get names from assets/trailers folder
    public static void initTrailers(ArrayList<String> trailers) {
        filmTrailerPaths = trailers;
    }


    public static HashMap<String,Film> getFilmMap() { return films;}

    public static Film getFilm(String filmName) {

        return films.get(filmName);
    }

    public static ArrayList<String> getFilmTrailerPaths() {return filmTrailerPaths;}

    public static void removeFilm(String filmName) {

        if(filmName == null) {
            return;
        }

        films.remove(filmName);

    }

    public static boolean isFilmExist(String filmName) {
        return films.containsKey(filmName);
    }

    public static boolean isTrailerPathExist(String trailerPath) {

        return filmTrailerPaths.contains(trailerPath);
    }

    public static void addFilm(Film film) {
        films.put(film.getName(),film);
    }



    // return hall names array for corresponding film
    public static ArrayList<String> getHalls(String filmName) {

        Film currentFilm = films.get(filmName);

        return currentFilm.getHallNames();

    }
}
