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


public class HallWindowAdmin {

    private final Button backButton = new Button("BACK",new ImageView(ProjectImages.getLeftArrow()));
    private final ComboBox<User> usersComboBox = new ComboBox<>();
    private final Text actionText = new Text();
    private final Text buyerInfoText = new Text();

    private final Hall currentHall;
    private final Film currentFilm;
    private final Stage primaryStage;
    private User currentBuyer;
    private final int discountPerc;




    public HallWindowAdmin(Stage primaryStage, Hall currentHall, Film currentFilm) {

        this.primaryStage = primaryStage;
        this.currentHall = currentHall;
        this.currentFilm = currentFilm;
        this.discountPerc = ScreenDataBase.getDiscountPercentage();

        setComboBox();

        currentBuyer = usersComboBox.getValue();

        // to update current user everytime a value is selected from combobox
        usersComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> currentBuyer = newValue);

        actionText.setTextAlignment(TextAlignment.CENTER);
        buyerInfoText.setTextAlignment(TextAlignment.CENTER);


    }

    private void setComboBox() {

        // reset
        this.usersComboBox.getItems().clear();

        // fill drop down menu
        ArrayList<User> usersData = new ArrayList<>(UserDataBase.getUserMap().values());
        usersComboBox.getItems().addAll(usersData);

        // set default of combobox
        try {
            usersComboBox.setValue(usersData.get(0));
        }
        catch (IndexOutOfBoundsException ignored) {

        }

    }

    public String getHallText() {

        return String.format("%s %s",currentFilm.toString(),currentHall.toString());
    }

    public int calcPrice() {

        int price = currentHall.getPricePerSeat();

        if(currentBuyer.getIsClubMember()) {
            price = (int) (Math.round(price - (price*discountPerc/100.0)));
        }

        return price;

    }


    public Scene createScene() {

        Text welcomeText = new Text(getHallText());
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(50,5,10,5));
        vBox.getChildren().add(welcomeText);

        int rowNumber = currentHall.getNumberOfRows();
        int columnNumber = currentHall.getNumberOfColumns();

        for(int i = 0;i<rowNumber;i++) {

            HBox box = new HBox(5);
            box.setAlignment(Pos.CENTER);

            for(int j = 0;j<columnNumber;j++) {

                Button button = createButton(i,j);
                box.getChildren().add(button);

            }

            vBox.getChildren().add(box);
        }

        backButton.setAlignment(Pos.BOTTOM_LEFT);
        vBox.getChildren().addAll(usersComboBox,buyerInfoText,actionText,backButton);

        vBox.setAlignment(Pos.CENTER);

        return new Scene(vBox,1000,1500);


    }

    public void display() {

        primaryStage.setScene(createScene());

        backButton.setOnAction(e -> new FilmWindowAdmin(currentFilm.getName(),primaryStage).display());

        primaryStage.setFullScreen(true);


        primaryStage.show();

    }

    public Button createButton(int rowIndex,int columnIndex) {


        ImageView reservedSeat = new ImageView(ProjectImages.getReservedSeat());
        ImageView emptySeat = new ImageView(ProjectImages.getEmptySeat());

        reservedSeat.setFitHeight(60);
        reservedSeat.setFitWidth(60);
        emptySeat.setFitHeight(60);
        emptySeat.setFitWidth(60);

        Button button = new Button("");
        button.setPrefSize(70,70);


        Seat currentSeat = currentHall.getSeat(rowIndex,columnIndex);

        button.setOnMouseEntered(e ->{

            String text = currentSeat.toString();
            buyerInfoText.setText(text);
        });

        button.setOnMouseExited(e -> buyerInfoText.setText(""));



        // if an empty seat
        if(currentSeat.getOwnerName().equals("null")) {
            button.setGraphic(emptySeat);
        }


       else {
           button.setGraphic(reservedSeat);
        }

        button.setOnAction(e -> {

            int rowPos = rowIndex+1;
            int columnPos = columnIndex+1;
            int price = calcPrice();

            // if trying to buy the seat
            if(button.getGraphic().equals(emptySeat)) {

                button.setGraphic(reservedSeat);
                currentSeat.setOwnerName(currentBuyer.getUserName());
                currentSeat.setBoughtPrice(price);


                String text = String.format("Seat at %d-%d is bought for %s %d tl succesfully!",rowPos,columnPos,currentBuyer.getUserName(),price);
                actionText.setText(text);
            }

            // refund the seat
            else  {

                String oldOwner = currentSeat.getOwnerName();
                currentSeat.setOwnerName("null");
                currentSeat.setBoughtPrice(-1);
                button.setGraphic(emptySeat);

                String text = String.format("Seat at %d-%d is refunded to %s succesfully!",rowPos,columnPos,oldOwner);
                actionText.setText(text);

            }
        });
        return button;

    }
}
