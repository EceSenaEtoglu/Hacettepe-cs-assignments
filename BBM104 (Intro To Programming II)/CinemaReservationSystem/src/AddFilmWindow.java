import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddFilmWindow implements Screen{

    private final Stage primaryStage;
    private final MediaPlayer errorSoundPlayer;

    private final Label nameLbl = new Label("Name:     ");
    private final Label trailerLbl = new Label("Trailer (Path):");
    private final Label durationLbl = new Label("Duration (m): ");

    private final TextField tfName = new TextField();
    private final TextField tfTrailer = new TextField();
    private final TextField tfDuration = new TextField();

    private final Button okButton = new Button("OK");
    private final Button backButton = new Button("BACK",new ImageView(ProjectImages.getLeftArrow()));

    private final Text welcomeText = new Text("Please give name, relative path of the trailer and duration of the film.");
    private final Text actionText = new Text();

    public AddFilmWindow(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.errorSoundPlayer = MyMediaPlayer.getErrorSoundPlayer();

    }

    @Override
    public String getName() {
        return "AddFilmWindow";
    }

    @Override
    public void display() {

        primaryStage.setScene(createScene());
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(200);
        primaryStage.show();

        okButton.setOnAction(e -> {

            if(isAValidOp(tfName.getText(),tfTrailer.getText(),tfDuration.getText())) {

                // a valid op
                int duration = Integer.parseInt(tfDuration.getText());
                // create film
                Film film = new Film(tfName.getText(),tfTrailer.getText(),duration);

                FilmDataBase.addFilm(film);
                actionText.setText("SUCCESS: Film added succesfully!!!");

                tfDuration.setText("");
                tfName.setText("");
                tfTrailer.setText("");

            }

            else {
                errorSoundPlayer.play();
                errorSoundPlayer.seek(Duration.ZERO);
            }

        });

        backButton.setOnAction(e -> new WelcomeScreenAdmin(primaryStage).display());

    }

    public  boolean isAValidOp(String filmName,String filmPath,String durationString) {

        if(filmName.equals("")) {
            actionText.setText("ERROR: Film name can not be empty!!!");
            return false;
        }
        else if(filmPath.equals("")) {
            actionText.setText("ERROR: Trailer path can not be empty!!!");
            return false;
        }

        else if(!isAValidDuration(durationString)) {
            actionText.setText("ERROR: Duration has to be a positive integer !!!");
            return false;
        }

        else if(FilmDataBase.isFilmExist(filmName)) {
            actionText.setText("ERROR: This film already exists!!!");
            return false;
        }

        else if (!FilmDataBase.isTrailerPathExist(filmPath)) {
            actionText.setText("ERROR: There is no such trailer!!!");
            return false;
        }

        return true;


    }

    private boolean isAValidDuration(String durationString) {

        int duration;

        try {
            duration= Integer.parseInt(durationString);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return duration > 0;

    }

    public Scene createScene() {

        // create gridPane
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(10);
        gridPane.setVgap(8);

        // allignments
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        actionText.setTextAlignment(TextAlignment.CENTER);

        // fill gridPane
        gridPane.add(nameLbl,0,0);
        gridPane.add(tfName,1,0);

        gridPane.add(trailerLbl,0,1);
        gridPane.add(tfTrailer,1,1);

        gridPane.add(durationLbl,0,2);
        gridPane.add(tfDuration,1,2);


        gridPane.add(backButton,0,3);
        gridPane.add(okButton,1,3);

        VBox finalBox = new VBox(10);
        finalBox.setPadding(new Insets(3,10,10,10));
        finalBox.setAlignment(Pos.CENTER);
        finalBox.getChildren().addAll(welcomeText,gridPane,actionText);

       return  new Scene(finalBox,400,340);

    }


}
