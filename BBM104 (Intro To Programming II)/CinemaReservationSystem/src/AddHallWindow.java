import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class AddHallWindow {

    private final Stage primaryStage;
    Film currentFilm;
    private final MediaPlayer errorSoundPlayer;

    ComboBox<String> rowDropDown = new ComboBox<>();
    ComboBox<String> columnDropDown = new ComboBox<>();

    Label nameLbl = new Label("Name:");
    Label priceLbl = new Label("Price:");
    Label rowLbl = new Label("Row:                    ");
    Label columnLbl = new Label("Column:              ");

    TextField priceField = new TextField();
    TextField nameField = new TextField();

    Button okButton = new Button("OK");
    Button backButton = new Button("Back",new ImageView(ProjectImages.getLeftArrow()));

    Text actionText = new Text();

    public AddHallWindow(Film film,Stage primaryStage) {

        currentFilm = film;
        this.primaryStage = primaryStage;
        this.errorSoundPlayer = MyMediaPlayer.getErrorSoundPlayer();

        createColumnDropDown();
        createRowDropDown();
    }

    public void createRowDropDown() {

        rowDropDown.getItems().clear();

        ArrayList<String> numbers = new ArrayList<>();

        for(int i = 3; i<=10;i++) {
            numbers.add(Integer.toString(i));
        }

        // set combobox
        rowDropDown.setMinWidth(100);


        rowDropDown.getItems().addAll(numbers);

        rowDropDown.setValue(numbers.get(0));

    }

    public void createColumnDropDown() {

        columnDropDown.getItems().clear();

        ArrayList<String> numbers = new ArrayList<>();

        for(int i = 3; i<=10;i++) {
            numbers.add(Integer.toString(i));
        }

        // set combobox
        columnDropDown.setMinWidth(100);


        columnDropDown.getItems().addAll(numbers);

        columnDropDown.setValue(numbers.get(0));

    }




    public Scene createScene() {


        String movieText = String.format("  %s ( %d minutes)",currentFilm.getName(),currentFilm.getDuration());
        Text welcomeText = new Text(movieText);
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        VBox wrapAction = new VBox();
        wrapAction.getChildren().add(actionText);


        // selection pane
        GridPane upperPane = new GridPane();

        upperPane.setPadding(new Insets(10,10,10,10));
        upperPane.setHgap(10);
        upperPane.setVgap(8);

        upperPane.add(rowLbl,0,0);
        upperPane.add(rowDropDown,1,0);
        upperPane.add(columnLbl,0,1);
        upperPane.add(columnDropDown,1,1);
        //upperPane.setAlignment(Pos.CENTER);


        // middle pane
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(10);
        gridPane.setVgap(8);

        // allignments
        //gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        actionText.setTextAlignment(TextAlignment.CENTER);

        // fill gridPane
        gridPane.add(nameLbl,0,0);
        gridPane.add(nameField,1,0);

        gridPane.add(priceLbl,0,1);
        gridPane.add(priceField,1,1);

        gridPane.add(backButton,0,3);
        gridPane.add(okButton,1,3);


        VBox finalBox = new VBox(20);
        finalBox.setPadding(new Insets(50,180,10,185));
        finalBox.getChildren().addAll(welcomeText,upperPane,gridPane,actionText);

        BorderPane finalPane = new BorderPane();
        finalPane.setCenter(finalBox);

        return  new Scene(finalPane,500,400);


    }

    public void display() {

        primaryStage.setScene(createScene());

        okButton.setOnAction(e -> {


            // add the hall to the system
            if(isAValidOp(nameField.getText(),priceField.getText())) {


                String filmName = currentFilm.getName();

                // create hall
                Hall hall = new Hall(filmName,nameField.getText(),priceField.getText(),rowDropDown.getValue(),columnDropDown.getValue());

                currentFilm.addHall(hall);
                HallDataBase.addHallToDataBase(hall);


                actionText.setText("SUCCESS: Hall added succesfully!!!");

                nameField.setText("");
                priceField.setText("");

            }

            else {
                errorSoundPlayer.play();
                errorSoundPlayer.seek(Duration.ZERO);
            }

        });



        backButton.setOnAction(e -> {
            FilmWindowAdmin filmWindowAdmin = new FilmWindowAdmin(currentFilm.getName(),primaryStage);
            filmWindowAdmin.display();
        });

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        primaryStage.show();

    }

    public boolean isAValidOp(String hallName,String priceString) {

        if(hallName.equals("")) {
            actionText.setText("ERROR: Hall name can not be empty!!!");
            return false;
        }

        else if(!isAValidPrice(priceString)) {
            actionText.setText("ERROR: Price has to be a positive integer !!!");
            return false;
        }

        else if(currentFilm.isAHallForFilm(hallName)) {
            actionText.setText("ERROR: This hall already exists in this film!!!");
            return false;
        }

        else if(HallDataBase.isHallExist(hallName)) {

            String filmName = HallDataBase.getFilmNameOfHall(hallName);

            String errorString = String.format("ERROR: This hall already exists in other film: %s",filmName);
            actionText.setText(errorString);
            return false;

        }

        return true;

    }

    private boolean isAValidPrice(String priceString) {

        int price;

        try {
            price = Integer.parseInt(priceString);
        }
        catch (NumberFormatException e) {
            return false;
        }

        return price > 0;

    }
}