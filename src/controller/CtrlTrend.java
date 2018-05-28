package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            if (RbDirectSize.isSelected()){
                fichier.write ("Direct Value");
            } else if (RbSize.isSelected()){
                fichier.write ("Size");
            } else if (RbOther.isSelected() && !TxtOther.getText().equals("")){
                fichier.write ("Other " + TxtOther.getText());
            } else if (RbSource.isSelected()){
                fichier.write ("Source");
            } else if (RbDestination.isSelected()){
                fichier.write ("Destination");
            } else if (RbRunningA.isSelected()){
                fichier.write ("Running average");
            } else if (RbVectorOfM.isSelected()){
                fichier.write ("Vector of moments " + SliderVector.getValue());
            } else if (RbDistinctOcc.isSelected()){
                fichier.write ("Distinct occurrences");
            } else if (RbValueDis.isSelected()){
                fichier.write ("Value distribution");
            } else if (RbCumulativeSum.isSelected()){
                fichier.write ("Cumulative sum");
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
        if (((RbDirectSize.isSelected() || RbSize.isSelected() || (RbOther.isSelected() && !TxtOther.getText().equals(""))
                || RbSource.isSelected() || RbDestination.isSelected()) && (RbRunningA.isSelected() || RbVectorOfM.isSelected()
                || RbDistinctOcc.isSelected() || RbValueDis.isSelected() || RbCumulativeSum.isSelected()))){
            isSelected = true;
        } else if (RbOther.isSelected() && TxtOther.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must type a value");
            alert.showAndWait();
        } else if (!(RbDirectSize.isSelected() || RbSize.isSelected() || (RbOther.isSelected() && !TxtOther.getText().equals(""))
                || RbSource.isSelected() || RbDestination.isSelected())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must make a choice on the first part");
            alert.showAndWait();
        } else if (!(RbRunningA.isSelected() || RbVectorOfM.isSelected() || RbDistinctOcc.isSelected() || RbValueDis.isSelected()
                || RbCumulativeSum.isSelected())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must make a choice on the second part");
            alert.showAndWait();
        }
        return isSelected;
    }
}
