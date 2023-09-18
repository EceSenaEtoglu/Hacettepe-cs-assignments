import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;

public class EditUsersWindow {


    Stage primaryStage;
    private User currentUser;
    private User tempUser;
    ObservableList<User> filteredList = FXCollections.observableArrayList();

    Button backButton = new Button("Back",new ImageView(ProjectImages.getLeftArrow()));
    Button promDemClubMember = new Button("Promote/Demote Club Member");
    Button promDemAdmin = new Button("Promote/Demote Admin");

    TableView<User> userTableView = new TableView<>();

    private static boolean canceled = false;


    public EditUsersWindow(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.currentUser = UserDataBase.currentUser;
        setFilteredList();

    }

    public Scene createScene() {

        HBox buttonBox = new HBox(30);
        buttonBox.getChildren().addAll(backButton,promDemClubMember,promDemAdmin);
        buttonBox.setPadding(new Insets(20,10,10,10));
        buttonBox.setAlignment(Pos.CENTER);

        VBox pane = new VBox();
        pane.setPadding(new Insets(20,20,10,10));

        // create table view
        TableColumn<User,String> usernameColumn = new TableColumn<>("User Name");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        usernameColumn.setMinWidth(160);

        TableColumn<User,Boolean> clubMemberColumn= new TableColumn<>("Club Member");
        clubMemberColumn.setCellValueFactory(new PropertyValueFactory<>("isClubMember"));
        clubMemberColumn.setMinWidth(160);

        TableColumn<User,Boolean> adminColumn= new TableColumn<>("Admin");
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        adminColumn.setMinWidth(150);


        this.userTableView.setItems(filteredList);
        this.userTableView.getColumns().add(usernameColumn);
        this.userTableView.getColumns().add(clubMemberColumn);
        this.userTableView.getColumns().add(adminColumn);

        // select default value
        this.userTableView.getSelectionModel().selectFirst(); // end of table view

        // change default empty message
        userTableView.setPlaceholder(new Label("No user available in the database!"));


        pane.getChildren().addAll(this.userTableView,buttonBox);
        pane.setAlignment(Pos.CENTER);

        return new Scene(pane);
    }

    public void display() {

        Scene scene = createScene();

        promDemClubMember.setOnAction(e -> {
            this.tempUser = userTableView.getSelectionModel().getSelectedItem();

            if(tempUser != null) {
                new CheckPassword(this, tempUser, primaryStage, "club member").display();
            }


        });

        promDemAdmin.setOnAction(e -> {
             this.tempUser = userTableView.getSelectionModel().getSelectedItem();

            if(tempUser != null) {
                new CheckPassword(this, tempUser, primaryStage, "admin").display();
            }


        });

        backButton.setOnAction(e -> new WelcomeScreenAdmin(primaryStage).display());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(200);
        primaryStage.show();
    }


    public void setFilteredList() {

        filteredList.clear();

        ArrayList<User> usersFiltered = UserDataBase.getFilteredUsers(this.currentUser);

        this.filteredList.addAll(usersFiltered);


    }


    public static void setCanceled(boolean state) {
        canceled = state;
    }

    public void clubMemberOp() {
        if(!canceled) {
            tempUser.setIsClubMember(!tempUser.getIsClubMember());
            // refresh to see changes
            userTableView.refresh();
        }
    }

    public void processOp(String op) {
        if(op.equals("admin")) {
            adminOp();
        }
        else{
            clubMemberOp();
        }
    }

    public void adminOp() {

        if(!canceled) {
            tempUser.setIsAdmin(!tempUser.getIsAdmin());
            // refresh to see changes
            userTableView.refresh();
        }
    }

    public User getOperatorUser() {
        return currentUser;
    }



}
