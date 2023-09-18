import java.util.ArrayList;
import java.util.Objects;

public class Film {

    private String name;
    private String trailerPath;
    private int duration;

    private ArrayList<String> hallNames = new ArrayList<>();

    public String getTrailerPath() { return this.trailerPath;}

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Film(String name, String trailerPath, int duration) {
        this.name = name;
        this.trailerPath = trailerPath;
        this.duration = duration;
    }

    public ArrayList<String> getHallNames() { return hallNames;}

    // add hall only to the corresponding film
    public void addHall(Hall hall) {
       this.hallNames.add(hall.getHallName());
    }

    public void removeHall(String hallName) {

        this.hallNames.remove(hallName);
    }

    public boolean isAHallForFilm(String hallName) {
        return this.hallNames.contains(hallName);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(name, film.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s (%d minutes)",this.name,this.duration);
    }
}

