package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Start of the application
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("./xml/Wizard.fxml"));
            primaryStage.setTitle("BeepBeep Data Mining Wizard");
            primaryStage.setScene(new Scene(root,760,465));
            primaryStage.getIcons().add(new Image("img/LIF_sigle.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
