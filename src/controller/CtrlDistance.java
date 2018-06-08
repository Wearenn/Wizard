package controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CtrlDistance {

    @FXML 
    private RadioButton RbManhattan, RbEuclidean, RbScalar, RbRatio;

    public RadioButton getRbManhattan() {
        return RbManhattan;
    }

    public RadioButton getRbEuclidean() {
        return RbEuclidean;
    }

    public RadioButton getRbScalar() {
        return RbScalar;
    }

    public RadioButton getRbRatio() {
        return RbRatio;
    }

    /**
     * We save in file "Choices.txt" the id of the user choice
     *
     * @throws IOException
     */
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
        return RbManhattan.isSelected() || RbEuclidean.isSelected() || RbScalar.isSelected() || RbRatio.isSelected();
    }
}
