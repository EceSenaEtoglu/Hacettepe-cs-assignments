import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class HallWindowNormal {

    private final Hall currentHall;
    private final Film currentFilm;
    private final Stage primaryStage;
    private final User currentBuyer = UserDataBase.getCurrentUser();
    private final int discountPerc;

    private final Button backButton = new Button("BACK",new ImageView(ProjectImages.getLeftArrow()));


    private final Text actionText = new Text();



    public HallWindowNormal(Stage primaryStage, Hall currentHall, Film currentFilm) {

        this.primaryStage = primaryStage;
        this.currentHall = currentHall;
        this.currentFilm = currentFilm;
        this.discountPerc = ScreenDataBase.getDiscountPercentage();

        actionText.setTextAlignment(TextAlignment.CENTER);

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

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(10,10,20,10));
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
        vBox.getChildren().addAll(actionText,backButton);

        vBox.setAlignment(Pos.CENTER);

         return new Scene(vBox);


    }

    public void display() {

        primaryStage.setScene(createScene());

        backButton.setOnAction(e -> new FilmWindowNormal(currentFilm.getName(),primaryStage).display());

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




        // if an empty seat
        if(currentSeat.getOwnerName().equals("null")) {

            button.setGraphic(emptySeat);
        }

        // if reserved by this user make clickable + reserved graphic button
        else if(currentSeat.getOwnerName().equals(currentBuyer.getUserName())) {

            button.setGraphic(reservedSeat);
        }

        // owned by other user
        else {

            button.setGraphic(reservedSeat);
            button.setDisable(true);
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


                String text = String.format("Seat at %d-%d is bought for %d tl succesfully!",rowPos,columnPos,price);
                actionText.setText(text);
            }

            // refund the seat
            else  {

                currentSeat.setOwnerName("null");
                currentSeat.setBoughtPrice(-1);
                button.setGraphic(emptySeat);

                String text = String.format("Seat at %d-%d is refunded succesfully!",rowPos,columnPos);
                actionText.setText(text);

            }


        });
        return button;

    }






}
