import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;


public class RemoveHallWindow {

    private final Film currentFilm;
    private final Stage primaryStage;

    private final Button backButton = new Button("BACK",new ImageView(ProjectImages.getLeftArrow()));
    private final Button okButton = new Button("OK");

    private final ComboBox<String> hallBox = new ComboBox<>();
    private final Text welcomeText = new Text();

    public RemoveHallWindow(Film film, Stage primaryStage) {
        this.currentFilm = film;
        this.primaryStage = primaryStage;
        setWelcomeText(film);
        setHallBox();
    }


    public void setWelcomeText(Film film) {
        welcomeText.setText(String.format("Select the hall that you desire to remove from %s and then click OK.",film.getName()));
        welcomeText.setTextAlignment(TextAlignment.CENTER);
    }

    public void setHallBox() {

        hallBox.getItems().clear();

        ArrayList<String> hallNames = currentFilm.getHallNames();

        // construct inside of box
        hallBox.getItems().addAll(hallNames);

        try {
            hallBox.setValue(hallNames.get(0));
        }
        catch (Exception e) {
            hallBox.setValue("");
        }

    }

    public Scene createScene() {


        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton,okButton);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40,10,10,10));
        layout.getChildren().addAll(welcomeText,hallBox,buttonBox);
        layout.setAlignment(Pos.TOP_CENTER);

        return new Scene(layout,500,300);

    }

    public void display() {

        primaryStage.setScene(createScene());

        backButton.setOnAction(e -> new FilmWindowAdmin(currentFilm.getName(),primaryStage).display());

        okButton.setOnAction(e -> {

            String hallName = hallBox.getValue();

            try {
                currentFilm.removeHall(hallName);
                HallDataBase.deleteHallFromDataBase(hallName);
            }

            catch (Exception ex) {
                System.out.println("a null pointer !!");
            }


            setHallBox();

        });


        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(800);
        primaryStage.show();


    }



















}
