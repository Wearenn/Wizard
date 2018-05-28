package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CtrlDistance {

    @FXML 
    private RadioButton RbManhattan, RbEuclidean, RbScalar, RbRatio;

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
                fichier.write ("Scalar distance");
            } else if (RbRatio.isSelected()){
                fichier.write ("Ratio distance");
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
