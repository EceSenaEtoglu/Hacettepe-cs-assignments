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

public class WelcomeScreenAdmin implements Screen {


    private User user;
    private final Stage primaryStage;
    ComboBox<String> comboBox = new ComboBox<>();

    // buttons
    private static final Button okButton = new Button("OK");
    private static final Button logOutButton = new Button("LOG OUT");
    private static final Button addFilmButton = new Button("Add Film");
    private static final Button removeFilmButton = new Button("Remove Film");
    private static final Button editUsersButton = new Button("Edit Users");


    public WelcomeScreenAdmin(Stage primaryStage) {

        user = UserDataBase.currentUser;
        this.primaryStage = primaryStage;
    }

    public void setComboBox() {

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

    @Override
    public String getName() {
        return "WelcomeScreenAdmin";
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
                    FilmWindowAdmin filmWindowAdmin = new FilmWindowAdmin(comboBox.getValue(), primaryStage);
                    filmWindowAdmin.display();
                } catch (Exception a) {

                    System.out.println(a);

                }
            }


        });

        addFilmButton.setOnAction(e -> {
            AddFilmWindow myAddFilm = new AddFilmWindow(primaryStage);
            myAddFilm.display();
        });

        removeFilmButton.setOnAction(e -> {
            RemoveFilmWindow removeFilmWindow = new RemoveFilmWindow(primaryStage);
            removeFilmWindow.display();
        });

        editUsersButton.setOnAction(e -> new EditUsersWindow(primaryStage).display());

        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(200);
        primaryStage.show();

    }

    private Scene createScene() {


        setComboBox();

        Text textLine1 = new Text(getWelcomeText());
        Text textLine2 = new Text("You can either select film below or do edits.");

        VBox textBox = new VBox(10);

        textBox.setPadding(new Insets(10,10,10,10));
        textBox.setAlignment(Pos.TOP_CENTER);
        textBox.getChildren().addAll(textLine1,textLine2); // end of texts


        // Hbox for combo box + button
        HBox comboButton = new HBox(10);
        comboButton.setPadding(new Insets(10,10,10,10));
        comboButton.getChildren().addAll(comboBox);
        comboButton.getChildren().add(okButton);

        comboButton.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addFilmButton, removeFilmButton, editUsersButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox wrapLogOut = new HBox(logOutButton);
        wrapLogOut.setPadding(new Insets(10,10,10,10));
        wrapLogOut.setAlignment(Pos.BASELINE_RIGHT);

        VBox finalBox = new VBox(12);
        //finalBox.setPadding(new Insets(10,10,10,10));
        finalBox.getChildren().addAll(textBox,comboButton,buttonBox,wrapLogOut);

        return new Scene(finalBox,800,300);

    }



    public  String getWelcomeText() {

        if (user.getIsClubMember()) {

            return String.format("Welcome %s (Admin - Club Member) !", user.getUserName());

        }

        else {
            return String.format("Welcome %s (Admin) !", user.getUserName());
        }

    }


}
