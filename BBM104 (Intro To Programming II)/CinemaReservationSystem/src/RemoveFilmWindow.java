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

public class RemoveFilmWindow {

    Stage primaryStage;
    ComboBox<String> movieBox = new ComboBox<>();


    public RemoveFilmWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setComboBox() {

        movieBox.getItems().clear();

        // set combobox
        movieBox.setPrefWidth(350);

        // fill drop down menu
        ArrayList<String> filmNames = new ArrayList<>(FilmDataBase.getFilmMap().keySet());
        movieBox.getItems().addAll(filmNames);


        // set default of combobox
        try {
            movieBox.setValue(filmNames.get(0));
        }
        catch (IndexOutOfBoundsException e) {
            movieBox.setValue("");
        }
    }


    public void display() {

        setComboBox();

        HBox hbox = new HBox(5);
        hbox.getChildren().add(movieBox);
        hbox.setAlignment(Pos.CENTER);

        Text text = new Text("Select the film you desire to remove and then click OK.");
        text.setTextAlignment(TextAlignment.CENTER);

        Button backButton2 = new Button("BACK",new ImageView(ProjectImages.getLeftArrow()));
        Button okButton2 = new Button("OK");

        HBox buttonBox = new HBox(15);
        buttonBox.getChildren().addAll(backButton2,okButton2);
        buttonBox.setAlignment(Pos.CENTER);

        VBox finalBox = new VBox(15);
        finalBox.setPadding(new Insets(3,10,10,10));
        finalBox.setAlignment(Pos.CENTER);
        finalBox.getChildren().addAll(text,hbox,buttonBox);

        okButton2.setOnAction(e -> {

            String movieName = movieBox.getValue();
            Film currentFilm = FilmDataBase.getFilm(movieName);

            try {
                HallDataBase.removeRelatedHalls(currentFilm);
                FilmDataBase.removeFilm(movieName);
            }
            catch (Exception ignored) {

            }

            setComboBox();
        });


        backButton2.setOnAction(e -> {
            WelcomeScreenAdmin welcomeScreenAdmin = new WelcomeScreenAdmin(primaryStage);
            welcomeScreenAdmin.display();
        });

        Scene newScene = new Scene(finalBox,750,300);
        primaryStage.setScene(newScene);
        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(200);
        primaryStage.show();

    }


}
