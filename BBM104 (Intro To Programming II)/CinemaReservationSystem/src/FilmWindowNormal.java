import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class FilmWindowNormal extends FilmWindow{

    private Film currentMovie;
    private MediaPlayer mediaPlayer;
    private ComboBox<String> hallBox = new ComboBox<>();


    public FilmWindowNormal(String filmName, Stage primaryStage) {
        // init current movie
        currentMovie = FilmDataBase.getFilmMap().get(filmName);
        // init media player
        setMediaPlayer(currentMovie);
        // init slider
        initSlider(mediaPlayer);
        this.primaryStage = primaryStage;
    }

    private void setMediaPlayer(Film film) {

        String mediaPath = null;
        try {
            mediaPath = "assets\\trailers\\" + film.getTrailerPath();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        mediaPlayer = MyMediaPlayer.getFilmMediaPlayer(mediaPath);
    }


    @Override
    public Scene createScene() {


        // control media
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(500);
        mediaView.setFitWidth(700);

        // control elements (buttons + slider)
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(2,10,10,20));
        controls.getChildren().addAll(playButton,goBackButton,goForwardButton,rewindButton, volumeControl);
        controls.setAlignment(Pos.CENTER_RIGHT);

        // media + control
        HBox middle = new HBox(5);
        middle.setAlignment(Pos.CENTER);
        middle.getChildren().addAll(mediaView,controls);

        String movieText = String.format("%s ( %d minutes)",currentMovie.getName(),currentMovie.getDuration());
        // movie text
        Text textLine1 = new Text(movieText);

        textLine1.setTextAlignment(TextAlignment.CENTER);

        setHallBox();

        HBox bottom = new HBox(20);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.getChildren().addAll(backButton,hallBox,okButton);


        VBox finalBox = new VBox(10);
        finalBox.setPadding(new Insets(15,15,10,10));
        finalBox.getChildren().addAll(textLine1,middle,bottom);
        finalBox.setAlignment(Pos.CENTER);


        // Create a scene and place it in the stage
        return new Scene(finalBox,650, 500);

    }

    public void  setHallBox() {

        // reset
        this.hallBox.getItems().clear();

        hallBox.setPrefWidth(350);

        // fill drop down menu
        ArrayList<String> hallNames = new ArrayList<>(FilmDataBase.getHalls(currentMovie.getName()));
        hallBox.getItems().addAll(hallNames);

        // set default of combobox
        try {
            hallBox.setValue(hallNames.get(0));
        }
        catch (IndexOutOfBoundsException e) {
            hallBox.setValue("");
        }
    }

    @Override
    public String getName() {
        return "FilmWindowNormal";
    }

    @Override
    public void display() {

        Scene scene = createScene();

        playButton.setOnAction(e -> {
            if (playButton.getText().equals("")) {
                mediaPlayer.play();
                playButton.setGraphic(null);
                playButton.setText("||");
            } else {
                mediaPlayer.pause();
                playButton.setGraphic(new ImageView(ProjectImages.getRightArrow()));
                playButton.setText("");
            }
        });


        goForwardButton.setOnAction(e-> {

            Duration currentDuration = mediaPlayer.getCurrentTime();
            Duration x = currentDuration.add(goForwardInDuration);
            mediaPlayer.seek(x);
        });

        goBackButton.setOnAction(e -> {

            Duration currentDuration = mediaPlayer.getCurrentTime();
            Duration x = currentDuration.add(goBackInDuration);
            mediaPlayer.seek(x);
        } );

        rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));

        backButton.setOnAction(e -> {

            resetScreen();
            WelcomeScreenNormalUser welcomeScreenNormalUser = new WelcomeScreenNormalUser(primaryStage);
            welcomeScreenNormalUser.display();

        });

        okButton.setOnAction(e -> {

            String hallName = hallBox.getValue();
            Hall currentHall = HallDataBase.getHallMap().get(hallName);

            if(currentHall != null) {

                try {

                    resetScreen();


                    new HallWindowNormal(primaryStage, currentHall, currentMovie).display();
                }

                catch (Exception a) {

                    System.out.println(a);

                }

            }

        });

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(1000);

        primaryStage.show(); // Display the stage


    }

    public void resetScreen() {
        mediaPlayer.stop();
        //mediaPlayer.seek(Duration.ZERO);
        //mediaPlayer = null;
    }
}
