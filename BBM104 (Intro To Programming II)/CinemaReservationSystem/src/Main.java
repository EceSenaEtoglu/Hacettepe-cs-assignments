import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage)  {

        primaryStage.setTitle(ScreenDataBase.getTitle());
        primaryStage.getIcons().add(ProjectImages.getTitleImage());


        // store backup
        primaryStage.setOnCloseRequest(event -> {

            // Save file

            try {
                String content = DataWriter.createContent();
                DataWriter.fileWriter(content);

            } catch (Exception e) {

                System.err.println("something went wrong while storing backup"+e.getMessage());
            }
        });


        // create login screen
        Login loginScreen = new Login(primaryStage);
        loginScreen.display();



    }

    public static void main(String[] args)  {

        Properties p = null;
        try {
            p = DataReader.readProperties("assets\\data\\properties.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScreenDataBase.setTitle(p.getProperty("title"));
        ScreenDataBase.setMaxErrorWithoutBlocked(Integer.parseInt(p.getProperty("maximum-error-without-getting-blocked")));
        ScreenDataBase.setDiscountPercentage(Integer.parseInt(p.getProperty("discount-percentage")));
        ScreenDataBase.setBlockTimeSeconds(Integer.parseInt(p.getProperty("block-time")));


        try {
            DataReader.readBackup("assets\\data\\backup.dat");
        } catch (IOException e) {
            //System.err.println("no backup.dat is found, starting app with admin");
            UserDataBase.initializeDataBase();

        }

        ProjectImages.initImages();
        DataReader.loadrailers();
        launch(args);

    }
}
