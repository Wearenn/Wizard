package application;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class FileChoose extends Application {

    @Override
    public void start(final Stage stage) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a log file");
        fileChooser.showOpenDialog(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
