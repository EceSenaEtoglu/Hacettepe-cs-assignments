import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class Login implements Screen {

    private final Stage primaryStage;

    private final MediaPlayer mediaPlayer;

    private final  int maxNumberOfWrongAttempt;
    private final  int timePenaltySeconds;

    private  int numberOfWrongAttempt = 0;
    private  boolean banned = false;

    // actionText text
    private final Text actionText = new Text();

    // text fields
    private  final TextField tfUserName = new TextField();
    private  final PasswordField tfPass = new PasswordField();

    // buttons
    private static final Button signUpButton = new Button("SIGN UP");
    private static final Button logInButton = new Button("LOG IN");

    // labels
    private final Label usernameLabel = new Label("Username: ");
    private final Label passLabel = new Label("Password: ");



    public Login(Stage primaryStage) {

        this.maxNumberOfWrongAttempt = ScreenDataBase.getMaxErrorWithoutBlocked();
        this.timePenaltySeconds = ScreenDataBase.getBlockTimeSeconds();
        this.mediaPlayer = MyMediaPlayer.getErrorSoundPlayer();
        this.primaryStage = primaryStage;

        SignUp signUpScreen = new SignUp(mediaPlayer,primaryStage);
        ScreenDataBase.addScreen(signUpScreen);

    }

    private Scene createLoginScene() {


        // welcomeText
        Text line1 = new Text("Welcome to the HUCS Cinema Reservation System!");
        Text line2 = new Text(" Please enter your credentials below and click LOGIN.");
        Text line3 = new Text("You can create a new account by clicking SIGN UP button");

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

        gridPane.add(passLabel,0,1);
        gridPane.add(tfPass,1,1);

        gridPane.add(signUpButton,0,2);
        gridPane.add(logInButton,1,2);

        gridPane.add(actionText,0,6);


        // allign gridPane
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(logInButton, HPos.RIGHT);

        // create vbox for texts
        VBox box = new VBox(15);
        box.setPadding(new Insets(10,10,10,10));
        box.getChildren().addAll(welcomeText,gridPane, actionText);
        box.setAlignment(Pos.CENTER);


        return new Scene(box,800,400);

    }

    public void display() {


        primaryStage.setScene(createLoginScene());
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(200);
        primaryStage.show();


        // pressed on log in
        logInButton.setOnAction(e -> {

                    if(banned) {
                        // play the media, return to start time
                        mediaPlayer.play();
                        mediaPlayer.seek(mediaPlayer.getStartTime());

                        actionText.setText(String.format("ERROR: Please wait until end of %d seconds to make a new attempt", timePenaltySeconds));
                        return;

                    }

                    String userName = tfUserName.getText();

                    // if a valid user enter the welcome  scene
                    if(isAValidEntry(userName, tfPass.getText())) {

                        UserDataBase.currentUser = UserDataBase.getUserFromName(userName);

                        // display normal welcome scene
                        if (UserDataBase.isANormalUser(userName)) {


                            WelcomeScreenNormalUser welcomeScreenNormalUser = new WelcomeScreenNormalUser(primaryStage);

                            resetLogin();
                            numberOfWrongAttempt = 0;
                            ScreenDataBase.addScreen(this);

                            welcomeScreenNormalUser.display();

                        }
                        // display admin scene
                        else {


                            WelcomeScreenAdmin welcomeScreenAdmin = new WelcomeScreenAdmin(primaryStage);

                            resetLogin();
                            numberOfWrongAttempt = 0;
                            ScreenDataBase.addScreen(this);
                            welcomeScreenAdmin.display();

                        }
                    }

                    // update wrong attempts, update banned, check banned
                    else {
                        // play the media, return to start time
                        mediaPlayer.play();
                        mediaPlayer.seek(mediaPlayer.getStartTime());

                        numberOfWrongAttempt ++;
                        banned = (numberOfWrongAttempt == maxNumberOfWrongAttempt);
                        if(banned) {
                            String text = String.format("ERROR: Please wait for %d seconds to make a new operation",timePenaltySeconds);
                            actionText.setText(text);
                            startTimer();
                        }
                    }
                }


        ); // end of login action


        // lead to log in after reseting data
        signUpButton.setOnAction(e -> {

            resetLogin();
            ScreenDataBase.addScreen(this);

            // lead to sign up
            SignUp signUp = (SignUp) ScreenDataBase.getScreen("SignUp");
            signUp.display();
        });

    }

    private void resetLogin() {
        tfUserName.setText("");
        tfPass.setText("");
        actionText.setText("");

    }

    // does not check banned situation
    private  boolean isAValidEntry(String userName,String password) {

        if (userName.equals("")) {
            actionText.setText("ERROR: Username can not be empty");
            tfPass.clear();
            return false;
        }
        else if (password.equals("")) {
            actionText.setText("ERROR: Password can not be empty");
            return false;
        }
        else if (!UserDataBase.doesUserExist(userName)) {
            actionText.setText("ERROR: Username couldn't be found");
            tfPass.clear();
            return false;
        }
        else if (!UserDataBase.isACredential(userName, password)) {
            actionText.setText("ERROR: There is no such credential");
            tfPass.clear();
            return false;
        }

        return true;

    }


    private void startTimer() {
        banned = true;

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(timePenaltySeconds*1000),
                ae -> {
                    banned = false;
                    numberOfWrongAttempt = 0;
                    actionText.setText("");
                }));
        timeline.play();
    }

    @Override
    public String getName() {
        return "Login";
    }
}
