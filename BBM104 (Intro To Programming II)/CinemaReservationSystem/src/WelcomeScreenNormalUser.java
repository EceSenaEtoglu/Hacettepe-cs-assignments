import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class WelcomeScreenNormalUser implements Screen{


    private final User user;
    private final Stage primaryStage;
    ComboBox<String> comboBox = new ComboBox<>();

    // buttons
    private static final Button okButton = new Button("OK");
    private static final Button logOutButton = new Button("LOG OUT");


    public WelcomeScreenNormalUser(Stage primaryStage) {

        user = UserDataBase.currentUser;
        this.primaryStage = primaryStage;
    }

    private void setComboBox() {

        comboBox.getItems().clear();

        // set combobox
        comboBox.setPrefWidth(350);

        // fill drop down menu
        ArrayList<String> filmNames = new ArrayList<>(FilmDataBase.getFilmMap().keySet());
        comboBox.getItems().addAll(filmNames);

        // set default of combobox
        try {
            comboBox.setValue(filmNames.get(0));
        }
        catch (IndexOutOfBoundsException e) {
            comboBox.setValue("");
        }
    }

    private Scene createScene() {

        setComboBox();

        Text textLine1 = new Text(getWelcomeText());
        Text textLine2 = new Text("Select a film and then click OK to continue.");

        VBox textBox = new VBox(10);

        textBox.setPadding(new Insets(10,10,10,10));
        textBox.setAlignment(Pos.TOP_CENTER);
        textBox.getChildren().addAll(textLine1,textLine2); // end of texts

        // Hbox with buttons
        HBox layout = new HBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(comboBox, okButton);
        layout.setAlignment(Pos.CENTER);

        HBox wrapLogOut = new HBox(logOutButton);
        wrapLogOut.setPadding(new Insets(10,10,10,10));
        wrapLogOut.setAlignment(Pos.BASELINE_RIGHT);


        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20,1,10,4));
        logOutButton.setAlignment(Pos.TOP_LEFT);
        vbox.getChildren().addAll(textBox,layout,wrapLogOut);

        return new Scene(vbox,400,300);

    }


    private String getWelcomeText() {

        if (user.getIsClubMember()) {

            return String.format("Welcome %s (Club Member) !", user.getUserName());

        }

        else {
            return String.format(" Welcome %s !", user.getUserName());
        }

    }

    public void display() {

        primaryStage.setScene(createScene());

        // go back to login screen
        logOutButton.setOnAction(e -> {

            Login login = (Login) ScreenDataBase.getScreen("Login");
            login.display();

        });

        okButton.setOnAction(e -> {

            String movieName = comboBox.getValue();

            if((FilmDataBase.getFilmMap().get(movieName)) != null) {
                try {
                    FilmWindowNormal filmWindowNormal = new FilmWindowNormal(comboBox.getValue(), primaryStage);
                    filmWindowNormal.display();
                } catch (NullPointerException ignored) {

                }
            }

        });

        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(200);

        primaryStage.show();

    }


    @Override
    public String getName() {
        return "WelcomeScreenNormalUser";
    }



}
