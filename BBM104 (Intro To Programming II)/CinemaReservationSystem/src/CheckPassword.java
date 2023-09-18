import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CheckPassword {

    private final Label passLabel = new Label("Password: ");
    private  final PasswordField tfPass = new PasswordField();

    private final Text opText = new Text();
    private final Text actionText = new Text();

    private final EditUsersWindow prevWindow;

    // stable
    private final Text windowText = new Text();
    private final MediaPlayer errorSoundPlayer;

    private final String opStr;

    // buttons
    private static final Button cancelButton = new Button("CANCEL");
    private static final Button okButton = new Button("CONFIRM");

    private final Stage primaryStage;
     private final User operatorUser;
    private boolean denied = false;

    private int wrongPassCount = 0;


    public CheckPassword(EditUsersWindow prevWindow,User user,Stage primaryStage,String op) {

        this.opStr = op;

        if(op.equals("admin")) {
            String text;
            if(user.getIsAdmin()) {
                text = String.format("You are demoting %s from Admin", user.getUserName());
            }
            else{
                text = String.format("You are promoting %s to Admin", user.getUserName());
            }
            actionText.setText(text);
        }

        else {

            String text;
            if(user.getIsClubMember()) {
                text = String.format("You are demoting %s from Club Member", user.getUserName());
            }
            else{
                text = String.format("You are promoting %s to Club Member", user.getUserName());
            }
            actionText.setText(text);

        }

        this.prevWindow = prevWindow;
        this.operatorUser = prevWindow.getOperatorUser();
        this.primaryStage = primaryStage;
        this.errorSoundPlayer = MyMediaPlayer.getErrorSoundPlayer();


        String txt = String.format("Welcome %s ! provide your password to proceed then click CONFIRM, or press CANCEL to cancel",operatorUser.getUserName());

        actionText.setTextAlignment(TextAlignment.CENTER);
        windowText.setTextAlignment(TextAlignment.CENTER);
        windowText.setText(txt);

        okButton.setDisable(false);
        cancelButton.setDisable(false);
    }

    public void display() {

        primaryStage.setScene(createScene());

        okButton.setOnAction(e -> {

            boolean valid = isAValidEntry(tfPass.getText());
            tfPass.clear();

            // operation is denied
            if (denied) {

                cancelButton.setDisable(true);
                okButton.setDisable(true);

                playSound();
                prevWindow.setCanceled(true);
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(5*1000),
                        ae -> new EditUsersWindow(primaryStage).display()));
                timeline.play();

            }

            else if (valid) {

                cancelButton.setDisable(true);

                // operation permitted
                prevWindow.setCanceled(false);
                prevWindow.processOp(opStr);
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(3*1000),
                        ae -> new EditUsersWindow(primaryStage).display()));
                timeline.play();

            }

            else{
                playSound();
            }

        });

        cancelButton.setOnAction(e -> {

            okButton.setDisable(true);

            opText.setText("Operation canceled ! You are being led to previous screen in 3 seconds.");
            prevWindow.setCanceled(true);
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(3*1000),
                    ae -> new EditUsersWindow(primaryStage).display()));
            timeline.play();

        });

        primaryStage.setWidth(1200);
        primaryStage.setHeight(500);
        primaryStage.show();

        }

    public Scene createScene() {



        HBox loginBox = new HBox(8);
        loginBox.setPadding(new Insets(10,10,10,10));
        loginBox.getChildren().addAll(passLabel,tfPass);
        loginBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(90);
        buttonBox.getChildren().addAll(okButton,cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox finalBox = new VBox(15);
        finalBox.setPadding(new Insets(10,40,10,40));
        finalBox.getChildren().addAll(windowText,actionText,loginBox,buttonBox,opText);
        cancelButton.setAlignment(Pos.TOP_RIGHT);



        finalBox.setAlignment(Pos.CENTER);

        return new Scene(finalBox);

    }

    // does not check banned situation
    private boolean isAValidEntry(String password) {

        boolean validPass = UserDataBase.isACredential(operatorUser.getUserName(),password);

        if (wrongPassCount == 3) {
            opText.setText("ERROR: Operation denied ! Max number of wrong operations (4) is achieved. You are being led to previous screen in 5 seconds");
            denied = true;
            return false;
        }

        else if (password.equals("")) {
            wrongPassCount++;
            opText.setText("ERROR: Password can not be empty");
            return false;
        }
        else if (!validPass) {
            wrongPassCount++;
            opText.setText("ERROR: Wrong password provided ! ");
            return false;
        }

        else if(validPass) {
            opText.setText("Succes !! operation verified. You are being led to previous screen in 3 seconds.");
            return true;
        }

        return true;

}

    private void playSound() {

        // play the media, return to start time
        errorSoundPlayer.play();
        errorSoundPlayer.seek(errorSoundPlayer.getStartTime());

    }

}
