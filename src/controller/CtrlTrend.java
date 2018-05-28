package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CtrlTrend {

    @FXML
    private RadioButton RbDirectSize, RbSize, RbOther, RbSource, RbDestination;
    @FXML
    private RadioButton RbRunningA, RbVectorOfM, RbDistinctOcc, RbValueDis, RbCumulativeSum;
    @FXML
    private TextField TxtOther;
    @FXML
    private Slider SliderVector;

    public void writeData() throws IOException {
        //write new informations
        FileWriter fichier = new FileWriter(new File("./src/txt/Choices.txt"),true);
        fichier.write("\n");

        try {
            if (RbDirectSize.isSelected()){
                fichier.write ("Manhattan distance");
            } else if (RbSize.isSelected()){
                fichier.write ("Euclidean distance");
            } else if (RbOther.isSelected()){
                fichier.write ("Scalar distance");
            } else if (RbSource.isSelected()){
                fichier.write ("Ratio distance");
            } else if (RbDestination.isSelected()){
                fichier.write ("Ratio distance");
            } else if (RbRunningA.isSelected()){
                fichier.write ("Ratio distance");
            } else if (RbVectorOfM.isSelected()){
                fichier.write ("Ratio distance");
            } else if (RbDistinctOcc.isSelected()){
                fichier.write ("Ratio distance");
            } else if (RbValueDis.isSelected()){
                fichier.write ("Ratio distance");
            } else if (RbCumulativeSum.isSelected()){
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
