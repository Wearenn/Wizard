package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CtrlThreshold {

    @FXML
    private RadioButton RbSmaller, RbLarger;
    @FXML
    private TextField TfThreshold;

    public void writeData() throws IOException {
        //write new informations
        FileWriter fichier = new FileWriter(new File("./src/txt/Choices.txt"),true);
        fichier.write("\n");

        try {
            if (RbLarger.isSelected() && !TfThreshold.getText().equals("")) {
                fichier.write ("exceeds the value of " + TfThreshold.getText());
            } else if (RbSmaller.isSelected() && !TfThreshold.getText().equals("")) {
                fichier.write ("are below the value of " + TfThreshold.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You must make a choice before going far !");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fichier.close();
    }
}
