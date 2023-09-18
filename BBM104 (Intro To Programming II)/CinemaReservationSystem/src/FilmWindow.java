import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public abstract class FilmWindow implements Screen {

    Stage primaryStage;

    protected  Button playButton = new Button("",new ImageView(ProjectImages.getRightArrow()));
    protected  Button goBackButton = new Button("<<");
    protected  Button goForwardButton = new Button(">>");
    protected  Button rewindButton = new Button("|<<");

    protected  Button  backButton = new Button("BACK",new ImageView(ProjectImages.getLeftArrow()));
    protected  Button okButton = new Button("OK");

    // Slider
    protected Slider volumeControl = new Slider();

    // duration times
    protected final Duration goBackInDuration = Duration.seconds(-5);
    protected final Duration goForwardInDuration = Duration.seconds(5);

    public abstract Scene createScene();

    public void initSlider(MediaPlayer mediaPlayer){
        volumeControl.setOrientation(Orientation.VERTICAL);
        volumeControl.setValue(70);
        volumeControl.setMinWidth(50);
        mediaPlayer.volumeProperty().bind(
                volumeControl.valueProperty().divide(100));
    }






}
