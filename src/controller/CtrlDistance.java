package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CtrlDistance {

    @FXML 
    private RadioButton RbManhattan, RbEuclidean, RbScalar, RbRatio;
    @FXML
    private Button BtnContinue;

    public Button getBtnContinue(){
        return BtnContinue;
    }

    public void writeData() throws IOException {

        //write new informations
        FileWriter fichier = new FileWriter(new File("./src/txt/Choices.txt"),true);
        fichier.write("\n");
        try {
            if (RbManhattan.isSelected()){
                fichier.write ("Manhattan distance");
            } else if (RbEuclidean.isSelected()){
                fichier.write ("Euclidean distance");
            } else if (RbScalar.isSelected()){
                fichier.write ("Scalar difference");
            } else if (RbRatio.isSelected()){
                fichier.write ("Ratio");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fichier.close();
    }

    /**
     * Verify if a radio Button is selected
     */
    public boolean isSelected(){
        boolean isSelected = false;
        if (RbManhattan.isSelected() || RbEuclidean.isSelected() || RbScalar.isSelected() || RbRatio.isSelected()) {
            isSelected = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must make a choice before going far or fill all fields!");
            alert.showAndWait();
        }
        return isSelected;
    }
}
