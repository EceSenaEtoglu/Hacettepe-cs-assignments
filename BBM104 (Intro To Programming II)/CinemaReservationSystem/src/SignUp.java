import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SignUp implements Screen {

    private final Stage primaryStage;

    private final MediaPlayer mediaPlayer;



    // actionText text
    private  final Text actionText = new Text();

    // text fields
    private final TextField tfUserName = new TextField();
    private final PasswordField tfPass1 = new PasswordField();
    private final PasswordField tfPass2 = new PasswordField();

    // buttons
    private static final Button signUpButton = new Button("SIGN UP");
    private static final Button logInButton = new Button("LOG IN");


    public SignUp(MediaPlayer mediaPlayer,Stage stage) {
        this.mediaPlayer = mediaPlayer;
        this.primaryStage = stage;
    }


    public void display() {

        primaryStage.setScene(createScene());
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(400);
        primaryStage.show();

        signUpButton.setOnAction(e -> {

            // processSignUp displays proper text on screen, returns boolean if succesfull

            // if succesfull add user to data base
            // clear signup fields
            if(processSignUp(tfUserName.getText(),tfPass1.getText(),tfPass2.getText()) ){

                String userName = tfUserName.getText();
                String password = tfPass1.getText();
                UserDataBase.addUser(userName,password);

                // clear text fields after succesful sign up
                resetSignUpFields();

            }

            // if not succesfull
            // update wrong attempts, update banned, check banned, play sound

            else {
                // play the media, return to start time
                mediaPlayer.play();
                mediaPlayer.seek(mediaPlayer.getStartTime());

            }

        });


        // pressed on log in
        // lead to  login window after reseting the page
        logInButton.setOnAction(e -> {

            resetSignUpFields();
            ScreenDataBase.addScreen(this);

            // lead to log in
            Login login1 = (Login) ScreenDataBase.getScreen("Login");
            login1.display();

        });

    }

    private void resetSignUpFields() {
        tfUserName.setText("");
        tfPass1.setText("");
        tfPass2.setText("");
    }

    private  boolean processSignUp(String userName, String password1, String password2) {

        if (userName.equals("")) {
            actionText.setText("ERROR: Username can not be empty");
            tfPass1.clear();
            tfPass2.clear();
            return false;
        }
        else if (password1.equals("") || password2.equals("")) {
            actionText.setText("ERROR: Password can not be empty");
            tfPass1.clear();
            tfPass2.clear();
            return false;
        }

        else if (UserDataBase.doesUserExist(userName)) {
            actionText.setText("ERROR: This username already exists!!");
            tfPass1.clear();
            tfPass2.clear();
            return false;
        }

        else if(!password1.equals(password2)) {
            actionText.setText("ERROR: Passwords have to match");
            tfPass1.clear();
            tfPass2.clear();
            return false;
        }

        else {
            actionText.setText("SUCCES!: You have registered with your new credentials");
            return true;

        }

    }

    private  Scene createScene() {

        // labels
        Label usernameLabel = new Label("Username: ");
        Label passLabel1 = new Label("Password: ");
        Label passLabel2 = new Label("Password: ");

        Text line1 = new Text("Welcome to the HUCS Cinema Reservation System!");
        Text line2 = new Text("Fill the form below to create a new account.");
        Text line3 = new Text("You can go to Log In page by clicking LOG IN button.");

        VBox welcomeText = new VBox(10);
        welcomeText.setPadding(new Insets(15,10,10,10));
        welcomeText.getChildren().addAll(line1,line2,line3);
        welcomeText.setAlignment(Pos.CENTER);


        // create gridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setHgap(10);
        gridPane.setVgap(8);

        // fill gridPane
        gridPane.add(usernameLabel,0,0);
        gridPane.add(tfUserName,1,0);

        gridPane.add(passLabel1,0,1);
        gridPane.add(tfPass1,1,1);

        gridPane.add(passLabel2,0,2);
        gridPane.add(tfPass2,1,2);



        gridPane.add(logInButton,0,3);
        gridPane.add(signUpButton,1,3);

        gridPane.add(actionText,0,7);


        // allign gridPane
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(signUpButton, HPos.RIGHT);

        // create vbox for texts
        VBox box = new VBox(15);
        box.setPadding(new Insets(10,10,10,10));
        box.getChildren().addAll(welcomeText,gridPane, actionText);
        box.setAlignment(Pos.CENTER);



        return new Scene(box,800,400);

    }


    @Override
    public String getName() {
        return "SignUp";
    }
}
