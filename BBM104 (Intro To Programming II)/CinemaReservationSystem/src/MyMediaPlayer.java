import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class MyMediaPlayer {

    public static final String  errorSoundPath = "assets\\effects\\error.mp3";

    public static MediaPlayer getErrorSoundPlayer() {
        Media media = new Media(Objects.requireNonNull(MyMediaPlayer.class.getResource(errorSoundPath)).toExternalForm());
        return new MediaPlayer(media);
    }


    /**
     *
     * @param mediaPath media of the specified movie
     * @return mediaPlayer corresponding MediaPlayer object for given path
     */

    public static MediaPlayer getFilmMediaPlayer(String mediaPath) {
        
        Media media = null;

        try {
             media = new Media(Objects.requireNonNull(MyMediaPlayer.class.getResource(mediaPath)).toExternalForm());
        }
        catch (Exception e) {
            System.err.println("something is wrong with assets\\trailers");
        }
        return new MediaPlayer(media);
    }




}
